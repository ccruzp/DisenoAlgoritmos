/**
 * Implements a simple undirected graph using
 * complete adjacency lists for each vertex
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimpleGraph {

    private int nVertex;
    private List<List<Integer>> edges;

    public SimpleGraph(int nVertex, OrderedEdge edges[] ) {
        this.nVertex = nVertex;
        // ArrayList is chosen for problem efficiency (fast for fixed-size creation and random access)
        this.edges = new ArrayList<List<Integer>>(nVertex+1);

        // Lists initialization
        for (int i=0; i<=nVertex; i++) {
            // LinkedList is chosen for problem efficiency (fast for adding and traversing)
            this.edges.add(new LinkedList<Integer>());
        }

        // Edge adding
        for (OrderedEdge edge : edges) {
            this.edges.get(edge.getSrc()).add(edge.getDst());
            this.edges.get(edge.getDst()).add(edge.getSrc());
        }
    }

    public int getNVertex() {
        return nVertex;
    }

    public List<Integer> getNeighbors(int vertex) {
        return edges.get(vertex);
    }
}