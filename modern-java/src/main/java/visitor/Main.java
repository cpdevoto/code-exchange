package visitor;

import static java.util.stream.Collectors.joining;

import java.util.List;

import com.google.common.collect.Lists;

public class Main {

  // @formatter:off
  private static final SearchExpression exp = new SearchExpression(
      new AndExpression(
          new StringExpression("fox"),
          new NotExpression(
              new StringExpression("brown")
          )
      )
  );
  // @formatter:on


  public static void main(String[] args) {

    test("The black fox");
    test("The brown fox");
    test("The brown cat");

    List<String> expressionLiterals = Lists.newArrayList();

    Visitor.subject(exp)
        .classFilter(StringExpression.class)
        .visit(expr -> expressionLiterals.add(StringExpression.class.cast(expr).getValue()));

    System.out.println(expressionLiterals.stream().collect(joining(", ")));

  }

  private static void test(String s) {
    System.out.printf("Testing expression [%s]: %s%n", s, String.valueOf(exp.find(s)));
  }


}
