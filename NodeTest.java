
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
/**
 * The test class NodeTest.
 *
 * @author  Hiep Le
 * @version 05/04/18
 */
public class NodeTest
{   
    /**
     * Method tests if nodes and edges connecting nodes are corrected constructed
     */
    @Test
    public void testEdges(){
        Node start = new Node(1);
        Node end1 = new Node(3);
        Node end2 = new Node(5);
        assertFalse("Node not connected to any", start.hasEdge(end1));

        Edge edge1 = new Edge(end1,2);
        start.addEdge(edge1);
        assertTrue("Node connected to end1", start.hasEdge(end1));
        assertEquals("Get edge connected to end1", edge1, start.getEdge(end1));
        assertTrue("Compare length", 2 == start.getEdge(end1).weight());

        Edge edge2 = new Edge(end2,3);
        start.addEdge(edge2);
        assertTrue("Node connected to end2", start.hasEdge(end2));
        assertEquals("Get edge connected to end2", edge2, start.getEdge(end2));
        assertTrue("Compare length", 3 == start.getEdge(end2).weight());
    }
    
    /**
     * Method tests if all neighbors are returned correctly
     */
    @Test 
    public void testGetNeighbors(){
        Node start = new Node(1);
        Node end1 = new Node(3);
        Node end2 = new Node(5);
        ArrayList<Node> neighbors = start.getNeighbors();
        Node[]expected = {};
        assertArrayEquals("Node not connected", expected, neighbors.toArray());

        Edge edge1 = new Edge(end1,2);      // Connect start to end1
        start.addEdge(edge1);
        Node[]expected2 = {end1};
        neighbors = start.getNeighbors();
        assertArrayEquals("1 neighbor", expected2, neighbors.toArray());

        Edge edge2 = new Edge(end2,3);      // Connect start to end2
        start.addEdge(edge2);
        Node[] expected3 = {end1,end2};
        neighbors = start.getNeighbors();
        assertArrayEquals("2 neighbors", expected3, neighbors.toArray());

        neighbors = end2.getNeighbors();
        assertArrayEquals("end2 has no neighbor", expected, neighbors.toArray());
        
        Edge edge3 = new Edge(start,6);     // Connect end to start
        end2.addEdge(edge3);
        Node[] expected4 = {start};
        neighbors = end2.getNeighbors();
        assertArrayEquals("end2 has start as neighbor", expected4, neighbors.toArray());

    }
}
