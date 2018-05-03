import java.util.*;

/**
 * Write a description of class Player here.
 *
 * @author HiepLe
 * @version (a version number or a date)
 */
public class PlayerFirst extends Player{

    public PlayerFirst(Node startingNode, Graph graph){
        super(startingNode, graph);
        this.newPath();
    }

    public PlayerFirst(int startingNode,Graph graph){
        super(startingNode, graph);
        this.newPath();
    }

    public Node findNext(){
        for(Node neighbor: this.current.getNeighbors()){
            if(!this.visited.contains(neighbor)){
                return neighbor;
            }
        }
        return null;
    }

    public void updateNewPath(){
        Node destination = this.findNext();  
        this.currentPath = new Path(this.current, destination);
        this.currentPath.findLength();
        this.currentPath.updateDistanceNextNode();
    }
    
    public String toString() { return "Player Type First";}
}
