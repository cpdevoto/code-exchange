package functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class PracticalCurrying {

  public static void main(String[] args) {
    List<CreditCard> cards = Arrays.asList(
        new CreditCard(2.0),
        new CreditCard(4.0));

    // Without currying
    double premium = cards.stream()
        .mapToDouble(card -> CreditCardUtils.getPremium1(cards.size(), card))
        .sum();

    System.out.println(premium);

    // With currying
    ToDoubleFunction<CreditCard> getPremium = CreditCardUtils.getPremium2(cards.size());

    premium = cards.stream()
        .mapToDouble(getPremium)
        .sum();

    System.out.println(premium);

  }


  private static class CreditCard {
    double balance;

    private CreditCard(double balance) {
      this.balance = balance;
    }
  }

  private static class CreditCardUtils {
    private static double getPremium1(int numCards, CreditCard card) {
      return numCards * card.balance;
    }

    private static ToDoubleFunction<CreditCard> getPremium2(int numCards) {
      return (card) -> numCards * card.balance;
    }

  }

}
