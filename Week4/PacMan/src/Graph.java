package assignment08;

import java.util.*;

public class Graph {
    Node start;
    Node end;
    Node[][] nodes;
    class Node{
        char value;
        boolean visited;
        Node cameFrom;
        List<Node> neighbors;
        public Node(char value){
            this.value = value;
            this.visited = false;
            this.neighbors = new ArrayList<>();
        }
    }

    Graph(char[][] maze, char startValue, char endValue){
        int rows = maze.length;
        int cols = maze[0].length;

        // Create nodes for each cell in the maze
        nodes = new Node[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                    //set the value of the node equal to it's value in the maze
                    nodes[i][j] = new Node(maze[i][j]);
                    //Extract the start and end node
                    if (nodes[i][j].value == startValue) {
                        start = nodes[i][j];
                    } else if (nodes[i][j].value == endValue) {
                        end = nodes[i][j];
                }

            }
        }
        // Connect neighboring nodes
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
            for(Node neighbor:current.neighbors){
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

    public void setPath() {
        Node current = end;
        while (current != null) {
            if (current.cameFrom != null && current.cameFrom != start) {
                current = current.cameFrom;
                current.value = '.';
            } else {
                break;
            }
        }
    }


}
