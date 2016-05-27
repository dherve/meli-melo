package com.melimelo.graphs.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.melimelo.graphs.Algorithms;
import com.melimelo.graphs.Graph;

/**
 * Unit tests for {@link Algorithms#bfs(Graph, com.melimelo.graphs.Vertex, com.melimelo.graphs.IAction)}
 */
public class BfsTest extends AlgorithmsTestBase {

    @Before
    public void setUp() throws Exception {
        m_action = new TraversalAction(SOURCE_VERTEX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBfsOnNullGraph() {
        Algorithms.bfs(GraphFactory.createNullGraph(), SOURCE_VERTEX, m_action);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBfsWithNullVertex() {
        Algorithms.bfs(GraphFactory.createEmptyGraph(), null, m_action);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBfsWithNullAction() {
        Algorithms.bfs(GraphFactory.createEmptyGraph(), SOURCE_VERTEX, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBfsOnEmptyGraph() {
        Algorithms.bfs(GraphFactory.createEmptyGraph(), SOURCE_VERTEX,
                m_action);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBfsOnSingletonGraph() {
        Graph graph = GraphFactory.createSingletonGraph();
        Algorithms.bfs(graph, SOURCE_VERTEX, m_action);
        assertEquals(m_action.getVertices().size(), graph.verticesCount());
        assertTrue(m_action.getVertices().containsAll(graph.getVertices()));
    }

    @Test
    public void testBfsOnUnDirectedWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedCyclicGraph();
        Algorithms.bfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testBfsOnUnDirectedUnWeightedCyclicGraph() {
        Graph graph = GraphFactory.createUnDirectedUnWeightedCyclicGraph();
        Algorithms.bfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testBfsOnUnDirectedWeightedACyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedAcyclicGraph();
        Algorithms.bfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testBfsOnUnDirectedUnWeightedACyclicGraph() {
        Graph graph = GraphFactory.createUnDirectedUnWeightedAcyclicGraph();
        Algorithms.bfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testBfsOnDirectedWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createDirectedWeightedCyclicGraph();
        Algorithms.bfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testBfsOnDirectedUnWeightedCyclicGraph() {
        Graph graph = GraphFactory.createDirectedUnWeightedCyclicGraph();
        Algorithms.bfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testBfsOnDirectedWeightedACyclicGraph() throws Exception {
        Graph graph = GraphFactory.createDirectedWeightedAcyclicGraph();
        Algorithms.bfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }

    @Test
    public void testBfsOnDirectedUnWeightedACyclicGraph() {
        Graph graph = GraphFactory.createDirectedUnWeightedAcyclicGraph();
        Algorithms.bfs(graph, SOURCE_VERTEX, m_action);
        verifyGraphTraversal(graph, m_action);
    }
}
