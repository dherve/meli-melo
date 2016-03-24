package com.melimelo.unionfind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.melimelo.unionfind.listbased.List;
import com.melimelo.unionfind.listbased.Node;

public class NodeTest {
    private final int NODE_VALUE = 5;
    private final int SUCCESSOR_NODE_VALUE = 10;
    private List<Integer> m_list;
    private Node<Integer> m_node;
    private Node<Integer> m_successor;

    @Before
    public void setUp() {
        m_list = new List<Integer>(NODE_VALUE);
        m_node = m_list.head();
        m_successor = new Node<Integer>(SUCCESSOR_NODE_VALUE, m_list);
        m_node.setNext(m_successor);
    }

    @Test
    public void testNode() {
        assertNotNull(m_node);
        assertNotNull(new Node<Integer>(NODE_VALUE, m_list));
    }

    @Test
    public void testValue() {
        assertEquals(NODE_VALUE, m_node.value().intValue());
    }

    @Test
    public void testNextWithoutSuccessor() {
        assertNull(m_successor.next());
    }

    @Test
    public void testNextWithSuccessor() {
        assertTrue(m_successor == m_node.next());
    }

    @Test
    public void testSetNext() {
        Node<Integer> node = new Node<Integer>(SUCCESSOR_NODE_VALUE, m_list);
        m_node.setNext(node);
        assertTrue(node == m_node.next());
        assertFalse(m_successor == node.next());
    }

    @Test
    public void testList() {
        assertTrue(m_list == m_node.list());
    }

    @Test
    public void testSetList() {
        List<Integer> list = new List<Integer>(NODE_VALUE);
        m_node.setList(list);
        assertTrue(list == m_node.list());
        assertFalse(m_list == m_node.list());
    }

    @Test
    public void testToString() {
        String description = m_node.toString();
        assertNotNull(description);
        assertTrue(description.contains(m_node.value().toString()));
        assertTrue(description.contains(m_node.next().value().toString()));
    }

}
