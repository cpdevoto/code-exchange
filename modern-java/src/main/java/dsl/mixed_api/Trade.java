package dsl.mixed_api;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

public class Trade {
  public enum Type {
    BUY, SELL
  }

  private Type type;
  private Stock stock;
  private int quantity;
  private double price;

  static Builder builder() {
    return new Builder();
  }

  private Trade(Builder builder) {
    this.type = builder.type;
    this.stock = builder.stock;
    this.quantity = builder.quantity;
    this.price = builder.price;
  }

  public Type getType() {
    return type;
  }

  public Stock getStock() {
    return stock;
  }

  public int getQuantity() {
    return quantity;
  }

  public double getPrice() {
    return price;
  }

  public double getValue() {
    return quantity * price;
  }

  @Override
  public String toString() {
    return "Trade [type=" + type + ", stock=" + stock + ", quantity=" + quantity + ", price="
        + price + "]";
  }

  static class Builder {
    private Type type;
    private Stock stock;
    private Integer quantity;
    private Double price;

    private Builder() {}

    Builder with(Consumer<Builder> consumer) {
      requireNonNull(consumer, "consumer cannot be null");
      consumer.accept(this);
      return this;
    }

    Builder withType(Type type) {
      this.type = requireNonNull(type, "type cannot be null");
      return this;
    }

    Builder withStock(Stock stock) {
      this.stock = requireNonNull(stock, "stock cannot be null");
      return this;
    }

    Builder withQuantity(int quantity) {
      checkArgument(quantity > 0, "quantity must be greater than zero");
      this.quantity = quantity;
      return this;
    }

    Builder withPrice(double price) {
      checkArgument(price > 0, "price must be greater than zero");
      this.price = price;
      return this;
    }

    Trade build() {
      requireNonNull(type, "type cannot be null");
      requireNonNull(stock, "stock cannot be null");
      requireNonNull(quantity, "quantity cannot be null");
      requireNonNull(price, "price cannot be null");
      return new Trade(this);

    }
  }
}
