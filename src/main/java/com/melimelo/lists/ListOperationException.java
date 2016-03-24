package com.melimelo.lists;
/**
 * An exception to be thrown when an operation supported by the list fails.
 */
public class ListOperationException extends Exception {

    /**
     * Create a new instance and set the error message.
     * @param message the exception message.
     */
    public ListOperationException(final String message) {
        super(message);
    }

    /**
     * Create a new instance.
     * @param message   the exception message.
     * @param cause     the exception cause.
     */
    public ListOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}