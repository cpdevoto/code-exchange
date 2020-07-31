package dsl.mixed_api;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class OrderBuilder {
  // This class follows the nested function approach by exposing three static methods,
  // The second two are typically invoked in a nested way as arguments to the forCustomer method

  // The buy and sell methods are implemented using the function sequencing style, while the
  // TradeBuilder and StockBuilder classes follow the fluent API (i.e. method chaining style)


  public static Order forCustomer(String customer, TradeBuilder... tradeBuilders) {
    Order.Builder orderBuilder = Order.builder();
    orderBuilder.withCustomer(customer);
    Stream.of(tradeBuilders).forEach(b -> orderBuilder.withTrade(b.build()));
    return orderBuilder.build();
  }

  public static TradeBuilder buy(Consumer<TradeBuilder> consumer) {
    return buildTrade(consumer, Trade.Type.BUY);
  }

  public static TradeBuilder sell(Consumer<TradeBuilder> consumer) {
    return buildTrade(consumer, Trade.Type.SELL);
  }

  private static TradeBuilder buildTrade(Consumer<TradeBuilder> consumer, Trade.Type type) {
    TradeBuilder tradeBuilder = new TradeBuilder(type);
    consumer.accept(tradeBuilder);
    return tradeBuilder;
  }
}
