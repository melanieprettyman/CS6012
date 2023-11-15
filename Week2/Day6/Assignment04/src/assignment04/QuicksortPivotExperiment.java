package assignment04;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import static assignment04.SortUtil.*;

public class QuicksortPivotExperiment {
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(new File("RANDOM_PIVOT_test.tsv"))) {

            int[] inputSizes = {1000, 5000, 10000, 50000, 100000}; // Example input sizes

            Comparator<Integer> comparator = Comparator.naturalOrder(); // Use natural ordering

            //for (int threshold : thresholdValues) {
            //System.out.println("Threshold Value: " + threshold);
            for (int size : inputSizes) {
                ArrayList<Integer> array = generateWorstCase(size); // Generate a large random array
                long startTime = System.nanoTime();
                quicksort(array, comparator);
                long endTime = System.nanoTime();

                long runningTime = endTime - startTime;
                System.out.println("Input Size: " + size + ", Running Time: " + runningTime + " ns");
                fw.write( size + "\t" + runningTime + "\n"); // write to file.

            }
            System.out.println();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}

