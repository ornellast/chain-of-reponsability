package com.ornellast.chainofresponsability.impl;

import java.util.Deque;

import com.ornellast.chainofresponsability.AbstractHandler;
import com.ornellast.chainofresponsability.Handler;
import com.ornellast.chainofresponsability.exception.InvalidOperationException;

public class PopperHandler extends AbstractHandler<Deque<Integer>> {

  public PopperHandler(Handler<Deque<Integer>> next) {
    super(next);
  }

  public PopperHandler() {
    super();
  }

  private static final String OPERATION = "POP";

  @Override
  public void handle(Deque<Integer> stack, String operation) {

    assertNotNull(stack, operation);

    if (OPERATION.equalsIgnoreCase(operation)) {
      if (stack.isEmpty()) {
        throw new InvalidOperationException("It is not possible to pop an empty stack.");
      }
      stack.pop();
    } else if (hasNext()) {
      getNext().handle(stack, operation);
    }

  }

}
