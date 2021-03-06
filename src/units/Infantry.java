package units;

public class Infantry extends Unit {

  private static final double[][] ARCHER_VAlUES = { { 50, 0.5, 0.6, 0.7 }, { 50, 0.5, 0.6, 0.7 },
      { 60, 0.6, 0.7, 0.8 } };
  private static final double[] ARCHER_TARGET_FACTORS = { 0.3, 0.4, 0.5 };
  private static final double[] INFANTRY_TARGET_FACTORS = { 0.1, 0.2, 0.3 };
  private static final double[] CAVALRY_TARGET_FACTORS = { 0.1, 0.2, 0.25 };

  public Infantry(int level, int maxSoldierCount, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
    super(level, maxSoldierCount, idleUpkeep, marchingUpkeep, siegeUpkeep);
  }

  public Infantry(int level) {
    this(level, (int) ARCHER_VAlUES[level - 1][0], ARCHER_VAlUES[level - 1][1], ARCHER_VAlUES[level - 1][2],
        ARCHER_VAlUES[level - 1][3]);
  }

  @Override
  public double unitFactor(Unit target, int level) {
    if (target instanceof Archer) {
      return ARCHER_TARGET_FACTORS[level - 1];
    }
    if (target instanceof Infantry) {
      return INFANTRY_TARGET_FACTORS[level - 1];
    }
    return CAVALRY_TARGET_FACTORS[level - 1];
  }

  @Override
  public String toString() {
    return "Type : Infantry" + "\n" + super.toString();
  }

}
