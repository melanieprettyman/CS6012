package assignment04;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static assignment04.SortUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class SortUtilTest {
    ArrayList<Integer> smallList = new ArrayList<>();
    ArrayList<Integer> expectedSL = new ArrayList<>();

    ArrayList<Integer> partitionSmallList = new ArrayList<>();

    ArrayList<Integer> oneElement = new ArrayList<>();

    ArrayList<Integer> twoElement = new ArrayList<>();
    Comparator<Integer> naturalOrderComparator = Comparator.naturalOrder();

    ArrayList<Integer> reverseOrder = new ArrayList<>();



    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        // Create a small list of integers (will use insertion sort)
        smallList.add(5);
        smallList.add(2);
        smallList.add(8);
        smallList.add(1);
        smallList.add(9);

        // Create an expected sorted smallList
        expectedSL.add(1);
        expectedSL.add(2);
        expectedSL.add(5);
        expectedSL.add(8);
        expectedSL.add(9);

        //sorted odd list 1-5
        partitionSmallList.add(1);
        partitionSmallList.add(2);
        partitionSmallList.add(3);
        partitionSmallList.add(4);
        partitionSmallList.add(5);

        oneElement.add(1);

        twoElement.add(5);
        twoElement.add(4);

        reverseOrder.add(5);
        reverseOrder.add(4);
        reverseOrder.add(3);
        reverseOrder.add(2);
        reverseOrder.add(1);





    }


@org.junit.jupiter.api.Test
    void testMerge() {
        // Test when the left element is smaller
        testMergeHelper(1, 2, 3, 4, 5, 6);

        // Test when the left and right elements are equal
        testMergeHelper(1, 1, 1, 1, 1, 1);
    }

    private void testMergeHelper(Integer... elements) {
        ArrayList<Integer> array = new ArrayList<>();
        for (Integer element : elements) {
            array.add(element);
        }

        // Set up indices for the merge method
        int start = 0;
        int middle = (array.size() - 1) / 2;
        int end = array.size() - 1;

        SortUtil.merge(array, start, middle, end, naturalOrderComparator);
        System.out.println(array);

        // Verify that the array is sorted after the merge operation
        for (int i = 0; i < array.size() - 1; i++) {
            Assertions.assertTrue(naturalOrderComparator.compare(array.get(i), array.get(i + 1)) <= 0);
        }
    }


    @org.junit.jupiter.api.Test
    void mergesort() {
        //CASE; SMALL LIST (USE INSERTION-SORT)
        // Sort the list using mergesort
        SortUtil.mergesort(smallList, naturalOrderComparator);

        // Assert that the sorted list is equal to the expected list
        Assertions.assertEquals(expectedSL, smallList);

        //CASE: LARGE (AVG CASE) LIST
        ArrayList<Integer> myList = SortUtil.generateAverageCase(20);

        SortUtil.mergesort(myList, naturalOrderComparator);

        // Assert that the list is sorted
        for (int i = 0; i < myList.size() - 1; i++) {
            assertTrue(naturalOrderComparator.compare(myList.get(i), myList.get(i + 1)) <= 0);
        }
        //CASE: reverse order array
        // Test when the right element is smaller
        SortUtil.mergesort(reverseOrder, naturalOrderComparator);
        assertEquals(partitionSmallList, reverseOrder);

    }

    @org.junit.jupiter.api.Test
    void merge() {

    }

    @org.junit.jupiter.api.Test
    void quickSort() {
        //CASE: UNSORTED ODD LIST
        quicksort(smallList, naturalOrderComparator);
        Assertions.assertEquals(expectedSL, smallList);

        //CASE: 1 ELEMENT
        quicksort(oneElement, naturalOrderComparator);
        assertEquals(1, oneElement.get(0));

        //CASE: 2 ELEMENT
        quicksort(twoElement, naturalOrderComparator);
        assertEquals(4, twoElement.get(0));
        assertEquals(5, twoElement.get(1));

        //CASE; ALL ELEMS EQUAL
        //Arraylist of 10 1's
        for(int i =1; i <10; i++) {
            oneElement.add(1);
        }
        quicksort(oneElement, naturalOrderComparator);
        assertEquals(1, oneElement.getLast());
        assertEquals(1, oneElement.getFirst());
        assertEquals(1, oneElement.get(5));

        //CASE: REVERSE ORDER ARRAY
        // Test when the right element is smaller
        SortUtil.quicksort(reverseOrder, naturalOrderComparator);
        assertEquals(partitionSmallList, reverseOrder);




    }

    @org.junit.jupiter.api.Test
    void testPartition(){
        //CASE: SORTED ODD
        int sortedPartition = partition(partitionSmallList,0, 4, 2, naturalOrderComparator);
        assertEquals(2, sortedPartition);
        //----------------------------

        //CASE: UN-SORTED ODD
        int unsortedPartition = partition(smallList,0, 4, 2, naturalOrderComparator);
        assertEquals(3, unsortedPartition);
        //----------------------------

        //CASE: UN-SORTED EVEN
        smallList.remove(4);
        int evenPartition = partition(smallList,0, 3, 2, naturalOrderComparator);
        assertEquals(0, evenPartition);
        //----------------------------

        //CASE: DUPLICATE OF PIVOT
        ArrayList<Integer> dupList = new ArrayList<>(List.of(3, 1, 2, 3, 5, 4));
        int pivotIndex = 3; // Pivot is 3

        int pivotPosition = SortUtil.partition(dupList, 0, dupList.size()-1, pivotIndex, naturalOrderComparator);

        // Verify that elements are correctly partitioned
        for (int i = 0; i < pivotPosition; i++) {
            assertTrue(dupList.get(i) <= dupList.get(pivotPosition));
        }
        for (int i = pivotPosition + 1; i < dupList.size(); i++) {
            assertTrue(dupList.get(i) >= dupList.get(pivotPosition));
        }
        //----------------------------

        //CASE: PIVOT IS FIRST ELEMENT
        ArrayList<Integer> firstElemList = new ArrayList<>(List.of(4, 2, 6, 1, 3, 5));
        int pivotIndex_FE = 0; // Pivot is 4

        int pivotPosition_FE = SortUtil.partition(firstElemList, 0, firstElemList.size()-1, pivotIndex_FE, naturalOrderComparator);
        System.out.println(firstElemList);
        // Verify that elements are correctly partitioned
        for (int i = 0; i < pivotPosition_FE; i++) {
            assertTrue(firstElemList.get(i) < firstElemList.get(pivotPosition_FE));
        }
        for (int i = pivotPosition_FE + 1; i < firstElemList.size(); i++) {
            assertTrue(firstElemList.get(i) >= firstElemList.get(pivotPosition_FE));
        }
        //----------------------------

        //CASE: PIVOT IS LAST ELEMENT
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9, 4));
        int pivotIndex_LE = arrayList.size() - 1; // Pivot is the last element

        int pivot = partition(arrayList, 0, arrayList.size() - 1, pivotIndex_LE, Comparator.naturalOrder());

        // Verify that elements are correctly partitioned
        for (int i = 0; i < pivot; i++) {
            assertTrue(arrayList.get(i) < arrayList.get(pivot));
        }
        for (int i = pivot + 1; i < arrayList.size(); i++) {
            assertTrue(arrayList.get(i) >= arrayList.get(pivot));
        }

    }



}