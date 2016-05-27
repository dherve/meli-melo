package com.melimelo.graphs.algorithms;

import java.math.BigDecimal;

import org.junit.Test;

import com.melimelo.graphs.Algorithms;
import com.melimelo.graphs.Edge;
import com.melimelo.graphs.Graph;
import com.melimelo.graphs.MinimumSpaningTree;

/**
 * unit tests for {@link Algorithms#kruskalMST(Graph, com.melimelo.graphs.Vertex)}
 */
public class KruskalMSTTest extends AlgorithmsTestBase {

    @Test(expected = IllegalArgumentException.class)
    public void testKruskalMSTOnNullGraph() throws Exception {
        Algorithms.kruskalMST(GraphFactory.createNullGraph(), SOURCE_VERTEX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKruskalMSTWithNullVertex() throws Exception {
        Algorithms.kruskalMST(GraphFactory.createDirectedWeightedAcyclicGraph(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKruskalMSTOnEmptyGraph() throws Exception {
        Algorithms.kruskalMST(GraphFactory.createEmptyGraph(), SOURCE_VERTEX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKruskalMSTOnSingletonGraph() throws Exception {
        Algorithms.kruskalMST(GraphFactory.createSingletonGraph(), SOURCE_VERTEX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKruskalMSTOnDirectedGraph() throws Exception {
        Algorithms.kruskalMST(GraphFactory.createDirectedUnWeightedAcyclicGraph(),
                SOURCE_VERTEX);
    }

    // -----------------------------------------------------------------------//
    //
    // unweighted graphs
    //
    // -----------------------------------------------------------------------//
    @Test
    public void testKruskalMSTOnUnDirectedUnWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedUnWeightedCyclicGraph();
        MinimumSpaningTree mst = Algorithms.kruskalMST(graph, SOURCE_VERTEX);
        verifyUnWeightedGraphMST(graph, mst);
    }

    @Test
    public void testKruskalMSTOnUnDirectedUnWeightedAcyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedUnWeightedAcyclicGraph();
        MinimumSpaningTree mst = Algorithms.kruskalMST(graph, SOURCE_VERTEX);
        verifyUnWeightedGraphMST(graph, mst);
    }

    // -----------------------------------------------------------------------//
    //
    // weighted graphs
    //
    // -----------------------------------------------------------------------//
    @Test
    public void testKruskalMSTOnUnDirectedWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedCyclicGraph();
        MinimumSpaningTree mst = Algorithms.kruskalMST(graph, SOURCE_VERTEX);

        // the expected cost is 16 given the topology of the graph used for
        // testing
        verifyWeightedGraphMST(graph, mst, BigDecimal.valueOf(16));
    }

    @Test
    public void testKruskalMSTOnUnDirectedWeightedAcyclicGraph()
            throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedAcyclicGraph();
        MinimumSpaningTree mst = Algorithms.kruskalMST(graph, SOURCE_VERTEX);
        BigDecimal cost = BigDecimal.ZERO;

        // Given that the graph is tree, we just need to sum up the weight of
        // all edges
        // divided by 2 because for undirected graph we have twice the same edge
        // (from-to) and (to-from)
        for (Edge edge : graph.getEdges()) {
            cost = cost.add(edge.getWeight());
        }
        verifyWeightedGraphMST(graph, mst, cost.divide(BigDecimal.valueOf(2)));
    }

}
