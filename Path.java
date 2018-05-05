import java.util.*;

/**
 * Class Path contains information about a particular path
 * and the route of the path as well as its distance
 *
 * @author Hiep Le
 * @version 05/04/2018
 */
public class Path
{   
    /**
     * Class has instance variables that keep track of the 
     * start, end and current nodes. It also has instance variable to
     * keep track of the route in the form of a list of nodes
     * There are other int variables that records the length of the path and
     * the number of steps left to traverse
     * 
     */
    private Node start;
    private Node end;
    private Node current;
    private ArrayList<Node> route;
    private int length;
    private int stepsLeft;
    private boolean done;
    private int distanceToNextNode;
    private int lengthTravelled;

    /**
     * Constructor takes in starting and end node of path
     */
    public Path(Node start, Node end){
        this.start = start;
        this.end = end;
        this.current = start;
        this.route = new ArrayList<Node>();
        this.route.add(start);
        if(start.getNumber() != end.getNumber()){
            this.route.add(end);}
        this.done = false;
        this.lengthTravelled = 0;
    }

    public ArrayList<Node> route() { return this.route;}

    public void setRoute(ArrayList<Node> route) {this.route = route;}

    public void addNode(Node node){ this.route.add(this.route.size()-1,node);}

    public Node current() { return this.current;}

    public void setCurrent(Node node) {this.current = node;}

    public int length() { return this.length;}

    public void setLength(int length) {this.length = length;}

    public int stepsLeft() { return this.stepsLeft;}

    public void setStepsLeft(int stepsLeft){this.stepsLeft = stepsLeft; }

    public int getLengthTravelled() {return this.lengthTravelled;}

    public void setLengthTravelled(int steps) { this.lengthTravelled = steps;}

    public int distanceToNextNode() {return this.distanceToNextNode;}

    public boolean done() {return this.done;}

    public void setDone(Boolean value){ this.done = value;}

    public Node start() {return this.start;}

    public Node end() { return this.end;}

    /**
     * Method returns the next node in the route
     */

    public Node nextNode(){
        if(this.route.size() > 1){
            int indexCurrent = this.route.indexOf(this.current);
            return this.route.get(indexCurrent +1);}
        return this.start;
    }

    /**
     * Method goes through the route to calculate length 
     * of path by summing the weights of all edges.
     */
    public void findLength(){
        int res = 0;
        for(int i=0; i<this.route.size() -1; i++){
            Node current = this.route.get(i);
            Node next = this.route.get(i+1);
            res += current.getEdge(next).weight();
        }
        this.length = res;
        this.stepsLeft = res;
    }

    /**
     * Method finds the total length needed to go the next node
     */
    public int totalLengthToNextNode(){
        int result = 0;
        int indexCurrent = this.route.indexOf(this.current);

        // If path has more than one node in route, work through the list to 
        // the node after current
        if(this.route.size() >1){
            for(int i = 0; i<=indexCurrent; i++){
                Node start = this.route.get(i);
                Node end = this.route.get(i+1);
                result += start.getEdge(end).weight();
            }
        }
        return result;
    }

    /**
     * Method finds length to the current node
     */
    public int lengthToCurrentNode(){
        int result = 0;
        int indexCurrent = this.route.indexOf(this.current);

        // If path has more than one node in route, work through the list to 
        // the node after current
        if(this.route.size() >1){
            for(int i = 0; i<indexCurrent; i++){
                Node start = this.route.get(i);
                Node end = this.route.get(i+1);
                result += start.getEdge(end).weight();
            }
        }
        return result;
    }

    /**
     * Method updates the distance to the next node in route
     * by calculating difference between length to next node and length travelled
     */
    public void updateDistanceNextNode(){
        //if(!this.current.equals(this.end)){
        this.distanceToNextNode = this.totalLengthToNextNode() - this.lengthTravelled;
        //}
        //else{
        //this.distanceToNextNode = this.stepsLeft;
        //}
    }

    /**
     * Method updates position of a player on path 
     * by updating the current node
     */
    public void updatePositionOnPath(){
        this.lengthTravelled = this.length - this.stepsLeft;

        // If length travelled is greater than total length to next node
        if(this.lengthTravelled >= this.totalLengthToNextNode()){
            int indexCurrent = this.route.indexOf(current);
            Node next = this.route.get(indexCurrent + 1);   // Set current to next node on route
            this.current = next;
        }

    }

    /**
     * Method returns a list of nodes on the path after current.
     * Used for updating players information in player class
     */
    public ArrayList<Node> routeAfterNode(Node node){
        ArrayList<Node> result = new ArrayList<Node>();
        int indexNode = this.route.indexOf(node);
        for(int i = indexNode+1; i<this.route.size(); i++){
            result.add(route.get(i));
        }
        return result;
    }

    /**
     * Method copies a path information to this path
     */
    public void updatePath(Path newPath){
        this.route.clear();
        for(Node node: newPath.route()){
            this.route.add(node);
        }
        this.length = newPath.length();
        this.stepsLeft = newPath.length();
    }

    /**
     * Method prints information about this path
     */
    public void print(){
        System.out.print("Current path: ");
        for(Node node: route){
            System.out.print(node.getNumber() + " ");
        }
        System.out.print(", Length: " + this.length);

        System.out.print(", Steps Left: " + this.stepsLeft + " \n");

        System.out.print("Distance to next node (Node " + this.nextNode().getNumber() + ") " + this.distanceToNextNode + " \n");
    }

}
