package assignment07;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class ChainingHashTable implements Set<String> {
    public LinkedList<String>[] storage;
    private int capacity_;
    private HashFunctor functor_;

    private int size_; // Current size of table


    /**
     * CONSTRUCTOR
     * allows ChainingHashTable class to be used with any hash function
     * constructor accepts a function object containing the int hash(String item) method
     *
     * @param capacity size of table
     * @param functor  hashcode function
     */
    public ChainingHashTable(int capacity, HashFunctor functor) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive integer");
        }
        capacity_ = capacity;
        functor_ = functor;
        //create an array of LinkedList objects, and the type cast to string
        storage = (LinkedList<String>[]) new LinkedList[capacity_];

        size_ = 0;
    }




    /**
     * Ensures that this set contains the specified item.
     *
     * @param item - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * the input item was actually inserted); otherwise, returns false
     */
    @Override
    public boolean add(String item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null.");
        }

        //Determine the index in the array based on the hash code
        int index = getBucketIndex(item);

        //If there's no LinkedList at the calculated index, create a new one
        if(storage[index] == null){
            storage[index] = new LinkedList<String>();
        }

        // Item already exists in the set
        if(contains(item)){
            return false;
        }

        //Add it as a new HashNode.
        storage[index].add(item);
        size_++; // Increment the size of the table

        // If load factor goes beyond threshold, rehash the table
        double loadFactor = (double) size_ / capacity_;
        if (loadFactor > 0.70) {
            rehash();
        }
        return true; // Item successfully added to the set

    }

    private void rehash() {
        // Double the capacity
        int newCapacity = capacity_ * 2;

        // Create a new storage array with the new capacity
        LinkedList<String>[] newStorage = (LinkedList<String>[]) new LinkedList[newCapacity];

        // Rehash the elements from the old storage to the new storage
        for (LinkedList<String> bucket : storage) {
            if (bucket != null) {
                //for each chain in the bucket, add the chain to the new bucket
                for (String chain : bucket) {
                    int hashCode = functor_.hash(chain);
                    int newIndex = Math.abs(hashCode % newCapacity);
                    if (newStorage[newIndex] == null) {
                        newStorage[newIndex] = new LinkedList<>();
                    }
                    newStorage[newIndex].add(chain);
                }
            }
        }

        // Update the storage and capacity
        storage = newStorage;
        capacity_ = newCapacity;
    }
    /**
     * Ensures that this set contains all items in the specified collection.
     *
     * @param items
     *          - the collection of items whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         any item in the input collection was actually inserted); otherwise,
     *         returns false
     */
    @Override
    public boolean addAll(Collection<? extends String> items) {
        if (items == null) {
            throw new NullPointerException("Collection cannot be null.");
        }
        boolean added = false;
        //Loop through items in collection
        for (String item : items) {
            //if the item can be add (not in storage), add them and return true
            if (add(item)) {
                added = true;
            }
        }
        return added; //items already in storage, cannot add
    }
    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        size_ = 0;
        Arrays.fill(storage, null);


    }
    /**
     * Determines if there is an item in this set that is equal to the specified
     * item.
     *
     * @param item
     *          - the item sought in this set
     * @return true if there is an item in this set that is equal to the input item;
     *         otherwise, returns false
     */
    @Override
    public boolean contains(String item) {
        if(isEmpty() || item == null){
            return false;
        }
        //calculate the index of the bucket where the item should be located.
        int index = getBucketIndex(item);
        //the bucket (linked list) at the calculated index is retrieved from the storage array
        LinkedList<String> bucket = storage[index];

        if (bucket != null) {
            //iterate through each chain in the bucket
            for (String chain : bucket) {
                if (chain.equals(item)) {
                    return true;
                }
            }
        }

        return false; //item not found
    }

    // This implements hash function to find index for a key
    private int getBucketIndex(String item) {
        int hashCode = functor_.hash(item);
        int index = Math.abs(hashCode % capacity_);
        // key.hashCode() could be negative.
       // index = index < 0 ? index * -1 : index; //if index is less than zero, convert it to positive number
        return index;
    }


    /**
     * Determines if for each item in the specified collection, there is an item in
     * this set that is equal to it.
     *
     * @param items
     *          - the collection of items sought in this set
     * @return true if for each item in the specified collection, there is an item
     *         in this set that is equal to it; otherwise, returns false
     */
    @Override
    public boolean containsAll(Collection<? extends String> items) {
        for(String item: items){
            if(!contains(item)){
                return false;
            }
        }
        return true;
    }
    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item
     *          - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually removed); otherwise, returns false
     */
    @Override
    public boolean remove(String item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null.");
        }

        boolean removed = false;

        //Determine the index in the array based on the hash code
        int index = getBucketIndex(item);

        // Item exists in the storage
        if(contains(item)){
            //remove item
            storage[index].remove(item);
            size_--; // decrement the size of the table
            removed = true;
            }
        return removed;
    }
    /**
     * Ensures that this set does not contain any of the items in the specified
     * collection.
     *
     * @param items
     *          - the collection of items whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         any item in the input collection was actually removed); otherwise,
     *         returns false
     */
    @Override
    public boolean removeAll(Collection<? extends String> items) {
        if (items == null) {
            throw new NullPointerException("Collection cannot be null.");
        }
        boolean removed = false;

        //Iterate through items
        for(String item: items){
            //if the item can be removed (remove is checking for containment) then removed is true
            if (remove(item)) {
                removed = true;
            }
        }

        return removed;
    }
    /**
     * Returns the number of items in this set.
     */
    @Override
    public int size() {
        return size_;
    }
}
