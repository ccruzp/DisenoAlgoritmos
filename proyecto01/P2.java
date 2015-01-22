import java.util.*; 

public class P2 {
    public static void main(String[] args) {
	int nCases, nVertex, src, dst, numQueries, idEdge;
	String query;
	Vertex[] vertices;
	ArrayList<Edge> edges = new ArrayList<Edge>();
	Graph graph;
	Scanner scan = new Scanner(System.in);
	// Case[] cases;
	nCases = scan.nextInt();
	// cases = new Case[nCases];
	nVertex = scan.nextInt();
	vertices = new Vertex[nVertex];
	for (int i = 0; i < nVertex; i++) {
	    vertices[i] = new Vertex(i+1);
	}
	for (int i = 1; i < nVertex; i++) {
	    src = scan.nextInt();
	    dst = scan.nextInt();
	    edges.add(new Edge(src,dst));
	    vertices[src-1].sons.add(vertices[dst-1]);
	    vertices[dst-1].father = vertices[src-1];
	}
	// for (int i = 0; i < nVertex; i++) {
	vertices[0].numSons = Vertex.calcNumSons(vertices[0]);
	// Vertex.calcNumSons(vertices[0]);
	// }
	// for (int i = 0; i < nVertex; i++) {
	//     System.out.println("El padre del nodo #" + (i+1) + " es: " + vertices[i].father);
	//     for (int j = 0; j < vertices[i].sons.size(); j++) {
	// 	System.out.println(vertices[i].sons.get(j));
	//     }
	// }
	// cases[0] = new Case(nVertex, edges);
	// for (int i = 0; i < nVertex; i++) {
	//     System.out.println("El nodo #" + (i+1) + " tiene: " + vertices[i].numSons + " hijos");
	// }
	graph = new Graph(vertices, edges);
	System.out.println(graph);
	numQueries = scan.nextInt();
	for (int i = 0; i < numQueries; i++) {
	    query = scan.next();
	    if (query.equals("Q")) {
	    	System.out.println("CALCULAR");
		graph.calculateConnectedComponentSize();
		for (int k = 0; k < graph.vertices.length; k++) {
		    System.out.println("El padre del nodo #" + (k+1) + " es: " + graph.vertices[k].father);
		    System.out.println("Num hijos: " + graph.vertices[k].sons.size());
		    for (int l = 0; l < graph.vertices[k].sons.size(); l++) {
			System.out.println("Hijo #" + l + " :" + graph.vertices[k].sons.get(l));
		    }
		}
		
	    } else {
	    	idEdge = scan.nextInt();
	    	System.out.println("REMOVER ARCO #" + idEdge);
		graph.removeEdge(idEdge);
		// for (int k = 0; k < graph.vertices.length; k++) {
		//     System.out.println("El padre del nodo #" + (k+1) + " es: " + graph.vertices[k].father);
		//     System.out.println("Num hijos: " + graph.vertices[k].sons.size());
		//     for (int l = 0; l < graph.vertices[k].sons.size(); l++) {
		// 	System.out.println("Hijo #" + l + " :" + graph.vertices[k].sons.get(l));
		//     }
		// }
	    }
	}
	    
	// scan.nextLine();
	// for (int i = 0; i < f; i++) {
	//     p = scan.nextLine();
	//     System.out.println(p);
	// }
	scan.close();
	System.out.println("¡Lo logré! ^*-*^");
    }
	
    // public static calculate(Vertex vertex) {
    // }
}

// class Reader01 {    
//     public static Case[] processInput() {
// 	int n, src, dst, act;
// 	// String ;
// 	Scanner scan = new Scanner(System.in);
// 	nCases = scan.nextInt();
// 	// For each case, get data
// 	for (int i = 0; i < nCases; i++) {
// 	    n = scan.nextInt();
// 	    System.out.println("Cantidad de nodos: " + n);
// 	    for (int j = 0; j < n-1; j++) {
// 		src = scan.nextInt();
// 		dst = scan.nextInt();
// 		System.out.println("ARCO #" + j + " (" + src + "," + dst + ")");
// 	    }
// 	    act = scan.nextInt();
// 	    for (int j = 0; j < act-1; j++) {
// 		p = scan.nextLine();
// 		System.out.println(p);
// 	    }
// 	}
	
// 	scan.close();
// 	return cases;
//     }
// }

// Contains the given data for each case
class Case {
    public int numVertex;
    public int[] fathers; 
    public int[] numChildren;
    public ArrayList<Edge> edges;
    
    public Case(int numVertex, ArrayList<Edge> edges) {
	this.numVertex = numVertex;
	System.out.println("NUMVERTEX: " + numVertex);
	fathers = new int[numVertex]; 
	// colors = new int[numVertex]; 
	numChildren = new int[numVertex];
	this.edges = edges;
	for (int i = 0; i < numVertex; i++) {
	    // this.colors[i] = 1;
	    this.numChildren[i] = 0;
	}
	this.fathers[0] = 0; 
	for (int i = 0; i < numVertex - 1; i++) {
	    Edge edge = this.edges.get(i);
	    this.fathers[edge.dst - 1] = edge.src - 1;
	    this.numChildren[edge.src-1] += 1;
	    // while (this.fathers[src-1] != ) {
		
		
		   
	    // }	    
	}
    }
}

class Graph {
    public Vertex[] vertices;
    public ArrayList<Edge> edges;

    public Graph(Vertex[] vertices, ArrayList<Edge> edges) {
	this.vertices = vertices;
	for (int i = 0; i < vertices.length; i++) {
	    System.out.println(this.vertices[i].numSons);
	}
	this.edges = edges;
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
	Edge edge = this.edges.get(idEdge-1);
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
	    if (vertex.father.id == (i+1)) {
		vertex.numSons = Vertex.calcNumSons(vertex);
	    }
	}	
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
	// int total = 1;
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

    // public static void removeEdge(int idEdge, ArrayList<Edge> edges) {
    // 	int i = 0;
    // 	int size;
    // 	Edge edge = edges.get(idEdge);
    // 	Vertex son;
    // 	Vertex vertex = vertices[edge.src-1];
    // 	size = vertex.sons.length;
	
    // 	while (i < size) {
    // 	    son = vertex.sons.get(i);
    // 	    if (son.id == edge.src) {
    // 		vertex.remove(son);
    // 		break;
    // 	    }
    // 	    i +=1;
    // 	}
    // }

}

class Edge {
    public int src, dst;
    
    public Edge(int src, int dst) {
	this.src = src;
	this.dst = dst;
    }
    
    public String toString() {
	return "(" + this.src + ", " + this.dst + ")";
    }   
}