
# Pathfinding for Pacman

This project addresses the task of creating a tool for Pacman to solve mazes. The tool is designed to find the shortest path from a start point to an end point in any enclosed field of obstacles. It processes input data represented as a text file, determines the optimal path, and outputs a modified text file highlighting the path found.





## Usage
### PathFinder Class

The `PathFinder` class provides a method `solveMaze(String inputFile, String outputFile)` for solving mazes.

#### Input File Format

The input files follow this structure:  

5 5  
XXXXX  
XS X  
X X  
X GX  
XXXXX  


- The first line contains the maze's height and width.
- Subsequent lines represent the layout of the field, where:
  - `X` represents a wall that Pacman cannot traverse.
  - `S` denotes the starting point.
  - `G` marks the end point.
  - Spaces represent open paths for Pacman to navigate.

#### Output File Format

The output file retains the maze dimensions and layout, with the shortest path indicated by dots (`.`) replacing some open spaces (` `).

### Example Usage

``` PathFinder.solveMaze("input.txt", "output.txt");```

```PathFinder.solveMaze("input.txt", "output.txt");```

This method reads the maze from input.txt, solves it, and outputs the solution to output.txt.

## Implementation 
- **PathFinder Class:** Provides the core functionality to read, solve, and write maze solutions.
- **Graph Class:** Constructs a graph representation of the maze, allowing for efficient pathfinding algorithms.
- **Node Class:** Represents each node in the graph, aiding in establishing connections and pathfinding.
- **Breadth-First Search (BFS):** Utilized to find the shortest path from the start to the end point.





## API Reference

### Class: PathFinder

##### **solveMaze(String inputFile, String outputFile)**

| Parameter  | Type     | Description                              |
| ---------- | -------- | ---------------------------------------- |
| `inputFile`| `String` | The name of the input file containing the maze. |
| `outputFile`| `String`| The name of the output file for the solved maze. |

### Class: Graph

#### Variables

| Variable | Type       | Description                               |
| -------- | ---------- | ----------------------------------------- |
| `start`  | `Node`     | Represents the starting node of the graph. |
| `end`    | `Node`     | Represents the ending node of the graph.   |
| `nodes`  | `Node[][]` | 2D array of nodes representing the graph.  |

#### Methods

##### **Graph(char[][] maze, char startValue, char endValue)**

Constructor function for the Graph class.

| Parameter     | Type       | Description                                                   |
| ------------- | ---------- | ------------------------------------------------------------- |
| `maze`        | `char[][]` | The maze represented as a 2D array of characters.              |
| `startValue`  | `char`     | Character indicating the starting point in the maze.            |
| `endValue`    | `char`     | Character indicating the ending point in the maze.              |

##### **boolean breathFirstSearch()**

Performs a breadth-first search to find a path from the start to the end node.

| Returns | Type      | Description                                      |
| ------- | --------- | ------------------------------------------------ |
| `true`  | `boolean` | Indicates whether a path exists in the graph.     |

##### **void setPath()**

Sets the path from the end node to the start node by marking the shortest path with dots ('.'). 

| Returns | Type   | Description                                       |
| ------- | ------ | ------------------------------------------------- |
|         |        | Sets the path in the graph represented by dots.    |

## Acknowledgements

The Pathfinding project was created by Melanie Prettyman and was developed as part of CS 6012 Algorithms. 

We also appreciate the guidance and support provided by Professor Ben Jones throughout the project.

