import java.util.*;
/**
 * Write a description of class Plyaer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PlayerFirst extends Player{

    public PlayerFirst(Node node, Graph graph){
        super(node, graph);
    }

    public PlayerFirst(int startingNode,Graph graph){
        super(startingNode, graph);
    }

    public void oneStep(){
        if(!this.checkExit()){
            System.out.println("STEP");
            int stepsToTake = this.rollDice() + this.extraSteps;

            this.traversePath(stepsToTake);
            while(this.extraSteps > 0){
                if(this.currentPath.done()){
                    this.newPath();}
                this.traversePath(this.extraSteps);
            }
            this.checkExit();
            this.print();
            System.out.println();
        }
        else{
            System.out.println("Exited Maze");
        }

    }

    public void traversePath(int steps){
        /*System.out.println("Path to Traverse");
        System.out.println("extraSteps " + this.extraSteps);
        System.out.println("No. of steps " + steps);
        this.currentPath.print();
        System.out.println();*/
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
        }
    }

    public void newPath(){
        if(this.deadEnd(this.current)){
            int currentIndex = this.visited.indexOf(current);
            System.out.println("Current " + current);
            Node previous = this.visited.get(currentIndex -1);
            this.currentPath = new Path(this.current, previous);
        }
        else if(this.checkExitInRange()){
            HashMap<Node, Path> allPaths = this.graph.shortestPaths(this.current);
            this.currentPath = allPaths.get(this.exitNodesInRange.get(0));
            for(Node node: exitNodesInRange){
                if(allPaths.get(node).length() < this.currentPath.length()){
                    this.currentPath = allPaths.get(node);
                }
            }
        }
        else{
            Node destination = this.firstUnvisited(this.current);  
            this.currentPath = new Path(this.current, destination);
            this.currentPath.findLength();
        }
    }

    public boolean checkExitInRange(){
        ArrayList<Node> nodes = this.graph.withinLimit(current);

        for(Node node: nodes){
            if(this.graph.getExitNodes().contains(node)){
                this.exitNodesInRange.add(node);
            }
        }

        if(this.exitNodesInRange.size() == 0){
            return false;
        }
        else{
            return true;
        }
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

    public boolean checkExit(){
        if(this.graph.getExitNodes().contains(this.current)){
            this.exitMaze = true;
            return true;
        }
        return false;
    }

    public void print(){
        System.out.println("Current " + this.current.getNumber());
        System.out.print("Path ");
        this.currentPath.print();
    }

    public boolean checkFinished(){
        return this.visited.size() == this.graph.getNodes().size();
    }
}
