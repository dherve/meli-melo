package com.melimelo.graphs;
import java.util.Objects;

/**
 * Define a graph vertex.
 */
public class Vertex implements Comparable<Vertex>{
    
    private String m_label;

    /**
     * Create a new instance and set the vertex label.
     * @param  label the vertex label.
     */
    public Vertex(final String label) {
        if(label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("The vertex label can't be null or empty!");
        }
        m_label = label;
    }

    /**
     * Create a new Vertex from anothe instance of a vertex.
     * @param  vertex the vertex from which to create the new instance.
     */
    public Vertex(final Vertex vertex) {
        if(vertex == null) {
            throw new IllegalArgumentException("The vertex can't be null !");
        }
        m_label = vertex.m_label;
    }

    public int compareTo(final Vertex other) {
        return m_label.compareTo(other.m_label);
    }

    /**
     * @return the vertex label
     */
    public String label() {
        return m_label;
    }

    @Override
    public String toString() {
        return m_label.toString();
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }

        if(!(other instanceof Vertex)) {
            return false;
        }

        Vertex vertex = (Vertex) other;
        return Objects.equals(m_label, vertex.m_label);
    }
 
    @Override
    public int hashCode() { 
        return Objects.hashCode(m_label);
    }
}