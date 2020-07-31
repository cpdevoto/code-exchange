package dsl.mixed_api;

import static dsl.mixed_api.OrderBuilder.buy;
import static dsl.mixed_api.OrderBuilder.forCustomer;
import static dsl.mixed_api.OrderBuilder.sell;

public class Main {

  public static void main(String[] args) {
    // API THAT MIXES FEATURES OF OTHER 3 API STYLES

    // ADVANTAGE: Produces a readable DSL that combines the best features of the other 3 API styles.

    // MINOR DISADVANTAGE: The resulting DSL appears to be less uniform than one that uses a single
    // technique, so users of this DSL will probably need more time to learn it.

    // @formatter:off
    Order order = forCustomer("BigBank",
          buy(t -> t.quantity(80)
              .stock("IBM")
              .on("NYSE")
              .at(125.00)),
          sell(t -> t.quantity(50)
              .stock("GOOGLE")
              .on("NASDAQ")
              .at(375.00))
        );
    // @formatter:on

    System.out.println(order);

    System.out.println(new TaxCollector()
        .with(Tax::regional)
        .with(Tax::surcharge)
        .calculate(order));
  }

}
