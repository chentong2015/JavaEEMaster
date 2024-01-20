package org.example.lrucache;

import java.util.Optional;

// 缓存需要对外提供的API接口
public interface Cache<K, V> {

    boolean put(K key, V value);

    Optional<V> get(K key);

    int size();

    boolean isEmpty();

    void clear();
}
