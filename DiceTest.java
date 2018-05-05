
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class DiceTest.
 *
 * @author  Hiep Le
 * @version 05/04/18
 */
public class DiceTest
{   
    /**
     * Method test if dice rolls a full range of expected values
     */
    @Test
    public void rollDiceTest(){
        Dice dice = new Dice(5,123);
        assertEquals("Roll 1", 3, dice.rollDice());
        assertEquals("Roll 2", 1, dice.rollDice());
        assertEquals("Roll 3", 2, dice.rollDice());        
        assertEquals("Roll 4", 5, dice.rollDice());
        assertEquals("Roll 5", 1, dice.rollDice());
    }
}
