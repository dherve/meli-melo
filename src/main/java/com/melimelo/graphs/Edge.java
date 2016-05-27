package com.melimelo.graphs;
import java.math.BigDecimal;
import java.util.Objects;

import com.melimelo.validation.ValidationUtils;

/**
 * Define a graph edge.
 */
public class Edge {
    public static final BigDecimal ZERO_WEIGHT = BigDecimal.ZERO;
    private Vertex m_start;
    private Vertex m_end;
    private BigDecimal m_weight;

    /**
     * Create a new instance and set the end points of the edge.
     * @param  start the first endpoint of the edge.
     * @param  end   the second endpoint of the edge.
     */
    public Edge(final Vertex start, final Vertex end) {
        this(start, end, ZERO_WEIGHT);
    }

    /**
     * Create a new instance and set the end points of the edge.
     * @param  start the label of the first endpoint of the edge.
     * @param  end   the label of the second endpoint of the edge.
     */
    public Edge(final String start, final String end) {
        this(new Vertex(start), new Vertex(end));
    }
    
    /**
     * Create a new instance and set the end points of the edge.
     * @param  start the label of the first endpoint of the edge.
     * @param  end   the label of the second endpoint of the edge.
     * @param  weight the edge weight.
     */
    public Edge(final String start, final String end, BigDecimal weight) {
        this(new Vertex(start), new Vertex(end), weight);
    }
    
    /**
     * Create a new instance and set the endpoints and the weight of the edge.
     * @param  start  the first endpoint of the edge.
     * @param  end    the second endpoint of the edge.
     * @param  weight the edge weight.
     */
    public Edge(final Vertex start, final Vertex end, final BigDecimal weight) {
        ValidationUtils.validateNotNull(start, "the start vertex can't be null !");
        ValidationUtils.validateNotNull(end, "the end vertex can't be null !");
        ValidationUtils.validateNotNull(weight, "the weight can't be null !");
        m_start = new Vertex(start);
        m_end = new Vertex(end);
        m_weight = weight;
    }

    /**
     * @return the edge weight.
     */
    public BigDecimal getWeight() {
        return m_weight;
    }

    /**
     * @return true if the edge gas a weight. False otherwise.
     */
    public boolean hasWeight() {
        return !(m_weight == null || ZERO_WEIGHT.equals(m_weight));
    }

    /**
     * @return the first endpoint of the edge.
     */
    public Vertex getStartVertex() {
        return m_start;
    }

    /**
     * @return the second endpoint of the edge.
     */
    public Vertex getEndVertex() {
        return m_end;
    }

    /**
     * @return an edge where the start vertex is current vertex end and 
     *         the end vertex is the current vertex start.
     */
    public Edge reversed() {
        return new Edge(m_end, m_start, m_weight);
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }

        if(!(other instanceof Edge)) {
            return false;
        }

        Edge edge = (Edge) other;
        return Objects.equals(m_start, edge.m_start) &&
               Objects.equals(m_end, edge.m_end) &&
               Objects.equals(m_weight, edge.m_weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_start, m_end, m_weight);
    }
    

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[start=");
        builder.append(m_start);
        builder.append(", end=");
        builder.append(m_end);

        if(hasWeight()) {
            builder.append(", weight=");
            builder.append(m_weight);
        }

        builder.append("]");
        return builder.toString();
    }
}