package com.williamfiset.algorithms.datastructures.sparsetable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;

public class SparseTableTest {

  private void queryResultTest(
      long[] values, int l, int r, long actual, int index, SparseTable.Operation op) {
    if (op == SparseTable.Operation.MIN) {
      minQuery(values, l, r, actual, index);
    } else if (op == SparseTable.Operation.MAX) {
      maxQuery(values, l, r, actual, index);
    } else if (op == SparseTable.Operation.SUM) {
      sumQuery(values, l, r, actual);
    } else if (op == SparseTable.Operation.MULT) {
      multQuery(values, l, r, actual);
    } else if (op == SparseTable.Operation.GCD) {
      gcdQuery(values, l, r, actual);
    }
  }

  private void minQuery(long[] values, int l, int r, long actual, int index) {
    long m = Long.MAX_VALUE;
    for (int i = l; i <= r; i++) m = Math.min(m, values[i]);
    assertEquals(m, actual);
    assertEquals(m, values[index]);
  }

  private void maxQuery(long[] values, int l, int r, long actual, int index) {
    long m = Long.MIN_VALUE;
    for (int i = l; i <= r; i++) m = Math.max(m, values[i]);
    assertEquals(m, actual);
    assertEquals(m, values[index]);
  }

  private void sumQuery(long[] values, int l, int r, long actual) {
    long m = 0;
    for (int i = l; i <= r; i++) m += values[i];
    assertEquals(m, actual);
  }

  private void multQuery(long[] values, int l, int r, long actual) {
    long m = 1;
    for (int i = l; i <= r; i++) m *= values[i];
    assertEquals(m, actual);
  }

  // Computes the Greatest Common Divisor (GCD) of a & b
  // This method ensures that the value returned is non negative
  public static long gcd(long a, long b) {
    return b == 0 ? (a < 0 ? -a : a) : gcd(b, a % b);
  }

  private void gcdQuery(long[] values, int l, int r, long actual) {
    long m = values[l];
    for (int i = l; i <= r; i++) m = gcd(m, values[i]);
    assertEquals(m, actual);
  }

  private void testAllOperations(long[] values) {
    SparseTable min_st = new SparseTable(values, SparseTable.Operation.MIN);
    SparseTable max_st = new SparseTable(values, SparseTable.Operation.MAX);
    SparseTable sum_st = new SparseTable(values, SparseTable.Operation.SUM);
    SparseTable mult_st = new SparseTable(values, SparseTable.Operation.MULT);
    SparseTable gcd_st = new SparseTable(values, SparseTable.Operation.GCD);

    for (int i = 0; i < values.length; i++) {
      for (int j = i; j < values.length; j++) {
        queryResultTest(
            values, i, j, min_st.query(i, j), min_st.queryIndex(i, j), SparseTable.Operation.MIN);
        queryResultTest(
            values, i, j, max_st.query(i, j), max_st.queryIndex(i, j), SparseTable.Operation.MAX);
        queryResultTest(
            values, i, j, sum_st.query(i, j), -1 /* unused */, SparseTable.Operation.SUM);
        queryResultTest(
            values, i, j, mult_st.query(i, j), -1 /* unused */, SparseTable.Operation.MULT);
        queryResultTest(
            values, i, j, gcd_st.query(i, j), -1 /* unused */, SparseTable.Operation.GCD);
      }
    }
  }

  @Test
  public void simple() {
    long[] values = {1, 0, 1, -2, 3, -4, 5, -6, 7, -8, 9};
    testAllOperations(values);
  }

  @Test
  public void smallRangeRandomArrayTests() {
    for (int i = 1; i < 100; i++) {
      long[] values = genRandArray(i, -10, 10);
      testAllOperations(values);
    }
  }

  @Test
  public void randomArrayTests() {
    for (int i = 1; i < 100; i++) {
      long[] values = genRandArray(i, -100000, 100000);
      testAllOperations(values);
    }
  }

  @Test
  public void verifyIndexIsAlwaysLeftmostPositionWhenThereAreCollisions() {
    long[] values = {5, 4, 3, 3, 3, 3, 3, 5, 6, 7};
    SparseTable st = new SparseTable(values, SparseTable.Operation.MIN);

    for (int i = 0; i < values.length; i++) {
      for (int j = i; j < values.length; j++) {
        long min = Long.MAX_VALUE;
        int minIndex = 0;
        for (int k = i; k <= j; k++) {
          if (values[k] < min) {
            min = values[k];
            minIndex = k;
          }
        }

        assertEquals(min, st.query(i, j));
        assertTrue(i <= minIndex && minIndex <= j);
        assertEquals(minIndex, st.queryIndex(i, j));
      }
    }
  }

  @Test
  public void verifyIndexIsAlwaysLeftmostPosition_randomized() {
    for (int loop = 2; loop < 100; loop++) {
      long[] values = genRandArray(loop, -100, +100);
      SparseTable min_st = new SparseTable(values, SparseTable.Operation.MIN);
      SparseTable max_st = new SparseTable(values, SparseTable.Operation.MAX);

      for (int i = 0; i < values.length; i++) {
        for (int j = i; j < values.length; j++) {
          long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
          int minIndex = 0, maxIndex = 0;
          ;
          for (int k = i; k <= j; k++) {
            if (values[k] < min) {
              min = values[k];
              minIndex = k;
            }
            if (values[k] > max) {
              max = values[k];
              maxIndex = k;
            }
          }
          assertEquals(min, min_st.query(i, j));
          assertTrue(i <= minIndex && minIndex <= j);
          assertEquals(minIndex, min_st.queryIndex(i, j));

          assertEquals(max, max_st.query(i, j));
          assertTrue(i <= maxIndex && maxIndex <= j);
          assertEquals(maxIndex, max_st.queryIndex(i, j));
        }
      }
    }
  }

  private static long[] genRandArray(int n, int lo, int hi) {
    return new Random().longs(n, lo, hi).toArray();
  }
}
