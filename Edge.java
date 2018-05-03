
public class Edge{
    private Integer weight;
    private Node end;
    
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