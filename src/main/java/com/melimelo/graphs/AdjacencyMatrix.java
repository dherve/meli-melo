package com.melimelo.graphs;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Adjacency matrix representation of a graph.
 */
public final class AdjacencyMatrix {

    private boolean m_directedEdges;
    private int[][] m_matrix;
    private Map<Vertex, Integer> m_verticesIndexes;
    public static final int UNDEFINED_INDEX = -1;
    public static final int EDGE_PRESENT = 1;
    public static final int EGDE_MISSING = 0;

    /**
     * Create a new instance with the edges and vertices provided.
     * 
     * @param edges the graph edges.
     * @param vertices the graph vertices.
     * @param directed a flag indicating whether or not the graph is directed.
     */
    public AdjacencyMatrix(final Set<Edge> edges, final boolean directed) {
        validateEdges(edges);
        m_directedEdges = directed;
        initializeVerticesIndexes(edges);
        initializeAdjacencyMatrix(edges);
    }

    /**
     * Check if there is no edge in a set of edges. Used to verify edges
     * provided at creation.
     * 
     * @param edges a set of edges to check.
     */
    private void validateEdges(final Set<Edge> edges) {
        if (edges == null) {
            throw new IllegalArgumentException(
                    "The set of edges can't be null");
        }
        for (Edge edge : edges) {
            if (edge == null) {
                throw new IllegalArgumentException("The edge can't be null");
            }
        }
    }

    /**
     * Check if the adjacency matrix contains a vertex.
     * 
     * @param vertex the vertex to check.
     * @return true if the adjacency matrix contains the vertex, false
     *         otherwise.
     */
    public boolean containsVertex(final Vertex vertex) {
        return vertex != null && m_verticesIndexes.containsKey(vertex);
    }

    /**
     * Check if an adjacency matrix contains an edge.
     * 
     * @param edge the edge to check.
     * @return true if the adjacency matrix contains the edge, false otherwise.
     */
    public boolean containsEdge(final Edge edge) {
        return edge != null && 
               containsVertex(edge.getStartVertex()) && 
               containsVertex(edge.getEndVertex()) &&
               m_matrix[getVertexIndex(edge.getStartVertex())]
                       [getVertexIndex(edge.getEndVertex())] == EDGE_PRESENT;
    }

    /**
     * 
     * @param
     * @return
     */
    /**
     * Check if an adjacency matrix contains an edge.
     * 
     * @param start the start vertex of the edge to check.
     * @param end the end vertex of the edge to chek.
     * @return true if the adjacency matrix contains the edge, false otherwise.
     */
    public boolean containsEdge(final Vertex start, final Vertex end) {
        return containsEdge(new Edge(start, end));
    }

    /**
     * @return the number of vertices.
     */
    public int size() {
        return m_matrix.length;
    }

    /**
     * @return the adjacency matrix vertices.
     */
    public Set<Vertex> getVertices() {
        return Collections.unmodifiableSet(m_verticesIndexes.keySet());
    }

    /**
     * @return the vertices label ordered by their indexes in the internal
     *         adjacency matrix.
     */
    private Vertex[] verticesOrderedByIndexes() {
        Vertex[] vertices = new Vertex[m_verticesIndexes.size()];
        for (Map.Entry<Vertex, Integer> vertexEntry : m_verticesIndexes.entrySet()) {
            vertices[vertexEntry.getValue().intValue()] = vertexEntry.getKey();
        }
        return vertices;
    }

    @Override
    public String toString() {
        Vertex[] vertices = verticesOrderedByIndexes();
        StringBuilder builder = new StringBuilder();
        builder.append(Arrays.toString(vertices));
        builder.append("\n");
        for (int row = 0; row < m_matrix.length; row++) {
            for (int col = 0; col < m_matrix[row].length; col++) {
                builder.append("[");
                builder.append(vertices[row].label());
                builder.append(",");
                builder.append(vertices[col].label());
                builder.append("]=");
                builder.append(m_matrix[row][col]);
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public void prettyPrint() {
        StringBuilder builder = new StringBuilder();
        builder.append("vertices order ");
        builder.append(Arrays.toString(verticesOrderedByIndexes()));
        builder.append("\n");
        for (int[] row : m_matrix) {
            builder.append(Arrays.toString(row));
            builder.append("\n");
        }
        builder.append("\n");
        System.out.println(builder.toString());
    }

    /**
     * Assign an index to each vertex of a matrix. This index is used by the
     * inner matrix (2D array) for row and columns indexes. For example if a
     * vertex is assigned an index i, then the row i and columns i corresponds
     * to that vertex and whenever there an entry at the cell j in the row i or
     * the column i, then there's an edge between the vertex with index i and
     * the vertex with index j.
     * 
     * @param vertices A list of vertices to which indexes are assigned.
     */
    private void initializeVerticesIndexes(final Set<Edge> edges) {
        m_verticesIndexes = new HashMap<Vertex, Integer>();
        int index = 0;
        for (Edge edge : edges) {
            if (!m_verticesIndexes.containsKey(edge.getStartVertex())) {
                m_verticesIndexes.put(edge.getStartVertex(), index++);
            }
            if (!m_verticesIndexes.containsKey(edge.getEndVertex())) {
                m_verticesIndexes.put(edge.getEndVertex(), index++);
            }
        }
    }

    /**
     * Initialize the adjacency matrix.
     * 
     * @param edges A list of edges with the vertices to use for building the
     *            adjacency matrix.
     * @param verticesCount the number of vertices.
     */
    private void initializeAdjacencyMatrix(final Set<Edge> edges) {
        m_matrix = new int[m_verticesIndexes.size()][m_verticesIndexes.size()];
        for (Edge edge : edges) {
            if (containsVertex(edge.getStartVertex()) && 
                containsVertex(edge.getEndVertex())) {

                int indexStartVertex = getVertexIndex(edge.getStartVertex());
                int indexEndVertex = getVertexIndex(edge.getEndVertex());

                m_matrix[indexStartVertex][indexEndVertex] = EDGE_PRESENT;
                if (!m_directedEdges) {
                    m_matrix[indexEndVertex][indexStartVertex] = EDGE_PRESENT;
                }
            }
        }
    }

    /**
     * Get the index of a vertex.
     * 
     * @param vertex the vertex vor which to get the index.
     * @return the vertex index.
     */
    private int getVertexIndex(final Vertex vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("Vertex " + vertex + " doesn't exists!");
        }
        return m_verticesIndexes.get(vertex);
    }
}