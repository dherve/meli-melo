package com.melimelo.bst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.melimelo.bst.BinarySearchTree;
import com.melimelo.bst.ITraversalAction;
import com.melimelo.bst.Node;
import com.melimelo.bst.Traversals;

public final class TraversalsTest {

    private final int [] VALUES = new int [] {14, 11, 31, 9, 29, 45, 20, 43, 46, 41, 21};
    private final BinarySearchTree<Integer> EMPTY_TREE = new BinarySearchTree<Integer>();
    private ValuesAccumulator<Integer> mValuesAccumulator;
    private BinarySearchTree<Integer> mBinarySearchTree;


    @Before
    public void setUp() {
        mValuesAccumulator = new ValuesAccumulator<Integer>(); 
        mBinarySearchTree = new BinarySearchTree<Integer>();
        for(int value : VALUES) {
            mBinarySearchTree.insert(value);
        }
    }

    @Test
    public void testInOrderTraversal() {
        List<Integer> expectedValues = Arrays.asList(9, 11, 14, 20, 21, 29, 31, 41, 43, 45, 46);
        Traversals.inOrderTraversal(mBinarySearchTree.root(), mValuesAccumulator);
        assertEquals(expectedValues, mValuesAccumulator.values());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInOrderTraversalWithNullAction() {
        Traversals.inOrderTraversal(mBinarySearchTree.root(), null);
    }

    @Test
    public void testInOrderTraversalWithEmptyTree() {
        Traversals.inOrderTraversal(EMPTY_TREE.root(), mValuesAccumulator);
        assertTrue(mValuesAccumulator.values().isEmpty());
    }

    @Test
    public void testPostOrderTraversal() {
        List<Integer> expectedValues = Arrays.asList(9, 11, 21, 20, 29, 41, 43, 46, 45, 31, 14);
        Traversals.postOrderTraversal(mBinarySearchTree.root(), mValuesAccumulator);
        assertEquals(expectedValues, mValuesAccumulator.values());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostOrderTraversalWithNullAction() {
        Traversals.postOrderTraversal(mBinarySearchTree.root(), null);
    }

    @Test
    public void testPostOrderTraversalWithEmptyTree() {
        Traversals.postOrderTraversal(EMPTY_TREE.root(), mValuesAccumulator);
        assertTrue(mValuesAccumulator.values().isEmpty());
    }

    @Test
    public void testPreOrderTraversal() {
        List<Integer> expectedValues = Arrays.asList(14, 11, 9, 31, 29, 20, 21, 45, 43, 41, 46);
        Traversals.preOrderTraversal(mBinarySearchTree.root(), mValuesAccumulator);
        assertEquals(expectedValues, mValuesAccumulator.values());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPreOrderTraversalWithNullAction() {
        Traversals.preOrderTraversal(mBinarySearchTree.root(), null);
    }

    @Test
    public void testPreOrderTraversalWithEmptyTree() {
        Traversals.preOrderTraversal(EMPTY_TREE.root(), mValuesAccumulator);
        assertTrue(mValuesAccumulator.values().isEmpty());
    }

    @Test
    public void testLevelTraversal() {
        List<Integer> expectedValues = Arrays.asList(14, 11, 31, 9, 29, 45, 20, 43, 46, 21, 41);
        Traversals.levelTraversal(mBinarySearchTree.root(), mValuesAccumulator);
        assertEquals(expectedValues, mValuesAccumulator.values());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLevelTraversalWithNullAction() {
        Traversals.levelTraversal(mBinarySearchTree.root(), null);
    }

    @Test
    public void testLevelTraversalWithEmptyTree() {
        Traversals.levelTraversal(EMPTY_TREE.root(), mValuesAccumulator);
        assertTrue(mValuesAccumulator.values().isEmpty());
    }

    /**
     * Helper classes used to accumulate nodes values in order in which 
     * they are visited by a traversal.
     */
    public static final class ValuesAccumulator<T extends Comparable<T>>
            implements ITraversalAction<T> {

        private final List<T> m_values;

        public ValuesAccumulator() {
            m_values = new ArrayList<T>();
        }

        public List<T> values () {
            return m_values;
        }

        public void apply(Node<T> node) {
            if (node != null) {
                m_values.add(node.value());
            }
        }
    }

}