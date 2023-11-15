package assignment04;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class MergesortVSQuicksortExperiment {
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(new File("MERGEvsQUICK_test.tsv"))) {


            int[] listSizes = {1000, 5000,  8000, 10000, 50000, 100000}; // List sizes for benchmarking

            // Benchmarking best-case lists
            System.out.println("Best-Case List");
            fw.write(   "Best-Case List"); // write to file.

            for (int size : listSizes) {
                ArrayList<Integer> bestCaseList = SortUtil.generateBestCase(size);
                long startTime = System.nanoTime();
                SortUtil.mergesort(bestCaseList, Comparator.naturalOrder());
                long endTime = System.nanoTime();
                long runningTime = endTime - startTime;
                System.out.println("MERGE-SORT" + "List Size: " + size + ", Running Time: " + runningTime + " ns");
                fw.write(  "  MERGE" + "\t"+size + "\t" + runningTime + "\n"); // write to file.


                ArrayList<Integer> copy = new ArrayList<>(bestCaseList);
                long startTime2 = System.nanoTime();
                SortUtil.quicksort(copy, Comparator.naturalOrder());
                long endTime2 = System.nanoTime();
                long runningTime2 = endTime2 - startTime2;
                System.out.println("QUICK-SORT" + "List Size: " + size + ", Running Time: " + runningTime2 + " ns");
                fw.write(  "QUICK" + "\t"+size + "\t" + runningTime2 + "\n"); // write to file.

            }

            // Benchmarking average-case lists
            System.out.println("Average-Case List");
            fw.write(   "Average-Case List"); // write to file.

            for (int size : listSizes) {
                ArrayList<Integer> avgCaseList = SortUtil.generateAverageCase(size);
                long startTime = System.nanoTime();
                SortUtil.mergesort(avgCaseList, Comparator.naturalOrder());
                long endTime = System.nanoTime();
                long runningTime = endTime - startTime;
                System.out.println("MERGE-SORT" + "List Size: " + size + ", Running Time: " + runningTime + " ns");
                fw.write(  "  MERGE" + "\t"+size + "\t" + runningTime + "\n"); // write to file.


                ArrayList<Integer> copy = new ArrayList<>(avgCaseList);
                long startTime2 = System.nanoTime();
                SortUtil.quicksort(copy, Comparator.naturalOrder());
                long endTime2 = System.nanoTime();
                long runningTime2 = endTime2 - startTime2;
                System.out.println("QUICK-SORT" + "List Size: " + size + ", Running Time: " + runningTime2 + " ns");
                fw.write("QUICK" + "\t" + size + "\t" + runningTime2 + "\n"); // write to file.

            }

            // Benchmarking worst-case lists
            System.out.println("Worst-Case List");
            fw.write(   "Worst-Case List"); // write to file.

            for (int size : listSizes) {
                ArrayList<Integer> worstCaseList = SortUtil.generateWorstCase(size);
                long startTime = System.nanoTime();
                SortUtil.mergesort(worstCaseList, Comparator.naturalOrder());
                long endTime = System.nanoTime();
                long runningTime = endTime - startTime;
                System.out.println("MERGE-SORT" + "List Size: " + size + ", Running Time: " + runningTime + " ns");
                fw.write(  "  MERGE" + "\t"+size + "\t" + runningTime + "\n"); // write to file.

                ArrayList<Integer> copy = new ArrayList<>(worstCaseList);
                long startTime2 = System.nanoTime();
                SortUtil.quicksort(copy, Comparator.naturalOrder());
                long endTime2 = System.nanoTime();
                long runningTime2 = endTime2 - startTime2;
                System.out.println("QUICK-SORT" + "List Size: " + size + ", Running Time: " + runningTime2 + " ns");
                fw.write(  "QUICK" + "\t"+size + "\t" + runningTime2 + "\n"); // write to file.

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}