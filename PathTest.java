
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class PathTest.
 *
 * @author  Hiep Le
 * @version 05/04/18
 */
public class PathTest{

    /**
     * Method tests the routeAfterNode() method
     */
    @Test
    public void testRouteAfterNode(){
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Path loop = new Path(node1,node1);  // Expected route is empty
        Node[] empty = {};
        assertArrayEquals("Path after node1", empty, loop.routeAfterNode(loop.current()).toArray());

        Path path1 = new Path(node1, node2);    // Expected route has node 1 and 2
        Node[]expected = {node2};
        assertArrayEquals("Path after node1", expected, path1.routeAfterNode(path1.current()).toArray());

        Node node3 = new Node(3);
        path1.addNode(node3);
        Node node5 = new Node(5);
        path1.addNode(node5);

        Node[]expected2 = {node3,node5,node2};  // Expected route has node 3 5 and 2
        assertArrayEquals("Path after node 1",expected2, path1.routeAfterNode(path1.current()).toArray());

        path1.setCurrent(node3);                
        Node[] expected3 = {node5,node2};       // expected route
        assertArrayEquals("Path after node 3", expected3, path1.routeAfterNode(path1.current()).toArray());

        Node node4 = new Node(4);
        path1.addNode(node4);
        path1.setCurrent(node3);
        Node[] expected4 = {node5,node4,node2}; // expected route
        assertArrayEquals("Path after node 2", expected4, path1.routeAfterNode(path1.current()).toArray());

    }

    /**
     * Method tests findLength()
     */
    @Test   
    public void testFindLength(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Node node1 = graph.getNode(1);
        Path circuit = new Path(node1,node1);       // Path 1 to 1 length 0 
        circuit.findLength();
        assertTrue("Circuit", 0 == circuit.length());

        Node node3 = graph.getNode(3);              // Edge 1 to 3 weight 7
        Path path1 = new Path(node1,node3);
        path1.findLength();
        assertTrue("Path 1", 7 == path1.length());

        Node node2 = graph.getNode(2);                  
        ArrayList<Node> route = new ArrayList<Node>();
        route.add(node1);
        route.add(node3);                           // 1 to 3 weight 7
        route.add(node2);                           // 3 to 2 weight 5

        path1.setRoute(route);
        path1.findLength();
        assertTrue("Path 1 to 3 to 2", 12 == path1.length());

        Node node5 = graph.getNode(5);
        route.add(0,node5);             // 5 to 1 weight 9
        path1.setRoute(route);
        path1.findLength();             // 7 + 5 + 21
        assertTrue("Path 5 to 1 to 3 to 2", 21 == path1.length());
    }      

    /**
     * Method tests if total length from starting node to next node is calculated correctly 
     */
    @Test
    public void testDistanceNext(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Node node1 = graph.getNode(1);
        Path circuit = new Path(node1,node1);
        assertTrue("Circuit", 0 == circuit.totalLengthToNextNode());    // Path is 1 to 1

        Path path1 = graph.shortestPath(1,3);
        assertTrue("1 to 3", 7 == path1.totalLengthToNextNode());  // Edge 1 to 3 weight 7 
        path1.updateDistanceNextNode();

        Path path2 = graph.shortestPath(4,2);       // Edge 4 to 3 weight 1
        assertTrue("4 to 3 to 2", 1 == path2.totalLengthToNextNode());  // Next node is 3. Current is 4

        path2.setCurrent(graph.getNode(3));
        // Next node is 2. current is 3. Edge 3 to 2 weight 5. Ans 5 + 1
        assertTrue("4 to 3 to 2", 6 == path2.totalLengthToNextNode());

        // Edge 5 to 1 weight 9 
        Path path3 = graph.shortestPath(5,6);
        assertTrue("5 to 1 to 3 to 6", 9 == path3.totalLengthToNextNode());

        // 1 to 3 is 7. 3 to 6 is 2. Ans 7 + 2 + 9
        path3.setCurrent(graph.getNode(3));
        assertTrue("5 to 1 to 3 to 6", 18 == path3.totalLengthToNextNode()); 
    }

    /**
     * Method tests if the distance to the previous node is calculated 
     * correctly
     */
    @Test
    public void testDistancePrev(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Node node1 = graph.getNode(1);
        Path circuit = new Path(node1,node1);
        assertTrue("Circuit", 0 == circuit.lengthToCurrentNode()); // Loop

        Path path1 = graph.shortestPath(1,3);
        assertTrue("1 to 3", 0 == path1.lengthToCurrentNode()); // 1 to 1 is 0 
        path1.setCurrent(graph.getNode(3));
        assertTrue("1 to 3", 7 == path1.lengthToCurrentNode()); // 3 to 1 is 7

        Path path2 = graph.shortestPath(4,2);

        path2.setCurrent(graph.getNode(3));
        assertTrue("4 to 3 to 2", 1 == path2.lengthToCurrentNode()); // 4 to 3 is 1

        Path path3 = graph.shortestPath(5,6);
        path3.setCurrent(graph.getNode(1));
        assertTrue("5 to 1 to 3 to 6", 9 == path3.lengthToCurrentNode()); // 5 to 1 is 9
        path3.setCurrent(graph.getNode(3));
        assertTrue("5 to 1 to 3 to 6", 16 == path3.lengthToCurrentNode()); // 1 to 3 is 7. 7 + 9 = 16    
    }

    /**
     * Method tests that the distance from current node to next
     * node on path is updated correctly
     */
    @Test
    public void testUpdateDistanceToNextNode(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Node node1 = graph.getNode(1);

        Path path1 = graph.shortestPath(1,3);
        path1.setStepsLeft(7);
        path1.updateDistanceNextNode();
        assertTrue("1 to 3", 7 == path1.distanceToNextNode());  // 1 to 3 is 7 with no steps

        path1.setLengthTravelled(5);            // Travel 5 steps
        path1.updateDistanceNextNode();
        path1.print();
        assertTrue("1 to 3", 2 == path1.distanceToNextNode());  // 1 to 3 is 2

        Path path2 = graph.shortestPath(5,2);
        path2.setLengthTravelled(3);            // Travel 3 steps
        path2.updateDistanceNextNode();
        assertTrue("5 to 1 to 3 to 2", 6 == path2.distanceToNextNode());    // 5 to 1 originally 9. Now 6

        path2.setCurrent(graph.getNode(1));     // 5 to 1 is 9. 1 to 3 is 7
        path2.setLengthTravelled(10);           // move past 5 head to 3. 6 steps left
        path2.updateDistanceNextNode();
        assertTrue("5 to 2", 6 == path2.distanceToNextNode());

        path2.setLengthTravelled(18);           // Move past 3 head to 2. 
        path2.setCurrent(graph.getNode(3));    // 3 to 2 is 5 steps. 3 steps left
        path2.updateDistanceNextNode();
        assertTrue("5 to 2", 3 == path2.distanceToNextNode());

    }

    /**
     * Method tests if position on path is updated correctly 
     */
    @Test 
    public void testUpdatePositionOnPath(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Node node1 = graph.getNode(1);

        Path path1 = graph.shortestPath(1,3);
        path1.setStepsLeft(path1.length());
        path1.updatePositionOnPath();
        assertEquals("Start", graph.getNode(1), path1.current()); // At node 1 at first

        path1.setStepsLeft(0);          // Traverse whole path. At node 3
        path1.updatePositionOnPath();
        assertEquals("Traverse", graph.getNode(3), path1.current());   

        Path path2 = graph.shortestPath(5,2);       // 5 to 1 is 9. 1 to 3 is 7
        path2.setStepsLeft(11);                 // At node 1. heading to 3
        path2.updatePositionOnPath();
        assertEquals("5 to 1 to 3 to 2", graph.getNode(1), path2.current());

        path2.setStepsLeft(4);              // At node 3, heading to 2
        path2.updatePositionOnPath();
        assertEquals("5 to 1 to 3 to 2", graph.getNode(3), path2.current());

        path2.setStepsLeft(0);              // Finish path. At node 2
        path2.updatePositionOnPath();
        assertEquals("5 to 1 to 3 to 2", graph.getNode(2), path2.current());

    }

    /**
     * Method tests that path is copied/ updated with another path correctly
     */
    @Test
    public void testUpdatePath(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  

        Node node1 = graph.getNode(1);
        Node node3 = graph.getNode(3);
        Path path1 = new Path(node1, node3);
        path1.findLength();

        Node node5 = graph.getNode(5);
        Node node4 = graph.getNode(4);
        Path path2 = new Path(node5, node4);
        path2.addNode(node1);
        path2.addNode(node3);
        path2.findLength();

        path1.updatePath(path2);
        assertEquals("Update to path2 route", path1.route().toArray(), path2.route().toArray());
        assertTrue("Check length", path1.length() == path2.length());
    }

}