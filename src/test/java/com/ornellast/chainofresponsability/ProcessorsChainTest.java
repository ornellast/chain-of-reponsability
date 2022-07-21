package com.ornellast.chainofresponsability;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Deque;

import org.junit.jupiter.api.Test;

import com.ornellast.chainofresponsability.impl.AdderHandler;
import com.ornellast.chainofresponsability.impl.DupperHandler;
import com.ornellast.chainofresponsability.impl.PopperHandler;
import com.ornellast.chainofresponsability.impl.PusherHandler;
import com.ornellast.chainofresponsability.impl.TakerHandler;
import com.ornellast.chainofresponsability.impl.UnknowOperationHandler;


public class ProcessorsChainTest {

  @Test
  public void should_HandlerBeRightOrder_when_created() {
    // Given
    ProcessorsChain chain = new ProcessorsChain();
    final Class<?>[] expected = { DupperHandler.class, PopperHandler.class, AdderHandler.class, TakerHandler.class,
        PusherHandler.class, UnknowOperationHandler.class };

    // When
    Class<?>[] actual = new Class[expected.length];
    Handler<Deque<Integer>> next = chain.getChain();
    int idx = 0;
    while (next != null) {
      actual[idx++] = next.getClass();
      next = next.getNext();
    }

    // Then

    assertArrayEquals(expected, actual);

  }
}
