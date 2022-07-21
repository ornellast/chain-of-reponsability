package com.ornellast.chainofresponsability.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ornellast.chainofresponsability.Handler;
import com.ornellast.chainofresponsability.exception.InvalidOperationException;

@ExtendWith(MockitoExtension.class)
public class DupperHandlerTest {

  private static final String OPERATION = "DUP";

  private Handler<Deque<Integer>> handler;
  private Deque<Integer> stack;

  @Mock
  private Handler<Deque<Integer>> next;

  @BeforeEach
  public void instatiateDependencies() {
    this.handler = new DupperHandler(null);
    this.stack = new ArrayDeque<>();
  }

  @Test
  public void should_repeatLastAdded_when_inputOk() {
    // Given
    String operation = OPERATION;
    this.stack.push(1);
    this.stack.push(2);
    int expected = 2;

    // When
    handler.handle(stack, operation);

    // Then
    assertEquals(expected, stack.peek());
  }

  @Test
  public void should_haveSizeThree_when_finishedOperation() {
    // Given
    String operation = OPERATION;
    this.stack.push(5);
    this.stack.push(3);
    Integer expected = 3;

    // When
    handler.handle(stack, operation);

    // Then
    assertEquals(expected, stack.size());
  }

  @Test
  void should_throwIOE_when_emptyStack() {

    // Given
    String operation = OPERATION;

    // When
    Executable executable = () -> handler.handle(stack, operation);

    // Then
    assertThrows(InvalidOperationException.class, executable);

  }

  @Test
  void should_notThrowIOE_when_stakOneElment() {

    // Given
    String operation = OPERATION;
    this.stack.push(5);

    // When
    Executable executable = () -> handler.handle(stack, operation);

    // Then
    assertDoesNotThrow(executable);

  }

  @Test
  void should_notCallNext_when_nextNotNull_and_validInput() {

    // Given
    String input = OPERATION;
    this.stack.push(5);
    this.stack.push(3);
    this.handler.setNext(this.next);

    // When
    this.handler.handle(stack, input);

    // Then
    verify(this.next, times(0)).handle(stack, input);

  }

  @Test
  void should_callNext_when_nextNotNull_and_invalidInput() {

    // Given
    String input = "-";
    this.handler.setNext(this.next);

    // When
    this.handler.handle(stack, input);

    // Then
    verify(this.next, times(1)).handle(stack, input);

  }
}
