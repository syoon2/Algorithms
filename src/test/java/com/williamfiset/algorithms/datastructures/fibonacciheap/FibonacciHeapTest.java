package com.williamfiset.algorithms.datastructures.fibonacciheap;

import static java.util.Collections.sort;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

import org.junit.jupiter.api.*;

// Disclaimer: Based by help of
// "http://langrsoft.com/jeff/2011/11/test-driving-a-heap-based-priority-queue/">Test-Driving a
// Heap-Based Priority Queue</a>
// Credits to the respecti owner for code

public final class FibonacciHeapTest {

  private Queue<Integer> queue;

  @BeforeEach
  public void setUp() {
    queue = new FibonacciHeap<Integer>();
  }

  @AfterEach
  public void tearDown() {
    queue = null;
  }

  @Test
  public void emptyWhenCreated() {
    assertTrue(queue.isEmpty());
    assertNull(queue.poll());
  }

  @Test
  public void noLongerEmptyAfterAdd() {
    queue.add(50);

    assertFalse(queue.isEmpty());
  }

  @Test
  public void singletonQueueReturnsSoleItemOnPoll() {
    queue.add(50);

    assertEquals(50, queue.poll());
  }

  @Test
  public void isEmptyAfterSoleElementRemoved() {
    queue.add(50);
    queue.poll();

    assertTrue(queue.isEmpty());
  }

  @Test
  public void returnsOrderedItems() {
    queue.add(100);
    queue.add(50);

    assertEquals(50, queue.poll());
    assertEquals(100, queue.poll());
    assertTrue(queue.isEmpty());
  }

  @Test
  public void insertSingleItem() {
    queue.add(50);

    assertEquals(50, queue.poll());
    assertTrue(queue.isEmpty());
  }

  @Test
  public void insertSameValuesAndReturnsOrderedItems() {
    queue.add(50);
    queue.add(100);
    queue.add(50);

    assertEquals(50, queue.poll());
    assertEquals(50, queue.poll());
    assertEquals(100, queue.poll());
    assertTrue(queue.isEmpty());
  }

  @Test
  public void returnsOrderedItemsFromRandomInsert() {
    final Random r = new Random(System.currentTimeMillis());
    final List<Integer> expected = new ArrayList<Integer>();

    for (int i = 0; i < 1000; i++) {
      Integer number = r.nextInt(10000);
      expected.add(number);
      queue.add(number);
    }
    sort(expected);

    for (Integer integer : expected) {
      Integer i = queue.poll();
      assertEquals(integer, i);
    }

    assertTrue(queue.isEmpty());
  }

  @Test
  public void addAllAndContinsItem() {
    Collection<Integer> c = new ArrayList<Integer>();

    c.add(50);
    c.add(100);
    c.add(20);
    c.add(21);

    queue.addAll(c);

    assertFalse(queue.isEmpty());
    assertTrue(queue.containsAll(c));

    assertTrue(queue.contains(100));
    assertTrue(queue.contains(21));
    assertTrue(queue.contains(50));
    assertTrue(queue.contains(20));
  }

  @Test
  public void clearQueue() {
    final Random r = new Random(System.currentTimeMillis());
    for (int i = 0; i < 1000; i++) {
      Integer number = r.nextInt(10000);
      queue.add(number);
    }

    assertFalse(queue.isEmpty());
    queue.clear();
    assertTrue(queue.isEmpty());
  }

  @Test
  public void offerPeekAndElement() {
    queue.offer(50);
    queue.offer(100);
    queue.offer(20);
    queue.offer(21);

    assertFalse(queue.isEmpty());
    ;
    assertEquals(20, queue.peek());
    assertEquals(20, queue.element());
    assertEquals(4, queue.size());
  }

  @Test
  public void elementThrowsException() {
    assertThrows(NoSuchElementException.class, () -> queue.element());
  }
}
