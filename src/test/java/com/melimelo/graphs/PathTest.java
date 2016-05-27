package com.melimelo.graphs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link Path}
 */
public class PathTest {

    private final Vertex START_VERTEX = new Vertex("A");
    private final Vertex END_VERTEX = new Vertex("Z");
    private final BigDecimal ZERO_DISTANCE = BigDecimal.ZERO;
    private final List<Vertex> NO_VERTICES = Collections.<Vertex> emptyList();
    private List<Vertex> m_pathVertices;
    private List<Vertex> m_verticesToAdd;
    private Path m_pathWithIntermediateVertices;
    private Path m_pathWithoutIntermediateVertices;
    private Path m_circularPath;

    @Before
    public void setUp() {
        m_pathVertices = new ArrayList<Vertex>();
        for (char c = 'B'; c < 'M'; c++) {
            m_pathVertices.add(new Vertex(String.valueOf(c)));
        }
        m_verticesToAdd = new ArrayList<Vertex>();
        for (char c = 'N'; c < 'Z'; c++) {
            m_verticesToAdd.add(new Vertex(String.valueOf(c)));
        }
        m_circularPath = createPath(START_VERTEX, START_VERTEX, m_pathVertices);
        m_pathWithIntermediateVertices = createPath(START_VERTEX, END_VERTEX,
                m_pathVertices);
        m_pathWithoutIntermediateVertices = createPath(START_VERTEX, END_VERTEX,
                NO_VERTICES);
    }

    @Test
    public void testCreatePath() {
        assertNotNull(m_circularPath);
        assertNotNull(m_pathWithIntermediateVertices);
        assertNotNull(m_pathWithoutIntermediateVertices);
    }

    @Test
    public void testGetStartFromCircularPath() {
        assertEquals(START_VERTEX, m_circularPath.getStart());
    }

    @Test
    public void testGetStartFromPathWithIntermediateVertices() {
        assertEquals(START_VERTEX, m_pathWithIntermediateVertices.getStart());
    }

    @Test
    public void testGetStartFromPathWithoutIntermediateVertices() {
        assertEquals(START_VERTEX,
                m_pathWithoutIntermediateVertices.getStart());
    }

    @Test
    public void testGetEndFromCircularPath() {
        assertEquals(START_VERTEX, m_circularPath.getEnd());
    }

    @Test
    public void testGetEndFromPathWithIntermediateVertices() {
        assertEquals(END_VERTEX, m_pathWithIntermediateVertices.getEnd());
    }

    @Test
    public void testGetEndFromPathWithoutIntermediateVertices() {
        assertEquals(END_VERTEX, m_pathWithoutIntermediateVertices.getEnd());
    }

    @Test
    public void testAddIntermediateVertexToCircularPath() {
        verifyVertexAddedToPath(m_verticesToAdd, m_circularPath);
    }

    @Test
    public void testAddIntermediateVertexToPathWithIntermediateVertices() {
        verifyVertexAddedToPath(m_verticesToAdd,
                m_pathWithIntermediateVertices);
    }

    @Test
    public void testAddIntermediateVertexToPathWithoutIntermediateVertices() {
        verifyVertexAddedToPath(m_verticesToAdd,
                m_pathWithoutIntermediateVertices);
    }

    @Test
    public void testAddDuplicateIntermediateVertexToCircularPath() {
        verifyDuplicateVertexNotAdded(m_pathVertices, m_circularPath);
    }

    @Test
    public void testAddDuplicateIntermediateVertexToPathWithIntermediateVertices() {
        verifyDuplicateVertexNotAdded(m_pathVertices,
                m_pathWithIntermediateVertices);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullIntermediateVertexToCircularPath() {
        m_circularPath.addIntermediateVertex(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullIntermediateVertexToPathWithIntermediateVertices() {
        m_pathWithIntermediateVertices.addIntermediateVertex(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullIntermediateVertexToPathWithoutIntermediateVertices() {
        m_pathWithoutIntermediateVertices.addIntermediateVertex(null);
    }

    @Test
    public void testAddIntermediateVerticesToCircularPath() {
        verifyVerticesAddToPath(m_circularPath, m_verticesToAdd);
    }

    @Test
    public void testAddIntermediateVerticesToPathWithIntermediateVertices() {
        verifyVerticesAddToPath(m_pathWithIntermediateVertices,
                m_verticesToAdd);
    }

    @Test
    public void testAddIntermediateVerticesToPathWithoutIntermediateVertices() {
        verifyVerticesAddToPath(m_pathWithoutIntermediateVertices,
                m_verticesToAdd);
    }

    @Test
    public void testGetIntermediatesVerticesFromCircularPath() {
        assertEquals(m_pathVertices, m_circularPath.intermediatesVertices());
    }

    @Test
    public void testGetIntermediatesVerticesFromPathWithIntermediateVertices() {
        assertEquals(m_pathVertices,
                m_pathWithIntermediateVertices.intermediatesVertices());
    }

    @Test
    public void testGetIntermediatesVerticesFromPathWithoutIntermediateVertices() {
        assertTrue(m_pathWithoutIntermediateVertices.intermediatesVertices()
                .isEmpty());
    }

    @Test
    public void testCircularPathAsList() {
        verifyPathList(START_VERTEX, START_VERTEX, m_pathVertices,
                m_circularPath);
    }

    @Test
    public void testPathWithIntermediateVerticesAsList() {
        verifyPathList(START_VERTEX, END_VERTEX, m_pathVertices,
                m_pathWithIntermediateVertices);
    }

    @Test
    public void testPathWithoutIntermediateVerticesAsList() {
        verifyPathList(START_VERTEX, END_VERTEX, NO_VERTICES,
                m_pathWithoutIntermediateVertices);
    }

    @Test
    public void testReverseCircularPath() {
        Path reversedPath = new Path(START_VERTEX, START_VERTEX, ZERO_DISTANCE);
        Collections.reverse(m_pathVertices);
        reversedPath.addIntermediateVertices(m_pathVertices);
        assertEquals(reversedPath, m_circularPath.reverse());
    }

    @Test
    public void testReversePathWithIntermediateVertices() {
        Path reversedPath = new Path(END_VERTEX, START_VERTEX, ZERO_DISTANCE);
        Collections.reverse(m_pathVertices);
        reversedPath.addIntermediateVertices(m_pathVertices);
        assertEquals(reversedPath, m_pathWithIntermediateVertices.reverse());
    }

    @Test
    public void testReversePathWithoutIntermediateVertices() {
        assertEquals(new Path(END_VERTEX, START_VERTEX, ZERO_DISTANCE),
                m_pathWithoutIntermediateVertices.reverse());
    }

    @Test
    public void testEquals() {

        // Paths should equals to themselves
        assertEquals(m_circularPath, m_circularPath);
        assertEquals(m_pathWithoutIntermediateVertices,
                m_pathWithoutIntermediateVertices);
        assertEquals(m_pathWithIntermediateVertices,
                m_pathWithIntermediateVertices);

        // different path should not equals to eahc other
        assertNotEquals(m_circularPath, m_pathWithIntermediateVertices);
        assertNotEquals(m_pathWithIntermediateVertices,
                m_pathWithoutIntermediateVertices);
        assertNotEquals(m_pathWithoutIntermediateVertices, m_circularPath);

        // A path created with the same vertices as an existing one should be
        // equals to it
        Path path = new Path(START_VERTEX, END_VERTEX, ZERO_DISTANCE);
        assertEquals(path, m_pathWithoutIntermediateVertices);

        path.addIntermediateVertices(m_pathVertices);
        assertEquals(path, m_pathWithIntermediateVertices);

        path = new Path(START_VERTEX, START_VERTEX, ZERO_DISTANCE);
        path.addIntermediateVertices(m_pathVertices);
        assertEquals(path, m_circularPath);
    }

    @Test
    public void testCircularPathToString() {
        verifyPathStringRepresentation(m_circularPath);
    }

    @Test
    public void testPathWithIntermediateVerticesToString() {
        verifyPathStringRepresentation(m_pathWithIntermediateVertices);
    }

    @Test
    public void testPathWithoutIntermediateVerticesToString() {
        verifyPathStringRepresentation(m_pathWithoutIntermediateVertices);
    }

    @Test
    public void testGetVerticesCounts() {
        assertEquals(2, m_pathWithoutIntermediateVertices.verticesCount());
        assertEquals(2 + m_pathVertices.size(),
                m_pathWithIntermediateVertices.verticesCount());
        assertEquals(1 + m_pathVertices.size(), m_circularPath.verticesCount());
    }

    @Test
    public void testLength() {
        // no length provide at initialization so the length should be zero
        assertEquals(BigDecimal.ZERO, m_circularPath.length());
        assertEquals(BigDecimal.ZERO, m_pathWithIntermediateVertices.length());
        assertEquals(BigDecimal.ZERO, m_pathWithoutIntermediateVertices.length());

        // distance provided at initialization so the length should be the same
        assertEquals(BigDecimal.TEN, new Path(START_VERTEX, END_VERTEX, BigDecimal.TEN).length());
    }

    /**
     * Verify that the string representation of a path contains labels of all
     * vertices part of the path and in their respective order.
     * 
     * @param path the path for which to check the string representation.
     */
    private void verifyPathStringRepresentation(Path path) {
        String stringPath = path.toString();
        assertNotNull(stringPath);
        // split the path into 2 parts, the part with the list of vertices and
        // the other with the length
        String[] parts = stringPath.split(";");
        String verticesPart = parts[0].split("=")[1];
        String[] vertices = verticesPart.substring(1, verticesPart.length() - 1)
                .split(",");
        assertEquals(path.getStart().label(), vertices[0].trim());
        assertEquals(path.getEnd().label(),
                vertices[vertices.length - 1].trim());
        int index = 1;
        for (Vertex vertex : path.intermediatesVertices()) {
            assertEquals(vertex.label(), vertices[index++].trim());
        }
    }

    /**
     * check that the list representation of a path contains all path vertices
     * and in their respective order.
     * 
     * @param start the vertex set as start when the path was created
     * @param end the vertex set as end when the path was created.
     * @param intermediateVertices vertices intermediate vertices set, when the
     *            path was created.
     * @param path the path to check.
     */
    private void verifyPathList(final Vertex start, final Vertex end,
            final List<Vertex> intermediateVertices, Path path) {
        List<Vertex> expectedList = new ArrayList<Vertex>();
        expectedList.add(start);
        expectedList.addAll(intermediateVertices);
        expectedList.add(end);
        assertEquals(expectedList, path.asList());
    }

    /**
     * check that duplicate vertices are not added to a path.
     * 
     * @param duplicateVertices a list with vertices that are already part of
     *            the path.
     * @param path the path to which duplicate vertices will be added.
     */
    private void verifyDuplicateVertexNotAdded(
            final List<Vertex> duplicateVertices, final Path path) {
        assertTrue(path.intermediatesVertices().containsAll(duplicateVertices));
        int intermediateVerticesCount = path.intermediatesVertices().size();
        for (Vertex vertex : duplicateVertices) {
            path.addIntermediateVertex(vertex);
            assertEquals(intermediateVerticesCount,
                    path.intermediatesVertices().size());
        }
        assertEquals(intermediateVerticesCount,
                path.intermediatesVertices().size());
    }

    /**
     * verify if multiple vertices can be added at the same time to a path.
     * 
     * @param path the path to which the vertices are to be added.
     * @param vertices the vertices to add.
     */
    private void verifyVerticesAddToPath(final Path path,
            final List<Vertex> vertices) {
        int intermediateVerticesInitialCount = path.intermediatesVertices()
                .size();
        path.addIntermediateVertices(vertices);
        assertTrue(path.intermediatesVertices().containsAll(vertices));
        assertEquals(intermediateVerticesInitialCount + vertices.size(),
                path.intermediatesVertices().size());
    }

    /**
     * check if an individual vertex can be added to a path.
     * 
     * @param vertices a list of vertices to add one by one.
     * @param path the path to which the vertices will be added
     */
    private void verifyVertexAddedToPath(final List<Vertex> vertices,
            final Path path) {
        assertFalse(path.intermediatesVertices().containsAll(vertices));
        for (Vertex vertex : vertices) {
            path.addIntermediateVertex(vertex);
            assertTrue(path.intermediatesVertices().contains(vertex));
        }
        assertTrue(path.intermediatesVertices().containsAll(vertices));
    }

    /**
     * Create a new path.
     * 
     * @param start the first vertex of the path.
     * @param end the last vertex of the path.
     * @param vertices intermediate vertices between the first and the last
     *            vertex of the baths.
     * @return a new path with the vertices provides.
     */
    private Path createPath(final Vertex start, final Vertex end,
            final List<Vertex> vertices) {
        Path path = new Path(start, end, ZERO_DISTANCE);
        path.addIntermediateVertices(vertices);
        return path;
    }
}