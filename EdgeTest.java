

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class EdgeTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class EdgeTest
{
    @Test
    public void testEdge(){
        Node start = new Node(2);
        Node end1 = new Node(5);
        Edge edge1 = new Edge(end1,6);
        
        assertTrue(start.addEdge(edge1));
        assertTrue("Test weight", 6 == edge1.weight());
        assertEquals("Test end", end1, edge1.end());
        
        edge1.setWeight(10);
        assertTrue("Test change weight", 10 ==  edge1.weight());
    }
}
