package com.ornellast.chainofresponsability;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

import com.ornellast.chainofresponsability.impl.AdderHandler;
import com.ornellast.chainofresponsability.impl.DupperHandler;
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

    // When 
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

    // When 
    chain.process(expectedStack, expectedOperation);

    // Then
    verify(handlerMock, times(1)).handle(expectedStack, expectedOperation);
  }

  @Test
  public void should_neverHaveNext_on_chainTail() {
    // Given
    Handler<Deque<Integer>> handler = new PusherHandler(new PopperHandler());

    // When 
    ProcessorsChain chainCreatedWithHandler = ProcessorsChain.builder().withHandler(handler).build();
    ProcessorsChain chainCreatedWithDefault = ProcessorsChain.builder().buildWithDefaultHandlers();

    // Then
    assertAll(
        () -> assertNull(chainCreatedWithDefault.getTail().getNext()),
        () -> assertNull(chainCreatedWithHandler.getTail().getNext()));
  }

  @Test
  public void should_handleTailCorrectly_when_chainAddedToWithHandler() {
    // Given
    Handler<Deque<Integer>> expectedTail = new PopperHandler();
    Handler<Deque<Integer>> adder = new AdderHandler(expectedTail);
    Handler<Deque<Integer>> dupper = new DupperHandler(adder);
    Handler<Deque<Integer>> head = new PusherHandler();

    // When 
    ProcessorsChain chain = ProcessorsChain.builder()
        .withHandler(head)
        .withHandler(dupper)
        .build();

    // Then
    assertSame(expectedTail, chain.getTail());
  }

  @Test
  public void should_createChainRightOrder_when_handlersAddedToChain() {
    // Given
    Handler<Deque<Integer>> popper = new PopperHandler();
    Handler<Deque<Integer>> adder = new AdderHandler(popper);
    Handler<Deque<Integer>> dupper = new DupperHandler();
    Handler<Deque<Integer>> head = new PusherHandler(dupper);

    Class<?>[] expectedOrder = { PusherHandler.class, DupperHandler.class, AdderHandler.class, PopperHandler.class };

    // When 
    ProcessorsChain chain = ProcessorsChain.builder()
        .withHandler(head)
        .withHandler(adder)
        .build();

    short index = 0;
    Class<?>[] actualOrder = new Class[expectedOrder.length];
    Handler<Deque<Integer>> handler = chain.getHead();

    actualOrder[index++] = handler.getClass();
    while (handler.hasNext()) {
      handler = handler.getNext();
      actualOrder[index++] = handler.getClass();
    }

    // Then
    assertArrayEquals(expectedOrder, actualOrder);
  }
}
