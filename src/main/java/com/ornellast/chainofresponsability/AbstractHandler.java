package com.ornellast.chainofresponsability;

import java.util.Objects;

public abstract class AbstractHandler<T> implements Handler<T> {

  private Handler<T> next;

  protected AbstractHandler(Handler<T> next) {
    this.next = next;
  }

  protected AbstractHandler() {
    super();
  }

  @Override
  public Handler<T> getNext() {
    return this.next;
  }

  @Override
  public void setNext(Handler<T> next) {
    this.next = next;
  }

  protected void assertNotNull(T stack, String operation) {
    Objects.requireNonNull(stack);
    Objects.requireNonNull(operation);
  }

}
