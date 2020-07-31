package visitor;

import java.util.function.Consumer;

public interface Visitable<T> {

  public void accept(Consumer<T> consumer);

}
