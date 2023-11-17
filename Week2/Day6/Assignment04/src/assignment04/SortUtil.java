package assignment04;

import java.util.*;

public class SortUtil<T> {

    private static int threshold_;
    static int pivotIndex_;
    private static int size;



    /**
     * DRIVER METHOD
     *  Redirects to recursive method, prevents user-error
     *
     * @param array array list to be sorted
     * @param comparator passed in
     * @param <T> Generics Type
     */

    public static <T> void mergesort(ArrayList<T> array, Comparator<? super T> comparator) {
        int start = 0;
        int end = array.size() - 1;

        mergeSortRecursive(array, start, end, comparator);
    }

    /**
     * MERGE-SORT METHOD
     *  Recursive method, sorts array based on comparator
     * @param array array list to be sorted
     * @param start begining of array
     * @param end end of array
     * @param comparator passed in
     * @param <T> Generics Type
     */
    private static <T> void mergeSortRecursive(ArrayList<T> array, int start, int end, Comparator<? super T> comparator) {
//        if (end - start <= 2) {
//            return;
//        }
        threshold_ = 500;

        //if the array length is less than the threshold, do insertion sort
        if(end - start <= threshold_){
            //needs start end, to only sort smaller array
            insertionSort(array, comparator);
        } else { //merge-sort
            //Get the middle index
            int middle = (start + end) / 2;
            //sort left
            mergeSortRecursive(array, start, middle, comparator);
            //sort right
            mergeSortRecursive(array, middle + 1, end, comparator);
            //merge
            merge(array, start, middle, end, comparator);
        }

    }

    /**
    *    MERGE METHOD
     *    splits arraylist into two and sorts both (based on the comparator)
     *    arrays in real time into original array
     * @param array array list to be sorted
     * @param start begining of array
     * @param end end of array
     * @param middle middle index which divides the arraylist
     * @param comparator passed in
     * @param <T> Generics Type
     */
    public static <T> void merge(ArrayList<T> array, int start, int middle, int end, Comparator<? super T> comparator) {
        //Initalize two temp arrays for left and right side of OG array
        T[] leftArray = (T[]) new Comparable[middle - start + 1];
        T[] rightArray = (T[]) new Comparable[end - middle];

        //TODO; Put copy [] in driver, n/2 size so its always safe and not make a ton of copies
        //Copy into temp arrays
        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = array.get(start + i);
        }

        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = array.get(middle + 1 + i);
        }

        //initialize indices
        int leftIndex = 0, rightIndex = 0;

        int currentIndex = start;

        //While L/R are smaller than their array length
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            //If left index is less than right index
            if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {
                //put the smaller value into the current OG array position
                array.set(currentIndex, leftArray[leftIndex]);
                //moves to compare
                leftIndex++;
            } else {//else right is smaller
                //put the smaller value into the current OG array position
                array.set(currentIndex, rightArray[rightIndex]);
                //moves to compare
                rightIndex++;
            }
            //updating current index in OG array
            currentIndex++;
        }
        //while left has more elements
        while (leftIndex < leftArray.length) {
            //copy those into OG array + increment
            array.set(currentIndex++, leftArray[leftIndex++]);
        }
        //while right has more elements
        while (rightIndex < rightArray.length) {
            //copy those into OG array + increment
            array.set(currentIndex++, rightArray[rightIndex++]);
        }
    }


    /**
     *    INSERTION SORT
     *      builds a sorted list one element at a time by looping over array comparing pairs of elements,
     *      based on comparator arranges element
     * @param arrayList arraylist passed in via Merge-sort
     *                  (could be whole O.G array or a partial array based on threshold)
     * @param comparator passed in comparator
     * @param <T> generics type
     */
    public static <T> void insertionSort(ArrayList<T> arrayList, Comparator<? super T> comparator) {
        int length = arrayList.size() ;

        for (int i = 1; i < length; i++) {
            T current = arrayList.get(i);
            int j = i - 1;

            // Keep moving elements to the right until the correct position is found
            while (j >= 0 && comparator.compare(arrayList.get(j), current) > 0) {
                // Shift elements to the right
                arrayList.set(j + 1, arrayList.get(j));
                j--;
            }

            // Set the correct position for the current element
            arrayList.set(j + 1, current);
        }
    }

//--------------

    /**
     *DRIVER METHOD
     *       *  Redirects to recursive method, prevents user-error
     * @param arrayList inputted arraylist
     * @param comparator inputted comparator
     * @param <T> generics type
     */
    public static <T> void quicksort(ArrayList<T> arrayList, Comparator<? super T> comparator) {
        int start = 0;
        int end = arrayList.size() -1;
        quicksortRecurse(arrayList, start, end, comparator);
    }

    /**
     *
     * RECURSIVE QUICK-SORT
     *  Partition the array around a pivot
     *  Sort the part before the pivot
     *  Sort the part after the pivot
     *  After each recursive call, pivot position is updated
     * @param arrayList
     * @param startIndex
     * @param endIndex
     * @param comparator
     * @param <T>
     */
    private static <T> void quicksortRecurse(ArrayList<T> arrayList, int startIndex, int endIndex, Comparator<? super T> comparator) {
        // verify that the start and end index have not overlapped
        if(endIndex - startIndex < 1)
        {
            return;
        }
        // Calculating the Pivot Index
        int pivotIndex = (startIndex + endIndex)/2;

        //Calculate the pivotPosition
        int pivotPosition = partition(arrayList, startIndex, endIndex, pivotIndex, comparator);
        // sort the left sub-array
        quicksortRecurse(arrayList, startIndex, pivotPosition, comparator);
        // sort the right sub-array
        quicksortRecurse(arrayList, pivotPosition + 1, endIndex, comparator);
    }

    /**
     *PARTITION
     *  moves elements in the array so that all elements smaller than the pivot come before it
     *          in the array and all elements larger come after
     *  returns the index of the pivot after partitioning
     * @param arrayList
     * @param startIndex
     * @param endIndex
     * @param pivotIndex
     * @param comparator
     * @return
     * @param <T>
     */
    public static <T> int partition(ArrayList<T> arrayList, int startIndex, int endIndex, int pivotIndex, Comparator<? super T> comparator) {
        int left = startIndex;
        int right = endIndex - 1; //true endIndex holds the pivot
        //Get pivot value
        var pivotValue = arrayList.get(pivotIndex);

        //Swap the pivot to the end of the array
        swap(arrayList, endIndex, pivotIndex);

        //Go until left and right meet on same index
        while (left <= right) {
            //check if left is smaller than pivot
            while (comparator.compare(arrayList.get(left), pivotValue) < 0) {
                left++; //keep moving towards pivot ([ L->   arr    ])
            }
            //check if right is bigger than pivot
            while (right >= startIndex && comparator.compare(arrayList.get(right), pivotValue) >= 0) {
                right--; //keep moving towards pivot ([   arr    <-R])
            }
            //if left is smaller than right swap
            if (left < right) {
                swap(arrayList, left, right);
            }
        }
        //Swap pivot to its rightful index
            //swap left (actually a big value) with the endIndex (which contains the pivot)
        swap(arrayList, left, endIndex);
        return left ;
    }


    /**
     *HELPER SWAP
     *  swaps elements in the array
     * @param arrayList
     * @param a
     * @param b
     * @param <T>
     */
    public static <T> void  swap(ArrayList<T> arrayList, int a, int b){
        T temp = arrayList.get(a);
        arrayList.set(a,arrayList.get(b));
        arrayList.set(b,temp);

    }

    /**
     *GENERATE BEST CASE
     *    This method generates and returns an ArrayList of integers 1 to size in ascending order.
     * @param size
     * @return
     */
    public static ArrayList<Integer> generateBestCase(int size){
        ArrayList<Integer> bestCaseArray = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            bestCaseArray.add(i);
        }
        return bestCaseArray;
    }

    /**
     * GENERATE AVERAGE CASE
     *  This method generates and returns an ArrayList of integers 1 to size in permuted order
     *      (i,e., randomly ordered).
     * @param size number of elements in array
     * @return
     */
    public static ArrayList<Integer> generateAverageCase(int size){
        Random rand = new Random();
        ArrayList<Integer> avgCaseArray = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            avgCaseArray.add(i);
        }
        for (int i = 0; i < size; i++) {
            int j = rand.nextInt(size);
            int temp = avgCaseArray.get(i);
            avgCaseArray.set(i, avgCaseArray.get(j));
            avgCaseArray.set(j, temp);
        }
        return avgCaseArray;
    }
    /**
     *GENERATE WORST CASE
     *  This method generates and returns an ArrayList of integers 1 to size in descending order.
     * @param size
     * @return
     */
    public static ArrayList<Integer> generateWorstCase(int size){
        ArrayList<Integer> worstCaseArray = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            worstCaseArray.add(i);
        }
        Collections.sort(worstCaseArray, Collections.reverseOrder());
        return worstCaseArray;

    }


}