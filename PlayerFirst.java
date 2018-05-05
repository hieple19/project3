import java.util.*;

/**
 * Class PlayerFirst extends the player class and uses the algorithm
 * that chooses the first neighbor of the current node
 *
 * @author HiepLe
 * @version 04/05/2018
 */
public class PlayerFirst extends Player{
    
    /**
     * Constructor takes in starting node and graph
     * A new path is then created
     */
    public PlayerFirst(Node startingNode, Graph graph){
        super(startingNode, graph);
        this.newPath();
    }

    public PlayerFirst(int startingNode,Graph graph){
        super(startingNode, graph);
        this.newPath();
    }
    
    /**
     * Method finds the next node to head towards
     * The next node is the first unvisited neighbor of the current node
     */
    public Node findNext(){
        for(Node neighbor: this.current.getNeighbors()){
            if(!this.visited.contains(neighbor)){
                return neighbor;        // Return the first unvisited neighbor
            }
        }
        return null;
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
    
    public String toString() { return "Player Type First";}
}
