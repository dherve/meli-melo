package com.melimelo.graphs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.melimelo.graphs.Vertex;
import com.melimelo.graphs.VertexNotFoundException;
import com.melimelo.graphs.VerticesIndexes;

public class VerticesIndexesTest {
    
    private final int INEXISTING_INDEX = 25;
    private final Vertex INEXSTING_VERTEX = new Vertex(String.valueOf(INEXISTING_INDEX));
    private final int VERTICES_COUNT = 10;
    private Vertex[] m_vertices;
    private VerticesIndexes m_verticesIndexes;

    @Before
    public void setUp() throws Exception {
        m_vertices = new Vertex[VERTICES_COUNT];
        for (int i = 0; i < VERTICES_COUNT; i++) {
            m_vertices[i] = new Vertex(String.valueOf(i));
        }
        m_verticesIndexes = new VerticesIndexes(Arrays.asList(m_vertices));
    }

    @Test
    public void testCreateVerticesIndexes() {
        assertNotNull(m_verticesIndexes);
    }
    
    @Test
    public void testCreateVerticesIndexesFromEmptyVerticesCollection() {
        m_verticesIndexes = new VerticesIndexes(new ArrayList<Vertex>());
        assertNotNull(m_verticesIndexes);
    }

    @Test
    public void testGetIndex() throws Exception {
        for (int expectedIndex = 0; expectedIndex < VERTICES_COUNT; expectedIndex++) {
            Vertex vertex = m_vertices[expectedIndex];
            assertEquals(expectedIndex, m_verticesIndexes.getIndex(vertex));
        }
    }

    @Test
    public void testGetIndexForInexistingVertex() {
        assertEquals(VerticesIndexes.UNDEFINED_INDEX,
                     m_verticesIndexes.getIndex(INEXSTING_VERTEX));
    }

    @Test
    public void testGetVertex() throws Exception {
        for (int index = 0; index < VERTICES_COUNT; index++) {
            Vertex expectedVertex = m_vertices[index];
            assertEquals(expectedVertex, m_verticesIndexes.getVertex(index));
        }
    }

    @Test(expected = VertexNotFoundException.class)
    public void testGetVertexForInexistingIndex() throws Exception {
        assertEquals(VerticesIndexes.UNDEFINED_INDEX,
                     m_verticesIndexes.getVertex(INEXISTING_INDEX));
    }
}
