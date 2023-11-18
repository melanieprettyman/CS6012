package assignment05;

import java.io.FileWriter;
import java.io.IOException;

public class timePushOperation {
    public static void main(String[] args) throws IOException {


        // Timing experiment for the push method
        Integer[] inputSizes = {10,
                20,
                50,
                100,
                200,
                400,
                800,
                1000,
                2000,
                5000,
                8000,
                10000}; // Varying input sizes

        for (Integer size : inputSizes) {
            // Create instances of ArrayStack and LinkedListStack
            ArrayStack<Integer> arrayStack = new ArrayStack<>();
            LinkedListStack<Integer> linkedListStack = new LinkedListStack<>();

            long startTime, endTime;


            // Timing ArrayStack push operation
            startTime = System.nanoTime();
            int element = 5; // Element to push
            for (int i = 0; i < size; i++) {
                arrayStack.push(element);

            }
            endTime = System.nanoTime();
            long arrayStackTime = endTime - startTime;

            // Timing LinkedListStack push operation
            startTime = System.nanoTime();
            for (int i = 0; i < size; i++) {
                linkedListStack.push(element);

            }
            endTime = System.nanoTime();
            long linkedListStackTime = endTime - startTime;


            System.out.println("\t" + size + "\t" + arrayStackTime + "\t");
            System.out.println("\t" + size + "\t"+ linkedListStackTime+"\t");

           // System.out.println("ArrayStack Peek Time: " + arrayStackTime + " ns");
           // System.out.println("LinkedListStack Peek Time: " + linkedListStackTime + " ns");
        }
    }
}