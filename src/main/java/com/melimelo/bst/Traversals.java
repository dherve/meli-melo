package com.melimelo.bst;

import java.util.Queue;
import java.util.LinkedList;

/**
 * Provide method for tree traversals.
 */
public final class Traversals {

    private Traversals() {
    }

    /**
     * Print all nodes values of a tree in an in-order traversal.
     * 
     * @param root the root node of the tree for which to print the values.
     */
    public static <T extends Comparable<T>> void printInOrder(final Node<T> root) {
        inOrderTraversal(root, new NodeValuePrinter<T>());
    }

    /**
     * Print all nodes values of a tree in an in-order traversal.
     * 
     * @param root the root node of the tree for which to print the values.
     */
    public static <T extends Comparable<T>> void printInPostOrder(final Node<T> node) {
        postOrderTraversal(node, new NodeValuePrinter<T>());
    }

    /**
     * Print all nodes values of a tree in an in-order traversal.
     * 
     * @param root the root node of the tree for which to print the values.
     */
    public static <T extends Comparable<T>> void printInPreOrder(final Node<T> node) {
        preOrderTraversal(node, new NodeValuePrinter<T>());
    }

    /**
     * Print all nodes values of a tree in an in-order traversal.
     * 
     * @param root the root node of the tree for which to print the values.
     */
    public static <T extends Comparable<T>> void printByLevel(final Node<T> node) {
        levelTraversal(node, new NodeValuePrinter<T>());
    }

    /**
     * Perform an in-order traversal.
     * 
     * @param node the node to from which to start the traversal.
     * @param action the action to apply on each node.
     */
    public static <T extends Comparable<T>> void inOrderTraversal(
            final Node<T> node, final ITraversalAction<T> action) {
        validateTraversalAction(action);
        if (node != null) {
            inOrderTraversal(node.left(), action);
            action.apply(node);
            inOrderTraversal(node.right(), action);
        }
    }

    /**
     * Perform a post-order traversal.
     * 
     * @param node the node to from which to start the traversal.
     * @param action the action to apply on each node.
     */
    public static <T extends Comparable<T>> void postOrderTraversal(
            final Node<T> node, final ITraversalAction<T> action) {
        validateTraversalAction(action);
        if (node != null) {
            postOrderTraversal(node.left(), action);
            postOrderTraversal(node.right(), action);
            action.apply(node);
        }
    }

    /**
     * Perform a pre-order traversal.
     * 
     * @param node the node to from which to start the traversal.
     * @param action the action to apply on each node.
     */
    public static <T extends Comparable<T>> void preOrderTraversal(
            final Node<T> node, final ITraversalAction<T> action) {
        validateTraversalAction(action);
        if (node != null) {
            action.apply(node);
            preOrderTraversal(node.left(), action);
            preOrderTraversal(node.right(), action);
        }
    }

    /**
     * Perform a level traversal.
     * 
     * @param node the node to from which to start the traversal.
     * @param action the action to apply on each node.
     */
    public static <T extends Comparable<T>> void levelTraversal(
            final Node<T> node, final ITraversalAction<T> action) {

        if(node != null) {

            validateTraversalAction(action);

            Queue<Node<T>> nodesQueue = new LinkedList<Node<T>>();
            nodesQueue.add(node);
            while (!nodesQueue.isEmpty()) {
                Node<T> nodeToVisit = nodesQueue.poll();
                action.apply(nodeToVisit);
                if (nodeToVisit.left() != null) {
                    nodesQueue.add(nodeToVisit.left());
                }
                
                if (nodeToVisit.right() != null) {
                    nodesQueue.add(nodeToVisit.right());
                }
            }
        }
    }

    /**
     * Validate that the traversal action is not null.
     * @param action the action to validate
     */
    private static <T extends Comparable<T>> void validateTraversalAction(
            ITraversalAction<T> action) {
        if(action == null) {
            throw new IllegalArgumentException("The traversal action can't be null!");
        }
    }

    /**
     * Helper class that print a node value.
     */
    public static final class NodeValuePrinter<T extends Comparable<T>>
            implements ITraversalAction<T> {

        /**
         * Print the node's value.
         * 
         * @param node the node with the value to print.
         */
        public void apply(Node<T> node) {
            if (node != null) {
                T value = node.value();
                System.out.print(value == null ? " null " :  " " + value + " ");
            }
        }
    }
}
