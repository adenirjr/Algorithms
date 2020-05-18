package org.coursera.week3.interview;

import com.sun.deploy.util.ArrayUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 *
 */
public class MergeSort {

    /**
     * 1. Merging with smaller auxiliary array.
     * Suppose that the subarray a[0] to a[n−1] is sorted and the
     * subarray a[n] to a[2*n-1] is sorted. How can you merge the two subarrays so that a[0]
     * to a[2*n-1] is sorted using an auxiliary array of length n (instead of 2n)?
     */
    private void merge(int[] arr, int[] aux, int init, int mid, int end) {

        // Copy first half of the array to aux
        for (int i = 0, j = init; j <= mid; i++, j++) {
            aux[i] = arr[j];
        }

        int auxIdx = 0;
        int left = init;
        int rightIdx = mid + 1;

        for (int i = init; i <= end; i++) {

            if (left > mid) {
                arr[i] = arr[rightIdx++];

            } else if (rightIdx > end) {
                arr[i] = aux[auxIdx++];
                left++;

            } else if (aux[auxIdx] < arr[rightIdx]) {
                arr[i] = aux[auxIdx++];
                left++;

            } else {
                arr[i] = arr[rightIdx++];
            }
        }
    }

    private void sort(int arr[], int[] aux, int init, int end) {
        int mid = (init + end) / 2;

        if (end <= init) {
            return;
        }

        sort(arr, aux, init, mid);
        sort(arr, aux, mid + 1, end);

        merge(arr, aux, init, mid, end);
    }

    /**
     * Merge sort using n/2 + 1 memory space.
     *
     * @param arr
     */
    public void mergeSort(int[] arr) {
        final int[] aux = new int[(arr.length / 2) + 1];
        sort(arr, aux, 0, arr.length - 1);
    }


    public static void main(String[] args) {
        final MergeSort mergeSort = new MergeSort();

        final int[] arr = new int[]{10, 9, 8, 7 , 6, 5, 4, 3 , 2};
        mergeSort.mergeSort(arr);

        Arrays.stream(arr).forEach(a -> System.out.println(a + " "));
    }

}
