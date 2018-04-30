import java.util.*;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    protected Graph graph;
    protected Node current;
    protected ArrayList<Node> visited;
    protected ArrayList<Node> exitNodesInRange;
    protected int limit;
    protected Path currentPath;
    protected int extraSteps;
    protected boolean exitMaze;
    protected Random random = new Random(4345);

    public Player(Node startingNode, Graph graph){
        this.graph = graph;
        this.current = startingNode;
        this.visited = new ArrayList<Node>();
        this.exitNodesInRange = new ArrayList<Node>();
        this.visited.add(current);
        this.newPath();
        this.current.print();
        this.exitMaze = false;
    }

    public Player(int startingNode,Graph graph){
        this.graph = graph;
        Node node = this.graph.find(startingNode);
        this.visited = new ArrayList<Node>();
        this.exitNodesInRange = new ArrayList<Node>();
        this.visited.add(current);
        this.newPath();
        this.current.print();
        this.exitMaze = false;
    }

    public void newPath(){
        while(this.deadEnd(this.current)){
            int currentIndex = this.visited.indexOf(current);
            this.current = this.visited.get(currentIndex - 1);
        }
        Node destination = this.firstUnvisited(this.current);  
        this.currentPath = new Path(this.current, destination);
        this.currentPath.findLength();
    }

    public boolean deadEnd(Node node){
        for(Node neighbor: node.getNeighbors()){
            if(!this.visited.contains(neighbor)){
                return false;
            }
        }
        return true;
    }

    public int rollDice(){
        int steps = random.nextInt(6) + 1;
        return steps;
    }

    public Node firstUnvisited(Node node){
        for(Node neighbor: node.getNeighbors()){
            if(!this.visited.contains(neighbor)){
                return neighbor;
            }
        }
        return null;
    }

}
