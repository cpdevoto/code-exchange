package scratch;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class Main {

  public static void main(String[] args) {
    // Q. Why is it possible to use File::isHidden here?
    // A. The FileFilter class has a single method that accepts a File object and returns a boolean
    // You can substitute and method with a similar signature, or a method on the File object itself
    // that accepts no arguments and returns a boolean
    File[] hiddenFiles = new File(".").listFiles(File::isHidden);
    Arrays.stream(hiddenFiles).forEach(System.out::println);

    List<Apple> inventory = new ArrayList<>();
    inventory.add(new Apple(Color.GREEN, 120));
    inventory.add(new Apple(Color.RED, 120));

    // What can we pass as a formatter arg to prettyPrintApple?

    // 1. A method on the Apple object itself that takes no params and returns a string
    prettyPrint(inventory, Apple::toString);

    // 2. A static method on the Apple class that takes an apple and returns a string
    prettyPrint(inventory, Apple::format);

    // 3. A lambda that takes an apple and returns a string
    prettyPrint(inventory, a -> "An apple of " + a.getWeight() + "g");

    // 4. A static method on a class other than Apple
    // that takes an apple and returns a string
    prettyPrint(inventory, Main::format);

    // 5. A method on an object
    // that takes an apple and returns a string (NOTE: the object could be 'this' if it has a helper
    // utility method)
    Formatter formatter = new Formatter();
    prettyPrint(inventory, formatter::format);

    // NOTE: An interface is still a functional interface if it declares many default
    // methods, as long as there is only one abstract method!


    // EXPLICITLY CASTING LAMBDAS
    // The following assignment will not compile because Object is not a functional
    // interface, so the type of the lambda cannot be deduced from it's context:
    //
    // Object o = () -> System.out.println("Tricky example");
    //
    // To solve this problem we can explictly cast the lambda to a functional interface
    // with a matching type signature before assigning it.
    //
    // You can also use casting to
    // disambiguate the type of a lambda when passing it as a parameter to one of several
    // overloaded methods that accept different functional interfaces which have the same
    // type signature.

    Object o = (Runnable) () -> System.out.println("Tricky example");
    Arrays.stream(o.getClass().getInterfaces()).forEach(System.out::println); // Should show it's a
                                                                              // Runnable!

    // TYPE SIGNATURES:

    // 1. STATIC METHOD OF A TYPE:
    // ClassName::staticMethod maps to
    // a. If the static method takes args: (args) -> ClassName.staticMethod(args)
    // b. If the static method takes no args: () -> ClassName.staticMethod()

    // The Main::format method maps to (Apple) -> String
    Function<Apple, String> f1 = Main::format; // equivalent to (apple) -> Main.format(apple);
    ToIntFunction<String> if0 = Integer::parseInt; // equivalent to (s) -> Integer.parseInt(s);

    // 2. INSTANCE METHOD OF A TYPE:
    // ClassName::instanceMethod maps to
    // a. If the instance method takes no args: (instance) -> instance.instanceMethod()
    // b. If the instance method takes args: (instance, args) -> instance.instanceMethod(args)

    // The Apple::toString method maps to a type signature of (Apple) -> String
    Function<Apple, String> f2 = Apple::toString; // equivalent to (apple) -> apple.toString()

    // The Apple::isColor method maps to a type signature of (Apple, Color) -> boolean
    BiPredicate<Apple, Color> p = Apple::isColor; // equivalent to (apple, color) ->
                                                  // apple.isColor(color)

    // The String::compareToIgnoreCase method maps to a type signature of (String, String) ->
    // boolean
    Comparator<String> c = String::compareToIgnoreCase; // This is equivalent to (s1, s2) ->
                                                        // s1.compareToIgnoreCase(s2);

    // 3. INSTANCE METHOD OF AN EXISTING OBJECT:
    // obj::instanceMethod maps to
    // a. If the instance method takes no args: () -> obj.instanceMethod()
    // b. If the instance method takes args: (args) -> obj.instanceMethod(args)

    // The System.out::println method maps to (String) -> {}
    Consumer<String> c2 = System.out::println; // equivalent to (s) -> System.out.println(s)

    Apple a = new Apple(Color.GREEN, 170);
    // The a::isColor method maps to (Color) -> boolean
    Predicate<Color> p2 = a::isColor; // equivalent to (color) -> a.isColor(color)

    // 4. CONSTRUCTOR METHOD OF A TYPE:
    // ClassName::new maps to
    // a. If the constructor takes no args: () -> new ClassName()
    // b. If the constructor takes args: (args) -> new ClassName(args)

    BiFunction<Color, Double, Apple> bf = Apple::new; // equivalent to (color, weight) -> new
                                                      // Apple(color, weight)

    Supplier<Formatter> s = Formatter::new; // equivalent to () -> new Formatter
  }

  private static <T> void prettyPrint(List<T> list, Function<T, String> formatter) {
    for (T object : list) {
      String output = formatter.apply(object);
      System.out.println(output);
    }
  }

  private static String format(Apple apple) {
    return "A " + apple.getColor().toString().toLowerCase() + " apple";
  }


  private static enum Color {
    RED, GREEN
  }

  private static class Apple {
    private final Color color;
    private final double weight;

    public static String format(Apple apple) {
      return "A " + (apple.color == Color.RED ? "juicy" : "delicious") + " apple";
    }

    public Apple(Color color, double weight) {
      super();
      this.color = color;
      this.weight = weight;
    }

    public Color getColor() {
      return color;
    }

    public double getWeight() {
      return weight;
    }

    public boolean isColor(Color color) {
      return this.color == color;
    }

    @Override
    public String toString() {
      return "Apple [color=" + color + ", weight=" + weight + "]";
    }
  }

  private static class Formatter {
    public String format(Apple apple) {
      if (apple.getColor() == Color.RED) {
        return "A fiery apple";
      }
      return "A jealous apple";
    }
  }
}

