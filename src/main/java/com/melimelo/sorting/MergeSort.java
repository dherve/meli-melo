package com.melimelo.sorting;
/**
 * MergeSort algorithm implementation.
 */
public class MergeSort {

    /**
     * Sort an array of elements. The elements to be sorted should define their
     * natural ordering or the desired ordering by implementing the
     * {@link Comparable}interface. The Algorithm uses
     * {@link Comparable#compareTo(Object)} to compare objects during the
     * sorting process.
     * 
     * @param array An array with the elements to be sorted.
     */
    public static <T extends Comparable<T>> void sort(final T[] array) {
        if (array == null) {
            throw new IllegalArgumentException(
                    "The array to sort can't be null!");
        }
        final T[] mergingArray = (T[]) new Comparable[array.length];
        sort(array, mergingArray, 0, array.length - 1);
    }

    /**
     * Sort a section of an array.
     * 
     * @param array the array with the section to sort.
     * @param mergingArray A temporary array used for for merging The array is
     *            initialized once and passed on subsequent recursive calls.
     *            This will avoid creating a new array during the merging step
     * @param start the beginning of the section to sort.
     * @param end the end of the section to sort.
     */
    private static <T extends Comparable<T>> void sort(final T[] array,
            final T[] mergingArray, final int start, final int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            sort(array, mergingArray, start, middle);
            sort(array, mergingArray, middle + 1, end);
            merge(array, mergingArray, start, middle, end);
        }
    }

    /**
     * Merge two consecutive sections of an array in one sorted section
     * 
     * @param array the array with the sections to merge.
     * @param mergingArray A temporary array used for for merging.
     * @param start the beginning of the first section.
     * @param middle the end of the first section. The second section begins
     *            right after this index.
     * @param end the end of the second section.
     */
    private static <T extends Comparable<T>> void merge(final T[] array,
            final T[] mergingArray, final int start, final int middle,
            final int end) {
        int leftIndex = start;
        int rightIndex = middle + 1;
        int mergeIndex = start;

        while ((leftIndex <= middle) && (rightIndex <= end)) {
            if (array[leftIndex].compareTo(array[rightIndex]) == -1) {
                mergingArray[mergeIndex++] = array[leftIndex++];
            } else {
                mergingArray[mergeIndex++] = array[rightIndex++];
            }
        }

        while (leftIndex <= middle) {
            mergingArray[mergeIndex++] = array[leftIndex++];
        }
        while (rightIndex <= end) {
            mergingArray[mergeIndex++] = array[rightIndex++];
        }

        for (mergeIndex = start; mergeIndex <= end; mergeIndex++) {
            array[mergeIndex] = mergingArray[mergeIndex];
        }
    }
}