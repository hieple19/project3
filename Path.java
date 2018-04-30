import java.util.*;

/**
 * Write a description of class Path here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Path
{
    private Node start;
    private Node end;
    private ArrayList<Node> route;
    private int length;
    private int stepsLeft;
    private boolean done;
    private int distanceToNextNode;
    private int lengthTravelled;
    private Node current;

    public Path(Node start, Node end){
        this.start = start;
        this.end = end;
        this.current = start;
        this.route = new ArrayList<Node>();
        this.route.add(start);
        if(start.getNumber() != end.getNumber()){
            this.route.add(end);}
        this.done = false;
    }

    public ArrayList<Node> route() { return this.route;}

    public Node current() { return this.current;}

    public void updatePath(Path newPath){
        this.route.clear();
        for(Node node: newPath.route()){
            this.route.add(node);
        }
        this.length = newPath.length();
        this.stepsLeft = newPath.length();
        //this.findLength();
    }

    public void updatePositionOnPath(){
        this.lengthTravelled = this.length - this.stepsLeft;
        if(this.lengthTravelled >= this.lengthToNextNode()){
            int indexCurrent = this.route.indexOf(current);
            Node next = this.route.get(indexCurrent + 1);
            this.current = next;
        }

    }

    public int lengthToNextNode(){
        int result = 0;
        int indexCurrent = this.route.indexOf(this.current);

        for(int i = 0; i<=indexCurrent; i++){
            Node start = this.route.get(i);
            Node end = this.route.get(i+1);
            result += start.getEdge(end).weight();
        }
        return result;
    }

    public Node nextNode(){
        if(this.route.size() > 1){
            int indexCurrent = this.route.indexOf(this.current);
            return this.route.get(indexCurrent +1);}
        return this.start;
    }

    public void updateDistanceNextNode(){
        /*if(!this.current.equals(this.end)){
        int indexCurrent = this.route.indexOf(current);
        System.out.println(this.current);
        System.out.println(indexCurrent);
        Node next = this.route.get(indexCurrent + 1);
        int currentEdge = this.current.getEdge(next).weight();
        int distanceTravelledCurrentEdge = this.lengthTravelled - this.lengthToCurrentNode();
        this.distanceToNextNode = currentEdge - distanceTravelledCurrentEdge;
        }*/
        if(!this.current.equals(this.end)){
            this.distanceToNextNode = this.lengthToNextNode() - this.lengthTravelled;
        }
        else{
            this.distanceToNextNode = this.stepsLeft;
            System.out.println("Running");
        }
    }

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

    public int length() { return this.length;}

    public void setLength(int length) {this.length = length;}

    public int stepsLeft() { return this.stepsLeft;}

    public boolean done() { return this.done;}

    public void setDone(Boolean value){
        this.done = value;
    }

    public Node start() {return this.start;}

    public Node end() { return this.end;}

    public boolean checkDone(int steps){
        int afterTraversal = stepsLeft - steps;
        if(afterTraversal<= 0){
            this.done = true;
            return true;
        }
        return false;
    }

    public void setStepsLeft(int stepsLeft){
        this.stepsLeft = stepsLeft;
    }

    public void print(){
        System.out.print("Path: ");
        for(Node node: route){
            System.out.print(node.getNumber() + " ");
        }
        System.out.print(", Length: " + this.length);

        System.out.print(", Steps Left: " + this.stepsLeft + ", done? " + this.done + " \n");

        System.out.print("Distance to next node (Node " + this.nextNode().getNumber() + ") " + this.distanceToNextNode + " \n");
    }

}
