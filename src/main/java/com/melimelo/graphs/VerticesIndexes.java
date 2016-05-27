package com.melimelo.graphs;

import java.util.Collection;
import java.util.HashMap;

import java.util.Map;
import java.util.TreeSet;

/**
 * Map vertices to indexes and indexes to vertices (two way association). The
 * indexes are assigned to vertices according to their natural ordering. i.e
 * first vertex will be assigned 0 as index, the second vertex will be assigned
 * 2 and so on. Used by algorithm that use adjacency matrix to process graph
 * vertices.
 */
public class VerticesIndexes {
    public static final int UNDEFINED_INDEX = -1;
    private final Vertex[] m_vertices;
    private final Map<Vertex, Integer> m_verticesIndexes;

    /**
     * Create an new instance and initialize association between indexes and
     * vertices.
     * 
     * @param vertices the vertices to be assigned indexes
     */
    public VerticesIndexes(final Collection<Vertex> vertices) {
        m_verticesIndexes = new HashMap<Vertex, Integer>();
        m_vertices = new Vertex[vertices.size()];
        initializeIndexes(vertices);
    }

    /**
     * Initialize association between indexes and vertices.
     * @param vertices the vertices to which indexes will be assigned.
     */
    private void initializeIndexes(final Collection<Vertex> vertices) {
        int index = 0;
        Collection<Vertex> sortedVertices = new TreeSet<Vertex>(vertices);
        for (Vertex vertex : sortedVertices) {
            m_vertices[index] = vertex;
            m_verticesIndexes.put(vertex, index);
            index++;
        }
    }

    /**
     * Get the index of a vertex.
     * 
     * @param vertex the vertex for which to get the index.
     * @return the index assigned to the vertex. -1 if the vertex provided was
     *         not part of the vertices provided at iteration.
     */
    public int getIndex(Vertex vertex) {
        return m_verticesIndexes.containsKey(vertex) ? 
               m_verticesIndexes.get(vertex) : UNDEFINED_INDEX;
    }

    /**
     * Get a vertex associated to an index.
     * 
     * @param index the index for which to get the associated vertex.
     * @return the vertex associated to the index provided.
     * @throws VertexNotFoundException if the index provided is not within the
     *             range of index assigned.
     */
    public Vertex getVertex(final int index) throws VertexNotFoundException {
        if (index < 0 || index >= m_vertices.length) {
            throw new VertexNotFoundException("No vertex found for index " + index);
        }
        return m_vertices[index];
    }
}