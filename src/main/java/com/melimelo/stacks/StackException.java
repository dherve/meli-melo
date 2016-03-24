package com.melimelo.stacks;
/**
 * Base class for all exception that can be thrown by stacks methods.
 */
public class StackException extends Exception {

    /**
     * Create a new instance.
     * @param message the exception message.
     */
    public StackException(final String message) {
        super(message);
    }

    /**
     * Create a new instance.
     * @param message   the exception message.
     * @param cause     the exception cause.
     */
    public StackException(final String message, final Throwable cause) {
        super(message, cause);
    }
}