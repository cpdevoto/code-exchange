package optional;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

public class WithOptionals {

  private static String getCarInsuranceName(Optional<Person> person, int minAge) {
    return person
        .filter(p -> p.getAge() >= minAge)
        .flatMap(Person::getCar)
        .flatMap(Car::getInsurance)
        .map(Insurance::getName)
        .orElse("Unknown");
  }

  private static Set<String> getCarInsuranceNames(List<Person> persons) {
    // return all insurance names, discarding any empty optionals
    return persons.stream()
        .map(Person::getCar)
        .map(optCar -> optCar.flatMap(Car::getInsurance))
        .map(optInsurance -> optInsurance.map(Insurance::getName))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toCollection(LinkedHashSet::new));
  }

  private static Insurance findCheapestInsurance(Person person, Car car) {
    return new Insurance("Cheapest Insurance");
  }

  private static Optional<Insurance> nullSafeFindCheapestInsurance1(Optional<Person> person,
      Optional<Car> car) {
    // This approach is bad because its coded just like the null-check code that Optionals are
    // trying to remove
    if (person.isPresent() && car.isPresent()) {
      return Optional.of(findCheapestInsurance(person.get(), car.get()));
    }
    return Optional.empty();
  }

  private static Optional<Insurance> nullSafeFindCheapestInsurance2(Optional<Person> person,
      Optional<Car> car) {
    return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
  }

  public static void main(String[] args) {
    List<Person> persons = Arrays.asList(
        new Person(16, null),
        new Person(24, new Car(null)),
        new Person(35, new Car(new Insurance("Geico"))),
        new Person(52, new Car(new Insurance("All State"))));

    System.out.println(getCarInsuranceName(Optional.empty(), 50));
    persons.stream()
        .map(Optional::ofNullable)
        .forEach(p -> System.out.println(getCarInsuranceName(p, 50)));

    System.out.println();
    System.out.println(getCarInsuranceNames(persons));
    System.out.println();

    System.out.println(
        nullSafeFindCheapestInsurance2(Optional.empty(), Optional.empty()));
    System.out.println(
        nullSafeFindCheapestInsurance2(Optional.of(persons.get(2)), persons.get(2).getCar()));
    System.out.println();

    // The following two sets of statements are equivalent
    List<Optional<Insurance>> insurances = Arrays.asList(
        Optional.empty(),
        Optional.of(new Insurance("All State")),
        Optional.of(new Insurance("Geico")));

    // THE UGLY WAY
    System.out.println("THE UGLY WAY:");
    IntStream.range(0, insurances.size())
        .boxed()
        .map(i -> {
          Optional<Insurance> optInsurance = insurances.get(i);
          if (optInsurance.isPresent() && !optInsurance.get().getName().equals("All State")) {
            return (i + 1) + ". ok";
          }
          return null;
        })
        .filter(s -> s != null)
        .forEach(System.out::println);

    // THE CLEAN WAY
    System.out.println("THE CLEAN WAY:");
    IntStream.range(0, insurances.size())
        .boxed()
        .map(i -> {
          return insurances.get(i)
              .filter(ins -> !ins.getName().equals("All State"))
              .map(name -> (i + 1) + ". ok")
              .orElse(null);
        })
        .filter(s -> s != null)
        .forEach(System.out::println);
  }


  private static class Person {
    private final int age;
    private final Optional<Car> car;

    public Person(int age, Car car) {
      this.age = age;
      this.car = Optional.ofNullable(car);
    }

    public int getAge() {
      return age;
    }

    public Optional<Car> getCar() {
      return car;
    }
  }

  private static class Car {
    private final Optional<Insurance> insurance;

    public Car(Insurance insurance) {
      this.insurance = Optional.ofNullable(insurance);
    }

    public Optional<Insurance> getInsurance() {
      return insurance;
    }
  }

  private static class Insurance {
    private final String name;

    public Insurance(String name) {
      this.name = requireNonNull(name, "name cannot be null");
    }

    public String getName() {
      return name;
    }

  }

}
