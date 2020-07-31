package visitor;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Visitor<T> {

  private final Visitable<T> visitable;
  private Predicate<T> predicate;

  public static <T> Visitor<T> subject(Visitable<T> visitable) {
    return new Visitor<>(visitable);
  }

  private Visitor(Visitable<T> visitable) {
    requireNonNull(visitable, "visitable cannot be null");
    this.visitable = visitable;
  }

  public Visitor<T> filter(Predicate<T> predicate) {
    requireNonNull(predicate, "predicate cannot be null");
    this.predicate = predicate;
    return this;
  }

  public Visitor<T> classFilter(Class<? extends T> clazz) {
    requireNonNull(clazz, "clazz cannot be null");
    this.predicate = (obj) -> clazz.isInstance(obj);
    return this;
  }

  public void visit(Consumer<T> consumer) {
    requireNonNull(consumer, "consumer cannot be null");
    Consumer<T> c;
    if (predicate != null) {
      c = (obj) -> {
        if (predicate.test(obj)) {
          consumer.accept(obj);
        }
      };
    } else {
      c = consumer;
    }
    visitable.accept(c);
  }

}
