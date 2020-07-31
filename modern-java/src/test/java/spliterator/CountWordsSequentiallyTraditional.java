package spliterator;

public class CountWordsSequentiallyTraditional {

  public static int countWords(String s) {
    int counter = 0;
    boolean lastCharWasSpace = true;
    for (char c : s.toCharArray()) {
      if (Character.isWhitespace(c)) {
        lastCharWasSpace = true;
      } else {
        if (lastCharWasSpace) {
          counter++;
        }
        lastCharWasSpace = false;
      }
    }
    return counter;
  }

  public static void main(String[] args) {
    String s = " Nel   mezzo del cammin  di nostra  vita " +
        "mi  ritrovai in una  selva oscura" +
        " ché la  dritta via era   smarrita ";

    System.out.println("Found " + countWords(s) + " words");
  }

}
