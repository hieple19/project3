
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class PlayerFirstTest, also tests methods in Player class
 *
 * @author  Hiep Le
 * @version 05/04/2018
 */
public class PlayerFirstTest
{   
    /**
     * Method checks if deadEnd() works as intended
     */
    @Test
    public void deadEndTest(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        PlayerFirst player = new PlayerFirst(graph.getStartingNode(), graph);
        player.setCurrent(graph.getNode(3));
        assertFalse("Not deadend", player.deadEnd());

        player.addVisited(graph.getNode(1));        // Add visited node 1
        player.setCurrent(graph.getNode(5));        // 5 only neighbor is 1 
        assertTrue("Dead end", player.deadEnd());

        player.addVisited(graph.getNode(3));        // Add visited node
        player.setCurrent(graph.getNode(2));        // 2 has 6 and 3 as neighbors
        assertFalse("Not Dead end", player.deadEnd());  // 6 is not visited

        player.addVisited(graph.getNode(6));
        assertTrue("Dead end", player.deadEnd());   // 6 is visited -> dead end
    }

    /**
     * Method tests checkExitInRange()
     */
    @Test
    public void testCheckExitInRange(){
        // 3 is exit node
        Graph graph = new Graph(5,"test.txt", "testConfigSmall.txt");  
        PlayerFirst player = new PlayerFirst(graph.getStartingNode(), graph);
        assertTrue("2 to 3 within 5", player.checkExitInRange());

        player = new PlayerFirst(graph.getNode(3), graph);
        assertTrue("3 to 3 within 5", player.checkExitInRange());

        player = new PlayerFirst(graph.getNode(1), graph);
        assertFalse("1 to 3 out of 5", player.checkExitInRange());

        player = new PlayerFirst(graph.getNode(5), graph);
        assertFalse("5 to 3 out of 5", player.checkExitInRange());

        player.setCurrent(graph.getNode(4));
        assertTrue("4 to 3 within 5", player.checkExitInRange());
    }

    /**
     * Method checks pathToNearestExit()
     */
    @Test
    public void testPathToNearestExit(){
        // 3 and 8 are exit nodes
        Graph graph = new Graph(10, "testBig.txt", "testConfigBig.txt");
        PlayerFirst player = new PlayerFirst(graph.getNode(4), graph);
        Path nearestExit = player.pathToNearestExit();
        Path expected = graph.shortestPath(4,3);
        assertArrayEquals("4 to 3", nearestExit.route().toArray(), expected.route().toArray());

        graph = new Graph(15, "testBig.txt", "testConfigBig.txt");
        player =  new PlayerFirst(graph.getNode(4), graph);
        nearestExit = player.pathToNearestExit();
        expected = graph.shortestPath(4,3);
        assertArrayEquals("4 to 3", nearestExit.route().toArray(), expected.route().toArray());

        player =  new PlayerFirst(graph.getNode(9), graph);
        nearestExit = player.pathToNearestExit();
        expected = graph.shortestPath(9,8);
        assertArrayEquals("9 to 8", nearestExit.route().toArray(), expected.route().toArray());

        graph = new Graph(1, "testBig.txt", "testConfigBig.txt");
        player =  new PlayerFirst(graph.getNode(9), graph); // Limit is 1 so no exit node in range
        nearestExit = player.pathToNearestExit();
        expected= null;
        assertTrue("No path", nearestExit == expected);
    }

    /**
     * Method tests new path created when current is a dead end
     */
    @Test
    public void newPathDeadEnd(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        PlayerFirst player = new PlayerFirst(graph.getStartingNode(), graph);
        player.addVisited(graph.getNode(3));
        player.addVisited(graph.getNode(1));
        player.addVisited(graph.getNode(5));
        player.setCurrent(graph.getNode(5));
        assertTrue("Dead end", player.deadEnd()); // 5 is only connected to 1
        player.newPath();

        // 1 is already visited. 5 is a dead end. Expected path is heading back to 1
        Path expected = graph.shortestPath(5,1);
        assertArrayEquals("5 back to 1", expected.route().toArray(), player.currentPath.route().toArray());

        player.setCurrent(graph.getNode(1));
        player.newPath();
        // 1 is connected to 3 and 5 both visited. Expected path heading back to 3
        expected = graph.shortestPath(1,3);
        assertArrayEquals("1 back 3", expected.route().toArray(), player.currentPath.route().toArray());
    }

    /**
     * Method checks new path created when exit is in range
     */
    @Test
    public void newPathExit(){
        // Exit 9
        Graph graph = new Graph(10,"testBig.txt", "testConfig3.txt");  
        PlayerFirst player = new PlayerFirst(graph.getStartingNode(), graph);
        player.newPath();
        Path expected = graph.shortestPath(4,9);
        assertArrayEquals("4 to exit 9",expected.route().toArray(),player.currentPath.route().toArray());

        player.setCurrent(graph.getNode(5));
        player.newPath();
        expected = graph.shortestPath(5,9);
        assertArrayEquals("5 to 8 to 7 to 9",expected.route().toArray(), player.currentPath.route().toArray());

        player.setCurrent(graph.getNode(7));
        player.newPath();
        expected = graph.shortestPath(7,9);
        assertArrayEquals("7 to 9", expected.route().toArray(),player.currentPath.route().toArray());   
    }
    
    /**
     * Method checks new path created in normal conditions
     * Player should be heading to first neighbor in list
     */
    @Test
    public void newPathNormal(){
        Graph graph = new Graph(1,"testBig.txt", "testConfig3.txt");  
        PlayerFirst player = new PlayerFirst(graph.getStartingNode(), graph);
        player.newPath();
        Path expected = graph.shortestPath(4,3);
        assertArrayEquals("First neighbor 3", expected.route().toArray(),player.currentPath.route().toArray());

        player.traversePath(1);
        player.newPath();
        expected = graph.shortestPath(3,1);
        assertArrayEquals("First neighbor of 3 is 1", expected.route().toArray(), player.currentPath.route().toArray());

        player.traversePath(7);
        player.newPath();
        expected = graph.shortestPath(1,5);
        assertArrayEquals("First neighbor of 1 is 5", expected.route().toArray(),player.currentPath.route().toArray());

    }

    /**
     * Method tests players' traversal of a path
     */
    @Test
    public void testTraverseTest(){
        Graph graph = new Graph(10,"testBig.txt", "testConfig3.txt");  
        PlayerFirst player = new PlayerFirst(graph.getStartingNode(), graph);
        player.newPath();
        Path expected = graph.shortestPath(4,9); // Length 9. 4 to 6 to 9

        player.traversePath(1); // 4 to 6 is 2. 1 step left to next node (6)
        assertTrue("Length left 8", 8 == player.currentPath.stepsLeft());
        assertEquals("Checking position", graph.getNode(4), player.current);
        assertTrue("Distance to next node 5", 1 == player.currentPath.distanceToNextNode());

        player.traversePath(3); // 4 to 6 done. 6 to 9 is 7. 5 steps left to 9
        
        assertTrue("Length left 5", 5 == player.currentPath.stepsLeft());
        assertEquals("Checking position", graph.getNode(6), player.current);
        assertTrue("Distance to next node 5", 5 == player.currentPath.distanceToNextNode());
        
        // Check expected visited route 
        Node[] expectedRoute = {graph.getNode(4),graph.getNode(6)};
        assertEquals("Expected visited", expectedRoute, player.visited.toArray());
        assertFalse("Path not done", player.currentPath.done());

        player.traversePath(10); // 6 to 9 is done. Path done. 5 extra steps
        assertTrue("Length left 0", 0 == player.currentPath.stepsLeft());
        assertEquals("Checking position", graph.getNode(9), player.current);
        
        // Check expected visted route
        Node[] expectedRoute2 = {graph.getNode(4),graph.getNode(6),graph.getNode(9)};
        assertEquals("Expected visited", expectedRoute2, player.visited.toArray());
        assertTrue("Path done", player.currentPath.done());
        assertTrue("Extra steps", 5 == player.extraSteps);

        graph = new Graph(10,"testBig.txt", "testConfig3.txt");  
        player = new PlayerFirst(graph.getStartingNode(), graph);
        player.newPath();
        player.traversePath(10); // Starting at 4, move through 6 and 9 - length 9. 1 step left
        assertTrue("Length left 0", 0 == player.currentPath.stepsLeft());
        assertEquals("Checking position", graph.getNode(9), player.current);
        Node[] expectedRoute3 = {graph.getNode(4),graph.getNode(6),graph.getNode(9)};
        assertEquals("Expected visited", expectedRoute3, player.visited.toArray());
        assertTrue("Path done", player.currentPath.done());
        assertTrue("Extra steps", 1 == player.extraSteps);
    }


}