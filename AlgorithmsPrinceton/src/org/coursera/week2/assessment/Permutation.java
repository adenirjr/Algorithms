package org.coursera.week2.assessment;

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

/**
 * Write a client program Permutation.java that takes an integer k as a command-line argument;
 * reads a sequence of strings from standard input using StdIn.readString();
 * and prints exactly k of them, uniformly at random. Print each item from the sequence at most once.
 * <p>
 * https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php
 */
public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            rQueue.enqueue(StdIn.readString());
        }

        final Iterator<String> itr = rQueue.iterator();
        while (k > 0 && itr.hasNext()) {
            System.out.println(itr.next());
            k--;
        }
    }
}
