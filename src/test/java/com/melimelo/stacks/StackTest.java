package com.melimelo.stacks;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.melimelo.stacks.Stack;
import com.melimelo.stacks.StackOverFlowException;
import com.melimelo.stacks.StackUnderFlowException;

public class StackTest {

    public static final int STACK_CAPACITY = 25;

    @Test
    public void testCreateStackWithDetaultCapacity() {
        Stack<Integer> stack = new Stack<Integer>();
        verifyStackInitialization(stack);
        assertEquals(Stack.DEFAULT_CAPACITY, stack.capacity());
        assertEquals(0, stack.size());
    }

    @Test
    public void testCreateStackWithCapacity() {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization(stack);
        assertEquals(STACK_CAPACITY, stack.capacity());
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testPush() throws Exception {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization(stack);

        for(int element = 0; element < STACK_CAPACITY; element++) {
            stack.push(element);
            assertFalse(stack.isEmpty());
            assertEquals(element + 1, stack.size());
            assertEquals(element, stack.peek().intValue());
        }
        assertFalse(stack.isEmpty());
    }

    @Test(expected = StackOverFlowException.class)
    public void testPushElementOnFullStack() throws Exception {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization( stack);
        for(int element = 0; element < STACK_CAPACITY; element ++) {
            stack.push(element);
        }

        assertFalse(stack.isEmpty());
        for(int element = 0; element < STACK_CAPACITY; element ++) {
            stack.push(element);
        }
    }

    @Test
    public void testPop() throws Exception {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization(stack);
        
        for(int element = 0; element < STACK_CAPACITY; element ++) {
            stack.push(element);
        }

        assertFalse(stack.isEmpty());

        int elementPoppedCount = 0;
        int expectedElement = STACK_CAPACITY - 1;
        while(!stack.isEmpty()) {
            Integer top = stack.pop();
            assertNotNull(top);
            assertEquals(expectedElement, top.intValue());
            elementPoppedCount++;
            expectedElement--;
        }

        assertEquals(STACK_CAPACITY, elementPoppedCount);
        assertTrue(stack.isEmpty());
    }

    @Test(expected = StackUnderFlowException.class)
    public void testPopEmptyStack() throws Exception {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization(stack);
        stack.pop();
    }

    @Test
    public void testPeek() throws Exception {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization(stack);

        for(int element = 0; element < STACK_CAPACITY; element++) {
            stack.push(element);
            assertNotNull(stack.peek());
            assertEquals(element, stack.peek().intValue());
        }
    }

    @Test(expected = StackUnderFlowException.class)
    public void testPeekEmptyStack() throws Exception {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization(stack);
        stack.peek();
    }

    @Test
    public void testClear()  throws Exception {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization( stack);

        for(int element = 0; element < STACK_CAPACITY; element ++) {
            stack.push(element);
        }

        assertFalse(stack.isEmpty());
        assertTrue(stack.isFull());
        assertEquals(STACK_CAPACITY, stack.size());
        stack.clear();

        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
        assertEquals(0, stack.size());
    }

    @Test
    public void testClearEmptyStack() {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization(stack);
        stack.clear();
        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
        assertEquals(0, stack.size());
    }

    @Test
    public void testIsEmpty () {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization(stack);
    }

    @Test
    public void testIsFull()  throws Exception {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization(stack);

        for(int element = 0; element < STACK_CAPACITY; element ++) {
            stack.push(element);
        }
        assertFalse(stack.isEmpty());
        assertTrue(stack.isFull());
    }

    @Test
    public void testToString()  throws Exception {
        Stack<Integer> stack = new Stack<Integer>(STACK_CAPACITY);
        verifyStackInitialization(stack);

        for(int element = 0; element < STACK_CAPACITY; element ++) {
            stack.push(element);
        }

        String stackRepresentation = stack.toString();
        assertTrue(containsChar(stackRepresentation, '['));
        assertTrue(containsChar(stackRepresentation, ']'));

        for(int element = 0; element < STACK_CAPACITY; element ++) {
            assertTrue(containsInt(stackRepresentation, element));
        }
    }

    private boolean containsChar(final String string, final char c){
        return string.indexOf(c) != -1;
    }

    private boolean containsInt(final String string, final int value){
        return string.indexOf(String.valueOf(value)) != -1;
    }

    private void verifyStackInitialization(final Stack <Integer> stack) {
        assertNotNull(stack);
        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
    }
}