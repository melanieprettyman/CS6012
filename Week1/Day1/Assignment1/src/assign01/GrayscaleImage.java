package assign01;


import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;


/**
 * Represents a grayscale (black and white) image as a 2D array of "pixel" brightnesses
 * 255 is "white" 127 is "gray" 0 is "black" with intermediate values in between
 * Author: Ben Jones and Melanie Prettyman
 */
public class GrayscaleImage {
    //Melanie: stores the actual pixel values of the image.
    private double[][] imageData; // the actual image data

    //Melanie
    private double pixel; //for getPixel

    /**
     * Initialize an image from a 2D array of doubles
     * This constructor creates a copy of the input array
     * @param data initial pixel values
     * @throws IllegalArgumentException if the input array is empty or "jagged" meaning not all rows are the same length
     */
    public GrayscaleImage(double[][] data){
        if(data.length == 0 || data[0].length == 0){
            throw new IllegalArgumentException("Image is empty");
        }
        imageData = new double[data.length][data[0].length];
        for(var row = 0; row < imageData.length; row++){
            if(data[row].length != imageData[row].length){
                throw new IllegalArgumentException("All rows must have the same length");
            }
            for(var col = 0; col < imageData[row].length; col++){
                imageData[row][col] = data[row][col];
            }
        }
    }

    /**
     * Fetches an image from the specified URL and converts it to grayscale
     * Uses the AWT Graphics2D class to do the conversion, so it may add
     * an item to your dock/menu bar as if you're loading a GUI program
     * @param url where to download the image
     * @throws IOException if the image can't be downloaded for some reason
     */
    public GrayscaleImage(URL url) throws IOException {
        var inputImage = ImageIO.read(url);
        //convert input image to grayscale
        //based on (https://stackoverflow.com/questions/6881578/how-to-convert-between-color-models)
        var grayImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d= grayImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, null);
        g2d.dispose();
        imageData = new double[grayImage.getHeight()][grayImage.getWidth()];

        //raster is basically a width x height x 1 3-dimensional array
        var grayRaster = grayImage.getRaster();
        for(var row = 0; row < imageData.length; row++){
            for(var col = 0; col < imageData[0].length; col++){
                //getSample parameters are x (our column) and y (our row), so they're "backwards"
                imageData[row][col] = grayRaster.getSampleDouble(col, row, 0);
            }
        }
    }

    public void savePNG(File filename) throws IOException {
        var outputImage = new BufferedImage(imageData[0].length, imageData.length, BufferedImage.TYPE_BYTE_GRAY);
        var raster = outputImage.getRaster();
        for(var row = 0; row < imageData.length; row++){
            for(var col = 0; col < imageData[0].length; col++){
                raster.setSample(col, row, 0, imageData[row][col]);
            }
        }
        ImageIO.write(outputImage, "png", filename);
    }

    ///Methods to be filled in by students below

   //GET-PIXEL; retrieve value at specific x,y coordinate
    public double getPixel(int x, int y){
        //if x or y is less than zero or greater than image size, throw exception
        if (x < 0 || x >= imageData[0].length || y < 0 || y >= imageData.length) {
            throw new IllegalArgumentException("Coordinates (x, y) are not within the image width/height.");
        }
        //2D array goes to value at row, column
        //but getPixel need the reverse order, going to the column then the row
        pixel = imageData[y][x];
        return pixel;
    }



    @Override
    public boolean equals(Object other) {

        // Check if the compared object is null or not an instance of GrayscaleImage
        if (other == null || !(other instanceof GrayscaleImage)) {
            return false;
        }

        // Cast the compared object as a GrayscaleImage
        GrayscaleImage otherImage = (GrayscaleImage) other;

        // Check if the dimensions of the images are not the same
        if (this.imageData.length != otherImage.imageData.length || this.imageData[0].length != otherImage.imageData[0].length) {
            return false;
        }

        // Check the pixel values of both images
        for (var row = 0; row < imageData.length; row++) {
            for (var col = 0; col < imageData[0].length; col++) {
                // Compare the corresponding pixels of both images
                if (getPixel(row, col) != otherImage.getPixel(col, row)) {
                    return false;
                }
            }
        }
        // The images are equal
        return true;
    }


    public double averageBrightness() {
        //Declare variables
        double totalValues = 0;
        double pixelCounter = 0;
        double average = 0;

        //Loop over the rows and columns in the image
        for (var row = 0; row < this.imageData.length; row++) {
            for (var col = 0; col < this.imageData[row].length; col++) {
                //get the pixel
                double pixel = this.getPixel(col, row);
                //Add value of pixel to total
                totalValues += pixel;
                //track the amount of pixels in image
                pixelCounter++;
            }
        }
        //Average is equal to total divided by amount of data
        average = totalValues / pixelCounter;
        return average;
    }

    public GrayscaleImage normalized(){

        double brightness = this.averageBrightness();
        double scale = 127/brightness;

        //Create a new image, set to the same size as OG image
        double[][] picSize = new double[this.imageData.length][this.imageData[0].length];
        GrayscaleImage normalizedImage = new GrayscaleImage(picSize);

        //Loop over the rows and columns in the image
        for (var row = 0; row < this.imageData.length; row++) {
            for (var col = 0; col < this.imageData[row].length; col++) {
                //get the pixel in the image
                double pixel = this.getPixel(col, row);
                //multiply the pixel by the scale
                double normalizedValue = pixel * scale;
                //update the 2D array with new pixel value
                normalizedImage.imageData[row][col] = normalizedValue;
            }
        }
        //return the new image
        return normalizedImage;
    }


    public GrayscaleImage mirrored(){
        double[][] picSize = new double[this.imageData.length][this.imageData[0].length];

        //Loop over the rows and columns in the image
        for (var row = 0; row < this.imageData.length; row++) {
            for (var col = 0; col < this.imageData[row].length; col++) {
                //set mirrorImage pixel to the reversed order by getting the total number of columns in the original image
                //and subtracting with col, takes the column from the last index and moving towards the beginning.
                picSize[row][col] = (this.imageData[row][imageData[0].length - 1 - col]);
            }
        }
        GrayscaleImage mirrorImage = new GrayscaleImage(picSize);

        return mirrorImage;
    }


    public GrayscaleImage cropped(int startRow, int startCol, int width, int height) throws IllegalArgumentException{
        // Check if width or height is less than or equal to zero
        if (startCol<0 || startRow<0 || width < 0 || height < 0 || startRow + height > this.imageData.length || startCol + width > this.imageData[0].length) {
            throw new IllegalArgumentException("Cropping goes outside the bounds of the original image.");
        }

        // Create the picSize array after verifying width and height
        double[][] picSize = new double[width][height];
        GrayscaleImage croppedImage = new GrayscaleImage(picSize);

        //Loop over the rows and columns in the OG image
        for (var row = 0; row < height; row++) {
            for (var col = 0; col < width; col++) {
                // 1st time; get the pixel is startRow and startCol
                //each time after: get the pixel at startRow + row...etc
                double pixel = this.getPixel(startRow + row, startCol+col);
                //Set pixel in cropped image to the OG pixel
                croppedImage.imageData[col][row] = pixel;
            }
        }
        return croppedImage;
    }

    public GrayscaleImage squarified() {
        int numRows = this.imageData.length;
        int numCols = this.imageData[0].length;

        // If the image is already square, return the original image
        if (numRows == numCols) {
            return this;
        } else {
            // Calculate the size of the smaller side (minimum value between the number of rows and columns)
            int minSide = Math.min(numCols, numRows);

            // Create a new square image of size 'minSide x minSide'
            double[][] picSize = new double[minSide][minSide];
            GrayscaleImage squarifiedImage = new GrayscaleImage(picSize);

            // Calculate the number of rows and columns to remove from the original image
            int removeRows = (numRows - minSide) / 2;
            int removeColumn = (numCols - minSide) / 2;

            // Loop over the rows and columns of the new square image
            for (var row = 0; row < minSide; row++) {
                for (var col = 0; col < minSide; col++) {
                    // Copy the pixels from the original image, starting from the adjusted rows and columns
                    squarifiedImage.imageData[row][col] = this.imageData[row + removeRows][col + removeColumn];
                }
            }
            return squarifiedImage;
        }
    }

    public int getNumRows(){
        return this.imageData.length;

    }
    public int getNumCol(){
        return this.imageData[0].length;

    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : imageData) {
            for (double pixel : row) {
                sb.append(pixel).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
