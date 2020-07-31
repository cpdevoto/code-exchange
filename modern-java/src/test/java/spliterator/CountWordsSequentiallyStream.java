package spliterator;

import java.util.stream.IntStream;

public class CountWordsSequentiallyStream {

  public static int countWords(String s) {
    WordCounter wordCounter = IntStream.range(0, s.length())
        .mapToObj(s::charAt)
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
  }

}
