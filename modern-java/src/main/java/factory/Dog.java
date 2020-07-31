package factory;

class Dog extends AbstractAnimal {

  Dog(String name) {
    super(name);
  }

  @Override
  public String talk() {
    return "bark!";
  }

}
