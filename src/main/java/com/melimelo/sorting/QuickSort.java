package com.melimelo.sorting;

import com.melimelo.arrays.ArraysUtils;

/**
 * QuickSort algorithm implementation.
 */
public class QuickSort {

    public static enum PartitionAlgorithm {
        LOMUTO, HOARE
    }

    /**
     * Sort an array an use {@link PartitionAlgorithm#LOMUTO} for partitioning
     * the elements in the array.
     * 
     * @param elements the array with elements to sort. The elements should
     *            define their natural ordering or the desired ordering by
     *            implementing the {@link Comparable}interface. The Algorithm
     *            uses {@link Comparable#compareTo(Object)} to compare objects
     *            during the sorting process.
     */
    public static <T extends Comparable<T>> void sort(final T[] elements) {
        sort(elements, PartitionAlgorithm.HOARE);
    }

    /**
     * Sort an array of elements using a specific partition strategy.
     * 
     * @param elements the array be sorted. These elements should define their
     *            natural ordering or the desired ordering by implementing the
     *            {@link Comparable}interface. The Algorithm uses
     *            {@link Comparable#compareTo(Object)} to compare objects during
     *            the sorting process.
     * @param partitionAlgorithm the algorithm to use for partitioning.
     */
    public static <T extends Comparable<T>> void sort(final T[] elements,
            final PartitionAlgorithm partitionAlgorithm) {
        if (elements == null) {
            throw new IllegalArgumentException(
                    "The array to sort can't be null!");
        }
        if (partitionAlgorithm == PartitionAlgorithm.LOMUTO) {
            sortUsingLomutoParition(elements, 0, elements.length - 1);
        } else if (partitionAlgorithm == PartitionAlgorithm.HOARE) {
            sortUsingHoarePartition(elements, 0, elements.length - 1);
        } else {
            throw new IllegalArgumentException("Unknown strategy "
                    + partitionAlgorithm);
        }
    }

    /**
     * Sort an array of elements and use Lomuto algorithm for partitioning the
     * elements.
     * 
     * @param elements the array with elements to sort.
     * @param start the first index of the elements to sort.
     * @param end the last index of the elements to sort.
     */
    private static <T extends Comparable<T>> void sortUsingLomutoParition(
            final T[] elements, final int start, final int end) {
        if (start < end) {
            int pivot = lomutoPartition(elements, start, end);
            sortUsingLomutoParition(elements, start, pivot - 1);
            sortUsingLomutoParition(elements, pivot + 1, end);
        }

    }

    /**
     * Sort an array of elements and used Hoare algorithm for partitioning the
     * elements.
     * 
     * @param elements the array with elements to sort.
     * @param start the first index of the elements to sort.
     * @param end the last index of the elements to sort.
     */
    private static <T extends Comparable<T>> void sortUsingHoarePartition(
            final T[] elements, final int start, final int end) {
        if (start < end) {
            int pivot = hoarePartition(elements, start, end);
            sortUsingHoarePartition(elements, start, pivot);
            sortUsingHoarePartition(elements, pivot + 1, end);
        }

    }

    /**
     * Implements Hoare partitioning algorithm.
     * 
     * @param elements the array with the element to partition
     * @param start the first index of the elements to partition.
     * @param end the last index of the elements to partition.
     * @return the index of the pivot.
     */
    private static <T extends Comparable<T>> int hoarePartition(
            final T[] elements, final int start, final int end) {
        T pivot = elements[start];
        int leftIndex = start - 1;
        int rightIndex = end + 1;
        while (true) {
            do {
                leftIndex++;
            } while (elements[leftIndex].compareTo(pivot) == -1);

            do {
                rightIndex--;
            } while (elements[rightIndex].compareTo(pivot) == 1);

            if (leftIndex < rightIndex) {
                ArraysUtils.swap(elements, leftIndex, rightIndex);
            } else {
                break;
            }
        }

        return rightIndex;
    }

    /**
     * Implements Lomuto partitioning algorithm.
     * 
     * @param elements the array with the element to partition
     * @param start the first index of the elements to partition.
     * @param end the last index of the elements to partition.
     * @return the index of the pivot.
     */
    private static <T extends Comparable<T>> int lomutoPartition(
            final T[] array, final int start, final int end) {

        // index of the last element encountered smaller than the pivot.
        int lowestIndex = start - 1;

        // the last element is used as pivot
        int pivotIndex = end;

        for (int index = start; index < end; index++) {
            if (array[index].compareTo(array[pivotIndex]) < 0) {
                lowestIndex++;
                ArraysUtils.swap(array, lowestIndex, index);
            }
        }

        ArraysUtils.swap(array, ++lowestIndex, pivotIndex);
        return lowestIndex;
    }
}