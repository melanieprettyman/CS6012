package lab01;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DiffUtilTest {
    private int[] arr1, arr2, arr3, arr4, arr5, arr6, arr7;


    // happens before every test method.
    @org.junit.jupiter.api.BeforeEach
    protected void setUp() {
        arr1 = new int[0];
        arr2 = new int[] { 3, 3, 3 };
        arr3 = new int[] { 52, 4, -8, 0, -17 };
        arr4 = new int[100];
        Arrays.fill(arr4, 1);
        arr5 = new int[]{1};
        arr6 = new int[]{-52, -6, -12, -10, -80};
        arr7 = new int[]{100, 80, 24, 15, 3, 1};


    }
    // happens right after every Test.
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void findSmallestDiff() {

    }
    @org.junit.jupiter.api.Test
    public void emptyArray() {
        assertEquals(-1, DiffUtil.findSmallestDiff(arr1));
    }
    @org.junit.jupiter.api.Test
    public void allArrayElementsEqual() {
        assertEquals(0, DiffUtil.findSmallestDiff(arr2));
    }
    @org.junit.jupiter.api.Test
    public void smallRandomArrayElements() {
        assertEquals(4, DiffUtil.findSmallestDiff(arr3));
    }
    //Test large array of size 100
    @org.junit.jupiter.api.Test
    public void largeArrayElements() {
        assertEquals(0, DiffUtil.findSmallestDiff(arr4));
    }
    //Test array size of 1
    @org.junit.jupiter.api.Test
    public void oneValueArrayElements() {
        assertEquals(-1, DiffUtil.findSmallestDiff(arr5));
    }
    //Test array of only negative values
    @org.junit.jupiter.api.Test
    public void allNegativeArrayElements() {
        assertEquals(2, DiffUtil.findSmallestDiff(arr6));
    }
    //Test array for repeat values
    @org.junit.jupiter.api.Test
    public void repeatValueArrayElements() {
        arr3[3]= -17;
        assertEquals(0, DiffUtil.findSmallestDiff(arr3));
    }
    //Test descending array
    @org.junit.jupiter.api.Test
    public void descendingArrayElements() {
        assertEquals(2, DiffUtil.findSmallestDiff(arr7));
    }

}