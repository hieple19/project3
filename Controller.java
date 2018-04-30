import java.util.*;

public class Controller{
    public static void main(String[] args){
        Graph graph = new Graph("test.txt","example2.txt");
        Game game = new Game(graph);
        graph.print();
        System.out.println("Playing ");
        PlayerShortest player1 = new PlayerShortest(graph.getStartingNode(),graph);
        //PlayerRandom player1 = new PlayerRandom(graph.getStartingNode(),graph);
        //PlayerFirst player1 = new PlayerFirst(graph.getStartingNode(),graph);
        HashMap<Node, Path> allPaths = graph.shortestPaths(6);
        Set<Map.Entry<Node,Path>> entries = allPaths.entrySet();
        Iterator itr = entries.iterator();
        System.out.println();
        /*while(itr.hasNext()){
            Map.Entry<Node,Path> next = (Map.Entry<Node, Path>)itr.next();
            Node entry = (Node)next.getKey();
            Path path = (Path) next.getValue();
            entry.print();
            path.print();
            System.out.println();

        }*/
        System.out.println("PLAYERS");
        for(int i = 0; i<12; i++){
            System.out.println("STEP " + (i+1));
            player1.oneStep();
        }

    }
}