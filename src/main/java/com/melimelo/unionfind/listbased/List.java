package com.melimelo.unionfind.listbased;

/**
 * A "specialized lightweight" implementation of singly linked list used by a
 * list-based union-find data structure.
 * #TODO refactor and extends the linked list impl.
 */
public final class List<T extends Comparable<T>> {

    private Node<T> m_head;
    private Node<T> m_tail;
    private int m_size;

    /**
     * Create a new list with one value.
     * 
     * @param value the value of the list to create.
     */
    public List(final T value) {
        append(value);
    }

    /**
     * Add a new value at the end of the list. A new node will be created to
     * keep the value added and this node will have a pointer to the current
     * list.
     * 
     * @param value the value to add.
     */
    public void append(final T value) {
        Node<T> node = new Node<T>(value, this);
        if (isEmpty()) {
            m_head = node;
            m_tail = m_head;
        } else {
            m_tail.setNext(node);
            m_tail = node;
        }
        node.setList(this);
        m_size++;
    }

    /**
     * Append a new list to the current list. Each node of the list appended is
     * updated to point to the current list.
     * 
     * @param list the list to append.
     */
    public void append(final List<T> list) {
        if (isEmpty()) {
            m_head = list.head();
        } else {
            m_tail.setNext(list.head());
        }
        m_tail = list.tail();
        m_size += list.size();
        Node<T> node = list.head();
        while (node != null) {
            node.setList(this);
            node = node.next();
        }
    }

    /**
     * Check if the list contains a value.
     * 
     * @param value the value to check.
     * @return true if the value to check is in the list, false otherwise.
     */
    public boolean contains(final T value) {
        if (isEmpty()) {
            return false;
        }

        Node<T> node = m_head;
        while (node != null && node.value().compareTo(value) != 0) {
            node = node.next();
        }
        return node != null;
    }

    /**
     * @return the list's head.
     */
    public Node<T> head() {
        return m_head;
    }

    /**
     * @return true if the list is empty.
     */
    public boolean isEmpty() {
        return m_head == null && m_tail == null;
    }

    /**
     * @return the number of nodes in the list;
     */
    public int size() {
        return m_size;
    }

    /**
     * @return the list's tail.
     */
    public Node<T> tail() {
        return m_tail;
    }

    @Override
    public String toString() {

        if (isEmpty()) {
            return "[]";
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("[");
        Node<T> node = m_head;
        while (node != null) {
            builder.append(node.value());
            if (node.next() != null) {
                builder.append(",");
            }
            node = node.next();
        }
        builder.append("]");
        return builder.toString();
    }
}