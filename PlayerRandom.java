import java.util.*;

/**
 * Class PlayerRandom extends the player class and uses the algorithm
 * that chooses a random neighbor of the current node
 *
 * @author HiepLe
 * @version 04/05/2018
 */
public class PlayerRandom extends Player
{   
    /**
     * Class has a random object which is used to generate the random neighbor choice
     */
    private Random chooseNextRandom;

    /**
     * Constructor takes in starting node, graph as well as a seed.
     * Mostly used for unit testing.
     */
    public PlayerRandom(Node startingNode, Graph graph, long seed){
        super(startingNode, graph);
        this.chooseNextRandom = new Random(seed);
        this.newPath();
    }

    /**
     * Constructor takes in starting node and graph. 
     * Random object uses a random seed to vary behavior of the player.
     */
    public PlayerRandom(int startingNode, Graph graph){
        super(startingNode, graph);
        this.chooseNextRandom = new Random();
        this.newPath();
    }

    public PlayerRandom(Node startingNode, Graph graph){
        super(startingNode, graph);
        this.chooseNextRandom = new Random();
        this.newPath();
    }

    /**
     * Method finds the next node to head towards
     * The next node is a random unvisited neighbor of the current node
     */
    public Node findNext(){
        ArrayList<Node> neighbors = this.current.getNeighbors();

        int neighborChoice = this.chooseNextRandom.nextInt(neighbors.size());

        // While neighbor is not already visited
        while(this.visited.contains(neighbors.get(neighborChoice))){
            neighborChoice = this.chooseNextRandom.nextInt(neighbors.size());
        }
        return neighbors.get(neighborChoice);
    }

    /**
     * Method initializes and update new path to the player's current node
     */
    public void updateNewPath(){
        Node destination = this.findNext();  
        this.currentPath = new Path(this.current, destination);
        this.currentPath.findLength();
        this.currentPath.updateDistanceNextNode();
    }

    public String toString() { return "Player Type Random";}
}
