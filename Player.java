import java.util.*;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Player implements Comparable
{
    protected Graph graph;
    protected Node current;
    protected ArrayList<Node> visited;
    protected ArrayList<Node> exitNodesInRange;
    protected Path currentPath;
    protected int extraSteps;
    protected boolean exitMaze;
    protected boolean onExitPath;
    protected Dice dice;
    protected int totalRounds;
    protected int totalSteps;

    public Player(Node startingNode, Graph graph){
        this.graph = graph;
        this.current = startingNode;
        this.visited = new ArrayList<Node>();
        this.exitNodesInRange = new ArrayList<Node>();
        this.visited.add(current);
        this.current.print();
        this.exitMaze = false;
        this.onExitPath = false;
    }

    public Player(int startingNode,Graph graph){
        this.graph = graph;
        this.current = this.graph.getNodes().get(startingNode);
        this.visited = new ArrayList<Node>();
        this.exitNodesInRange = new ArrayList<Node>();
        this.visited.add(current);
        this.current.print();
        this.exitMaze = false;
    }

    public void setDice(Dice dice) {this.dice = dice;}

    public void oneStep(){
        if(!this.checkExit()){
            this.totalRounds++;
            int stepsToTake = this.dice.rollDice();
            System.out.println(stepsToTake);
            this.totalSteps += stepsToTake;
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
        }
        this.printPosition();

    }

    public void newPath(){
        if(this.deadEnd(this.current)){
            int currentIndex = this.visited.indexOf(current);
            Node previous = this.visited.get(currentIndex -1);
            this.currentPath = new Path(this.current, previous);
            this.currentPath.findLength();
            this.currentPath.updateDistanceNextNode();
        }
        else if(this.checkExitInRange()){
            System.out.println("Round 3");
            this.currentPath = this.pathToNearestExit();
            this.currentPath.findLength();
            this.currentPath.setDone(false);
            this.currentPath.updateDistanceNextNode();
        }
        else{
            System.out.println("Checking");
            this.updateNewPath();
        }
    }

    public void traversePath(int steps){
        if(steps >= this.currentPath.stepsLeft()){
            this.extraSteps = steps - this.currentPath.stepsLeft();
            for(Node toAdd: currentPath.pathAfterCurrent()){
                this.visited.add(toAdd);
            }
            this.current = this.currentPath.end();
            this.currentPath.setStepsLeft(0);
            this.currentPath.setDone(true);
        }
        else{
            int stepsLeftOnPath = this.currentPath.stepsLeft() - steps;
            this.currentPath.setStepsLeft(stepsLeftOnPath);
            this.currentPath.updatePositionOnPath();
            this.currentPath.updateDistanceNextNode();
            if(this.current.getNumber() != this.currentPath.current().getNumber()){
                System.out.println("Checking RUNING");
                this.visited.add(this.currentPath.current());
                this.current = this.currentPath.current();
                if(checkExitInRange()){
                    this.extraSteps = steps - this.currentPath.lengthToPrevNode();
                    return;
                }

            }
           
            this.extraSteps = 0;
        }
        this.checkExit();
    }

    public boolean deadEnd(Node node){
        for(Node neighbor: node.getNeighbors()){
            if(!this.visited.contains(neighbor)){
                return false;
            }
        }
        return true;
    }

    public boolean exit(){ return this.exitMaze;}

    public abstract Node findNext(Node node);

    public abstract void updateNewPath();

    public boolean checkExit(){
        if(this.graph.getExitNodes().values().contains(this.current)){
            this.exitMaze = true;
            return true;
        }
        return false;
    }

    public boolean checkExitInRange(){
        HashMap<Integer,Node> nodesWithinLimit = this.graph.withinLimit(current);

        for(Node node: nodesWithinLimit.values()){
            if(this.graph.getExitNodes().containsValue(node)){
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

    public void printPosition(){
        System.out.println();
        if(!this.exitMaze){
            System.out.println("Current Node " + this.current.getNumber());
            System.out.println("Number of nodes visited " + this.visited.size());
            System.out.println(this.visited);
            System.out.println("Total steps: " + this.totalSteps);
            this.currentPath.print();
        }
        else{
            System.out.println(this + " Exited Maze");
            System.out.println("Number of nodes visited " + this.visited.size());
            System.out.println(this.visited);
            System.out.println("Total steps: " + (this.totalSteps-this.extraSteps));
            System.out.println("Total rounds: " + this.totalRounds);
        }
        System.out.println("------");
        System.out.println();
    }

    public int compareTo(Object o){
        Player p = (Player) o;
        if(this.totalRounds != p.totalRounds){
            Integer thisCount = (Integer) this.totalRounds;
            Integer pCount = (Integer) p.totalRounds;
            return thisCount.compareTo(pCount);
        }
        Integer stepCountThis = (Integer) (this.totalSteps - this.extraSteps);
        Integer stepCountP = (Integer) (p.totalSteps - p.extraSteps);
        if(stepCountThis != stepCountP){
            return stepCountThis.compareTo(stepCountP);
        }
        Integer thisCount = this.visited.size();
        Integer pCount = p.visited.size();
        return thisCount.compareTo(pCount);
    }

    public abstract String toString();

}