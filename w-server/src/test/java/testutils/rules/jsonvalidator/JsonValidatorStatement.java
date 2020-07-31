package testutils.rules.jsonvalidator;

import org.junit.runners.model.Statement;

class JsonValidatorStatement extends Statement {
  private final Statement base;

  JsonValidatorStatement(Statement base) {
    this.base = base;
  }

  @Override
  public void evaluate() throws Throwable {
    base.evaluate();
  }

}
