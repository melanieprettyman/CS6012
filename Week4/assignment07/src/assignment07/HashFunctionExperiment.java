package assignment07;

import java.util.*;

public class HashFunctionExperiment {

    public static void main(String[] args) {


        int[] tableSizes = generateTableSizes(1,60000, 1000); // Add more sizes for broader testing

        // Create hash functors
        HashFunctor[] hashFunctions = {
                new GoodHashFunctor(),
                new MediocreHashFunctor(),
                new BadHashFunctor()
        };

        // Perform experiment for each hash function
        for (HashFunctor hashFunction : hashFunctions) {
            System.out.println("Evaluating hash function: " + hashFunction.getClass().getSimpleName());
            System.out.println("Size\tCollision Count\tAdd Time (ns)\tRemove Time (ns)\tSearch Time (ns)");
            for (int tableSize : tableSizes) {
                ChainingHashTable hashTable = new ChainingHashTable(tableSize, hashFunction);

                // Generate new test data for each run
                List<String> testData = generateTestData(tableSize);

                // Measure collision count
                int collisionCount = measureCollisions(hashTable, testData);

                // Measure operation time (e.g., add, remove, search)
                long addTime = measureAddTime(hashTable, testData);
                long removeTime = measureRemoveTime(hashTable, testData);
                long searchTime = measureSearchTime(hashTable, testData);

                // Output results in tabular format
                System.out.println(tableSize + "\t" + collisionCount + "\t" + addTime + "\t" + removeTime + "\t" + searchTime);

            }
            System.out.println(); // Add an empty line between hash functions
        }
    }

    // Helper method to generate test data
    private static List<String> generateTestData(int size) {
        List<String> testData = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            testData.add("string" + random.nextInt(1000)); // Generate random strings
        }
        return testData;
    }

    // Helper method to measure collisions
    private static int measureCollisions(ChainingHashTable hashTable, Collection<String> testData) {
        int collisionCount = 0;
        for (String data : testData) {
            hashTable.add(data);
        }

        // Iterate through the hash table's buckets
        for (LinkedList<String> bucket : hashTable.storage) {
            if (bucket != null && bucket.size() > 1) {
                collisionCount += bucket.size() - 1; // Count collisions in each non-empty bucket
            }
        }

        return collisionCount;
    }

    private static long measureAddTime(ChainingHashTable hashTable, Collection<String> testData) {
        long startTime = System.nanoTime();
        for (String data : testData) {
            hashTable.add(data);
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private static long measureRemoveTime(ChainingHashTable hashTable, Collection<String> testData) {
        long startTime = System.nanoTime();
        for (String data : testData) {
            hashTable.remove(data);
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private static long measureSearchTime(ChainingHashTable hashTable, Collection<String> testData) {
        long startTime = System.nanoTime();
        for (String data : testData) {
            hashTable.contains(data);
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    // Initialize hash table sizes
    private static int[] generateTableSizes(int start, int end, int step) {
        int sizeCount = (end - start) / step + 1;
        int[] tableSizes = new int[sizeCount];
        int index = 0;
        for (int size = start; size <= end; size += step) {
            tableSizes[index++] = size;
        }
        return tableSizes;
    }
}

