import java.util.*;

/**
 * Class Dice creates objects that generates a random of a fixed range
 * similar to how a dice works 
 *
 * @author Hiep Le
 * @version 05/04/18
 */
public class Dice
{   
    /**
     * Class has a random object to generate a random value as well
     * as a max value variable 
     */
    private Random random; 
    private int maxValue;
    
    /**
     * Constructor takes in max value of number generated 
     */
    public Dice(int maxValue){
        this.random = new Random();
        this.maxValue = maxValue;
    }
    
    public Dice(int maxValue, long seed){
        this.random = new Random(seed);
        this.maxValue = maxValue;
    }

    public int rollDice(){
        int next =  this.random.nextInt(maxValue) + 1;        
        return next;
    }
}
