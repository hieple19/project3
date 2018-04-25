
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
public class UndirectedGraphTest
{
    @Test
    public void testAdd() {
        Graph graph = new Graph();                            //create graph
        graph.addNode(1);                                                                       //add the same node twice
        graph.addNode(1);

        int[] expect = {1};                                                                     //expect
        assertArrayEquals(expect, graph.nodesArray());

        graph.addNode(2);
        graph.addNode(3);
        int[] expect2 = {1,2,3};
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
        Node node1 = graph.find(1);
        Node node2 = graph.find(2);
        assertTrue(node1.hasEdge(node2));
        assertTrue(node2.hasEdge(node1));
        assertEquals(3, node1.getEdge(node2).weight());
        assertEquals(3, node2.getEdge(node1).weight());
        
        graph.addNode(3);
        graph.addNode(4);
        assertTrue(graph.addEdge(1,2,9));
        assertEquals(9, node1.getEdge(node2).weight());
        assertEquals(9, node2.getEdge(node1).weight());

        assertTrue(graph.addEdge(1,3,10));
        assertTrue(graph.addEdge(1,4,11)); 

        Node node3 = graph.find(3);
        Node node4 = graph.find(4);
        assertTrue(node1.hasEdge(node3));
        assertTrue(node3.hasEdge(node1));
        assertTrue(node4.hasEdge(node1));
        assertTrue(node1.hasEdge(node4));
        assertEquals(10, node1.getEdge(node3).weight());
        assertEquals(11, node1.getEdge(node4).weight());
        assertEquals(10, node3.getEdge(node1).weight());
        assertEquals(11, node4.getEdge(node1).weight());

    }
}
