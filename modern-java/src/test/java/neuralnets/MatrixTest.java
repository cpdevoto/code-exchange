package neuralnets;

import static neuralnets.Matrix.row;

import org.junit.Test;

public class MatrixTest {

  @Test
  public void test() {
    Matrix m1 = Matrix.of(
        row(1, 2),
        row(3, 4));

    Matrix m2 = Matrix.of(
        row(5, 6),
        row(7, 8));

    System.out.println(m1.dotProduct(m2));

    Matrix m3 = Matrix.of(
        row(5),
        row(7));

    System.out.println();
    System.out.println(m1.dotProduct(m3));

    System.out.println();
    System.out.println(Matrix.of(row(3, 4, 2)).dotProduct(Matrix.of(
        row(13, 9, 7, 15),
        row(8, 7, 4, 6),
        row(6, 4, 0, 3))));

    System.out.println();
    Matrix xHidden = Matrix.of(
        row(0.9, 0.3, 0.4),
        row(0.2, 0.8, 0.2),
        row(0.1, 0.5, 0.6)).dotProduct(
            Matrix.of(
                row(0.9),
                row(0.1),
                row(0.8)));
    System.out.println(xHidden);

    System.out.println();
    System.out.println(xHidden.sigmoid());


  }

}
