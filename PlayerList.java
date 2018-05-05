import java.util.*;

/**
 * Class PlayerList is a players containers and holds information about 
 * all players in the list
 *
 * @author HiepLe
 * @version 04/05/2018
 */
public class PlayerList{
    /**
     * Class has instance variables that keep track of the players in list
     * and players that have exited the maze 
     */

    private ArrayList<Player> list;
    private ArrayList<Player> exitedList;
    private int roundCount;

    /**
     * Constructor takes in number of players of different types and 
     * a graph where players will traverse through
     */
    
    public PlayerList(int noTypeFirst, int noTypeRandom, int noTypeShortest,Graph graph){
        this.list = new ArrayList<Player>();
        this.exitedList = new ArrayList<Player>();
        this.roundCount = 1;
        for(int i = 0; i<noTypeFirst; i++){
            PlayerFirst newPlayer = new PlayerFirst(graph.getStartingNode(), graph);
            this.list.add(newPlayer);
        }
        for(int i = 0; i<noTypeRandom; i++){
            PlayerRandom newPlayer = new PlayerRandom(graph.getStartingNode(), graph);
            this.list.add(newPlayer);
        }
        for(int i = 0; i<noTypeShortest; i++){
            PlayerShortest newPlayer = new PlayerShortest(graph.getStartingNode(), graph);
            this.list.add(newPlayer);
        }
    }

    public ArrayList<Player> getPlayers() {return this.list;}

    public ArrayList<Player> exitedPlayers() {return this.exitedList;}
    
    /**
     * Method set dice for all players in the list
     */
    public void setDice(Dice dice){
        for(Player player: list){
            player.setDice(dice);
        }
    }
    
    /**
     * Method prompts all players in the list to go through one step
     * For every player step, it also checks if the player has exited the maze
     */
    public void oneStep(){
        System.out.println("Round " + this.roundCount + " Done");
        System.out.println();
        for(Player player: list){
            player.oneStep();
            
            // Check if player has exited maze and is not already in exited list
            if(player.exit() && !this.exitedList.contains(player)){
                exitedList.add(player);
                Collections.sort(this.exitedList); // Sort exit list based on compareTo method in player class
            }
        }
        this.roundCount++;
    }
    
    /**
     * Method checks if all players have exited maze
     */
    public boolean checkAllExit(){
        for(Player player: list){
            if(!player.exit()){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Method skips to the point where every player has exited maze
     * by calling the oneStep() method repeatedly
     */
    public void skipToCompletion(){
        while(!this.checkAllExit()){
            this.oneStep();
        }
    }
    
    /**
     * Method prints current information for all players
     */
    public void printPosition(){
        for(Player player: list){
            if(!this.exitedList.contains(player)){
                player.printPosition();
            }
        }
        this.printFinished();
    }
    
    /**
     * Method prints information about players that have exited the maze 
     */
    public void printFinished(){
        int rank = 1;

        if(this.exitedList.size() != 0){
            System.out.println("List of players who successfully exited maze");
            for(Player player: this.exitedList){
                System.out.println("RANK " + rank);
                player.printPosition();
                rank++;
            }
        }
    }
}