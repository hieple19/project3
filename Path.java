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


    public Path(Node start, Node end){
        this.start = start;
        this.end = end;
        this.route = new ArrayList<Node>();
        this.route.add(start);
        //if(start.getNumber() != end.getNumber()){
            //this.route.add(end);}
        this.done = false;
    }

    public ArrayList<Node> route() { return this.route;}

    public void updatePath(Path newPath){
        this.route.clear();
        for(Node node: newPath.route()){
            this.route.add(node);
        }
        this.length = newPath.length();
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

    public void printPath(){
        System.out.println("Path: ");
        for(Node node: route){
            System.out.print(node.getNumber() + " ");
        }
        System.out.println();
    }

    public void print(){
        System.out.print(start.getNumber() + " to " + end.getNumber());
        System.out.print(" Length " + this.length);
        System.out.print(" StepsLeft " + this.stepsLeft + ", status " + this.done);
        System.out.println();
    }

}
