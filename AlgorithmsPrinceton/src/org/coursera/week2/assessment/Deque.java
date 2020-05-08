package org.coursera.week2.assessment;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Write a generic data type for a deque and a randomized queue.
 * The goal of this assignment is to implement elementary data structures using arrays and linked lists,
 * and to introduce you to generics and iterators.
 *
 * Your deque implementation must support each deque operation (including construction)
 * in constant worst-case time. A deque containing n items must use at most 48n + 192 bytes of memory.
 * Additionally, your iterator implementation must support each operation
 * (including construction) in constant worst-case time.
 *
 *
 * https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php
 *
 * @author Adenir Junior
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    // 16 bytes overhead
    // 8 bytes pointer to inner class

    private Node head; // 8 bytes
    private Node tail; // 8 bytes

    private int size; // 4 bytes

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        final Node<Item> first = head;
        final Node<Item> newNode = new Node(item, first, null);

        head = newNode;

        if (first == null) {
            tail = newNode;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        final Node<Item> last = tail;
        final Node<Item> newNode = new Node(item, null, last);

        tail = newNode;

        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }

        final Node<Item> first = head;
        head = first.next;

        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }

        size--;

        return first.content;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }

        final Node<Item> last = tail;
        tail = last.prev;

        if (tail == null) {
            head = null;
        }

        size--;

        return last.content;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private Node<Item> pointer = head;

            @Override
            public boolean hasNext() {
                return pointer != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                final Node<Item> node = pointer;
                pointer = node.next;

                return node.content;
            }
        };
    }

    /**
     * Memory Usage:
     * Overhead = 16 bytes
     * content ref = 8 bytes
     * next = 8 bytes
     * prev = 8 bytes
     *
     * 40 bytes each Node
     *
     * @param <Item>
     */
    private static class Node<Item> {
        Item content;
        Node<Item> next;
        Node<Item> prev;

        public Node(final Item item, final Node<Item> next, final Node<Item> prev) {
            this.content = item;
            this.next = next;
            this.prev = prev;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        final Deque<Integer> stack = new Deque<>();

        System.out.println("Stack Test: ");
        stack.addFirst(1);
        System.out.println(stack.removeFirst());

        stack.addFirst(2);
        stack.addFirst(3);
        stack.addFirst(4);

        System.out.println(stack.removeFirst());
        System.out.println(stack.removeFirst());
        System.out.println(stack.removeFirst());

        System.out.println("Stack should be empty by now: " + stack.isEmpty());

        final Deque<Integer> queue = new Deque<>();

        System.out.println("Queue Test (FIFO): ");
        queue.addLast(1);
        System.out.println(queue.removeLast());

        queue.addLast(2);
        queue.addLast(3);
        queue.addLast(4);

        System.out.println(queue.removeFirst());
        System.out.println(queue.removeFirst());
        System.out.println(queue.removeFirst());

        System.out.println("Queue should be empty by now: " + queue.isEmpty());

        System.out.println("Iterate Queue");
        queue.addLast(50);
        queue.addLast(51);
        queue.addLast(52);
        queue.addLast(53);
        queue.addLast(54);
        queue.addLast(55);
        queue.iterator().forEachRemaining(System.out::println);
    }
}
