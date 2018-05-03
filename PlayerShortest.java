import java.util.*;

public class PlayerShortest extends Player
{
    public PlayerShortest(int startingNode, Graph graph){
        super(startingNode, graph);
        this.newPath();
    }

    public PlayerShortest(Node startingNode, Graph graph){
        super(startingNode, graph);
        this.newPath();
    }

    public Node findNext(Node current){
        HashMap<Integer,Node> nodesWithinLimit = this.graph.withinLimit(current);

        for(Node node: this.visited){
            nodesWithinLimit.remove(node.getNumber(),node);
        }
        int index = nodesWithinLimit.keySet().iterator().next();
        /*int nearestIndex;
        for(Node node: nodesWithinLimit.values()){
            node = */
        Path nearestNodePath = this.graph.shortestPath(current,nodesWithinLimit.get(index));
        for(Node node: nodesWithinLimit.values()){
            Path currentNodePath = this.graph.shortestPath(current, node);
            if(currentNodePath.length() < nearestNodePath.length()){
                nearestNodePath = currentNodePath;
                index = node.getNumber();
            }
        }
        return nodesWithinLimit.get(index);
    } 

    public void updateNewPath(){
        Node destination = this.findNext(this.current);
        this.currentPath = this.graph.shortestPath(this.current, destination);
        this.currentPath.setDone(false);
        this.currentPath.updateDistanceNextNode();

    }

    public String toString() { return "Player Type Shortest";}
}
