package dsl.fluent_api;

public class TradeBuilder {
  private final OrderBuilder orderBuilder;
  private final Trade.Builder tradeBuilder = Trade.builder();

  TradeBuilder(OrderBuilder orderBuilder, Trade.Type type, int quantity) {
    this.orderBuilder = orderBuilder;
    tradeBuilder.withType(type)
        .withQuantity(quantity);
  }

  public StockBuilder stock(String symbol) {
    return new StockBuilder(orderBuilder, tradeBuilder, symbol);
  }

}
