package assignment05;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
class SinglyLinkedListTest {
    ArrayList<Integer> arrayList = new ArrayList();
    ArrayList<Object> toArray = new ArrayList();
    SinglyLinkedList<Integer> emptyList;
    SinglyLinkedList<Integer> populatedList;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        emptyList = new SinglyLinkedList<>();

        ArrayList<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);

        populatedList = new SinglyLinkedList<>(values);

    }
    @org.junit.jupiter.api.Test

    void insertFirst() {
        emptyList.insertFirst(5);
        assertEquals(5, emptyList.getFirst());
        assertEquals(1, emptyList.size());
    }

    @org.junit.jupiter.api.Test
    void insert() {
        populatedList.insert(1, 10);
        assertEquals(10, populatedList.get(1));
        assertEquals(4, populatedList.size());
    }
    @org.junit.jupiter.api.Test
    void getFirst() {
        assertEquals(1, populatedList.getFirst());
        assertThrows(NoSuchElementException.class, () -> emptyList.getFirst());
    }

    @org.junit.jupiter.api.Test
    void get() {
        assertEquals(2, populatedList.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> populatedList.get(5));
    }

    @org.junit.jupiter.api.Test
    void deleteFirst() {
        populatedList.deleteFirst();
        assertEquals(2, populatedList.getFirst());
        //assertEquals(2, populatedList.size());
        assertThrows(NoSuchElementException.class, () -> emptyList.deleteFirst());
    }

//    @org.junit.jupiter.api.Test
//    void delete() {
//        populatedList.delete(1);
//        assertEquals(3, populatedList.get(1));
//        assertEquals(2, populatedList.size());
//        assertThrows(IndexOutOfBoundsException.class, () -> populatedList.delete(5));
//    }

    @org.junit.jupiter.api.Test
    void indexOf() {
        assertEquals(1, populatedList.indexOf(2));
        assertEquals(-1, populatedList.indexOf(5));
    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(0, emptyList.size());
        assertEquals(3, populatedList.size());
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertTrue(emptyList.isEmpty());
        assertFalse(populatedList.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void clear() {
        populatedList.clear();
        assertTrue(populatedList.isEmpty());
        assertEquals(0, populatedList.size());
    }

    @org.junit.jupiter.api.Test
    void toArray() {
        Object[] array = populatedList.toArray();
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }
    @org.junit.jupiter.api.Test

    void testIterator() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        ArrayList<Integer> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.insert(i, i);
            data.add(i);
        }

        Iterator<Integer> iterator = list.iterator();
        assertTrue(iterator.hasNext());

        // Test iterator remove
        iterator = list.iterator();
        iterator.next();
        iterator.remove(); // remove the first element

        // Check the size after removal
        assertEquals(data.size() - 1, list.size());

//iterator for stack of 1
    }

}