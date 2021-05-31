package engine;

public class Distance {

  private String from; // The name of the city that the army will begin moving from, READ ONLY
  private String to; // The name of the city that the army will move to, READ ONLY
  private int distance; // The distance between the two cities, READ ONLY

  public String getFrom() {
    return this.from;
  }

  public String getTo() {
    return this.to;
  }

  public int getDistance() {
    return this.distance;
  }

  public Distance(String from, String to, int distance) {
    this.from = from;
    this.to = to;
    this.distance = distance;
  }
  
}
