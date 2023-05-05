package com.williamfiset.algorithms.datastructures.balancedtree;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.*;

public class TreapTreeTest {

  static final int MAX_RAND_NUM = +100000;
  static final int MIN_RAND_NUM = -100000;

  static final int TEST_SZ = 500;

  private TreapTree<Integer> tree;

  @BeforeEach
  public void setup() {
    tree = new TreapTree<>();
  }

  @Test
  public void testNullInsertion() {
    assertThrows(IllegalArgumentException.class, () -> tree.insert(null));
  }

  @Test
  public void testTreeContainsNull() {
    assertFalse(tree.contains(null));
  }

  @Test
  public void LeftLeftCase() {
    tree.insert(15, 15);
    tree.insert(10, 8);
    tree.insert(20, 10);
    tree.insert(30, 9);

    assertEquals(10, tree.root.left.getValue());
    assertEquals(15, tree.root.getValue());
    assertEquals(20, tree.root.right.getValue());
    assertEquals(30, tree.root.right.right.getValue());

    tree.insert(32, 14);

    assertEquals(10, tree.root.left.getValue());
    assertEquals(15, tree.root.getValue());
    assertEquals(32, tree.root.right.getValue());
    assertEquals(20, tree.root.right.left.getValue());
    assertEquals(30, tree.root.right.left.right.getValue());

    assertNull(tree.root.right.left.right.left);
    assertNull(tree.root.right.left.right.right);
    assertNull(tree.root.right.left.left);
    assertNull(tree.root.right.right);
    assertNull(tree.root.left.left);
    assertNull(tree.root.left.right);
  }

  @Test
  public void testLeftRightCase() {
    tree.insert(20, 10);
    tree.insert(17, 5);
    tree.insert(26, 7);

    assertEquals(20, tree.root.getValue());
    assertEquals(17, tree.root.left.getValue());
    assertEquals(26, tree.root.right.getValue());

    tree.insert(18, 15);
    assertEquals(18, tree.root.getValue());
    assertEquals(17, tree.root.left.getValue());
    assertEquals(20, tree.root.right.getValue());
    assertEquals(26, tree.root.right.right.getValue());

    assertNull(tree.root.left.left);
    assertNull(tree.root.left.right);
    assertNull(tree.root.right.left);
    assertNull(tree.root.right.right.left);
    assertNull(tree.root.right.right.right);
  }

  @Test
  public void testRightRightCase() {
    tree.insert(10, 2);
    tree.insert(8, 1);

    assertEquals(10, tree.root.getValue());
    assertEquals(8, tree.root.left.getValue());

    tree.insert(7, 3);

    assertEquals(7, tree.root.getValue());
    assertEquals(10, tree.root.right.getValue());
    assertEquals(8, tree.root.right.left.getValue());

    assertNull(tree.root.left);
    assertNull(tree.root.right.right);
    assertNull(tree.root.right.left.right);
    assertNull(tree.root.right.left.left);
  }

  @Test
  public void testRightLeftCase() {
    tree.insert(15, 10);
    tree.insert(16, 8);

    assertEquals(15, tree.root.getValue());
    assertEquals(16, tree.root.right.getValue());

    tree.insert(13, 11);

    assertEquals(13, tree.root.getValue());
    assertEquals(15, tree.root.right.getValue());
    assertEquals(16, tree.root.right.right.getValue());

    assertNull(tree.root.left);
    assertNull(tree.root.right.left);
    assertNull(tree.root.right.right.left);
    assertNull(tree.root.right.right.right);
  }

  @Test
  public void randomTreapOperations() {
    TreeSet<Integer> ts = new TreeSet<>();
    for (int i = 0; i < TEST_SZ; i++) {

      int size = i;
      List<Integer> lst = genRandList(size);
      for (Integer value : lst) {
        tree.insert(value);
        ts.add(value);
      }
      Collections.shuffle(lst);

      // Remove all the elements we just placed in the tree.
      for (int j = 0; j < size; j++) {

        Integer value = lst.get(j);

        assertEquals(ts.remove(value), tree.remove(value));
        assertFalse(tree.contains(value));
        assertEquals(size - j - 1, tree.size());
      }

      assertTrue(tree.isEmpty());
    }
  }

  static List<Integer> genRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i); // unique values.
    Collections.shuffle(lst);
    return lst;
  }

  public static int randValue() {
    return (int) (Math.random() * MAX_RAND_NUM * 2) + MIN_RAND_NUM;
  }
}
