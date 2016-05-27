package com.melimelo.graphs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

import com.melimelo.graphs.Edge;
import com.melimelo.graphs.Vertex;
public class EdgeTest {

    private final String START_VERTEX_LABEL = "A";
    private final String END_VERTEX_LABEL = "B";
    private final Vertex START_VERTEX = new Vertex(START_VERTEX_LABEL);
    private final Vertex END_VERTEX = new Vertex(END_VERTEX_LABEL);
    private final BigDecimal EDGE_WEIGHT = new BigDecimal(25);
    private final Vertex NULL_VERTEX = null;
    private final String NULL_LABEL = null;
    private Edge m_unWeightedEdge;
    private Edge m_weightedEdge;


    @Before
    public void setUp() {
        m_unWeightedEdge = new Edge(START_VERTEX, END_VERTEX);
        m_weightedEdge = new Edge(START_VERTEX, END_VERTEX, EDGE_WEIGHT); 
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCreateEdgeWithNullVertices() {
        new Edge(NULL_VERTEX,NULL_VERTEX);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCreateEdgeWithNullLabels() {
        new Edge(NULL_LABEL,NULL_LABEL);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCreateWeightedEdgeWithNullWeight() {
        new Edge(START_VERTEX, END_VERTEX, null); 
    }

    @Test
    public void testCreateUnWeightedEdgeFromVertices() {
        assertNotNull(m_unWeightedEdge);
        assertEquals(Edge.ZERO_WEIGHT, m_unWeightedEdge.getWeight());
    }

    @Test
    public void testCreateUnWeightedEdgeFromVerticesLabels() {
        m_unWeightedEdge = new Edge(START_VERTEX_LABEL, END_VERTEX_LABEL);
        assertNotNull(m_unWeightedEdge);
        assertEquals(START_VERTEX_LABEL, m_unWeightedEdge.getStartVertex().label());
        assertEquals(END_VERTEX_LABEL, m_unWeightedEdge.getEndVertex().label());
    }

    @Test
    public void testCreateWeightedEdge() {
        assertNotNull(m_weightedEdge);
        assertEquals(EDGE_WEIGHT, m_weightedEdge.getWeight());
    }

    @Test
    public void testCreateWeightedEdgeFromVerticesLabels() {
        assertNotNull(m_weightedEdge);
        assertEquals(START_VERTEX_LABEL, m_weightedEdge.getStartVertex().label());
        assertEquals(END_VERTEX_LABEL, m_weightedEdge.getEndVertex().label());
    }

    @Test
    public void testGetWeightFromWeightedEdge() {
        assertEquals(EDGE_WEIGHT, m_weightedEdge.getWeight());
    }

    @Test
    public void testGetWeightFromUnWeightedEdge() {
        assertEquals(Edge.ZERO_WEIGHT, m_unWeightedEdge.getWeight());
    }

    @Test
    public void testUnWeightedEdgeHasWeight() {
        assertFalse(m_unWeightedEdge.hasWeight());
    }

    @Test
    public void testWeightedEdgeHasWeight() {
        assertTrue(m_weightedEdge.hasWeight());
    }

    @Test
    public void testGetStartVertex() {
        assertEquals(START_VERTEX, m_weightedEdge.getStartVertex());
        assertEquals(START_VERTEX, m_unWeightedEdge.getStartVertex());
    }

    @Test
    public void testGetEndVertex() {
        assertEquals(END_VERTEX, m_weightedEdge.getEndVertex());
        assertEquals(END_VERTEX, m_unWeightedEdge.getEndVertex());
    }

    @Test
    public void testUnweightedEdgeReversed() {
        Edge reversed = m_unWeightedEdge.reversed();
        assertNotNull(reversed);
        assertEquals(m_unWeightedEdge.getEndVertex(), reversed.getStartVertex());
        assertEquals(m_unWeightedEdge.getStartVertex(), reversed.getEndVertex());
        assertEquals(m_unWeightedEdge.getWeight(), reversed.getWeight());
        assertNotEquals(m_unWeightedEdge, reversed);
    }

    @Test
    public void testWeightedEdgeReversed() {
        Edge reversed = m_weightedEdge.reversed();
        assertNotNull(reversed);
        assertEquals(m_weightedEdge.getEndVertex(), reversed.getStartVertex());
        assertEquals(m_weightedEdge.getStartVertex(), reversed.getEndVertex());
        assertEquals(m_weightedEdge.getWeight(), reversed.getWeight());
        assertNotEquals(m_weightedEdge, reversed);
    }

    @Test
    public void testWeightedEdgesEquality() {
        assertNotEquals(m_weightedEdge, null);
        assertEquals(m_weightedEdge, m_weightedEdge);
        assertEquals(m_weightedEdge, new Edge(START_VERTEX, END_VERTEX, EDGE_WEIGHT));
        assertNotEquals(m_weightedEdge, new Edge(END_VERTEX, START_VERTEX, EDGE_WEIGHT));
        assertNotEquals(m_weightedEdge, new Edge(START_VERTEX, END_VERTEX, new BigDecimal(Integer.MAX_VALUE)));
    }

    @Test
    public void testUnWeightedEdgesEquality() {
        assertNotEquals(m_unWeightedEdge, null);
        assertEquals(m_unWeightedEdge, m_unWeightedEdge);
        assertEquals(m_unWeightedEdge, new Edge(START_VERTEX, END_VERTEX));
        assertNotEquals(m_unWeightedEdge, new Edge(END_VERTEX, START_VERTEX));
    }

    @Test
    public void testWeightedAndUnWeightedEdgeEquality() {
        assertFalse(m_unWeightedEdge.equals(m_weightedEdge));
        assertFalse(m_weightedEdge.equals(m_unWeightedEdge));
    }

    @Test
    public void testWeightedEdgeHashCode() {
        assertTrue(m_weightedEdge.hashCode() > 0);
        assertNotEquals(m_weightedEdge.hashCode(), (new Object()).hashCode());
        assertNotEquals(m_weightedEdge.hashCode(), m_weightedEdge.reversed().hashCode());
    }

    @Test
    public void testUnWeightedEdgeHashCode() {
        assertTrue(m_unWeightedEdge.hashCode() > 0);
        assertNotEquals(m_unWeightedEdge.hashCode(), (new Object()).hashCode());
        assertNotEquals(m_unWeightedEdge.hashCode(), m_weightedEdge.hashCode());
        assertNotEquals(m_unWeightedEdge.hashCode(), m_unWeightedEdge.reversed().hashCode());
    }

    @Test
    public void testWeightedEdgeToString() {
        String description = m_weightedEdge.toString();
        assertNotNull(description);
        assertFalse(description.isEmpty());
        assertTrue(description.contains(m_weightedEdge.getStartVertex().label()));
        assertTrue(description.contains(m_weightedEdge.getEndVertex().label()));
        assertTrue(description.contains(m_weightedEdge.getWeight().toString()));
    }

    @Test
    public void testUnWeightedEdgeToString() {
        String description = m_unWeightedEdge.toString();
        assertNotNull(description);
        assertFalse(description.isEmpty());
        assertTrue(description.contains(m_unWeightedEdge.getStartVertex().label()));
        assertTrue(description.contains(m_unWeightedEdge.getEndVertex().label()));
        assertFalse(description.contains(m_unWeightedEdge.getWeight().toString()));
    }
}