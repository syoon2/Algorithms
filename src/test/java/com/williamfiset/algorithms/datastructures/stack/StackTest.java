package com.williamfiset.algorithms.datastructures.stack;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class StackTest {

  private static List<Stack<Integer>> inputs() {
    List<Stack<Integer>> stacks = new ArrayList<>();
    stacks.add(new ListStack<Integer>());
    stacks.add(new ArrayStack<Integer>());
    stacks.add(new IntStack(2));
    return stacks;
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testEmptyStack(Stack<Integer> stack) {
    assertTrue(stack.isEmpty());
    assertEquals(0, stack.size());
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPopOnEmpty(Stack<Integer> stack) {
    assertThrows(Exception.class, () -> stack.pop());
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPeekOnEmpty(Stack<Integer> stack) {
    assertThrows(Exception.class, () -> stack.peek());
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPush(Stack<Integer> stack) {
    stack.push(2);
    assertEquals(1, stack.size());
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPeek(Stack<Integer> stack) {
    stack.push(2);
    assertEquals(2, (Integer) stack.peek());
    assertEquals(1, stack.size());
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPop(Stack<Integer> stack) {
    stack.push(2);
    assertEquals(2, stack.pop());
    assertEquals(0, stack.size());
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testExhaustively(Stack<Integer> stack) {
    assertTrue(stack.isEmpty());
    stack.push(1);
    assertFalse(stack.isEmpty());
    stack.push(2);
    assertEquals(2, stack.size());
    assertEquals(2, stack.peek());
    assertEquals(2, stack.size());
    assertEquals(2, stack.pop());
    assertEquals(1, stack.size());
    assertEquals(1, stack.peek());
    assertEquals(1, stack.size());
    assertEquals(1, stack.pop());
    assertEquals(0, stack.size());
    assertTrue(stack.isEmpty());
  }
}
