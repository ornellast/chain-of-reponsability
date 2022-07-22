package com.ornellast.chainofresponsability;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

import com.ornellast.chainofresponsability.impl.PopperHandler;
import com.ornellast.chainofresponsability.impl.PusherHandler;
import com.ornellast.chainofresponsability.impl.UnknowOperationHandler;

public class ProcessorsChainTest {

  @Test
  public void should_UnknowOperationHandlerBeTheLastOnChain_when_buildWithDefaultHandlers() {
    // Given
    ProcessorsChain chain = ProcessorsChain.builder().buildWithDefaultHandlers();
    final Class<?> expected = UnknowOperationHandler.class;

    // When - get the last handler
    Handler<Deque<Integer>> actual = chain.getHead();
    while (actual.getNext() != null) {
      actual = actual.getNext();
    }

    // Then
    assertInstanceOf(expected, actual);

  }

  @Test
  public void should_TheLastOnChainSameTail_when_buildWithDefaultHandlers() {
    // Given
    ProcessorsChain chain = ProcessorsChain.builder().buildWithDefaultHandlers();
    final Handler<Deque<Integer>> expected = chain.getTail();

    // When - get the last handler
    Handler<Deque<Integer>> actual = chain.getHead();
    while (actual.getNext() != null) {
      actual = actual.getNext();
    }

    // Then
    assertSame(expected, actual);
  }

  @Test
  public void should_HeadSameTail_when_buildWithOneHandler() {
    // Given
    Handler<Deque<Integer>> handlerMock = mock(PusherHandler.class);
    ProcessorsChain chain = ProcessorsChain.builder().withHandler(handlerMock).build();
    final Handler<Deque<Integer>> expected = chain.getHead();

    // When - get the last handler
    final Handler<Deque<Integer>> actual = chain.getTail();

    // Then
    assertSame(expected, actual);
  }

  @Test
  public void should_callHeadHandle_when_proccesInvoked() {
    // Given
    Handler<Deque<Integer>> handlerMock = mock(PusherHandler.class);
    ProcessorsChain chain = ProcessorsChain.builder().withHandler(handlerMock).build();
    Deque<Integer> expectedStack = new ArrayDeque<>();
    String expectedOperation = "5";
    // When - get the last handler
    chain.process(expectedStack, expectedOperation);

    // Then
    verify(handlerMock, times(1)).handle(expectedStack, expectedOperation);
  }

  @Test
  public void should_neverHaveNext_on_chainTail() {
    // Given
    Handler<Deque<Integer>> handler = new PusherHandler(new PopperHandler());

    // When - get the last handler
    ProcessorsChain chainCreatedWithHandler = ProcessorsChain.builder().withHandler(handler).build();
    ProcessorsChain chainCreatedWithDefault = ProcessorsChain.builder().buildWithDefaultHandlers();

    // Then
    assertAll(
        () -> assertNull(chainCreatedWithDefault.getTail().getNext()),
        () -> assertNull(chainCreatedWithHandler.getTail().getNext()));
  }
}
