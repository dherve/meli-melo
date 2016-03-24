package com.melimelo.stacks;
/**
 * Base class for exception that can be thrown by the StackList.
 */
public class StackListException extends Exception {

    /**
     * Create a new instance and set the error message.
     * @param message the exception message.
     */
    public StackListException(final String message) {
        super(message);
    }

    /**
     * Create a new instance.
     * @param message   the exception message.
     * @param cause     the exception cause.
     */
    public StackListException(final String message, final Throwable cause) {
        super(message, cause);
    }
}