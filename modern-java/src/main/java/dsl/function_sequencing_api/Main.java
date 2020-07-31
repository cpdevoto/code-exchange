package dsl.function_sequencing_api;

import static dsl.function_sequencing_api.OrderBuilder.order;

public class Main {

  public static void main(String[] args) {
    // FUNCTION SEQUENCING API (USES LAMBDAS)

    // @formatter:off

    // ADVANTAGE: Combines two positive characterics from the method chaining and nested function
    // DSL styles:
    //    1. Like the method chaining pattern, it allows the user to define the trading order in a fluent way.
    //    2. Similar to the nested function pattern, it preserves the hierarchy structure of our domain objects
    //       in the nesting level of the different lambda expressions.
    
    // DISADVANTAGE: Requires a lot of setup code (i.e. three builders), and using the DSL itself is affected
    // by the noise of the lambda expression syntax.

    Order order = order(o -> {
      o.forCustomer("BigBank");
      o.buy(t -> {
        t.quantity(80);
        t.price(125.00);
        t.stock(s -> {
          s.symbol("IBM");
          s.market("NYSE");
        });
      });
      o.sell(t -> {
        t.quantity(50);
        t.price(375.00);
        t.stock(s -> {
          s.symbol("GOOGLE");
          s.market("NASDAQ");
        });
      });
    });
    // @formatter:on

    System.out.println(order);
  }

}
