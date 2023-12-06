package assignment09;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TimingExperiment {

    //initializing the iteration count to 100
    private static final int ITER_COUNT = 100;

    public static void main(String[] args) {
        int beginning=1;
        int end=10001;
        int increment=1000;

        //set up the filewriter to hold the data
        try (FileWriter fw = new FileWriter(new File("BSPconstruction.tsv"))) {

            //setting up the size of each experiment - initially 2^10 and goes up to 2^20
            for (int size = beginning; size <= end; size+=increment) {
//                int size = (int) Math.pow(2, exp);

                //initialize the total time at 0 to be updated later
                long totalTime = 0;



                ArrayList<Segment> as = new ArrayList<>();

                double y =0;
                for (int i=0; i<size; i++){
                    as.add(new Segment (0, y, 1, y));
                    y+=.01;
                }


                //start loop
                for (int iter = 0; iter < ITER_COUNT; iter++) {

                    //start the timer (get the start time)
                    long start = System.nanoTime();

                    //execute the contains function
                    BSPTree bsp = new BSPTree(as);

                    //stop the timer (get the end time)
                    long stop = System.nanoTime();
                    //get the total time (stop time - start time = execution time)
                    totalTime += stop - start;
                }
                //get the average time from all 100 experiments per sample size
                double bulkTime = totalTime / (double) ITER_COUNT;





                totalTime=0;

                for (int iter = 0; iter < ITER_COUNT; iter++) {

                    //start the timer (get the start time)
                    long start = System.nanoTime();

                    BSPTree oneAtTime=new BSPTree();

                    //execute the contains function
                    for (Segment a : as) {
                        oneAtTime.insert(a);
                    }

                    //stop the timer (get the end time)
                    long stop = System.nanoTime();
                    //get the total time (stop time - start time = execution time)
                    totalTime += stop - start;
                }

                //get the average time from all 100 experiments per sample size
                double oneAtTime = totalTime / (double) ITER_COUNT;


                //write to the console and to a file what the average time was for each sample size

                var output = (size + "\t" + bulkTime + "\t" + oneAtTime);
                System.out.println(output);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}