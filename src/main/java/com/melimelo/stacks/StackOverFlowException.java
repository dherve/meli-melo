package com.melimelo.stacks;
/**
 * Base class for exception thrown when an illegal operation on a full stack
 * is performed.
 */
public final class StackOverFlowException extends StackException {

    /**
     * Create a new instance.
     * @param message the exception message.
     */
    public StackOverFlowException(final String message) {
        super(message);
    }
}