package com.ornellast.chainofresponsability;

public interface Handler<T> {
  
  void handle(T stack, String operation);

  void setNext(Handler<T> next);

  Handler<T> getNext();

  default boolean hasNext() {
    return getNext() != null;
  }
}
