package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Farm extends EconomicBuilding {

  private static final int FARM_COST = 1000;
  private static final int[] FARM_UPGRADE_COST = { 500, 700 };
  private static final int[] HARVEST_VALUES = { 500, 700, 1000 };

  public Farm() {
    super(FARM_COST, FARM_UPGRADE_COST[0]);
  }

  @Override
  public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
    super.upgrade();
    upgradeCost(FARM_UPGRADE_COST);
  }

  @Override
  public int harvest() {
    return HARVEST_VALUES[getLevel() - 1];
  }
}
