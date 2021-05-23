/**
 * Implementation of the Minimum Weight Perfect Matching (MWPM) problem. In this problem you are
 * given a distance matrix which gives the distance from each node to every other node, and you want
 * to pair up all the nodes to one another minimizing the overall cost.
 *
 * <p>Time Complexity: O(n^2 * 2^n)
 *
 * @author William Fiset
 */
package com.williamfiset.algorithms.dp;

import java.awt.geom.*;
import java.util.*;

public class MinimumWeightPerfectMatching {

  // Inputs
  private final int n;
  private double[][] cost;

  // Internal
  private final int END_STATE;
  private boolean solved;

  // Outputs
  private double minWeightCost;
  private int[] matching;

  // The cost matrix should be a symmetric (i.e cost[i][j] = cost[j][i])
  public MinimumWeightPerfectMatching(double[][] cost) {
    if (cost == null) throw new IllegalArgumentException("Input cannot be null");
    n = cost.length;
    if (n == 0) throw new IllegalArgumentException("Matrix size is zero");
    if (n % 2 != 0)
      throw new IllegalArgumentException("Matrix has an odd size, no perfect matching exists.");
    if (n > 32)
      throw new IllegalArgumentException(
          "Matrix too large! A matrix that size for the MWPM problem with a time complexity of"
              + "O(n^2*2^n) requires way too much computation and memory for a modern home computer.");
    END_STATE = (1 << n) - 1;
    this.cost = cost;
  }

  public double getMinWeightCost() {
    solve();
    return minWeightCost;
  }

  /**
   * Get the minimum weight cost matching. The matching is returned as an array where the nodes at
   * index 2*i and 2*i+1 form a matched pair. For example, nodes at indexes (0, 1) are a pair, (2,
   * 3) are another pair, etc...
   *
   * <p>How to iterate over the pairs:
   *
   * <pre>{@code
   * MinimumWeightPerfectMatching mwpm = ...
   * int[] matching = mwpm.getMinWeightCostMatching();
   * for (int i = 0; i < matching.length / 2; i++) {
   *   int node1 = matching[2*i];
   *   int node2 = matching[2*i+1];
   *   // Do something with the matched pair (node1, node2)
   * }
   * }</pre>
   */
  public int[] getMinWeightCostMatching() {
    solve();
    return matching;
  }

  public void solve() {
    if (solved) return;

    // The DP state is encoded as a bitmask where the i'th bit is flipped on if the i'th node is
    // included in the state. Encoding the state this way allows us to compactly represent selecting
    // a subset of the nodes present in the matching. Furthermore, it allows using the '&' binary
    // operator to compare states to see if they overlap and the '|' operator to combine states.
    Double[] dp = new Double[1 << n];

    // Memo table to save the history of the chosen states. This table is used to reconstruct the
    // chosen pairs of nodes after the algorithm has executed.
    int[] history = new int[1 << n];

    // Singleton pair states with only two nodes are the building blocks of this algorithm. Every
    // iteration, we try to add singleton pairs to previous states to construct a larger matching.
    final int numPairs = (n * (n + 1)) / 2;
    int[] pairStates = new int[numPairs];
    double[] pairCost = new double[numPairs];

    for (int i = 0, k = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++, k++) {
        int state = (1 << i) | (1 << j);
        dp[state] = cost[i][j];
        pairStates[k] = state;
        pairCost[k] = cost[i][j];
      }
    }

    for (int state = 0b11; state < (1 << n); state++) {
      // Skip states with an odd number of nodes. Check dp[state] instead of 
      // Integer.bitCount for convenience.
      if (dp[state] == null) {
        continue;
      }
      for (int i = 0; i < numPairs; i++) {
        int pair = pairStates[i];
        // Ignore states which overlap
        if ((state & pair) != 0) continue;

        int newState = state | pair;
        double newCost = dp[state] + pairCost[i];
        if (dp[newState] == null || newCost < dp[newState]) {
          dp[newState] = newCost;
          // Save the fact that we went from 'state' -> 'newState'. From this we will be able to
          // reconstruct which pairs of nodes were taken by looking at 'state' xor 'newState' which
          // should give us the binary representation (state) of the pair used.
          history[newState] = state;
        }
      }
    }

    reconstructMatching(history);

    minWeightCost = dp[END_STATE];
    solved = true;
  }

  // Populates the `matching` array with a sorted deterministic matching sorted by lowest node
  // index. For example, if the perfect matching consists of the pairs (3, 4), (1, 5), (0, 2).
  // The matching is sorted such that the pairs appear in the ordering: (0, 2), (1, 5), (3, 4).
  // Furthermore, it is guaranteed that for any pair (a, b) that a < b.
  private void reconstructMatching(int[] history) {
    // A map between pairs of nodes that were matched together.
    int[] map = new int[n];
    int[] leftNodes = new int[n/2];

    // Reconstruct the matching of pairs of nodes working backwards through computed states.
    for (int i = 0, state = END_STATE; state != 0; state = history[state]) {
      // Isolate the pair used by xoring the state with the state used to generate it.
      int pairUsed = state ^ history[state];

      int leftNode = getBitPosition(Integer.lowestOneBit(pairUsed));
      int rightNode = getBitPosition(Integer.highestOneBit(pairUsed));

      leftNodes[i++] = leftNode;
      map[leftNode] = rightNode;
    }

    // Sort the left nodes in ascending order.
    java.util.Arrays.sort(leftNodes);

    matching = new int[n];
    for (int i = 0; i < n/2; i++) {
      matching[2*i] = leftNodes[i];
      int rightNode = map[leftNodes[i]];
      matching[2*i+1] = rightNode;
    }
  }

  // Gets the zero base index position of the 1 bit in `k`. `k` must be a power of 2, so there is
  // only ever 1 bit in the binary representation of k.
  private int getBitPosition(int k) {
    int count = -1;
    while (k > 0) {
      count++;
      k >>= 1;
    }
    return count;
  }

  /* Example */

  public static void main(String[] args) {
    test2();
    // for (int i = 0; i < 50; i++) {
    //   System.out.println(i + " " + include(i) + " " + Integer.toBinaryString(i));
    // }
  }

  private static String include(int i) {
    boolean toInclude = Integer.bitCount(i) >= 2 && Integer.bitCount(i) % 2 == 0;
    return toInclude ? "YES" : " NO";
  }

  private static void test1() {
    int n = 18;
    List<Point2D> pts = new ArrayList<>();

    // Generate points on a 2D plane which will produce a unique answer
    for (int i = 0; i < n / 2; i++) {
      pts.add(new Point2D.Double(2 * i, 0));
      pts.add(new Point2D.Double(2 * i, 1));
    }
    Collections.shuffle(pts);

    double[][] cost = new double[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        cost[i][j] = pts.get(i).distance(pts.get(j));
      }
    }

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    double minCost = mwpm.getMinWeightCost();
    if (minCost != n / 2) {
      System.out.printf("MWPM cost is wrong! Got: %.5f But wanted: %d\n", minCost, n / 2);
    } else {
      System.out.printf("MWPM is: %.5f\n", minCost);
    }

    int[] matching = mwpm.getMinWeightCostMatching();
    for (int i = 0; i < matching.length / 2; i++) {
      int ii = matching[2 * i];
      int jj = matching[2 * i + 1];
      System.out.printf(
          "(%d, %d) <-> (%d, %d)\n",
          (int) pts.get(ii).getX(),
          (int) pts.get(ii).getY(),
          (int) pts.get(jj).getX(),
          (int) pts.get(jj).getY());
    }
  }

  private static void test2() {
    double[][] costMatrix = {
      {0, 2, 1, 2},
      {2, 0, 2, 1},
      {1, 2, 0, 2},
      {2, 1, 2, 0},
    };

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
    double cost = mwpm.getMinWeightCost();
    if (cost != 2.0) {
      System.out.println("error cost not 2");
    }
  }
}
