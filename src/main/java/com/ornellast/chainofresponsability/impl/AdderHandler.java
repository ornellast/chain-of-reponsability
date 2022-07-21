package com.ornellast.chainofresponsability.impl;

import java.util.Deque;

import com.ornellast.chainofresponsability.AbstractHandler;
import com.ornellast.chainofresponsability.Handler;
import com.ornellast.chainofresponsability.exception.InvalidOperationException;

public class AdderHandler extends AbstractHandler<Deque<Integer>> {

  public AdderHandler(Handler<Deque<Integer>> next) {
    super(next);
  }

  private static final String OPERATION = "+";

  @Override
  public void handle(Deque<Integer> stack, String operation) {

    assertNotNull(stack, operation);

    if (OPERATION.equalsIgnoreCase(operation)) {
      if(stack.size() < 2) {
        throw new InvalidOperationException("It is not possible to add only one number");
      }
      int top = stack.pop();
      int previous = stack.pop();
      stack.push(top + previous);
    } else if (hasNext()) {
      getNext().handle(stack, operation);
    }

  }

}
