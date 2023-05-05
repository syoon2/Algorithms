package com.williamfiset.algorithms.datastructures.linkedlist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.*;

public class LinkedListTest {
  private static final int LOOPS = 10000;
  private static final int TEST_SZ = 40;
  private static final int NUM_NULLS = TEST_SZ / 5;
  private static final int MAX_RAND_NUM = 250;

  DoublyLinkedList<Integer> list;

  @BeforeEach
  public void setup() {
    list = new DoublyLinkedList<>();
  }

  @Test
  public void testEmptyList() {
    assertTrue(list.isEmpty());
    assertEquals(0, list.size());
  }

  @Test
  public void testRemoveFirstOfEmpty() {
    assertThrows(Exception.class, () -> list.removeFirst());
  }

  @Test
  public void testRemoveLastOfEmpty() {
    assertThrows(Exception.class, () -> list.removeLast());
  }

  @Test
  public void testPeekFirstOfEmpty() {
    assertThrows(Exception.class, () -> list.peekFirst());
  }

  @Test
  public void testPeekLastOfEmpty() {
    assertThrows(Exception.class, () -> list.peekLast());
  }

  @Test
  public void testAddFirst() {
    list.addFirst(3);
    assertEquals(1, list.size());
    list.addFirst(5);
    assertEquals(2, list.size());
  }

  @Test
  public void testAddLast() {
    list.addLast(3);
    assertEquals(1, list.size());
    list.addLast(5);
    assertEquals(2, list.size());
  }

  @Test
  public void testAddAt() throws Exception {
    list.addAt(0, 1);
    assertEquals(1, list.size());
    list.addAt(1, 2);
    assertEquals(2, list.size());
    list.addAt(1, 3);
    assertEquals(3, list.size());
    list.addAt(2, 4);
    assertEquals(4, list.size());
    list.addAt(1, 8);
    assertEquals(5, list.size());
  }

  @Test
  public void testRemoveFirst() {
    list.addFirst(3);
    assertEquals(3, list.removeFirst());
    assertTrue(list.isEmpty());
  }

  @Test
  public void testRemoveLast() {
    list.addLast(4);
    assertEquals(4, list.removeLast());
    assertTrue(list.isEmpty());
  }

  @Test
  public void testPeekFirst() {
    list.addFirst(4);
    assertEquals(4, list.peekFirst());
    assertEquals(1, list.size());
  }

  @Test
  public void testPeekLast() {
    list.addLast(4);
    assertEquals(4, list.peekLast());
    assertEquals(1, list.size());
  }

  @Test
  public void testPeeking() {
    // 5
    list.addFirst(5);
    assertEquals(5, list.peekFirst());
    assertEquals(5, list.peekLast());

    // 6 - 5
    list.addFirst(6);
    assertEquals(6, list.peekFirst());
    assertEquals(5, list.peekLast());

    // 7 - 6 - 5
    list.addFirst(7);
    assertEquals(7, list.peekFirst());
    assertEquals(5, list.peekLast());

    // 7 - 6 - 5 - 8
    list.addLast(8);
    assertEquals(7, list.peekFirst());
    assertEquals(8, list.peekLast());

    // 7 - 6 - 5
    list.removeLast();
    assertEquals(7, list.peekFirst());
    assertEquals(5, list.peekLast());

    // 7 - 6
    list.removeLast();
    assertEquals(7, list.peekFirst());
    assertEquals(6, list.peekLast());

    // 6
    list.removeFirst();
    assertEquals(6, list.peekFirst());
    assertEquals(6, list.peekLast());
  }

  @Test
  public void testRemoving() {
    DoublyLinkedList<String> strs = new DoublyLinkedList<>();
    strs.add("a");
    strs.add("b");
    strs.add("c");
    strs.add("d");
    strs.add("e");
    strs.add("f");
    strs.remove("b");
    strs.remove("a");
    strs.remove("d");
    strs.remove("e");
    strs.remove("c");
    strs.remove("f");
    assertEquals(0, strs.size());
  }

  @Test
  public void testRemoveAt() {
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.removeAt(0);
    list.removeAt(2);
    assertEquals(2, list.peekFirst());
    assertEquals(3, list.peekLast());
    list.removeAt(1);
    list.removeAt(0);
    assertEquals(0, list.size());
  }

  @Test
  public void testClear() {
    list.add(22);
    list.add(33);
    list.add(44);
    assertEquals(3, list.size());
    list.clear();
    assertEquals(0, list.size());
    list.add(22);
    list.add(33);
    list.add(44);
    assertEquals(3, list.size());
    list.clear();
    assertEquals(0, list.size());
  }

  @Test
  public void testRandomizedRemoving() {
    java.util.LinkedList<Integer> javaLinkedList = new java.util.LinkedList<>();
    for (int loops = 0; loops < LOOPS; loops++) {

      list.clear();
      javaLinkedList.clear();

      List<Integer> randNums = genRandList(TEST_SZ);
      for (Integer value : randNums) {
        javaLinkedList.add(value);
        list.add(value);
      }

      Collections.shuffle(randNums);

      for (int i = 0; i < randNums.size(); i++) {

        Integer rm_val = randNums.get(i);
        assertEquals(javaLinkedList.remove(rm_val), list.remove(rm_val));
        assertEquals(javaLinkedList.size(), list.size());

        assertIterableEquals(javaLinkedList, list);
      }

      list.clear();
      javaLinkedList.clear();

      for (Integer value : randNums) {
        javaLinkedList.add(value);
        list.add(value);
      }

      // Try removing elements whether or not they exist
      for (int i = 0; i < randNums.size(); i++) {

        Integer rm_val = (int) (MAX_RAND_NUM * Math.random());
        assertEquals(javaLinkedList.remove(rm_val), list.remove(rm_val));
        assertEquals(javaLinkedList.size(), list.size());

        assertIterableEquals(javaLinkedList, list);
      }
    }
  }

  @Test
  public void testRandomizedRemoveAt() {
    java.util.LinkedList<Integer> javaLinkedList = new java.util.LinkedList<>();

    for (int loops = 0; loops < LOOPS; loops++) {

      list.clear();
      javaLinkedList.clear();

      List<Integer> randNums = genRandList(TEST_SZ);

      for (Integer value : randNums) {
        javaLinkedList.add(value);
        list.add(value);
      }

      for (int i = 0; i < randNums.size(); i++) {

        int rm_index = (int) (list.size() * Math.random());

        Integer num1 = javaLinkedList.remove(rm_index);
        Integer num2 = list.removeAt(rm_index);
        assertEquals(num1, num2);
        assertEquals(javaLinkedList.size(), list.size());

        assertIterableEquals(javaLinkedList, list);
      }
    }
  }

  @Test
  public void testRandomizedIndexOf() {
    java.util.LinkedList<Integer> javaLinkedList = new java.util.LinkedList<>();

    for (int loops = 0; loops < LOOPS; loops++) {

      javaLinkedList.clear();
      list.clear();

      List<Integer> randNums = genUniqueRandList(TEST_SZ);

      for (Integer value : randNums) {
        javaLinkedList.add(value);
        list.add(value);
      }

      Collections.shuffle(randNums);

      for (int i = 0; i < randNums.size(); i++) {
        Integer elem = randNums.get(i);
        Integer index1 = javaLinkedList.indexOf(elem);
        Integer index2 = list.indexOf(elem);

        assertEquals(index1, index2);
        assertEquals(javaLinkedList.size(), list.size());

        assertIterableEquals(javaLinkedList, list);
      }
    }
  }

  @Test
  public void testToString() {
    DoublyLinkedList<String> strs = new DoublyLinkedList<>();
    assertEquals("[  ]", strs.toString());
    strs.add("a");
    assertEquals("[ a ]", strs.toString());
    strs.add("b");
    assertEquals("[ a, b ]", strs.toString());
    strs.add("c");
    strs.add("d");
    strs.add("e");
    strs.add("f");
    assertEquals("[ a, b, c, d, e, f ]", strs.toString());
  }

  // Generate a list of random numbers
  static List<Integer> genRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add((int) (Math.random() * MAX_RAND_NUM));
    for (int i = 0; i < NUM_NULLS; i++) lst.add(null);
    Collections.shuffle(lst);
    return lst;
  }

  // Generate a list of unique random numbers
  static List<Integer> genUniqueRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i);
    for (int i = 0; i < NUM_NULLS; i++) lst.add(null);
    Collections.shuffle(lst);
    return lst;
  }
}
