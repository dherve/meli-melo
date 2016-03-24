package com.melimelo.heap;

/**
 * Heap backed by an array as a backend data structure. The capacity of the heap
 * is fixed i.e once full the capacity doesn't get increase. Duplicate values
 * are allowed but null values insertion is not allowed. An attempt to insert
 * such value will throw an {@link IllegalArgumentException}
 */
public class Heap<T extends Comparable<T>> {

    /**
     * Define the heap types.
     */
    public static enum HeapType {

        /**
         * Max-heap: the parent is bigger than its children.
         */
        MAX_HEAP {
            public int sign() {
                return POSITIVE_SIGN;
            }
        },

        /**
         * Min-heap: the children are bigger than their parent.
         */
        MIN_HEAP {
            public int sign() {
                return NEGATIVE_SIGN;
            }
        };

        /**
         * Return the sign comparison used when comparing elements for
         * dominance. An element dominate another one if it comes first
         * according to the ordering used by the heap. The sign is affected to
         * the value returned by {@link Comparable#compareTo(Object)}
         * 
         * @return the comparison sign for the heap type.
         */
        public abstract int sign();

        public static int NEGATIVE_SIGN = -1;
        public static int POSITIVE_SIGN = 1;
    }

    public static final int DEFAULT_CAPACITY = 1024;

    /**
     * Define the natural logarithm of 2 used for computing the heap height. The
     * heap height is equals to the log in base 2 of the number of element which
     * is equals to log(n)/log(2).
     */
    public static final double DECIMAL_LOG_2 = Math.log((double) 2);

    /**
     * The expected value when testing for dominance between two elements, the
     * first element dominate the second element.
     */
    private final int ELEMENT_DOMINANCE_VALUE = 1;

    /**
     * The value returner by {@link Comparable#compareTo(Object)} when the two
     * elements compared are equals.
     */
    private final int ELEMENT_EQUALITY_VALUE = 0;

    /**
     * Index of the topmost element in the heap (the min for a min heap, the max
     * for a max heap). The index start at 1 instead of 0.
     */
    private final int TOP_ELEMENT_INDEX = 1;
    private final T[] m_elements;
    private final HeapType m_type;
    private int m_lastElementIndex;
    private int m_capacity;

    /**
     * Create a new Heap of the specified type.
     * 
     * @param type the type of the heap to create.
     */
    public Heap(HeapType type) {
        this(type, DEFAULT_CAPACITY);
    }

    /**
     * Create a new heap of a specific type and with a specified capacity.
     * 
     * @param type the type of the heap to create.
     * @param capacity the capacity of the heap to create.
     */
    @SuppressWarnings("unchecked")
    public Heap(final HeapType type, final int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(
                    "The capacity can't be negative!");
        }
        m_type = type;
        m_capacity = capacity;
        m_lastElementIndex = 0;
        m_elements = (T[]) new Comparable[capacity + 1];
    }

    /**
     * Create an new heap of the specified type with the elements provided.
     * 
     * @param type the type fo the heap to create.
     * @param elements the heap elements.
     */
    public Heap(final HeapType type, final T[] elements) {
        this(type, elements.length);
        for (int i = 0; i < elements.length; i++) {
            m_elements[i + 1] = elements[i];
        }
        m_lastElementIndex = elements.length;
        build();
    }

    /**
     * @return The value of the top element i.e the smallest element for a min
     *         heap and the biggest element for a max heap. If the heap is
     *         empty, the value returned will be null.
     */
    public T get() throws HeapUnderflowException {
        if (isEmpty()) {
            return null;
        }
        return m_elements[TOP_ELEMENT_INDEX];
    }

    /**
     * Insert a new element and recursively adjust the heap.
     * 
     * @param element the element to insert.
     * @throws HeapOverflowException if the Heap is full.
     */
    public void recusiveInsert(final T element) throws HeapOverflowException {
        verifyElementToInsert(element);
        if (isFull()) {
            throw new HeapOverflowException();
        }

        m_elements[++m_lastElementIndex] = element;
        recursiveMoveUp(m_lastElementIndex);
    }

    /**
     * Delete the top element and recursively adjust the heap.
     * 
     * @return The value of the element deleted.
     * @throws HeapUnderflowException if the heap is empty.
     */
    public T recursiveDelete() throws HeapUnderflowException {
        if (isEmpty()) {
            throw new HeapUnderflowException();
        }
        T topElement = m_elements[TOP_ELEMENT_INDEX];
        m_elements[TOP_ELEMENT_INDEX] = m_elements[m_lastElementIndex];
        m_elements[m_lastElementIndex--] = null;
        recursiveMoveDown(TOP_ELEMENT_INDEX);
        return topElement;
    }

    /**
     * Insert a new element and recursively adjust the heap.
     * 
     * @param element the element to insert.
     * @throws HeapOverflowException if the Heap is full.
     */
    public void iterativeInsert(final T element) throws HeapOverflowException {
        verifyElementToInsert(element);
        if (isFull()) {
            throw new HeapOverflowException();
        }
        m_elements[++m_lastElementIndex] = element;
        iterativeMoveUp(m_lastElementIndex);
    }

    /**
     * Delete the top element and recursively adjust the heap.
     * 
     * @return The value of the element deleted.
     * @throws HeapUnderflowException if the heap is empty.
     */
    public T iterativeDelete() throws HeapUnderflowException {
        if (isEmpty()) {
            throw new HeapUnderflowException();
        }
        T topElement = m_elements[TOP_ELEMENT_INDEX];
        swap(TOP_ELEMENT_INDEX, m_lastElementIndex);
        m_elements[m_lastElementIndex--] = null;
        iterativeMoveDown(TOP_ELEMENT_INDEX);
        return topElement;
    }

    /**
     * Check if an element is present in the heap.
     * 
     * @param element the element to check.
     * @return true if the element is present, false if the element is null or
     *         the heap is empty.
     */
    public boolean contains(final T element) {
        if (element == null || isEmpty()) {
            return false;
        }

        for (int index = TOP_ELEMENT_INDEX; index <= m_lastElementIndex; index++) {
            if (m_elements[index].compareTo(element) == ELEMENT_EQUALITY_VALUE) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return true if the heap is empty, false otherwise.
     */
    public boolean isEmpty() {
        return m_lastElementIndex == 0;
    }

    /**
     * @return true if the heap is full, false otherwise.
     */
    public boolean isFull() {
        return m_lastElementIndex == m_capacity;
    }

    /**
     * @return the number of elements in the heap.
     */
    public int size() {
        return m_lastElementIndex;
    }

    /**
     * @return the height of the heap (if it was represented as a tree)
     */
    public int height() {
        return isEmpty() ? 0 : log2(m_lastElementIndex);
    }

    /**
     * @return the heap type.
     */
    public HeapType type() {
        return m_type;
    }

    /**
     * @return A new empty max heap
     */
    public static <T extends Comparable<T>> Heap<T> newMaxHeap() {
        return new Heap<T>(HeapType.MAX_HEAP);
    }

    /**
     * @return A new empty min heap
     */
    public static <T extends Comparable<T>> Heap<T> newMinHeap() {
        return new Heap<T>(HeapType.MIN_HEAP);
    }

    /**
     * Create a new max heap with provided values.
     * 
     * @param values the values to use for building the heap.
     * @return A max-heap with the values provided.
     */
    public static <T extends Comparable<T>> Heap<T>
            newMaxHeap(final T[] values) {
        return new Heap<T>(HeapType.MAX_HEAP, values);
    }

    /**
     * Create a new min heap with provided values.
     * 
     * @param values the values to use for building the heap.
     * @return A min-heap with the values provided
     */
    public static <T extends Comparable<T>> Heap<T>
            newMinHeap(final T[] values) {
        return new Heap<T>(HeapType.MIN_HEAP, values);
    }

    /**
     * @return A String representation of the heap elements.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int index = TOP_ELEMENT_INDEX; index <= m_lastElementIndex; index++) {
            builder.append(m_elements[index]);
            if (index < m_lastElementIndex) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Build the the heap. Used when the heap is created from an array of
     * elements.
     */
    private void build() {
        for (int i = m_lastElementIndex / 2; i >= TOP_ELEMENT_INDEX; i--) {
            recursiveMoveDown(i);
        }
    }

    /**
     * Recursively move an element up in the hierarchy, until the top reached.
     * 
     * @param index the index of the element to move up.
     */
    private void recursiveMoveUp(final int index) {
        if (index != TOP_ELEMENT_INDEX) {
            int parentIndex = parentIndex(index);
            if (dominate(index, parentIndex)) {
                swap(index, parentIndex);
                recursiveMoveUp(parentIndex);
            }
        }
    }

    /**
     * Move an element up in the hierarchy, until the top reached.
     * 
     * @param index the index of the element to move up.
     */
    private void iterativeMoveUp(final int index) {
        int elementIndex = index;
        int parentIndex = parentIndex(index);
        while ((elementIndex != TOP_ELEMENT_INDEX) && 
                dominate(elementIndex, parentIndex)) {
            swap(parentIndex, elementIndex);
            elementIndex = parentIndex;
            parentIndex = parentIndex(parentIndex);
        }
    }

    /**
     * Recursively move an element down in the hierarchy, until the end of the
     * 
     * @param index the index of the element to move down.
     */
    private void recursiveMoveDown(final int index) {
        int dominantIndex = index;
        int leftChildIndex = leftChildIndex(index);
        int rightChildIndex = rightChildIndex(index);

        if (validateIndex(leftChildIndex) && 
            dominate(leftChildIndex, dominantIndex)) {
            dominantIndex = leftChildIndex;
        }

        if (validateIndex(rightChildIndex) && 
            dominate(rightChildIndex, dominantIndex)) {
            dominantIndex = rightChildIndex;
        }

        if (dominantIndex != index) {
            swap(dominantIndex, index);
            recursiveMoveDown(dominantIndex);
        }
    }

    /**
     * Move an element down in the hierarchy, until the end of the
     * 
     * @param index the index of the element to move down.
     */
    private void iterativeMoveDown(final int index) {
        int elementIndex = index;
        while (elementIndex < m_lastElementIndex) {
            int dominantIndex = elementIndex;
            int leftChildIndex = leftChildIndex(elementIndex);
            int rightChildIndex = rightChildIndex(elementIndex);

            // find out the dominant element between the left child
            // and the current dominant element
            if (validateIndex(leftChildIndex) && 
                dominate(leftChildIndex, dominantIndex)) {
                dominantIndex = leftChildIndex;
            }

            // find out the dominant element between the right child
            // and the current dominant element
            if (validateIndex(rightChildIndex) && 
                dominate(rightChildIndex, dominantIndex)) {
                dominantIndex = rightChildIndex;
            }

            if (dominantIndex == elementIndex) {
                break;
            }

            swap(elementIndex, dominantIndex);
            elementIndex = dominantIndex;
        }
    }

    /**
     * Compute the index of the left child.
     * 
     * @param index the parent index.
     * @return the left child index.
     */
    private int leftChildIndex(final int index) {
        return index * 2;
    }

    /**
     * Compute the index of the right child.
     * 
     * @param index the parent index
     * @return the right child index.
     */
    private int rightChildIndex(final int index) {
        return (index * 2) + 1;
    }

    /**
     * Compute the parent index.
     * 
     * @param index the child index.
     * @return the parent index.
     */
    private int parentIndex(final int index) {
        return index / 2;
    }

    /**
     * Check if an element dominate another one. For a max heap, the dominant
     * element is the one with the biggest value. For a min heap, the dominant
     * element is the one with the lowest value.
     * 
     * @param indexFirstElement the index of the first element.
     * @param indexSecondElement the index of the second element.
     * @return true if the first element dominate the second one, false
     *         otherwise.
     */
    private boolean dominate(final int indexFirstElement,
            final int indexSecondElement) {
        // CompareTo return 1 when the firstElement is bigger and -1 when
        // the first element is smaller.
        // We need to take into account the order of element depending of the
        // type of the heap, hence multiply with the comparison sign associated
        // to the heap type.
        // For a max heap, if the first element is bigger then it is the
        // dominant one
        // For a min heap, if the first element is smaller then it the dominant
        // one but in this case the value returned by CompareTo is -1.
        return m_elements[indexFirstElement]
                .compareTo(m_elements[indexSecondElement]) * m_type.sign() == ELEMENT_DOMINANCE_VALUE;
    }

    /**
     * Swap elements at specific position.
     * 
     * @param from the position of the first element to swap.
     * @param to the position of the second element ot swap.
     */
    private void swap(final int from, final int to) {
        T tmp = m_elements[from];
        m_elements[from] = m_elements[to];
        m_elements[to] = tmp;
    }

    /**
     * Check if an index is within the bounds of valid positions. Valid position
     * range from the first element index (1) and the size of the array used to
     * stored elements (capacity)
     * 
     * @param index the index to check.
     * @return true if the element is out of bounds, false otherwise.
     */
    private boolean indexOutOfBounds(final int index) {
        return index < TOP_ELEMENT_INDEX || index > m_capacity;
    }

    /**
     * Check if a specific index is not out of bounds and contains a non null
     * element.
     * 
     * @param index the index to check.
     * @return true if the index is not out of bounds and has a non null
     *         element, false otherwise.
     */
    private boolean validateIndex(final int index) {
        return !(indexOutOfBounds(index) || m_elements[index] == null);
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
        return Double.valueOf(Math.log((double) value) / DECIMAL_LOG_2).intValue();
    }

    /**
     * verify if an element to insert is not null. If so, then throws an
     * {@link IllegalArgumentException}
     * 
     * @param element the element to verify.
     */
    private void verifyElementToInsert(final T element) {
        if (element == null) {
            throw new IllegalArgumentException("Null elements are not allowed !");
        }
    }
}
