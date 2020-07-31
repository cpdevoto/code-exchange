package dsl.fluent_api;

import static dsl.fluent_api.OrderBuilder.forCustomer;

public class Main {

  public static void main(String[] args) {
    // FLUENT API W/ METHOD CHAINING

    // ADVANTAGE 1: The choice of having multiple builder classes--and in particular, two different
    // trade builders--is made to force the user of this DSL to call the methods of its fluent API
    // in a predetermined sequence, ensuring that a trade has been configured correctly before the
    // user start creating the next one.
    //
    // ADVANTAGE 2: The parameters used to set an order up are scoped inside the builder, which
    // minimizes the use of static methods and allows the names of the methods to act as named
    // arguments, thus further improving the readability of the DSL.
    //
    // DISADVANTAGE 1: The main issue with a method chaining approach is that there is a lot of
    // verbosity required to implement the builders. A lot of glue code is necessary to mix the
    // top-level builders with the lower-level ones.
    //
    // DISADVANTAGE 2: You have no way to enforce the indentation convention used to underline the
    // nesting hierarchy of the objects in your domain.


    // @formatter:off
    Order order = forCustomer("BigBank")
                    .buy(80)
                    .stock("IBM")
                      .on("NYSE")
                    .at(125.00)
                    .sell(50)
                    .stock("GOOGLE")
                      .on("NASDAQ")
                    .at(375.00)
                  .end();
    // @formatter:on

    System.out.println(order);
  }

}
