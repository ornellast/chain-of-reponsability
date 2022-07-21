package com.ornellast.chainofresponsability.impl;

import java.util.Deque;

import com.ornellast.chainofresponsability.AbstractHandler;
import com.ornellast.chainofresponsability.Handler;
import com.ornellast.chainofresponsability.exception.InvalidOperationException;

public class DupperHandler extends AbstractHandler<Deque<Integer>> {

  public DupperHandler(Handler<Deque<Integer>> next) {
    super(next);
  }

  private static final String OPERATION = "DUP";

  @Override
  public void handle(Deque<Integer> stack, String operation) {

    assertNotNull(stack, operation);

    if (OPERATION.equalsIgnoreCase(operation)) {

      if(stack.isEmpty()) {
        throw new InvalidOperationException("It is not possible to duplicate. The stack is empty.");
      }
      
      stack.push(stack.peek());
    } else if (hasNext()) {
      getNext().handle(stack, operation);
    }

  }

}
