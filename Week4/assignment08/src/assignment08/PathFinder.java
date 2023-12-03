package assignment08;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class PathFinder {
    static char[][] maze_;
    static Graph graph;

    /**
     * Reads in a file, creates a 2D array maze, creates a graph from the maze, if a path exists, set the path
     * Write file out with path
     * @param inputFile file name to read to
     * @param outputFile file name to write to
     */
    public static void solveMaze(String inputFile, String outputFile) {
        //extract Maze from file
        maze_ = readInFile(inputFile);
        //pass maze into graph constructor
        graph = new Graph(maze_,'S','G');
        boolean pathExist = graph.breathFirstSearch();
        //if path exist, set the path inside the graph and write the file out
        if(pathExist) {
            graph.setPath();
            writeToFile(outputFile);
        }
        //else if the file doesn't exist, write out the origional file
        else if (!pathExist) {
            writeOriginalToFile(outputFile);
        }
    }

    /**
     * Function to read in the file and create a 2D array representation of maze
     * @param inputFile file to open and read
     * @return char[][] representation of the graph
     */
    public static char[][]  readInFile(String inputFile) {
        char[][] maze;
        try {
            File file = new File(inputFile);
            Scanner scanner = new Scanner(file);

            // Extract the dimensions
            String dimensionsLine = scanner.nextLine();
            String[] dimensions = dimensionsLine.split(" ");
            int height = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);

            // Create the maze layout
            maze = new char[height][width];
            for (int i = 0; i < height; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < width; j++) {
                    maze[i][j] = line.charAt(j);
                }
            }
            // Close the scanner
            scanner.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return maze;

    }

    /**
     * Function to write out solved maze
     * @param outputFile filename to write to
     */
    private static void writeToFile(String outputFile) {
        //Create file to write to
        try (PrintWriter output = new PrintWriter(new FileWriter(outputFile))) {
            int height = maze_.length;
            int width = maze_[0].length;
            //write graph dimensions
            output.println(height + " " + width);

            //Loop through the inputted maze
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    //if the graph node (in same location as maze elements) has a period, print out a period
                    if (graph.nodes[i][j].value == '.') {
                        output.print('.');
                    } else {
                        //else print out the contents of the maze element
                        output.print(maze_[i][j]);
                    }
                }
                output.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to write out un-solved maze
     * @param outputFile filename to write to
     */
    private static void writeOriginalToFile(String outputFile) {
        // If no path exists, copy the original maze to the output file
        try (Scanner scanner = new Scanner(new File(outputFile));
             PrintWriter output = new PrintWriter(new FileWriter(outputFile))) {

            while (scanner.hasNextLine()) {
                output.println(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



