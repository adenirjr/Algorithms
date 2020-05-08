package org.coursera.week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
 *
 * Write a program to estimate the value of the percolation threshold via Monte Carlo simulation.
 *
 * @author Adenir Junior
 */
public class Percolation {

    private final WeightedQuickUnionUF unionFind;
    private final boolean[][] modelGrid; // false == Closed; true == Open;
    private final int virtualTopSite;
    private final int virtualBottomSite;
    private int openSites; // Total of open sites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N can not be less of equal to zero");
        }

        unionFind = new WeightedQuickUnionUF((n * n) + 2);
        modelGrid = new boolean[n][n];
        virtualTopSite = (n * n);
        virtualBottomSite = (n * n) + 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                modelGrid[i][j] = false; // Initializing whole grid blocked
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        int rowIndex = row - 1;
        int colIndex = col - 1;

        final SiteHelper siteHelper = new SiteHelper(rowIndex, colIndex, modelGrid.length);
        modelGrid[rowIndex][colIndex] = true;
        openSites++;

        // Top
        if (rowIndex > 0) {
            if (isOpen(row - 1, col)) {
                unionFind.union(siteHelper.getSite(), siteHelper.getTopNeighbor());
            }
        } else {
            unionFind.union(virtualTopSite, siteHelper.getSite());
        }

        // Right
        if (colIndex < modelGrid.length - 1) {
            if (isOpen(row, col + 1)) {
                unionFind.union(siteHelper.getSite(), siteHelper.getRightNeighbor());
            }
        }

        // Bottom
        if (rowIndex < modelGrid.length - 1) {
            if (isOpen(row + 1, col)) {
                unionFind.union(siteHelper.getSite(), siteHelper.getBottomNeighbor());
            }
        } else {
            unionFind.union(virtualBottomSite, siteHelper.getSite());
        }

        // Left
        if (colIndex > 0) {
            if (isOpen(row, col - 1)) {
                unionFind.union(siteHelper.getSite(), siteHelper.getLeftNeighbor());
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return modelGrid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        int rowIndex = row - 1;
        int colIndex = col - 1;

        final SiteHelper siteHelper = new SiteHelper(rowIndex, colIndex, modelGrid.length);

        return unionFind.find(siteHelper.getSite()) == unionFind.find(virtualTopSite);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(virtualTopSite) == unionFind.find(virtualBottomSite);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > modelGrid.length) {
            throw new IllegalArgumentException("Row number is out of range");
        }

        if (col < 1 || col > modelGrid[0].length) {
            throw new IllegalArgumentException("Column number is out of range");
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("Starting Percolation");

        final Percolation percolation = new Percolation(10);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);
        percolation.open(5, 1);
        percolation.open(6, 1);
        percolation.open(7, 1);
        percolation.open(8, 1);
        percolation.open(9, 1);
        percolation.open(10, 1);
        percolation.open(10, 4); // Doesn't pass backwash test

        final SiteHelper siteHelper = percolation.new SiteHelper(3, 3, 5);
        System.out.println("Site Number: " + siteHelper.getSite());
        System.out.println("Site Top Neighbor: " + siteHelper.getTopNeighbor());
        System.out.println("Site Right Neighbor: " + siteHelper.getRightNeighbor());
        System.out.println("Site Bottom Neighbor: " + siteHelper.getBottomNeighbor());
        System.out.println("Site Left Neighbor: " + siteHelper.getLeftNeighbor());

        System.out.println("Is (1,1) open ? " + percolation.isOpen(1, 1));
        System.out.println("Is (1,2) open ? " + percolation.isOpen(1, 2));

        System.out.println("Is (1,1) full ? " + percolation.isFull(1, 1));
        System.out.println("Is (2,1) full ? " + percolation.isFull(2, 1));
        System.out.println("Is (3,1) full ? " + percolation.isFull(3, 1));
        System.out.println("Is (4,1) full ? " + percolation.isFull(4, 1));
        System.out.println("Is (7,1) full ? " + percolation.isFull(7, 1));
        System.out.println("Is (8,1) full ? " + percolation.isFull(8, 1));
        System.out.println("Is (9,1) full ? " + percolation.isFull(9, 1));
        System.out.println("Is (10,1) full ? " + percolation.isFull(10, 1));
        System.out.println("Is (10,4) full ? " + percolation.isFull(10, 4));

        System.out.println("------------------------------");
        percolation.printModel();
        System.out.println("------------------------------");

        System.out.println("Does system percolates ? " + percolation.percolates());

        System.out.println("End Percolation");
    }

    private void printModel() {
        for (int i = 0; i < modelGrid.length; i++) {
            for (int j = 0; j < modelGrid[i].length; j++) {
                if (j < modelGrid[i].length - 1) {
                    System.out.print(modelGrid[i][j] + " ");
                } else {
                    System.out.println(modelGrid[i][j]);
                }
            }
        }
    }

    /**
     * With X and Y starting from 0;
     */
    private class SiteHelper {
        private final int coordinateX;
        private final int coordinateY;
        private final int gridSize;

        public SiteHelper(int row, int col, int length) {
            coordinateX = row;
            coordinateY = col;
            gridSize = length;
        }

        public int getSite() {
            return (gridSize * coordinateX) + coordinateY;
        }

        public int getTopNeighbor() {
            return (gridSize * (coordinateX - 1)) + coordinateY;
        }

        public int getRightNeighbor() {
            return (gridSize * coordinateX) + coordinateY + 1;
        }

        public int getBottomNeighbor() {
            return (gridSize * (coordinateX + 1)) + coordinateY;
        }

        public int getLeftNeighbor() {
            return (gridSize * coordinateX) + coordinateY - 1;
        }
    }
}
