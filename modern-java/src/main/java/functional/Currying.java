package functional;

import java.util.function.DoubleUnaryOperator;

public class Currying {

  // This is a factory method that produces functions with different values for adjuster,
  // factor, and baseLine. This allows clients to make function calls which only require
  // a single parameter as opposed to all four parameters. This approach is called currying.

  // Currying is a technique in which a function f of two arguments (such as x and y) is instead
  // viewed as a function g of one argument that returns a function also of one argument.

  private static DoubleUnaryOperator unitConverter(double adjuster, double factor,
      double baseline) {
    return (double x) -> (x + adjuster) * factor + baseline;
  }

  private static DoubleUnaryOperator convertCToF = unitConverter(0, 9.0 / 5, 32);
  private static DoubleUnaryOperator convertFToC = unitConverter(-32, 5.0 / 9, 0);
  private static DoubleUnaryOperator convertKToM = unitConverter(0, 0.621371, 0);
  private static DoubleUnaryOperator convertMToK = unitConverter(0, 1.60934, 0);

  public static void main(String[] args) {
    double fahrenheit = convertCToF.applyAsDouble(0);
    double celsius = convertFToC.applyAsDouble(32);
    double miles = convertKToM.applyAsDouble(1);
    double kilometers = convertMToK.applyAsDouble(1);

    System.out.println(fahrenheit);
    System.out.println(celsius);
    System.out.println(miles);
    System.out.println(kilometers);
  }



}
