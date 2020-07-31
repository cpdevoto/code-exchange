package factory;

class Cat extends AbstractAnimal {

  Cat(String name) {
    super(name);
  }

  @Override
  public String talk() {
    return "meow!";
  }

}
