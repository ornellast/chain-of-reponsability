package com.ornellast.chainofresponsability.impl;

import java.util.Deque;

import com.ornellast.chainofresponsability.AbstractHandler;
import com.ornellast.chainofresponsability.Handler;
import com.ornellast.chainofresponsability.exception.InvalidOperationException;

public class TakerHandler extends AbstractHandler<Deque<Integer>> {

  public TakerHandler(Handler<Deque<Integer>> next) {
    super(next);
  }

  public TakerHandler() {
  }

  private static final String OPERATION = "-";

  @Override
  public void handle(Deque<Integer> stack, String operation) {

    assertNotNull(stack, operation);

    if (OPERATION.equalsIgnoreCase(operation)) {
      if (stack.size() < 2) {
        throw new InvalidOperationException("It is not possible to take only one number");
      }
      int top = stack.pop();
      int previous = stack.pop();
      stack.push(previous - top);
    } else if (hasNext()) {
      getNext().handle(stack, operation);
    }

  }

}
