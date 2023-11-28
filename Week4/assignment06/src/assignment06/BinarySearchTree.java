package assignment06;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet<T>{

    class Node {
        T value_;
        Node left;
        Node right;

        int height;

        Node(T value) {
            this.value_ = value;
            right = null;
            left = null;
        }
    }

    Node root_; //Starting node of tree
   public int size_;
    /**
     * Ensures that this set contains the specified item.
     *
     * @param item
     *          - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually inserted); otherwise, returns false
     * @throws NullPointerException
     *           if the item is null
     */
    @Override
    public boolean add(T item) {

        if (item == null) {
            throw new NullPointerException();
        }
        if(this.contains(item)){
            return false; //if the item is already in the tree, return false
        }
        root_ = addRecursive(root_, item);
        // Call updateHeight after adding a node
        updateHeight();
        size_++;
        return true;
    }
    private Node addRecursive(Node current, T item) {
        if (current == null) {
            return new Node(item);
        }
        //compare the node to be added (NTA), to the current node the method is on (starts at the root)
        int comparisonResult = item.compareTo(current.value_);

        //if the NTA is less than the current node
        if (comparisonResult < 0) {
            //set NTA to the left of the current node, recursively call add
            current.left = addRecursive(current.left, item);

        //else NTA is greater than current node
        } else if (comparisonResult > 0) {
            //set NTA to the right of the current node, recursively call add
            current.right = addRecursive(current.right, item);
        } else {
            return current; // value already exists

        }
        return current; //returns new root of the subtree

    }

    /**
     * Ensures that this set contains all items in the specified collection.
     *
     * @param items
     *          - the collection of items whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         any item in the input collection was actually inserted); otherwise,
     *         returns false
     * @throws NullPointerException
     *           if any of the items is null
     */
    @Override
    public boolean addAll(Collection<? extends T> items) {
        //check if the items collection is null
        if (items == null) {
            throw new NullPointerException();
        }

        boolean changed = false; //initialize a boolean variable changed, to keep track of whether any items were actually added into the tree.

        //For each item, call the add method and check if it returns true, indicating that the item was inserted into the tree
        for (T item : items) {
            if (add(item)) {
                changed = true;
            }
        }

        return changed; //return the value of changed
    }

    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        root_ = null;
        size_ = 0;
    }

    /**
     * Determines if there is an item in this set that is equal to the specified
     * item.
     *
     * @param item
     *          - the item sought in this set
     * @return true if there is an item in this set that is equal to the input item;
     *         otherwise, returns false
     * @throws NullPointerException
     *           if the item is null
     */
    @Override
    public boolean contains(T item) {
        return containsRecursive(root_, item);
    }

    private boolean containsRecursive(Node index, T item){
        if(item == null){
            throw new NullPointerException();
        }
        //root null, doesn't contain the item
        if(index == null){
            return false;

        }

        if (item.compareTo(index.value_) == 0){
            return true; //item equals current node
        }
        //if the item is less than the current node, traverse the left subtree
        if(item.compareTo(index.value_) < 0){
            return containsRecursive(index.left, item);
        }
        else{
            //the item is greater than the current node, traverse the right subtree
            return containsRecursive(index.right, item);
        }

    }

    /**
     * Determines if for each item in the specified collection, there is an item in
     * this set that is equal to it.
     *
     * @param items
     *          - the collection of items sought in this set
     * @return true if for each item in the specified collection, there is an item
     *         in this set that is equal to it; otherwise, returns false
     * @throws NullPointerException
     *           if any of the items is null
     */
    @Override
    public boolean containsAll(Collection<? extends T> items) {
        // Check if the items collection is null
        if (items == null) {
            throw new NullPointerException();
        }

        // For each item, check if it exists in the set
        for (T item : items) {
            if (!contains(item)) {
                return false; // Return false if any item is not found in the set
            }
        }

        return true; // Return true if all items are found in the set
    }

    /**
     * Returns the first (i.e., smallest) item in this set.
     *
     * @throws NoSuchElementException
     *           if the set is empty
     */
    @Override
    public T first() throws NoSuchElementException {
        if (root_ == null) {
            throw new NoSuchElementException("Tree is empty");
        }
        Node current = root_;
        while (current.left != null) {
            current = current.left;
        }
        return current.value_;
    }

    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return root_ == null;
    }
    /**
     * Returns the last (i.e., largest) item in this set.
     *
     * @throws NoSuchElementException
     *           if the set is empty
     */
    @Override
    public T last() throws NoSuchElementException {
        Node current = root_;
        while (current.right != null) {
            current = current.right;
        }
        return current.value_;
    }
    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item
     *          - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually removed); otherwise, returns false
     * @throws NullPointerException
     *           if the item is null
     */
    @Override
    public boolean remove(T item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if(this.contains(item)){
            root_ = removeRecursive(root_, item);
            // Call updateHeight after removing a node
            updateHeight();
            size_--;
            return true;
        }
        return false;
    }
    private Node removeRecursive(Node current, T item) {
        if (current == null) {
            return null;
        }

        if (item.equals(current.value_)) {// Node to remove found, remove node

            //NODE HAS NO CHILDREN – replace this node with null in its parent node
            if (current.left == null && current.right == null) {
                return null;
            }

            //NODE HAS ONLY 1 CHILD– in the parent node, replace this node with its only child
                //returning the non-null child so it can be assigned to the parent node.
            if (current.right == null) {
                return current.left;
            }

            if (current.left == null) {
                return current.right;
            }

            //NODE HAS TWO CHILDREN – tree reorganization
                //Find the successor of the node to be removed. The successor is the smallest value in the right subtree of the NTR
                Node successor = findSuccessor(current.right);
                //Replace the value of the node to be removed with the value of its successor.
                current.value_ = successor.value_;
                //Remove the successor node from the tree. Recursively calling the remove on the right subtree, with successor
                current.right = removeRecursive(current.right, successor.value_);
        }

        //compare the node to be removed (NTR), to the current node the method is on (starts at the root)
        int comparisonResult = item.compareTo(current.value_);

        //if the NTR is less than the current node
        if (comparisonResult < 0) {
            current.left = removeRecursive(current.left, item);//traverse left
            return current;
        }
        //else the NTR is greater than the current node
       else {
           current.right = removeRecursive(current.right, item); //traverse right
        }
        return current;
    }

    //The successor is the smallest value in the right subtree of the node to be removed. Find the leftmost node in the right subtree.
    private Node findSuccessor(Node current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
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
     * @throws NullPointerException
     *           if any of the items is null
     */
    @Override
    public boolean removeAll(Collection<? extends T> items) {
        // Check if the items collection is null
        if (items == null) {
            throw new NullPointerException();
        }

        boolean changed = false; // Initialize a boolean variable changed to false

        // For each item, call the remove method and check if it returns true
        for (T item : items) {
            if (remove(item)) {
                changed = true;
            }
        }

        return changed; // Return the value of changed
    }
    /**
     * Returns the number of items in this set.
     */
    @Override
    public int size() {
        return size_;
    }
    /**
     * Returns an ArrayList containing all of the items in this set, in sorted
     * order.
     */
    @Override
    public ArrayList<T> toArrayList() {
        ArrayList<T> list = new ArrayList<>(); //create a new ArrayList called list to store the sorted items
        inOrderTraversal(root_, list);//call the inOrderTraversal() method, passing the root node and the list
        return list; //return list
    }

    private void inOrderTraversal(Node node, ArrayList<T> list) {
        // starts from the root and moves recursively to the left child until it encounters a null node
        if (node != null) {
            inOrderTraversal(node.left, list);
            //When it reaches the smallest element (a leaf node in the leftmost branch), it adds that element to the ArrayList before moving to the right
            list.add(node.value_);
            inOrderTraversal(node.right, list);
        }
    }


    //HELPER FUNCTIONS
    public void updateHeight() {
        calculateHeight(root_);
    }

    private int calculateHeight(Node node) {
        if (node == null) {
            return -1;
        }

        //calculate the height of the left and right subtrees of the current node
        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);

        //determine the height of the current node by taking the maximum of the two heights and adding 1
        int height = Math.max(leftHeight, rightHeight) + 1;

        //update the height variable of the node to the calculated height
        node.height = height;

        return height;
    }

    public int getHeight(){
    return root_.height;
    }
    // Driver method
    // Generates a .dot file representing this tree.
    // Use each node's hashCode to uniquely identify it
    public void writeDot(String filename) {
        try {
            PrintWriter output = new PrintWriter(new FileWriter(filename));
            output.println("graph g {");
            if (root_ != null)
                output.println(root_.hashCode() + "[label=\"" + root_ + "\"]");
            writeDotRecursive(root_, output);
            output.println("}");
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Recursively traverse the tree, outputting each node to the .dot file
    private void writeDotRecursive(Node n, PrintWriter output) throws Exception {
        if (n == null)
            return;
        if (n.left != null) {
            output.println(n.left.hashCode() + "[label=\"" + n.left + "\"]");
            output.println(n.hashCode() + " -- " + n.left.hashCode());
        }
        if (n.right != null) {
            output.println(n.right.hashCode() + "[label=\"" + n.right + "\"]");
            output.println(n.hashCode() + " -- " + n.right.hashCode());
        }

        writeDotRecursive(n.left, output);
        writeDotRecursive(n.right, output);
    }

}
