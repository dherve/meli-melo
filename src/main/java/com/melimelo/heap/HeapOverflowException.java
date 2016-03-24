package com.melimelo.heap;
/**
 * Exception thrown when the heap is full and an attempt to insert an element is
 * made.
 */
@SuppressWarnings("serial")
public class HeapOverflowException extends HeapException {

    /**
     * Create a new instance.
     */
    public HeapOverflowException() {
        super("HeapOverflowException");
    }

    /**
     * Create a new instance with a specific error message.
     * 
     * @param message the error message.
     */
    public HeapOverflowException(final String message) {
        super(message);
    }
}