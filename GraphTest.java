
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class UndirectedGraphTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GraphTest
{
    @Test
    public void testAdd() {
        Graph graph = new Graph();                            //create graph
        graph.addNode(1);                                                                       //add the same node twice
        graph.addNode(1);

        Integer[] expect = {1};                                                                     //expect
        assertArrayEquals(expect, graph.nodesArray());

        graph.addNode(2);
        graph.addNode(3);
        Integer[] expect2 = {1,2,3};
        assertArrayEquals(expect2, graph.nodesArray());
    }

    @Test 
    public void testAddEge(){
        Graph graph = new Graph();                            //create graph
        assertFalse(graph.addEdge(1,2,3));         

        graph.addNode(1);    
        assertFalse(graph.addEdge(1,2,3)); 

        graph.addNode(2);
        assertTrue(graph.addEdge(1,2,3)); 
        Node node1 = graph.getNode(1);
        Node node2 = graph.getNode(2);
        assertTrue(node1.hasEdge(node2));
        assertTrue(node2.hasEdge(node1));
        assertTrue(3 == node1.getEdge(node2).weight());
        assertTrue(3 == node2.getEdge(node1).weight());

        graph.addNode(3);
        graph.addNode(4);
        assertTrue(graph.addEdge(1,2,9));
        assertTrue(9 == node1.getEdge(node2).weight());
        assertTrue(9 == node2.getEdge(node1).weight());

        assertTrue(graph.addEdge(1,3,10));
        assertTrue(graph.addEdge(1,4,11)); 

        Node node3 = graph.getNode(3);
        Node node4 = graph.getNode(4);
        assertTrue(node1.hasEdge(node3));
        assertTrue(node3.hasEdge(node1));
        assertTrue(node4.hasEdge(node1));
        assertTrue(node1.hasEdge(node4));
        assertTrue(10 == node1.getEdge(node3).weight());
        assertTrue(11 == node1.getEdge(node4).weight());
        assertTrue(10 == node3.getEdge(node1).weight());
        assertTrue(11 == node4.getEdge(node1).weight());
    }

    @Test 
    public void testGetNeighbors(){
        Graph graph = new Graph();                            //create graph
        graph.addNode(1);
        Node node1 = graph.getNode(1);
        ArrayList<Node> neighbors = node1.getNeighbors();
        Node[]expected = {};
        assertArrayEquals("Node not connected", expected, neighbors.toArray());

        graph.addNode(2);
        Node node2 = graph.getNode(2);
        graph.addEdge(1,2,3);
        Node[]expected2 = {node2};
        neighbors = node1.getNeighbors();
        assertArrayEquals("1 neighbor", expected2, neighbors.toArray());

        graph.addNode(3);
        Node node3 = graph.getNode(3);
        graph.addEdge(1,3,5);
        Node[] expected3 = {node2,node3};
        neighbors = node1.getNeighbors();
        assertArrayEquals("2 neighbors", expected3, neighbors.toArray());

        graph.addNode(4);
        Node node4 = graph.getNode(4);
        graph.addEdge(1,4,8);
        graph.addEdge(2,4,3);
        Node[] expected4 = {node2,node3,node4};
        neighbors = node1.getNeighbors();
        assertArrayEquals("3 neighbors", expected4, neighbors.toArray());

        Node[] expected5 = {node1,node4};
        neighbors = node2.getNeighbors();
        assertArrayEquals("Node 2's neighbors", expected5, neighbors.toArray());      
    }

    @Test
    public void testWithinLimit(){
        Graph graph = new Graph(0,"test.txt", "testConfig.txt");   
        HashMap<Integer,Node> withinLimit = graph.withinLimit(graph.getNode(5));
        Integer[] empty = new Integer[withinLimit.size()];
        int i = 0;
        for(Integer key: withinLimit.keySet()){
            empty[i] = key;
            i++;
        }
        Integer[]emptyExpected = {5};
        assertArrayEquals("Within 5 steps of 5", emptyExpected,empty);

        graph = new Graph(10,"test.txt", "testConfig.txt");   
        withinLimit = graph.withinLimit(graph.getNode(3));
        Integer[] results = new Integer[withinLimit.size()];
        i=0;
        for(Integer key: withinLimit.keySet()){
            results[i] = key;
            i++;
        }
        Integer[]expected = {1,2,3,4,6};
        assertArrayEquals("Within 10 steps of 3", expected,results);

        graph = new Graph(5,"test.txt", "testConfig.txt");   
        withinLimit = graph.withinLimit(graph.getNode(6));
        Integer[] results2 = new Integer[withinLimit.size()];
        i=0;
        for(Integer key: withinLimit.keySet()){
            results2[i] = key;
            i++;
        }
        Integer[]expected2 = {3,4,6};
        assertArrayEquals("Within 5 steps of 6", expected2,results2);
    }

    @Test 
    public void testShortestPaths(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");   
        HashMap<Node,Path> shortestPaths = graph.shortestPaths(6);
        System.out.println(shortestPaths.keySet());
        ArrayList<Integer> shortestDistances = new ArrayList<Integer>();
        for(Path path: shortestPaths.values()){
            shortestDistances.add(path.length());
        }
        Integer[] expected = {0,2,2,6,9,18};
        Collections.sort(shortestDistances);
        assertArrayEquals("Shortest from 6", expected,shortestDistances.toArray());

        shortestPaths = graph.shortestPaths(3);

        shortestDistances = new ArrayList<Integer>();
        for(Path path: shortestPaths.values()){
            shortestDistances.add(path.length());
        }
        Integer[] expected2 = {0,1,2,5,7,16};
        Collections.sort(shortestDistances);
        assertArrayEquals("Shortest from 3", expected2,shortestDistances.toArray());

        shortestPaths = graph.shortestPaths(1);

        shortestDistances = new ArrayList<Integer>();
        for(Path path: shortestPaths.values()){
            shortestDistances.add(path.length());
        }
        Integer[] expected3 = {0,7,8,9,9,12};
        Collections.sort(shortestDistances);
        assertArrayEquals("Shortest from 3", expected3,shortestDistances.toArray());
    }

    @Test
    public void testShortestPath(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Path toItself = graph.shortestPath(5,5);
        assertEquals("5 to 5", 0, toItself.length());
        Node[] circuit = {graph.getNode(5)};
        assertArrayEquals("Route 5 to 5", circuit, toItself.route().toArray());

        Path short1 = graph.shortestPath(5,6);
        assertEquals("5 to 6", 18, short1.length());
        Node[] route1 = {graph.getNode(5),graph.getNode(1),graph.getNode(3),graph.getNode(6)};
        assertArrayEquals("Route 5 to 6", route1, short1.route().toArray());

        Path short2 = graph.shortestPath(2,1);
        assertEquals("2 to 1", 12, short2.length());
        Node[] route2 = {graph.getNode(2), graph.getNode(3), graph.getNode(1)};
        assertArrayEquals("Route 2 to 1", route2, short2.route().toArray());

        Path short3 = graph.shortestPath(3,6);
        assertEquals("3 to 6", 2, short3.length());
        Node[] route3 = {graph.getNode(3), graph.getNode(6)};
        assertArrayEquals("Route 3 to 6", route3, short3.route().toArray());

    }

    @Test
    public void testCheckForExit(){
        Graph graph = new Graph(5,"test.txt", "testConfigSmall.txt");  
        assertEquals("Check exit for 5", false, graph.exitWithinNode(graph.getNode(5)));
        assertEquals("Check exit for 1", false, graph.exitWithinNode(graph.getNode(1)));
        assertEquals("Check exit for 4", true, graph.exitWithinNode(graph.getNode(4)));
        assertEquals("Check exit for 2", true, graph.exitWithinNode(graph.getNode(2)));

    }
}
