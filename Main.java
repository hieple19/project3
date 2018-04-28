
public class Main{
    public static void main(String[] args){
        Graph graph = new Graph("example.txt");
        graph.print();
        //PlayerFirst player1 = new PlayerFirst(3,graph);
        System.out.println(graph.getEdge(1,3));
        graph.shortestPath(2,2).printPath();
        graph.shortestPath(2,2).print();
        //player1.oneStep();

    }
}