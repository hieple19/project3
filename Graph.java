
import java.util.*;
import java.io.*;

public class Graph{
    private ArrayList<Node> nodes;
    private ArrayList<Node> exitNodes;
    private Node startingNode;
    private int size;
    private int limit;

    public Graph(){
        this.nodes = new ArrayList<Node>();
        this.exitNodes = new ArrayList<Node>();
    }

    public Graph(int n){
        this.size = n;
        this.nodes = new ArrayList<Node>();
        this.exitNodes = new ArrayList<Node>();
    }

    public Graph(String nodeFile, String exitFile){
        this.nodes = new ArrayList<Node>();
        this.exitNodes = new ArrayList<Node>();
        File fileNode = new File(nodeFile);
        File fileExit = new File(exitFile);
        try{
            Scanner scanner1 = new Scanner(fileNode);
            while(scanner1.hasNextLine()){
                String line = scanner1.nextLine();
                String[] elements = line.split(" ");
                int key1 = Integer.parseInt(elements[0]);
                int key2 = Integer.parseInt(elements[2]);
                int weight = Integer.parseInt(elements[3]);

                this.addNode(key1);
                this.addNode(key2);
                this.addEdge(key1,key2,weight);
            }

            Scanner scanner2 = new Scanner(fileExit);
            String line = scanner2.nextLine();
            String[]elements = line.split(" ");
            int starting = Integer.parseInt(elements[1]);
            this.startingNode = this.find(starting);

            line = scanner2.nextLine();
            String[] exits = line.split(" ");
            for(int i = 1; i<exits.length; i++){
                Node exit = this.find(Integer.parseInt(exits[i]));
                this.exitNodes.add(exit);
            }

            scanner1.close();
            scanner2.close();
        }
        catch(IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public ArrayList<Node> getNodes(){        return this.nodes;    }
    
    public ArrayList<Node> getExitNodes(){        return this.exitNodes;    }
    
    public Node getStartingNode() { return this.startingNode;}

    public ArrayList<Node> withinLimit(Node current){
        ArrayList<Node> nodesWithinLimit = new ArrayList<Node>();
        HashMap<Node, Path> shortestPaths = this.shortestPaths(current);
        for(Map.Entry<Node,Path> entry: shortestPaths.entrySet()){
            if(entry.getValue().length() < limit){
                nodesWithinLimit.add(entry.getKey());
            }
        }
        return nodesWithinLimit;
    }

    public HashMap<Node,Path> shortestPaths(int key){
        Node start = this.find(key);
        return this.shortestPaths(start);
    }

    public HashMap<Node, Path> shortestPaths(Node start){
        HashMap<Node, Path> allPaths = new HashMap<Node, Path>();
        ArrayList<Node> visited = new ArrayList<Node>();

        for(int i = 0; i<this.nodes.size(); i++){
            Path newPath = new Path(start, nodes.get(i));
            if(this.nodes.get(i) == start){
                newPath.setLength(0);
            }
            else{
                newPath.setLength(Integer.MAX_VALUE);
            }
            allPaths.put(nodes.get(i), newPath);
        }

        while(visited.size() < this.nodes.size()){
            Node minNode = this.minNode(allPaths);
            allPaths.get(minNode).setDone(true);
            visited.add(minNode);
            ArrayList<Node> neighbors = minNode.getNeighbors();
            for(int i = 0; i< neighbors.size(); i++){
                Node neighbor = neighbors.get(i);
                Path currentPath = allPaths.get(neighbor);
                int newPathLength = allPaths.get(minNode).length() + this.getEdge(minNode, neighbor).weight();
                if(currentPath.length() > newPathLength){
                    currentPath.updatePath(allPaths.get(minNode));
                    currentPath.setLength(newPathLength);
                    currentPath.route().add(neighbor);
                }
            }
        }
        return allPaths;
    }

    public Path shortestPath(int start, int end){
        Node startNode = this.find(start);
        Node endNode = this.find(end);
        HashMap<Node, Path> allPaths = this.shortestPaths(start);
        return allPaths.get(endNode);
    }

    public Node minNode(HashMap<Node, Path> allPaths){
        Node minNode = null; 

        for(Map.Entry<Node,Path> entry: allPaths.entrySet()){
            if(!entry.getValue().done()){
                minNode = entry.getKey();
                break;
            }
        }
        for(Map.Entry<Node,Path> entry: allPaths.entrySet()){
            if(!entry.getValue().done()){
                if(entry.getValue().length() < allPaths.get(minNode).length()){
                    minNode = entry.getKey();
                    break;}
            }
        }

        return minNode;
    }

    public boolean addNode(int n){
        if(this.hasNode(n)){
            return false;
        }
        Node newNode = new Node(n);
        nodes.add(newNode);
        return true;
    }

    public boolean hasNode(int k){
        for(Node node: nodes){
            if(node.getNumber() == k){
                return true;
            }
        }
        return false;
    }

    public boolean exitWithinNode(Node current){
        ArrayList<Node> nodes = this.withinLimit(current);
        for(Node node: exitNodes){
            if(nodes.contains(node)){
                return true;
            }
        }
        return false;
    }

    public Node find(int k){
        for(Node node: nodes){
            if(node.getNumber() == k){
                return node;
            }
        }
        return null;
    }

    public boolean addEdge(int k1, int k2, int w){
        if(this.hasNode(k1) && this.hasNode(k2)){
            Node node1 = this.find(k1);
            Node node2 = this.find(k2);
            if(node1.hasEdge(node2)){
                node1.getEdge(node2).setWeight(w);
                node2.getEdge(node1).setWeight(w);
            }
            else{
                Edge edgeTo1 = new Edge(node1,w);
                node2.addEdge(edgeTo1);
                Edge edgeTo2 = new Edge(node2,w);
                node1.addEdge(edgeTo2);
            }
            return true;
        }
        return false;
    }

    public Edge getEdge(int k, int a){
        Node start = this.find(k);
        Node end = this.find(a);
        return this.getEdge(start,end);
    }

    public Edge getEdge(Node start, Node end){
        return start.getEdge(end);
    }

    public ArrayList<Node> neighbors(int k){
        if(this.hasNode(k)){
            Node node = this.find(k);
            return node.getNeighbors();
        }
        return null;
    }

    public void print(){
        for(Node node: nodes){
            node.print();
        }
        for(Node node: this.exitNodes){
            System.out.println("Exit Node " + node);
        }
        System.out.println("Node " + startingNode);       
    }

    public int[] nodesArray(){
        int[] result = new int[this.nodes.size()];
        for(int i = 0; i<result.length; i++){
            result[i] = this.nodes.get(i).getNumber();
        }
        return result;
    }
}

