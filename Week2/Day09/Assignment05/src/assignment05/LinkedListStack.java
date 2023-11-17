package assignment05;

import java.util.NoSuchElementException;

public class LinkedListStack<E> implements Stack<E>{

    private SinglyLinkedList<E> singlyLinkedList;

    public LinkedListStack() {
        //initialize the head variable to null, indicating an empty LinkedList.
        singlyLinkedList = new SinglyLinkedList<>();
    }

    //TODO; Include a zero-parameter constructor:
    /**
     * Removes all of the elements from the stack.
     */
    @Override
    public void clear() {
        //clear linked list
        singlyLinkedList.clear();
    }
    /**
     * @return true if the stack contains no elements; false, otherwise.
     */
    @Override
    public boolean isEmpty() {
        return singlyLinkedList.isEmpty();
    }
    /**
     * Returns, but does not remove, the element at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public E peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        //return the top element
        return singlyLinkedList.getFirst();
    }
    /**
     * Returns and removes the item at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public E pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
            return singlyLinkedList.deleteFirst();

    }
    /**
     * Adds a given element to the stack, putting it at the top of the stack.
     *
     * @param element - the element to be added
     */
    @Override
    public void push(E element) {
        singlyLinkedList.insertFirst(element);
    }
    /**
     * @return the number of elements in the stack
     */
    @Override
    public int size() {
        return singlyLinkedList.size();
    }
}
