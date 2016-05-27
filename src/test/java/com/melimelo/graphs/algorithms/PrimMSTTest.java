package com.melimelo.graphs.algorithms;

import java.math.BigDecimal;

import org.junit.Test;

import com.melimelo.graphs.Algorithms;
import com.melimelo.graphs.Edge;
import com.melimelo.graphs.Graph;
import com.melimelo.graphs.MinimumSpaningTree;

/**
 * unit tests for minimum spanning tree Prim algorithm
 */
public class PrimMSTTest extends AlgorithmsTestBase {

    @Test(expected = IllegalArgumentException.class)
    public void testPrimMSTOnNullGraph() throws Exception {
        Algorithms.primMST(GraphFactory.createNullGraph(), SOURCE_VERTEX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrimMSTWithNullVertex() throws Exception {
        Algorithms.primMST(GraphFactory.createDirectedWeightedAcyclicGraph(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrimMSTOnEmptyGraph() throws Exception {
        Algorithms.primMST(GraphFactory.createEmptyGraph(), SOURCE_VERTEX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrimMSTOnSingletonGraph() throws Exception {
        Algorithms.primMST(GraphFactory.createSingletonGraph(), SOURCE_VERTEX);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testPrimMSTOnDirectedGraph() throws Exception {
        Algorithms.primMST(GraphFactory.createDirectedUnWeightedAcyclicGraph(), SOURCE_VERTEX);
    }

    //-----------------------------------------------------------------------//
    //
    //              unweighted graphs
    //
    //-----------------------------------------------------------------------//
    @Test
    public void testPrimMSTOnUnDirectedUnWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedUnWeightedCyclicGraph(); 
        MinimumSpaningTree mst = Algorithms.primMST(graph, SOURCE_VERTEX); 
        verifyUnWeightedGraphMST(graph, mst);
    }

    @Test
    public void testPrimMSTOnUnDirectedUnWeightedAcyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedUnWeightedAcyclicGraph(); 
        MinimumSpaningTree mst = Algorithms.primMST(graph, SOURCE_VERTEX); 
        verifyUnWeightedGraphMST(graph, mst);
    }

    //-----------------------------------------------------------------------//
    //
    //              weighted graphs
    //
    //-----------------------------------------------------------------------//
    @Test
    public void testPrimMSTOnUnDirectedWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedCyclicGraph();
        MinimumSpaningTree mst = Algorithms.primMST(graph, SOURCE_VERTEX);
        
        // the expected cost is 16 given the topology of the graph used for testing 
        verifyWeightedGraphMST(graph, mst, BigDecimal.valueOf(16));
    }

    @Test
    public void testPrimMSTOnUnDirectedWeightedAcyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedAcyclicGraph(); 
        MinimumSpaningTree mst = Algorithms.primMST(graph, SOURCE_VERTEX);
        BigDecimal cost = BigDecimal.ZERO;
        
        //  Given that the graph is tree, we just need to sum up the weight of all edges
        // divided by 2 because for undirected graph we have twice the same edge (from-to) and (to-from)
        for (Edge edge : graph.getEdges()) {
            cost = cost.add(edge.getWeight());
        }
        verifyWeightedGraphMST(graph, mst, cost.divide(BigDecimal.valueOf(2)));
    }
}
