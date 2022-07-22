package com.ornellast;

import java.util.ArrayDeque;
import java.util.Deque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ornellast.chainofresponsability.ProcessorsChain;
import com.ornellast.chainofresponsability.exception.InvalidOperationException;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    // Run args: 6 5 DUP + POP 3 +
    // Result: 9
    public static void main(String[] args) {

        App app = new App();

        StringBuilder sb = new StringBuilder();

        for (String string : args) {
            sb.append(string + " ");
        }

        LOGGER.info("The value is {}",
                app.process(sb.toString()));
    }

    private ProcessorsChain processorsChain;

    public App() {
        processorsChain = ProcessorsChain.builder().buildWithDefaultHandlers();
    }

    public int process(String s) {

        if (s == null || s.trim().length() == 0) {
            return -1;
        }

        Deque<Integer> stack = new ArrayDeque<>();
        String[] elements = s.split(" ");
        for (String operation : elements) {
            try {
                processorsChain.process(stack, operation);
            } catch (InvalidOperationException e) {
                LOGGER.info(e.getMessage());
                return -1;
            }
        }

        return stack.isEmpty() ? -1 : stack.pop();
    }

}
