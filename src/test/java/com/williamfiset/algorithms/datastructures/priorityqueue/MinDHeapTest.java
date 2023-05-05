package com.williamfiset.algorithms.datastructures.priorityqueue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.jupiter.api.*;

public class MinDHeapTest {

  static final int LOOPS = 1000;
  static final int MAX_SZ = 100;

  @BeforeEach
  public void setup() {}

  @Test
  public void testEmpty() {
    MinDHeap<Integer> q = new MinDHeap<>(4, 0);
    assertEquals(0, q.size());
    assertTrue(q.isEmpty());
    assertNull(q.poll());
    assertNull(q.peek());
  }

  @Test
  public void testHeapProperty() {

    MinDHeap<Integer> q = new MinDHeap<>(3, 30);
    Integer[] nums = {3, 2, 5, 6, 7, 9, 4, 8, 1};

    // Try manually creating heap
    for (int n : nums) q.add(n);
    for (int i = 1; i <= 9; i++) assertEquals(i, q.poll());
  }

  @Test
  public void testPriorityQueueSizeParam() {
    for (int i = 1; i < LOOPS; i++) {

      Integer[] lst = genRandArray(i);

      MinDHeap<Integer> pq = new MinDHeap<>(i, lst.length);
      PriorityQueue<Integer> pq2 = new PriorityQueue<>(i);

      for (int x : lst) {
        pq2.add(x);
        pq.add(x);
      }
      while (!pq2.isEmpty()) assertEquals(pq2.poll(), pq.poll());
    }
  }

  @Test
  public void testPriorityRandomOperations() {
    for (int loop = 0; loop < LOOPS; loop++) {

      double p1 = Math.random();
      double p2 = Math.random();
      if (p2 < p1) {
        double tmp = p1;
        p1 = p2;
        p2 = tmp;
      }

      Integer[] ar = genRandArray(LOOPS);
      int d = 2 + (int) (Math.random() * 6);
      MinDHeap<Integer> pq = new MinDHeap<>(d, LOOPS);
      PriorityQueue<Integer> pq2 = new PriorityQueue<>(LOOPS);

      for (int i = 0; i < LOOPS; i++) {
        int e = ar[i];
        double r = Math.random();
        if (0 <= r && r <= p1) {
          pq.add(e);
          pq2.add(e);
        } else if (p1 < r && r <= p2) {
          if (!pq2.isEmpty()) assertEquals(pq2.poll(), pq.poll());
        } else {
          pq.clear();
          pq2.clear();
        }
      }

      assertEquals(pq2.peek(), pq.peek());
    }
  }

  @Test
  public void testClear() {
    String[] strs = {"aa", "bb", "cc", "dd", "ee"};
    MinDHeap<String> q = new MinDHeap<>(2, strs.length);
    for (String s : strs) q.add(s);
    q.clear();
    assertEquals(0, q.size());
    assertTrue(q.isEmpty());
  }

  /*
  @Test
  public void testContainmentRandomized() {

    for (int i = 0; i < LOOPS; i++) {

      List <Integer> randNums = genRandList(100);
      PriorityQueue <Integer> PQ = new PriorityQueue<>();
      PQueue <Integer> pq = new PQueue<>();
      for (int j = 0; j < randNums.size(); j++) {
        pq.add(randNums.get(j));
        PQ.add(randNums.get(j));
      }

      for (int j = 0; j < randNums.size(); j++) {

        int randVal = randNums.get(j);
        assertEquals(PQ.contains(randVal), pq.contains(randVal));
        pq.remove(randVal); PQ.remove(randVal);
        assertEquals(PQ.contains(randVal), pq.contains(randVal));

      }

    }

  }

  public void sequentialRemoving(Integer[] in, Integer[] removeOrder) {

    assertEquals(in.length, removeOrder.length);

    PQueue <Integer> pq = new PQueue<>(in);
    PriorityQueue <Integer> PQ = new PriorityQueue<>();
    for (int value : in) PQ.offer(value);

    assertTrue(pq.isMinHeap(0));

    for (int i = 0; i < removeOrder.length; i++) {

      int elem = removeOrder[i];

      assertEquals(PQ.peek(), pq.peek());
      assertEquals(PQ.remove(elem), pq.remove(elem));
      assertEquals(PQ.size(), pq.size());
      assertTrue(pq.isMinHeap(0));

    }

    assertTrue(pq.isEmpty());

  }

  @Test
  public void testRemoving() {

    Integer [] in = {1,2,3,4,5,6,7};
    Integer [] removeOrder = { 1,3,6,4,5,7,2 };
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {1,2,3,4,5,6,7,8,9,10,11};
    removeOrder = new Integer[] {7,4,6,10,2,5,11,3,1,8,9};
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {8, 1, 3, 3, 5, 3};
    removeOrder = new Integer[] {3,3,5,8,1,3};
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {7, 7, 3, 1, 1, 2};
    removeOrder = new Integer[] {2, 7, 1, 3, 7, 1};
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {32, 66, 93, 42, 41, 91, 54, 64, 9, 35};
    removeOrder = new Integer[] {64, 93, 54, 41, 35, 9, 66, 42, 32, 91};
    sequentialRemoving(in, removeOrder);

  }
  */

  @Test
  public void testRemovingDuplicates() {

    Integer[] in = new Integer[] {2, 7, 2, 11, 7, 13, 2};
    MinDHeap<Integer> pq = new MinDHeap<>(3, in.length + 1);

    for (Integer x : in) pq.add(x);
    assertEquals(2, pq.peek());
    pq.add(3);

    assertEquals(2, pq.poll());
    assertEquals(2, pq.poll());
    assertEquals(2, pq.poll());
    assertEquals(3, pq.poll());
    assertEquals(7, pq.poll());
    assertEquals(7, pq.poll());
    assertEquals(11, pq.poll());
    assertEquals(13, pq.poll());
  }
  /*
  @Test
  public void testRandomizedPolling() {

    for (int i = 0; i < LOOPS; i++) {

      int sz = i;
      List <Integer> randNums = genRandList(sz);
      PriorityQueue <Integer> pq1 = new PriorityQueue<>();
      PQueue <Integer> pq2 = new PQueue<>();

      // Add all the elements to both priority queues
      for (Integer value : randNums) {
        pq1.offer(value);
        pq2.add(value);
      }

      while( !pq1.isEmpty() ) {

        assertTrue(pq2.isMinHeap(0));
        assertEquals(pq1.size(), pq2.size());
        assertEquals(pq1.peek(), pq2.peek());
        assertEquals(pq1.contains(pq1.peek()), pq2.contains(pq2.peek()));

        Integer v1 = pq1.poll();
        Integer v2 = pq2.poll();

        assertEquals(v1, v2);
        assertEquals(pq1.peek(), pq2.peek());
        assertEquals(pq1.size(), pq2.size());
        assertTrue(pq2.isMinHeap(0));

      }

    }

  }

  @Test
  public void testRandomizedRemoving() {

    for (int i = 0; i < LOOPS; i++) {

      int sz = i;
      List <Integer> randNums = genRandList(sz);
      PriorityQueue <Integer> pq1 = new PriorityQueue<>();
      PQueue <Integer> pq2 = new PQueue<>();

      // Add all the elements to both priority queues
      for (Integer value : randNums) {
        pq1.offer(value);
        pq2.add(value);
      }

      Collections.shuffle(randNums);
      int index = 0;

      while( !pq1.isEmpty() ) {

        int removeNum = randNums.get(index++);

        assertTrue(pq2.isMinHeap(0));
        assertEquals(pq1.size(), pq2.size());
        assertEquals(pq1.peek(), pq2.peek());
        pq1.remove(removeNum); pq2.remove(removeNum);
        assertEquals(pq1.peek(), pq2.peek());
        assertEquals(pq1.size(), pq2.size());
        assertTrue(pq2.isMinHeap(0));

      }

    }

  }

  @Test
  public void testPQReusability() {

    List <Integer> SZs = genUniqueRandList(LOOPS);

    PriorityQueue <Integer> PQ = new PriorityQueue<>();
    PQueue <Integer> pq = new PQueue<>();

    for (int sz : SZs) {

      pq.clear();
      PQ.clear();

      List <Integer> nums = genRandList(sz);
      for (int n : nums) {
        pq.add(n);
        PQ.add(n);
      }

      Collections.shuffle(nums);

      for (int i = 0; i < sz/2; i++) {

        // Sometimes add a new number into the Pqueue
        if (0.25 < Math.random()) {
          int randNum = (int) (Math.random() * 10000);
          PQ.add(randNum);
          pq.add(randNum);
        }

        int removeNum = nums.get(i);

        assertTrue(pq.isMinHeap(0));
        assertEquals(PQ.size(), pq.size());
        assertEquals(PQ.peek(), pq.peek());

        PQ.remove(removeNum);
        pq.remove(removeNum);

        assertEquals(PQ.peek(), pq.peek());
        assertEquals(PQ.size(), pq.size());
        assertTrue(pq.isMinHeap(0));

      }

    }

  }
  */

  static Integer[] genRandArray(int sz) {
    Integer[] lst = new Integer[sz];
    for (int i = 0; i < sz; i++) lst[i] = (int) (Math.random() * MAX_SZ);
    return lst;
  }

  // Generate a list of random numbers
  static List<Integer> genRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add((int) (Math.random() * MAX_SZ));
    return lst;
  }

  // Generate a list of unique random numbers
  static List<Integer> genUniqueRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i);
    Collections.shuffle(lst);
    return lst;
  }
}
