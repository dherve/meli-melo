package com.melimelo.heap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.melimelo.heap.Heap;
import com.melimelo.heap.HeapException;
import com.melimelo.heap.HeapOverflowException;
import com.melimelo.heap.HeapUnderflowException;

public class HeapTests {

    private final int ELEMENTS_COUNT = 25;
    private final int MIN_ELEMENT = 1;
    private final int MAX_ELEMENT = 25;
    private final Heap.HeapType[] HEAP_TYPES = Heap.HeapType.values();

    private Integer[] m_randomizedArray;
    private List<Integer> m_randomizedList;

    @Before
    public void setUp() {
        m_randomizedArray = createRandomizedArray(MIN_ELEMENT, MAX_ELEMENT);
        m_randomizedList = createRandomizedList(MIN_ELEMENT, MAX_ELEMENT);
    }

    @Test
    public void testCreateHeapWithType() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type);
            verifyEmptyHeap(heap);
            assertEquals(type, heap.type());
        }
    }

    @Test
    public void testCreateHeapWithTypeAndCapacity() throws Exception {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type, ELEMENTS_COUNT);
            verifyEmptyHeap(heap);
            assertEquals(type, heap.type());

            int doubleTestCapacity = ELEMENTS_COUNT * 2;
            int element = 1;
            try {
                while (element < doubleTestCapacity) {
                    heap.iterativeInsert(element);
                    element++;
                }
            } catch (HeapException exception) {
                assertTrue(exception instanceof HeapOverflowException);

                // the top element should be the max when the heap is a max
                // type.
                if (type == Heap.HeapType.MAX_HEAP) {
                    assertEquals(ELEMENTS_COUNT, heap.get().intValue());
                }

                // the top element should be the min when the heap is a min
                // type.
                if (type == Heap.HeapType.MIN_HEAP) {
                    assertEquals(1, heap.get().intValue());
                }
                // The exception should be thrown once we exceed the heap's
                // capacity
                assertEquals(element, ELEMENTS_COUNT + 1);
            }

        }
    }

    @Test
    public void testCreateHeapWithTypeAndElements() throws Exception {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type, m_randomizedArray);
            assertEquals(type, heap.type());
            verifyFullHeap(heap, ELEMENTS_COUNT);

            if (type == Heap.HeapType.MAX_HEAP) {
                assertEquals(MAX_ELEMENT, heap.get().intValue());
            }

            if (type == Heap.HeapType.MIN_HEAP) {
                assertEquals(MIN_ELEMENT, heap.get().intValue());
            }
        }
    }

    @Test
    public void testGet() throws Exception {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type, m_randomizedArray);
            assertEquals(type, heap.type());
            verifyFullHeap(heap, ELEMENTS_COUNT);

            if (type == Heap.HeapType.MAX_HEAP) {
                assertEquals(MAX_ELEMENT, heap.get().intValue());
            }

            // the top element should be the min when the heap is a min type.
            if (type == Heap.HeapType.MIN_HEAP) {
                assertEquals(MIN_ELEMENT, heap.get().intValue());
            }
        }
    }

    @Test
    public void testGetFromEmptyHeap() throws Exception {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type);
            assertNull(heap.get());
        }
    }

    @Test
    public void testMaxHeapRecursiveInsert() throws Exception {
        Heap<Integer> heap = Heap.<Integer> newMaxHeap();
        verifyEmptyHeap(heap);
        assertEquals(Heap.HeapType.MAX_HEAP, heap.type());
        List<Integer> elementsAdded = new ArrayList<Integer>();
        for (int element : m_randomizedList) {
            heap.recusiveInsert(element);
            assertFalse(heap.isEmpty());
            elementsAdded.add(element);
            assertTrue(heap.contains(element));
            assertEquals(elementsAdded.size(), heap.size());
            assertEquals(Collections.max(elementsAdded), heap.get());
        }
    }

    @Test
    public void testMaxHeapIterativeInsert() throws Exception {
        Heap<Integer> heap = Heap.<Integer> newMaxHeap();
        verifyEmptyHeap(heap);
        assertEquals(Heap.HeapType.MAX_HEAP, heap.type());
        List<Integer> elementsAdded = new ArrayList<Integer>();
        for (int element : m_randomizedList) {
            heap.iterativeInsert(element);
            assertFalse(heap.isEmpty());
            elementsAdded.add(element);
            assertTrue(heap.contains(element));
            assertEquals(elementsAdded.size(), heap.size());
            assertEquals(Collections.max(elementsAdded), heap.get());
        }
    }

    @Test
    public void testRecursiveInsertNullValue() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type);
            verifyEmptyHeap(heap);
            try {
                heap.recusiveInsert(null);
            } catch (Exception exception) {
                assertTrue(exception instanceof IllegalArgumentException);
            }
        }
    }

    @Test
    public void testIterativeInsertNullValue() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type);
            verifyEmptyHeap(heap);
            try {
                heap.iterativeInsert(null);
            } catch (Exception exception) {
                assertTrue(exception instanceof IllegalArgumentException);
            }
        }
    }

    @Test
    public void testMinHeapRecursiveInsert() throws Exception {
        Heap<Integer> heap = Heap.<Integer> newMinHeap();
        verifyEmptyHeap(heap);
        assertEquals(Heap.HeapType.MIN_HEAP, heap.type());
        List<Integer> elementsAdded = new ArrayList<Integer>();
        for (int element : m_randomizedList) {
            heap.recusiveInsert(element);
            assertFalse(heap.isEmpty());
            elementsAdded.add(element);
            assertTrue(heap.contains(element));
            assertEquals(elementsAdded.size(), heap.size());
            assertEquals(Collections.min(elementsAdded), heap.get());
        }
    }

    @Test
    public void testRecursiveInsertInFullHeap() {
        int valueToInsert = 100;
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type, m_randomizedArray);
            try {
                heap.recusiveInsert(valueToInsert);
            } catch (HeapException exception) {
                assertTrue(exception instanceof HeapOverflowException);
            }
        }
    }

    @Test
    public void testIterativeInsertInFullHeap() {
        int valueToInsert = 100;
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type, m_randomizedArray);
            try {
                heap.iterativeInsert(valueToInsert);
            } catch (HeapException exception) {
                assertTrue(exception instanceof HeapOverflowException);
            }
        }
    }

    @Test
    public void testMinHeapIterativeInsert() throws Exception {

        Heap<Integer> heap = Heap.<Integer> newMinHeap();
        verifyEmptyHeap(heap);
        assertEquals(Heap.HeapType.MIN_HEAP, heap.type());
        List<Integer> elementsAdded = new ArrayList<Integer>();
        for (int element : m_randomizedList) {
            heap.iterativeInsert(element);
            assertFalse(heap.isEmpty());
            assertTrue(heap.contains(element));
            elementsAdded.add(element);
            assertEquals(elementsAdded.size(), heap.size());
            assertEquals(Collections.min(elementsAdded), heap.get());
        }
    }

    @Test
    public void testMaxHeapIterativeDelete() throws Exception {

        Heap<Integer> heap = Heap.<Integer> newMaxHeap(m_randomizedArray);

        Arrays.sort(m_randomizedArray);
        reverse(m_randomizedArray);
        int elementCount = m_randomizedArray.length;

        verifyFullHeap(heap, Heap.HeapType.MAX_HEAP, elementCount);

        while (!heap.isEmpty()) {
            int deleted = heap.iterativeDelete();
            int newMax = successor(deleted, m_randomizedArray);
            assertEquals(--elementCount, heap.size());

            // The element should no longer be there since there were no
            // duplicates
            // in the elements used to create the heap.
            assertFalse(heap.contains(deleted));
            if (!heap.isEmpty()) {
                assertEquals(newMax, heap.get().intValue());
            }
        }
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testMaxHeapRecusiveDelete() throws Exception {
        Heap<Integer> heap = Heap.<Integer> newMaxHeap(m_randomizedArray);

        Arrays.sort(m_randomizedArray);
        reverse(m_randomizedArray);
        int elementCount = m_randomizedArray.length;

        verifyFullHeap(heap, Heap.HeapType.MAX_HEAP, elementCount);

        while (!heap.isEmpty()) {
            int deleted = heap.recursiveDelete();
            int newMax = successor(deleted, m_randomizedArray);
            assertEquals(--elementCount, heap.size());

            // The element should no longer be there since there were no
            // duplicates
            // in the elements used to create the heap.
            assertFalse(heap.contains(deleted));
            if (!heap.isEmpty()) {
                assertEquals(newMax, heap.get().intValue());
            }
        }
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testMinHeapRecusiveDelete() throws Exception {
        Heap<Integer> heap = Heap.<Integer> newMinHeap(m_randomizedArray);
        Arrays.sort(m_randomizedArray);
        int elementCount = m_randomizedArray.length;

        verifyFullHeap(heap, Heap.HeapType.MIN_HEAP, elementCount);

        while (!heap.isEmpty()) {
            int deleted = heap.recursiveDelete();
            int newMax = successor(deleted, m_randomizedArray);
            assertEquals(--elementCount, heap.size());

            // The element should no longer be there since there were no
            // duplicates
            // in the elements used to create the heap.
            assertFalse(heap.contains(deleted));
            if (!heap.isEmpty()) {
                assertEquals(newMax, heap.get().intValue());
            }
        }
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testMinHeapIterativeDelete() throws Exception {
        Heap<Integer> heap = Heap.<Integer> newMinHeap(m_randomizedArray);
        Arrays.sort(m_randomizedArray);
        int elementCount = m_randomizedArray.length;

        verifyFullHeap(heap, Heap.HeapType.MIN_HEAP, elementCount);

        while (!heap.isEmpty()) {
            int deleted = heap.iterativeDelete();
            int newMax = successor(deleted, m_randomizedArray);
            assertEquals(--elementCount, heap.size());

            if (!heap.isEmpty()) {
                assertEquals(newMax, heap.get().intValue());
            }
        }
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testRecursiveDeleteFromEmptyheap() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type);
            try {
                heap.recursiveDelete();
            } catch (HeapException exception) {
                assertTrue(exception instanceof HeapUnderflowException);
            }
        }
    }

    @Test
    public void testIterativeDeleteFromEmptyheap() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type);
            try {
                heap.iterativeDelete();
            } catch (HeapException exception) {
                assertTrue(exception instanceof HeapUnderflowException);
            }
        }
    }

    @Test
    public void testRecursiveDeleteFromHeapWithDuplicates()
            throws HeapUnderflowException {
        Arrays.fill(m_randomizedArray, MAX_ELEMENT);
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type, m_randomizedArray);
            verifyFullHeap(heap, type, m_randomizedArray.length);
            while (heap.size() > 1) {
                assertEquals(MAX_ELEMENT, heap.recursiveDelete().intValue());
                assertTrue(heap.contains(MAX_ELEMENT));
            }
            assertEquals(MAX_ELEMENT, heap.recursiveDelete().intValue());
            assertFalse(heap.contains(MAX_ELEMENT));
        }
    }

    @Test
    public void testIterativeDeleteFromHeapWithDuplicates()
            throws HeapUnderflowException {
        Arrays.fill(m_randomizedArray, MAX_ELEMENT);
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type, m_randomizedArray);
            verifyFullHeap(heap, type, m_randomizedArray.length);
            while (heap.size() > 1) {
                assertEquals(MAX_ELEMENT, heap.iterativeDelete().intValue());
                assertTrue(heap.contains(MAX_ELEMENT));
            }
            assertEquals(MAX_ELEMENT, heap.recursiveDelete().intValue());
            assertFalse(heap.contains(MAX_ELEMENT));
        }

    }

    @Test
    public void testContainsPresentElements() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type, m_randomizedArray);
            for (Integer element : m_randomizedArray) {
                assertTrue(heap.contains(element));
            }
        }
    }

    @Test
    public void testContainsWithEmptyHeap() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type);
            verifyEmptyHeap(heap);
            for (Integer element : m_randomizedArray) {
                assertFalse(heap.contains(element));
            }
        }
    }

    @Test
    public void testContainsAbsentElements() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type, m_randomizedArray);
            for (int element = MAX_ELEMENT * 2, maxElement = MAX_ELEMENT * 3; element < maxElement; element++) {
                assertFalse(heap.contains(element));
            }

        }
    }

    @Test
    public void testContainsNullElement() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type, m_randomizedArray);
            assertFalse(heap.contains(null));
        }
    }

    @Test
    public void testIsEmpty() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> emptyHeap = new Heap<Integer>(type);
            verifyEmptyHeap(emptyHeap);

            Heap<Integer> fullheap = new Heap<Integer>(type, m_randomizedArray);
            assertFalse(fullheap.isEmpty());
        }
    }

    @Test
    public void testIsFull() {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> fullheap = new Heap<Integer>(type, m_randomizedArray);
            verifyFullHeap(fullheap, m_randomizedArray.length);
            Heap<Integer> emptyHeap = new Heap<Integer>(type);
            assertFalse(emptyHeap.isFull());
        }
    }

    @Test
    public void testSize() throws Exception {

        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type);
            int expectedHeapSize = 0;
            assertEquals(expectedHeapSize, heap.size());
            for (Integer element : m_randomizedArray) {
                heap.iterativeInsert(element);
                expectedHeapSize++;
                assertEquals(expectedHeapSize, heap.size());
            }
        }
    }

    @Test
    public void height() throws Exception {
        for (Heap.HeapType type : HEAP_TYPES) {
            Heap<Integer> heap = new Heap<Integer>(type);
            int expectedHeapHeight = 0;
            int elementdAdded = 0;
            assertEquals(expectedHeapHeight, heap.height());
            for (Integer element : m_randomizedArray) {
                heap.iterativeInsert(element);
                expectedHeapHeight = log2(++elementdAdded);
                assertEquals(expectedHeapHeight, heap.height());
            }
        }
    }

    @Test
    public void testNewMinHeap() {
        Heap<Integer> minHeap = Heap.newMinHeap();
        verifyEmptyHeap(minHeap);
        assertEquals(Heap.HeapType.MIN_HEAP, minHeap.type());
    }

    @Test
    public void testNewMaxHeap() {
        Heap<Integer> maxHeap = Heap.newMaxHeap();
        verifyEmptyHeap(maxHeap);
        assertEquals(Heap.HeapType.MAX_HEAP, maxHeap.type());
    }

    @Test
    public void testNewMinHeapWithValues() {
        Integer[] elements = createRandomizedArray(MIN_ELEMENT, MAX_ELEMENT);
        Heap<Integer> minHeap = Heap.newMinHeap(elements);
        verifyFullHeap(minHeap, Heap.HeapType.MIN_HEAP, elements.length);
        for (Integer element : elements) {
            assertTrue(minHeap.contains(element));
        }
    }

    @Test
    public void testNewMaxHeapWithValues() {
        Integer[] elements = createRandomizedArray(MIN_ELEMENT, MAX_ELEMENT);
        Heap<Integer> maxHeap = Heap.newMaxHeap(elements);
        verifyFullHeap(maxHeap, Heap.HeapType.MAX_HEAP, elements.length);
        for (Integer element : elements) {
            assertTrue(maxHeap.contains(element));
        }
    }

    // -------------------------------------------------------------------------
    //
    // Helper methods
    //
    // -------------------------------------------------------------------------
    /**
     * Verify if the heap is empty.
     * 
     * @param heap the heap to be verified
     */
    private void verifyEmptyHeap(final Heap<Integer> heap) {
        assertNotNull(heap);
        assertTrue(heap.isEmpty());
        assertFalse(heap.isFull());
        assertEquals(0, heap.size());
        assertEquals(0, heap.height());
    }

    /**
     * Verify if a heap is full.
     * 
     * @param heap the heap to verify.
     * @param type the type of the heap to verify
     * @param size the expected size of the heap to verify.
     */
    private void verifyFullHeap(final Heap<Integer> heap,
            final Heap.HeapType type, final int size) {
        assertEquals(type, heap.type());
        verifyFullHeap(heap, size);
    }

    /**
     * Verify if a heap is full.
     * 
     * @param heap the heap to verify.
     * @param size the expected size of the heap to verify.
     */
    private void verifyFullHeap(final Heap<Integer> heap, final int size) {
        assertNotNull(heap);
        assertFalse(heap.isEmpty());
        assertTrue(heap.isFull());
        assertEquals(size, heap.size());
    }

    /**
     * Reverse an array.
     * 
     * @param array the array to reverse.
     */
    private void reverse(Integer[] array) {
        int start = 0;
        int end = array.length - 1;
        if (end > start) {
            while (start < end) {
                Integer temp = array[start];
                array[start] = array[end];
                array[end] = temp;
                start++;
                end--;
            }
        }
    }

    /**
     * Return the element in the position to the position of the element
     * provided.
     * 
     * @param element the element for which to get the successor.
     * @param elements the array with elements.
     * @return the next element, -1 if the array is empty or the the element is
     *         the last one.
     */
    private Integer successor(Integer element, Integer[] elements) {

        for (int index = 0; index < elements.length; index++) {
            int indexNextElement = index + 1;
            if (element.equals(elements[index])
                    && indexNextElement < elements.length) {
                return elements[indexNextElement];
            }
        }
        return -1;
    }

    /**
     * Create a list with elements in randomized positions (not values).
     * 
     * @param min the maximum element.
     * @param max the minimum element.
     * @return A new list.
     */
    private List<Integer> createRandomizedList(int min, int max) {
        final List<Integer> elements = new ArrayList<Integer>();

        for (int element = min; element <= max; element++) {
            elements.add(element);
        }
        Collections.shuffle(elements);
        return elements;
    }

    /**
     * Create an array with elements in randomized positions (not values).
     * 
     * @param min the maximum element.
     * @param max the minimum element.
     * @return A new Array.
     */
    private Integer[] createRandomizedArray(int min, int max) {
        return createRandomizedList(min, max).toArray(new Integer[0]);
    }

    /**
     * Compute the logarithm in base two for a specific value. Used for
     * computing the heap height.
     * 
     * @param value the value for which to compute the logarithm.
     * @return the logarithm value of the provided value. Integer.MAX_VALUE will
     *         be returned when the value exceeds the range of positive integer.
     *         Integer.MIN_VALUE will be returned when the value exceeds the
     *         range of negative integer.
     */
    private int log2(final int value) {
        return Double.valueOf(Math.log((double) value) / Heap.DECIMAL_LOG_2).intValue();
    }
}