package dsl.fluent_api;

public class TradeBuilderWithStock {
  private final OrderBuilder orderBuilder;
  private final Trade.Builder tradeBuilder;


  TradeBuilderWithStock(OrderBuilder orderBuilder, Trade.Builder tradeBuilder) {
    this.orderBuilder = orderBuilder;
    this.tradeBuilder = tradeBuilder;
  }

  public OrderBuilder at(double price) {
    tradeBuilder.withPrice(price);
    return orderBuilder.addTrade(tradeBuilder.build());
  }

}
