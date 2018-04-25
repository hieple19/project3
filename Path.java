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

    public void print(){
        System.out.print(start.getNumber() + " to " + end.getNumber());
        System.out.print(" Length " + this.length);
        System.out.print(" StepsLeft " + this.stepsLeft + ", status " + this.done);
        System.out.println();
    }

    public Path(Node start, Node end){
        this.start = start;
        this.end = end;
        this.route = new ArrayList<Node>();
        this.route.add(start);
        this.route.add(end);
    }

    public void addNode(Node node){
        this.route.add(1,node);
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

    public int stepsLeft() { return this.stepsLeft;}

    public boolean done() { return this.done;}

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
}
