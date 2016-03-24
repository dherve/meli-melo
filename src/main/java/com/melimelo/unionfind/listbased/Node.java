package com.melimelo.unionfind.listbased;
/**
 * 
 */
public final class Node<T extends Comparable<T>> {
    private Node<T> m_next;
    private T m_value;
    private List<T> m_list;

    /**
     * Create a new node with the specified value.
     * 
     * @param value the value of the node.
     */
    /**
     * Create a new node with a value.
     * @param value the value of the node to create.
     * @param list a pointer to the list containing the node to be created.
     */
    public Node(final T value, final List<T> list) {
        m_value = value;
        m_next = null;
        m_list = list;
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
     * set the node's successor in the parent list.
     * 
     * @param next the node to be set as next.
     */
    public void setNext(final Node<T> next) {
        m_next = next;
    }

    /**
     * @return the list containing the node.
     */
    public List<T> list() {
        return m_list;
    }

    /**
     * Set the list containing the node.
     * 
     * @param list the list ot set.
     */
    public void setList(final List<T> list) {
        m_list = list;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                    .append("[value:")
                    .append(m_value)
                    .append(", next:")
                    .append(m_next == null ? "null" : m_next.value())
                    .append("]")
                    .toString();
    }
}