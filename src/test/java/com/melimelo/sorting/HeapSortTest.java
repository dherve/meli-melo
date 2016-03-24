package com.melimelo.sorting;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.melimelo.arrays.ArraysUtils;
import com.melimelo.sorting.HeapSort;

public class HeapSortTest {
    private final int MAX_SIZE_ARRAY = 10000000;
    private final int MAX_TEST_VALUES_COUNT = 10;
    private final Integer [] EMPTY_ARRAY = new Integer[0];
    private Integer [] m_sortedValues = new Integer [MAX_TEST_VALUES_COUNT];
    private Integer [] m_testValues;

    @Before
    public void setUp() {
        m_testValues = new Integer [MAX_TEST_VALUES_COUNT];
        for(int index = 0; index < MAX_TEST_VALUES_COUNT; index ++) {
            m_sortedValues[index] = index;
            m_testValues[index] = index;
        }
    }

    @Test
    public void testSort() {
        ArraysUtils.shuffle(m_testValues);
        HeapSort.sort(m_testValues);
        assertArrayEquals(m_sortedValues, m_testValues);
    }

    @Test
    public void testSortEmptyArray() {
        HeapSort.sort(EMPTY_ARRAY);
        assertTrue(EMPTY_ARRAY.length == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSortNullArray() {
        HeapSort.sort(null);
    }

    @Test
    public void testSortedArray() {
        HeapSort.sort(m_testValues);
        assertArrayEquals(m_sortedValues, m_testValues);
    }

    @Test
    public void testSortArrayWithOneValue() {
        m_testValues = new Integer [] { 1 };
        HeapSort.sort(m_testValues);
        assertArrayEquals(new Integer [] { 1 }, m_testValues);
    }

    @Test
    public void testSortBigArray() {
        m_sortedValues= new Integer[MAX_SIZE_ARRAY];
        for(int index = 0; index < MAX_SIZE_ARRAY; index++) {
            m_sortedValues[index] = index;
        }
        m_testValues = ArraysUtils.copy(m_sortedValues);
        ArraysUtils.shuffle(m_testValues);
        HeapSort.sort(m_testValues);
        assertArrayEquals(m_sortedValues, m_testValues);
    }

    @Test
    public void testSortReversedArray() {
        ArraysUtils.reverse(m_testValues);
        HeapSort.sort(m_testValues);
        assertArrayEquals(m_sortedValues, m_testValues);
    }

    @Test
    public void testSortArrayWithDuplicateValues() {
        for(int i = 0 ; i < MAX_TEST_VALUES_COUNT; i += 5) {
            for(int j = i, end = i + 5;  j < end; j++) {
                m_sortedValues[j] = m_sortedValues[i];
            }
        }
        m_testValues = ArraysUtils.copy(m_sortedValues);
        ArraysUtils.shuffle(m_testValues);
        HeapSort.sort(m_testValues);
        assertArrayEquals(m_sortedValues, m_testValues);
    }    
}