package dsl.mixed_api;

import static java.util.Objects.requireNonNull;

import java.util.function.DoubleUnaryOperator;

public class TaxCollector {
  private DoubleUnaryOperator taxFunction = value -> value;

  public TaxCollector with(DoubleUnaryOperator taxFunction) {
    requireNonNull(taxFunction, "taxFunction cannot be null");
    this.taxFunction.andThen(taxFunction);
    return this;
  }

  public double calculate(Order order) {
    requireNonNull(order, "order cannot be null");
    return taxFunction.applyAsDouble(order.getValue());
  }
}
