package com.melimelo.sorting;

import com.melimelo.arrays.ArraysUtils;

/**
 * Heapsort algorithm implementation.
 */
public final class HeapSort {

    /**
     * Index of the first element. When an array is used to represent a heap,
     * the index of the first element (the topmost in the heap) starts at 1, not
     * 0;
     */
    private static final int TOP_ELEMENT_INDEX = 1;

    /**
     * check if the an element of an array is bigger than another element in the
     * same array. The comparison is done according to the order defined by the
     * element's compareTo method implementation.
     * 
     * @param firstElementIndex the index of the first element to compare.
     * @param secondElementIndex the index of the second element to compare.
     * @param array the array with elements to be compared.
     * @return true if the first element is the biggest, false otherwise.
     */
    private static <T extends Comparable<T>> boolean isBigger(
            final int firstElementIndex, final int secondElementIndex,
            final T[] array) {
        return array[firstElementIndex].compareTo(array[secondElementIndex]) == 1;
    }

    /**
     * Check if an index is within the left and right bound of an array. (The
     * left bound is the index of the first element and the right bound value is
     * passed as a parameter) and doesn't point to a null element in an array.
     * 
     * @param index the index to check.
     * @param rightBound the right bound value.
     * @param elements An array with elements.
     * @return true id the index is valid, false otherwise.
     */
    private static <T extends Comparable<T>> boolean validateIndex(
            final int index, final int rightBound, final T[] elements) {
        return !(index < TOP_ELEMENT_INDEX || index > rightBound || elements[index] == null);
    }

    /**
     * Re-arrange the elements of a portion of an array in a max-heap order i.e
     * if the elements were part of max heap then each element would be bigger
     * than its children.
     * 
     * @param from the index of the first element in the portion to be
     *            re-arranged.
     * @param to the index of the last element in the portion to be re-arranged.
     * @param array the array with the elements to re-arrange.
     */
    private static <T extends Comparable<T>> void maxHeapify(final int from,
            final int to, final T[] array) {
        int index = from;
        while (index < to) {
            int maxElementIndex = index;
            int leftChildIndex = index * 2;
            int rightChildIndex = (index * 2) + 1;

            // check if the left child is bigger than the current max element
            if (validateIndex(leftChildIndex, to, array) && 
                isBigger(leftChildIndex, maxElementIndex, array)) {
                maxElementIndex = leftChildIndex;
            }

            // check if the right child is bigger than the current max element
            if (validateIndex(rightChildIndex, to, array) && 
                isBigger(rightChildIndex, maxElementIndex, array)) {
                maxElementIndex = rightChildIndex;
            }

            if (maxElementIndex == index) {
                break;
            }

            ArraysUtils.swap(array, index, maxElementIndex);
            index = maxElementIndex;
        }
    }

    /**
     * Re-arrange the first n elements of a portion of an array in a max-heap
     * order i.e if the elements were part of max heap then each element would
     * be bigger than its children.
     * 
     * @param end the index of the last element in the portion to be
     *            re-arranged.
     * @param array the array with the elements to re-arrange.
     */
    private static <T extends Comparable<T>> void maxHeapify(final T[] array,
            final int end) {
        for (int index = end / 2; index > 0; index--) {
            maxHeapify(index, end, array);
        }
    }

    /**
     * Sort a portion of an array.
     * 
     * @param start the index of the first element in the portion to be sorted.
     * @param end the index of the last element in the portion to be sorted.
     * @param array the array with the elements to be sorted.
     */
    private static <T extends Comparable<T>> void sort(final int start,
            final int end, final T[] array) {
        int lastElementIndex = end;
        for (int index = end; index > 1; index--) {
            ArraysUtils.swap(array, start, index);
            lastElementIndex--;
            maxHeapify(start, lastElementIndex, array);
        }
    }

    
    /**
     * Sort an array of elements. The elements to be sorted should define their
     * natural ordering or the desired ordering by implementing the
     * {@link Comparable}interface. The Algorithm uses
     * {@link Comparable#compareTo(Object)} to compare objects during the
     * sorting process.
     * 
     * @param array An array with the elements to be sorted.
     */
    @SuppressWarnings("unchecked")
    public static final <T extends Comparable<T>> void sort(final T[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The array to sort can't be null !");
        }

        if (!ArraysUtils.isEmpty(array)) {

            final T[] elements = (T[]) new Comparable[array.length + 1];

            // Copy the elements in a temporary array to be used fro sorting
            for (int index = 0; index < array.length; index++) {
                elements[index + 1] = array[index];
            }

            // Re-arrange the elements in a max-heap order and sort them
            maxHeapify(elements, array.length);
            sort(TOP_ELEMENT_INDEX, array.length, elements);

            // Copy back the elements sorted.
            for (int index = 0; index < array.length; index++) {
                array[index] = elements[index + 1];
            }
        }

    }
}