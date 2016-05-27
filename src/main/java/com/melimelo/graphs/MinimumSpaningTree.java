package com.melimelo.graphs;

import java.math.BigDecimal;

/**
 * Keeps edges and cost of a minimum spanning tree built from a graph.
 */
public final class MinimumSpaningTree extends Graph {
    private BigDecimal m_cost = BigDecimal.ZERO;

    /**
     * Create a new instance of a minimum spanning tree.
     * 
     * @param weighted A flag indicating whether the tree is weighted or not,
     *            the value is derived from the graph used to build the minimum
     *            spanning tree.
     * 
     */
    public MinimumSpaningTree(boolean weighted) {
        super(false, weighted);
    }

    @Override
    public void addEdge(Edge edge) {
        super.addEdge(edge);
        if (edge.getWeight() != null) {
            m_cost = m_cost.add(edge.getWeight());
        }
    }

    /**
     * @return the tree's cost.
     */
    public BigDecimal getCost() {
        return m_cost;
    }

}
