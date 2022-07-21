package com.ornellast.chainofresponsability;

import java.util.Deque;

import com.ornellast.chainofresponsability.impl.AdderHandler;
import com.ornellast.chainofresponsability.impl.DupperHandler;
import com.ornellast.chainofresponsability.impl.PopperHandler;
import com.ornellast.chainofresponsability.impl.PusherHandler;
import com.ornellast.chainofresponsability.impl.TakerHandler;
import com.ornellast.chainofresponsability.impl.UnknowOperationHandler;

public class ProcessorsChain {
  private final Handler<Deque<Integer>> chain;

  public ProcessorsChain() {
    this.chain = new DupperHandler(
        new PopperHandler(
            new AdderHandler(
                new TakerHandler(
                    new PusherHandler(
                        new UnknowOperationHandler(null))))));
  }

  public void process(Deque<Integer> stack, String operation) {
    chain.handle(stack, operation);
  }

  public Handler<Deque<Integer>> getChain() {
    return chain;
  }

}
