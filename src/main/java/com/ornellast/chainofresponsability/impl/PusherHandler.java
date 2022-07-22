package com.ornellast.chainofresponsability.impl;

import java.util.Deque;

import com.ornellast.chainofresponsability.AbstractHandler;
import com.ornellast.chainofresponsability.Handler;

public class PusherHandler extends AbstractHandler<Deque<Integer>> {

  public PusherHandler(Handler<Deque<Integer>> next) {
    super(next);
  }

  public PusherHandler() {
  }

  @Override
  public void handle(Deque<Integer> stack, String opertation) {

    assertNotNull(stack, opertation);
    try {
      stack.push(Integer.parseInt(opertation));
    } catch (NumberFormatException e) {
      if (hasNext()) {
        getNext().handle(stack, opertation);
      }
    }

  }

}
