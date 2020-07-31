package dsl.nested_function_api;

import java.util.stream.Stream;

public class OrderBuilder {

  public static Order order(String customer, Trade... trades) {
    return Order.builder()
        .withCustomer(customer)
        .with(builder -> {
          Stream.of(trades).forEach(trade -> builder.withTrade(trade));
        })
        .build();
  }

  public static Trade buy(int quantity, Stock stock, double price) {
    return buildTrade(quantity, stock, price, Trade.Type.BUY);
  }

  public static Trade sell(int quantity, Stock stock, double price) {
    return buildTrade(quantity, stock, price, Trade.Type.SELL);
  }

  public static Stock stock(String symbol, String market) {
    return Stock.builder()
        .withSymbol(symbol)
        .withMarket(market)
        .build();
  }

  public static String on(String market) {
    return market;
  }

  public static double at(double price) {
    return price;
  }

  private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type type) {
    return Trade.builder()
        .withQuantity(quantity)
        .withStock(stock)
        .withPrice(price)
        .withType(type)
        .build();
  }

}
