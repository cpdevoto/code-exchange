package scratch;

import static factory.Animals.Type.CAT;
import static factory.Animals.Type.DOG;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import factory.Animal;
import factory.Animals;

public class Main2 {

  // This shows an example of how we can build a map of constructor references keyed by type
  // in order to implement a factory method (i.e. Animals.create(Type, String)).
  public static void main(String[] args) {
    List<Animal> animals = Stream.of(
        Animals.create(DOG, "Barney"),
        Animals.create(CAT, "Rutherford"))
        .collect(toList());

    animals.forEach(Main2::print);

  }

  private static void print(Animal a) {
    System.out.println(a.getName() + ": " + a.talk());
  }

}
