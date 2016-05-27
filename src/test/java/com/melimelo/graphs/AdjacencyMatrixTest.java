package com.melimelo.graphs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.melimelo.graphs.AdjacencyMatrix;
import com.melimelo.graphs.Edge;
import com.melimelo.graphs.Vertex;

/**
 * Adjacency list unit tests.
 * 
 * Edges Graph used for testing. For all tests for directed graph, all edges go
 * from the first vertex to the second vertex.
 * <pre>
 *       A,B
 *       A,D
 *       B,C
 *       B,D
 *       B,E
 *       C,F
 *       D,E
 *       D,G
 *       E,C
 *       E,F
 *       E,G
 *       F,H
 *       G,F
 *       G,I
 *       G,H
 *       H,J
 *       I,J
 * </pre>
 * 
 * The corresponding adjacency matrix looks like this (considering the graph as
 * undirected and unweighted)
 * 
 * <pre>
 *        | A | B | C | D | E | F | G | H | I | J |
 *      -------------------------------------------
 *      A |   | 1 |   | 1 |   |   |   |   |   |   |
 *      -------------------------------------------
 *      B | 1 |   | 1 |   | 1 |   |   |   |   |   |
 *      -------------------------------------------
 *      C |   | 1 |   |   |   | 1 |   |   |   |   |
 *      -------------------------------------------
 *      D | 1 |   |   |   |   |   |   |   |   |   |
 *      -------------------------------------------
 *      E |   | 1 |   |   |   | 1 | 1 |   |   |   |
 *      -------------------------------------------
 *      F |   |   | 1 |   | 1 |   | 1 | 1 |   |   |
 *      -------------------------------------------
 *      G |   |   |   |   | 1 | 1 |   | 1 | 1 |   |
 *      -------------------------------------------
 *      H |   |   |   |   |   | 1 | 1 |   |   |   |
 *      -------------------------------------------
 *      I |   |   |   |   |   |   | 1 |   |   | 1 |
 *      -------------------------------------------
 *      J |   |   |   |   |   |   |   |   | 1 |   |
 *      -------------------------------------------
 * </pre>
 */
public class AdjacencyMatrixTest {

    private final boolean DIRECTED_EDGES = true;
    private final Set<Edge> EMPTY_EDGES_SET = Collections.<Edge> emptySet();
    private final int DESCRIPTION_EDGE_STATUS_INDEX = 1;
    private final int DESCRIPTION_EDGE_VERTICES_INDEX = 0;
    private final int DESCRIPTION_VERTICES_LIST_INDEX = 0;
    private final String DESCRIPTION_LINE_STATUS_SEPARATOR = "=";
    private final String DESCRITPION_LINE_VERTICES_SEPARATOR = ",";
    private static Set<Edge> m_existingEgdes = new HashSet<Edge>();
    private static Set<Edge> m_inexistingEgdes = new HashSet<Edge>();
    private static Set<Vertex> m_existingVertices = new HashSet<Vertex>();
    private static Set<Vertex> m_inexistingVertices = new HashSet<Vertex>();
    private AdjacencyMatrix m_directedEdgesAdjacencyMatrix;
    private AdjacencyMatrix m_unDirectedEdgesAdjacencyMatrix;
    private AdjacencyMatrix m_emptyAdjacencyMatrix;

    @BeforeClass
    public static void setUpBeforeClass() {

        m_existingEgdes.add(new Edge("A", "B"));
        m_existingEgdes.add(new Edge("A", "D"));
        m_existingEgdes.add(new Edge("B", "C"));
        m_existingEgdes.add(new Edge("B", "D"));
        m_existingEgdes.add(new Edge("B", "E"));
        m_existingEgdes.add(new Edge("C", "F"));
        m_existingEgdes.add(new Edge("D", "E"));
        m_existingEgdes.add(new Edge("D", "G"));
        m_existingEgdes.add(new Edge("E", "C"));
        m_existingEgdes.add(new Edge("E", "F"));
        m_existingEgdes.add(new Edge("E", "G"));
        m_existingEgdes.add(new Edge("F", "H"));
        m_existingEgdes.add(new Edge("G", "F"));
        m_existingEgdes.add(new Edge("G", "I"));
        m_existingEgdes.add(new Edge("G", "H"));
        m_existingEgdes.add(new Edge("H", "J"));
        m_existingEgdes.add(new Edge("I", "J"));

        for (Edge edge : m_existingEgdes) {
            m_existingVertices.add(edge.getStartVertex());
            m_existingVertices.add(edge.getEndVertex());
        }

        // non existing edges with existing vertices
        m_inexistingEgdes.add(new Edge("A", "I"));
        m_inexistingEgdes.add(new Edge("J", "E"));
        m_inexistingEgdes.add(new Edge("B", "H"));
        m_inexistingEgdes.add(new Edge("H", "C"));
        m_inexistingEgdes.add(new Edge("G", "A"));
        m_inexistingEgdes.add(new Edge("D", "F"));

        // non existing edges with non existing vertices
        m_inexistingEgdes.add(new Edge("X", "Y"));
        m_inexistingEgdes.add(new Edge("U", "V"));

        for (char vertex = 'K'; vertex < 'Z'; vertex++) {
            m_inexistingVertices.add(new Vertex(String.valueOf(vertex)));
        }
    }

    @Before
    public void setUp() {
        m_directedEdgesAdjacencyMatrix = new AdjacencyMatrix(m_existingEgdes,
                DIRECTED_EDGES);
        m_unDirectedEdgesAdjacencyMatrix = new AdjacencyMatrix(m_existingEgdes,
                !DIRECTED_EDGES);
        m_emptyAdjacencyMatrix = new AdjacencyMatrix(EMPTY_EDGES_SET, DIRECTED_EDGES);
    }

    @Test
    public void testCreateAdjacencyMatrixWithDirectedEdges() {
        assertNotNull(m_directedEdgesAdjacencyMatrix);
    }

    @Test
    public void testCreateAdjacencyMatrixWithUnDirectedEdges() {
        assertNotNull(m_unDirectedEdgesAdjacencyMatrix);
    }

    @Test
    public void testCreateAdjacencyMatrixWithEmptyEgdes() {
        assertNotNull(new AdjacencyMatrix(EMPTY_EDGES_SET, DIRECTED_EDGES));
        assertNotNull(new AdjacencyMatrix(EMPTY_EDGES_SET, !DIRECTED_EDGES));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAdjacencyMatrixWithNullEdges() {
        Set<Edge> edges = new HashSet<Edge>(m_existingEgdes);
        edges.add(null);
        new AdjacencyMatrix(edges,DIRECTED_EDGES);
    }

    @Test
    public void testContainsVertex() {
        for (Vertex vertex : m_existingVertices) {
            assertFalse(m_emptyAdjacencyMatrix.containsVertex(vertex));
            assertTrue(m_directedEdgesAdjacencyMatrix.containsVertex(vertex));
            assertTrue(m_unDirectedEdgesAdjacencyMatrix.containsVertex(vertex));
        }
    }

    @Test
    public void testContainsInexistingVertex() {
        for (Vertex vertex : m_inexistingVertices) {
            assertFalse(m_emptyAdjacencyMatrix.containsVertex(vertex));
            assertFalse(m_directedEdgesAdjacencyMatrix.containsVertex(vertex));
            assertFalse(m_unDirectedEdgesAdjacencyMatrix.containsVertex(vertex));
        }
    }

    @Test
    public void testContainsNullVertex() {
        assertFalse(m_emptyAdjacencyMatrix.containsVertex(null));
        assertFalse(m_directedEdgesAdjacencyMatrix.containsVertex(null));
        assertFalse(m_unDirectedEdgesAdjacencyMatrix.containsVertex(null));
    }

    @Test
    public void testContainsEdge() {
        for (Edge edge : m_existingEgdes) {

            assertFalse(m_emptyAdjacencyMatrix.containsEdge(edge));

            assertTrue(m_directedEdgesAdjacencyMatrix.containsEdge(edge));
            assertTrue(m_unDirectedEdgesAdjacencyMatrix.containsEdge(edge));

            // According to the data graph used for testing the reversed edge
            // should be present if the graph is considered to be directed
            assertTrue(m_unDirectedEdgesAdjacencyMatrix.containsEdge(edge.reversed()));

            // but it shouldn't be there if the graph is considered not to be
            // directed
            assertFalse(m_directedEdgesAdjacencyMatrix.containsEdge(edge.reversed()));
        }
    }

    @Test
    public void testContainsInexistingEdge() {
        for (Edge edge : m_inexistingEgdes) {
            assertFalse(m_directedEdgesAdjacencyMatrix.containsEdge(edge));

            // for undirected graph both the edge and the reverse edge should
            // not be present, according to the data graph used for testing.
            assertFalse(m_unDirectedEdgesAdjacencyMatrix.containsEdge(edge));
            assertFalse(m_unDirectedEdgesAdjacencyMatrix.containsEdge(edge.reversed()));
        }
    }

    @Test
    public void containsEdgeVertices() {
        for (Vertex vertex : m_existingVertices) {
            assertFalse(m_emptyAdjacencyMatrix.containsVertex(vertex));
            assertTrue(m_directedEdgesAdjacencyMatrix.containsVertex(vertex));
            assertTrue(m_unDirectedEdgesAdjacencyMatrix.containsVertex(vertex));
        }
    }

    @Test
    public void containsEdgeWihtInexistingVertices() {
        for (Vertex vertex : m_inexistingVertices) {
            assertFalse(m_directedEdgesAdjacencyMatrix.containsVertex(vertex));
            assertFalse(m_unDirectedEdgesAdjacencyMatrix.containsVertex(vertex));
        }
    }

    @Test
    public void testGetSize() {
        assertEquals(EMPTY_EDGES_SET.size(), m_emptyAdjacencyMatrix.size());
        assertEquals(m_existingVertices.size(), m_directedEdgesAdjacencyMatrix.size());
        assertEquals(m_existingVertices.size(), m_unDirectedEdgesAdjacencyMatrix.size());
    }

    @Test
    public void testGetvertices() {
        assertNotNull(m_emptyAdjacencyMatrix.getVertices());
        assertTrue(m_emptyAdjacencyMatrix.getVertices().isEmpty());

        assertNotNull(m_directedEdgesAdjacencyMatrix.getVertices());
        assertEquals(m_existingVertices.size(), m_directedEdgesAdjacencyMatrix
                .getVertices().size());
        assertTrue(m_existingVertices
                .containsAll(m_directedEdgesAdjacencyMatrix.getVertices()));

        assertNotNull(m_unDirectedEdgesAdjacencyMatrix.getVertices());
        assertEquals(m_existingVertices.size(),
                m_unDirectedEdgesAdjacencyMatrix.getVertices().size());
        assertTrue(m_existingVertices
                .containsAll(m_unDirectedEdgesAdjacencyMatrix.getVertices()));
    }

    @Test
    public void testToStringWithUnDirectedEdgesAdjacencyMatrix() {
        verifyAdjacenctyMatrixDescription(m_directedEdgesAdjacencyMatrix, !DIRECTED_EDGES);
    }

    @Test
    public void testToStringWithDirectedEdgesAdjacencyMatrix() {
        verifyAdjacenctyMatrixDescription(m_directedEdgesAdjacencyMatrix, DIRECTED_EDGES);
    }

    private void verifyAdjacenctyMatrixDescription(AdjacencyMatrix adjacencyMatrix, 
            boolean directedEdges) {
        String description = adjacencyMatrix.toString();

        assertNotNull(description);
        assertFalse(description.isEmpty());

        String [] lines = description.split(System.getProperty("line.separator"));

        assertEquals((adjacencyMatrix.size() * adjacencyMatrix.size()) + 1, lines.length);

        // first line should contains all vertices
        verifyAdjacencyMatrixVerticesStatus(m_existingVertices, true, lines);

        // but no inexisting vertex should be there.
        verifyAdjacencyMatrixVerticesStatus(m_inexistingVertices, false, lines);

        // All existing edges should be there
        verifyAdjacencyMatrixEdgesStatus(m_existingEgdes,
                AdjacencyMatrix.EDGE_PRESENT, lines);

        // All existing edges should be there
        verifyAdjacencyMatrixEdgesStatus(m_existingEgdes,
                AdjacencyMatrix.EDGE_PRESENT, lines);

        // set the status expected for reversed edges depending of whether 
        // the edges are directed or not.
        int reversedEdgesStatus = directedEdges ? 
                AdjacencyMatrix.EGDE_MISSING : AdjacencyMatrix.EDGE_PRESENT;

        verifyAdjacencyMatrixEdgesStatus(reverseEdges(m_existingEgdes),
                reversedEdgesStatus, lines);

        // No inexisting edge should be there.
        verifyAdjacencyMatrixEdgesStatus(m_inexistingEgdes,
                AdjacencyMatrix.EGDE_MISSING, lines);        
    }
    
    @Test
    public void testToStringEmptyAdjacencyMatrix() {
        String description = m_emptyAdjacencyMatrix.toString();
        assertNotNull(description);
        assertFalse(description.isEmpty());
        assertEquals("[]", description.trim());
    }

    // -------------------------------------------------------------------------
    //
    // helper functions
    //
    // -------------------------------------------------------------------------
    
    /**
     * Check the if labels of vertices of an adjacency matrix are present or
     * missing in the description of that matrix.
     * 
     * @param vertices the adjacency matrix vertices.
     * @param expectedStatus the expected status for the vertices. Indicate
     *            whether the vertices are present or missing.
     * @param description the adjacency matrix description.
     */
    private void verifyAdjacencyMatrixVerticesStatus(final Set<Vertex> vertices,
            final boolean expectedStatus, final String [] description) {
        for (Vertex vertex : vertices) {
            assertEquals(expectedStatus,
                    description[DESCRIPTION_VERTICES_LIST_INDEX]
                            .contains(vertex.label()));
        }
    }

    /**
     * Reverse all edges in a set.
     * 
     * @param edges A set with the edges to be reversed.
     * @return A set of edges where for each original edge there's an edge with
     *         the two endpoints of the original edge reversed.
     */
    private Set<Edge> reverseEdges(final Set<Edge> edges) {
        Set<Edge> reversed = new HashSet<Edge>();
        for (Edge edge : edges) {
            reversed.add(edge.reversed());
        }
        return reversed;
    }

    /**
     * Check the if edges of an adjacency matrix are present or missing in the
     * description of that matrix.
     * 
     * @param edges the adjacency matrix edges.
     * @param expectedStatus the expected status for the edges. Indicate whether
     *            the edges are present or missing.
     * @param description the adjacency matrix description.
     */
    private void verifyAdjacencyMatrixEdgesStatus(final Set<Edge> edges,
            final int expectedStatus, final String [] description) {
        for (Edge edge : edges) {
            for (int i = 1; i < description.length; i++) {
                String [] data = description[i].split(DESCRIPTION_LINE_STATUS_SEPARATOR);
                int status = Integer.valueOf(data[DESCRIPTION_EDGE_STATUS_INDEX]);
                String [] vertices = data[DESCRIPTION_EDGE_VERTICES_INDEX]
                        .substring(1,data[DESCRIPTION_EDGE_VERTICES_INDEX].length() - 1)
                        .split(DESCRITPION_LINE_VERTICES_SEPARATOR);
                if (edge.getStartVertex().equals(vertices[0]) && 
                    edge.getStartVertex().equals(vertices[1])) {
                    assertEquals(expectedStatus, status);
                }
            }
        }
    }
}