package assignment07;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChainingHashTableTest {
    private ChainingHashTable hashTable;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        HashFunctor hashFunctor = new GoodHashFunctor();
        hashTable = new ChainingHashTable(10, hashFunctor);
    }

    @org.junit.jupiter.api.Test
    void add() {
        assertTrue(hashTable.add("apple"));
        assertTrue(hashTable.add("banana"));
        assertFalse(hashTable.add("apple")); // Adding duplicate item
        assertEquals(2, hashTable.size());
    }

    @org.junit.jupiter.api.Test
    void addAll() {
        String[] fruits = {"apple", "banana", "orange"};
        hashTable.addAll(List.of(fruits));
        assertFalse(hashTable.addAll(List.of(fruits))); // Adding the same items again
        assertEquals(3, hashTable.size());
    }

    @org.junit.jupiter.api.Test
    void contains() {
        hashTable.add("apple");
        hashTable.add("banana");
        assertTrue(hashTable.contains("apple"));
        assertTrue(hashTable.contains("banana"));
        assertFalse(hashTable.contains("orange")); // Item not in the set
    }

    @org.junit.jupiter.api.Test
    void containsAll() {
        hashTable.add("apple");
        hashTable.add("banana");
        String[] fruits = {"apple", "banana"};
        assertTrue(hashTable.containsAll(List.of(fruits)));
        String[] itemsToCheck = {"apple", "banana", "orange"};
        assertFalse(hashTable.containsAll(List.of(itemsToCheck)));
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        hashTable.add("apple");
        hashTable.add("banana");
        hashTable.clear();
        assertEquals(0, hashTable.size());
        assertTrue(hashTable.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void remove() {
        hashTable.add("apple");
        assertTrue(hashTable.remove("apple"));
        assertFalse(hashTable.remove("orange")); // Item not in the set
        assertEquals(0, hashTable.size());
    }

    @org.junit.jupiter.api.Test
    void removeAll() {
        hashTable.add("apple");
        hashTable.add("banana");
        String[] fruits = {"apple", "banana"};
        assertTrue(hashTable.removeAll(List.of(fruits)));
        assertFalse(hashTable.removeAll(List.of(fruits))); // Items already removed
        assertTrue(hashTable.isEmpty());
    }
}