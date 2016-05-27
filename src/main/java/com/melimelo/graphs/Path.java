package com.melimelo.graphs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import com.melimelo.validation.ValidationUtils;

/**
 * Defines a sequence of ordered vertices starting from a given vertex (start)
 * and stops to another vertex. The order is important i.e the path (a,b,c,d) is
 * not the same as the path (a,b,d,c) although both contains the same vertices.
 */
public class Path {
    private Vertex m_start;
    private Vertex m_end;
    private LinkedHashSet<Vertex> m_intermediateVertices;
    private BigDecimal m_length;

    /**
     * Create a new path and set the first and last vertices of the path.
     * 
     * @param start the vertex from which the path starts.
     * @param end the vertex on which the path ends.
     * @param length the length of the path. This is not the number of vertices
     *            in the path.
     */
    public Path(final Vertex start, final Vertex end, final BigDecimal length) {
        m_start = new Vertex(start.label());
        m_end = new Vertex(end.label());
        // workaround for copy constructor
        m_length = BigDecimal.ZERO.add(length);
        m_intermediateVertices = new LinkedHashSet<Vertex>();
    }

    /**
     * @return the vertex from which the path starts.
     */
    public Vertex getStart() {
        return m_start;
    }

    /**
     * @return the vertex on which the path ends.
     */
    public Vertex getEnd() {
        return m_end;
    }

    /**
     * Add vertex before the last vertex of the path.
     * 
     * @param vertex the vertex to add.
     */
    public void addIntermediateVertex(final Vertex vertex) {
        ValidationUtils.validateNotNull(vertex, "the vertex can't be null");
        m_intermediateVertices.add(vertex);
    }

    /**
     * Add vertices before the last vertex of the path.
     * 
     * @param vertices a list of vertices to add.
     */
    public void addIntermediateVertices(final List<Vertex> vertices) {
        m_intermediateVertices.addAll(vertices);
    }

    /**
     * @return the vertices between the first and last vertices of the path, in
     *         order in which they were added.
     */
    public List<Vertex> intermediatesVertices() {
        return new ArrayList<Vertex>(m_intermediateVertices);
    }

    /**
     * @return a list of vertices in the path, from the first vertex to the last
     *         vertex.
     */
    public List<Vertex> asList() {
        // #TODO : find a way to reduce intermediates data structures used
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(m_start);
        vertices.addAll(m_intermediateVertices);
        vertices.add(m_end);
        return vertices;
    }

    /**
     * @return a path where the vertices are in the inverse order of the current
     *         path. i.e the first vertex is the current path last vertex and
     *         the last vertex is the current first vertex.
     */
    public Path reverse() {
        Path path = new Path(m_end, m_start, m_length);
        List<Vertex> vertices = intermediatesVertices();
        Collections.reverse(vertices);
        path.addIntermediateVertices(vertices);
        return path;
    }

    /**
     * @return the length of the path. This is not the number of vertices in the
     *         path but the sum of width of all edges making the path.
     */
    public BigDecimal length() {
        return m_length;
    }
    
    /**
     * @return the number of vertices in this path.
     */
    public int verticesCount() {
        return m_intermediateVertices.size() + (m_start.equals(m_end) ? 1 : 2);
    }

    @Override
    public String toString() {
        return "[vertices=" + asList().toString() + "; length = " + m_length + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Path)) {
            return false;
        }

        Path path = (Path) other;
        return m_start.equals(path.m_start) && m_end.equals(path.m_end)
                && m_intermediateVertices.equals(path.m_intermediateVertices)
                && m_length.equals(m_length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_start, m_intermediateVertices, m_end, m_length);
    }
}