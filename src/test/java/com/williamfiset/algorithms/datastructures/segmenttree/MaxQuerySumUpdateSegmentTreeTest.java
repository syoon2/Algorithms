/**
 * gradle test --info --tests
 * "com.williamfiset.algorithms.datastructures.segmenttree.MaxQuerySumUpdateSegmentTreeTest"
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import static org.junit.jupiter.api.Assertions.*;

import com.williamfiset.algorithms.utils.TestUtils;

import org.junit.jupiter.api.*;

public class MaxQuerySumUpdateSegmentTreeTest {

  static int ITERATIONS = 1000;

  @BeforeEach
  public void setup() {}

  @Test
  public void simpleTest() {
    long[] ar = {2, 1, 3, 4, -1};
    MaxQuerySumUpdateSegmentTree st = new MaxQuerySumUpdateSegmentTree(ar);

    st.rangeUpdate1(0, 4, 1);

    assertEquals(5, st.rangeQuery1(0, 4));
    assertEquals(3, st.rangeQuery1(0, 1));
    assertEquals(4, st.rangeQuery1(1, 2));
    assertEquals(5, st.rangeQuery1(1, 3));

    st.rangeUpdate1(3, 4, 4);

    assertEquals(9, st.rangeQuery1(0, 4));
    assertEquals(3, st.rangeQuery1(0, 1));
    assertEquals(9, st.rangeQuery1(3, 4));
    assertEquals(2, st.rangeQuery1(1, 1));
    assertEquals(4, st.rangeQuery1(2, 2));
    assertEquals(9, st.rangeQuery1(3, 3));
    assertEquals(9, st.rangeQuery1(1, 3));
    assertEquals(9, st.rangeQuery1(2, 3));
    assertEquals(4, st.rangeQuery1(1, 2));

    st.rangeUpdate1(1, 3, 3);

    assertEquals(12, st.rangeQuery1(0, 4));
    assertEquals(7, st.rangeQuery1(0, 2));
    assertEquals(12, st.rangeQuery1(2, 4));
    assertEquals(12, st.rangeQuery1(1, 3));
    assertEquals(3, st.rangeQuery1(0, 0));
    assertEquals(5, st.rangeQuery1(1, 1));
    assertEquals(7, st.rangeQuery1(2, 2));
    assertEquals(12, st.rangeQuery1(3, 3));
    assertEquals(4, st.rangeQuery1(4, 4));
  }

  @Test
  public void testRandomRangeSumUpdatesWithSumRangeQueries() {
    for (int n = 5; n < ITERATIONS; n++) {
      long[] ar = TestUtils.randomLongArray(n, -100, +100);
      MaxQuerySumUpdateSegmentTree st = new MaxQuerySumUpdateSegmentTree(ar);

      for (int i = 0; i < n; i++) {
        // System.out.printf("n = %d, i = %d\n", n, i);
        int j = TestUtils.randValue(0, n - 1);
        int k = TestUtils.randValue(0, n - 1);
        int i1 = Math.min(j, k);
        int i2 = Math.max(j, k);

        j = TestUtils.randValue(0, n - 1);
        k = TestUtils.randValue(0, n - 1);
        int i3 = Math.min(j, k);
        int i4 = Math.max(j, k);

        // Range update
        long randValue = TestUtils.randValue(-10, 10);
        // System.out.printf("UPDATE [%d, %d] with %d\n", i3, i4, randValue);
        bruteForceSumRangeUpdate(ar, i3, i4, randValue);
        st.rangeUpdate1(i3, i4, randValue);

        // Range query
        long bfMax = bruteForceMax(ar, i1, i2);
        long segTreeMax = st.rangeQuery1(i1, i2);
        // System.out.printf("QUERY [%d, %d], want = %d, got = %d\n", i1, i2, bfMax, segTreeMax);
        assertEquals(bfMax, segTreeMax);
      }
    }
  }

  // Finds the sum in an array between [l, r] in the `values` array
  private static long bruteForceSum(long[] values, int l, int r) {
    long s = 0;
    for (int i = l; i <= r; i++) {
      s += values[i];
    }
    return s;
  }

  // Finds the min value in an array between [l, r] in the `values` array
  private static long bruteForceMin(long[] values, int l, int r) {
    long m = values[l];
    for (int i = l; i <= r; i++) {
      m = Math.min(m, values[i]);
    }
    return m;
  }

  // Finds the max value in an array between [l, r] in the `values` array
  private static long bruteForceMax(long[] values, int l, int r) {
    long m = values[l];
    for (int i = l; i <= r; i++) {
      m = Math.max(m, values[i]);
    }
    return m;
  }

  private static void bruteForceSumRangeUpdate(long[] values, int l, int r, long x) {
    for (int i = l; i <= r; i++) {
      values[i] += x;
    }
  }

  private static void bruteForceMulRangeUpdate(long[] values, int l, int r, long x) {
    for (int i = l; i <= r; i++) {
      values[i] *= x;
    }
  }

  private static void bruteForceAssignRangeUpdate(long[] values, int l, int r, long x) {
    for (int i = l; i <= r; i++) {
      values[i] = x;
    }
  }
}
