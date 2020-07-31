package visitor;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

public class SearchExpression implements Visitable<Expression> {
  private Expression exp;

  SearchExpression(Expression exp) {
    this.exp = exp;
  }

  public boolean find(String s) {
    requireNonNull(s, "s cannot be null");
    return exp.find(s);
  }

  @Override
  public void accept(Consumer<Expression> consumer) {
    exp.accept(consumer);
  }

}
