package dsl.no_dsl;

import java.util.ArrayList;
import java.util.List;

public class Order {
  private String customer;
  private List<Trade> trades = new ArrayList<>();

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public void addTrade(Trade trade) {
    trades.add(trade);
  }

  public double getValue() {
    return trades.stream().mapToDouble(Trade::getValue).sum();
  }

  @Override
  public String toString() {
    return "Order [customer=" + customer + ", trades=" + trades + "]";
  }


}
