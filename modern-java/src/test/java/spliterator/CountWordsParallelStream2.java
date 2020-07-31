package spliterator;

import java.util.Spliterator;
import java.util.stream.StreamSupport;

public class CountWordsParallelStream2 {

  public static int countWords(String s) {
    Spliterator<Character> spliterator = new WordCounterSpliterator(s);

    WordCounter wordCounter = StreamSupport.stream(spliterator, true)
        .reduce(
            new WordCounter(),
            WordCounter::accumulate,
            WordCounter::combine);
    return wordCounter.getCounter();
  }

  public static void main(String[] args) {
    String s = " Nel   mezzo del cammin  di nostra  vita " +
        "mi  ritrovai in una  selva oscura" +
        " ché la  dritta via era   smarrita ";

    System.out.println("Found " + countWords(s) + " words");
    System.out.println();

    s = "  StringwithnospacesStringwithnospaces";

    System.out.println("Found " + countWords(s) + " words");
  }

}
