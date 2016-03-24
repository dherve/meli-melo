package com.melimelo.stacks;


/**
 * Array based stack implementation. The stack has a fixed capacity and does not
 * auto-increase when the stack is full.
 */
public class Stack<E extends Comparable<E>> {

    public static final int DEFAULT_CAPACITY = 256;
    private final int LOWER_BOUND_INDEX = -1;
    private final E [] m_elements;
    private final int m_capacity;
    private int m_indexTop = LOWER_BOUND_INDEX;

    /**
     * Create a new Stack with the default capacity.
     */
    public Stack() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Create a new Stack with a specific capacity.
     * @param capacity the stack capacity.
     */
    public Stack(final int capacity) {
        m_capacity = capacity;
        m_elements = (E []) new Comparable[capacity];
    }

    /**
     * Add a new element at the top of the stack.
     * @param element the element to add.
     * @throws StackOverFlowException if the stack is full.
     */
    public void push(final E element) throws StackOverFlowException {
        if(isFull()) {
            throw new StackOverFlowException("Can't add element to a full statck !");
        }
        m_elements[++m_indexTop] = element;
    }

    /**
     * @return the top element.
     * @throws StackUnderFlowException if the stack is empty.
     */
    public E peek() throws StackUnderFlowException {
        if(isEmpty()) {
            throw new StackUnderFlowException("Can't get top element from " + 
                                              "an empty stack !");
        }
        return m_elements[m_indexTop];
    }

    /**
     * Remove the top element from the stack.
     * @return the element removed.
     * @throws StackUnderFlowException if the stack is empty.
     */
    public E pop() throws StackUnderFlowException {
        if(isEmpty()) {
            throw new StackUnderFlowException("Can't remove top element from " + 
                                              "an empty stack !");
        }
        E element = m_elements[m_indexTop];
        m_elements[m_indexTop--] = null;
        return element;
    }

    /**
     * @return true if the stack is empty.
     */
    public boolean isEmpty() {
        return m_indexTop == LOWER_BOUND_INDEX;
    }

    /**
     * @return true if the stack is full, false otherwise.
     */
    public boolean isFull() {
        return m_indexTop == m_capacity - 1;
    }

    /**
     * Remove all elements from the stack.
     */
    public void clear() {
        for(int i = 0; i < m_capacity; i++) {
            m_elements[i] = null;
        }
        m_indexTop = LOWER_BOUND_INDEX;
    }

    /**
     * @return the number of elements in the stack.
     */
    public int size() {
        return isEmpty() ? 0 : m_indexTop + 1;
    }

    /**
     * @return  the stack capacity i.e the maximum number of elements that
     *          the stack can keep.
     */
    public int capacity() {
        return m_capacity;
    }

    /**
     * @return A string representation of the stack.
     */
    public String toString() {
        if(isEmpty()){
            return "[]";
        }
        
        final StringBuilder builder = new StringBuilder();
        final int end = isFull() ? m_capacity : m_indexTop + 1;
        builder.append("[");
        for(int i = 0; i < end; i++) {
            builder.append(m_elements[i]);
            if(i < (end - 1)) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}