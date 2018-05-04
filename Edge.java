
/**
 * Class contains information about an edge.
 *
 * @ Hiep Le
 * @ 05/03/2018
 */
public class Edge{
    /**
     * Class contains information about the edge's weight and end
     */
    private Integer weight;
    private Node end;
    
    /** 
     * Constructor takes in the end node and weight
     */
    public Edge(Node end, Integer weight){
        this.end = end;
        this.weight = weight;
    }

    public Node end() { return this.end;}

    public Integer weight() {return this.weight;}

    public void setWeight(Integer weight) {this.weight = weight;}

    public String toString(){
        return this.end.getNumber() + "|" + this.weight;
    }

}