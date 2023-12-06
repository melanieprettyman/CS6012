package assignment08;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    void testMazeWithNoPath() {
        char[][] maze = {
                {'X', 'X', 'X'},
                {'X', 'S', 'X'},
                {'X', 'X', 'X'}
        };
        Graph graph = new Graph(maze, 'S', 'G');
        assertFalse(graph.breathFirstSearch());
    }

    @Test
    void testMazeWithValidPath() {
        char[][] maze = {
                {'X', 'X', 'X', 'X', 'X'},
                {'X', 'S', ' ', ' ', 'X'},
                {'X', ' ', 'X', ' ', 'X'},
                {'X', ' ', 'X', 'G', 'X'},
                {'X', 'X', 'X', 'X', 'X'}
        };
        Graph graph = new Graph(maze, 'S', 'G');
        assertTrue(graph.breathFirstSearch());
        graph.setPath();
        // Check if path is correctly set
        assertEquals('S', graph.start.value);
        assertEquals('G', graph.end.value);
        assertEquals('.', graph.nodes[1][2].value);
    }

    @Test
    void testMazeWithPathThroughWalls() {
        char[][] maze = {
                {'X', 'X', 'X', 'X', 'X'},
                {'X', 'S', 'X', ' ', 'X'},
                {'X', ' ', 'X', ' ', 'X'},
                {'X', ' ', 'X', 'G', 'X'},
                {'X', 'X', 'X', 'X', 'X'}
        };
        Graph graph = new Graph(maze, 'S', 'G');
        assertFalse(graph.breathFirstSearch());
    }

}