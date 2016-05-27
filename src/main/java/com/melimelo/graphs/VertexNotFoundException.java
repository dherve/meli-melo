package com.melimelo.graphs;

/**
 * Thrown when a vertex cannot be found from graph.
 */
@SuppressWarnings("serial")
public final class VertexNotFoundException extends GraphOperationException {
    /**
     * Create a new instance.
     * @param vertexLabel the label of the vertex not found.
     */
    public VertexNotFoundException(String vertexLabel) {
        super("Vertex " + vertexLabel + " not found !");
    }
}