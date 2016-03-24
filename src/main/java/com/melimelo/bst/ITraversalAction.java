package com.melimelo.bst;

/**
 * Base interface for all classes used to define action applied to nodes
 * during a tree traversal.
 */
public interface ITraversalAction <T extends Comparable<T>> {
    /**
     * Apply an action to the node.
     * @param node the node on which to apply the action.
     */
    public void apply(Node<T> node);
}