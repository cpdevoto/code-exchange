package dsl.function_sequencing_api;

import java.util.function.Consumer;

public class TradeBuilder {
  private final Trade.Builder builder = Trade.builder();

  TradeBuilder(Trade.Type type) {
    builder.withType(type);
  }

  public void quantity(int quantity) {
    builder.withQuantity(quantity);
  }

  public void price(double price) {
    builder.withPrice(price);
  }

  public void stock(Consumer<StockBuilder> consumer) {
    StockBuilder stockBuilder = new StockBuilder();
    consumer.accept(stockBuilder);
    builder.withStock(stockBuilder.build());
  }

  Trade build() {
    return builder.build();
  }

}
