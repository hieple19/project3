
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class PathTest{

    @Test
    public void pathAfterCurrentTest(){
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Path loop = new Path(node1,node1);
        Node[] empty = {};
        assertArrayEquals("Path after current", empty, loop.pathAfterCurrent().toArray());

        Path path1 = new Path(node1, node2);
        Node[]expected = {node2};
        assertArrayEquals("Path after current", expected, path1.pathAfterCurrent().toArray());

        Node node3 = new Node(3);
        path1.addNode(node3);
        Node node5 = new Node(5);
        path1.addNode(node5);

        Node[]expected2 = {node3,node5,node2};
        assertArrayEquals("Path after node 1",expected2, path1.pathAfterCurrent().toArray());

        path1.setCurrent(node3);
        Node[] expected3 = {node5,node2};
        assertArrayEquals("Path after node 3", expected3, path1.pathAfterCurrent().toArray());

        Node node4 = new Node(4);
        path1.addNode(node4);
        path1.setCurrent(node3);
        Node[] expected4 = {node5,node4,node2};
        assertArrayEquals("Path after node 2", expected4, path1.pathAfterCurrent().toArray());

    }

    @Test   
    public void testFindLength(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Node node1 = graph.getNode(1);
        Path circuit = new Path(node1,node1);
        circuit.findLength();
        assertTrue("Circuit", 0 == circuit.length());

        Node node3 = graph.getNode(3);
        Path path1 = new Path(node1,node3);
        path1.findLength();
        assertTrue("Path 1", 7 == path1.length());

        Node node2 = graph.getNode(2);
        ArrayList<Node> route = new ArrayList<Node>();
        route.add(node1);
        route.add(node3);
        route.add(node2);

        path1.setRoute(route);
        path1.findLength();
        assertTrue("Path 1 to 3 to 2", 12 == path1.length());

        Node node5 = graph.getNode(5);
        route.add(0,node5);
        path1.setRoute(route);
        path1.findLength();
        assertTrue("Path 5 to 1 to 3 to 2", 21 == path1.length());
    }      

    @Test
    public void testDistanceNext(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Node node1 = graph.getNode(1);
        Path circuit = new Path(node1,node1);
        assertTrue("Circuit", 0 == circuit.totalLengthToNextNode());

        Path path1 = graph.shortestPath(1,3);
        assertTrue("1 to 3", 7 == path1.totalLengthToNextNode());
        path1.updateDistanceNextNode();

        Path path2 = graph.shortestPath(4,2);
        assertTrue("4 to 3 to 2", 1 == path2.totalLengthToNextNode());

        path2.setCurrent(graph.getNode(3));
        assertTrue("4 to 3 to 2", 6 == path2.totalLengthToNextNode());

        Path path3 = graph.shortestPath(5,6);
        assertTrue("5 to 1 to 3 to 6", 9 == path3.totalLengthToNextNode());

        path3.setCurrent(graph.getNode(3));
        assertTrue("5 to 1 to 3 to 6", 18 == path3.totalLengthToNextNode()); 
    }

    @Test
    public void testDistancePrev(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Node node1 = graph.getNode(1);
        Path circuit = new Path(node1,node1);
        assertTrue("Circuit", 0 == circuit.lengthToPrevNode());

        Path path1 = graph.shortestPath(1,3);
        assertTrue("1 to 3", 0 == path1.lengthToPrevNode());
        path1.setCurrent(graph.getNode(3));
        assertTrue("1 to 3", 7 == path1.lengthToPrevNode());

        Path path2 = graph.shortestPath(4,2);

        path2.setCurrent(graph.getNode(3));
        assertTrue("4 to 3 to 2", 1 == path2.lengthToPrevNode());

        Path path3 = graph.shortestPath(5,6);
        path3.setCurrent(graph.getNode(1));
        assertTrue("5 to 1 to 3 to 6", 9 == path3.lengthToPrevNode());
        path3.setCurrent(graph.getNode(3));
        assertTrue("5 to 1 to 3 to 6", 7 == path3.lengthToPrevNode());        
    }

    @Test
    public void testUpdateDistanceToNextNode(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Node node1 = graph.getNode(1);

        Path path1 = graph.shortestPath(1,3);
        //path1.findLength();
        path1.setStepsLeft(7);
        path1.updateDistanceNextNode();
        assertTrue("1 to 3", 7 == path1.distanceToNextNode());

        path1.setLengthTravelled(5);
        path1.updateDistanceNextNode();
        path1.print();
        assertTrue("1 to 3", 2 == path1.distanceToNextNode());

        Path path2 = graph.shortestPath(5,2);
        //path2.findLength();
        path2.setLengthTravelled(3);
        path2.updateDistanceNextNode();
        assertTrue("5 to 1 to 3 to 2", 6 == path2.distanceToNextNode());

        path2.setCurrent(graph.getNode(1));
        path2.setLengthTravelled(10);
        path2.updateDistanceNextNode();
        assertTrue("5 to 2", 6 == path2.distanceToNextNode());

        path2.setLengthTravelled(18);
        path2.setCurrent(graph.getNode(3));
        path2.updateDistanceNextNode();
        assertTrue("5 to 2", 3 == path2.distanceToNextNode());

    }

    @Test 
    public void testUpdatePositionOnPath(){
        Graph graph = new Graph(10,"test.txt", "testConfig.txt");  
        Node node1 = graph.getNode(1);

        Path path1 = graph.shortestPath(1,3);
        path1.setStepsLeft(path1.length());
        path1.updatePositionOnPath();
        assertEquals("Start", graph.getNode(1), path1.current());

        path1.setStepsLeft(0);
        path1.updatePositionOnPath();
        assertEquals("Traverse", graph.getNode(3), path1.current());

        Path path2 = graph.shortestPath(5,2);
        path2.setStepsLeft(11);
        path2.updatePositionOnPath();
        assertEquals("5 to 1 to 3 to 2", graph.getNode(1), path2.current());

        path2.setStepsLeft(4);
        path2.updatePositionOnPath();
        assertEquals("5 to 1 to 3 to 2", graph.getNode(3), path2.current());

        path2.setStepsLeft(0);
        path2.updatePositionOnPath();
        assertEquals("5 to 1 to 3 to 2", graph.getNode(2), path2.current());

    }

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