package visitor;

import java.util.function.Consumer;

class AndExpression implements Expression {
  private Expression left;
  private Expression right;

  AndExpression(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public boolean find(String s) {
    return left.find(s) && right.find(s);
  }

  @Override
  public void accept(Consumer<Expression> consumer) {
    consumer.accept(this);
    left.accept(consumer);
    right.accept(consumer);
  }

}
