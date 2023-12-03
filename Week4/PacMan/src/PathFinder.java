package assignment08;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class PathFinder {
    static char[][] maze_;
    static List<Graph.Node> path;
    static Graph graph;

    public static void solveMaze(String inputFile, String outputFile) {
        //extract Maze from file
        maze_ = readInFile(inputFile);
         graph = new Graph(maze_,'S','G');
        boolean pathExist = graph.breathFirstSearch();
        if(pathExist) {
            graph.setPath();
            writeToFile(outputFile);

        }
    }
    public static char[][]  readInFile(String inputFile) {
        char[][] maze;
        try {
            File file = new File(inputFile);
            Scanner scanner = new Scanner(file);

            // Read the dimensions
            String dimensionsLine = scanner.nextLine();
            String[] dimensions = dimensionsLine.split(" ");
            int height = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);

            // Read the maze layout
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
    private static void writeToFile(String outputFile) {
        try (PrintWriter output = new PrintWriter(new FileWriter(outputFile))) {
            int height = maze_.length;
            int width = maze_[0].length;
            output.println(height + " " + width);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (graph.nodes[i][j].value == '.') {
                        output.print('.');
                    } else {
                        output.print(maze_[i][j]);
                    }
                }
                output.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeOriginalToFile(String inputFile, String outputFile) {
        // If no path exists, simply copy the original maze to the output file
        try (Scanner scanner = new Scanner(new File(inputFile));
             PrintWriter output = new PrintWriter(new FileWriter(outputFile))) {

            while (scanner.hasNextLine()) {
                output.println(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



