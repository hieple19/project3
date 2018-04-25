import java.util.*;
/**
 * Write a description of class Plyaer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PlayerFirst{
    private Graph graph;
    private Node current;
    private ArrayList<Node> visited;
    private int limit;
    private Path currentPath;
    private int extraSteps;

    public PlayerFirst(int startingNode,Graph graph){
        this.graph = graph;
        this.current = this.graph.find(startingNode);
        this.visited = new ArrayList<Node>();
        this.visited.add(current);
        this.newPath();
        this.current.print();
    }

    public void oneStep(){
        System.out.println("STEP");
        if(!checkFinished()){
            int stepsToTake = this.rollDice() + this.extraSteps;

            this.traversePath(stepsToTake);
            while(this.extraSteps > 0){
                if(checkFinished()){
                    break;
                }
                this.newPath();
                this.traversePath(this.extraSteps);
            }
            this.print();
            System.out.println();
        }
        else{
            System.out.println("Done");
            System.out.println(visited);
        }

    }

    public int rollDice(){
        Random random = new Random(123);
        int steps = random.nextInt(9) + 1;
        return steps;
    }

    public void traversePath(int steps){
        if(!checkFinished()){
            System.out.println("Path to Traverse");
            System.out.println("extraSteps " + this.extraSteps);
            System.out.println("No. of steps " + steps);
            this.currentPath.print();
            System.out.println();
            if(currentPath.checkDone(steps)){
                if(steps>currentPath.stepsLeft()){
                    this.extraSteps = steps - currentPath.stepsLeft();
                }
                this.current = this.currentPath.end();
                this.visited.add(currentPath.end());
            }
            else{
                int stepsLeftOnPath = this.currentPath.stepsLeft() - steps;
                currentPath.setStepsLeft(stepsLeftOnPath);
                this.extraSteps = 0;
            }}
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

    public Node firstUnvisited(Node node){
        for(Node neighbor: node.getNeighbors()){
            if(!this.visited.contains(neighbor)){
                return neighbor;
            }
        }
        return null;
    }

    public boolean deadEnd(Node node){
        for(Node neighbor: node.getNeighbors()){
            if(!this.visited.contains(neighbor)){
                return false;
            }
        }
        return true;
    }

    public void print(){
        System.out.println("Current " + this.current.getNumber());
        System.out.print("Path ");
        this.currentPath.print();
    }

    public boolean checkFinished(){
        return this.visited.size() == 6;
    }
}
