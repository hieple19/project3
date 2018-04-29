import java.util.*;

public class Controller{
    public static void main(String[] args){
        Graph graph = new Graph("example.txt","example2.txt");
        Game game = new Game(graph);
        graph.print();
        PlayerFirst player1 = new PlayerFirst(graph.getStartingNode(),graph);

        /*HashMap<Node, Path> allPaths = graph.shortestPaths(3);
        Set<Map.Entry<Node,Path>> entries = allPaths.entrySet();
        Iterator itr = entries.iterator();
        System.out.println();
        while(itr.hasNext()){
            Map.Entry<Node,Path> next = (Map.Entry<Node, Path>)itr.next();
            Node entry = (Node)next.getKey();
            Path path = (Path) next.getValue();
            entry.print();
            path.printPath();
            path.print();
            System.out.println();

        }*/
        player1.oneStep();
        player1.oneStep();

    }
}