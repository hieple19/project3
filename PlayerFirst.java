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
            int stepsToTake = this.rollDice();
            System.out.println("Dice rolled " + stepsToTake);
            this.traversePath(stepsToTake);
            while(this.extraSteps > 0 && !this.exitMaze){
                this.newPath();
                System.out.println("extra steps " + extraSteps);
                this.traversePath(this.extraSteps);

            }

            if(!this.exitMaze && this.currentPath.done()){
                this.newPath();
            }
            System.out.println();
            System.out.println("Printing Info at end of steps");
            this.print();
            System.out.println("Ending info");
            System.out.println();
        }
        else{
            System.out.println("Exited Maze");
        }

    }

    public void traversePath(int steps){
        if(currentPath.checkDone(steps)){
            if(steps>currentPath.stepsLeft()){
                this.extraSteps = steps - currentPath.stepsLeft();
            }
            this.current = this.currentPath.end();
            this.visited.add(currentPath.end());
            currentPath.setStepsLeft(0);
        }
        else{
            //System.out.println("Traversing path not done");
            int stepsLeftOnPath = this.currentPath.stepsLeft() - steps;
            //System.out.println("stepsLeftOnPath " + stepsLeftOnPath);
            this.currentPath.setStepsLeft(stepsLeftOnPath);
            this.currentPath.updatePositionOnPath();
            this.currentPath.updateDistanceNextNode();
            this.current = this.currentPath.current();
            if(!this.visited.contains(this.current)){
                this.visited.add(this.current);
            }
            this.extraSteps = 0;
        }
        this.checkExit();
    }

    public void newPath(){
        if(this.deadEnd(this.current)){
            int currentIndex = this.visited.indexOf(current);
            Node previous = this.visited.get(currentIndex -1);
            this.currentPath = new Path(this.current, previous);
            this.currentPath.findLength();
        }
        else if(this.checkExitInRange()){
            this.currentPath = this.pathToNearestExit();
            this.currentPath.findLength();
            this.currentPath.setDone(false);
        }
        else{
            Node destination = this.firstUnvisited(this.current);  
            this.currentPath = new Path(this.current, destination);
            this.currentPath.findLength();
        }
    }

    public Path pathToNearestExit(){
        HashMap<Node, Path> allPaths = this.graph.shortestPaths(this.current);
        Path result = allPaths.get(this.exitNodesInRange.get(0));
        for(Node node: exitNodesInRange){
            if(allPaths.get(node).length() < result.length()){
                result = allPaths.get(node);
            }
        }
        return result;
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
        if(!this.exitMaze){
            System.out.println("Current " + this.current.getNumber());
            System.out.print("Path ");
            this.currentPath.print();}
        else{
            System.out.println("Exited");
        }
    }
}
