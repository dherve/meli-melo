package com.melimelo.unionfind.listbased;
import java.util.HashMap;
import java.util.Map;

/**
 * A list based union-find implementation. In order to maintain the unicity of
 * values, this data-structure is backed by a hashMap. The values used should
 * override {@link Object#equals(Object)} and {@link Object#hashCode()}
 */
public class UnionFind<T extends Comparable<T>> {
    // we're duplicating information:/ TODO find a way to avoid this
    private Map<T, Node<T>> m_nodes;

    public UnionFind() {
        m_nodes = new HashMap<T, Node<T>>();
    }

    /**
     * Create a new list with the provided value.
     * 
     * @param value the value of the list to be created.
     * @return A new list with one unique value provided.
     */
    public List<T> create(final T value) {
        validateNotNull(value);
        if (m_nodes.containsKey(value)) {
            throw new IllegalArgumentException(
                    "The value " + value + " already exists in another set");
        }
        List<T> list = new List<T>(value);
        m_nodes.put(list.head().value(), list.head());
        return list;
    }

    /**
     * Find the representative value of the list containing the provided value.
     * 
     * @param value the value for which to get the representative.
     * @return the representative value.
     */
    public T find(final T value) {
        validateInputValue(value);
        return m_nodes.get(value).list().head().value();
    }

    /**
     * Merge the lists containing the provided values, if those values are not
     * in the same list. If the list with the first element is longer than the
     * list containing the second element, then the list of with the second
     * value will be appended to the list with the first value. Otherwise the
     * list with the first value will be appended to the list with the second
     * value.All nodes in the appended list will be updated so that they can
     * point to the longest list.
     * 
     * @param first a value from the first list to merge.
     * @param second a value from the second list to merge.
     */
    public void union(final T first, final T second) {
        validateInputValue(first);
        validateInputValue(second);
        if (!find(first).equals(find(second))) {
            Node<T> node = m_nodes.get(first);
            Node<T> other = m_nodes.get(second);
            if (node.list().size() > other.list().size()) {
                node.list().append(other.list());
            } else {
                other.list().append(node.list());
            }
        }

    }

    /**
     * check if an input value is not null and doesn't already exists.
     * 
     * @param value the value to check.
     */
    private void validateInputValue(final T value) {
        validateNotNull(value);
        if (!m_nodes.containsKey(value)) {
            throw new IllegalArgumentException(
                    "The value " + value + " doesn't belongs to any set !");
        }
    }

    /**
     * Check if a value is not null.
     * 
     * @param value the value to check.
     */
    private void validateNotNull(final T value) {
        if (value == null) {
            throw new IllegalArgumentException("The value can't be null");
        }
    }
}