package dsl.nested_function_api;

import static dsl.nested_function_api.OrderBuilder.at;
import static dsl.nested_function_api.OrderBuilder.buy;
import static dsl.nested_function_api.OrderBuilder.on;
import static dsl.nested_function_api.OrderBuilder.order;
import static dsl.nested_function_api.OrderBuilder.sell;
import static dsl.nested_function_api.OrderBuilder.stock;

public class Main {

  public static void main(String[] args) {
    // NESTED FUNCTION API

    // ADVANTAGE 1: The code required to implement this DSL style is far more compact that the code
    // required to implement the method chaining API

    // ADVANTAGE 2: Unlike the method chaining approach of the fluent API, the hierarchy structure
    // of your domain objects is visible by the way in which the different functions are nested.

    // DISADVANTAGE 1: This DSL requires a lot of parentheses.

    // DISADVANTAGE 2: The list of arguments that have to be passed to the static methods is rigidly
    // predetermined, so if the objects of your domain have some optional fields, you need to
    // implement different overloaded versions of those methods which allow you to omit the missing
    // parameters.

    // DISADVANTAGE 3: The meanings of the different parameters are defined by their positions
    // rather than their names, though you can mitigate this last problem by introducing a few dummy
    // methods (as we did with the at() and on() methods).

    // @formatter:off
    Order order = order("BigBank",
                    buy(80,
                      stock("IBM", on("NYSE")),
                      at(125.00)),
                    sell(50,
                        stock("GOOGLE", on("NASDAQ")),
                        at(375.00))
                  );
    // @formatter:on

    System.out.println(order);
  }

}
