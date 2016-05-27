package com.melimelo.graphs.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.melimelo.graphs.Algorithms;
import com.melimelo.graphs.Graph;
import com.melimelo.graphs.Path;
import com.melimelo.graphs.Vertex;
import com.melimelo.graphs.VerticesIndexes;
/**
 * Unit tests for {@link Algorithms#allPairsShortestPaths(Graph)}
 */
public class AllPairsShortestPathsTest extends AlgorithmsTestBase {
    private final int INF = -1;

    @Test(expected = IllegalArgumentException.class)
    public void testAllPairsShortestPathsOnNullGraph() throws Exception {
        Algorithms.allPairsShortestPaths(GraphFactory.createNullGraph());
    }

    @Test
    public void testAllPairsShortestPathsOnEmptyGraph() throws Exception {
        Graph graph = GraphFactory.createEmptyGraph();
        Map<Vertex, List<Path>> paths = Algorithms.allPairsShortestPaths(graph);
        assertTrue(paths.isEmpty());
    }

    @Test
    public void testAllPairsShortestPathsOnSingletonGraph() throws Exception {
        Graph graph = GraphFactory.createSingletonGraph();
        Map<Vertex, List<Path>> paths = Algorithms.allPairsShortestPaths(graph);
        assertFalse(paths.isEmpty());
        Vertex vertex = new Vertex(GraphFactory.SINGLETON_GRAPH_VERTEX_LABEL);
        assertTrue(paths.containsKey(vertex));
        assertTrue(paths.get(vertex).isEmpty());
    }

    @Test
    public void testAllPairsShortestPathsOnUnDirectedWeightedCyclicGraph() throws Exception {
        String[][] expectedPaths = new String[][] {
            { "A", "A", "B", "B", "D", "G", "E", "J", "J", "G" }, // all paths from A  to other vertices
            { "B", "B", "B", "B", "D", "G", "E", "J", "J", "G" }, // all paths from B to  other vertices
            { "B", "C", "C", "E", "C", "C", "E", "J", "J", "G" }, // all paths from C to other vertices
            { "B", "D", "E", "D", "D", "G", "E", "J", "J", "G" }, // all paths from D to other vertices
            { "B", "D", "E", "E", "E", "G", "E", "J", "J", "G" }, // all paths from E to other vertices
            { "B", "D", "F", "E", "G", "F", "F", "F", "J", "H" }, // all paths from F to other vertices
            { "B", "D", "E", "E", "G", "G", "G", "J", "J", "G" }, // all paths from G to other vertices
            { "B", "D", "E", "E", "G", "H", "J", "H", "J", "H" }, // all paths from H to other vertices
            { "B", "D", "E", "E", "G", "H", "J", "J", "I", "I" }, // all paths from I to other vertices
            { "B", "D", "E", "E", "G", "H", "J", "J", "J", "J" }  // all paths from J to other vertices
        };
        int[][] distances = new int[][] { 
            { 0, 1, 6, 4, 6, 10, 7, 9, 12, 8 }, // all paths from A to other vertices
            { 1, 0, 5, 3, 5, 9, 6, 8, 11, 7 },  // all paths from B to other vertices
            { 6, 5, 0, 3, 1, 5, 2, 4, 7, 3 },   // all paths from C to other vertices
            { 4, 3, 3, 0, 2, 6, 3, 5, 8, 4 },   // all paths from D to other vertices
            { 6, 5, 1, 2, 0, 4, 1, 3, 6, 2 },   // all paths from E to other vertices
            { 10, 9, 5, 6, 4, 0, 3, 2, 7, 3 },  // all paths from F to other vertices
            { 7, 6, 2, 3, 1, 3, 0, 2, 5, 1 },   // all paths from G to other vertices
            { 9, 8, 4, 5, 3, 2, 2, 0, 5, 1 },   // all paths from H to other vertices
            { 12, 11, 7, 8, 6, 7, 5, 5, 0, 4 }, // all paths from I to other vertices
            { 8, 7, 3, 4, 2, 3, 1, 1, 4, 0 }    // all paths from J to other vertices
        };
        performTest(GraphFactory.createUnDirectedWeightedCyclicGraph(), expectedPaths, distances);
    }

    @Test
    public void testAllPairsShortestPathsOnUnDirectedUnWeightedCyclicGraph() throws Exception {
        String[][] expectedPaths = new String[][] {
                { "A", "A", "B", "A", "B", "C", "D", "G", "G", "H" }, // all paths from A to other vertices
                { "B", "B", "B", "B", "B", "C", "D", "F", "G", "H" }, // all paths from B to other vertices
                { "B", "C", "C", "B", "C", "C", "E", "F", "G", "H" }, // all paths from C to other vertices
                { "D", "D", "B", "D", "D", "E", "D", "G", "G", "H" }, // all paths from D to other vertices
                { "B", "E", "E", "E", "E", "E", "E", "F", "G", "H" }, // all paths from E to other vertices
                { "B", "C", "F", "E", "F", "F", "F", "F", "G", "H" }, // all paths from F to other vertices
                { "D", "D", "E", "G", "G", "G", "G", "G", "G", "H" }, // all paths from G to other vertices
                { "D", "C", "F", "G", "F", "H", "H", "H", "G", "H" }, // all paths from H to other vertices
                { "D", "D", "E", "G", "G", "G", "I", "G", "I", "I" }, // all paths from I to other vertices
                { "D", "C", "F", "G", "F", "H", "H", "J", "J", "J" }  // all paths from J to other vertices
        };

        int[][] distances = new int[][] {
                { 0, 1, 2, 1, 2, 3, 2, 3, 3, 4 }, // all paths from A to other vertices
                { 1, 0, 1, 1, 1, 2, 2, 3, 3, 4 }, // all paths from B to other vertices
                { 2, 1, 0, 2, 1, 1, 2, 2, 3, 3 }, // all paths from C to other vertices
                { 1, 1, 2, 0, 1, 2, 1, 2, 2, 3 }, // all paths from D to other vertices
                { 2, 1, 1, 1, 0, 1, 1, 2, 2, 3 }, // all paths from E to other vertices
                { 3, 2, 1, 2, 1, 0, 1, 1, 2, 2 }, // all paths from F to other vertices
                { 2, 2, 2, 1, 1, 1, 0, 1, 1, 2 }, // all paths from G to other vertices
                { 3, 3, 2, 2, 2, 1, 1, 0, 2, 1 }, // all paths from H to other vertices
                { 3, 3, 3, 2, 2, 2, 1, 2, 0, 1 }, // all paths from I to other vertices
                { 4, 4, 3, 3, 3, 2, 2, 1, 1, 0 }  // all paths from J to other vertices
        };
        performTest(GraphFactory.createUnDirectedUnWeightedCyclicGraph(), expectedPaths, distances);
    }

    @Test
    public void testAllPairsShortestPathsOnUnDirectedWeightedAcyclicGraph() throws Exception {
        String[][] expectedPaths = new String[][] {
                { "A", "A", "B", "A", "B", "E", "E", "G", "J", "H" }, // all paths from A to other vertices
                { "B", "B", "B", "A", "B", "E", "E", "G", "J", "H" }, // all paths from B to other vertices
                { "B", "C", "C", "A", "B", "E", "E", "G", "J", "H" }, // all paths from C to other vertices
                { "D", "A", "B", "D", "B", "E", "E", "G", "J", "H" }, // all paths from D to other vertices
                { "B", "E", "B", "A", "E", "E", "E", "G", "J", "H" }, // all paths from E to other vertices
                { "B", "E", "B", "A", "F", "F", "E", "G", "J", "H" }, // all paths from F to other vertices
                { "B", "E", "B", "A", "G", "E", "G", "G", "J", "H" }, // all paths from G to other vertices
                { "B", "E", "B", "A", "G", "E", "H", "H", "J", "H" }, // all paths from H to other vertices
                { "B", "E", "B", "A", "G", "E", "H", "J", "I", "I" }, // all paths from I to other vertices
                { "B", "E", "B", "A", "G", "E", "H", "J", "J", "J" }  // all paths from J to other vertices
        };
        int[][] distances = new int[][] {
                { 0, 1, 6, 8, 11, 20, 12, 21, 26, 22 },   // all paths from A to other vertices
                { 1, 0, 5, 9, 10, 19, 11, 20, 25, 21 },   // all paths from B to other vertices
                { 6, 5, 0, 14, 15, 24, 16, 25, 30, 26 },  // all paths from C to other vertices
                { 8, 9, 14, 0, 19, 28, 20, 29, 34, 30 },  // all paths from D to other vertices
                { 11, 10, 15, 19, 0, 9, 1, 10, 15, 11 },  // all paths from E to other vertices
                { 20, 19, 24, 28, 9, 0, 10, 19, 24, 20 }, // all paths from F to other vertices
                { 12, 11, 16, 20, 1, 10, 0, 9, 14, 10 },  // all paths from G to other vertices
                { 21, 20, 25, 29, 10, 19, 9, 0, 5, 1 },   // all paths from H to other vertices
                { 26, 25, 30, 34, 15, 24, 14, 5, 0, 4 },  // all paths from I to other vertices
                { 22, 21, 26, 30, 11, 20, 10, 1, 4, 0 }   // all paths from J to other vertices
        };
        performTest(GraphFactory.createUnDirectedWeightedAcyclicGraph(), expectedPaths, distances);
    }

    @Test
    public void testAllPairsShortestPathsOnUnDirectedUnWeightedAcyclicGraph() throws Exception {
        String[][] expectedPaths = new String[][] {
                { "A", "A", "B", "A", "B", "E", "E", "G", "J", "H" }, // all paths from A to other vertices
                { "B", "B", "B", "A", "B", "E", "E", "G", "J", "H" }, // all paths from B to other vertices
                { "B", "C", "C", "A", "B", "E", "E", "G", "J", "H" }, // all paths from C to other vertices
                { "D", "A", "B", "D", "B", "E", "E", "G", "J", "H" }, // all paths from D to other vertices
                { "B", "E", "B", "A", "E", "E", "E", "G", "J", "H" }, // all paths from E to other vertices
                { "B", "E", "B", "A", "F", "F", "E", "G", "J", "H" }, // all paths from F to other vertices
                { "B", "E", "B", "A", "G", "E", "G", "G", "J", "H" }, // all paths from G to other vertices
                { "B", "E", "B", "A", "G", "E", "H", "H", "J", "H" }, // all paths from H to other vertices
                { "B", "E", "B", "A", "G", "E", "H", "J", "I", "I" }, // all paths from I to other vertices
                { "B", "E", "B", "A", "G", "E", "H", "J", "J", "J" }  // all paths from J to other vertices
        };
        int[][] distances = new int[][] {
                { 0, 1, 2, 1, 2, 3, 3, 4, 6, 5 }, // all paths from A to other vertices
                { 1, 0, 1, 2, 1, 2, 2, 3, 5, 4 }, // all paths from B to other vertices
                { 2, 1, 0, 3, 2, 3, 3, 4, 6, 5 }, // all paths from C to other vertices
                { 1, 2, 3, 0, 3, 4, 4, 5, 7, 6 }, // all paths from D to other vertices
                { 2, 1, 2, 3, 0, 1, 1, 2, 4, 3 }, // all paths from E to other vertices
                { 3, 2, 3, 4, 1, 0, 2, 3, 5, 4 }, // all paths from F to other vertices
                { 3, 2, 3, 4, 1, 2, 0, 1, 3, 2 }, // all paths from G to other vertices
                { 4, 3, 4, 5, 2, 3, 1, 0, 2, 1 }, // all paths from H to other vertices
                { 6, 5, 6, 7, 4, 5, 3, 2, 0, 1 }, // all paths from I to other vertices
                { 5, 4, 5, 6, 3, 4, 2, 1, 1, 0 }  // all paths from J to other vertices
        };
        performTest(GraphFactory.createUnDirectedUnWeightedAcyclicGraph(), expectedPaths, distances);
    }

    @Test
    public void testAllPairsShortestPathsOnDirectedWeightedCyclicGraph() throws Exception {
        String[][] expectedPaths = new String[][] {
                { "A", "A", "B", "B", "D", "G", "E", "F", "G", "H" }, // all paths from A to other vertices
                { "-", "B", "B", "B", "D", "G", "E", "F", "G", "H" }, // all paths from B to other vertices
                { "-", "-", "C", "-", "-", "C", "J", "F", "G", "H" }, // all paths from C to other vertices
                { "-", "-", "E", "D", "D", "G", "E", "F", "G", "H" }, // all paths from D to other vertices
                { "-", "-", "E", "-", "E", "G", "E", "F", "G", "H" }, // all paths from E to other vertices
                { "-", "-", "-", "-", "-", "F", "J", "F", "G", "H" }, // all paths from F to other vertices
                { "-", "-", "-", "-", "-", "G", "G", "F", "G", "H" }, // all paths from G to other vertices
                { "-", "-", "-", "-", "-", "G", "J", "H", "G", "H" }, // all paths from H to other vertices
                { "-", "-", "-", "-", "-", "G", "J", "F", "I", "I" }, // all paths from I to other vertices
                { "-", "-", "-", "-", "-", "G", "J", "F", "G", "J" }  // all paths from J to other vertices
        };
        int[][] distances = new int[][] {
                { 0, 1, 6, 4, 6, 10, 7, 12, 13, 13 },        // all paths from A to other vertices
                { INF, 0, 5, 3, 5, 9, 6, 11, 12, 12 },       // all paths from B to other vertices
                { INF, INF, 0, INF, INF, 5, 9, 7, 15, 8 },   // all paths from C to other vertices
                { INF, INF, 3, 0, 2, 6, 3, 8, 9, 9 },        // all paths from D to other vertices
                { INF, INF, 1, INF, 0, 4, 1, 6, 7, 7 },      // all paths from E to other vertices
                { INF, INF, INF, INF, INF, 0, 4, 2, 10, 3 }, // all paths from A to other vertices
                { INF, INF, INF, INF, INF, 3, 0, 5, 6, 6 },  // all paths from G to other vertices
                { INF, INF, INF, INF, INF, 5, 2, 0, 8, 1 },  // all paths from H to other vertices
                { INF, INF, INF, INF, INF, 8, 5, 10, 0, 4 }, // all paths from I to other vertices
                { INF, INF, INF, INF, INF, 4, 1, 6, 7, 0 }   // all paths from J to other vertices
        };
        performTest(GraphFactory.createDirectedWeightedCyclicGraph(), expectedPaths, distances);
    }

    @Test
    public void testAllPairsShortestPathsOnDirectedUnWeightedCyclicGraph() throws Exception {
        String[][] expectedPaths = new String[][] {
                { "A", "A", "B", "A", "B", "C", "D", "G", "G", "H" }, // all paths from A to other vertices
                { "-", "B", "B", "B", "B", "C", "D", "F", "G", "H" }, // all paths from B to other vertices
                { "-", "-", "C", "-", "-", "C", "J", "F", "G", "H" }, // all paths from C to other vertices
                { "-", "-", "E", "D", "D", "E", "D", "G", "G", "H" }, // all paths from D to other vertices
                { "-", "-", "E", "-", "E", "E", "E", "F", "G", "H" }, // all paths from E to other vertices
                { "-", "-", "-", "-", "-", "F", "J", "F", "G", "H" }, // all paths from F to other vertices
                { "-", "-", "-", "-", "-", "G", "G", "G", "G", "H" }, // all paths from G to other vertices
                { "-", "-", "-", "-", "-", "G", "J", "H", "G", "H" }, // all paths from H to other vertices
                { "-", "-", "-", "-", "-", "G", "J", "G", "I", "I" }, // all paths from I to other vertices
                { "-", "-", "-", "-", "-", "G", "J", "G", "G", "J" }  // all paths from J to other vertices
        };
        int[][] distances = new int[][] {
                { 0, 1, 2, 1, 2, 3, 2, 3, 3, 4 },           // all paths from A to other vertices
                { INF, 0, 1, 1, 1, 2, 2, 3, 3, 4 },         // all paths from B to other vertices
                { INF, INF, 0, INF, INF, 1, 4, 2, 5, 3 },   // all paths from C to other vertices
                { INF, INF, 2, 0, 1, 2, 1, 2, 2, 3 },       // all paths from D to other vertices
                { INF, INF, 1, INF, 0, 1, 1, 2, 2, 3 },     // all paths from E to other vertices
                { INF, INF, INF, INF, INF, 0, 3, 1, 4, 2 }, // all paths from F to other vertices
                { INF, INF, INF, INF, INF, 1, 0, 1, 1, 2 }, // all paths from G to other vertices
                { INF, INF, INF, INF, INF, 3, 2, 0, 3, 1 }, // all paths from H to other vertices
                { INF, INF, INF, INF, INF, 3, 2, 3, 0, 1 }, // all paths from I to other vertices
                { INF, INF, INF, INF, INF, 2, 1, 2, 2, 0 }  // all paths from J to other vertices
        };
        performTest(GraphFactory.createDirectedUnWeightedCyclicGraph(), expectedPaths, distances);
    }

    @Test
    public void testAllPairsShortestPathsOnDirectedWeightedAcyclicGraph() throws Exception {
        String[][] expectedPaths = new String[][] {
                { "A", "A", "B", "B", "D", "G", "E", "F", "G", "H" }, // all paths from A to other vertices
                { "-", "B", "B", "B", "D", "G", "E", "F", "G", "H" }, // all paths from B to other vertices
                { "-", "-", "C", "-", "-", "C", "-", "F", "-", "H" }, // all paths from C to other vertices
                { "-", "-", "E", "D", "D", "G", "E", "F", "G", "H" }, // all paths from D to other vertices
                { "-", "-", "E", "-", "E", "G", "E", "F", "G", "H" }, // all paths from E to other vertices
                { "-", "-", "-", "-", "-", "F", "-", "F", "-", "H" }, // all paths from F to other vertices
                { "-", "-", "-", "-", "-", "G", "G", "F", "G", "H" }, // all paths from G to other vertices
                { "-", "-", "-", "-", "-", "-", "-", "H", "-", "H" }, // all paths from H to other vertices
                { "-", "-", "-", "-", "-", "-", "-", "-", "I", "I" }, // all paths from I to other vertices
                { "-", "-", "-", "-", "-", "-", "-", "-", "-", "J" }  // all paths from A to other vertices
        };
        int[][] distances = new int[][] {
                { 0, 1, 6, 4, 6, 10, 7, 12, 13, 13 },              // all paths from A to other vertices
                { INF, 0, 5, 3, 5, 9, 6, 11, 12, 12 },             // all paths from B to other vertices
                { INF, INF, 0, INF, INF, 5, INF, 7, INF, 8 },      // all paths from C to other vertices
                { INF, INF, 3, 0, 2, 6, 3, 8, 9, 9 },              // all paths from D to other vertices
                { INF, INF, 1, INF, 0, 4, 1, 6, 7, 7 },            // all paths from E to other vertices
                { INF, INF, INF, INF, INF, 0, 1, 2, 1, 3 },        // all paths from F to other vertices
                { INF, INF, INF, INF, INF, 3, 0, 5, 6, 6 },        // all paths from G to other vertices
                { INF, INF, INF, INF, INF, INF, INF, 0, 1, 1 },    // all paths from H to other vertices
                { INF, INF, INF, INF, INF, INF, INF, INF, 0, 4 },  // all paths from I to other vertices
                { INF, INF, INF, INF, INF, INF, INF, INF, INF, 0 } // all paths from J to other vertices
        };
        performTest(GraphFactory.createDirectedWeightedAcyclicGraph(), expectedPaths, distances);
    }

    @Test
    public void testAllPairsShortestPathsOnDirectedUnWeightedAcyclicGraph() throws Exception {
        String[][] expectedPaths = new String[][] {
                { "A", "A", "B", "A", "B", "C", "D", "G", "G", "H" }, // all paths from A to other vertices
                { "-", "B", "B", "B", "B", "C", "D", "F", "G", "H" }, // all paths from B to other vertices
                { "-", "-", "C", "-", "-", "C", "-", "F", "-", "H" }, // all paths from C to other vertices
                { "-", "-", "E", "D", "D", "E", "D", "G", "G", "H" }, // all paths from D to other vertices
                { "-", "-", "E", "-", "E", "E", "E", "F", "G", "H" }, // all paths from E to other vertices
                { "-", "-", "-", "-", "-", "F", "-", "F", "-", "H" }, // all paths from F to other vertices
                { "-", "-", "-", "-", "-", "G", "G", "G", "G", "H" }, // all paths from G to other vertices
                { "-", "-", "-", "-", "-", "-", "-", "H", "-", "H" }, // all paths from H to other vertices
                { "-", "-", "-", "-", "-", "-", "-", "-", "I", "I" }, // all paths from I to other vertices
                { "-", "-", "-", "-", "-", "-", "-", "-", "-", "J" }  // all paths from J to other vertices
        };
        int[][] distances = new int[][] {
                { 0, 1, 2, 1, 2, 3, 2, 3, 3, 4 },                  // all paths from A to other vertices
                { INF, 0, 1, 1, 1, 2, 2, 3, 3, 4 },                // all paths from B to other vertices
                { INF, INF, 0, INF, INF, 1, INF, 2, INF, 3 },      // all paths from C to other vertices
                { INF, INF, 2, 0, 1, 2, 1, 2, 2, 3 },              // all paths from D to other vertices
                { INF, INF, 1, INF, 0, 1, 1, 2, 2, 3 },            // all paths from E to other vertices
                { INF, INF, INF, INF, INF, 0, INF, 1, INF, 2 },    // all paths from F to other vertices
                { INF, INF, INF, INF, INF, 1, 0, 1, 1, 2 },        // all paths from G to other vertices
                { INF, INF, INF, INF, INF, INF, INF, 0, INF, 1 },  // all paths from H to other vertices
                { INF, INF, INF, INF, INF, INF, INF, INF, 0, 1 },  // all paths from I to other vertices
                { INF, INF, INF, INF, INF, INF, INF, INF, INF, 0 } // all paths from J to other vertices
        };
        performTest(GraphFactory.createDirectedUnWeightedAcyclicGraph(), expectedPaths, distances);
    }

    /**
     * Run {@link Algorithms#allPairsShortestPaths(Graph)} on the provided graph
     * and verify if the paths returned correspond to the expected paths and
     * have the expected distances.
     * 
     * @param graph the graph on which to run the algorithm.
     * @param expectedPaths the expected paths.
     * @param distances the expected distances for each of the paths returned
     * @throws Exception if an error occurs while performing the text
     */
    private void performTest(final Graph graph, final String[][] expectedPaths, 
            final int[][] distances) throws Exception {
        Map<Vertex, List<Path>> paths = Algorithms.allPairsShortestPaths(graph);
        assertEquals(graph.verticesCount(), paths.size());
        VerticesIndexes verticesIndexes = new VerticesIndexes(graph.getVertices());
        for (Vertex vertex : graph.getVertices()) {
            List<Path> vertexPaths = paths.get(vertex);
            assertNotNull(vertexPaths);
            int vertexIndex = verticesIndexes.getIndex(vertex);
            for (Path path : vertexPaths) {
                Vertex from = path.getStart();
                assertEquals(vertex, from);
                Vertex[] verticesPath = path.asList().toArray(new Vertex[0]);

                // check the path vertices
                for (int i = verticesPath.length - 1; i > 0; i--) {
                    Vertex to = verticesPath[i];
                    int indexTo = verticesIndexes.getIndex(to);
                    Vertex intermediate = verticesPath[i - 1];
                    assertEquals(expectedPaths[vertexIndex][indexTo], intermediate.label());
                }

                // check the path distance
                int indexTo = verticesIndexes.getIndex(path.getEnd());
                assertEquals(BigDecimal.valueOf(distances[vertexIndex][indexTo]), path.length());
            }
        }
    }
}
