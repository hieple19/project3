
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class PlayerListTest.
 *
 * @author  Hiep Le
 * @version 05/04/2018
 */
public class PlayerListTest
{   
    /**
     * Method tests if players are added appropriately
     */
    @Test
    public void playerListTest(){
        Graph graph = new Graph(10,"test.txt","testConfigSmall.txt");
        PlayerList players = new PlayerList(1,1,1,graph);
        Dice dice = new Dice(10,123);
        players.setDice(dice);
        players.oneStep();

        Player playerShortest = players.getPlayers().get(2);
        Player[] expected = {playerShortest};
        assertArrayEquals("Player shortest exited", expected, players.exitedPlayers().toArray());

        dice = new Dice(4,22);
        players.setDice(dice);
        players.oneStep();

        Player playerFirst = players.getPlayers().get(0);
        Player[] expected2 = {playerShortest,playerFirst};
        assertArrayEquals("Player first exited", expected2, players.exitedPlayers().toArray());

        players.oneStep();
        Player playerLast = players.getPlayers().get(1);
        Player[] expected3 = {playerShortest,playerFirst,playerLast};
        assertArrayEquals("Player first exited", expected3, players.exitedPlayers().toArray());

    }
}
