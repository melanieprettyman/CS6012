package assignment03;

import java.lang.reflect.Array;
import java.util.*;

public class BinarySearchSet<E> implements SortedSet<E>{


    private E[] set_;

    private Comparator<? super E> comparator_;
    private int capacity_;
    private int size_;

    //EMPTY CONSTRUCTOR
        //this constructor is used to create the sorted set, sorted in natural order
        public BinarySearchSet(){
            //Initalize capacity
            capacity_=10;
            //initializes an empty array called set
            this.set_ = (E[]) new Object[capacity_];
            comparator_ = null;
            size_ = 0;


        }
    //COMPARATOR CONSTRUCTOR
        //this constructor is used to create the sorted set with specific comparator
        public BinarySearchSet(Comparator<? super E> comparator){
            //Initalize capacity
            capacity_=10;
            //initializes an empty array called set
            this.set_ = (E[]) new Object[capacity_];
            //initialize comparator and size
            comparator_= comparator;
            size_ = 0;
        }
    //COMPARATOR
        //This method will return the Comparator used to sort elements in array
        @Override
        public Comparator comparator() {
            return comparator_;
        }
    //GET FIRST
        //Get the first (lowest, smallest) element currently in this set. @throws NoSuchElementException if the set is empty
        @Override
        public E first() throws NoSuchElementException {
            if (isEmpty()) {
                throw new NoSuchElementException("Set is empty");
            }
            return set_[0];
        }

    //GET LAST
        //Get the last (farthest, largest) element currently in this set. @throws NoSuchElementException if the set is empty
        @Override
        public E last() throws NoSuchElementException {
            if (isEmpty()) {
                throw new NoSuchElementException("Set is empty");
            }
            //return last element
            return set_[this.size() -1];
        }

    //ADDS
        //Add the specified element to this set if it is not already present and not set to null.
        //return true if this set did not already contain the specified element
        @Override
        public boolean add(E element) {
            boolean added = false;

            //If set is null, create an array set add element to set
            if (set_ == null) {
                set_ = (E[]) new Object[1];
                set_[0] = element;
                size_ = 1;
                added = true;
                return added;
            }
            //If set does not contain this element, add it
            if(!contains(element)) {
                // Call binarySearch to find the insertion point
                int insertionPoint = binarySearch(element);
                // If insertionPoint is indeed greater than or equal to 0, it's used as is for the insertion point.
                //If insertionPoint is negative, indicating that the element was not found in the set, the logic -(insertionPoint + 1) is used.
                // The negation of (insertionPoint + 1) converts it back to a positive value that represents the insertion point.
                insertionPoint = insertionPoint >= 0 ? insertionPoint : -(insertionPoint + 1);
                //If size is greater than capacity then grow the array
                if (size_ >= capacity_) {
                    growArray();
                }
                //Shift the elements to the right, starting from the insertion point, to make space for the new element
                System.arraycopy(set_, insertionPoint, set_, insertionPoint + 1, size_ - insertionPoint);
                //assign the new element to the insertion point and increments the size of the set
                set_[insertionPoint] =  element;
                size_++;
                //return true
                added = true;
            }
                //else not added, return false
                return added;

        }

    //ADD-ALL
        //Adds all of the elements in the specified collection to this set if they are not already present and not set to null.
    //TODO: test
    @Override
    public boolean addAll(Collection<? extends E> elements) {
        //size of original collection
        int originalSize = this.size();

        //loop through all the elements in the collection, if the element is not already in the set,
        // use the add method to put them in the set.
        for (E obj : elements) {
            if(!this.contains(obj)){
                this.add(obj);
            }
        }
        //Size of modified collection
        int modifiedSize = this.size();

        //return true if the modified size is greater than original size (array grew)
        return modifiedSize > originalSize;
    }


    //CLEARS
        @Override
        public void clear() {
        size_ =0;
        }

    //CONTAINS
        //Return true if this set contains the specified element
        @Override
        public boolean contains(E element) {
            //Search for element in array with binary search
            int index = binarySearch((E) element);
            if (index >= 0) {
                return true; // Element already exists in the array
            }
            return false;
        }

    //CONTAINS-ALL
        //collection to be checked for containment in this set.
        // Returns true if this set contains all of the elements of the specified collection
    @Override
    public boolean containsAll(Collection<? extends E> elements) {
        //loop through all the elements in the collection, if any of the elements are not in the set then return false
        for (E obj : elements) {
            if (!this.contains(obj)) {
                return false;
            }
        }
        //else all the elements are in the set, return true
        return true;
    }

    //IS-EMPTY
        //Return true if this set contains no elements
        @Override
        public boolean isEmpty() {
            return (size_ == 0);
        }


    //REMOVE
        //Removes the specified element from this set if it is present. Returns true if this set contained the specified element
        @Override
        public boolean remove(E element){
            int index = binarySearch(element); // Find the index of the element in the array
            if (contains(element)) {
                // If the element is found, remove it from the array
                System.arraycopy(set_, index + 1, set_, index, size_ - index - 1);
                if (size_ > 0) {
                    set_[--size_] = null; // Set the last element to null
                }
                return true;
            }
                return false; // Element not found in the array
        }

    //REMOVE-ALL
        //Removes from this set all of its elements that are contained in the specified collection
        //return true if this set changed as a result of the call
    @Override
    public boolean removeAll(Collection elements) {
        List<E> elementsToRemove = new ArrayList<>(elements);
        boolean anyRemoved = false;

        for (E element : elementsToRemove) {
            if (this.contains(element)) {
                this.remove(element);
                anyRemoved = true;
            }
        }

        return anyRemoved;
    }

    //SIZE
    @Override
    public int size() {
        return size_;
    }

    //TO-ARRAY
        //return an array containing all of the elements in this set, in sorted (ascending) order.
    @Override
        public E[] toArray() {
        // Create a new array to avoid modifying the internal set_
//        return Arrays.copyOf(set_, size_);
        return Arrays.copyOf(set_, size_, (Class<? extends E[]>) set_.getClass());
        }

    //---------------------------------//
    //         HELPER METHODS
    //---------------------------------//

    //GROW-ARRAY
    //Dynamically grows the array (increases capacity)
    public void growArray() {
        //Dynamically allocate memory for a temporary array that is twice the size of the original.
        capacity_ = 2 * capacity_;
        //Copy the contents over from set to this temp array by looping over the temp array.
        E[] tempArray = Arrays.copyOf(set_, capacity_);
        //Set = the pointer to the temp array.
        set_ = tempArray;
        //Set the pointer to the temp array to nullptr.
        tempArray = null;
    }

//BINARY-SEARCH
    //If the target element is found in the set, the function returns the index where the element is located.
    //If the target element is not found, the function returns the negative of the insertion point.
private int binarySearch(E element) {
    int low = 0; // Index of the lowest element in the search range
    int high = size_ - 1; // Index of the highest element in the search range
    int cmp;

    while (low <= high) { // Perform a binary search within the range [low, high]
        int mid = (low + high) >>> 1; // Calculate the middle index of the current range

        if (comparator_ != null) {
            cmp = comparator_.compare(set_[mid], element);
        }
        else {
            @SuppressWarnings("unchecked")
            Comparable<? super E> midVal = (Comparable<? super E>) set_[mid];

            if (midVal == null) {
                // If the middle value is null, stop comparison
                // (This can indicate that the elements are not comparable)
                break;
            }

            cmp = midVal.compareTo(element); // Compare the middle value to the target element
        }
            if (cmp < 0) {
                low = mid + 1; // Update the search range: element is in the upper half
            } else if (cmp > 0) {
                high = mid - 1; // Update the search range: element is in the lower half
            } else {
                return mid; // Element found at index 'mid'
            }

    }
    // If the element is not found, return the negative of the insertion point
    // (-low - 1): low represents the index where the element should be inserted
    return -(low + 1);
    }

    //TODO: write & test
    //---------------------------------//
    //            ITERATOR
    //---------------------------------//
    //Create and iterator from custom iterator inner  class
    @Override
    public Iterator iterator() {
        return new MyIterator(this);
    }
    //HELPER ITERATOR FUNCTIONS
    //GET SET
    public E[] getSet_() {
        if(set_.equals(null)){
            throw new NullPointerException("Set DNE.");
        }
        return set_;
    }
    //GET
    //Get element from set
    public E get(int i ){
        if(i<0 || i>= size_){
            throw new IndexOutOfBoundsException("Index " + i + " out of bounds.");
        }
        return set_[i];
    }
    //ITERATOR INNER-CLASS
    class MyIterator implements Iterator<E> {
         E[] set_;

        int position;

        public MyIterator(BinarySearchSet<E> set) {
            //Class array is equal to array passed in
            this.set_ = set.getSet_();
        }
        //HAS-NEXT
        //returns true is there are more elements in the array
        @Override
        public boolean hasNext() {
            if (position < size_)
                return true;
            else
                return false;
        }
        //NEXT
        //return "next" element and "advance" the iterator
        @Override
        public E next() {
            E obj = get(position);
            position++;
            return  obj;
        }
        //REMOVE
        //remove the element that the most recent call to next returned
        @Override
        public void remove() {
            E obj = get(position);
            BinarySearchSet.this.remove(obj);
        }

    }

}
