package spliterator;

// Immutable accumulator for use with Stream.reduce
public class WordCounter {
  private final int counter;
  private final boolean lastCharWasSpace;

  public WordCounter() {
    this(0, true);
  }

  private WordCounter(int counter, boolean lastCharWasSpace) {
    this.counter = counter;
    this.lastCharWasSpace = lastCharWasSpace;
  }

  public WordCounter accumulate(Character c) {
    if (Character.isWhitespace(c)) {
      return lastCharWasSpace ? this : new WordCounter(counter, true);
    } else {
      return lastCharWasSpace ? new WordCounter(counter + 1, false) : this;
    }
  }

  public WordCounter combine(WordCounter wordCounter) {
    return new WordCounter(counter + wordCounter.counter, wordCounter.lastCharWasSpace);
  }

  public int getCounter() {
    return counter;
  }
}
