package com.melimelo.graphs.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.melimelo.graphs.AdjacencyList;
import com.melimelo.graphs.Edge;
import com.melimelo.graphs.Graph;
import com.melimelo.graphs.IAction;
import com.melimelo.graphs.MinimumSpaningTree;
import com.melimelo.graphs.NoOpAction;
import com.melimelo.graphs.Vertex;

/**
 * Base class for all classes implementing graph algorithms tests.
 */
public abstract class AlgorithmsTestBase {

    @Rule
    public ExpectedException m_expectedException= ExpectedException.none();
    
    protected final Vertex SOURCE_VERTEX = new Vertex("A");
    protected TraversalAction m_action;
    protected IAction<Vertex> m_noOpAction = new NoOpAction<Vertex>();
    
    @Before
    public void setUp() throws Exception {
        m_action = new TraversalAction(SOURCE_VERTEX);
    }
    

    /**
     * Setup an expected {@link IllegalArgumentException} with a specific message.
     * @param message the message of the expected illegalArgumentException.
     */
    protected void setUpExpectedIllegalArgumentException(String message) {
        setupExpectedException(IllegalArgumentException.class, message);
    }
    
    /**
     *  Setup an expected exception with a specific message.
     * @param clazz the class of the expected exception
     * @param message the message of the expected exception.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void setupExpectedException(Class clazz, String message) {
        m_expectedException.expect(clazz);
        m_expectedException.expectMessage(message);
    }
    /**
     * Check if a graph contains a set of specific edges.
     * @param graph the graph from which to verify if all the edge are present.
     * @param edges a set of edges to be checked.
     */
    protected void verifyGraphContainsEdges(final Graph graph, final Set<Edge> edges) {
        for (Edge edge : edges) {
            verifyGraphContainsEdge(graph, edge);
        }
    }
    /**
     * Check if a graph contains a specific edge.
     * @param graph the graph from which to verify if the edge is present.
     * @param edge the edge to check.
     */
    protected void verifyGraphContainsEdge(final Graph graph, final Edge edge) {
        verifyGraphContainsEdge(graph, edge.getStartVertex(), edge.getEndVertex());
    }

    /**
     * Check if a graph contains a specific edge.
     * @param graph the graph from which to verify if the edge is present.
     * @param start the first vertex of the edge to check.
     * @param end the second vertex of the edge to check.
     */
    protected void verifyGraphContainsEdge(final Graph graph, final Vertex start, 
            final Vertex end) {
        boolean edgeExist = false;
        for (Edge edge : graph.getEdges()) {
            if (edge.getStartVertex().equals(start) && 
                edge.getEndVertex().equals(end)) {
                edgeExist = true;
                break;
            }
        }
        assertTrue(edgeExist);
    }
    
    /**
     * Check if all vertices are covered by executing a graph traversal
     * algorithm.
     * 
     * @param graph the graph on which the algorithm was performed.
     * @param action the action executed on each edge covered and each vertex
     *            visited.
     */
    protected void verifyGraphTraversal(Graph graph, TraversalAction action) {

        // we should have the same number of vertices and the same vertices =>
        // all vertices have been visited
        assertEquals(action.getVertices().size(), graph.verticesCount());
        assertTrue(action.getVertices().containsAll(graph.getVertices()));

        // we can't have more edges than what is initially in the graph
        assertTrue(action.getEdges().size() <= graph.edgesCount());

        AdjacencyList adjacencyList = graph.asAdjacencyList();

        for (Edge edge : action.getEdges()) {
            assertTrue(adjacencyList.containsEdge(edge));
        }
    }
    
    /**
     * Check if a minimum spanning tree contains all vertices from a graph, and
     * all edges in the minimum spanning tree are part of the graph.
     * 
     * @param graph the original graph from which the minimum spanning tree was
     *            built.
     * @param mst the minimum spanning tree to check.
     */
    protected void verfitGraphMST(final Graph graph, final MinimumSpaningTree mst) {
        // All vertices should be part of the mst
        assertEquals(graph.verticesCount(), mst.verticesCount());
        assertTrue(graph.getVertices().containsAll(mst.getVertices()));

        // the graph used for testing is not a tree hence we should have less
        // edges
        assertTrue(graph.edgesCount() >= mst.edgesCount());

        // all mst edges should be part of the original graph
        verifyGraphContainsEdges(graph, mst.getEdges());
    }

    /**
     * Check if a minimum spanning tree contains all vertices from a graph, and
     * all edges in the minimum spanning tree are part of the graph. For
     * unweighted graph, the total cost is 0.
     * 
     * @param graph the original graph from which the minimum spanning tree was
     *            built.
     * @param mst the minimum spanning tree to check.
     */
    protected void verifyUnWeightedGraphMST(final Graph graph, final MinimumSpaningTree mst) {
        verfitGraphMST(graph, mst);
        assertEquals(BigDecimal.ZERO, mst.getCost());
    }

    /**
     * Check if a minimum spanning tree contains all vertices from a graph, and
     * all edges in the minimum spanning tree are part of the graph. Check if
     * the total cost of the tree corresponds to the expected cost.
     * 
     * @param graph the original graph from which the minimum spanning tree was
     *            built.
     * @param mst the minimum spanning tree to check.
     * @param expectedCost the expected cost for the minimum spanning tree
     */
    protected void verifyWeightedGraphMST(final Graph graph,
            final MinimumSpaningTree mst, final BigDecimal expectedCost) {
        verfitGraphMST(graph, mst);
        assertEquals(expectedCost, mst.getCost());
    }
}
