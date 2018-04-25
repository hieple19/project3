
public class Edge{
    private int weight;
    private Node end;
    
    public Edge(Node end, int weight){
        this.end = end;
        this.weight = weight;
    }
    
    public Node end() { return this.end;}
    public int weight() {return this.weight;}
    
    public void setWeight(int weight) {this.weight = weight;}
    
    public String toString(){
        return this.end.getNumber() + "|" + this.weight;
    }
}