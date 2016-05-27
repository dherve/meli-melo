package com.melimelo.graphs;

/**
 * Define a generic no-op action. Can be used with algorithms that accept an
 * {@link IAction} and when no specific action is needed to be carried.
 */
public final class NoOpAction<T> implements IAction<T> {
    @Override
    public void processEdge(T start, T end) {
    }
}
