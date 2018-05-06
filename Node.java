import java.util.*;

/**
 * Class contains information about a node.
 * This includes node's number and list of edges of the node.
 *
 * @ Hiep Le
 * @ 05/03/2018
 */

public class Node implements Comparable<Node>{
    /**
     * Class has instance variables to keep track of the node number
     * and the list of the edges of the node
     */
    private Integer number;
    private ArrayList<Edge> edges;

    /**
     * Constructor for a node takes in the node's number
     * @param Integer node number
     */
    public Node(Integer number){
        this.number = number;
        this.edges = new ArrayList<Edge>();
    }

    public Integer getNumber() { return this.number;}

    public ArrayList<Edge> edges() { return this.edges;}

    /**
     * Method adds an edge to the edge list of node
     * If an edge to the end is already present, the weight is changed to
     * that of the edge to be added.
     * @param Edge to be added
     */
    public void addEdge(Edge edgeToAdd){
        for(int i = 0; i<edges.size(); i++){
            Edge edge = edges.get(i);
            if(edge.end() == edgeToAdd.end()){
                edge.setWeight(edgeToAdd.weight()); // Change weight and return if edge is present
                return;
            }
        }
        this.edges.add(edgeToAdd);      // Add edge normally
    }

    /**
     * Method checks if the node is connected to a node 
     * by checking if there is an edge between two
     * 
     * @param Node to be checked
     * @return true if node is connected
     */
    public boolean hasEdge(Node k){
        for(int i = 0; i<edges.size(); i++){
            Edge edge = edges.get(i);
            if(edge.end() == k){
                return true;
            }
        }
        return false;
    }

    /**
     * Method returns the edge that connects this node to another
     * @param node 
     * @return edge that connects two node
     */
    public Edge getEdge(Node k){
        for(int i = 0; i<edges.size(); i++){
            Edge edge = edges.get(i);
            if(edge.end() == k){
                return edge;
            }
        }
        return null;
    }

    /**
     * Method returns a list of neighbors to this node 
     * by checking the end of every edge
     * @return ArrayList of neighbors
     */
    public ArrayList<Node> getNeighbors(){
        ArrayList<Node> result = new ArrayList<Node>();
        for(Edge edge: edges){
            result.add(edge.end());
        }
        return result;
    }
    
    /**
     * Method prints information about the node's value and edges   
     */
    public void print(){
        String res = "" + this.number;
        for(Edge edge: edges){
            res += " " + edge.toString() + " ";
        }
        System.out.println(res.trim());
    }
    
    /**
     * Method compareTo compares two node objects using their values
     */
    public int compareTo(Node k){
        Integer thisValue = this.number;
        Integer otherValue = k.getNumber();
        return thisValue.compareTo(otherValue);
    }

    public String toString(){
        return ""+ this.number;
    }

}