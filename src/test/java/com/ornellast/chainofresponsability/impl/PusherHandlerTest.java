package com.ornellast.chainofresponsability.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
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

@ExtendWith(MockitoExtension.class)
public class PusherHandlerTest {

  private Handler<Deque<Integer>> handler;
  private Deque<Integer> stack;

  @Mock
  private Handler<Deque<Integer>> next;

  @BeforeEach
  public void instatiateDependencies() {
    this.handler = new PusherHandler(null);
    this.stack = new ArrayDeque<>();
  }

  @Test
  void should_addElement_when_NumericString() {

    // Given
    String value = "5";
    short expectedStackSize = 1;
    short expectedStackContent = 5;
    // When

    handler.handle(stack, value);

    // Then

    assertAll(
        () -> assertEquals(expectedStackSize, stack.size()),
        () -> assertEquals(expectedStackContent, stack.peek()));
  }

  @Test
  void should_stackBeEmpty_when_nonNumericString() {

    // Given
    String value = "c";
    short expectedStackSize = 0;
    Integer expectedStackContent = null;

    // When

    handler.handle(stack, value);

    // Then

    assertAll(
        () -> assertEquals(expectedStackSize, stack.size()),
        () -> assertEquals(expectedStackContent, stack.peek()));
  }

  @Test
  void should_throwNPE_when_eitherInputsIsNull() {

    // Given
    String value = "c";

    // When

    Executable nullStack = () -> handler.handle(null, value);
    Executable nullValue = () -> handler.handle(stack, null);
    Executable bothNull = () -> handler.handle(null, null);

    // Then

    assertAll(
        () -> assertThrows(NullPointerException.class, nullStack),
        () -> assertThrows(NullPointerException.class, nullValue),
        () -> assertThrows(NullPointerException.class, bothNull));
  }

  @Test
  void should_NOTcallNext_when_nextNotNull_and_validInput() {

    // Given
    String value = "5";
    this.handler.setNext(this.next);

    // When
    this.handler.handle(stack, value);

    // Then
    verify(this.next, times(0)).handle(stack, value);

  }

  @Test
  void should_callNext_when_nextNotNull_and_invalidInput() {

    // Given
    String value = "c";
    this.handler.setNext(this.next);

    // When
    this.handler.handle(stack, value);

    // Then
    verify(this.next, times(1)).handle(stack, value);

  }
}
