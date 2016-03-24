package com.melimelo.arrays;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ArraysUtils {

    /**
     * Swap to elements of an array.
     * 
     * @param array the array with the elements to be swap
     * @param from the index of the first element to swapped
     * @param to the index of the second element to be swapped.
     */
    public static <T extends Comparable<T>> void swap(final T[] array,
            final int from, final int to) {
        T temp = array[from];
        array[from] = array[to];
        array[to] = temp;
    }

    /**
     * Copy an array.
     * 
     * @param array the array to be copied.
     * @return a copy of the array.
     * @see Arrays#copyOf(T[] original, int newLength)
     */
    public static <T extends Comparable<T>> T[] copy(final T[] array) {
        return Arrays.copyOf(array, array.length);
    }

    /**
     * Print to the console all elements of an array on a new line
     * 
     * @param array the array to print.
     */
    public static <T extends Comparable<T>> void println(final T[] array) {
        System.out.println(Arrays.toString(array));
    }

    /**
     * Print to the console all elements of an array on the same line as the
     * cursor.
     * 
     * @param array the array to print.
     */
    public static <T extends Comparable<T>> void print(final T[] array) {
        System.out.print(Arrays.toString(array));
    }

    /**
     * Reverse the order of elements in an array.
     * 
     * @param array the array to reverse.
     */
    public static <T extends Comparable<T>> void reverse(final T[] array) {
        reverse(array, 0, array.length - 1);
    }

    /**
     * Reverse the order of elements an array within a specific range.
     * 
     * @param array the array of the elements to reverse.
     * @param from the index of the first element in the range
     * @param to the index of the last element in the range.
     */
    public static <T extends Comparable<T>> void reverse(final T[] array,
            final int from, final int to) {
        if (!isEmpty(array)) {
            int start = from;
            int end = to;
            if (end > start) {
                while (start < end) {
                    swap(array, start, end);
                    start++;
                    end--;
                }
            }
        }
    }

    /**
     * Shuffle elements of an array.
     * 
     * @param array the array with elements to shuffle.
     */
    public static <T extends Comparable<T>> void shuffle(final T[] array) {
        if (!isEmpty(array)) {
            final List<T> values = Arrays.asList(array);
            Collections.shuffle(values);
            for (int index = 0; index < values.size(); index++) {
                array[index] = values.get(index);
            }
        }
    }

    /**
     * Check if an array is not empty.
     * 
     * @param array the array to check.
     * @return true if the array is empty otherwise false.
     */
    public static <T> boolean isEmpty(final T[] array) {
        return array.length == 0;
    }

}