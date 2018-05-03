
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class DiceTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class DiceTest
{
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
