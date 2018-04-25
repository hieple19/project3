import java.util.*;

public class Node{
    private int number;
    private ArrayList<Edge> edges;

    public Node(int number){
        this.number = number;
        this.edges = new ArrayList<Edge>();
    }

    public ArrayList<Edge> edges() { return this.edges;}

    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public int getNumber() { return this.number;}

    public boolean hasEdge(Node k){
        for(int i = 0; i<edges.size(); i++){
            Edge edge = edges.get(i);
            if(edge.end() == k){
                return true;
            }
        }
        return false;
    }

    public Edge getEdge(Node k){
        for(int i = 0; i<edges.size(); i++){
            Edge edge = edges.get(i);
            if(edge.end() == k){
                return edge;
            }
        }
        return null;
    }

    public ArrayList<Node> getNeighbors(){
        ArrayList<Node> result = new ArrayList<Node>();
        for(Edge edge: edges){
            result.add(edge.end());
        }
        return result;
    }

    public String toString(){
       return ""+ this.number;
    }

    public void print(){
        String res = "" + this.number;
        for(Edge edge: edges){
            res += " " + edge.toString() + " ";
        }
        System.out.println(res.trim());
    }

    public int compareTo(Node k){
        Integer thisValue = this.number;
        Integer otherValue = k.getNumber();
        return thisValue.compareTo(otherValue);
    }
}