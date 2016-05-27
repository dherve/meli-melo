package com.melimelo.graphs.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.melimelo.graphs.Algorithms;
import com.melimelo.graphs.Graph;

/**
 * unit test for the stack based version of depth first search algorithm.
 */
public class StackBasedDfsTest extends AlgorithmsTestBase {

    @Test(expected = IllegalArgumentException.class)
    public void testStackBasedDfsOnNullGraph() {
        Algorithms.stackBasedDfs(GraphFactory.createNullGraph(), SOURCE_VERTEX, m_action);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStackBasedDfsWithNullVertex() {
        Algorithms.stackBasedDfs(GraphFactory.createEmptyGraph(), null, m_action);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStackBasedDfsWithNullAction() {
        Algorithms.stackBasedDfs(GraphFactory.createEmptyGraph(), SOURCE_VERTEX, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStackBasedDfsOnEmptyGraph() {
        Algorithms.stackBasedDfs(GraphFactory.createEmptyGraph(), SOURCE_VERTEX, m_action);
    }

    // #TODO :fix this -> we shouldn't really thrown an exception here, 
    // since there's only one vertex
    @Test(expected = IllegalArgumentException.class)
    public void testStackBasedDfsOnSingletonGraph() {
        Graph graph = GraphFactory.createSingletonGraph();
        Algorithms.stackBasedDfs(graph, SOURCE_VERTEX, m_action);
        assertEquals(m_action.getVertices().size(), graph.verticesCount());
        assertTrue(m_action.getVertices().containsAll(graph.getVertices()));
    }

    @Test
    public void testStackBasedDfsOnUnDirectedWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedCyclicGraph();
        Algorithms.stackBasedDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testStackBasedDfsOnUnDirectedUnWeightedCyclicGraph() {
        Graph graph = GraphFactory.createUnDirectedUnWeightedCyclicGraph();
        Algorithms.stackBasedDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testStackBasedDfsOnUnDirectedWeightedACyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedAcyclicGraph();
        Algorithms.stackBasedDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testStackBasedDfsOnUnDirectedUnWeightedACyclicGraph() {
        Graph graph = GraphFactory.createUnDirectedUnWeightedAcyclicGraph();
        Algorithms.stackBasedDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testStackBasedDfsOnDirectedWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createDirectedWeightedCyclicGraph();
        Algorithms.stackBasedDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testStackBasedDfsOnDirectedUnWeightedCyclicGraph() {
        Graph graph = GraphFactory.createDirectedUnWeightedCyclicGraph();
        Algorithms.stackBasedDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testStackBasedDfsOnDirectedWeightedACyclicGraph() throws Exception {
        Graph graph = GraphFactory.createDirectedWeightedAcyclicGraph();
        Algorithms.stackBasedDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testStackBasedDfsOnDirectedUnWeightedACyclicGraph() {
        Graph graph = GraphFactory.createDirectedUnWeightedAcyclicGraph();
        Algorithms.stackBasedDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }
}
