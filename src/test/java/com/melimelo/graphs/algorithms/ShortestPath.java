package com.melimelo.graphs.algorithms;

import java.math.BigDecimal;

import com.melimelo.graphs.Path;
import com.melimelo.graphs.Vertex;

/**
 * Define a sequence of vertices between two vertices, such that the distance
 * between the two vertices is the shortest in the graph.
 */
public class ShortestPath extends Path {
    /**
     * Create a new instance.
     * 
     * @param start the label of the vertex starting the path.
     * @param end the label of the vertex ending the path.
     * @param length the length of the path. This is not the number of vertices
     *            but rather a value computed from weight of edges of the path.
     * @param intermediateVertics labels of vertices between the first and the
     *            last vertices.
     */
    public ShortestPath(final String start, final String end, final int length,
            final String... intermediateVertices) {
        super(new Vertex(start), new Vertex(end), BigDecimal.valueOf(length));
        for (String vertex : intermediateVertices) {
            addIntermediateVertex(new Vertex(vertex));
        }
    }
}
