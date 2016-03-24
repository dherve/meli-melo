package com.melimelo.stacks;

import com.melimelo.lists.LinkedList;
import com.melimelo.lists.Node;

/**
 * A stack backed by a list. Grow "infinitevly"
 */
public class StackList <T extends Comparable<T>>{

    private final LinkedList<T> m_elements = new LinkedList<T>();

    public StackList() {
    }

    /**
     * Add a new element at the top of the stack.
     * @param element the element to add.
     */
    public void push(final T element) {
        m_elements.prepend(element);
    }

    /**
     * Remove the element on the top of the stack.
     * @return the the element removed.
     * @throws StackListExceptionif the stack is empty.
     */
    public T pop() throws StackListException{
        if(m_elements.isEmpty()) {
            throw new StackListException("Can't pop element from an empty stack!");
        }

        try{
            Node<T> head = m_elements.head();
            m_elements.remove(head.value());
            return head.value();
        }catch (Exception exception){
            throw new StackListException("An exception thrown when removing " + 
                                         "the first element!", exception);
        }
    }

    /**
     * @return the value of the element on the top of the stack.
     * @throws StackListExceptionif the stack is empty.
     */
    public T peek() throws StackListException {
        if(m_elements.isEmpty()) {
            throw new StackListException("Can't pop element from an empty stack!");
        }
        try{
            return m_elements.head().value();
        }catch (Exception exception){
            throw new StackListException("An exception thrown accessing " + 
                                         "the top element!", exception);
        }
        
    }

    /**
     * @return true if the stack has no elements, false otherwise.
     */
    public boolean isEmpty(){
        return m_elements.isEmpty();
    }

    /**
     * @return the number of elements in the stack.
     */
    public int size() {
        return m_elements.size();
    }

    /**
     * Remove all elements from the stack.
     * @throws StackListException if the stack is empty.
     */
    public void clear() throws StackListException{
        if(m_elements.isEmpty()) {
            throw new StackListException("Can't clear an empty stack!");
        }

        try{
            m_elements.clear();
        }catch (Exception exception){
            throw new StackListException("An exception thrown when clearing " + 
                                         "the stack!", exception);
        }
    }
}