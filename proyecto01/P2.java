import java.util.*; 

public class P2 {
    public static void main(String[] args) {
	Case ca;
	Case[] cases = Reader01.processInput();
	for (int c = 0; c < cases.length; c++) {
	    ca = cases[c];
	    for (int i = 0; i < ca.queries.length; i++) {
		
		if (ca.queries[i].equals("Q")) {
		    ca.graph.calculateConnectedComponentSize();
    		    System.out.println(ca.graph.calculateDisconnectedOffices());
		} else {
		    int idEdge = Integer.parseInt(ca.queries[i].split(" ")[1]);
		    ca.graph.nComponents += 1;
		    ca.graph.removeEdge(idEdge);
		}
	    }
	    System.out.println();
	}
    }
}

// Contains the given data for each case
class Case {
    Graph graph;
    String[] queries;

    public Case(Vertex[] vertices, ArrayList<Edge> edges, String[] queries) {
	this.graph = new Graph(vertices, edges);
	this.queries = queries;
    }
}

class Reader01 {    
    public static Case[] processInput() {
	int nCases, nVertex, src, dst, numQueries, idEdge;
	int nComp = 1;
	String q;
	String[] queries;
	Vertex[] vertices;
	ArrayList<Edge> edges = new ArrayList<Edge>();
	Scanner scan = new Scanner(System.in);
	Case[] cases;
	nCases = scan.nextInt();
	cases = new Case[nCases];
	for (int i = 0; i < nCases; i++) {
	    nVertex = scan.nextInt();
	    vertices = new Vertex[nVertex];
	    for (int j = 0; j < nVertex; j++) {
		vertices[j] = new Vertex(j+1);
	    }
	    for (int j = 1; j < nVertex; j++) {
		src = scan.nextInt();
		dst = scan.nextInt();
		edges.add(new Edge(j, src,dst));
		vertices[src-1].sons.add(vertices[dst-1]);
		vertices[dst-1].father = vertices[src-1];
	    }
	    vertices[0].numSons = Vertex.calcNumSons(vertices[0]);
	    numQueries = scan.nextInt();
	    scan.nextLine();
	    queries = new String[numQueries];
    	    for (int j = 0; j < numQueries; j++) {
    		queries[j] = scan.nextLine();
    	    }
	    cases[i] = new Case(vertices, edges, queries);
	}
	scan.close();
	return cases;
    }
}


class Graph {
    public Vertex[] vertices;
    public ArrayList<Edge> edges;
    public int nComponents;

    public Graph(Vertex[] vertices, ArrayList<Edge> edges) {
	this.vertices = vertices;
	this.edges = edges;
	nComponents = 1;
    }
    
    public String toString() {
	String graph = "";
	for (int i = 0; i < vertices.length; i++) {
	    graph += vertices[i] + "\n";
	}
	for (int i = 0; i < edges.size(); i++) {
	    graph += edges.get(i) + "\n";
	}
	return graph;
    }

    public void removeEdge(int idEdge) {
	int i = 0;
	Edge edge;
	while (i < edges.size() && edges.get(i).id != idEdge) {
	    i += 1;
	}
	edge = edges.get(i);
	i = 0;
	Vertex vertex = vertices[edge.src -1];
	while (vertex.sons.get(i).id != edge.dst) {
	    i += 1;
	}
	vertex.sons.remove(i);
	vertex = vertices[edge.dst-1];
	vertex.father = vertex;
    }

    public void calculateConnectedComponentSize() {
	int size = vertices.length;
	Vertex vertex;
	for (int i = 0; i < size; i++) {
	    vertex = vertices[i];
	    vertex.numSons = 1;
	    if (vertex.father.id == vertex.id) {
		vertex.numSons = Vertex.calcNumSons(vertex);
	    }
	}	
    }

    public int calculateDisconnectedOffices() {
	int i = 0;
	int j = 0;
	int total = 0;
	int[] sizeComp = new int[nComponents];
	if (nComponents != 1) {
	    while (j < nComponents && i < vertices.length) {
	    	if (vertices[i].id == (i + 1)) {
	    	    sizeComp[j] = vertices[i].numSons;
	    	    j += 1;
	    	}
		i += 1;
	    }
		
	    for (i = 0; i < nComponents; i++) {
		for (j = 0; j < nComponents; j++) {
		    if (i != j) {
			total += sizeComp[i] * sizeComp[j];
		    }
		}
	    }
	}
	return (total/2);
    }
}

class Vertex {
    public int id, color, numSons;
    public Vertex father;
    public ArrayList<Vertex> sons;
    
    public Vertex(int id) {
	this.id = id;
	this.numSons = 1;
	this.father = this;
	this.sons = new ArrayList<Vertex>();
    }
    
    public String toString() {
	return "Nodo #" + this.id;
    }

    public static int calcNumSons(Vertex vertex) {
	int size = vertex.sons.size();
	Vertex v;
	if (vertex.sons != null) {	    
	    for (int i = 0; i < size; i++) {
		v = vertex.sons.get(i);
		vertex.numSons += Vertex.calcNumSons(v);
		v.father = vertex;
	    }
	}
	return vertex.numSons;
    }
}

class Edge {
    public int id, src, dst;
    
    public Edge(int id, int src, int dst) {
	this.id = id;
	this.src = src;
	this.dst = dst;
    }
    
    public String toString() {
	return "(" + this.src + ", " + this.dst + ")";
    }   
}