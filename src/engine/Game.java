package engine;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import buildings.EconomicBuilding;
import exceptions.FriendlyFireException;
import exceptions.TargetNotReachedException;
import units.Army;
import units.Status;
import units.Unit;
import units.UnitFactory;
import utlis.ReadingCSVFile;

public class Game {

  private Player player;
  private GameListener gameListener;
  private UnitFactory unitFactory = new UnitFactory();
  private ArrayList<City> availableCities;
  private ArrayList<Route> distances;
  private static final int MAX_TURN_COUNT = 50;
  private int currentTurnCount = 1;
  private static final double INITIAL_TREASURY = 5000;
  private static final String ON_ROAD_STRING = "OnRoad";
  private static final String NUMBER_OF_UNITS_STRING = "Number Of Units: ";

  public Player getPlayer() {
    return player;
  }

  public UnitFactory getUnitFactory() {
    return unitFactory;
  }

  public void setUnitFactory(UnitFactory unitFactory) {
    this.unitFactory = unitFactory;
  }

  public GameListener getGameListener() {
    return gameListener;
  }

  public void setGameListener(GameListener gameListener) {
    this.gameListener = gameListener;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public List<City> getAvailableCities() {
    return this.availableCities;
  }

  public List<Route> getDistances() {
    return this.distances;
  }

  public int getCurrentTurnCount() {
    return this.currentTurnCount;
  }

  public void setCurrentTurnCount(int currentTurnCount) {
    this.currentTurnCount = currentTurnCount;
  }

  public Game(String playerName, String cityName, String level) throws IOException {
    this.player = new Player(playerName);
    player.setTreasury(INITIAL_TREASURY);
    setCurrentTurnCount(1);
    distances = new ArrayList<>();
    availableCities = new ArrayList<>();
    loadCitiesAndDistances();
    loadCitiesFiles(cityName, level);
  }

  private void loadCitiesFiles(String cityName, String level) throws IOException {
    for (City city : availableCities) {
      if (!city.getName().equals(cityName)) {
        String fileName = city.getName().toLowerCase() + "_army.csv";
        var path = Path.of("assets", "csv", level, fileName).toFile().toString();
        loadArmy(city.getName(), path);
      } else {
        player.addControlCity(city);
      }
    }
  }

  public void loadCitiesAndDistances() throws IOException {
    List<List<String>> data = ReadingCSVFile.readFile("./assets/csv/distances.csv");
    for (List<String> line : data) {
      String from = line.get(0);
      String to = line.get(1);
      var distance = Integer.parseInt(line.get(2));
      distances.add(new Route(from, to, distance));
      addToSet(new City(to));
      addToSet(new City(from));
    }
  }

  private void addToSet(City city) {
    if (!availableCities.contains(city)) {
      availableCities.add(city);
    }
  }

  public void loadArmy(String cityName, String path) throws IOException {
    ArrayList<Unit> unitList = new ArrayList<>();
    List<List<String>> data = ReadingCSVFile.readFile(path);
    var currentCity = searchForCity(cityName, availableCities);
    var army = new Army(cityName);
    readUnitValues(unitList, data, army);
    army.setUnits(unitList);
    if (currentCity != null) {
      currentCity.setDefendingArmy(army);
    }
  }

  private void readUnitValues(ArrayList<Unit> unitList, List<List<String>> data, Army army) {
    for (List<String> line : data) {
      String unitName = line.get(0);
      var level = Integer.parseInt(line.get(1));
      setUnitType(unitList, unitName, level, army);
    }
  }

  public static Army searchForArmy(String cityName, List<Army> list) {
    return list.stream().filter(army -> army.getCurrentLocation().equals(cityName)).findFirst().orElse(null);
  }

  public static City searchForCity(String cityName, List<City> availableCities) throws NullPointerException {
    return availableCities.stream().filter(city -> cityName.equals(city.getName())).findFirst()
        .orElse(new City(cityName));
  }

  private void setUnitType(ArrayList<Unit> unitList, String unitName, int level, Army army) {
    var unit = unitFactory.createUnit(unitName, level);
    addUnitToUnits(unitList, army, unit);
  }

  private void addUnitToUnits(ArrayList<Unit> unitList, Army army, Unit archer) {
    archer.setParentArmy(army);
    unitList.add(archer);
  }

  public int searchForDistance(String x, String y) {
    for (Route distance : distances) {
      if (distance.containsCity(x, y)) {
        return distance.getDistance();
      }
    }
    return 0;
  }

  public void targetCity(Army army, String targetName) {
    if (army.getCurrentLocation().equals(targetName)) {
      return;
    }
    var currentCity = army.getCurrentLocation();
    var previousCity = searchForCity(army.getCurrentLocation(), availableCities);
    int distance = searchForDistance(currentCity, targetName);

    if (army.getCurrentLocation().equals(ON_ROAD_STRING)) {
      currentCity = army.getTarget();
      distance = searchForDistance(currentCity, targetName);
      distance += army.getDistancetoTarget();
    }

    if (army.getCurrentStatus().equals(Status.BESIEGING)) {
      previousCity.removeSieging();
    }

    army.setDistancetoTarget(distance);
    army.setTarget(targetName);
    army.setCurrentLocation(ON_ROAD_STRING);
    army.setCurrentStatus(Status.MARCHING);
    if (gameListener != null) {
      gameListener.onTargetCity(army, previousCity);
    }
  }

  public void endTurn() {
    currentTurnCount++;
    player.setCurrentSiege(0);
    clearBuildings();
    handleTarget();
    feedArmy();
    updateSiege();
    whoWon();
  }

  private void updateSiege() {
    for (City city : availableCities) {
      if (city.isUnderSiege()) {
        var army = searchForArmy(city.getName(), player.getControlledArmies());
        if (city.reachedMaxSiege()) {
          forceAttack(city, army);
        } else {
          attackerLoseUnits(city, army);
        }
      }
    }
  }

  private void attackerLoseUnits(City city, Army army) {
    city.getDefendingArmy().killUnits();
    city.incTurnsUnderSiege();
    if (gameListener != null) {
      gameListener.onSiegeUpdate(city, army);
    }
  }

  private void forceAttack(City city, Army army) {
    city.setUnderSiege(false);
    if (gameListener != null) {
      gameListener.attackY3am(city, army);
    }
  }

  private void handleTarget() {
    for (Army army : player.getControlledArmies()) {
      if (!army.isReached()) {
        army.decTargetDistance();
        if (gameListener != null) {
          gameListener.onDistanceUpdated(army);
        }
        if (army.getDistancetoTarget() == 0) {
          army.setArmyArrived();
          if (gameListener != null) {
            gameListener.armyArrived(army);
          }
        }
      }
    }
  }

  private void feedArmy() {
    double foodNeeded = 0;
    foodNeeded += player.attackingArmyFeeding();
    foodNeeded += player.defendingArmyFeeding();
    if (!player.isFoodEnough(foodNeeded)) {
      player.loseAttackingArmies();
      player.loseDefendingArmies();
    }
    player.setFood(player.getFood() - foodNeeded);
    if (gameListener != null) {
      gameListener.onFeedUpdated();
    }
  }

  private void clearBuildings() {
    for (City city : player.getControlledCities()) {
      city.clearMilitaryBuildings();
      for (EconomicBuilding economicBuilding : city.getEconomicalBuildings()) {
        economicBuilding.setCoolDown(false);
        player.getHarvestAndTreasury(economicBuilding);
      }
    }
    if (gameListener != null) {
      gameListener.onTreasuryUpdate();
    }
  }

  public void occupy(Army army, String cityName) {
    var city = searchForCity(cityName, availableCities);
    player.getControlledArmies().remove(army);
    player.addControlCity(city);
    city.setDefendingArmy(army);
    city.removeSieging();
    army.setCurrentStatus(Status.IDLE);
    if (gameListener != null) {
      gameListener.onOccupy(city, army);
    }
  }

  public void autoResolve(Army attacker, Army defender) throws FriendlyFireException {
    if (player.isFriend(attacker) && player.isFriend(defender)) {
      throw new FriendlyFireException();
    }
    var attackerTurn = true;
    while (theBattleIsGoing(attacker, defender)) {
      attackerTurn = alternateAttacking(attacker, defender, attackerTurn);
    }
    battleEnded(attacker, defender);
  }

  private boolean alternateAttacking(Army attacker, Army defender, boolean attackerTurn) throws FriendlyFireException {
    var attackerUnit = attacker.getRandomUnit();
    var defenderUnit = defender.getRandomUnit();
    if (attackerTurn) {
      attackerUnit.attack(defenderUnit);
    } else {
      defenderUnit.attack(attackerUnit);
    }
    attackerTurn = !attackerTurn;
    return attackerTurn;
  }

  private boolean theBattleIsGoing(Army attacker, Army defender) {
    return attacker.didWinTheBattle() && defender.didWinTheBattle();
  }

  private void removeTheAttack(Army attacker, Army defender) {
    player.getControlledArmies().remove(attacker);
    var currentCity = searchForCity(defender.getCurrentLocation(), availableCities);
    currentCity.removeSieging();

  }

  public void battleEnded(Army attacker, Army defender) {
    if (attacker.didWinTheBattle() && !defender.didWinTheBattle()) {
      occupy(attacker, defender.getCurrentLocation());

      if (gameListener != null) {
        gameListener.onBattleEnded(attacker, defender, true);
      }
    } else if (defender.didWinTheBattle() && !attacker.didWinTheBattle()) {
      removeTheAttack(attacker, defender);
      gameListener.onBattleEnded(attacker, defender, false);
    }
  }

  public void whoWon() {
    if (isGameOver()) {
      if (isThePlayerWon()) {
        if (gameListener != null) {
          gameListener.playerWon();
        }
      } else {
        if (gameListener != null) {
          gameListener.playerLost();
        }
      }
    }
  }

  public boolean isGameOver() {
    return isThePlayerWon() || isTurnsOver();
  }

  private boolean isTurnsOver() {
    return currentTurnCount > MAX_TURN_COUNT;
  }

  private boolean isThePlayerWon() {
    return availableCities.size() == player.getControlledCities().size();
  }

  public void startBattle(Army army, City city) throws FriendlyFireException, TargetNotReachedException {
    if (army.haveReached(city)) {
      throw new TargetNotReachedException("the army hasn't arrived yet!");
    }

    if (player.isFriend(city)) {
      throw new FriendlyFireException("you can't attack a friend");
    }

  }

  public String toString(Army army) {
    var message = new StringBuilder();
    var city = searchForCity(army.getCurrentLocation(), this.getAvailableCities());
    unitsLog(army, message);
    marchingLog(army, message);
    besiegingLog(army, message, city);
    return message.toString();
  }

private void unitsLog(Army army, StringBuilder message) {
	message.append("Current Location : " + army.getCurrentLocation() + "\n");
  message.append("Current Status : " + army.getCurrentStatus() + "\n");
  message.append(NUMBER_OF_UNITS_STRING + " : " + army.getUnits().size() + "\n");
}

  private void besiegingLog(Army army, StringBuilder message, City city) {
    if (army.getCurrentStatus().equals(Status.BESIEGING)) {
      message.append("Besieged City : " + army.getCurrentLocation() + "\n");
      message.append("Turns undersiege : " + city.getTurnsUnderSiege() + "\n" + "\n");
    }
  }

  private void marchingLog(Army army, StringBuilder message) {
    if (army.getCurrentStatus().equals(Status.MARCHING)) {
      message.append("Target : " + army.getTarget() + "\n");
      message.append("No of Turns till reach : " + army.getDistancetoTarget() + "\n" + "\n");
    }
  }
}
