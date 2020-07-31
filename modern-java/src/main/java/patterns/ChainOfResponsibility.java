package patterns;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ChainOfResponsibility {

  public static void main(String[] args) {
    UnaryOperator<String> headerProcessing = s -> "From Carlos: " + s;
    UnaryOperator<String> spellCheckerProcessing = s -> s.replaceAll("labda", "lambda");

    Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);

    String result = pipeline.apply("Aren't labdas really sexy?!!");

    System.out.println(result);
  }

}
