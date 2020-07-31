package adfunction;

public class ConstArg {
  private final String name;
  private final String value;

  public ConstArg(String name, String value) {
    this.name = name;
    this.value = value;
  }

  String getName() {
    return name;
  }

  String getValue() {
    return value;
  }

}
