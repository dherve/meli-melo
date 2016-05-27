package com.melimelo.graphs.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.melimelo.graphs.Algorithms;
import com.melimelo.graphs.Graph;

/**
 * unit test for the recursive version of depth first search algorithm.
 */
public class RecursiveDfsTest extends AlgorithmsTestBase {

    @Before
    public void setUp() throws Exception {
        m_action = new TraversalAction(SOURCE_VERTEX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecursiveDfsOnNullGraph() {
        Algorithms.recursiveDfs(GraphFactory.createNullGraph(), SOURCE_VERTEX, m_action);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecursiveDfsWithNullVertex() {
        Algorithms.recursiveDfs(GraphFactory.createEmptyGraph(), null, m_action);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecursiveDfsWithNullAction() {
        Algorithms.recursiveDfs(GraphFactory.createEmptyGraph(), SOURCE_VERTEX, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecursiveDfsOnEmptyGraph() {
        Algorithms.recursiveDfs(GraphFactory.createEmptyGraph(), SOURCE_VERTEX, m_action);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecursiveDfsOnSingletonGraph() {
        Graph graph = GraphFactory.createSingletonGraph();
        Algorithms.recursiveDfs(graph, SOURCE_VERTEX, m_action);
        assertEquals(m_action.getVertices().size(), graph.verticesCount());
        assertTrue(m_action.getVertices().containsAll(graph.getVertices()));
    }

    @Test
    public void testRecursiveDfsOnUnDirectedWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedCyclicGraph();
        Algorithms.recursiveDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testRecursiveDfsOnUnDirectedUnWeightedCyclicGraph() {
        Graph graph = GraphFactory.createUnDirectedUnWeightedCyclicGraph();
        Algorithms.recursiveDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testRecursiveDfsOnUnDirectedWeightedACyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedAcyclicGraph();
        Algorithms.recursiveDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testRecursiveDfsOnUnDirectedUnWeightedACyclicGraph() {
        Graph graph = GraphFactory.createUnDirectedUnWeightedAcyclicGraph();
        Algorithms.recursiveDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testRecursiveDfsOnDirectedWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createDirectedWeightedCyclicGraph();
        Algorithms.recursiveDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testRecursiveDfsOnDirectedUnWeightedCyclicGraph() {
        Graph graph = GraphFactory.createDirectedUnWeightedCyclicGraph();
        Algorithms.recursiveDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testRecursiveDfsOnDirectedWeightedACyclicGraph() throws Exception {
        Graph graph = GraphFactory.createDirectedWeightedAcyclicGraph();
        Algorithms.recursiveDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testRecursiveDfsOnDirectedUnWeightedACyclicGraph() {
        Graph graph = GraphFactory.createDirectedUnWeightedAcyclicGraph();
        Algorithms.recursiveDfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

}
