package stream;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IterableReader implements Iterable<Character>, AutoCloseable {
  private final Reader reader;
  private boolean iterated = false;

  public static IterableReader iterable(Reader reader) {
    return new IterableReader(reader);
  }

  private IterableReader(Reader reader) {
    this.reader = requireNonNull(reader, "reader cannot be null");
  }

  public Stream<Character> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

  @Override
  public Iterator<Character> iterator() {
    if (iterated) {
      throw new IllegalStateException("cannot iterate more than once");
    }
    iterated = true;
    return new Iterator<Character>() {
      int token = nextToken();

      @Override
      public boolean hasNext() {
        return token != -1;
      }

      @Override
      public Character next() {
        if (token == -1) {
          throw new NoSuchElementException();
        }
        int lastToken = token;
        token = nextToken();
        return (char) lastToken;
      }

    };
  }


  @Override
  public void close() throws Exception {
    reader.close();
  }

  private int nextToken() {
    try {
      return reader.read();
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
  }

  public static void main(String[] args) throws Exception {
    try (IterableReader iterable = iterable(new StringReader("Carlos Devoto"))) {
      iterable.stream()
          .map(Character::toUpperCase)
          .forEach(System.out::print);
      System.out.println();
    }
  }


}
