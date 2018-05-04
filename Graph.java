
import java.util.*;
import java.io.*;

/**
 * Class holds information about nodes and exit nodes in a graph,
 * as well as the 'limit' of the program
 * 
 * @author Hiep Le
 * @date 05/03/18
 */
public class Graph{
    /**
     * Class has two hashmaps used to keep track of the 
     * nodes and variables for the starting node and limit.
     */
    private HashMap<Integer,Node> nodes;
    private HashMap<Integer,Node> exitNodes;
    private Node startingNode;
    private int limit;

    /**
     * Default constructor which initializes two hashmaps
     */
    public Graph(){
        this.nodes = new HashMap<Integer,Node>();
        this.exitNodes = new HashMap<Integer,Node>();
    }

    /**
     * Constructor creates graph based on input files and limit value
     * @param limit
     * @param input files
     */
    public Graph(int limit, String nodeFile, String exitFile){
        this.limit = limit;
        this.nodes = new HashMap<Integer,Node>();
        this.exitNodes = new HashMap<Integer,Node>();
        File fileNode = new File(nodeFile);
        File fileExit = new File(exitFile);

        try{
            Scanner scanner1 = new Scanner(fileNode);

            //Read info about nodes and edges
            while(scanner1.hasNextLine()){
                String line = scanner1.nextLine();
                String[] elements = line.split(" ");
                Integer key1 = Integer.parseInt(elements[0]);
                Integer key2 = Integer.parseInt(elements[2]);
                Integer weight = Integer.parseInt(elements[3]);

                this.addNode(key1);             // Add nodes using the value as key to map
                this.addNode(key2);
                this.addEdge(key1,key2,weight); // Add edges
            }

            // Read info about starting and exit nodes
            Scanner scanner2 = new Scanner(fileExit);
            while(scanner2.hasNextLine()){
                String line = scanner2.nextLine();

                // Read starting nodes
                if(line.startsWith("start")){
                    String[]elements = line.split(" ");
                    Integer starting = Integer.parseInt(elements[1]);
                    this.startingNode = this.nodes.get(starting);
                }

                // Read exit nodes
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

    /**
     * Method finds the node with the shortest path/ nearest node to the node 
     * that is being processed in Dijkstra's algorithm 
     * @param map of all paths
     * @retun node 
     */
    public Node minNode(HashMap<Node, Path> allPaths){
        Node minNode = null; 

        // Find the first node with the path that's not done
        for(Map.Entry<Node,Path> entry: allPaths.entrySet()){
            if(!entry.getValue().done()){
                minNode = entry.getKey();
                break;
            }
        }

        // Find the node with the shortest path 
        for(Map.Entry<Node,Path> entry: allPaths.entrySet()){
            if(!entry.getValue().done()){
                if(entry.getValue().length() < allPaths.get(minNode).length()){
                    minNode = entry.getKey();
                    break;}
            }
        }

        return minNode;
    }

    /**
     * Method finds shortest paths to all nodes in graphfrom one source node
     * using Dijkstra's algorithms
     * @param Node
     * @return Map of shortest paths with Node as keys and Path as values
     */
    public HashMap<Node, Path> shortestPaths(Node start){
        HashMap<Node, Path> allPaths = new HashMap<Node, Path>();   //Result map

        ArrayList<Node> visited = new ArrayList<Node>();           

        //Initialize a path for all nodes in the graph 
        for(Node node: this.nodes.values()){
            Path newPath = new Path(start, node);
            if(node == start){
                newPath.setLength(0);   // Path from source itself is 0 length
            }
            else{
                newPath.setLength(Integer.MAX_VALUE); // Max length for other nodes
            }
            allPaths.put(node, newPath);    // Add all paths to result map
        }

        while(visited.size() < this.nodes.size()){      // Check for all nodes
            Node minNode = this.minNode(allPaths);      // Find the nearest node
            allPaths.get(minNode).setDone(true);        // Set path from source to nearest as done
            visited.add(minNode);

            ArrayList<Node> neighbors = minNode.getNeighbors();

            // Check all neighbors of min node
            for(int i = 0; i< neighbors.size(); i++){
                Node neighbor = neighbors.get(i);
                Path currentPath = allPaths.get(neighbor);  // Get current path to neighbor

                // New path is path to min node + edge from min node to neighbor
                int newPathLength = allPaths.get(minNode).length() + this.getEdge(minNode, neighbor).weight();

                // If new path is shorter then current path
                if(currentPath.length() > newPathLength){
                    currentPath.updatePath(allPaths.get(minNode));  // Update current path to be new path
                    currentPath.setLength(newPathLength);
                    currentPath.route().add(neighbor);
                }
                currentPath.findLength();
            }
        }
        return allPaths;
    }

    /**
     * Method checks for nodes within limit of a particular node
     * @param Node
     * @return HashMap of nodes within limit
     */
    public HashMap<Integer,Node> withinLimit(Node current){
        HashMap<Integer,Node> nodesWithinLimit = new HashMap<Integer,Node>();

        // Get shortest paths to all nodes in graphs
        HashMap<Node, Path> shortestPaths = this.shortestPaths(current);

        // Check for length of path and add all paths/ nodes with length lower than limit
        for(Map.Entry<Node,Path> entry: shortestPaths.entrySet()){
            if(entry.getValue().length() <= limit){
                Integer key = entry.getKey().getNumber();
                nodesWithinLimit.put(key,entry.getKey());
            }

        }
        return nodesWithinLimit;
    }

    /**
     * Similar to previous method but with an integer as parameter
     * @param Integer node value
     * @return HashMap of nodes within limit
     */
    public HashMap<Node,Path> shortestPaths(Integer key){
        Node start = this.nodes.get(key);
        return this.shortestPaths(start);
    }

    /**
     * Method finds the shortest path from one node to another
     * @param start node
     * @param end node
     * @return shortest path
     */
    public Path shortestPath(Node start, Node end){
        HashMap<Node, Path> allPaths = this.shortestPaths(start);
        return allPaths.get(end);
    }

    /**
     * Similar to previous method but with integers as parameter
     * @param Integer node value
     * @return shortest path
     */
    public Path shortestPath(Integer start, Integer end){
        Node startNode = this.nodes.get(start);
        Node endNode = this.nodes.get(end);
        return shortestPath(startNode, endNode);
    }

    /**
     * Method adds a node to map of nodes, with the node value as key 
     * @param  key
     * @return true if node is not already present
     */
    public boolean addNode(Integer key){
        if(this.nodes.containsKey(key)){
            return false;
        }
        Node newNode = new Node(key);
        this.nodes.put(key,newNode);
        return true;
    }

    /**
     * Method checks if node is present in map by 
     * checking the key 
     * @param key 
     * @return true if node is present
     */
    public boolean hasNode(Integer key){
        return this.nodes.containsKey(key);
    }

    /**
     * Method checks if an exit node is within limit of a node
     * @param node current
     * @return true if an exit node is within limit
     */

    public boolean exitWithinNode(Node current){
        HashMap<Integer,Node> nodesWithinLimit = this.withinLimit(current);
        for(Integer key: this.exitNodes.keySet()){
            if(nodesWithinLimit.containsKey(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * Method adds a weighted edge between two nodes.
     * If edge is already present, weight is changed to weight in add edge
     * @param key1
     * @return true if both nodes are present.
     */
    public boolean addEdge(Integer key1, Integer key2, Integer weight){
        if(this.hasNode(key1) && this.hasNode(key2)){   // Check that graph has both nodes
            Node node1 = this.nodes.get(key1);
            Node node2 = this.nodes.get(key2);          
            if(node1.hasEdge(node2)){
                node1.getEdge(node2).setWeight(weight); // Change weight of existing edge
                node2.getEdge(node1).setWeight(weight);
            }
            else{
                Edge edgeTo1 = new Edge(node1,weight);  // Add edge
                node2.addEdge(edgeTo1);
                Edge edgeTo2 = new Edge(node2,weight);
                node1.addEdge(edgeTo2);
            }
            return true;
        }
        return false;
    }

    /**
     * Method returns an edge betwteen two nodes
     * @param Node start and end
     */
     public Edge getEdge(Node start, Node end){
        if(start.hasEdge(end)){
            return start.getEdge(end);
        }
        return null;
    }
    
    /**
     * Similar method but takes integer as parameters
     */
    public Edge getEdge(Integer key1, Integer key2){
        Node start = this.nodes.get(key1);
        Node end = this.nodes.get(key2);
        return this.getEdge(start,end);
    }

   
    /*public ArrayList<Node> neighbors(Integer k){
        if(this.hasNode(k)){
            Node node = this.nodes.get(k);
            return node.getNeighbors();
        }
        return null;
    }*/
    
    /**
     * Method prints information about the graph such as all nodes and exit nodes
     */
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

    /**
     * Method returns an integer array of nodes values.
     * Used for testing purposes.
     */
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

