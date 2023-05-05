package com.williamfiset.algorithms.datastructures.queue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;

public class IntQueueTest {

  @BeforeEach
  public void setup() {}

  @Test
  public void testEmptyQueue() {
    IntQueue queue = new IntQueue(0);
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
  }

  @Disabled("Doesn't apply to this implementation because of wrap")
  @Test
  public void testPollOnEmpty() {
    assertThrows(Exception.class, () -> {
      IntQueue queue = new IntQueue(0);
      queue.poll();
    });
  }

  @Disabled("Doesn't apply to this implementation because of wrap")
  @Test
  public void testPeekOnEmpty() {
    assertThrows(Exception.class, () -> {
      IntQueue queue = new IntQueue(0);
      queue.peek();
    });
  }

  @Test
  public void testofferOneElement() {
    IntQueue queue = new IntQueue(1);
    queue.offer(77);
    assertEquals(1, queue.size());
  }

  @Test
  public void testAll() {
    int n = 5;
    IntQueue queue = new IntQueue(10);
    assertTrue(queue.isEmpty());
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertFalse(queue.isEmpty());
    }
    for (int i = 1; i <= n; i++) {
      assertEquals(i, queue.peek());
      assertEquals(i, queue.poll());
      assertEquals(n - i, queue.size());
    }
    assertTrue(queue.isEmpty());
    n = 8;
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertFalse(queue.isEmpty());
    }
    for (int i = 1; i <= n; i++) {
      assertEquals(i, queue.peek());
      assertEquals(i, queue.poll());
      assertEquals(n - i, queue.size());
    }
    assertTrue(queue.isEmpty());
    n = 9;
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertFalse(queue.isEmpty());
    }
    for (int i = 1; i <= n; i++) {
      assertEquals(i, queue.peek());
      assertEquals(i, queue.poll());
      assertEquals(n - i, queue.size());
    }
    assertTrue(queue.isEmpty());
    n = 10;
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertFalse(queue.isEmpty());
    }
    for (int i = 1; i <= n; i++) {
      assertEquals(i, queue.peek());
      assertEquals(i, queue.poll());
      assertEquals(n - i, queue.size());
    }
    assertTrue(queue.isEmpty());
  }

  @Test
  public void testPeekOneElement() {
    IntQueue queue = new IntQueue(1);
    queue.offer(77);
    assertEquals(77, queue.peek());
    assertEquals(1, queue.size());
  }

  @Test
  public void testpollOneElement() {
    IntQueue queue = new IntQueue(1);
    queue.offer(77);
    assertEquals(77, queue.poll());
    assertEquals(0, queue.size());
  }

  @Test
  public void testRandom() {

    for (int qSize = 1; qSize <= 50; qSize++) {

      IntQueue intQ = new IntQueue(qSize);
      ArrayDeque<Integer> javaQ = new ArrayDeque<>(qSize);

      assertEquals(javaQ.isEmpty(), intQ.isEmpty());
      assertEquals(javaQ.size(), intQ.size());

      for (int operations = 0; operations < 5000; operations++) {

        double r = Math.random();

        if (r < 0.60) {
          int elem = (int) (1000 * Math.random());
          if (javaQ.size() < qSize) {
            javaQ.offer(elem);
            intQ.offer(elem);
          }
        } else {
          if (!javaQ.isEmpty()) {
            assertEquals(javaQ.poll(), intQ.poll());
          }
        }

        assertEquals(javaQ.isEmpty(), intQ.isEmpty());
        assertEquals(javaQ.size(), intQ.size());
      }
    }
  }
}
