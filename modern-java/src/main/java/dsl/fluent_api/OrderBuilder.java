package dsl.fluent_api;

public class OrderBuilder {
  private final Order.Builder builder = Order.builder();

  public static OrderBuilder forCustomer(String customer) {
    return new OrderBuilder(customer);
  }

  private OrderBuilder(String customer) {
    builder.withCustomer(customer);
  }

  public TradeBuilder buy(int quantity) {
    return new TradeBuilder(this, Trade.Type.BUY, quantity);
  }

  public TradeBuilder sell(int quantity) {
    return new TradeBuilder(this, Trade.Type.SELL, quantity);
  }

  public Order end() {
    return builder.build();
  }

  OrderBuilder addTrade(Trade trade) {
    builder.withTrade(trade);
    return this;
  }


}
