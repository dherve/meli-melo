package com.melimelo.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Define a graph representation as an adjacency list.
 */
public final class AdjacencyList {

    private Map<Vertex, SortedSet<Vertex>> m_adjacencyMap;
    private boolean m_directedEdges;

    /**
     * Represent the degree of a vertex which doesn't exist in the adjacency
     * list.
     */
    public static int UNDEFINED_DEGREE = -1;

    public static final SortedSet<Vertex> EMPTY_VERTICES_SET = new TreeSet<Vertex>();

    /**
     * Create a new instance.
     * 
     * @param edges         A list of edges with vertices.
     * @param directedEdges A flag indicating whether edges provided are
     *                      directed or not.
     */
    public AdjacencyList(final Set<Edge> edges, final boolean directedEdges) {
        m_directedEdges = directedEdges;
        initializeAdjacencyMap(edges);
    }

    /**
     * @return true if the adjacencyList was create from a directed graph, 
     *         false otherwise.
     */
    public boolean hasDirectedEdges() {
        return m_directedEdges;
    }

    /**
     * Check if the adjacencyList contains a vertex.
     * 
     * @param vertex the vertex to check.
     * @return true if vertex to check is not null and the adjacencyList
     *         contains the provided vertex, false otherwise.
     */
    public boolean containsVertex(final Vertex vertex) {
        return vertex != null && m_adjacencyMap.containsKey(vertex);
    }

    /**
     * Check if the adjacencyList contains an edge.
     * 
     * @param edge the edge to check
     * @return true if the edge is not null and the adjacencyList contains the
     *         provided edge, false otherwise.
     */
    public boolean containsEdge(final Edge edge) {
        if (edge == null) {
            return false;
        }
        Set<Vertex> adjacents = m_adjacencyMap.get(edge.getStartVertex());
        return adjacents != null && adjacents.contains(edge.getEndVertex());
    }

    /**
     * Get the number of edges connected to the vertex.
     * 
     * @param vertex the vertex for which to get the degree.
     * @return the vertex degree, -1 if the vertex doesn't exist in the
     *         adjacency list.
     */
    public int getVertexDegree(final Vertex vertex) {
        return containsVertex(vertex) ? getAdjacentVertices(vertex).size()
                : UNDEFINED_DEGREE;
    }

    /**
     * @return the adjacencylist's vertices.
     */
    public Set<Vertex> getVertices() {
        return m_adjacencyMap.keySet();
    }

    /**
     * Get the vertices adjacent to a vertex.
     * 
     * @param vertex the vertex for which to get the adjacent vertices.
     * @return A set of vertices adjacent to the vertex provided. If the vertex
     *         has no adjacent vertices an immutable empty set will be returned.
     */
    public Set<Vertex> getAdjacentVertices(Vertex vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("Vertex " + vertex + " not found !");
        }
        return m_adjacencyMap.get(vertex);
    }

    /**
     * initialize the inner adjacency map from a set of edges.
     * 
     * @param edges A set of edges with vertices used when initializing the
     *            adjacency map.
     */
    private void initializeAdjacencyMap(final Set<Edge> edges) {
        m_adjacencyMap = new HashMap<Vertex, SortedSet<Vertex>>();
        for (Edge edge : edges) {
            addEdgeVertices(edge);
        }
    }

    /**
     * Add two adjacent vertices to the adjacency list.
     * 
     * @param vertex the first vertex to add.
     * @param adjacent the vertex adjacent to the first vertex added.
     */
    private void addAdjacentVertices(final Vertex vertex, final Vertex adjacent) {
        Set<Vertex> adjacents = m_adjacencyMap.get(vertex);
        if (adjacents == null || adjacents == EMPTY_VERTICES_SET) {
            m_adjacencyMap.put(vertex, new TreeSet<Vertex>());
        }
        if (adjacent != null) {
            m_adjacencyMap.get(vertex).add(adjacent);
        }
    }

    /**
     * Add vertices of an edge to the inner adjacency map.
     * 
     * @param edge an edge with the vertices to add.
     */
    private void addEdgeVertices(final Edge edge) {
        if (edge != null) {
            Vertex startVertex = edge.getStartVertex();
            Vertex endVertex = edge.getEndVertex();
            addAdjacentVertices(startVertex, endVertex);

            if (!m_adjacencyMap.containsKey(endVertex)) {
                m_adjacencyMap.put(endVertex, EMPTY_VERTICES_SET);
            }

            if (!m_directedEdges) {
                addAdjacentVertices(endVertex, startVertex);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Vertex, SortedSet<Vertex>> entry : m_adjacencyMap.entrySet()) {
            stringBuilder.append(entry.getKey().label());
            stringBuilder.append(" => ");
            stringBuilder.append(entry.getValue().toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}