package assignment05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListStackTest {


    private LinkedListStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new LinkedListStack<>();
    }

    @Test
    void testClear() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        stack.clear();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testIsEmpty() {
        assertTrue(stack.isEmpty());

        stack.push(10);
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPeek() {
        assertThrows(NoSuchElementException.class, () -> stack.peek());

        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.peek());
        assertEquals(20, stack.peek());
    }

    @Test
    void testPop() {
        assertThrows(NoSuchElementException.class, () -> stack.pop());

        stack.push(10);
        stack.push(20);
        stack.pop();
        assertEquals(10, stack.peek());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPush() {
        stack.push(10);
        stack.push(20);
        assertEquals(2, stack.size());
    }

    @Test
    void testSize() {
        assertEquals(0, stack.size());

        stack.push(10);
        stack.push(20);
        assertEquals(2, stack.size());

        stack.pop();
        assertEquals(1, stack.size());

        stack.clear();
        assertEquals(0, stack.size());
    }
}