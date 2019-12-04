import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 *
 */
public class Solution {

  /*
   * Make a single ring.
   */
  static List<Integer> makeRing(List<List<Integer>> matrix, int ringNum, int m, int n) {

    // **** ****
    List<Integer> ring = new ArrayList<Integer>();

    // **** go right ****
    for (int i = ringNum; i < (n - ringNum); i++) {
      ring.add(matrix.get(ringNum).get(i));
    }

    // **** go down (skip first and last) ****
    for (int i = ringNum + 1; i < m - ringNum - 1; i++) {
      ring.add(matrix.get(i).get(n - ringNum - 1));
    }

    // **** go left ****
    for (int i = (n - ringNum - 1); i >= ringNum; i--) {
      ring.add(matrix.get(m - ringNum - 1).get(i));
    }

    // **** go up (skip first and last) ****
    for (int i = m - ringNum - 2; i > ringNum; i--) {
      ring.add(matrix.get(i).get(ringNum));
    }

    // **** ****
    return ring;
  }

  /*
   * Make rings.
   */
  static List<List<Integer>> makeRings(List<List<Integer>> matrix, int m, int n) {

    // **** list of rings ****
    List<List<Integer>> rings = new ArrayList<>();

    // **** make rings (outer ring == 0) ****
    for (int ringNum = 0; ringNum < Math.min(m / 2, n / 2); ringNum++) {

      // **** ****
      List<Integer> ring = makeRing(matrix, ringNum, m, n);

      // **** add ring to rings ****
      rings.add(ring);
    }

    // **** return rings ****
    return rings;
  }

  /*
   * Rotate the specified ring.
   */
  static void rotateRing(List<List<Integer>> rings, int r, int ringNum) {

    // **** initialization ****
    List<Integer> tempRing = new ArrayList<>();
    List<Integer> ring = rings.get(ringNum);
    int len = ring.size();

    // **** copy values to temp ring ****
    int i = r % len;
    while (true) {

      // ***** copy this element ****
      tempRing.add(ring.get(i));

      // **** update index ****
      i++;
      if (i >= len)
        i = 0;

      // **** check if done with copy ****
      if (i == (r % len))
        break;
    }

    // **** replace ring with temp ring ****
    rings.set(ringNum, tempRing);
  }

  /*
   * Rotate rings.
   */
  static List<List<Integer>> rotateRings(List<List<Integer>> rings, int m, int n, int r) {

    // **** loop rotating rings ****
    for (int ringNum = 0; ringNum < rings.size(); ringNum++) {

      // **** rotate this ring ****
      rotateRing(rings, r, ringNum);
    }

    // **** return rotated rings ****
    return rings;
  }

  /*
   * Convert rings to rotated matrix.
   */
  static int[][] ringsToMatrix(List<List<Integer>> rotatedRings, int m, int n) {

    // **** declare rotated matrix ****
    int[][] matrix = new int[m][n];

    // **** traverse each ring populating the matrix ****
    for (int ringNum = 0; ringNum < Math.min(m / 2, n / 2); ringNum++) {

      // **** select ring ****
      List<Integer> ring = rotatedRings.get(ringNum);

      // **** populate matrix with the ring contents ****
      for (int i = 0; i < ring.size();) {

        // **** go right ****
        for (int col = ringNum; col < (n - ringNum); col++) {
          matrix[ringNum][col] = ring.get(i++);
        }

        // **** go down ****
        for (int row = ringNum + 1; row < (m - ringNum); row++) {
          matrix[row][n - ringNum - 1] = ring.get(i++);
        }

        // **** go left ****
        for (int col = (n - ringNum - 2); col >= ringNum; col--) {
          matrix[m - ringNum - 1][col] = ring.get(i++);
        }

        // **** go up ****
        for (int row = m - ringNum - 2; row > ringNum; row--) {
          matrix[row][ringNum] = ring.get(i++);
        }
      }
    }

    // **** return rotated matrix ****
    return matrix;
  }

  /*
   * Print the specified matrix.
   */
  static void printMatrix(int[][] matrix, int m, int n) {

    // **** ****
    for (int row = 0; row < m; row++) {
      for (int col = 0; col < n; col++) {
        System.out.print(matrix[row][col] + " ");
      }
      System.out.println();
    }
  }

  /*
   * Complete the matrixRotation function below.
   */
  static void matrixRotation(List<List<Integer>> matrix, int r) {

    // **** ****
    int m = matrix.size();
    int n = matrix.get(0).size();

    // **** make rings ****
    List<List<Integer>> rings = makeRings(matrix, m, n);

    // **** rotate rings ****
    List<List<Integer>> rotatedRings = rotateRings(rings, m, n, r);

    // **** rings to matrix ****
    int[][] rotMatrix = ringsToMatrix(rotatedRings, m, n);

    // **** print matrix ****
    printMatrix(rotMatrix, m, n);
  }

  /*
   * Test scaffolding.
   */
  public static void main(String[] args) {

    // **** open scanner ****
    Scanner sc = new Scanner(System.in);

    // **** read m ****
    int m = sc.nextInt();

    // **** read n ****
    int n = sc.nextInt();

    // **** read r ****
    int r = sc.nextInt();
    sc.nextLine();

    // **** declare the matrix ****
    List<List<Integer>> matrix = new ArrayList<>();

    // **** populate matrix (a row at a time) ****
    for (int i = 0; i < m; i++) {

      // **** read the next row of values ****
      String[] matrixRowTempItems = sc.nextLine().split(" ");

      // **** ****
      List<Integer> matrixRowItems = new ArrayList<Integer>();

      // **** ****
      for (int j = 0; j < matrixRowTempItems.length; j++) {
        int matrixItem = Integer.parseInt(matrixRowTempItems[j]);
        matrixRowItems.add(matrixItem);
      }

      // **** ****
      matrix.add(matrixRowItems);
    }

    // **** close scanner ****
    sc.close();

    // **** call function ****
    matrixRotation(matrix, r);
  }
}