package factory;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Animals {
  public enum Type {
    CAT, DOG
  };

  private static Map<Type, Function<String, Animal>> FACTORY_METHODS;

  static {
    Map<Type, Function<String, Animal>> factoryMethods = new HashMap<>();
    factoryMethods.put(Type.CAT, Cat::new);
    factoryMethods.put(Type.DOG, Dog::new);
    FACTORY_METHODS = Collections.unmodifiableMap(factoryMethods);
  }

  public static Animal create(Type type, String name) {
    requireNonNull(type, "type cannot be null");
    requireNonNull(name, "name cannot be null");
    return FACTORY_METHODS.get(type).apply(name);
  }

  private Animals() {}
}
