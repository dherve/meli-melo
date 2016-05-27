package com.melimelo.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class MapUtils {
    private MapUtils() {
        throw new AssertionError("Thou shall not instantiate this class!");
    }

    /**
     * Create a map with default keys and the same default value for all keys.
     * 
     * @param keys the keys of the map to create.
     * @param defaultValue the default value for all keys of the map.
     * @return a map with the keys the value provided
     */
    public final static <K, V> Map<K, V> createMap(final Set<K> keys,
            final V defaultValue) {
        Map<K, V> map = new HashMap<K, V>();
        for (K key : keys) {
            map.put(key, defaultValue);
        }
        return map;
    }
}
