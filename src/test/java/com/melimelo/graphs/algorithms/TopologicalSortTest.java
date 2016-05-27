package com.melimelo.graphs.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.melimelo.graphs.Algorithms;
import com.melimelo.graphs.Graph;
import com.melimelo.graphs.Vertex;

/**
 * Unit tests for
 * {@link Algorithms#topologicalSort(Graph, Vertex, com.melimelo.graphs.IAction)}
 */
public class TopologicalSortTest extends AlgorithmsTestBase {
    private final String CYCLC_GRAPH_ERROR_MESSAGE = "The graph can't contains cycle !";
    private final String UNDIRECTED_GRAPH_ERROR_MESSAGE = "The graph must be directed !";
    private String[] ORDERED_VERTICES = new String[] 
            { "A", "B", "D", "E", "G", "I", "C", "F", "H", "J" };

    @Test(expected = IllegalArgumentException.class)
    public void testTopologicalSortOnNullGraph() {
        Algorithms.topologicalSort(GraphFactory.createNullGraph(),
                SOURCE_VERTEX, m_noOpAction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTopologicalSortWithNullVertex() {
        Algorithms.topologicalSort(
                GraphFactory.createDirectedWeightedAcyclicGraph(), null,
                m_noOpAction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTopologicalSortWithNullAction() {
        Algorithms.topologicalSort(
                GraphFactory.createDirectedWeightedAcyclicGraph(),
                SOURCE_VERTEX, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTopologicalSortOnEmptyGraph() {
        Algorithms.topologicalSort(GraphFactory.createEmptyGraph(),
                SOURCE_VERTEX, m_noOpAction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTopologicalSortOnSingletonGraph() {
        Algorithms.topologicalSort(GraphFactory.createSingletonGraph(),
                SOURCE_VERTEX, m_noOpAction);
    }

    @Test
    public void testTopologicalSortOnUnDirectedWeightedCyclicGraph() {
        setUpExpectedIllegalArgumentException(UNDIRECTED_GRAPH_ERROR_MESSAGE);
        Algorithms.topologicalSort(
                GraphFactory.createUnDirectedWeightedCyclicGraph(),
                SOURCE_VERTEX, m_noOpAction);
    }

    @Test
    public void testTopologicalSortOnUnDirectedUnWeightedCyclicGraph() {
        setUpExpectedIllegalArgumentException(UNDIRECTED_GRAPH_ERROR_MESSAGE);
        Algorithms.topologicalSort(
                GraphFactory.createUnDirectedUnWeightedCyclicGraph(),
                SOURCE_VERTEX, m_noOpAction);
    }

    @Test
    public void testTopologicalSortOnUnDirectedWeightedACyclicGraph() {
        setUpExpectedIllegalArgumentException(UNDIRECTED_GRAPH_ERROR_MESSAGE);
        Algorithms.topologicalSort(
                GraphFactory.createUnDirectedWeightedAcyclicGraph(),
                SOURCE_VERTEX, m_noOpAction);
    }

    @Test
    public void testTopologicalSortOnUnDirectedUnWeightedACyclicGraph() {
        setUpExpectedIllegalArgumentException(UNDIRECTED_GRAPH_ERROR_MESSAGE);
        Algorithms.topologicalSort(
                GraphFactory.createUnDirectedUnWeightedAcyclicGraph(),
                SOURCE_VERTEX, m_noOpAction);
    }

    @Test
    public void testTopologicalSortOnDirectedWeightedCyclicGraph() {
        setupExpectedException(IllegalArgumentException.class,
                CYCLC_GRAPH_ERROR_MESSAGE);
        Algorithms.topologicalSort(
                GraphFactory.createDirectedWeightedCyclicGraph(), SOURCE_VERTEX,
                m_noOpAction);
    }

    @Test
    public void testTopologicalSortOnDirectedUnWeightedCyclicGraph() {
        setupExpectedException(IllegalArgumentException.class,
                CYCLC_GRAPH_ERROR_MESSAGE);
        Algorithms.topologicalSort(
                GraphFactory.createDirectedUnWeightedCyclicGraph(),
                SOURCE_VERTEX, m_noOpAction);
    }

    @Test
    public void testTopologicalSortOnDirectedWeightedACyclicGraph() {
        performTest(GraphFactory.createDirectedWeightedAcyclicGraph());
    }

    @Test
    public void testTopologicalSortOnDirectedUnWeightedACyclicGraph() {
        performTest(GraphFactory.createDirectedUnWeightedAcyclicGraph());
    }

    /**
     * Perform topological sort on vertices of a graph and verify if the
     * vertices are in the expected order.
     * 
     * @param graph the graph with the vertices to sort.
     */
    private void performTest(final Graph graph) {
        List<Vertex> vertices = Algorithms.topologicalSort(graph, SOURCE_VERTEX, m_noOpAction);
        assertEquals(ORDERED_VERTICES.length, vertices.size());
        for (int i = 0; i < ORDERED_VERTICES.length; i++) {
            assertTrue(graph.containsVertex(vertices.get(i)));
            assertEquals(ORDERED_VERTICES[i], vertices.get(i).label());
        }
    }
}