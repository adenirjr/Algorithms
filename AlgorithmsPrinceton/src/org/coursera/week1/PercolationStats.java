package org.coursera.week1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] fractionOpenSites;
    private double mean = 0;
    private double stddev = 0;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        fractionOpenSites = new double[trials];

        for (int i = 0; i < trials; i++) {
            final Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }

            fractionOpenSites[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean == 0) {
            mean = StdStats.mean(fractionOpenSites);
        }
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev == 0) {
            stddev = StdStats.stddev(fractionOpenSites);
        }
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(fractionOpenSites.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(fractionOpenSites.length));
    }

    // test client (see below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        StdOut.println("Your n was: " + n);
        StdOut.println();
//
        StdOut.println("Your T was: " + t);
        StdOut.println();

        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("Input can not be lower or equal do zero");
        }

        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + " , " + stats.confidenceHi() + "]");
    }
}
