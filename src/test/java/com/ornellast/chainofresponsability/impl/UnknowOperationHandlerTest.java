package com.ornellast.chainofresponsability.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.ornellast.chainofresponsability.Handler;
import com.ornellast.chainofresponsability.exception.InvalidOperationException;

public class UnknowOperationHandlerTest {
  private Handler<Deque<Integer>> handler;
  private Deque<Integer> stack;

  @BeforeEach
  public void instatiateDependencies() {
    this.handler = new UnknowOperationHandler();
    this.stack = new ArrayDeque<>();
  }

  @Test
  public void should_neverHaveNext_when_constructed() {
    // Given

    // New Hanlder
    UnknowOperationHandler localHandler = new UnknowOperationHandler(new AdderHandler());

    // When

    // Then
    assertNull(localHandler.getNext());
  }

  @Test
  public void should_neverHaveNext_when_setNext() {
    // Given

    // default handler

    // When
    handler.setNext(new AdderHandler());

    // Then
    assertNull(handler.getNext());
  }

  @Test
  public void should_throwInvalidOperationException_when_handle() {
    // Given

    // default handler

    // When
    Executable executable = () -> handler.handle(stack, "POP");

    // Then
    assertThrows(InvalidOperationException.class, executable);
  }
}
