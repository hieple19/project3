import java.util.*;

/**
 * Class PlayerShortest extends the player class and uses the algorithm
 * that chooses the nearest neighbor within the limit
 *
 * @author HiepLe
 * @version 04/05/2018
 */
public class PlayerShortest extends Player
{   
    /**
     * Constructor takes in starting node and graph. 
     */
    public PlayerShortest(int startingNode, Graph graph){
        super(startingNode, graph);
        this.newPath();
    }

    public PlayerShortest(Node startingNode, Graph graph){
        super(startingNode, graph);
        this.newPath();
    }

    /**
     * Method finds the next node to head towards
     * The next node is the nearest node within limit
     */
    public Node findNext(){
        HashMap<Integer,Node> nodesWithinLimit = this.graph.withinLimit(this.current);

        // Remove visited nodes from nodes withinlimit
        for(Node node: this.visited){
            nodesWithinLimit.remove(node.getNumber(),node);
        }

        // If there is a node within limit
        if(nodesWithinLimit.size() > 0){
            // Choose first unvisited node
            int index = nodesWithinLimit.keySet().iterator().next();

            // Set current path to path to nearest node
            Path nearestNodePath = this.graph.shortestPath(current,nodesWithinLimit.get(index));

            // Go through all nodes within limit
            for(Node node: nodesWithinLimit.values()){
                Path nodePath = this.graph.shortestPath(current, node);

                // Update index if path is shorter than current path
                if(nodePath.length() < nearestNodePath.length()){
                    nearestNodePath = nodePath;
                    index = node.getNumber();
                }
            }
            return nodesWithinLimit.get(index);
        } 

        // If there is no node within limit, player will choose the
        // first unvisited node, similar to PlayerFirst class
        else{
            for(Node neighbor: this.current.getNeighbors()){
                if(!this.visited.contains(neighbor)){
                    return neighbor;
                }
            }
        }
        return null;
    }

    /**
     * Method initializes and update new path to the player's current node
     */
    public void updateNewPath(){
        Node destination = this.findNext();
        this.currentPath = this.graph.shortestPath(this.current, destination);
        this.currentPath.setDone(false);
        this.currentPath.updateDistanceNextNode();

    }

    public String toString() { return "Player Type Shortest";}
}
