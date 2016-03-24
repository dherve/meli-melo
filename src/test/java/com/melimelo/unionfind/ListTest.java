package com.melimelo.unionfind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.melimelo.unionfind.listbased.List;
import com.melimelo.unionfind.listbased.Node;

public class ListTest {
    private final int LISTS_COUNT = 5;
    private final int LIST_ELEMENT_COUNT = 5;
    private Object[] m_lists = new Object[LISTS_COUNT];

    @Before
    public void setUp() {
        for (int i = 0; i < LISTS_COUNT; i++) {
            List<Integer> list = new List<Integer>(i);
            for (int value = 1; value < LIST_ELEMENT_COUNT; value++) {
                list.append(value);
            }
            m_lists[i] = list;
        }
    }

    @Test
    public void testList() {
        for (Object object : m_lists) {
            assertNotNull(asList(object));
        }
    }

    @Test
    public void testAppendValue() {
        for (Object object : m_lists) {
            List<Integer> list = asList(object);
            int size = list.size();
            Node<Integer> head = list.head();
            Node<Integer> tail = list.tail();
            for (int value = LIST_ELEMENT_COUNT; value < LIST_ELEMENT_COUNT
                    * 2; value++) {
                list.append(value);
                size++;
                assertEquals(size, list.size());
                assertTrue(list.contains(value));
                assertEquals(head, list.head());
                assertNotEquals(tail, list.tail());
            }
        }
    }

    @Test
    public void testAppendList() {
        // we select the first list as the one to which lists will be appended
        List<Integer> list = asList(m_lists[0]);

        for (int i = 1; i < m_lists.length; i++) {
            int listSize = list.size();
            List<Integer> listToAppend = asList(m_lists[i]);
            int listToAppendSize = listToAppend.size();
            assertNotNull(listToAppend);

            list.append(listToAppend);

            // the size of the list should be increased by the number of
            // elements added
            assertEquals(listSize + listToAppendSize, list.size());

            // the list should contains all element added
            for (Node<Integer> node = listToAppend
                    .head(); node != null; node = node.next()) {
                assertTrue(list.contains(node.value()));
            }

            // nodes in the new list should point to the same list
            for (Node<Integer> node = list.head(); node != null; node = node
                    .next()) {
                assertTrue(node.list() == list);
            }
        }
    }

    @Test
    public void testContains() {
        for (int i = 0; i < m_lists.length; i++) {
            assertTrue(asList(m_lists[i]).contains(i));
        }
    }

    @Test
    public void testHead() {
        for (int i = 0; i < m_lists.length; i++) {
            List<Integer> list = asList(m_lists[i]);
            assertNotNull(list.head());
            assertEquals(i, list.head().value().intValue());
        }
    }

    @Test
    public void testTail() {
        for (int i = 0; i < m_lists.length; i++) {
            List<Integer> list = asList(m_lists[i]);
            int tailValue = i + 1;
            list.append(tailValue);
            assertNotNull(list.tail());
            assertEquals(tailValue, list.tail().value().intValue());
        }
    }

    @Test
    public void testIsEmptyFromListsWithValues() {
        for (Object object : m_lists) {
            assertFalse(asList(object).isEmpty());
        }
    }

    @Test
    public void testSize() {
        for (Object object : m_lists) {
            List<Integer> list = asList(object);
            assertEquals(LIST_ELEMENT_COUNT, list.size());
        }
    }

    @Test
    public void testToString() {
        for (Object object : m_lists) {
            List<Integer> list = asList(object);
            String description = list.toString();

            String[] values = description.substring(1, description.length() - 1).split(",");
            assertEquals(list.size(), values.length);
            Node<Integer> node = list.head();
            int index = 0;
            while (node.next() != null) {
                assertEquals(node.value(), Integer.valueOf(values[index]));
                node = node.next();
                index++;
            }

        }
    }

    @SuppressWarnings("unchecked")
    private List<Integer> asList(Object object) {
        return (List<Integer>) object;
    }

}
