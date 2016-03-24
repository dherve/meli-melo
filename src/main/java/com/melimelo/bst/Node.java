package com.melimelo.bst;

/**
 * Node used by a binary search tree.
 */
public class Node<T extends Comparable<T>>{

    private T m_value;
    private Node<T> m_left;
    private Node<T> m_right;
    private Node<T> m_parent;

    /**
     * Create a new node with a specific value.
     * @param  value the value of the node to create.
     */
    public Node(final T value){
        m_value = value;
    }

    /**
     * @return the node's value.
     */
    public T value() {
        return m_value;
    }
    
    /**
     * Set the node value.
     * @param value the value to set.
     */
    public void setValue(final T value) {
        m_value = value;
    }

    /**
     * @return the left child node.
     */
    public Node<T> left() {
        return m_left;
    }

    /**
     * Set the left child node.
     * @param node The node to set as the left child.  If the node is not null 
     *             then the current node will be it's new parent.
     * 
     */
    public void setLeft(final Node<T> node) {
        m_left = node;
        assignParent(node);
    }

    /**
     * @return the right child node
     */
    public Node<T> right() {
        return m_right;
    }

    /**
     * Set the right child node.
     * @param node The node to set as the left child.  If the node is not null 
     *             then the current node will be it's new parent.
     * 
     */
    public void setRight(final Node<T> node) {
        m_right = node;
        assignParent(node);
    }
    
    /**
     * @return the node's parent.
     */
    public Node<T> parent() {
        return m_parent;
    }

    /**
     * Set the node's parent.
     * @param the parent not to set.
     */
    public void setParent(Node<T> node) {
        m_parent = node;
    }

    /**
     * @return true if the node has no children, false otherwise.
     */
    public boolean isLeaf() {
        return m_left == null && m_right == null;
    }
    
    /**
     * @return true if the node has no parent, false otherwise.
     */
    public boolean hasParent() {
        return m_parent != null;
    }

    /**
     * @return true if the current node is a left child of its parent, false otherwise.
     */
    public boolean isLeftChild() {
        return hasParent() && this == m_parent.left();
    }

    /**
     * @return true if the current node is a right child of its parent, false otherwise.
     */
    public boolean isRightChild() {
        return hasParent() && this == m_parent.right();
    }

    /**
     * Set the current node as parent node to another node.
     * @param node the node for which to set the parent.
     */
    private void assignParent(Node<T> node) {
        if(node != null){
            node.setParent(this);
        }
    }
    
    @Override
    public String toString() {
        return new StringBuilder()
                    .append("[")
                    .append("value=")
                    .append(m_value)
                    .append(", left=")
                    .append(m_left == null ? "null" : m_left.value())
                    .append(", right=")
                    .append(m_right == null ? "null" : m_right.value())
                    .append(", parent=")
                    .append(m_parent == null ? "null" : m_parent.value())
                    .append("]")
                    .toString();
    }
}