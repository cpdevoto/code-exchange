package visitor;

import java.util.function.Consumer;

class NotExpression implements Expression {
  private Expression exp;

  NotExpression(Expression exp) {
    this.exp = exp;
  }

  @Override
  public boolean find(String s) {
    return !exp.find(s);
  }

  @Override
  public void accept(Consumer<Expression> consumer) {
    consumer.accept(this);
    exp.accept(consumer);
  }

}
