package org.coursera.week2.interview;


import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * TwoStacksQueue with two stacks.
 * Implement a queue with two stacks so that each queue operations
 * takes a constant amortized number of stack operations.
 *
 * Add operation run in amortized constant time. Poll and Peek run in linear time
 * in worst case since eventually all elements from one stack needs to move to the other.
 *
 * @author Adenir Junior
 */
public class TwoStacksQueue<E> extends AbstractQueue<E> {

    private final Deque<E> inputStack = new ArrayDeque<E>(); // Implemented as Stack
    private final Deque<E> outputStack = new ArrayDeque<E>(); // Implemented as Stack

    @Override
    public int size() {
        return inputStack.size() + outputStack.size();
    }

    @Override
    public boolean offer(E e) {
        return inputStack.offerFirst(e);
    }

    @Override
    public E poll() {
        if (peek() != null) {
            return outputStack.removeFirst();
        }
        return null;
    }

    @Override
    public E peek() {
        transferData();
        if (outputStack.isEmpty()) {
            return null;
        }

        return outputStack.peekFirst();
    }

    private void transferData() {
        if (outputStack.isEmpty()) {
            while (!inputStack.isEmpty()) {
                outputStack.addFirst(inputStack.removeFirst());
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private final class Itr implements Iterator<E> {

        private final Iterator<E> inputStackIter = inputStack.descendingIterator();
        private final Iterator<E> outputStackIter = outputStack.iterator();

        @Override
        public boolean hasNext() {
            return outputStackIter.hasNext() ? true : inputStackIter.hasNext();
        }

        @Override
        public E next() {
            return outputStackIter.hasNext() ? outputStackIter.next() : inputStackIter.next();
        }
    }
}
