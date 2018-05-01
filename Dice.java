import java.util.*;

/**
 * Write a description of class Dice here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Dice
{
    private Random random; 
    private int maxValue;

    public Dice(int maxValue){
        this.random = new Random();
        this.maxValue = maxValue;
    }
    
    public Dice(int maxValue, long seed){
        this.random = new Random(seed);
        this.maxValue = maxValue;
    }

    public int rollDice(){
        return this.random.nextInt(maxValue) + 1;
    }
}
