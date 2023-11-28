package assignment06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimingExperiments {
        public static void main(String[] args) throws IOException {

            for(int N = 100; N <= 4000; N+= 200){
                //creating a bst for each N in the range, going up by 100000
                BinarySearchTree<Integer> bstSorted = new BinarySearchTree<>();
                BinarySearchTree<Integer> bstRandom = new BinarySearchTree<>();

                //SORTED---
                for(int i = 1; i <= N; i++){
                    bstSorted.add(i); //adding in sorted order
                }

                long startTimeSorted = System.nanoTime();
                for (int i = 1; i <= N; i++){
                    bstSorted.contains(i);
                }
                long endtimeSorted = System.nanoTime();
                long timeTakenSorted = endtimeSorted - startTimeSorted;

                System.out.println(N + "\t" + timeTakenSorted);

                //RANDOM---

                long totalTimeRandom = 0;
                int runNums = 100;
                for(int i = 0; i < runNums; i++) {
                    bstRandom.clear();
                    List<Integer> randomOrder = generateRandomOrder(N);
                    for(int item :randomOrder){
                        bstRandom.add(item);
                    }

                    long startTimeRandom = System.nanoTime();
                    for(int j = 1; j <= N; j++){
                        bstRandom.contains(j);
                    }
                    long endTimeRandom = System.nanoTime();
                    totalTimeRandom += (endTimeRandom - startTimeRandom);
                }
                long timeTakenRandomAverage = totalTimeRandom / runNums;

                System.out.println(N + "\t" + timeTakenRandomAverage);

        }

    }

    public static List<Integer> generateRandomOrder(int N){
            List<Integer> randomOrder = new ArrayList<>();

            //adding integers from 1 to N in order
            for(int i = 1; i <= N; i++){
                randomOrder.add(i);
            }

            //method to put this list into a randomly shuffled order
            Collections.shuffle(randomOrder);

            return randomOrder;
    }

}
