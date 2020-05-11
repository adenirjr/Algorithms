package org.coursera.week2.assessment;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A randomized queue is similar to a stack or queue,
 * except that the item removed is chosen uniformly at random among items in the data structure.
 * Create a generic data type RandomizedQueue that implements the following API.
 * <p>
 * https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php
 *
 * @param <Item>
 * @Author Adenir Junior
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INITIAL_CAPACITY = 15;

    private Object[] items;

    private int elementCount;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = new Object[INITIAL_CAPACITY];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return elementCount == 0;
    }


    // return the number of items on the randomized queue
    public int size() {
        return elementCount;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (elementCount >= items.length) {
            doubleCapacity();
        }

        items[elementCount++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (elementCount == 0) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniform(0, elementCount);

        Item item = (Item) items[random];
        elementCount--;

        // Move last element to the position of removed element. Filling the blanks.
        if (random < elementCount) {
            items[random] = items[elementCount];
        }

        items[elementCount] = null;

        if (elementCount <= items.length / 4) {
            halveCapacity();
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (elementCount == 0) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniform(0, elementCount);
        return (Item) items[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Object[] shuffled = Arrays.copyOf(items, elementCount);
            int runner;

            @Override
            public boolean hasNext() {
                return runner < shuffled.length;
            }

            @Override
            public Item next() {
                if (runner == 0) {
                    StdRandom.shuffle(shuffled);
                }
                if (runner >= shuffled.length) {
                    throw new NoSuchElementException();
                }
                return (Item) shuffled[runner++];
            }
        };
    }

    private void doubleCapacity() {
        items = Arrays.copyOf(items, items.length * 2);
    }

    private void halveCapacity() {
        if(items.length <= INITIAL_CAPACITY) {
            return;
        }

        items = Arrays.copyOf(items, items.length / 2);
    }

    // unit testing (required)
    public static void main(String[] args) {
        final RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();

        rQueue.enqueue(1);
        rQueue.enqueue(2);
        rQueue.enqueue(3);
        rQueue.enqueue(4);
        rQueue.enqueue(5);
        rQueue.enqueue(6);
        rQueue.enqueue(7);
        rQueue.enqueue(8);
        rQueue.enqueue(9);
        rQueue.enqueue(10);
        rQueue.enqueue(11);
        rQueue.enqueue(12);
        rQueue.enqueue(13);

        System.out.println("Sample :" + rQueue.sample());

        System.out.println("Dequeue : " + rQueue.dequeue());
        System.out.println("Dequeue : " + rQueue.dequeue());
        System.out.println("Dequeue : " + rQueue.dequeue());
        System.out.println("Dequeue : " + rQueue.dequeue());
    }

}