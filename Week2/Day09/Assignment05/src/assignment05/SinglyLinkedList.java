package assignment05;

import java.util.*;

public class SinglyLinkedList<E> implements List<E> {
    class Node<E> {
        E data;
        Node<E> next; //pointer to node

        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<E> head; //head points to the first node of the LinkedList
    private int size;

    Node<E> tail;



    //CONSTRUCTOR: ZERO PARAMETERS
    //use when we want to create an empty LinkedList and populate it later using other methods.
    public SinglyLinkedList() {
        //initialize the head variable to null, indicating an empty LinkedList.
        head = null;
        size = 0;
    }

    public SinglyLinkedList(ArrayList<E> arr) {
        tail = head;
        for (var x : arr) {
            //check if LinkedList is empty
            if (head == null) {
                //head and tail pointers need to be updated to point to the first element being added
                head = new Node(x); // Create a new Node and assign it to head
                tail = head; // Update tail to point to the same Node as head
            } else {
                tail.next = new Node(x); // Create a new Node and assign it to tail.next
                tail = tail.next; // Update tail to point to the newly added Node
            }

        }
        size = arr.size();

    }
    //TODO make iterator class (like library book) (extra credit O(1)??)


    /**
     * Inserts an element at the beginning of the list.
     * O(1) for a singly-linked list.
     *
     * @param element - the element to add
     */
    @Override
    public void insertFirst(E element) {
        //create a new Node object with the provided element
        Node<E> newElement = new Node<>(element);
        newElement.next = head; //update the new node's next pointer to the previous head
        head = newElement;// update the head to point to the new node
        size++;
    }

    /**
     * Inserts an element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index   - the specified position
     * @param element - the element to add
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index > size())
     */
    @Override
    public void insert(int index, E element) throws IndexOutOfBoundsException {
        //check if the index is out of bounds
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        //create a new Node object with the provided element
        Node<E> newElement = new Node<>((E) element);
        Node<E> prevNode = getPreviousNode(index);

        //If the index is 0, update the new node's next pointer to the previous head
        // update the head to point to the new node
        if (index == 0) {
            newElement.next = head;
            head = newElement;
        } else {
            //newElement points to what prevNode is point at
            newElement.next = prevNode.next;
            //prevNode is pointing to new element
            prevNode.next = newElement;
        }
        size++; //update size
    }

    /**
     * Gets the first element in the list.
     * O(1) for a singly-linked list.
     *
     * @return the first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E getFirst() throws NoSuchElementException {
        //head points to the first element, return the head to get the first elem
        if (head == null) {
            throw new NoSuchElementException("You aint gettin no head from me");
        }
        return head.data;
    }

    /**
     * Gets the element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index - the specified position
     * @return the element at the position
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        //check if the index is out of bounds
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        var node = getNode(index);

        //return data of the node
        return node.data;
    }

    /**
     * Deletes and returns the first element from the list.
     * O(1) for a singly-linked list.
     *
     * @return the first element
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E deleteFirst() throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }

        E deletedData = head.data; // Store the data of the node being deleted
        head = head.next; // Move the head to the next node
        size--; // Update size

        return deletedData; // Return the data of the deleted node
    }


    /**
     * Deletes and returns the element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index - the specified position
     * @return the element at the position
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
     */
    @Override
    public E delete(int index) throws IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        //check if the index is out of bounds
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if(index ==0){
            return deleteFirst();
        }
        Node<E> prevNode = getPreviousNode(index);
        var node = getNode(index);
        //set previous node to the node after the delete-node
        prevNode.next = prevNode.next.next;
        //update size
        size--;
        //return deleted element
        return node.data;
    }
//    @Override
//    public E delete(int index) throws IndexOutOfBoundsException {
//        if (head == null || index < 0 || index >= size) {
//            return null;
//        }
//
//        Node temp = head;
//        E deletedValue = null;
//        if (index == 0) {
//            head = temp.next;
//            //Update deleted value
//            deletedValue = (E) temp.data;
//        } else {
//            for (int i = 0; temp != null && i < index - 1; i++) {
//                temp = temp.next;
//            }
//
//            if (temp == null || temp.next == null) {
//                return null;
//            }
//
//            Node nextNode = temp.next.next;
//            //Save this.
//            deletedValue = (E) temp.next.data;
//            //Unlink deleted node from list.
//            temp.next = nextNode;
//        }
//
//        size--;
//        return deletedValue;
//    }
    /**
     * Determines the index of the first occurrence of the specified element in the list,
     * or -1 if this list does not contain the element.
     * O(N) for a singly-linked list.
     *
     * @param element - the element to search for
     * @return the index of the first occurrence; -1 if the element is not found
     */
    @Override
    public int indexOf(E element) {
        var node = head;
        int index = 0;
        while (node != null) { //Loop over the nodes
            if (node.data == element) { //if a node matches the node in question return it's index
                return index;
            }
            node = node.next;
            index++;
        }
        return -1;//element not found
    }

    /**
     * O(1) for a singly-linked list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * O(1) for a singly-linked list.
     *
     * @return true if this collection contains no elements; false, otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0; //if head equals null, return true
    }

    /**
     * Removes all of the elements from this list.
     * O(1) for a singly-linked list.
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Generates an array containing all of the elements in this list in proper sequence
     * (from first element to last element).
     * O(N) for a singly-linked list.
     *
     * @return an array containing all of the elements in this list, in order
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size]; //create an array the same size as LL
        Node<E> node = head;
        int index = 0;

        while (node != null) { //loop over nodes
            array[index++] = node.data; //assign the data at a node to index in array
            node = node.next;
        }
        return array; //return array
    }

    //HELPER FUNCTIONS
    public Node<E> getPreviousNode(int index) {
        Node<E> prevNode = head;
        var node = head;
        for (int i = 0; i < index - 1; i++) {
            prevNode = node.next;
        }
        return prevNode;
    }

    public Node<E> getNode(int index) {
        var node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }


    /**
     * @return an iterator over the elements in this list in proper sequence (from first
     * element to last element)
     */
    @Override
    public Iterator iterator() {
        return new myIterator(); // Instantiates a custom iterator
    }

    // Custom Iterator class implementing the Iterator interface
    public class myIterator implements Iterator<E> {
        Node<E> current; // Reference to the current Node
        private Node<E> previous; // Reference to the previous Node

        Boolean canRemove; // Flag indicating if removal is permitted

        // Constructor for the custom iterator
        public myIterator() {
            current = head; // Sets the iterator's current to the head of the list
            previous = null; // Initializes previous to null
            canRemove = false; // Initially, removal is not permitted
        }

        // Checks if there is another element in the list
        @Override
        public boolean hasNext() {
            return current != null; // Returns true if there is another element
        }

        // Retrieves the next element in the iteration
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements exist in the list");
            }
            E value = current.data; // Retrieves the data of the current Node
            //prevofprev = prev
            previous = current; // Updates the previous Node
            current = current.next; // Moves the iterator to the next Node
            canRemove = true; // Marks the element for removal
            return value; // Returns the data of the current Node
        }

        // Removes the last element returned by next()
        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("No more elements to iterate over");
            }

            if (previous == null) { //~~prevofprev ==null
                        //~~head=current
                // Removing the first element.
                head = head.next; // Moves the head to the next Node
                current = head; // Updates the iterator's current
            } else { //~~prevofprev.next = cuurent
                // Removing a non-first element.
                if (current != null) {
                    previous.next = current.next; // Removes the current Node from the list
                    current = previous.next; // Moves the iterator to the next Node
                } else {
                    // If there is no next element, set current to null.
                    current = null;
                }
            }
            //~~prev = null
            canRemove = false; // Resets the removal flag
            --size; // Decrements the size of the list
        }
    }

}
