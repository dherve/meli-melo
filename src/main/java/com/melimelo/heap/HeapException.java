package com.melimelo.heap;
/**
 * Base class for exception thrown by the heap. 
 */
@SuppressWarnings("serial")
public class HeapException extends Exception {

    /**
     * Create a new instance with a specific error message.
     * 
     * @param message the error message.
     */
    public HeapException(final String message) {
        super(message);
    }
}