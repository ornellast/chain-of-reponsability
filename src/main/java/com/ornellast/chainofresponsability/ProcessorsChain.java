package com.ornellast.chainofresponsability;

import java.util.Deque;

import com.ornellast.chainofresponsability.impl.AdderHandler;
import com.ornellast.chainofresponsability.impl.DupperHandler;
import com.ornellast.chainofresponsability.impl.PopperHandler;
import com.ornellast.chainofresponsability.impl.PusherHandler;
import com.ornellast.chainofresponsability.impl.TakerHandler;
import com.ornellast.chainofresponsability.impl.UnknowOperationHandler;

public class ProcessorsChain {

  public static class ProcessorsChainBuilder {

    private Handler<Deque<Integer>> head;
    private Handler<Deque<Integer>> tail;

    public ProcessorsChain buildWithDefaultHandlers() {
      return this
          .withHandler(new DupperHandler())
          .withHandler(new PopperHandler())
          .withHandler(new AdderHandler())
          .withHandler(new TakerHandler())
          .withHandler(new PusherHandler())
          .withHandler(new UnknowOperationHandler())
          .build();
    }

    public ProcessorsChainBuilder withHandler(Handler<Deque<Integer>> handler) {

      if (head == null) {
        head = handler;
      } else {
        tail.setNext(handler);
      }

      tail = handler;
      tail.setNext(null);

      return this;
    }

    public ProcessorsChain build() {
      return new ProcessorsChain(head, tail);
    }

  }

  private Handler<Deque<Integer>> head;
  private Handler<Deque<Integer>> tail;

  private ProcessorsChain(Handler<Deque<Integer>> head, Handler<Deque<Integer>> tail) {
    this.head = head;
    this.tail = tail;
  }

  public static ProcessorsChainBuilder builder() {
    return new ProcessorsChainBuilder();
  }

  public void process(Deque<Integer> stack, String operation) {
    head.handle(stack, operation);
  }

  public Handler<Deque<Integer>> getHead() {
    return head;
  }

  public Handler<Deque<Integer>> getTail() {
    return tail;
  }

}
