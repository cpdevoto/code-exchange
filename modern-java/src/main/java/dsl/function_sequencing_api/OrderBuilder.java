package dsl.function_sequencing_api;

import java.util.function.Consumer;

public class OrderBuilder {
  private final Order.Builder builder = Order.builder();

  public static Order order(Consumer<OrderBuilder> consumer) {
    OrderBuilder orderBuilder = new OrderBuilder();
    consumer.accept(orderBuilder);
    return orderBuilder.build();
  }

  private OrderBuilder() {}

  public void forCustomer(String customer) {
    builder.withCustomer(customer);
  }

  public void buy(Consumer<TradeBuilder> consumer) {
    trade(consumer, Trade.Type.BUY);
  }

  public void sell(Consumer<TradeBuilder> consumer) {
    trade(consumer, Trade.Type.BUY);
  }

  private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
    TradeBuilder tradeBuilder = new TradeBuilder(type);
    consumer.accept(tradeBuilder);
    this.builder.withTrade(tradeBuilder.build());
  }

  private Order build() {
    return builder.build();
  }

}
