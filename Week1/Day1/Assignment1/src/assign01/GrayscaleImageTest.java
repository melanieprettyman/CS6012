package assign01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrayscaleImageTest {

    private GrayscaleImage smallSquare;
    private GrayscaleImage smallWide;
    private GrayscaleImage largeImage;
    private GrayscaleImage allWhiteImage;
    private GrayscaleImage microImage;




    @BeforeEach
    void setUp() {
        microImage = new GrayscaleImage(new double[][]{{1}});
        smallSquare = new GrayscaleImage(new double[][]{{1,2},{3,4}});
        smallWide = new GrayscaleImage(new double[][]{{1,2,3},{4,5,6}});
        largeImage = new GrayscaleImage( new double[][]{{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}});
        allWhiteImage = new GrayscaleImage( new double[][] {{255,255,255}, {255,255,255}});
    }

    @Test
    void testGetPixel(){
        assertEquals(smallSquare.getPixel(0,0), 1);
        assertEquals(smallSquare.getPixel(1,0), 2);
        assertEquals(smallSquare.getPixel(0,1), 3);
        assertEquals(smallSquare.getPixel(1,1), 4);

    }
    //Note: If a method is marked as possibly throwing an exception, you should add a test verifying that it does when appropriate!
    @Test
    void testEquals() {
        assertEquals(smallSquare, smallSquare);
        var equivalent = new GrayscaleImage(new double[][]{{1,2},{3,4}});
        assertEquals(smallSquare, equivalent);
    }


    @Test
    void averageBrightness() {
        assertEquals(smallSquare.averageBrightness(), 2.5, 2.5*.001);
        var bigZero = new GrayscaleImage(new double[1000][1000]);
        assertEquals(bigZero.averageBrightness(), 0);
    }

    @Test
    void normalized() {
        var smallNorm = smallSquare.normalized();
        assertEquals(smallNorm.averageBrightness(), 127, 127*.001);
        var scale = 127/2.5;
        var expectedNorm = new GrayscaleImage(new double[][]{{scale, 2*scale},{3*scale, 4*scale}});
        for(var row = 0; row < 2; row++){
            for(var col = 0; col < 2; col++){
                assertEquals(smallNorm.getPixel(col, row), expectedNorm.getPixel(col, row),
                        expectedNorm.getPixel(col, row)*.001,
                        "pixel at row: " + row + " col: " + col + " incorrect");
            }
        }
    }

    @Test
    void mirrored() {
        var expected = new GrayscaleImage(new double[][]{{2,1},{4,3}});
        assertEquals(smallSquare.mirrored(), expected);
    }

    @Test
    void cropped() {
        var cropped = smallSquare.cropped(1,1,1,1);
        assertEquals(cropped, new GrayscaleImage(new double[][]{{4}}));
    }


    @Test
    void squarified() {
        var squared = smallWide.squarified();
        var expected = new GrayscaleImage(new double[][]{{1,2},{4,5}});
        assertEquals(squared, expected);
    }

    @Test
    void testGetPixelThrowsOnNegativeX(){
        assertThrows(IllegalArgumentException.class, () -> { smallSquare.getPixel(-1,0);});
    }


    //GET-PIXEl
    //test that the method behaves as expected on the boundaries of the image
    @Test
    void testBoundaryCases() {
        GrayscaleImage image = new GrayscaleImage(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
        });

        // Test for pixels at the boundaries of the image
        assertEquals(1.0, image.getPixel(0, 0)); // Top left corner
        assertEquals(3.0, image.getPixel(2, 0)); // Top right corner
    }

    //AVERAGE-BRIGHTNESS
    //Test averageBrightness on an all white image
    @Test
    void averageBrightnessWhiteImage() {
        assertEquals(allWhiteImage.averageBrightness(), 255);
    }

    //NORMALIZED
    @Test
    void testMicroImage() {
        // Test on very small image
        GrayscaleImage normalizedSmallImage = microImage.normalized();
        double normalizedSmallImageAverage = normalizedSmallImage.averageBrightness();
        // Assert for the correctness of handling very small images
        assertTrue(normalizedSmallImageAverage >= 0 && normalizedSmallImageAverage <= 127);

    }
    @Test
    void testLargeImage(){
        // Test on very large image
        GrayscaleImage veryLargeImage = largeImage.normalized();
        double normalizedLargeImageAverage = veryLargeImage.averageBrightness();
        // Assert for the correctness and performance of handling very large images
        assertTrue(normalizedLargeImageAverage >= 0 && normalizedLargeImageAverage <= 127);
    }


    //MIRRORED
    // image is mirrored again to verify if the original image is obtained
    @Test
    void testMirroredTwice() {
        // Mirror the original image
        GrayscaleImage mirroredImage = smallSquare.mirrored();

        // Mirror the mirrored image again
        GrayscaleImage twiceMirroredImage = mirroredImage.mirrored();

        // Verify if the twice-mirrored image matches the original image
        assertTrue(smallSquare.equals(twiceMirroredImage));
    }


    //CROPPED
    //test IllegalArgumentException for cropped
        @Test
    void testCroppedThrowsOnOutOfBounds(){
        assertThrows(IllegalArgumentException.class, () -> { smallWide.cropped(1,1, -1, 1);});
    }

    @Test
    void testValidCropWithinImageBounds() {
        // Create an image
        double[][] imageData = new double[][] {{1, 2, 3, 4}, {5, 6, 7, 8},};
        GrayscaleImage image = new GrayscaleImage(imageData);

        // Crop a section within the image bounds
        GrayscaleImage croppedImage = image.cropped(0, 0, 2, 2);

        // Define the expected cropped image
        GrayscaleImage expectedCroppedImage = new GrayscaleImage(new double[][] {{1, 2}, {5, 6}});

        // Verify if the actual cropped image matches the expected result
        assertTrue(expectedCroppedImage.equals(croppedImage));
    }



    //SQUARIFIED
    //Testing when the number of rows is equal to the number of columns, if it returns the original image without performing any operations.
    @Test
    void testSquarifiedForSquareImage() {
        // Call the squarified method
        GrayscaleImage squarifiedImage = smallSquare.squarified();
        // Assert that the output squarified image is the same as the original square image
        assertEquals(smallSquare, squarifiedImage);

    }

    //Non-Square Image Case, testing if when the number of rows doesn't equal the number of columns, it computes a minimum side length and creates a new squarified image.
    @Test
    void testSquarifiedForNonSquareImage() {
        // Create a non-square grayscale image (with numRows != numCols)
        int rows = 5; // Replace these values with your specific number of rows and columns
        int columns = 8;
        double[][] imageData = new double[rows][columns];
        GrayscaleImage nonSquareImage = new GrayscaleImage(imageData);

        // Call the squarified method
        GrayscaleImage squarifiedImage = nonSquareImage.squarified();

        // Calculate the minimum side expected
        int expectedSide = Math.min(rows, columns);

        // Assert that the output squarified image has a side length equal to the minimum side between rows and columns
        assertEquals(expectedSide, squarifiedImage.getNumRows());
        assertEquals(expectedSide, squarifiedImage.getNumCol());
    }


}
