package visitor;

import java.util.function.Consumer;

class OrExpression implements Expression {
  private Expression left;
  private Expression right;

  OrExpression(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public boolean find(String s) {
    return left.find(s) || right.find(s);
  }

  @Override
  public void accept(Consumer<Expression> consumer) {
    consumer.accept(this);
    left.accept(consumer);
    right.accept(consumer);
  }

}
