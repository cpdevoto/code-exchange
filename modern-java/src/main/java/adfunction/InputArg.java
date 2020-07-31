package adfunction;

public class InputArg {
  private final String name;
  private final long ts;
  private final String value;

  public InputArg(String name, long ts, String value) {
    this.name = name;
    this.ts = ts;
    this.value = value;
  }

  String getName() {
    return name;
  }

  long getTs() {
    return ts;
  }

  String getValue() {
    return value;
  }

}
