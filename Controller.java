import java.util.*;

public class Controller{
    public static void main(String[] args){
        Graph graph = new Graph(10,"test.txt","example2.txt");
        graph.print();
        System.out.println("Playing ");
        Dice dice = new Dice(6);
        /*PlayerShortest player1 = new PlayerShortest(graph.getStartingNode(),graph);
        //PlayerRandom player1 = new PlayerRandom(graph.getStartingNode(),graph);
        //PlayerFirst player1 = new PlayerFirst(graph.getStartingNode(),graph);
        player1.setDice(dice);
        HashMap<Node, Path> allPaths = graph.shortestPaths(6);
        Set<Map.Entry<Node,Path>> entries = allPaths.entrySet();
        Iterator itr = entries.iterator();
        System.out.println();
        while(itr.hasNext()){
        Map.Entry<Node,Path> next = (Map.Entry<Node, Path>)itr.next();
        Node entry = (Node)next.getKey();
        Path path = (Path) next.getValue();
        entry.print();
        path.print();
        System.out.println();

        }*/
        /*System.out.println("PLAYERS");
        for(int i = 0; i<11; i++){
        System.out.println("STEP " + (i+1));
        player1.oneStep();
        }*/
        //Graph graph = new Graph(10,"test.txt","example2.txt");
        //Dice dice = new Dice(6, 123);
        //Dice dice = new Dice(6);
        PlayerList playerList = new PlayerList(1,1,1,graph);
        playerList.setDice(dice);

        Scanner scanner = new Scanner(System.in);
        while(true){
            if(playerList.checkAllExit()){
                break;
            }
            display();
            String line = scanner.nextLine().toLowerCase().trim();
            if(line.equals("i")){
                playerList.oneStep();
            }
            else if(line.equals("c")){
                playerList.skipToCompletion();
                break;
            }
            else if(line.equals("p")){
                playerList.printPosition();
            }
            else if(line.equals("x")){
                break;
            }
            else{
                System.out.println("Wrong command");
            }
        }
        System.out.println();
        System.out.println("Final Position");
        playerList.printPosition();

    }

    public static void display(){
        System.out.println("Instructions");
        System.out.println("Type 'i' to advance one round");
        System.out.println("Type 'x' to exit program");
        System.out.println("Type 'c' to continue to completion");
        System.out.println("Type 'p' print players' position");
    }
}