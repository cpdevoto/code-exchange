package visitor;

class StringExpression implements Expression {
  private String value;

  StringExpression(String value) {
    this.value = value;
  }


  @Override
  public boolean find(String s) {
    return s.contains(value);
  }

  String getValue() {
    return value;
  }
}
