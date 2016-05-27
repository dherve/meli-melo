package com.melimelo.graphs;

/**
 * Thrown when an edge with specific endpoints cannot be found from a graph.
 */
@SuppressWarnings("serial")
public final class EdgeNotFoundException extends GraphOperationException {
    /**
     * Create  a new instance.
     * @param start the label of the first vertex of the edge not found.
     * @param end the label of the second vertex of the edge not found.
     */
    public EdgeNotFoundException(final String start, final String end) {
        super("Edge (" + start + "," + end + ") not found !");
    }
    
    /**
     * Create  a new instance.
     * @param start the first vertex of the edge not found.
     * @param end the second vertex of the edge not found.
     */
    public EdgeNotFoundException(final Vertex start, final Vertex end) {
        super("Edge (" + start + "," + end + ") not found !");
    }
}