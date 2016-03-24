package com.melimelo.bst;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.melimelo.bst.BinarySearchTree;
import com.melimelo.bst.Node;

/**
 * Binary search tree tests.
 */
public class BinarySearchTreeTest{
    private final int MAX_TEST_VALUES_COUNT = 25;
    private final int UNSUPPORTED_STRATEGY = -1;


    @Test
    public void testCreateBinarySearchTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertNotNull(tree);
        assertNull(tree.root());
    }

    @Test
    public void testCreateBinarySearchTreeWithRootValue() {
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>(value);
            assertNotNull(tree);
            Node<Integer> root = tree.root();
            assertNotNull(root);
            assertEquals(value, root.value().intValue());
        }
    }

    @Test
    public void testRoot() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertNull(tree.root());

        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            tree = new BinarySearchTree<Integer>(value);
            Node<Integer> root = tree.root();
            assertNotNull(root);
            assertEquals(value, root.value().intValue());
        }
    }
    
    @Test
    public void testInsert() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            Node<Integer> node = tree.insert(value);
            assertNotNull(node);
            assertEquals(value, node.value().intValue());
            assertValueInserted(tree, value);
            assertEquals(value + 1, tree.size());
        }
    }

    @Test
    public void testInsertUsingRecursiveStrategy() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            tree.insert(value, BinarySearchTree.RECURSIVE_STRATEGY);
            assertValueInserted(tree, value);
            assertEquals(value + 1, tree.size());
        }
    }

    @Test
    public void testInsertUsingIterativeStrategy() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            tree.insert(value, BinarySearchTree.ITERATIVE_STRATEGY);
            assertValueInserted(tree, value);
            assertEquals(value + 1, tree.size());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertUsingUnsupportedStrategy() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        tree.insert(MAX_TEST_VALUES_COUNT, UNSUPPORTED_STRATEGY);
    }

    @Test
    public void insertDuplicateValue() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            tree.insert(value);
        }

        assertEquals(MAX_TEST_VALUES_COUNT, tree.size());

        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            Node<Integer> nodeValue = tree.search(value);
            assertNotNull(nodeValue);
            tree.insert(value);

            // we should get the same node as before since no new node should 
            // have been added.
            assertTrue(nodeValue == tree.search(value));

            // The size shouldn't change when duplicate values are added.
            assertEquals(MAX_TEST_VALUES_COUNT, tree.size());
        }
    }

    @Test
    public void testSearch() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            Node<Integer> node = tree.search(value);
            assertNotNull(node);
            assertEquals(value, node.value().intValue());
        }
    }

    @Test
    public void testSearchForInexistingValues() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        for(int value = MAX_TEST_VALUES_COUNT; value < MAX_TEST_VALUES_COUNT * 2; value++) {
            assertNull(tree.search(value));
        }
    }

    @Test
    public void testSearchWithEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            assertNull(tree.search(value));
        }
    }

    @Test
    public void testSearchUsingRecursiveStrategy() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            Node<Integer> node = tree.search(value, BinarySearchTree.RECURSIVE_STRATEGY);
            assertNotNull(node);
            assertEquals(value, node.value().intValue());
        }
    }

    @Test
    public void testSearchUsingIterativeStrategy() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            Node<Integer> node = tree.search(value, BinarySearchTree.ITERATIVE_STRATEGY);
            assertNotNull(node);
            assertEquals(value, node.value().intValue());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchUsingUnsupportedStrategy() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        tree.search(MAX_TEST_VALUES_COUNT, UNSUPPORTED_STRATEGY);
    }

    @Test
    public void testContains() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        assertFalse(tree.isEmpty());
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            assertTrue(tree.contains(value));
        }
    }

    @Test
    public void testContainsWithInexistingValues() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        assertFalse(tree.isEmpty());
        for(int value = MAX_TEST_VALUES_COUNT; value < MAX_TEST_VALUES_COUNT * 2; value++) {
            assertFalse(tree.contains(value));
        }
    }

    @Test
    public void testContainsOnEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            assertFalse(tree.contains(value));
        }
    }

    @Test
    public void testDelete() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        assertFalse(tree.isEmpty());
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            assertTrue(tree.contains(value));
            Node<Integer> node = tree.delete(value);
            assertFalse(tree.contains(value));
        }
    }

    @Test
    public void testRandomDelete() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        assertFalse(tree.isEmpty());
        List<Integer> randomValues = createListWithRandomizedValues();
        for(int value : randomValues) {
            assertTrue(tree.contains(value));
            Node<Integer> node = tree.delete(value);
            assertNotNull(node);
            assertEquals(value, node.value().intValue());
            assertFalse(tree.contains(value));
        }
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testRandomDelete2() {
        int [] values = new int [] {14, 11, 31, 9, 29, 45, 20, 43, 46, 41, 21};
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());

        for(int value : values){
            tree.insert(value);
            assertTrue(tree.contains(value));
            assertFalse(tree.isEmpty());
        }
        
        for(int value : values){
            Node<Integer> node = tree.delete(value);
            assertNotNull(node);
            assertEquals(value, node.value().intValue());
            assertFalse(tree.contains(value));
        }
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testDeleteInexistingValues() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        assertFalse(tree.isEmpty());
        for(int value = MAX_TEST_VALUES_COUNT; value < MAX_TEST_VALUES_COUNT * 2; value++) {
            assertNull(tree.delete(value));
        }
    }

    @Test
    public void testDeleteValuesFromEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            assertNull(tree.delete(value));
        }
    }

    @Test
    public void testMax() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        List<Integer> randomValues = createListWithRandomizedValues();
        List<Integer> valuesAdded = new ArrayList<Integer>();
        for(int value : randomValues) {
            tree.insert(value);
            assertTrue(tree.contains(value));
            valuesAdded.add(value);
            Node<Integer> max = tree.max();
            assertNotNull(max);
            assertEquals(Collections.max(valuesAdded), max.value());
        }
    }

    @Test
    public void testMaxFromEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertNull(tree.max());
    }

    @Test
    public void testMin() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        List<Integer> randomValues = createListWithRandomizedValues();
        List<Integer> valuesAdded = new ArrayList<Integer>();
        for(int value : randomValues) {
            tree.insert(value);
            assertTrue(tree.contains(value));
            valuesAdded.add(value);
            Node<Integer> min = tree.min();
            assertEquals(Collections.min(valuesAdded), min.value());
        }
    }

    @Test
    public void testMinFromEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertNull(tree.min());
    }

    @Test
    public void testSuccessor() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        List<Integer> randomValues = createListWithRandomizedValues();
        for(int value : randomValues) {
            tree.insert(value);
        }

        Collections.sort(randomValues);
        int lastElementIndex = randomValues.size() - 1;
        for(int index = 0; index < lastElementIndex; index++) {
            int value = randomValues.get(index);
            int successorValue = randomValues.get(index + 1);
            Node<Integer> successor = tree.successor(value);
            assertNotNull(successor);
            assertEquals(successorValue, successor.value().intValue());
        }

        // The biggest element should have no successor
        assertNull(tree.successor(randomValues.get(lastElementIndex)));
        assertNull(tree.successor(tree.max().value()));
    }

    @Test
    public void testPredecessor() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertTrue(tree.isEmpty());
        List<Integer> randomValues = createListWithRandomizedValues();
        for(int value : randomValues) {
            tree.insert(value);
        }

        Collections.sort(randomValues);
        int firstElementIndex = 0;
        for(int index = randomValues.size() - 1; index > firstElementIndex; index--) {
            int value = randomValues.get(index);
            int predecessorValue = randomValues.get(index - 1);
            Node<Integer> predecessor = tree.predecessor(value);
            assertNotNull(predecessor);
            assertEquals(predecessorValue, predecessor.value().intValue());
        }

        // the smallest element should have no predecessor
        assertNull(tree.predecessor(randomValues.get(firstElementIndex)));
        assertNull(tree.predecessor(tree.min().value()));
    }

    @Test
    public void testIsEmpty() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        assertFalse(tree.isEmpty());
        while(!tree.isEmpty()) {
            tree.delete(tree.min().value());
        }
        assertTrue(tree.isEmpty());
    }

    // fail because delete is not working
    @Test
    public void testSize() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        int size = MAX_TEST_VALUES_COUNT;
        assertFalse(tree.isEmpty());
        assertEquals(size, tree.size());
        while(!tree.isEmpty()) {
            tree.delete(tree.min().value());
            assertEquals(--size, tree.size());
        }
        assertTrue(tree.isEmpty());
        assertEquals(size, tree.size());
    }

    // Fail because delete does not work
    @Test
    public void testClear() {
        BinarySearchTree<Integer> tree = createTreeWithValues();
        assertFalse(tree.isEmpty());
        assertEquals(MAX_TEST_VALUES_COUNT, tree.size());
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            assertTrue(tree.contains(value));
        }

        tree.clear();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            assertFalse(tree.contains(value));
        }

    }


    private BinarySearchTree<Integer>  createTreeWithValues() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        for(int value = 0; value < MAX_TEST_VALUES_COUNT; value++) {
            tree.insert(value);
        }
        return tree;
    }


    private List<Integer> createListWithRandomizedValues() {
        List<Integer> values = new ArrayList<Integer>();
        for(int  value = 0 ; value < MAX_TEST_VALUES_COUNT; value++) {
            values.add(value);
        }
        Collections.shuffle(values);
        return values;
    }

     private void assertValueInserted(final BinarySearchTree<Integer> tree, final int value) {
        assertFalse(tree.isEmpty());

        // the three should contains the value added.
        assertTrue(tree.contains(value));

        // We should be able to get a node from the tree with the value added.
        Node<Integer> node = tree.search(value);
        assertNotNull(node);
        assertEquals(value, node.value().intValue());
    }

}