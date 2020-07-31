package visitor;

import java.util.function.Consumer;

interface Expression extends Visitable<Expression> {

  boolean find(String s);

  default void accept(Consumer<Expression> consumer) {
    consumer.accept(this);
  }

}
