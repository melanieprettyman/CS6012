package assignment08;

import java.util.*;

/**
 * Graph class create the graph and sets the path from start-> end
 */
public class Graph {
    Node start;
    Node end;
    Node[][] nodes; //2D array of nodes
    class Node{
        char value; //value at each node
        boolean visited;
        Node cameFrom;
        List<Node> neighbors;

        //CONSTRUCTOR
        public Node(char value){
            this.value = value;
            this.visited = false;
            this.neighbors = new ArrayList<>();
        }
    }

    //CONSTRUCTOR

    /**
     * GRAPH CONSTRUCTOR
     * Loop over maze and turn every element in the maze into a node
     * Assign neighbors to every node that is not an X (wall)
     * @param maze char[][]
     * @param startValue char value 'S'
     * @param endValue char value 'G'
     */
    Graph(char[][] maze, char startValue, char endValue){
        int rows = maze.length;
        int cols = maze[0].length;

        // Create nodes for each element in the maze
        nodes = new Node[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                    //set the value of the node equal to the value in the maze
                    nodes[i][j] = new Node(maze[i][j]);
                    //extract the start and end node
                    if (nodes[i][j].value == startValue) {
                        start = nodes[i][j];
                    } else if (nodes[i][j].value == endValue) {
                        end = nodes[i][j];
                }

            }
        }
        // Connect neighboring nodes that are not X
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (nodes[i][j] != null) {
                    //node above
                    if (i > 0 && nodes[i - 1][j].value != 'X') {
                        nodes[i][j].neighbors.add(nodes[i - 1][j]);
                    }
                    //node below
                    if (i < rows - 1 && nodes[i + 1][j].value != 'X') {
                        nodes[i][j].neighbors.add(nodes[i + 1][j]);
                    }
                    //node left
                    if (j > 0 && nodes[i][j - 1].value != 'X') {
                        nodes[i][j].neighbors.add(nodes[i][j - 1]);
                    }
                    //node right
                    if (j < cols - 1 && nodes[i][j + 1].value != 'X') {
                        nodes[i][j].neighbors.add(nodes[i][j + 1]);
                    }
                }
            }
        }

    }

    /**
     * BreathFirstSearch Function
     * Create a queue to store visited nodes. Starting from start node, visit neighboring nodes until
     * the end node is reached. Each time a node is added to the queue for exploration, update cameFrom node to be
     * the node it came from.
     *
     * @return true if a path exist
     */
    boolean breathFirstSearch(){
        //Mark start node as visited, enqueue it
        start.visited = true;
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);

        //While the queue is not empty, dequeue a node form queue
        while(!queue.isEmpty()){
            Node current = queue.poll();

            //Check if current node is the end node, path exist, end path
            if(current == end){
                return true;
            }

            //if current is not end, visit un-visted neighbors, visit them and marked them as visited
            for(Node neighbor: current.neighbors){
                if(!neighbor.visited){
                    neighbor.visited = true;
                    //Update cameFrom: neighbor node, cameFrom current node
                    neighbor.cameFrom = current;
                    queue.add(neighbor);
                }
            }
        }
        return false; //path doesn't exit
    }

    /**
     * Set Path
     * 'cameFrom' links a path from end -> start
     * set each value that cameFrom the end to a '.'
     */
    public void setPath() {
        //Starting from the end
        Node current = end;
        while (current != null) {
            //if the current node came-from another node and not the start node
            if (current.cameFrom != null && current.cameFrom != start) {
                //update current to be the node it came-from
                current = current.cameFrom;
                //set the value of node to '.'
                current.value = '.';
            } else {
                break;
            }
        }
    }


}
