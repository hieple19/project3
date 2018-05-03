
import java.util.*;
import java.io.*;

public class Graph{
    private HashMap<Integer,Node> nodes;
    private HashMap<Integer,Node> exitNodes;
    private Node startingNode;
    private int size;
    private int limit;

    public Graph(){
        this.nodes = new HashMap<Integer,Node>();
        this.exitNodes = new HashMap<Integer,Node>();
    }

    public Graph(int n){
        this.size = n;
        this.nodes = new HashMap<Integer,Node>();
        this.exitNodes = new HashMap<Integer,Node>();
        this.limit = 100;
    }

    public Graph(int limit, String nodeFile, String exitFile){
        this.limit = limit;
        this.nodes = new HashMap<Integer,Node>();
        this.exitNodes = new HashMap<Integer,Node>();
        File fileNode = new File(nodeFile);
        File fileExit = new File(exitFile);
        try{
            Scanner scanner1 = new Scanner(fileNode);
            while(scanner1.hasNextLine()){
                String line = scanner1.nextLine();
                String[] elements = line.split(" ");
                Integer key1 = Integer.parseInt(elements[0]);
                Integer key2 = Integer.parseInt(elements[2]);
                Integer weight = Integer.parseInt(elements[3]);

                this.addNode(key1);
                this.addNode(key2);
                this.addEdge(key1,key2,weight);
            }

            Scanner scanner2 = new Scanner(fileExit);
            while(scanner2.hasNextLine()){
                String line = scanner2.nextLine();
                if(line.startsWith("start")){
                    String[]elements = line.split(" ");
                    Integer starting = Integer.parseInt(elements[1]);
                    this.startingNode = this.nodes.get(starting);
                }
                if(line.startsWith("exit")){
                    String[] exits = line.split(" ");
                    for(int i = 1; i<exits.length; i++){
                        Integer nodeNumber = Integer.parseInt(exits[i]);
                        Node exit = this.nodes.get(nodeNumber);
                        this.exitNodes.put(nodeNumber, exit);
                    }
                }
            }
            scanner1.close();
            scanner2.close();
        }
        catch(IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public HashMap<Integer,Node> getNodes(){return this.nodes;    }

    public Node getNode(Integer key) {return this.nodes.get(key);}

    public HashMap<Integer,Node> getExitNodes(){return this.exitNodes;    }

    public Node getStartingNode() { return this.startingNode;}

    public HashMap<Integer,Node> withinLimit(Node current){
        HashMap<Integer,Node> nodesWithinLimit = new HashMap<Integer,Node>();
        HashMap<Node, Path> shortestPaths = this.shortestPaths(current);
        for(Map.Entry<Node,Path> entry: shortestPaths.entrySet()){
            if(entry.getValue().length() <= limit){
                Integer key = entry.getKey().getNumber();
                nodesWithinLimit.put(key,entry.getKey());
            }

        }
        return nodesWithinLimit;
    }

    public HashMap<Node,Path> shortestPaths(Integer key){
        Node start = this.nodes.get(key);
        return this.shortestPaths(start);
    }

    public HashMap<Node, Path> shortestPaths(Node start){
        HashMap<Node, Path> allPaths = new HashMap<Node, Path>();
        ArrayList<Node> visited = new ArrayList<Node>();

        for(Node node: this.nodes.values()){
            System.out.println(node);
            Path newPath = new Path(start, node);
            if(node == start){
                newPath.setLength(0);
            }
            else{
                newPath.setLength(Integer.MAX_VALUE);
            }
            allPaths.put(node, newPath);
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
                currentPath.findLength();
            }
        }
        return allPaths;
    }

    public Path shortestPath(Node start, Node end){
        HashMap<Node, Path> allPaths = this.shortestPaths(start);
        return allPaths.get(end);
    }

    public Path shortestPath(Integer start, Integer end){
        Node startNode = this.nodes.get(start);
        Node endNode = this.nodes.get(end);
        return shortestPath(startNode, endNode);
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

    public boolean addNode(Integer key){
        if(this.nodes.containsKey(key)){
            return false;
        }
        Node newNode = new Node(key);
        this.nodes.put(key,newNode);
        return true;
    }

    public boolean hasNode(Integer key){
        return this.nodes.containsKey(key);
    }

    public boolean exitWithinNode(Node current){
        HashMap<Integer,Node> nodesWithinLimit = this.withinLimit(current);
        for(Integer key: exitNodes.keySet()){
            if(nodesWithinLimit.containsKey(key)){
                return true;
            }
        }
        return false;
    }

    public boolean addEdge(Integer key1, Integer key2, Integer weight){
        if(this.hasNode(key1) && this.hasNode(key2)){
            Node node1 = this.nodes.get(key1);
            Node node2 = this.nodes.get(key2);
            if(node1.hasEdge(node2)){
                node1.getEdge(node2).setWeight(weight);
                node2.getEdge(node1).setWeight(weight);
            }
            else{
                Edge edgeTo1 = new Edge(node1,weight);
                node2.addEdge(edgeTo1);
                Edge edgeTo2 = new Edge(node2,weight);
                node1.addEdge(edgeTo2);
            }
            return true;
        }
        return false;
    }

    public Edge getEdge(Integer key1, Integer key2){
        Node start = this.nodes.get(key1);
        Node end = this.nodes.get(key2);
        return this.getEdge(start,end);
    }

    public Edge getEdge(Node start, Node end){
        return start.getEdge(end);
    }

    public ArrayList<Node> neighbors(Integer k){
        if(this.hasNode(k)){
            Node node = this.nodes.get(k);
            return node.getNeighbors();
        }
        return null;
    }

    public void print(){
        for(Node node: this.nodes.values()){
            node.print();
        }
        for(Node node: this.exitNodes.values()){
            System.out.println("Exit Node " + node);
        }
        System.out.println("Node " + startingNode);      
        System.out.println("Limit " + this.limit);
    }

    public Integer[] nodesArray(){
        Integer[] results = new Integer[this.nodes.size()];
        int i = 0;
        for(Integer key: this.nodes.keySet()){
            results[i] = key;
            i++;
        }
        return results;
    }
}

