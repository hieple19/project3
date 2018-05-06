import java.util.*;
/**
 * Class holds information about the abstract parent class player. 
 *
 * @author Hiep Le
 * @version 05/04/2018
 */
public abstract class Player implements Comparable<Player>
{   
    /**
     * Class has instance variables that keep track of 
     * the last/current node the player is on, the list of nodes it has
     * visited and the exit nodes in range. The number of steps the player has taken
     * and the extra steps for every round is also recorded.
     */
    protected Graph graph;

    protected Node current;
    protected ArrayList<Node> visited;
    protected ArrayList<Node> exitNodesInRange;

    protected Path currentPath;

    protected int extraSteps;

    protected boolean exitMaze;

    protected Dice dice;

    protected int totalRounds;
    protected int totalSteps;

    /**
     * Constructor takes in the starting node and the graph 
     * that the player will traverse on
     * @param Node starting
     * @param Graph 
     */
    public Player(Node startingNode, Graph graph){
        this.graph = graph;
        this.current = startingNode;
        this.visited = new ArrayList<Node>();
        this.exitNodesInRange = new ArrayList<Node>();
        this.visited.add(current);
        this.exitMaze = false;
    }

    /**
     * Similar constructor but takes in the starting node's integer values instead
     */
    public Player(int startingNode,Graph graph){
        this.graph = graph;
        this.current = this.graph.getNodes().get(startingNode);
        this.visited = new ArrayList<Node>();
        this.exitNodesInRange = new ArrayList<Node>();
        this.visited.add(current);
        this.exitMaze = false;
    }

    public void setDice(Dice dice) {this.dice = dice;}

    public boolean exit(){ return this.exitMaze;}

    public void addVisited(Node node) {this.visited.add(node);}

    public void setCurrent(Node node) {this.current = node;}

    /**
     * An abstract method that finds the next node to head towards
     * Will be implemented in child classes
     */
    public abstract Node findNext();

    /**
     * An abstract method that finds a new path and update the path for 
     * the player
     */
    public abstract void updateNewPath();

    /**
     * Method checks if the player is currently at an exit node
     */
    public boolean checkExit(){
        if(this.graph.getExitNodes().values().contains(this.current)){
            this.exitMaze = true;
            return true;
        }
        return false;
    }

    /**
     * Method checks if the player is currently at a node where it has visited
     * all of the node's neighbors - a dead end
     */
    public boolean deadEnd(){
        for(Node neighbor: this.current.getNeighbors()){
            if(!this.visited.contains(neighbor)){
                return false;
            }
        }
        return true;
    }

    /**
     * Method checks if the player is within range of an exit node
     * i.e. the exit node distance to the player is within the limit 
     * of the program
     */
    public boolean checkExitInRange(){
        HashMap<Integer,Node> nodesWithinLimit = this.graph.withinLimit(this.current);

        /*
         *  Check if the exit node is within the limit and the 
         *  list of exit nodes does not already contain the exit node in range
         */
        for(Node node: nodesWithinLimit.values()){
            if(this.graph.getExitNodes().containsValue(node)){
                if(!this.exitNodesInRange.contains(node)){
                    this.exitNodesInRange.add(node);      
                }
            }
        }

        if(this.exitNodesInRange.size() == 0){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Method returns the path to the nearest exit of the player
     */
    public Path pathToNearestExit(){
        if(this.exitNodesInRange.size() > 0){
            // Shortest paths to all nodes from the player
            HashMap<Node, Path> allPaths = this.graph.shortestPaths(this.current);

            // Set result to first path found
            Path result = allPaths.get(this.exitNodesInRange.get(0));

            // Update result if there is any nearer node
            for(Node node: exitNodesInRange){
                if(allPaths.get(node).length() < result.length()){
                    result = allPaths.get(node);
                }
            }
            return result;
        }
        return null;
    }

    /**
     * Method represents one turn of the player
     */
    public void oneStep(){
        if(!this.checkExit()){
            this.totalRounds++; // Increase number of turns         
            int stepsToTake = this.dice.rollDice(); // Number of steps to take from rolling dice
            this.totalSteps += stepsToTake; // Total steps add number of steps to take
            this.traversePath(stepsToTake); // Traverse the corresponding number of steps

            // If there are extra steps and player has not exited maze
            while(this.extraSteps > 0 && !this.exitMaze){
                this.newPath(); // Find a new path and traverse with the extra steps
                this.traversePath(this.extraSteps);

            }

            // If player has not exited maze and the next path is done with the extra steps
            // Find a new path
            if(!this.exitMaze && this.currentPath.done()){
                this.newPath();
            }
        }
        //this.printPosition();

    }

    /**
     * Method provides a traversing mechanism for the players
     */
    public void traversePath(int steps){
        if(steps >= this.currentPath.stepsLeft()){
            // If the number of steps is greater than number of steps left
            // Path is done, update the extra steps number
            this.extraSteps = steps - this.currentPath.stepsLeft();

            // Add the nodes on the path after the current node to visited list
            for(Node toAdd: currentPath.routeAfterNode(this.current)){
                this.visited.add(toAdd);
            }
            this.current = this.currentPath.end(); // Update current position
            this.currentPath.setStepsLeft(0);      // Update steps left to 0
            this.currentPath.setDone(true);
        }
        else{
            // If the number of steps do not cover all steps left 
            // Update steps left and position on path
            int stepsLeftOnPath = this.currentPath.stepsLeft() - steps;
            this.currentPath.setStepsLeft(stepsLeftOnPath);
            this.currentPath.updatePositionOnPath();
            this.currentPath.updateDistanceNextNode();

            // Update visited list and checks 
            if(this.current.getNumber() != this.currentPath.current().getNumber()){

                // Move through route of path and add nodes passed through
                // End when reach current position on path

                this.visited.add(this.currentPath.current());
                this.current = this.currentPath.current();

                /*
                 * Once we reach a different node, check for exit nodes
                 * If exit node is in range, return to create new path to exit
                 */
                if(checkExitInRange()){
                    // Extra steps is equal to length to 
                    this.extraSteps = this.currentPath.getLengthTravelled() - this.currentPath.lengthToCurrentNode();
                    return;
                }
            }

            this.extraSteps = 0;    // Reset extra steps
        }
        this.checkExit();           // Check if exit node is within limit
    }

    /**
     * Method finds a new path for the player
     */
    public void newPath(){
        if(this.deadEnd()){
            // If the player is at a dead end - it has visited all of 
            // the nodes' neighbors
            int currentIndex = this.visited.indexOf(current);
            Node previous = this.visited.get(currentIndex -1);
            this.currentPath = new Path(this.current, previous);
            this.currentPath.findLength();
            this.currentPath.updateDistanceNextNode(); // Update distance to the next node of path's route
        }
        else if(this.checkExitInRange()){
            // If exit nodes are in range
            this.currentPath = this.pathToNearestExit(); // Set path to path to nearest node
            this.currentPath.findLength();
            this.currentPath.setDone(false);    
            this.currentPath.updateDistanceNextNode(); // Update distance to next node of path's route
        }
        else{
            this.updateNewPath();       // Find a completely new path using an algorithm
        }
    }

    /**
     * Method prints important information about the player
     * like current node, number of nodes visited and number of steps
     */
    public void printPosition(){
        System.out.println();
        if(!this.exitMaze){
            System.out.println(this);
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

    /**
     * Method compareTo provides a basis for comparing two players
     * Firstly, by number of rounds needed to exit maze. Then by
     * number of steps and lastky by number of nodes visited.
     */
    public int compareTo(Player p){
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