package lab03;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CopyTimingExperiment {
    private static final int ITER_COUNT = 100;


    public static void main(String[] args) {
        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1_000_000_000)
            ;

        try (FileWriter fw = new FileWriter(new File("copy_experiment.tsv"))) { // open up a file writer so we can write to file.
            for (int exp = 10; exp <= 20; exp++) { // This is used as the exponent to calculate the size of the set.
                int size = (int) Math.pow(2, exp); // or ..

                // Do the experiment multiple times, and average out the results
                long totalTime = 0;

                for (int iter = 0; iter < ITER_COUNT; iter++) {

                    // SET UP!
                    // Adding element to set
                    List<Integer> set =  new ArrayList<Integer>();
                    // creating object of Source list and destination List
                    List<Integer> destlst =  new ArrayList<Integer>();

                    for (int i = 0; i < size; i++) {
                        set.add(i);
                        destlst.add(0);
                    }


                    // TIME IT!
                    long start = System.nanoTime();
                    // copy element into destlst
                    Collections.copy(destlst, set);
                    long stop = System.nanoTime();
                    totalTime += stop - start;


                }
                double averageTime = totalTime / (double) ITER_COUNT;
                System.out.println(averageTime); // print to console
                fw.write(size + "\t" + averageTime + "\n"); // write to file.
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}