package com.ornellast.chainofresponsability.impl;

import java.util.Deque;

import com.ornellast.chainofresponsability.AbstractHandler;
import com.ornellast.chainofresponsability.Handler;
import com.ornellast.chainofresponsability.exception.InvalidOperationException;

public final class UnknowOperationHandler extends AbstractHandler<Deque<Integer>> {

  public UnknowOperationHandler(Handler<Deque<Integer>> next) {
    super(null);
  }

  public UnknowOperationHandler() {
  }

  @Override
  public void handle(Deque<Integer> stack, String operation) {
    throw new InvalidOperationException(String.format("Operation not recognized: %s", operation));

  }

  @Override
  public void setNext(Handler<Deque<Integer>> next) {
    super.setNext(null);
  }

}
