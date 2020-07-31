package dsl.mixed_api;

public class TradeBuilder {
  private final Trade.Builder builder = Trade.builder();

  TradeBuilder(Trade.Type type) {
    builder.withType(type);
  }

  public TradeBuilder quantity(int quantity) {
    builder.withQuantity(quantity);
    return this;
  }

  public TradeBuilder at(double price) {
    builder.withPrice(price);
    return this;
  }

  public StockBuilder stock(String symbol) {
    return new StockBuilder(this, symbol);
  }

  void stock(Stock stock) {
    builder.withStock(stock);
  }

  Trade build() {
    return builder.build();
  }

}
