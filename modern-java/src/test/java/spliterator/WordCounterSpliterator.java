package spliterator;

import static java.util.Objects.requireNonNull;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {
  private final String string;
  private int idx = 0;

  public WordCounterSpliterator(String string) {
    this.string = requireNonNull(string, "string cannot be null");
  }

  @Override
  public boolean tryAdvance(Consumer<? super Character> action) {
    action.accept(string.charAt(idx++));
    return idx < string.length();
  }

  @Override
  public Spliterator<Character> trySplit() {
    int currentSize = string.length() - idx;
    if (currentSize < 10) {
      // We won't allow a split if there are less than 10 chars left in the string,
      // thereby terminating the recursive divide-and-conquer algorithm used for splitting streams
      return null;
    }
    // Find a split position that does not fall in the middle of a word, starting in the middle of
    // the remaining string
    for (int splitPos = idx + currentSize / 2; splitPos < string.length(); splitPos++) {
      if (Character.isWhitespace(string.charAt(splitPos))) {
        return split(splitPos);
      }
    }

    // We could not find a split searching forward from the middle, so try searching backward from
    // the middle
    for (int splitPos = idx + currentSize / 2; splitPos > idx; splitPos--) {
      if (Character.isWhitespace(string.charAt(splitPos))) {
        return split(splitPos);
      }
    }

    // There were no whitespace characters, so don't do a split!
    return null;
  }

  private Spliterator<Character> split(int splitPos) {
    // Found a good split position; create a new spliterator encapsulating the substring between
    // idx and the splitPos
    Spliterator<Character> spliterator =
        new WordCounterSpliterator(string.substring(idx, splitPos));
    // TODO: Remove the following block before productionizing!!
    synchronized (WordCounterSpliterator.class) {
      System.out.println("SPLIT >>>>>>");
      System.out.println("SOURCE: \"" + string.substring(idx) + "\"");
      System.out.println("SPLIT1: \"" + string.substring(idx, splitPos) + "\"");
      System.out.println("SPLIT2: \"" + string.substring(splitPos) + "\"");
      System.out.println();
    }
    // Set idx to the splitPos
    idx = splitPos;
    return spliterator;
  }

  @Override
  public long estimateSize() {
    return string.length() - idx;
  }

  @Override
  public int characteristics() {
    return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
  }

}
