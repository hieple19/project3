
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class PlayerShortestTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PlayerShortestTest
{
    @Test
    public void findNewPath(){
        Graph graph = new Graph(5,"testBig.txt", "testConfig3.txt");  
        PlayerShortest player = new PlayerShortest(graph.getNode(4), graph);
        player.newPath();
        Path expected = graph.shortestPath(4,3);
        assertArrayEquals("Nearest node is 3", expected.route().toArray(),player.currentPath.route().toArray());

        player.traversePath(1);
        player.newPath();
        expected = graph.shortestPath(3,6);
        assertArrayEquals("Nearest node of 3 is 6", expected.route().toArray(), player.currentPath.route().toArray());

        player.traversePath(2);
        player.newPath();
        expected = graph.shortestPath(6,5); // 6 to 4 to 5
        assertArrayEquals("Nearest node of 6 is 5", expected.route().toArray(),player.currentPath.route().toArray());

        graph = new Graph(0,"testBig.txt", "testConfig3.txt");  
        player = new PlayerShortest(graph.getNode(4), graph);
        player.newPath();
        expected = graph.shortestPath(4,3);
        assertArrayEquals("First neighbor of 4 is 3", expected.route().toArray(),player.currentPath.route().toArray());

    }
}
