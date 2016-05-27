package com.melimelo.graphs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.BeforeClass;
import org.junit.Test;

import com.melimelo.graphs.AdjacencyList;
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
 */
public final class AdjacencyListTest {

    private final boolean DIRECTED_EDGES = true;
    private static Set<Edge> EMPTY_EDGES_SET = Collections.<Edge> emptySet();
    private static Set<Edge> m_existingEgdes = new HashSet<Edge>();
    private static Set<Edge> m_inexistingEgdes = new HashSet<Edge>();
    private static Set<Vertex> m_existingVertices = new HashSet<Vertex>();
    private static Set<Vertex> m_inexistingVertices = new HashSet<Vertex>();

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

    @Test
    public void testCreateAdjacencyListFromDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes,
                DIRECTED_EDGES);
        verifyAdjacencyListCreation(adjacencyList, m_existingEgdes,
                m_existingVertices, DIRECTED_EDGES);
    }

    @Test
    public void testCreateAdjacencyListFromUnDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES);
        verifyAdjacencyListCreation(adjacencyList, m_existingEgdes,
                m_existingVertices, !DIRECTED_EDGES);
    }

    @Test
    public void testCreateAdjacencyListWithNoEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(EMPTY_EDGES_SET, !DIRECTED_EDGES);
        assertNotNull(adjacencyList);
        assertFalse(adjacencyList.hasDirectedEdges());

        adjacencyList = new AdjacencyList(EMPTY_EDGES_SET, DIRECTED_EDGES);
        assertNotNull(adjacencyList);
        assertTrue(adjacencyList.hasDirectedEdges());
    }

    @Test
    public void testhasDirectedEdges() {
        assertTrue(new AdjacencyList(m_existingEgdes, DIRECTED_EDGES).hasDirectedEdges());
        assertTrue(new AdjacencyList(EMPTY_EDGES_SET, DIRECTED_EDGES).hasDirectedEdges());
        assertFalse(new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES).hasDirectedEdges());
        assertFalse(new AdjacencyList(EMPTY_EDGES_SET, !DIRECTED_EDGES).hasDirectedEdges());
    }

    @Test
    public void testdjacencyListWithDirectedEdgesContainsVertex() {
        verifyContainsVertices(new AdjacencyList(m_existingEgdes, DIRECTED_EDGES), 
                m_existingVertices);
    }

    @Test
    public void testdjacencyListWithUnDirectedEdgesContainsVertex() {
        verifyContainsVertices(new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES), 
                m_existingVertices);
    }

    @Test
    public void testAdjacencyListWithNoEdgeContainsVertex() {
        AdjacencyList adjacencyList = new AdjacencyList(EMPTY_EDGES_SET, DIRECTED_EDGES);
        for (Vertex vertex : m_existingVertices) {
            assertFalse(adjacencyList.containsVertex(vertex));
        }
    }

    @Test
    public void testAdjacencyListWithDirectedEdgesContainsExistingEdge() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, DIRECTED_EDGES);
        verifyContainsEdges(adjacencyList, m_existingEgdes);
    }

    @Test
    public void testAdjacencyListWithUnDirectedEdgesContainsExistingEdge() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES);
        verifyContainsEdges(adjacencyList, m_existingEgdes);
    }

    @Test
    public void testAdjacencyListWithDirectedEdgesContainsInexistingEgde() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, DIRECTED_EDGES);
        for (Edge edge : m_inexistingEgdes) {
            assertFalse(adjacencyList.containsEdge(edge));
        }
    }

    @Test
    public void testAdjacencyListWithUnDirectedEdgesContainsInexistingEgde() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES);
        for (Edge edge : m_inexistingEgdes) {
            assertFalse(adjacencyList.containsEdge(edge));
        }
    }

    @Test
    public void testGetVertexDegreeFromAdjacencyListWithDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, DIRECTED_EDGES);
        Map<Vertex, Integer> verticesDegrees = getVerticesDegrees(
                m_existingEgdes, DIRECTED_EDGES);
        for (Vertex vertex : adjacencyList.getVertices()) {
            int expectedDegree = verticesDegrees.get(vertex);
            assertEquals(expectedDegree, adjacencyList.getVertexDegree(vertex));
        }
    }

    @Test
    public void testGetVertexDegreeFromAdjacencyListWithUnDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES);
        Map<Vertex, Integer> verticesDegrees = getVerticesDegrees(
                m_existingEgdes, !DIRECTED_EDGES);
        for (Vertex vertex : adjacencyList.getVertices()) {
            int expectedDegree = verticesDegrees.get(vertex);
            assertEquals(expectedDegree, adjacencyList.getVertexDegree(vertex));
        }
    }

    @Test
    public void testGetVertexDegreeForInexistingVertex() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, DIRECTED_EDGES);
        for (Vertex vertex : m_inexistingVertices) {
            assertEquals(AdjacencyList.UNDEFINED_DEGREE,
                    adjacencyList.getVertexDegree(vertex));
        }

        adjacencyList = new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES);
        for (Vertex vertex : m_inexistingVertices) {
            assertEquals(AdjacencyList.UNDEFINED_DEGREE,
                    adjacencyList.getVertexDegree(vertex));
        }
    }

    @Test
    public void testGetVertexDegreeFromAdjacencyListWithNoEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(EMPTY_EDGES_SET, DIRECTED_EDGES);
        for (Vertex vertex : m_existingVertices) {
            assertEquals(AdjacencyList.UNDEFINED_DEGREE,
                    adjacencyList.getVertexDegree(vertex));
        }
    }

    @Test
    public void testGetVerticesFromAdjacencyListWithDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, DIRECTED_EDGES);
        Set<Vertex> vertices = adjacencyList.getVertices();
        assertNotNull(vertices);
        assertEquals(vertices, m_existingVertices);
    }

    @Test
    public void testGetVerticesFromAdjacencyListWithUnDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES);
        Set<Vertex> vertices = adjacencyList.getVertices();
        assertNotNull(vertices);
        assertEquals(vertices, m_existingVertices);
    }

    @Test
    public void testGetVerticesFromAdjacencyListWithNoEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(EMPTY_EDGES_SET, DIRECTED_EDGES);
        Set<Vertex> vertices = adjacencyList.getVertices();
        assertNotNull(vertices);
        assertTrue(vertices.isEmpty());
    }

    @Test
    public void testGetAdjacentsVerticesFromAdjacencyListWithDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, DIRECTED_EDGES);
        Map<Vertex, Set<Vertex>> adjacents = getAdjacentsVertices(
                m_existingEgdes, DIRECTED_EDGES);
        for (Vertex vertex : adjacencyList.getVertices()) {
            assertTrue(adjacents.containsKey(vertex));
            Set<Vertex> expectedAdjacents = adjacents.get(vertex);
            assertNotNull(expectedAdjacents);
            assertEquals(expectedAdjacents,
                    adjacencyList.getAdjacentVertices(vertex));
        }
    }

    @Test
    public void testGetAdjacentsVerticesFromAdjacencyListWithUnDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES);
        Map<Vertex, Set<Vertex>> adjacents = 
                getAdjacentsVertices(m_existingEgdes, !DIRECTED_EDGES);
        for (Vertex vertex : adjacencyList.getVertices()) {
            assertTrue(adjacents.containsKey(vertex));
            Set<Vertex> expectedAdjacents = adjacents.get(vertex);
            assertNotNull(expectedAdjacents);
            assertEquals(expectedAdjacents, adjacencyList.getAdjacentVertices(vertex));
        }
    }

    @Test
    public void testGetadjacentsVerticesForInexistingVertexFromAdjacencyListWithDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, DIRECTED_EDGES);
        for (Vertex vertex : m_inexistingVertices) {
            try {
                adjacencyList.getAdjacentVertices(vertex);
            } catch (Exception exception) {
                assertTrue(exception instanceof IllegalArgumentException);
            }
        }
    }

    @Test
    public void testGetadjacentsVerticesForInexistingVertexFromAdjacencyListWithUnDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES);
        for (Vertex vertex : m_inexistingVertices) {
            try {
                adjacencyList.getAdjacentVertices(vertex);
            } catch (Exception exception) {
                assertTrue(exception instanceof IllegalArgumentException);
            }
        }
    }

    @Test
    public void testGetadjacentsVerticesForInexistingVertexFromAdjacencyListWithNoEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(EMPTY_EDGES_SET, DIRECTED_EDGES);
        for (Vertex vertex : m_inexistingVertices) {
            try {
                adjacencyList.getAdjacentVertices(vertex);
            } catch (Exception exception) {
                assertTrue(exception instanceof IllegalArgumentException);
            }
        }
    }

    @Test
    public void testToStringWithAdjacencyListWithDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, DIRECTED_EDGES);
        String adjacencyListStr = adjacencyList.toString();
        assertNotNull(adjacencyListStr);
        assertFalse(adjacencyListStr.isEmpty());
        for (Edge edge : m_existingEgdes) {
            assertTrue(adjacencyListStr.contains(edge.getStartVertex().label()));
            assertTrue(adjacencyListStr.contains(edge.getEndVertex().label()));
        }

        Map<Vertex, Set<Vertex>> verticesWithAdjacents = 
                getAdjacentsVertices(m_existingEgdes, DIRECTED_EDGES);
        for (Map.Entry<Vertex, Set<Vertex>> entry : verticesWithAdjacents.entrySet()) {
            assertNotNull(entry);
            assertTrue(adjacencyListStr.contains(entry.getKey() + " => "
                    + entry.getValue().toString()));
        }
    }

    @Test
    public void testToStringWithAdjacencyListWithUnDirectedEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(m_existingEgdes, !DIRECTED_EDGES);
        String adjacencyListStr = adjacencyList.toString();
        assertNotNull(adjacencyListStr);
        assertFalse(adjacencyListStr.isEmpty());
        for (Edge edge : m_existingEgdes) {
            assertTrue(adjacencyListStr.contains(edge.getStartVertex().label()));
            assertTrue(adjacencyListStr.contains(edge.getEndVertex().label()));
        }

        Map<Vertex, Set<Vertex>> verticesWithAdjacents = getAdjacentsVertices(
                m_existingEgdes, !DIRECTED_EDGES);
        for (Map.Entry<Vertex, Set<Vertex>> entry : verticesWithAdjacents.entrySet()) {
            assertNotNull(entry);
            assertTrue(adjacencyListStr.contains(entry.getKey() + " => "
                    + entry.getValue().toString()));
        }
    }

    @Test
    public void testToStringWithAdjacencyListWithNoEdges() {
        AdjacencyList adjacencyList = new AdjacencyList(EMPTY_EDGES_SET, !DIRECTED_EDGES);
        String adjacencyListStr = adjacencyList.toString();
        assertNotNull(adjacencyListStr);
        assertTrue(adjacencyListStr.isEmpty());
    }

    // -------------------------------------------------------------------------
    //
    // helper functions
    //
    // -------------------------------------------------------------------------

    /**
     * Check if an adjacency list has been created and initialized correctly
     * 
     * @param adjacencyList the adjacency list to check.
     * @param edges a set of edges used to create the adjacency list.
     * @param vertices a set of vertices used to create the adjacency list.
     * @param directed a flag indicating whether or not if the edges are
     *            directed.
     */
    private void verifyAdjacencyListCreation(AdjacencyList adjacencyList,
            Set<Edge> edges, Set<Vertex> vertices, boolean directed) {
        assertNotNull(adjacencyList);
        assertEquals(directed, adjacencyList.hasDirectedEdges());
        verifyContainsEdges(adjacencyList, edges);
        verifyContainsVertices(adjacencyList, vertices);
    }

    /**
     * Check if an adjacencyList contains a set of edges.
     * 
     * @param adjacencyList the adjacency list to check.
     * @param edges a set of edges for which to check the presence in the
     *            adjacency list
     */
    private void verifyContainsEdges(AdjacencyList adjacencyList, Set<Edge> edges) {
        for (Edge edge : edges) {
            assertTrue(adjacencyList.containsEdge(edge));
            if (!adjacencyList.hasDirectedEdges()) {
                // we should have the edge in the opposite direction if the
                // edges are not directed.
                assertTrue(adjacencyList.containsEdge(edge.reversed()));
            }
        }
    }

    /**
     * Check if an adjacency list contains all vertices from a set.
     * 
     * @param adjacencyList the adjacency list to be checked.
     * @param vertices a set of vertices for which to check the presence in the
     *            adjacency list
     */
    private void verifyContainsVertices(AdjacencyList adjacencyList,
            Set<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            assertTrue(adjacencyList.containsVertex(vertex));
        }
    }

    /**
     * Get adjacent vertices for each edge vertices
     * 
     * @param edges the edges with the vertices for which to get adjacent
     *            vertices
     * @param directed a flag indicating whether or not the edges are directed.
     * @return A map where the key is the vertex and the value a set of adjacent
     *         vertices.
     */
    private Map<Vertex, Set<Vertex>> getAdjacentsVertices(Set<Edge> edges, boolean directed) {
        Map<Vertex, Set<Vertex>> adjacents = new HashMap<Vertex, Set<Vertex>>();
        for (Edge edge : edges) {
            Vertex start = edge.getStartVertex();
            Vertex end = edge.getEndVertex();
            if (!adjacents.containsKey(start)) {
                adjacents.put(start, new TreeSet<Vertex>());
            }
            if (!adjacents.containsKey(end)) {
                adjacents.put(end, new TreeSet<Vertex>());
            }
            adjacents.get(start).add(end);

            if (!directed) {
                adjacents.get(end).add(start);
            }
        }
        return adjacents;
    }

    /**
     * Compute the degree of vertices of edges.
     * 
     * @param edges the edges with the vertices for which to compute the degrees
     * @param directed a flag indicating whether or not the edges are directed.
     * @return a map where the key is a vertex and the value the vertex degree.
     */
    private Map<Vertex, Integer> getVerticesDegrees(Set<Edge> edges, boolean directed) {
        Map<Vertex, Integer> verticesDegrees = new HashMap<Vertex, Integer>();
        for (Map.Entry<Vertex, Set<Vertex>> entry : getAdjacentsVertices(edges,
                directed).entrySet()) {
            verticesDegrees.put(entry.getKey(), entry.getValue().size());
        }
        return verticesDegrees;
    }

}