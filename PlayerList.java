import java.util.*;

public class PlayerList{
    private ArrayList<Player> list;
    private TreeSet<Player> exitedList;
    private int roundCount;

    public PlayerList(int noTypeFirst, int noTypeRandom, int noTypeShortest,Graph graph){
        this.list = new ArrayList<Player>();
        this.exitedList = new TreeSet<Player>();
        this.roundCount = 1;
        for(int i = 0; i<noTypeFirst; i++){
            PlayerFirst newPlayer = new PlayerFirst(graph.getStartingNode(), graph);
            this.list.add(newPlayer);
        }
        for(int i = 0; i<noTypeRandom; i++){
            PlayerRandom newPlayer = new PlayerRandom(graph.getStartingNode(), graph);
            this.list.add(newPlayer);
        }
        for(int i = 0; i<noTypeShortest; i++){
            PlayerShortest newPlayer = new PlayerShortest(graph.getStartingNode(), graph);
            this.list.add(newPlayer);
        }
    }

    public void oneStep(){
        System.out.println("Round " + this.roundCount);
        System.out.println();
        for(Player player: list){
            player.oneStep();
            if(player.exitMaze){
                exitedList.add(player);
            }
        }
        this.roundCount++;
    }

    public boolean checkAllExit(){
        for(Player player: list){
            if(!player.exit()){
                return false;
            }
        }
        return true;
    }

    public void skipToCompletion(){
        while(!this.checkAllExit()){
            this.oneStep();
        }
    }

    public void setDice(Dice dice){
        for(Player player: list){
            player.setDice(dice);
        }
    }

    public void printPosition(){
        for(Player player: list){
            if(this.exitedList.contains(player)){
                player.printPosition();
            }
        }
        this.printFinished();
    }

    public void printFinished(){
        int rank = 1;
        if(this.exitedList.size() != 0){
            System.out.println("List of players who successfully exited maze");
            System.out.println();
            for(Player player: exitedList){
                System.out.println("RANK " + rank);
                player.printPosition();
                rank++;
                System.out.println();
            }
        }
    }
}