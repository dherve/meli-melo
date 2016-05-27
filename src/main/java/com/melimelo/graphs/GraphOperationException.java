package com.melimelo.graphs;

@SuppressWarnings("serial")
/**
 * Base class for all exceptions that can be thrown while performing an
 * operation on graph.
 */
public class GraphOperationException extends Exception {
    public GraphOperationException(final String message) {
        super(message);
    }

    public GraphOperationException(final String message,
            final Throwable cause) {
        super(message, cause);
    }
}
