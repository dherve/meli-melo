package com.melimelo.graphs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.melimelo.graphs.AdjacencyList;
import com.melimelo.graphs.AdjacencyMatrix;
import com.melimelo.graphs.Edge;
import com.melimelo.graphs.EdgeNotFoundException;
import com.melimelo.graphs.Graph;
import com.melimelo.graphs.Vertex;
import com.melimelo.graphs.VertexNotFoundException;

/**
 * Graph unit tests.
 * 
 * Edges Graph used for testing. For all tests for directed graph, all edges go
 * from the first vertex to the second vertex.
 * 
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
public class GraphTest {
    private final boolean GRAPH_WEIGHTED = true;
    private final boolean DIRECTED_EDGES = true;
    private final Set<Edge> EMPTY_EDGES_SET = Collections.<Edge> emptySet();
    private final Set<Vertex> EMPTY_VERTICES_SET = Collections
            .<Vertex> emptySet();
    private Set<Edge> m_unWeightedEgdes = new HashSet<Edge>();
    private Set<Edge> m_weightedEgdes = new HashSet<Edge>();
    private Set<Edge> m_inexistingEgdes = new HashSet<Edge>();
    private Set<Vertex> m_vertices = new HashSet<Vertex>();
    private Set<Vertex> m_inexistingVertices = new HashSet<Vertex>();

    private Graph m_emptyGraph;
    private Graph m_weightedDirectedGraph;
    private Graph m_unWeightedDirectedGraph;
    private Graph m_weightedUnDirectedGraph;
    private Graph m_unWeightedUnDirectedGraph;

    @Before
    public void setUp() {
        m_unWeightedEgdes = new HashSet<Edge>();
        m_weightedEgdes = new HashSet<Edge>();
        m_inexistingEgdes = new HashSet<Edge>();
        m_vertices = new HashSet<Vertex>();
        m_inexistingVertices = new HashSet<Vertex>();

        m_unWeightedEgdes.add(new Edge("A", "B"));
        m_unWeightedEgdes.add(new Edge("A", "D"));
        m_unWeightedEgdes.add(new Edge("B", "C"));
        m_unWeightedEgdes.add(new Edge("B", "D"));
        m_unWeightedEgdes.add(new Edge("B", "E"));
        m_unWeightedEgdes.add(new Edge("C", "F"));
        m_unWeightedEgdes.add(new Edge("D", "E"));
        m_unWeightedEgdes.add(new Edge("D", "G"));
        m_unWeightedEgdes.add(new Edge("E", "C"));
        m_unWeightedEgdes.add(new Edge("E", "F"));
        m_unWeightedEgdes.add(new Edge("E", "G"));
        m_unWeightedEgdes.add(new Edge("F", "H"));
        m_unWeightedEgdes.add(new Edge("G", "F"));
        m_unWeightedEgdes.add(new Edge("G", "I"));
        m_unWeightedEgdes.add(new Edge("G", "H"));
        m_unWeightedEgdes.add(new Edge("H", "J"));
        m_unWeightedEgdes.add(new Edge("I", "J"));

        int weight = 1;
        for (Edge edge : m_unWeightedEgdes) {
            m_vertices.add(edge.getStartVertex());
            m_vertices.add(edge.getEndVertex());

            m_weightedEgdes.add(new Edge(edge.getStartVertex(), edge
                    .getEndVertex(), new BigDecimal(weight++)));
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

        for (char vertex = 'L'; vertex < 'Z'; vertex++) {
            m_inexistingVertices.add(new Vertex(String.valueOf(vertex)));
        }
        m_emptyGraph = new Graph(DIRECTED_EDGES, GRAPH_WEIGHTED);
        m_weightedDirectedGraph = new Graph(DIRECTED_EDGES, GRAPH_WEIGHTED, m_weightedEgdes, m_vertices);
        m_unWeightedDirectedGraph = new Graph(DIRECTED_EDGES, !GRAPH_WEIGHTED, m_unWeightedEgdes, m_vertices);
        m_weightedUnDirectedGraph = new Graph(!DIRECTED_EDGES, GRAPH_WEIGHTED, m_weightedEgdes, m_vertices);
        m_unWeightedUnDirectedGraph = new Graph(!DIRECTED_EDGES, !GRAPH_WEIGHTED, m_unWeightedEgdes, m_vertices);
    }

    @Test
    public void testCreateGraphWithoutVertices() {
        assertNotNull(m_emptyGraph);
        assertTrue(m_emptyGraph.getEdges().isEmpty());
        assertTrue(m_emptyGraph.getVertices().isEmpty());
    }

    @Test
    public void testCreateSingletonGraph() throws Exception {
        Set<Vertex> vertices = new HashSet<Vertex>();
        Vertex uniqueVertex = new Vertex("A");
        vertices.add(uniqueVertex);
        Graph graph = new Graph(DIRECTED_EDGES,GRAPH_WEIGHTED, Collections.<Edge>emptySet(), vertices);
        assertNotNull(graph);
        assertTrue(graph.getEdges().isEmpty());
        assertEquals(1, graph.getVertices().size());
        assertEquals(uniqueVertex, graph.getVertex(uniqueVertex.label()));
        
    }

    @Test
    public void testCreateDirectedUnWeightedGraphUsingEdgesOnly() {
        Graph graph = new Graph(DIRECTED_EDGES, !GRAPH_WEIGHTED, m_unWeightedEgdes);
        assertNotNull(graph);
        assertTrue(graph.isDirected());
        assertFalse(graph.isWeighted());
        assertTrue(graph.getEdges().containsAll(m_unWeightedEgdes));
        verifyGraphContainsEdges(graph, m_unWeightedEgdes);
    }
    
    @Test
    public void testCreateDirectedWeightedGraphUsingEdgesOnly() {
        Graph graph = new Graph(DIRECTED_EDGES, GRAPH_WEIGHTED, m_weightedEgdes);
        assertNotNull(graph);
        assertTrue(graph.isDirected());
        assertTrue(graph.isWeighted());
        assertTrue(graph.getEdges().containsAll(m_weightedEgdes));
        verifyGraphContainsEdges(graph, m_weightedEgdes);
    }

    
    @Test
    public void testCreateDirectedGraph() {
        assertNotNull(m_weightedDirectedGraph);
        assertTrue(m_weightedDirectedGraph.isDirected());
        assertNotNull(m_unWeightedDirectedGraph);
        assertTrue(m_unWeightedDirectedGraph.isDirected());
    }

    @Test
    public void testCreateWeightedGraph() {
        assertNotNull(m_weightedDirectedGraph);
        assertTrue(m_weightedDirectedGraph.isWeighted());
        assertNotNull(m_weightedUnDirectedGraph);
        assertTrue(m_weightedUnDirectedGraph.isWeighted());
    }

    @Test
    public void testCreateGraphWithVerticesAndEdges() {
        assertNotNull(m_weightedDirectedGraph);
        assertNotNull(m_unWeightedDirectedGraph);
        assertNotNull(m_weightedUnDirectedGraph);
        assertNotNull(m_unWeightedUnDirectedGraph);
    }

    @Test
    public void testIsDirected() {
        assertTrue(m_weightedDirectedGraph.isDirected());
        assertTrue(m_unWeightedDirectedGraph.isDirected());
        assertFalse(m_weightedUnDirectedGraph.isDirected());
        assertFalse(m_unWeightedUnDirectedGraph.isDirected());
    }

    @Test
    public void testIsWeighted() {
        assertTrue(m_weightedDirectedGraph.isWeighted());
        assertFalse(m_unWeightedDirectedGraph.isWeighted());
        assertTrue(m_weightedUnDirectedGraph.isWeighted());
        assertFalse(m_unWeightedUnDirectedGraph.isWeighted());
    }

    
    //-------------------------------------------------------------------------
    //
    //  Tests for getEdge(Vertex start, Vertex end) 
    //
    //-------------------------------------------------------------------------
    @Test
    public void testGetEdgeFromUnWeightedDirectedGraph() throws Exception {
        for (Edge edge : m_unWeightedEgdes) {
            assertEquals(edge, m_unWeightedDirectedGraph.getEdge(edge.getStartVertex(), edge.getEndVertex()));
        }
    }

    @Test
    public void testGetEdgeFromUnWeightedUnDirectedGraph() throws Exception {
        for (Edge edge : m_unWeightedEgdes) {
            assertEquals(edge, m_unWeightedUnDirectedGraph.getEdge(edge.getStartVertex(), edge.getEndVertex()));
            Edge reversed = edge.reversed();
            assertEquals(reversed,m_unWeightedUnDirectedGraph.getEdge(reversed.getStartVertex(), reversed.getEndVertex()));
        }
    }

    @Test
    public void testGetEdgeFromWeightedDirectedGraph() throws Exception {
        for (Edge edge : m_weightedEgdes) {
            assertEquals(edge, m_weightedDirectedGraph.getEdge(edge.getStartVertex(), edge.getEndVertex()));
        }
    }

    @Test
    public void testGetEdgeFromWeightedUnDirectedGraph() throws Exception {
        for (Edge edge : m_weightedEgdes) {
            assertEquals(edge, m_weightedUnDirectedGraph.getEdge(edge.getStartVertex(), edge.getEndVertex()));
            Edge reversed = edge.reversed();
            assertEquals(reversed, m_weightedUnDirectedGraph.getEdge(reversed.getStartVertex(), reversed.getEndVertex()));
        }
    }

    @Test(expected = EdgeNotFoundException.class)
    public void testGetEdgeFromEmptyGraph() throws Exception {
        Edge edge = m_weightedEgdes.iterator().next();
        m_emptyGraph.getEdge(edge.getStartVertex(), edge.getEndVertex());
    }

    @Test(expected = EdgeNotFoundException.class)
    public void testGetInexistingEdgeFromEmptyGraph() throws Exception {
        Edge edge = m_inexistingEgdes.iterator().next();
        m_emptyGraph.getEdge(edge.getStartVertex(), edge.getEndVertex());
    }

    
    //-------------------------------------------------------------------------
    //
    //  Tests for getEdges() 
    //
    //-------------------------------------------------------------------------
    @Test
    public void testGetEdgesFromEmptyGraph() {
        assertTrue(m_emptyGraph.getEdges().isEmpty());
    }

    @Test
    public void testGetEdgesFromUnWeightedDirectedGraph() {
        verifyGraphEdgesRetrieval(m_unWeightedDirectedGraph, m_unWeightedEgdes);
    }

    @Test
    public void testGetEdgesFromUnWeightedUnDirectedGraph() {
        verifyGraphEdgesRetrieval(m_unWeightedUnDirectedGraph, m_unWeightedEgdes);
    }

    @Test
    public void testGetEdgesFromWeightedDirectedGraph() {
        verifyGraphEdgesRetrieval(m_weightedDirectedGraph, m_weightedEgdes);
    }

    @Test
    public void testGetEdgesFromWeightedUnDirectedGraph() {
        verifyGraphEdgesRetrieval(m_weightedUnDirectedGraph, m_weightedEgdes);
    }
    
    
    //-------------------------------------------------------------------------
    //
    //  Tests for verticesCount()
    //
    //-------------------------------------------------------------------------
    @Test
    public void testGetVerticesCountFromUnWeightedDirectedGraph() {
        assertEquals(m_vertices.size(), m_unWeightedDirectedGraph.verticesCount());
    }

    @Test
    public void testGetVerticesCountFromUnWeightedUnDirectedGraph() {
        assertEquals(m_vertices.size(), m_unWeightedUnDirectedGraph.verticesCount());
    }
    
    @Test
    public void testGetVerticesCountFromWeightedDirectedGraph() {
        assertEquals(m_vertices.size(), m_weightedDirectedGraph.verticesCount());
    }
    
    @Test
    public void testGetVerticesCountFromWeightedUnDirectedGraph() {
        assertEquals(m_vertices.size(), m_weightedUnDirectedGraph.verticesCount());
    }

    //-------------------------------------------------------------------------
    //
    //  Tests for edgesCount()
    //
    //-------------------------------------------------------------------------
    @Test
    public void testGetEdgesCountFromWeightedDirectedGraph() {
        assertEquals(m_weightedEgdes.size(), m_weightedDirectedGraph.edgesCount());
    }

    @Test
    public void testGetEdgesCountFromUnWeightedDirectedGraph() {
        assertEquals(m_unWeightedEgdes.size(), m_unWeightedDirectedGraph.edgesCount());
    }

    @Test
    public void testGetEdgesCountFromUnWeightedUnDirectedGraph() {
        assertEquals(m_unWeightedEgdes.size() * 2, m_unWeightedUnDirectedGraph.edgesCount());
    }
    
    @Test
    public void testGetEdgesCountFromWeightedUnDirectedGraph() {
        assertEquals(m_weightedEgdes.size() * 2, m_weightedUnDirectedGraph.edgesCount());
    }

    
    //-------------------------------------------------------------------------
    //
    //  Tests for addEdge(Edge edge)
    //
    //-------------------------------------------------------------------------
    @Test
    public void testAddDuplicateEdgeToUnWeightedDirectedGraph() {
        verifyDuplicateEdgesAdded(m_unWeightedDirectedGraph, m_unWeightedEgdes);
    }

    @Test
    public void testAddDuplicateEdgeToUnWeightedUnDirectedGraph() {
        verifyDuplicateEdgesAdded(m_unWeightedUnDirectedGraph, m_unWeightedEgdes);
    }
    
    @Test
    public void testAddDuplicateEdgeToWeightedDirectedGraph() {
        verifyDuplicateEdgesAdded(m_weightedDirectedGraph, m_weightedEgdes);
    }

    @Test
    public void testAddDuplicateEdgeToWeightedUnDirectedGraph() {
        verifyDuplicateEdgesAdded(m_weightedUnDirectedGraph, m_weightedEgdes);
    }

    @Test
    public void testAddEdgeToUnWeightedDirectedGraph() {
        verifyNewEdgeAdded(m_unWeightedDirectedGraph, m_inexistingEgdes);
    }

    @Test
    public void testAddEdgeToUnWeightedUnDirectedGraph() {
        verifyNewEdgeAdded(m_unWeightedUnDirectedGraph, m_inexistingEgdes);
    }
    
    @Test
    public void testAddEdgeToWeightedDirectedGraph() {
        verifyNewEdgeAdded(m_weightedDirectedGraph, m_inexistingEgdes);
    }

    @Test
    public void testAddEdgeToWeightedUnDirectedGraph() {
        verifyNewEdgeAdded(m_weightedUnDirectedGraph, m_inexistingEgdes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullEdgeToUnWeightedDirectedGraph() {
        m_unWeightedDirectedGraph.addEdge(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullEdgeToUnWeightedUnDirectedGraph() {
        m_unWeightedUnDirectedGraph.addEdge(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullEdgeToWeightedUnDirectedGraph() {
        m_weightedUnDirectedGraph.addEdge(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullEdgeToWeightedDirectedGraph() {
        m_weightedDirectedGraph.addEdge(null);
    }

  //-------------------------------------------------------------------------
    //
    //  Tests for addEdges(Set<Edge> edges)
    //
    //-------------------------------------------------------------------------
    @Test
    public void testAddEdgesToUnWeightedDirectedGraphFromEmptySet() {
        verifyEdgesAddedFromEmptySet(m_unWeightedDirectedGraph);
    }

    @Test
    public void testAddEdgesToUnWeightedUnDirectedGraphFromEmptySet() {
        verifyEdgesAddedFromEmptySet(m_unWeightedUnDirectedGraph);
    }
    
    @Test
    public void testAddEdgesToWeightedDirectedGraphFromEmptySet() {
        verifyEdgesAddedFromEmptySet(m_weightedDirectedGraph);
    }

    @Test
    public void testAddEdgesToWeightedUnDirectedGraphFromEmptySet() {
        verifyEdgesAddedFromEmptySet(m_weightedUnDirectedGraph);
    }

    @Test
    public void testAddEdgesToUnWeightedDirectedGraph() {
        verifyEdgesAddedFromSet(m_unWeightedDirectedGraph, m_inexistingEgdes);
    }

    @Test
    public void testAddEdgesToUnWeightedUnDirectedGraph() {
        verifyEdgesAddedFromSet(m_unWeightedUnDirectedGraph, m_inexistingEgdes);
    }
    
    @Test
    public void testAddEdgesToWeightedDirectedGraph() {
        verifyEdgesAddedFromSet(m_weightedDirectedGraph, m_inexistingEgdes);
    }
    
    @Test
    public void testAddEdgesToWeightedUnDirectedGraph() {
        verifyEdgesAddedFromSet(m_weightedUnDirectedGraph, m_inexistingEgdes);
    }

    //-------------------------------------------------------------------------
    //
    //  Tests for getVertex(Vertex vertex) 
    //
    //-------------------------------------------------------------------------
    @Test
    public void testGetVertexFromUnWeightedDirectedGraph() throws Exception {
        for (Vertex vertex : m_vertices) {
            assertEquals(vertex, m_unWeightedDirectedGraph.getVertex(vertex.label()));
        }
    }

    @Test
    public void testGetVertexFromUnWeightedUnDirectedGraph() throws Exception {
        for (Vertex vertex : m_vertices) {
            assertEquals(vertex, m_unWeightedUnDirectedGraph.getVertex(vertex.label()));
        }
    }
    
    @Test
    public void testGetVertexFromWeightedUnDirectedGraph() throws Exception {
        for (Vertex vertex : m_vertices) {
            assertEquals(vertex, m_weightedUnDirectedGraph.getVertex(vertex.label()));
        }
    }
    
    @Test
    public void testGetVertexFromWeightedDirectedGraph() throws Exception {
        for (Vertex vertex : m_vertices) {
            assertEquals(vertex, m_weightedDirectedGraph.getVertex(vertex.label()));
        }
    }


    @Test(expected = VertexNotFoundException.class)
    public void testGetVertexFromEmptyGraph() throws Exception {
        m_emptyGraph.getVertex(m_vertices.iterator().next().label());
    }

    @Test(expected = VertexNotFoundException.class)
    public void testGetInexistingVertexFromUnWeightedDirectedGraph() throws Exception {
        Vertex inexistingVertex = m_inexistingVertices.iterator().next();
        m_unWeightedDirectedGraph.getVertex(inexistingVertex.label());
    }

    @Test(expected = VertexNotFoundException.class)
    public void testGetInexistingVertexFromUnWeightedUnDirectedGraph() throws Exception {
        Vertex inexistingVertex = m_inexistingVertices.iterator().next();
        m_unWeightedUnDirectedGraph.getVertex(inexistingVertex.label());
    }
    
    @Test(expected = VertexNotFoundException.class)
    public void testGetInexistingVertexFromWeightedDirectedGraph() throws Exception {
        Vertex inexistingVertex = m_inexistingVertices.iterator().next();
        m_weightedDirectedGraph.getVertex(inexistingVertex.label());
    }

    @Test(expected = VertexNotFoundException.class)
    public void testGetInexistingVertexFromWeightedUnDirectedGraph() throws Exception {
        Vertex inexistingVertex = m_inexistingVertices.iterator().next();
        m_weightedUnDirectedGraph.getVertex(inexistingVertex.label());
    }

    
    //-------------------------------------------------------------------------
    //
    //  Tests for getVertices()
    //
    //-------------------------------------------------------------------------
    @Test
    public void testGetVerticesFromUnWeightedDirectedGraph() {
        verifyGetVerticesFromGraph(m_unWeightedDirectedGraph);
    }

    @Test
    public void testGetVerticesFromUnWeightedUnDirectedGraph() {
        verifyGetVerticesFromGraph(m_unWeightedUnDirectedGraph);
    }
    
    @Test
    public void testGetVerticesFromWeightedDirectedGraph() {
        verifyGetVerticesFromGraph(m_weightedDirectedGraph);
    }
    
    @Test
    public void testGetVerticesFromWeightedUnDirectedGraph() {
        verifyGetVerticesFromGraph(m_weightedUnDirectedGraph);
    }

    @Test
    public void testGetVerticesFromEmptyGraph() {
        assertTrue(m_emptyGraph.getVertices().isEmpty());
    }

    //-------------------------------------------------------------------------
    //
    //  Tests for addVertex(Vertex vertex)
    //
    //-------------------------------------------------------------------------
    @Test
    public void testAddDuplicateVerticesToUnWeightedDirectedGraph() {
        verifyDublicateVerticesAdded(m_unWeightedDirectedGraph, m_vertices);
    }
    
    @Test
    public void testAddDuplicateVerticesToUnWeightedUnDirectedGraph() {
        verifyDublicateVerticesAdded(m_unWeightedUnDirectedGraph, m_vertices);
    }

    @Test
    public void testAddDuplicateVerticesToWeightedUnDirectedGraph() {
        verifyDublicateVerticesAdded(m_unWeightedUnDirectedGraph, m_vertices);
    }

    @Test
    public void testAddDuplicateVerticesToWeightedDirectedGraph() {
        verifyDublicateVerticesAdded(m_weightedDirectedGraph, m_vertices);
    }

    @Test
    public void testAddVerticesToUnWeightedDirectedGraph() {
        verifyVerticesAddedFromSet(m_unWeightedDirectedGraph, m_inexistingVertices);
    }
    
    @Test
    public void testAddVerticesToUnWeightedUnDirectedGraph() {
        verifyVerticesAddedFromSet(m_unWeightedUnDirectedGraph, m_inexistingVertices);
    }
    @Test
    public void testAddVerticesToWeightedDirectedGraph() {
        verifyVerticesAddedFromSet(m_weightedDirectedGraph, m_inexistingVertices);
    }
    
    @Test
    public void testAddVerticesToWeightedUnDirectedGraph() {
        verifyVerticesAddedFromSet(m_weightedUnDirectedGraph, m_inexistingVertices);
    }

    @Test
    public void testAddVerticesFromEmptySetToUnWeightedDirectedGraph() {
        verifyEmptyVerticesSetAdded(m_unWeightedDirectedGraph, EMPTY_VERTICES_SET);
    }

    @Test
    public void testAddVerticesFromEmptySetToUnWeightedUnDirectedGraph() {
        verifyEmptyVerticesSetAdded(m_unWeightedUnDirectedGraph, EMPTY_VERTICES_SET);
    }

    @Test
    public void testAddVerticesFromEmptySetToWeightedDirectedGraph() {
        verifyEmptyVerticesSetAdded(m_weightedDirectedGraph, EMPTY_VERTICES_SET);
    }
    
    @Test
    public void testAddVerticesFromEmptySetToWeightedUnDirectedGraph() {
        verifyEmptyVerticesSetAdded(m_weightedUnDirectedGraph, EMPTY_VERTICES_SET);
    }
    
    
    //-------------------------------------------------------------------------
    //
    //  Tests for asAdjacencyMatrix()
    //
    //-------------------------------------------------------------------------
    @Test
    public void testAsAdjencencyMatrixFromEmptyGraph() {
        AdjacencyMatrix matrix = m_emptyGraph.asAdjacencyMatrix();
        assertNotNull(matrix);
        assertTrue(matrix.getVertices().isEmpty());
        for (Edge edge : m_unWeightedEgdes) {
            assertFalse(matrix.containsEdge(edge));
        }

        for (Edge edge : m_weightedEgdes) {
            assertFalse(matrix.containsEdge(edge));
        }
    }

    @Test
    public void testAsAdjacencyMatrixFromUnWeightedDirectedGraph() {
        verifyGraphAdjacencyMatrix(m_unWeightedDirectedGraph);
    }

    @Test
    public void testAsAdjacencyMatrixFromUnWeightedUnDirectedGraph() {
        verifyGraphAdjacencyMatrix(m_unWeightedUnDirectedGraph);
    }

    @Test
    public void testAsAdjacencyMatrixFromWeightedDirectedGraph() {
        verifyGraphAdjacencyMatrix(m_weightedDirectedGraph);
    }
    
    @Test
    public void testAsAdjacencyMatrixFromWeightedUnDirectedGraph() {
        verifyGraphAdjacencyMatrix(m_weightedUnDirectedGraph);
    }

    //-------------------------------------------------------------------------
    //
    //  Tests for asAdjacencyList()
    //
    //-------------------------------------------------------------------------
    @Test
    public void testAsAdjacencyListFromEmptyGraph() {
        AdjacencyList adjacencyList = m_emptyGraph.asAdjacencyList();
        assertNotNull(adjacencyList);
        assertTrue(adjacencyList.getVertices().isEmpty());
        for (Edge edge : m_weightedEgdes) {
            assertFalse(adjacencyList.containsEdge(edge));
        }
        for (Edge edge : m_unWeightedEgdes) {
            assertFalse(adjacencyList.containsEdge(edge));
        }
    }
    
    @Test
    public void testAsAdjacencyListFromUnWeightedDirectedGraph() {
        verifyGraphAdjacencyList(m_unWeightedDirectedGraph);
    }

    @Test
    public void testAsAdjacencyListFromUnWeightedUnDirectedGraph() {
        verifyGraphAdjacencyList(m_unWeightedUnDirectedGraph);
    }
    @Test
    public void testAsAdjacencyListFromWeightedUnDirectedGraph() {
        verifyGraphAdjacencyList(m_weightedUnDirectedGraph);
    }

    @Test
    public void testAsAdjacencyListFromWeightedDirectedGraph() {
        verifyGraphAdjacencyList(m_weightedDirectedGraph);
    }

    //-------------------------------------------------------------------------
    //
    //  Helper classes
    //
    //-------------------------------------------------------------------------
    
    /**
     * Verify if new edges from a set can be simultaneously added to a graph.
     * @param graph the graph to which the edges will be added.
     * @param edges a set of new edges to add to the graph.
     */
    private void verifyEdgesAddedFromSet(Graph graph, Set<Edge> edges) {
        int expectedEdgesCount = graph.edgesCount() + edges.size();
        if (!graph.isDirected()) {
            expectedEdgesCount += edges.size();
        }
        
        graph.addEdges(edges);
        for (Edge edge : edges) {
            assertTrue(graph.containsEdge(edge));
        }
        assertEquals(expectedEdgesCount, graph.edgesCount());
    }

    /**
     * Verify if new edges from a set can be added one by one to a graph.
     * @param graph the graph to which the edges will be added.
     * @param edges a set of new edges to add to the graph.
     */
    private void verifyNewEdgeAdded(Graph graph, Set<Edge> edges) {
        int expectedEdgesCount = graph.edgesCount();
        int expectedVerticesCount = graph.verticesCount();
        for (Edge newEdge : edges) {

            if (!graph.containsVertex(newEdge.getStartVertex())) {
                expectedVerticesCount++;
            }
            if (!graph.containsVertex(newEdge.getEndVertex())) {
                expectedVerticesCount++;
            }

            graph.addEdge(newEdge);

            assertTrue(graph.containsEdge(newEdge));
            if (graph.isDirected()) {
                expectedEdgesCount++;
            } else {
                expectedEdgesCount += 2;
                assertTrue(graph.containsEdge(newEdge.reversed()));
            }
            assertEquals(expectedEdgesCount, graph.edgesCount());
            assertTrue(graph.containsVertex(newEdge.getStartVertex()));
            assertTrue(graph.containsVertex(newEdge.getEndVertex()));
            assertEquals(expectedVerticesCount, graph.verticesCount());
        }
    }

    /**
     * Verify that the graph do no change when edges are added from an empty set.
     * @param graph the graph to be checked.
     */
    private void verifyEdgesAddedFromEmptySet(Graph graph) {
        Set<Vertex> vertices = new HashSet<Vertex>(graph.getVertices());
        Set<Edge> edges = new HashSet<Edge>(graph.getEdges());
        graph.addEdges(EMPTY_EDGES_SET);
        assertEquals(vertices.size(), graph.verticesCount());
        assertTrue(vertices.containsAll(graph.getVertices()));
        assertEquals(edges.size(), graph.edgesCount());
        assertTrue(edges.containsAll(graph.getEdges()));
    }
    
    /**
     * Verify that the edges returned by a graph contains all edges used to
     * create the graph.
     * 
     * @param graph the graph to check.
     * @param edges the edges used to create the graph.
     */
    private void verifyGraphEdgesRetrieval(final Graph graph, Set<Edge> edges) {
        Set<Edge> expectedEdges = new HashSet<Edge>(edges);
        if (!graph.isDirected()) {
            for (Edge edge : edges) {
                expectedEdges.add(edge.reversed());
            }
        }
        assertEquals(expectedEdges.size(), graph.getEdges().size());
        assertTrue(expectedEdges.containsAll(graph.getEdges()));
    }
    
    /**
     * Check if vertices returned by a graph contains all vertices used to create the graph.
     * @param graph the graph returning the vertices and to be checked.
     */
    private void verifyGetVerticesFromGraph(Graph graph) {
        assertEquals(m_vertices.size(), graph.getVertices().size());
        assertTrue(m_vertices .containsAll(graph.getVertices()));
    }
    
    /**
     * Verify if new vertices from a set can be simultaneously added to a graph.
     * @param graph the graph to which the vertices will be added.
     * @param vertices a set of new vertices to add to the graph.
     */
    private void verifyVerticesAddedFromSet(Graph graph, Set<Vertex> vertices) {
        int expectedVerticesCount = graph.verticesCount() + vertices.size();
        graph.addVertices(vertices);
        for (Vertex vertex : vertices) {
            assertTrue(graph.containsVertex(vertex));
        }
        assertEquals(expectedVerticesCount, graph.verticesCount());
    }

    /**
     * Check that the graph do not change when an empty set of vertices is added
     * to the graph.
     *
     * @param graph the graph to which the vertices are added and to be checked.
     * @param emptyVerticesSet an empty set of vertices.
     */
    private void verifyEmptyVerticesSetAdded(Graph graph, Set<Vertex> emptyVerticesSet) {
        Set<Vertex> initialVertices = new HashSet<Vertex>(graph.getVertices());
        graph.addVertices(emptyVerticesSet);
        assertEquals(initialVertices, graph.getVertices());
    }

    /**
     * Check that the graph do not change when duplicate vertices are added one by one.
     * @param graph the graph to which the vertices are added and to be checked.
     * @param edges an set of vertices already existing in the graph.
     */
    private void verifyDublicateVerticesAdded(Graph graph, Set<Vertex> vertices) {
        int initialSize = graph.verticesCount();
        for (Vertex vertex : m_vertices) {
            graph.addVertex(vertex);
            assertEquals(initialSize, graph.verticesCount());
        }
    }

    /**
     * Check that the graph do not change when duplicate edges are added one by one.
     * @param graph the graph to which the edges are added and to be checked.
     * @param edges an set of edges already existing in the graph.
     */
    private void verifyDuplicateEdgesAdded(Graph graph, Set<Edge> edges) {
        Set<Edge> currentEdges = new HashSet<Edge>(graph.getEdges());
        for (Edge edge : edges) {
            graph.addEdge(edge);
            assertEquals(currentEdges.size(), graph.getEdges().size());
            assertTrue(currentEdges.containsAll(graph.getEdges()));
        }
    }

    /**
     * Check that the adjacencyList returned by a graph contains all edges and
     * all vertices from the graph.
     * @param graph the graph returning the adjacencyList to check.
     */
    private void verifyGraphAdjacencyList(final Graph graph) {
        AdjacencyList adjacencyList = graph.asAdjacencyList();
        assertNotNull(adjacencyList);
        assertEquals(graph.getVertices().size(), adjacencyList.getVertices().size());
        assertTrue(adjacencyList.getVertices().containsAll(graph.getVertices()));
        for (Edge edge : graph.getEdges()) {
            assertTrue(adjacencyList.containsEdge(edge));
        }

    }

    /**
     * Check that the adjacencyMatrix returned by a graph contains all edges and
     * all vertices from the graph.
     * @param graph the graph returning the adjacencyMatrix to check.
     */
    private void verifyGraphAdjacencyMatrix(final Graph graph) {
        AdjacencyMatrix matrix = graph.asAdjacencyMatrix();
        assertNotNull(matrix);
        Set<Vertex> vertices = graph.getVertices();
        Set<Edge> edges = graph.getEdges();
        assertEquals(vertices.size(), matrix.size());
        for (Vertex vertex : vertices) {
            assertTrue(matrix.containsVertex(vertex));
        }
        for (Edge edge : edges) {
            assertTrue(matrix.containsEdge(edge));
        }
    }
    
    /**
     * Check if a graph contains edges
     * @param graph the graph to check.
     * @param edges the edges to check.
     */
    private void verifyGraphContainsEdges(Graph graph, Set<Edge> edges) {
        assertEquals(edges.size(), graph.edgesCount());
        Set<Vertex> vertices = new HashSet<Vertex>();
        for (Edge edge : edges) {
            assertTrue(graph.containsVertex(edge.getStartVertex()));
            assertTrue(graph.containsVertex(edge.getEndVertex()));
            vertices.add(edge.getStartVertex());
            vertices.add(edge.getEndVertex());
        }        
        
        assertEquals(vertices.size(), graph.verticesCount());
        assertTrue(graph.getVertices().containsAll(vertices));
    }
}