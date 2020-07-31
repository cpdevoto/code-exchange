package dsl.mixed_api;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

public class Stock {
  private final String symbol;
  private final String market;

  static Builder builder() {
    return new Builder();
  }

  private Stock(Builder builder) {
    this.symbol = builder.symbol;
    this.market = builder.market;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getMarket() {
    return market;
  }

  @Override
  public String toString() {
    return "Stock [symbol=" + symbol + ", market=" + market + "]";
  }

  static class Builder {
    private String symbol;
    private String market;

    private Builder() {}

    Builder with(Consumer<Builder> consumer) {
      requireNonNull(consumer, "consumer cannot be null");
      consumer.accept(this);
      return this;
    }

    Builder withSymbol(String symbol) {
      this.symbol = requireNonNull(symbol, "symbol cannot be null");
      return this;
    }

    Builder withMarket(String market) {
      this.market = requireNonNull(market, "market cannot be null");
      return this;
    }

    Stock build() {
      requireNonNull(symbol, "symbol cannot be null");
      requireNonNull(market, "market cannot be null");
      return new Stock(this);
    }

  }

}
