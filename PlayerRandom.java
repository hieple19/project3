import java.util.*;

/**
 * Write a description of class PlayerRandom here.
 *
 * @author (Hiep Le)
 * @version (a version number or a date)
 */
public class PlayerRandom extends Player
{   
    private Random chooseNextRandom;

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

    public Node findNext(Node current){
        ArrayList<Node> neighbors = current.getNeighbors();
        int neighborChoice = this.chooseNextRandom.nextInt(neighbors.size());
        while(this.visited.contains(neighbors.get(neighborChoice))){
            neighborChoice = this.chooseNextRandom.nextInt(neighbors.size());
        }
        System.out.println("Current " + current.getNumber() + " Neighbor Choice " + neighborChoice);
        return neighbors.get(neighborChoice);
    }

    public void updateNewPath(){
        Node destination = this.findNext(this.current);  
        this.currentPath = new Path(this.current, destination);
        this.currentPath.findLength();
        this.currentPath.updateDistanceNextNode();
    }

    public String toString() { return "Player Type Random";}
}
