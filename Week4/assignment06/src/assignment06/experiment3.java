package assignment06;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class experiment3 {


    public static void main(String[] args) {
        // Define the range for N (number of items)
        int startN = 1000;
        int endN = 10000;
        int step = 100;

        System.out.println("N, TreeSetAddTime(ns), TreeSetContainsTime(ns), BSTAddTime(ns), BSTContainsTime(ns)");

        for (int N = startN; N <= endN; N += step) {
            TreeSet<Integer> treeSet = new TreeSet<>();
            BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();

            // Generate a sorted ArrayList of N integers
            ArrayList<Integer> sortedList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                sortedList.add(i);
            }

            // Record time for adding N items to TreeSet
            long treeSetAddStart = System.nanoTime();
            treeSet.addAll(sortedList);
            long treeSetAddTime = System.nanoTime() - treeSetAddStart;

            // Record time for 'contains' operation in TreeSet
            long treeSetContainsStart = System.nanoTime();
            for (Integer num : sortedList) {
                treeSet.contains(num);
            }
            long treeSetContainsTime = System.nanoTime() - treeSetContainsStart;

            // Record time for adding N items to BinarySearchTree
            long bstAddStart = System.nanoTime();
            binarySearchTree.addAll(sortedList);
            long bstAddTime = System.nanoTime() - bstAddStart;

            // Record time for 'contains' operation in BinarySearchTree
            long bstContainsStart = System.nanoTime();
            for (Integer num : sortedList) {
                binarySearchTree.contains(num);
            }
            long bstContainsTime = System.nanoTime() - bstContainsStart;

            // Output the results for this N value
            System.out.println(N + "\t" + treeSetAddTime + "\t" + treeSetContainsTime + "\t" + bstAddTime + "\t" + bstContainsTime);
        }
    }
}

