
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class PlayerRandomTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PlayerRandomTest
{
    @Test
    public void findNewPath(){
        Graph graph = new Graph(1,"testBig.txt", "testConfig3.txt");  
        PlayerRandom player = new PlayerRandom(graph.getNode(4), graph,123);
        player.newPath();
        Path expected = graph.shortestPath(4,3);
        assertArrayEquals("Random neighbor 3", expected.route().toArray(),player.currentPath.route().toArray());

        player.traversePath(1);
        player.newPath();
        expected = graph.shortestPath(3,6);
        assertArrayEquals("Random neighbor of 3 is 6", expected.route().toArray(), player.currentPath.route().toArray());

        player.traversePath(2);
        player.newPath();
        expected = graph.shortestPath(6,9);
        assertArrayEquals("Random neighbor of 6 is 9", expected.route().toArray(),player.currentPath.route().toArray());

    }

}
