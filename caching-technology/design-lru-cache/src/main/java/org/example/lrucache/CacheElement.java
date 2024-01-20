package org.example.lrucache;

// Cache 缓存中要存储的数据
// <Key, Value> 使用键值来快速的查找，泛型支持不同的存储类型
public class CacheElement<K,V> {

    private K key;
    private V value;

    public CacheElement(K key, V value) {
        this.value = value;
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
