package com.melimelo.graphs.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.melimelo.graphs.Edge;
import com.melimelo.graphs.IAction;
import com.melimelo.graphs.Vertex;

/**
 * Store all vertices visited and all edges covered during a graph traversal.
 */
public class TraversalAction implements IAction<Vertex> {
    private List<Edge> m_edges = new ArrayList<Edge>();
    private Set<Vertex> m_vertices = new HashSet<Vertex>();

    /**
     * Create a new instance and set the vertex from which the traversal
     * started.
     * 
     * @param source the vertex from which the graph traversal started.
     */
    public TraversalAction(Vertex source) {
        m_vertices.add(source);
    }

    /**
     * @return all vertices covered (visited + processed) during a graph
     *         traversal.
     */
    public Set<Vertex> getVertices() {
        return m_vertices;
    }

    /**
     * @return all edges covered during a graph traversal.
     */
    public List<Edge> getEdges() {
        return m_edges;
    }

    /**
     * {@inheritDoc}
     */
    public void processEdge(Vertex start, Vertex end) {
        m_edges.add(new Edge(start, end));
        m_vertices.add(start);
        m_vertices.add(end);
    }
}
