import java.util.*;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Player
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
        this.current.print();
        this.exitMaze = false;
    }

    public Player(int startingNode,Graph graph){
        this.graph = graph;
        this.current = this.graph.find(startingNode);
        this.visited = new ArrayList<Node>();
        this.exitNodesInRange = new ArrayList<Node>();
        this.visited.add(current);
        this.current.print();
        this.exitMaze = false;
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
        if(currentPath.checkDone(steps)){
            if(steps>currentPath.stepsLeft()){
                this.extraSteps = steps - currentPath.stepsLeft();
            }
            this.current = this.currentPath.end();
            this.visited.add(currentPath.end());
            currentPath.setStepsLeft(0);
        }
        else{
            int stepsLeftOnPath = this.currentPath.stepsLeft() - steps;
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

    public abstract Node findNext(Node node);

    public abstract void updateNewPath();

    public boolean checkExit(){
        if(this.graph.getExitNodes().contains(this.current)){
            this.exitMaze = true;
            return true;
        }
        return false;
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
        System.out.println("Current Position");
        if(!this.exitMaze){
            System.out.println("Current Node " + this.current.getNumber());
            System.out.println("Visited " + this.visited);
            this.currentPath.print();
        }
        else{
            System.out.println("Exited Maze");
        }
        System.out.println("------");
        System.out.println();
    }

}
