package dsl.function_sequencing_api;

public class StockBuilder {
  private Stock.Builder builder = Stock.builder();

  StockBuilder() {}

  public void symbol(String symbol) {
    builder.withSymbol(symbol);
  }

  public void market(String market) {
    builder.withMarket(market);
  }

  Stock build() {
    return builder.build();
  }

}
