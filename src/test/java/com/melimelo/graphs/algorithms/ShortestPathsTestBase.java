package com.melimelo.graphs.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.melimelo.graphs.Graph;
import com.melimelo.graphs.Path;
import com.melimelo.graphs.Vertex;

/**
 * Parent class for all tests that find shortest paths on a graph.
 */
public abstract class ShortestPathsTestBase extends AlgorithmsTestBase {

    /**
     * Check if the paths returned by the execution of the algorithm correspond
     * to the expected paths.
     * 
     * @param expectedPaths the expected paths
     * @param graph the graph used for testing the algorithm.
     * @param paths a list of path returned by the algorithm.
     * @throws Exception if an error occurs while verifying the paths
     */
    protected void verifyShortestPaths(final Map<String, ShortestPath> expectedPaths,
            final Graph graph, List<Path> paths) throws Exception {
        assertFalse(paths.isEmpty());
        Set<Vertex> unreachedVertices = new HashSet<Vertex>(graph.getVertices());
        unreachedVertices.remove(SOURCE_VERTEX);

        for (Path path : paths) {
            assertEquals(path.getStart(), SOURCE_VERTEX);
            unreachedVertices.remove(path.getEnd());
            List<Vertex> vertices = path.asList();
            
            // all edges should be in the original graph, hence they all
            // should be part of the graph.
            if (vertices.size() > 1) {
                Iterator<Vertex> verticesIterator = vertices.iterator();
                Vertex start = verticesIterator.next();
                Vertex end = verticesIterator.next();
                graph.getEdge(start, end);
                while (verticesIterator.hasNext()) {
                    start = end;
                    end = verticesIterator.next();
                    graph.getEdge(start, end);
                }
            }

            ShortestPath expectedPath = expectedPaths.get(path.getEnd().label());
            assertNotNull(expectedPath);
            assertEquals(expectedPath.asList(), vertices);
        }

        // all vertices should have been reached
        assertTrue(unreachedVertices.isEmpty());
    }
}
