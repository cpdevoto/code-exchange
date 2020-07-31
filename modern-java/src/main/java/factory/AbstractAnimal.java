package factory;

import static java.util.Objects.requireNonNull;

abstract class AbstractAnimal implements Animal {

  private final String name;

  protected AbstractAnimal(String name) {
    this.name = requireNonNull(name, "name cannot be null");
  }

  public final String getName() {
    return name;
  }
}
