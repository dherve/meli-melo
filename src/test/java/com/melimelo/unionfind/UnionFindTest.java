package com.melimelo.unionfind;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.melimelo.unionfind.listbased.List;
import com.melimelo.unionfind.listbased.UnionFind;

public class UnionFindTest {
    private final int COMPONENT_COUNT = 10;
    private final int INEXISTING_VALUE = 25;
    private final int FIRST_NODE_VALUE = 0;
    private UnionFind<Integer> m_unionFind;

    @Before
    public void setUp() {
        m_unionFind = new UnionFind<Integer>();
    }

    @Test
    public void testCreate() {
        for (int value = 0; value < COMPONENT_COUNT; value++) {
            List<Integer> list = m_unionFind.create(value);
            assertNotNull(list);
            assertEquals(1, list.size());
            assertEquals(value, list.head().value().intValue());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFromNullValue() {
        m_unionFind.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFromDuplicateValue() {
        for (int value = 0; value < COMPONENT_COUNT; value++) {
            m_unionFind.create(value);
        }
        m_unionFind.create(0);
    }

    @Test
    public void testFind() {
        for (int value = 0; value < COMPONENT_COUNT; value++) {
            m_unionFind.create(value);
            assertEquals(value, m_unionFind.find(value).intValue());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindForNullValue() {
        m_unionFind.find(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindForInexistingValue() {
        m_unionFind.find(INEXISTING_VALUE);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUnion() {

        Object[] lists = new Object[COMPONENT_COUNT];
        for (int value = FIRST_NODE_VALUE; value < COMPONENT_COUNT; value++) {
            lists[value] = m_unionFind.create(value);
        }

        List<Integer> listWithFirstValue = (List<Integer>) lists[FIRST_NODE_VALUE];
        int listWithFirstValueSize = listWithFirstValue.size();
        for (int value = 1; value < COMPONENT_COUNT; value++) {
            List<Integer> listWithValue = (List<Integer>) lists[value];
            assertFalse(listWithValue.contains(FIRST_NODE_VALUE));
            assertFalse(listWithFirstValue.contains(value));
            assertNotEquals(m_unionFind.find(FIRST_NODE_VALUE),
                    m_unionFind.find(value));
            assertEquals(value, m_unionFind.find(value).intValue());

            m_unionFind.union(value, FIRST_NODE_VALUE);
            assertEquals(m_unionFind.find(FIRST_NODE_VALUE), m_unionFind.find(value));
            assertNotEquals(value, m_unionFind.find(value).intValue());
            assertEquals(FIRST_NODE_VALUE, m_unionFind.find(FIRST_NODE_VALUE).intValue());

            assertTrue(listWithFirstValue.contains(value));
            assertFalse(listWithValue.contains(FIRST_NODE_VALUE));
            assertEquals(++listWithFirstValueSize, listWithFirstValue.size());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUnionValuesFromSameList() {
        Object[] lists = new Object[COMPONENT_COUNT];
        for (int value = FIRST_NODE_VALUE; value < COMPONENT_COUNT; value++) {
            lists[value] = m_unionFind.create(value);
        }

        for (int i = 0; i < lists.length; i += 2) {
            List<Integer> list = (List<Integer>) lists[i];
            m_unionFind.union(i, i);

            assertEquals(m_unionFind.find(i), m_unionFind.find(i));
            assertEquals(1, list.size());
        }

    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testUnionWithInexistingValue() {
        m_unionFind.union(FIRST_NODE_VALUE, INEXISTING_VALUE);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testUnionWithInexistingValues() {
        m_unionFind.union(INEXISTING_VALUE, INEXISTING_VALUE);
    }
}