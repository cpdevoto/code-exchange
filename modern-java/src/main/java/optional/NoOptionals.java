package optional;

import static java.util.Objects.requireNonNull;

public class NoOptionals {

  private static String getCarInsuranceName(Person person) {
    if (person != null) {
      Car car = person.getCar();
      if (car != null) {
        Insurance insurance = car.getInsurance();
        if (insurance != null) {
          return insurance.getName();
        }
      }
    }
    return "Unknown";
  }

  public static void main(String[] args) {
    System.out.println(getCarInsuranceName(null));
    System.out.println(getCarInsuranceName(new Person(null)));
    System.out.println(getCarInsuranceName(new Person(new Car(null))));
    System.out.println(getCarInsuranceName(new Person(new Car(new Insurance("Geico")))));
  }


  private static class Person {
    private final Car car;

    public Person(Car car) {
      this.car = car;
    }

    public Car getCar() {
      return car;
    }
  }

  private static class Car {
    private final Insurance insurance;

    public Insurance getInsurance() {
      return insurance;
    }

    public Car(Insurance insurance) {
      this.insurance = insurance;
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
