package assignment03;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class AddTimingExperiment {

    private static final int ITER_COUNT = 100;

    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(new File("add_experiment.tsv"))) {
            Random random = new Random();

            for (int exp = 10; exp <= 20; exp++) {
                int size = (int) Math.pow(2, exp);
                long totalTime = 0;

                for (int iter = 0; iter < ITER_COUNT; iter++) {
                    // SET UP!
                    BinarySearchSet<Integer> set = new BinarySearchSet<>();
                    for (int i = 0; i < size; i++) {
                        set.add(i);
                    }
                    int addElement = random.nextInt(size);

                    // Remove and Add timing
                    set.remove(addElement);
                    long start = System.nanoTime();
                    set.add(addElement);
                    long stop = System.nanoTime();
                    totalTime += stop - start;
                }

                double averageTime = totalTime / (double) ITER_COUNT;
                System.out.println(size + "\t" + averageTime); // print to console
                fw.write(size + "\t" + averageTime + "\n"); // write to file.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
