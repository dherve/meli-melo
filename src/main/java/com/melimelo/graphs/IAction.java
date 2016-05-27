package com.melimelo.graphs;

/**
 * Base interface for classes defining actions to apply on edges of graph during
 * a traversal
 */
public interface IAction<T> {
    /**
     * Process a graph edge.
     * 
     * @param start the first vertex of the edge to process.
     * @param end the end vertex of the edge to process.
     */
    public void processEdge(final T start, final T end);
}