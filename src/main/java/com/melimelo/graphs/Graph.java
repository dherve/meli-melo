package com.melimelo.graphs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.melimelo.validation.ValidationUtils;

/**
 * Define a simple graph (no self loops or multi-edges)
 */
public class Graph {
    private final String NULL_VERTEX_MESSAGE = "The vertex can't be null!";
    private final String NULL_VERTICES_MESSAGE = "The vertices can't be null!";
    private final String NULL_EDGE_MESSAGE = "The edge can't be null!";
    private final String NULL_EDGES_MESSAGE = "The edge can't be null!";
    private boolean m_weighted;
    private boolean m_directed;

    private Set<Edge> m_edges;

    // In order to avoid going through the edges list every time getVertices is
    // called we use an additional list to store the vertices but this come with
    // a cost of duplicate information stored.
    private Set<Vertex> m_vertices;

    /**
     * Create an empty graph.
     * 
     * @param directed a flag indicating or not whether the graph is directed.
     * @param weighted a flag indicating or not whether the graph is weighted.
     */
    public Graph(final boolean directed, final boolean weighted) {
        this(directed, weighted, new HashSet<Edge>(), new HashSet<Vertex>());
    }

    /**
     * Create a graph with the provided edges.
     * 
     * @param directed a flag indicating or not whether the graph is directed.
     * @param weighted a flag indicating or not whether the graph is weighted.
     * @param edges the graph edges.
     */
    public Graph(final boolean directed, final boolean weighted,
            final Set<Edge> edges) {
        this(directed, weighted, edges, new HashSet<Vertex>());
    }

    /**
     * Create a graph with the provided edges and vertices
     * 
     * @param directed a flag indicating or not whether the graph is directed.
     * @param weighted a flag indicating or not whether the graph is weighted.
     * @param edges the graph edges.
     * @param vertices the graph vertices.
     */
    public Graph(final boolean directed, final boolean weighted,
            final Set<Edge> edges, final Set<Vertex> vertices) {
        m_directed = directed;
        m_weighted = weighted;
        initializeEdges(edges);
        initializeVertices(edges, vertices);
    }

    /**
     * initialize graph vertices from the edges and the vertices provided at
     * Initialization time
     * 
     * @param edges the graph edges.
     * @param vertices the graph vertices.
     */
    private void initializeVertices(final Set<Edge> edges,
            final Set<Vertex> vertices) {
        ValidationUtils.validateNotNull(vertices, NULL_VERTICES_MESSAGE);
        ValidationUtils.validateNoNeNull(vertices, NULL_VERTEX_MESSAGE);
        m_vertices = new HashSet<Vertex>(vertices);
        for (Edge edge : edges) {
            m_vertices.add(edge.getStartVertex());
            m_vertices.add(edge.getEndVertex());
        }
    }

    /**
     * initialize graph edges from the edges provided at Initialization time
     * 
     * @param edges the graph edges.
     */
    private void initializeEdges(final Set<Edge> edges) {
        ValidationUtils.validateNotNull(edges, NULL_EDGES_MESSAGE);
        ValidationUtils.validateNoNeNull(edges, NULL_EDGE_MESSAGE);
        m_edges = new HashSet<Edge>(edges);
        if (!m_directed) {
            for (Edge edge : edges) {
                m_edges.add(edge.reversed());
            }
        }
    }

    /**
     * @return if the graph's edges have direction, false otherwise.
     */
    public boolean isDirected() {
        return m_directed;
    }

    /**
     * @return true if the graph is weighted, false otherwise.
     */
    public boolean isWeighted() {
        return m_weighted;
    }

    /**
     * Add an edge to the graph.
     * 
     * @param edge the edge to add.
     */
    public void addEdge(final Edge edge) {
        ValidationUtils.validateNotNull(edge, NULL_EDGE_MESSAGE);
        m_edges.add(edge);
        if (!m_directed) {
            m_edges.add(edge.reversed());
        }
        m_vertices.add(edge.getStartVertex());
        m_vertices.add(edge.getEndVertex());
    }

    /**
     * Add edges to the graph.
     * 
     * @param edges the edges to add. None of the edges can't be null.
     */
    public void addEdges(final Set<Edge> edges) {
        ValidationUtils.validateNotNull(edges, NULL_EDGES_MESSAGE);
        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    /**
     * Check if the graph has a specific edge.
     * 
     * @param edge the edge to check.
     * @return true if the provided edge is not null and is in the graph, false
     *         otherwise.
     */
    public boolean containsEdge(final Edge edge) {
        return edge == null ? false : m_edges.contains(edge);
    }

    /**
     * Check if the graph has an edge with the vertices provided, regardless of
     * the weight. For weighted graph, it is recommended to use
     * {@link #containsEdge(Edge)} because if take into account the weight of
     * the edged.
     * 
     * @param start the start vertex of the edge to check.
     * @param end the end vertex of edge to check.
     * @return true if the graph contains and edge with the provided vertices,
     *         false otherwise.
     */
    public boolean containsEdge(final Vertex start, final Vertex end) {
        if (start == null || end == null) {
            return false;
        }
        if (containsEdge(new Edge(start, end))) {
            return true;
        }
        for (Edge edge : m_edges) {
            if (start.equals(edge.getStartVertex())
                    && end.equals(edge.getEndVertex())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the number of edges in the graph
     */
    public int edgesCount() {
        return m_edges.size();
    }

    /**
     * @return true if the graph is empty (i.e no vertices and no edges), false
     *         otherwise.
     */
    public boolean isEmpty() {
        return !(hasVertices() || hasEdges());
    }

    /**
     * @return true if graph has at least one vertex, false otherwise.
     */
    public boolean hasVertices() {
        return !m_vertices.isEmpty();
    }

    /**
     * @return true if graph has at least one edge, false otherwise.
     */
    public boolean hasEdges() {
        return !m_edges.isEmpty();
    }

    /**
     * Get the edge with the provided start and end vertices.
     * 
     * @param start the start vertex of the edge to get. Can't be null.
     * @param end the end vertex of the edge to get. Can't be null.
     * @return the edge with the provided vertices as end points
     * @throws EdgeNotFoundException #TODO: add a test covering np args
     */
    public Edge getEdge(final Vertex start, final Vertex end)
            throws EdgeNotFoundException {
        ValidationUtils.validateNoNeNull(
                "the start and end vertex can't be null", start, end);
        for (Edge edge : m_edges) {
            if (edge.getStartVertex().equals(start)
                    && edge.getEndVertex().equals(end)) {
                return edge;
            }
        }
        throw new EdgeNotFoundException(start, end);
    }

    /**
     * @return the graph's edges.
     */
    public Set<Edge> getEdges() {
        return Collections.<Edge> unmodifiableSet(m_edges);
    }

    /**
     * Add a vertex to the graph.
     * 
     * @param vertex the vertex to add. Can't be null.
     */
    public void addVertex(final Vertex vertex) {
        ValidationUtils.validateNotNull(vertex, NULL_VERTEX_MESSAGE);
        m_vertices.add(vertex);
    }

    /**
     * Add vertices to the graph.
     * 
     * @param vertices the vertices to add. #TODO add test case for null args
     */
    public void addVertices(final Set<Vertex> vertices) {
        ValidationUtils.validateNotNull(vertices, NULL_VERTICES_MESSAGE);
        for (Vertex vertex : vertices) {
            addVertex(vertex);
        }
    }

    /**
     * Check if the graph contains a vertex.
     * 
     * @param vertex the vertex to check.
     * @return true if the vertex provided is not null and the graph contains
     *         it, false otherwise
     */
    public boolean containsVertex(final Vertex vertex) {
        return vertex == null ? false : m_vertices.contains(vertex);
    }

    /**
     * Get a vertex with the provide label.
     * 
     * @param label the label of the graph to get.
     * @return a vertex in the graph and with the provided label.
     * @throws VertexNotFoundException if the graph doesn't have a vertex with
     *             the provided label. #TODO: add test covering null args
     */
    public Vertex getVertex(final String label) throws VertexNotFoundException {
        ValidationUtils.validateNotNull(label, "The vertex label can't be null");
        for (Vertex vertex : m_vertices) {
            if (vertex.label().equals(label)) {
                return vertex;
            }
        }
        throw new VertexNotFoundException(label);
    }

    /**
     * @return the graph's vertices.
     */
    public Set<Vertex> getVertices() {
        if (m_vertices.isEmpty() && !m_edges.isEmpty()) {
            for (Edge edge : m_edges) {
                m_vertices.add(edge.getStartVertex());
                m_vertices.add(edge.getEndVertex());
            }
        }
        return Collections.unmodifiableSet(m_vertices);
    }

    /**
     * @return the number of vertices in the graph.
     */
    public int verticesCount() {
        return m_vertices.size();
    }

    /**
     * @return the graph as an adjacency matrix.
     */
    public AdjacencyMatrix asAdjacencyMatrix() {
        return new AdjacencyMatrix(m_edges, m_directed);
    }

    /**
     * @return the graph as an adjacency list.
     */
    public AdjacencyList asAdjacencyList() {
        return new AdjacencyList(m_edges, m_directed);
    }
}