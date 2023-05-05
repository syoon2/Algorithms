package com.williamfiset.algorithms.datastructures.kdtree;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GeneralKDTreeTest {

  /* TREE CREATION TESTS */
  @Test
  public void testDimensionsZero() {
    assertThrows(IllegalArgumentException.class, () -> new GeneralKDTree<>(0));
  }

  @Test
  public void testDimensionsNegative() {
    assertThrows(IllegalArgumentException.class, () -> new GeneralKDTree<>(-5));
  }

  /* INSERT METHOD TESTS */
  @Test
  public void testInsert() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(3);
    assertNull(kdTree.getRootPoint());
    Integer[] pointRoot = {3, 4, 3};
    Integer[] pointLeft = {1, 7, 6};
    Integer[] pointRight = {3, 0, 2};
    kdTree.insert(pointRoot);
    kdTree.insert(pointLeft);
    kdTree.insert(pointRight);
    assertNotNull(kdTree.getRootPoint());
    assertSame(pointRoot, kdTree.getRootPoint());
  }

  @Test
  public void testInsertNull() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.insert(null));
  }

  @Test
  public void testInsertMismatchDimensions() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.insert(new Integer[] {1, 2, 3}));
  }

  /* SEARCH METHOD TESTS */
  @Test
  public void testSearch() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(4);
    assertFalse(kdTree.search(new Integer[] {7, 5, 4, 9}));
    Integer[] point1 = {3, 4, 3, 9};
    Integer[] point2 = {2, 1, 5, 9};
    Integer[] point3 = {5, 6, 9, 9};
    Integer[] point4 = {4, 4, 0, 9};
    kdTree.insert(point1);
    kdTree.insert(point2);
    kdTree.insert(point3);
    kdTree.insert(point4);
    assertTrue(kdTree.search(point1));
    assertTrue(kdTree.search(point2));
    assertTrue(kdTree.search(point3));
    assertTrue(kdTree.search(point4));
    assertFalse(kdTree.search(new Integer[] {7, 5, 4, 9}));
  }

  @Test
  public void testSearchNull() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.search(null));
  }

  @Test
  public void testSearchMismatchDimensions() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.search(new Integer[] {1, 2, 3}));
  }

  /* FINDMIN METHOD TESTS */
  @Test
  public void testFindMin() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(3);
    assertNull(kdTree.findMin(0));
    Integer[] min1 = {0, 5, 4};
    Integer[] min2 = {3, 0, 7};
    Integer[] min3 = {6, 6, 0};
    kdTree.insert(new Integer[] {3, 7, 9});
    kdTree.insert(min1);
    kdTree.insert(min3);
    kdTree.insert(min2);
    kdTree.insert(new Integer[] {4, 7, 5});
    kdTree.insert(new Integer[] {3, 4, 8});
    kdTree.insert(new Integer[] {7, 7, 2});
    kdTree.insert(new Integer[] {8, 9, 8});
    assertSame(min1, kdTree.findMin(0));
    assertSame(min2, kdTree.findMin(1));
    assertSame(min3, kdTree.findMin(2));
  }

  @Test
  public void testFindMinOutOfBounds() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.findMin(2));
  }

  @Test
  public void testFindMinNegative() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.findMin(-1));
  }

  /* DELETE METHOD TESTS */
  @Test
  public void testDeleteEmpty() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertNull(kdTree.delete(new Integer[] {1, 2}));
  }

  @Test
  public void testDeleteRoot() {
    // General Setup
    GeneralKDTree<Integer> kdTreeBarren = new GeneralKDTree<Integer>(3);
    GeneralKDTree<Integer> kdTreeLeft = new GeneralKDTree<Integer>(3);
    GeneralKDTree<Integer> kdTreeRight = new GeneralKDTree<Integer>(3);
    GeneralKDTree<Integer> kdTreeTwo = new GeneralKDTree<Integer>(3);
    Integer[] rootPoint = {2, 2, 2};
    Integer[] leftPoint = {1, 1, 1};
    Integer[] rightPoint = {3, 3, 3};
    // No child test
    kdTreeBarren.insert(rootPoint);
    assertSame(rootPoint, kdTreeBarren.delete(rootPoint));
    // Left child test
    kdTreeLeft.insert(rootPoint);
    kdTreeLeft.insert(leftPoint);
    assertSame(rootPoint, kdTreeLeft.delete(rootPoint));
    // Right child test
    kdTreeRight.insert(rootPoint);
    kdTreeRight.insert(rightPoint);
    assertSame(rootPoint, kdTreeRight.delete(rootPoint));
    // Both children test
    kdTreeTwo.insert(rootPoint);
    kdTreeTwo.insert(leftPoint);
    kdTreeTwo.insert(rightPoint);
    assertSame(rootPoint, kdTreeTwo.delete(rootPoint));
  }

  @Test
  public void testDelete() {
    // Tree Setup
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(3);
    assertNull(kdTree.findMin(0));
    Integer[] point1 = {3, 7, 9};
    Integer[] point2 = {0, 5, 4};
    Integer[] point3 = {6, 6, 0};
    Integer[] point4 = {3, 0, 7};
    Integer[] point5 = {4, 7, 5};
    Integer[] point6 = {3, 4, 8};
    Integer[] point7 = {7, 7, 2};
    Integer[] point8 = {8, 9, 8};
    kdTree.insert(point1);
    kdTree.insert(point2);
    kdTree.insert(point3);
    kdTree.insert(point4);
    kdTree.insert(point5);
    kdTree.insert(point6);
    kdTree.insert(point7);
    kdTree.insert(point8);
    // Delete Action Assertions
    assertSame(point8, kdTree.delete(point8));
    assertSame(point5, kdTree.delete(point5));
    assertSame(point3, kdTree.delete(point3));
    assertNull(kdTree.delete(point8));
  }

  @Test
  public void testDeleteNull() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.delete(null));
  }

  @Test
  public void testDeleteMismatchDimensions() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.delete(new Integer[] {1, 2, 3}));
  }
}
