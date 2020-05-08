package org.coursera.week2.interview;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Stack with max.
 * Create a data structure that efficiently supports the stack operations (push and pop)
 * and also a return-the-maximum operation.
 * Assume the elements are real numbers so that you can compare them.
 *
 * @author Adenir Junior
 */
public class StackMax {

    private final Deque<Integer> mainStack = new ArrayDeque<>();
    private final Deque<Integer> maxStack = new ArrayDeque<>();

    public void push(final Integer item) {
        mainStack.offerFirst(item);

        if(maxStack.isEmpty()) {
            maxStack.offerFirst(item);
        } else {
            if(item >= maxStack.peekFirst()) {
                maxStack.offerFirst(item);
            }
        }
    }

    public Integer pop() {
        Integer item = mainStack.removeFirst();

        if(item.equals(maxStack.peekFirst())) {
            maxStack.removeFirst();
        }

        return item;
    }

    public Integer getMax() {
        return maxStack.peekFirst();
    }
}
