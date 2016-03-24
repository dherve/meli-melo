package com.melimelo.heap;
/**
 * Exception thrown when the heap is empty and an attempt to delete element is
 * made.
 */
@SuppressWarnings("serial")
public class HeapUnderflowException extends HeapException {

    /**
     * Create a new instance.
     */
    public HeapUnderflowException() {
        super("HeapUnderflowException");
    }

    /**
     * Create a new instance with a specific error message.
     * 
     * @param message the error message.
     */
    public HeapUnderflowException(final String message) {
        super(message);
    }
}