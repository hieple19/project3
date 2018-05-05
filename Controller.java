import java.util.*;

/**
 * Class Controller takes in user's parameters and execute the game.
 *
 * @author HiepLe
 * @version 04/05/2018
 */
public class Controller{
    public static void main(String[] args){
        int limit = Integer.parseInt(args[0]);
        Graph graph = new Graph(limit,args[5],args[6]); // Read limit and files
        graph.print();
        int maxDice = Integer.parseInt(args[4]);
        Dice dice = new Dice(maxDice);                  // Set dice and max value

        int noPlayerFirst = Integer.parseInt(args[1]);
        int noPlayerRandom = Integer.parseInt(args[2]);
        int noPlayerShortest = Integer.parseInt(args[3]);   // Read number of players of each type

        PlayerList playerList = new PlayerList(noPlayerFirst,noPlayerRandom,noPlayerShortest,graph);
        playerList.setDice(dice);

        Scanner scanner = new Scanner(System.in);
        while(true){
            if(playerList.checkAllExit()){  // If all players have exit break loop
                break;
            }
            display();                       // Else display instructions and read input
            String line = scanner.nextLine().toLowerCase().trim();
            System.out.println();
            if(line.equals("i")){
                playerList.oneStep();        // Advance one round
            }
            else if(line.equals("c")){
                playerList.skipToCompletion();  // Skip to completion and break loop
                break;
            }
            else if(line.equals("p")){          // Print current position
                playerList.printPosition();
            }
            else if(line.equals("x")){          // Quit program
                break;
            }
            else{
                System.out.println("Wrong command");
            }
        }
        System.out.println();
        System.out.println("Final Position");   // Print final position when all done
        playerList.printPosition();

    }

    public static void display(){
        System.out.println();
        System.out.println("Instructions");
        System.out.println("Type 'i' to advance one round");
        System.out.println("Type 'x' to exit program");
        System.out.println("Type 'c' to continue to completion");
        System.out.println("Type 'p' print players' position");
    }
}