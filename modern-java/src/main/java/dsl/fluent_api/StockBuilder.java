package dsl.fluent_api;

public class StockBuilder {
  private final OrderBuilder orderBuilder;
  private final Trade.Builder tradeBuilder;
  private final Stock.Builder stockBuilder = Stock.builder();

  StockBuilder(OrderBuilder orderBuilder, Trade.Builder tradeBuilder, String symbol) {
    this.orderBuilder = orderBuilder;
    this.tradeBuilder = tradeBuilder;
    this.stockBuilder.withSymbol(symbol);
  }

  public TradeBuilderWithStock on(String market) {
    stockBuilder.withMarket(market);
    tradeBuilder.withStock(stockBuilder.build());
    return new TradeBuilderWithStock(orderBuilder, tradeBuilder);
  }

}
