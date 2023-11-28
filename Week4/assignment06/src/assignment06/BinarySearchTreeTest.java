package assignment06;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {
    BinarySearchTree bt = new BinarySearchTree();
    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    BinarySearchTree<Integer> bts = new BinarySearchTree<>();



    ArrayList compare = new ArrayList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);

    }
    @org.junit.jupiter.api.Test
    void firtLast(){
        assertEquals(3, bt.first());
        assertEquals(9, bt.last());

        //CASE: Single Node Tree
        bst.add(1);
        assertEquals(1, bst.first());
        assertEquals(1, bst.last());
        assertTrue(bst.contains(1));

    }
    @org.junit.jupiter.api.Test
    void add() {
        //Assert the height of the tree
        int btHeight = bt.getHeight();
        assertEquals(2, btHeight);

        //CASE: adding null node
        assertThrows(NullPointerException.class, () -> {
            bst.add(null);
        });

        //CASE: duplicate add
        bst.add(5);
        assertFalse(bst.add(5));

        //CASE: standard add
        assertTrue(bst.add(6));

        }
    @org.junit.jupiter.api.Test
    void addAll() {
        List<Integer> items = Arrays.asList(5, 3, 7, 2, 4, 6, 8);

        bst.addAll(items);

        for (Integer item : items) {
            assertTrue(bst.contains(item));
        }
    //CASE: null list
        List<Integer> noItems = null;
        assertThrows(NullPointerException.class, () -> {
            bts.addAll(noItems);
        });
    }

    @org.junit.jupiter.api.Test
    void clear(){
        bt.clear();
        assertEquals(0, bt.size());
        assertTrue(bt.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void contains(){
        assertTrue(bt.contains(3));
        assertThrows(NullPointerException.class, () -> {
            bts.contains(null);
        });
    }
    @Test
    public void removing() {
        // Test removing elements
        assertTrue(bt.remove(9));
        assertFalse(bt.remove(10)); // Removing non-existing element, should return false
    }

    @org.junit.jupiter.api.Test
    void removeALL(){
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        List<Integer> items = Arrays.asList(5, 3, 7, 2, 4, 6, 8);

        bst.addAll(items);
        bst.removeAll(items);
        assertFalse(bst.containsAll(items));

    }
    @org.junit.jupiter.api.Test
    void toArrayList(){
        compare.add(3);
        compare.add(4);
        compare.add(5);
        compare.add(6);
        compare.add(7);
        compare.add(8);
        compare.add(9);
        assertEquals(compare, bt.toArrayList());

    }

//    @Test
//    public void testBalancedVsUnbalancedTrees() {
//        BinarySearchTree<Integer> balancedTree = new BinarySearchTree<>();
//        BinarySearchTree<Integer> unbalancedTree = new BinarySearchTree<>();
//
//        // Test balanced tree scenario
//        assertTrue(balancedTree.addAll(List.of(5, 3, 7, 2, 4, 6, 8)));
//        assertEquals(2, balancedTree.getHeight()); // Check the height for a balanced tree
//
//        // Test unbalanced tree scenario
//        assertTrue(unbalancedTree.addAll(List.of(1, 2, 3, 4, 5)));
//        assertTrue(unbalancedTree.remove(5)); // Remove to balance the tree
//        assertEquals(3, unbalancedTree.getHeight()); // Check the height for an unbalanced tree
//    }

    @Test
    public void testAddingElementsAtBoundaries() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        // Adding the minimum and maximum integer values
        assertTrue(bst.add(Integer.MIN_VALUE));
        assertTrue(bst.add(Integer.MAX_VALUE));

        // Verifying the elements are present in the BST
        assertTrue(bst.contains(Integer.MIN_VALUE));
        assertTrue(bst.contains(Integer.MAX_VALUE));
    }
    @Test
    public void testRemovingElementsAtExtremes() {
        // Adding elements
        bst.add(Integer.MIN_VALUE);
        bst.add(Integer.MAX_VALUE);

        // Removing the elements
        bst.remove(Integer.MIN_VALUE);
        bst.remove(Integer.MAX_VALUE);

        // Verifying the elements are no longer present in the BST
        assertFalse(bst.contains(Integer.MIN_VALUE));
        assertFalse(bst.contains(Integer.MAX_VALUE));
    }


}