package com.melimelo.lists;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.melimelo.lists.LinkedList;
import com.melimelo.lists.ListOperationException;
import com.melimelo.lists.Node;

public class LinkedListTest{

    private final int TEST_DATA_SIZE = 10;

    @Test
    public void testPrepend() {
        LinkedList<Integer> list = new LinkedList<Integer> ();
        for(int value = 0; value < TEST_DATA_SIZE; value++){
            list.prepend(value);
            assertNotNull(list.head());
            assertEquals(value, list.head().value().intValue());
            assertTrue(list.contains(value));
            assertEquals(value + 1, list.size());
            assertFalse(list.isEmpty());
        }
    }

    @Test
    public void testAppend() {
        LinkedList<Integer> list = new LinkedList<Integer> ();
        for(int value = 0; value < TEST_DATA_SIZE; value++){
            list.append(value);
            assertTrue(list.contains(value));
            assertEquals(value + 1, list.size());
            assertFalse(list.isEmpty());
        }
    }

    @Test
    public void testContainsPresentElements() {
        LinkedList<Integer> list = createFilledList(0, TEST_DATA_SIZE);
        for(int value = 0; value < TEST_DATA_SIZE; value++){
            assertTrue(list.contains(value));
        }
    }

    @Test
    public void testContainsAbsentElements() {
        LinkedList<Integer> list = createFilledList(0, TEST_DATA_SIZE);
        for(int value = TEST_DATA_SIZE * 2, end = TEST_DATA_SIZE * 3; value < end; value++) {
            assertFalse(list.contains(value));
        }
    }

    @Test
    public void testEmptyListContainsElements() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        assertTrue(list.isEmpty());
        for(int value = 0 ; value < TEST_DATA_SIZE; value++) {
            assertFalse(list.contains(value));
        }
    }

    @Test
    public void testRemovePresentElements() throws Exception {
        LinkedList<Integer> list = createFilledList(0, TEST_DATA_SIZE);
        int size = list.size();
        int expectedSize = TEST_DATA_SIZE;
        assertEquals(expectedSize, size);
        int elementRemovedCount = 0;
        for(int value = 0; value < TEST_DATA_SIZE; value++) {
            Node<Integer> node = list.remove(value);
            assertNotNull(node);
            assertEquals(value, node.value().intValue());
            elementRemovedCount ++;
            size = list.size();
            expectedSize = TEST_DATA_SIZE - elementRemovedCount;
            assertEquals(expectedSize, size);
        }
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveInexistingValue() throws Exception {
        LinkedList<Integer> list = createFilledList(TEST_DATA_SIZE);
        int size = list.size();
        assertEquals(TEST_DATA_SIZE, size);

        for(int value = TEST_DATA_SIZE * 2, end = TEST_DATA_SIZE *3; value < end; value++) {
            Node<Integer> node = list.remove(value);
            assertNull(node);
            size = list.size();
            assertEquals(TEST_DATA_SIZE, size);
        }
        assertFalse(list.isEmpty());
    }

    @Test(expected = ListOperationException.class)
    public  void testRemoveElementFromEmptyList() throws Exception{
        LinkedList<Integer> list = new LinkedList<Integer> ();
        list.remove(1);
    }

    @Test
    public void testGetElementWithInRangeIndex() {
        int from = TEST_DATA_SIZE;
        int to = TEST_DATA_SIZE * 2;
        LinkedList<Integer> list = createFilledList(from, to);
        for(int index = 0; index <TEST_DATA_SIZE; index++){
            Node<Integer> node = list.get(index);
            assertNotNull(node);
            int expected = index + TEST_DATA_SIZE;
            assertNotNull(node);
            assertEquals(expected, node.value().intValue());
        }

    }

    @Test
    public void testGetElementWithOutOfRangeIndex() {
        LinkedList<Integer> list = createFilledList(TEST_DATA_SIZE);
        for(int index = TEST_DATA_SIZE * 2, end = TEST_DATA_SIZE * 3; index < end; index++ ){
            Node<Integer> node = list.get(index);
            assertNull(node);
        }
    }

    @Test
    public void testSize() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        assertEquals(0, list.size());
        list = createFilledList(TEST_DATA_SIZE);
        assertEquals(TEST_DATA_SIZE, list.size());
    }

    @Test
    public void testHead() throws ListOperationException{
        LinkedList<Integer> list = new LinkedList<Integer>();
        assertNull(list.head());

        int elementsToAddCount = 5;
        Node<Integer> previousHead = list.head();
        for(int value  = 0; value < elementsToAddCount; value++) {
            list.prepend(value);
            Node<Integer> newHead = list.head();
            assertNotNull(newHead);
            assertEquals(value, newHead.value().intValue());
            assertFalse(newHead == previousHead);
            previousHead = newHead;
        }

        int lastElementValue = elementsToAddCount - 1;
        for(int value  = 0; value < lastElementValue; value++) {
            list.remove(value);
            Node<Integer> head = list.head();
            assertNotNull(head);
            assertNotEquals(value, head.value().intValue());
        }

        list.remove(lastElementValue);
        assertNull(list.head());
    }

    @Test
    public void testTail() throws Exception {
        LinkedList<Integer> list = new LinkedList<Integer>();
        assertNull(list.tail());

        int elementsToAddCount = 5;
        Node<Integer> previousTail = list.tail();
        for(int value = 0; value < elementsToAddCount; value++) {
            list.append(value);
            Node<Integer> newTail = list.tail();
            assertNotNull(newTail);
            assertEquals(value, newTail.value().intValue());
            assertFalse(newTail == previousTail);
            previousTail = newTail;
        }

        int lastElementValue = elementsToAddCount - 1;
        for(int value  = lastElementValue; value > 0; value--) {
            list.remove(value);
            Node<Integer> tail = list.tail();
            assertNotNull(tail);
            assertNotEquals(value, tail.value().intValue());
            lastElementValue--;
        }

        list.remove(lastElementValue);
        assertNull(list.tail());
    }

    @Test(expected = ListOperationException.class)
    public  void testClearEmptyList() throws Exception{
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.clear();
    }

    @Test
    public void testClearListWithValues() throws Exception {
        LinkedList<Integer> list = createFilledList(TEST_DATA_SIZE);
        assertEquals(TEST_DATA_SIZE, list.size());
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        for(int deletedValue = 0; deletedValue < TEST_DATA_SIZE; deletedValue ++) {
            assertFalse(list.contains(deletedValue));
        }
    }

    private LinkedList<Integer> createFilledList( int upTo) {
        return createFilledList(0,upTo);
    }

    private LinkedList<Integer> createFilledList( int from,  int to) {
        LinkedList<Integer> list = new LinkedList<Integer> ();
        for(int i = from; i < to; i ++) {
            list.append(i);
        }
        return list;
    }
}