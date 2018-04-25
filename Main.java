
public class Main{
    public static void main(String[] args){
        Graph graph = new Graph("example.txt");
        graph.print();
        PlayerFirst player1 = new PlayerFirst(3,graph);
        player1.oneStep();
        player1.oneStep();
        player1.oneStep();
        player1.oneStep();
        player1.oneStep();
    }
}