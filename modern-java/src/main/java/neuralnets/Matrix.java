package neuralnets;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;

public class Matrix {
  private final int width;
  private final int height;
  private final List<List<Double>> values;

  public static Matrix of(Row... rows) {
    checkArgument(rows.length > 0, "A matrix must have at least one row");
    final int width = rows[0].values().size();
    if (rows.length > 1) {
      checkArgument(!IntStream.range(1, rows.length)
          .mapToObj(i -> rows[i])
          .filter(row -> row.values().size() != width)
          .findFirst()
          .isPresent(), "All rows must have the same number of elements");
    }
    return new Matrix(rows);
  }

  public static Row row(double... values) {
    return new Row(values);
  }

  private Matrix(Row... rows) {
    this.values = Arrays.stream(rows)
        .map(Row::values)
        .collect(collectingAndThen(toList(), ImmutableList::copyOf));
    this.height = rows.length;
    this.width = values.get(0).size();
  }

  public Matrix dotProduct(Matrix m) {
    checkArgument(this.width == m.height,
        "The specified matrix cannot be multiplied by the current matrix");
    int targetWidth = m.width;
    int targetHeight = this.height;
    double[][] values = new double[targetHeight][targetWidth];
    for (int row = 0; row < targetHeight; row++) {
      for (int column = 0; column < targetWidth; column++) {
        values[row][column] = dotProduct(m, row, column);
      }
    }
    List<Row> rows = IntStream.range(0, values.length)
        .mapToObj(row -> new Row(values[row]))
        .collect(Collectors.toList());
    return new Matrix(rows.toArray(new Row[rows.size()]));
  }

  public Matrix sigmoid() {
    double[][] values = new double[height][width];
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        values[row][column] = sigmoid(row, column);
      }
    }
    List<Row> rows = IntStream.range(0, values.length)
        .mapToObj(row -> new Row(values[row]))
        .collect(Collectors.toList());
    return new Matrix(rows.toArray(new Row[rows.size()]));

  }

  private double sigmoid(int row, int column) {
    return 1.0 / (1.0 + Math.pow(Math.E, -1 * values.get(row).get(column)));
  }

  private double dotProduct(Matrix m, int row, int column) {
    double result = 0;
    for (int i = 0; i < this.width; i++) {
      result += values.get(row).get(i) * m.values.get(i).get(column);
    }
    return result;
  }

  @Override
  public String toString() {
    int padWidth = values.stream()
        .flatMap(row -> row.stream())
        .map(val -> String.format("%,.4f", val))
        .mapToInt(String::length)
        .max()
        .getAsInt();

    return values.stream()
        .map(row -> row.stream()
            .map(val -> String.format("%,.4f", val))
            .map(val -> padLeft(val, padWidth))
            .collect(Collectors.joining(" ")))
        .collect(Collectors.joining("\n"));

  }

  private static String padLeft(String s, int padWidth) {
    StringBuilder buf = new StringBuilder();
    for (int i = s.length(); i < padWidth; i++) {
      buf.append(" ");
    }
    buf.append(s);
    return buf.toString();
  }

}
