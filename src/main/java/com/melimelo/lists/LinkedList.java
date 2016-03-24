package com.melimelo.lists;
/**
 * Singly linked list implementation.
 */
public class LinkedList<T extends Comparable<T>>{

    private Node<T> m_head;
    private Node<T> m_tail;

    /**
     * Create a new instance with the head and tail uninitialized.
     */
    public LinkedList(){}

    /**
     * Create a new LinkedList and initialize the head of the list with the
     * specified value.
     * @param headValue the value of the head.
     */
    public LinkedList(final T headValue) {
        m_head = new Node<T>(headValue);
        m_tail = m_head;
    }

    /**
     * Add a value to the end of the list.
     * @param value the value to add.
     */
    public void append(final T value) {
        Node<T> node = new Node<T>(value);
        if(isEmpty()) {
            m_head = node;
        }else{
            Node<T> temp = m_head;
            while(temp.next() != null) {
                temp = temp.next();
            }
            temp.setNext(node);
        }
        m_tail = node;
    }

    /**
     * Add a value at the beginning of the list.
     * @param value the value to add.
     */
    public void prepend(final T value) {
        Node<T> node = new Node<T>(value);
        node.setNext(m_head);
        m_head = node;
    }

    /**
     * Remove the first occurence of value in the list.
     * @param value the value to remove.
     * @return A node with the value removed.
     * @throws ListOperationException if the list is empty. 
     */
    public Node<T> remove(final T value) throws ListOperationException{
        if(isEmpty()){
            throw new ListOperationException("Can't remove a value from an empty list!");
        }

        Node<T> node = m_head;
        Node<T> previous = null;
        while(node != null && node.value().compareTo(value) != 0) {
            previous = node;
            node = node.next();
        }

        if(previous != null && hasValue(node, value)) {
            // we found a node with the value.
            previous.setNext(node.next());
            node.setNext(null);
        }

        // if the node to delete is the head then the new node will be the new head
        if(m_head.value().compareTo(value) == 0){
            m_head = m_head.next();
        }

        // if the node to delete is the tail then the previous node will be the new tail
        if(node == m_tail) {
            m_tail = previous;
        }

        return node;
    }

    /**
     * Check if the list contains a value.
     * @param value the value to check.
     * @return true if the value to check is in the list, false otherwise.
     */
    public boolean contains(final T value) {
        if(isEmpty()) {
            return false;
        }

        Node<T> node = m_head;
        while(node != null && node.value().compareTo(value) != 0) {
            node = node.next();
        }
        return hasValue(node, value);
    }

    /**
     * Get the value at a specific position from teh beginning.
     * @param position The position of the value to get.
     * @return A node with the value at the specified position, 
     *         null if not found.
     */
    public Node<T> get(final int position) {
        int index = 0;
        Node<T> node = m_head;
        while(index != position && node != null){
            node = node.next();
            index ++;
        }
        return node;
    }

    /**
     * @return true if the list is empty.
     */
    public boolean isEmpty() {
        return m_head == null;
    }

    /**
     * @return the list's head.
     */
    public Node<T>  head() {
        return m_head;
    }

    /**
     * @return the list tail.
     */
    public Node<T> tail() {
        return m_tail;
    }

    /**
     * @return the number of elements in the list.
     */
    public int size() {

        if(isEmpty()) {
            return 0;
        }

        int count = 0;
        Node<T> node = m_head;
        while(node != null ){
            count ++;
            node = node.next();
        }
        return count;
    }

    /**
     * Remove all elements from the list.
     * @throws ListOperationException if the list is empty.
     */
    public void clear() throws ListOperationException{
        if(isEmpty()) {
            throw new ListOperationException("Can't clear an empty list!");
        }

        if(m_head.next() != null){
            Node<T> previous = m_head;
            Node<T> current = m_head.next();
            while(current != null) {
                previous = null;
                previous = current;
                current = current.next();
            }
        }

        m_head = null;
        m_tail = null;
    }

    @Override
    public String toString(){

        if(isEmpty()){
            return "[]";
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("[");
        Node<T> node = m_head;
        while(node != null) {
            builder.append(node.toString());
            if(node.next() != null) {
                builder.append(",");
            }
            node = node.next();
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Check if a node has a specific value.
     * @param node  the node to check.
     * @param value the value to check against the node value.
     * @return true if the node is not null and has the provided value, false
     *         otherwise.
     */
    private boolean hasValue(final Node<T> node, final T value) {
        return node != null && (node.value().compareTo(value) == 0);
    }
}