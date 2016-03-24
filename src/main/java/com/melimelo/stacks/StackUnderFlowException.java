package com.melimelo.stacks;

/**
 * Base class for exceptions thrown when an illegal operation on an empty
 * stack is performed.
 */
public final class StackUnderFlowException extends StackException {

    /**
     * Create a new instance.
     * @param message the exception message.
     */
    public StackUnderFlowException(final String message){
        super(message);
    }
}