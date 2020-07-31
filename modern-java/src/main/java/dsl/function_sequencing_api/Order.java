package dsl.function_sequencing_api;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;

public class Order {
  private final String customer;
  private final List<Trade> trades;

  static Builder builder() {
    return new Builder();
  }

  private Order(Builder builder) {
    this.customer = builder.customer;
    this.trades = ImmutableList.copyOf(builder.trades);
  }

  public String getCustomer() {
    return customer;
  }

  public double getValue() {
    return trades.stream().mapToDouble(Trade::getValue).sum();
  }

  @Override
  public String toString() {
    return "Order [customer=" + customer + ", trades=" + trades + "]";
  }

  static class Builder {
    private String customer;
    private List<Trade> trades = new ArrayList<>();

    private Builder() {}

    Builder with(Consumer<Builder> consumer) {
      requireNonNull(consumer, "consumer cannot be null");
      consumer.accept(this);
      return this;
    }

    Builder withCustomer(String customer) {
      this.customer = requireNonNull(customer, "customer cannot be null");
      return this;
    }

    Builder withTrade(Trade trade) {
      requireNonNull(trade, "trade cannot be null");
      trades.add(trade);
      return this;
    }

    Order build() {
      requireNonNull(customer, "customer cannot be null");
      checkArgument(!trades.isEmpty(), "must include at least one trade");
      return new Order(this);
    }

  }

}
