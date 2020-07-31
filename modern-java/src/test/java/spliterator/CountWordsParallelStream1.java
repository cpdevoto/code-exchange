package spliterator;

import java.util.stream.IntStream;

public class CountWordsParallelStream1 {

  public static int countWords(String s) {
    WordCounter wordCounter = IntStream.range(0, s.length())
        .parallel()
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

    // PROBLEM!!
    // Produces incorrect results, because if the stream of characters
    // gets split in the middle of a word, the word gets double-counted!
    // To prevent this issue, we need to build a custom spliterator that
    // will never split the stream in the middle of a word.
    System.out.println("Found " + countWords(s) + " words");
  }

}
