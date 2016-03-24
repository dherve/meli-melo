package com.melimelo.lists;
/**
 * Define a Node used by a single list. The node has a value and one pointer
 * to the next Node but not to the previous one.
 */
public class Node<T extends Comparable<T>>{
    private T m_value;
    private Node<T> m_next;

    /**
     * Create a new instance with the value and the successor uninitialized.
     */
    public Node() { }

    /**
     * Create a new node with the specified value.
     * @param value the value of the node.
     */
    public Node(final T value) {
        this(value,null);
    }

    /**
     * Create a new node with a specified value and set the pointer to the
     * successor Node in the parent list.
     * @param value the value of the node.
     * @param next  the Next node.
     */
    public Node(final T value, final Node<T> next) {
        m_value = value;
        m_next = next;
    }

    /**
     * Copy constructor.
     * @param node the Node from which to create the new one.
     */
    public Node(final Node<T> node) {
        this(node.value(),node.next());
    }

    /**
     * @return the value of the node.
     */
    public T value() {
        return m_value;
    }

    /**
     * @return the node's successor in the parent list.
     */
    public Node<T> next() {
        return m_next;
    }

    /**
     * Set the value of the node.
     * @param value the value to set.
     */
    public void setValue(final T value) {
        m_value = value;
    }

    /**
     * Set the successor of the node in the parent list.
     * @param next the value of successor to set.
     */
    public void setNext(final Node<T> next) {
        m_next = next;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(m_value);
        // builder.append("->");
        // builder.append(m_next == null ? "null" : m_next.value());
        return builder.toString();
    }
}