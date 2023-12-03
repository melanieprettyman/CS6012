package assignment03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchSetTest {
    BinarySearchSet<Integer> integerSet = new BinarySearchSet<>();
    BinarySearchSet<Integer> set = new BinarySearchSet<>();

    //EMPTY SET
    BinarySearchSet<Integer> emptySet = new BinarySearchSet<>();
    @BeforeEach
    void setUp() {
        //Add elements to array
        integerSet.add(1);
        integerSet.add(5);
        integerSet.add(2);
        integerSet.add(7);
        integerSet.add(8);
    }

    @Test
    void first() {
        //CASE: UTILITY
        //Size of the array should be 5, the fist element should be 1
        assertEquals(integerSet.size(), 5);
        assertEquals(integerSet.first(), 1);
    }

    @Test
    void last() {
        //CASE: UTILITY
        //last element should be 8
        assertEquals(integerSet.last(), 8);

    }

    @Test
    void add() {
        //CASE: UTILITY
        integerSet.add(69);
        assertEquals(integerSet.last(), 69);

        //CASE: DUPLICATE ADD
        //Cannot add duplicate values, should return false
        assertFalse(integerSet.add(69));

    }

    @Test
    void addAll() {
        //CASE: UTILITY
        // Create a new instance of MySet
        BinarySearchSet<Integer> addAllSet = new BinarySearchSet<>();

        // Create a collection of elements to add
        List<Integer> elements = Arrays.asList(1, 2, 3, 4, 5);

        // Call the addAll method
        boolean result = addAllSet.addAll(elements);

        // Assert that the method returns true
        assertTrue(result);

        // Assert that the set contains all the elements
        assertTrue(addAllSet.containsAll(elements));



    }


    @Test
    void contains() {
    //CASE: UTILITY
        //Test contains
        assertTrue(integerSet.contains(1));
        assertTrue(integerSet.contains(5));
        assertTrue(integerSet.contains(2));
        assertFalse(integerSet.contains(6));
    }

    @Test
    void containsAll() {
    //CASE: UTILITY
        List<Integer> collection = new ArrayList<>();
        collection.add(1);
        collection.add(2);
        collection.add(5);
        collection.add(7);
        collection.add(8);

        assertTrue(integerSet.containsAll(collection));

    }

    @Test
    void isEmpty() {
    //CASE: UTILITY
        //Empty set should be empty
        assertTrue(emptySet.isEmpty());
        //test isEmpty on non-empty set
        assertFalse(integerSet.isEmpty());

    }
    @Test
    void remove() {
        //CASE: UTILITY
        //remove last element
        integerSet.add(69);
        integerSet.remove(69);
        //Last element should be 7 and the size should now be 5
        assertEquals(integerSet.last(), 8);
        assertEquals(integerSet.size(), 5);
    }
    @Test
    void removeAll() {
        BinarySearchSet<Integer> set2 = new BinarySearchSet<>();

        set2.add(1);
        set2.add(2);
        set2.add(3);

        List<Integer> elementsToRemove = new ArrayList<>();
        elementsToRemove.add(2);
        elementsToRemove.add(4);

        boolean result = set2.removeAll(elementsToRemove);

        assertTrue(result);
        assertFalse(set2.contains(2));
        assertTrue(set2.contains(1));
        assertTrue(set2.contains(3));
    }

    @Test
    void toArray() {
        //CASE: UTILITY
        // Call the toArray() method
        Object[] result = integerSet.toArray();
        // Create an expected array with the elements in sorted order
        Object[] expected = {1,2,5,7,8,};

        // Assert that the result array is equal to the expected array
        assertArrayEquals(expected, result);
    }

    //ITERATOR AND CONTAINS ALL
    @Test
    void iterator() {
        //CASE: UTILITY
        BinarySearchSet<Integer> iteratorSet = new BinarySearchSet<>();
        Integer[] integerArray = {1,2,5,7,8,};

        //For each loop to iterate over integerSet and copy contents to empty BS
        var iterator = integerSet.iterator();
        while(iterator.hasNext()){
            var x = iterator.next();
            iteratorSet.add((Integer) x);
        }
        //Iterator set contain the same contents as IntegerSet (which is the same as integerArray), should be true
        assertTrue(iteratorSet.containsAll(List.of(integerArray)));
    }
    @Test
    void clear() {
    //CASE: UTILITY
        //clear set
        integerSet.clear();
        //Set should be empty
        assertTrue(integerSet.isEmpty());
    }

}