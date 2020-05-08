package org.coursera.week2.interview;

import java.util.Iterator;
import java.util.Queue;
import java.util.stream.IntStream;

public class TwoStacksQueueTest {

    // FIFO
    private static void assertFirstElementsAddedAreTheFirstRemoved() {
        final Queue<Integer> queue = new TwoStacksQueue<>();

        IntStream.range(0, 7).forEach(queue::offer);

        int value = queue.peek();
        assert value == 0;

        value = queue.poll();
        assert value == 0;

        value = queue.poll();
        assert value == 1;

        queue.poll();
        queue.poll();
        queue.poll();

        value = queue.poll();
        assert value == 5;
    }

    private static void assertQueueSize() {
        final Queue<Integer> queue = new TwoStacksQueue<>();

        IntStream.range(0, 32).forEach(queue::offer); // Larger than ArrayDeque initial capacity (16)

        int size = queue.size(); //Checks input stack only. Output stack not used yet.
        assert size == 32;

        queue.peek(); // Force output stack to be used. Input stack is empty now.
        queue.offer(32); // Add to input stack
        queue.offer(33); // Add to input stack

        size = queue.size();
        assert size == 34;
    }

    private static void assertIterationOrder() {
        final Queue<Integer> queue = new TwoStacksQueue<>();
        int counter = 0;

        IntStream.range(0, 10).forEach(queue::offer); // Input stack only

        final Iterator<Integer> itr = queue.iterator();
        while(itr.hasNext()) {
            int item = itr.next();
            assert counter++ == item;

            System.out.print(item);
            System.out.print(" ");
        }

        queue.peek(); // Force output stack to be used. Input stack is empty now.
        queue.offer(10); // Add to input stack
        queue.offer(11); // Add to input stack

        System.out.println();
        counter = 0;

        final Iterator<Integer> itr2 = queue.iterator();
        while(itr2.hasNext()) {
            int item = itr2.next();
            assert counter++ == item;

            System.out.print(item);
            System.out.print(" ");
        }
    }

    public static void main(String... args) {

        assertFirstElementsAddedAreTheFirstRemoved();
        assertQueueSize();
        assertIterationOrder();
    }
}
