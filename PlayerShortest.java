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
        ArrayList<Node> nodesWithinLimit = this.graph.withinLimit(current);

        for(Node node: this.visited){
            nodesWithinLimit.remove(node);
        }
        System.out.println("Finding nodes within");
        System.out.println(nodesWithinLimit);
        Path nearestNodePath = this.graph.shortestPath(current,nodesWithinLimit.get(0));
        int nearestIndex = 0;
        for(int i = 1; i<nodesWithinLimit.size(); i++){
            Path currentNodePath = this.graph.shortestPath(current, nodesWithinLimit.get(i));
            if(currentNodePath.length() < nearestNodePath.length()){
                nearestNodePath = currentNodePath;
                nearestIndex = i;
            }
        }
        return nodesWithinLimit.get(nearestIndex);
    } 

    public void updateNewPath(){
        Node destination = this.findNext(this.current);
        this.currentPath = this.graph.shortestPath(this.current, destination);
        this.currentPath.setDone(false);
        this.currentPath.updateDistanceNextNode();

    }

    public String toString() { return "Player Type Shortest";}
}
