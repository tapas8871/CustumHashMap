

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyMap<K, V> {
    private int capacity; // Bucket size
    private int size; // Number of entries in the hashmap
    private final int INITIAL_CAPACITY = 15;
    private List<Node<K, V>> bucket;

    public MyMap() {
        bucket = new ArrayList<>();
        capacity = INITIAL_CAPACITY;
        size = 0;
        for (int i = 0; i < capacity; i++) {
            bucket.add(null);
        }
    }

    private int getBucketIndex(K key) {
        int hash = key.hashCode();
        return hash % capacity;
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        Node<K, V> head = bucket.get(bucketIndex);
        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        Node<K, V> head = bucket.get(bucketIndex);
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }
        size++;
        Node<K, V> newEntry = new Node<>(key, value);
        head = bucket.get(bucketIndex);
        newEntry.next = head;
        bucket.set(bucketIndex, newEntry);
        double loadFactor = (1.0 * size) / capacity;
        if (loadFactor > 0.75) {
            rehash();
        }
    }

    private void rehash() {
        System.out.println("Rehashing");
        List<Node<K, V>> temp = bucket;
        bucket = new ArrayList<>();
        capacity = capacity * 2;
        size = 0;
        for (int i = 0; i < capacity; i++) {
            bucket.add(null);
        }
        for (int i = 0; i < temp.size(); i++) {
            Node<K, V> head = temp.get(i);
            while (head != null) {
                put(head.key, head.value);
                head = head.next;
            }
        }
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        Node<K, V> head = bucket.get(bucketIndex);
        Node<K, V> prev = null;
        while (head != null) {
            if (head.key.equals(key)) {
                if (prev == null) {
                    bucket.set(bucketIndex, head.next);
                } else {
                    prev.next = head.next;
                }
                head.next = null;
                size--;
                break;
            }
            prev = head;
            head = head.next;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(INITIAL_CAPACITY, bucket, capacity, size);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyMap other = (MyMap) obj;
        return INITIAL_CAPACITY == other.INITIAL_CAPACITY && Objects.equals(bucket, other.bucket)
                && capacity == other.capacity && size == other.size;
    }

    private class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
}