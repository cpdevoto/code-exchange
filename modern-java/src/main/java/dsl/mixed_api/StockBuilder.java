package dsl.mixed_api;

public class StockBuilder {
  private final TradeBuilder tradeBuilder;
  private final Stock.Builder builder = Stock.builder();

  StockBuilder(TradeBuilder tradeBuilder, String symbol) {
    this.tradeBuilder = tradeBuilder;
    builder.withSymbol(symbol);
  }

  public TradeBuilder on(String market) {
    builder.withMarket(market);
    tradeBuilder.stock(builder.build());
    return tradeBuilder;
  }

}
