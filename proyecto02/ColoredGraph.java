/**
 * Implements a subclass of SimpleGraph capable
 * of solving the chromatic number problem.
 */

import java.util.TreeSet;
import java.util.Iterator;
import java.util.List;

public class ColoredGraph extends SimpleGraph {

    int x[];         //Colors chosen for each vertex
    TreeSet<Integer> labels;   // Brelaz labels
    
    int k;          // Actual coloring vertex
    int w;          // Dimension of initial clique
    int q;          // Best solution of the problem
    boolean back;   // Control variable (to detect backtracking? #!)

    int l;          // Numbers of colors used on the actual partial solution
    int u[];        // Numbers of colors used on the partial solutions
    TreeSet<Integer> U;      // Set that contains the colors available for a vertex

    public ColoredGraph(int nVertex, OrderedEdge edges[]) {
        super(nVertex, edges);
        x = new int[nVertex+1];
        labels = new TreeSet<Integer>();
        u = new int[nVertex+1];

        w = 0;
        q = getNVertex(); // TODO PENDING FOR CORRECTNESS

    }

    public void solveColoring() {

        // Initial clique solving
        solveInitialClique();

        // Initialization
        back = false;
        k = w + 1;
        // #! LABEL CLIQUE VERTICES


        // Main Cycle
        while (true) {

            // Expanding the actual partial solution
            if (!back) {
		u[k] = l; // Not sure if it's like this.
                U = determineU(k); // Set of posible colors for vertex k

            // We are returning so we must eliminate this group of partial solutions
            } else {
                U = determineU(k);
		U.remove(x[k]);// Removes actual color from the set of possible colors (I think it's like this).
                // TODO Remove label if it exists
            }

            // If color assignment is possible
            if (!U.isEmpty()) {
                // Color actual vertex with minimal color possible
                x[k] = U.pollFirst(); // TODO change for actual color

                // Increment actual colors used if that is the case TODO Correctness
                if (100 > l) ++l;

                // #! TODO Where u[k] assignment goes?

                // Go to next vertex
                k++;

                // All vertices have been colored, this is a new BETTER solution
                if (k > getNVertex()) {

                    // This new BETTER solution is the actual solution counter
                    q = l;

                    // If the best solution is the same as the
                    // initial clique size, there is no better solution to search
                    if (q == w) break;

                    // Set k to the minimal rank among the vertices with the last color
                    // TODO PENDING

                    // Remove all labels from vertices higher or equal than k
                    // TODO PENDING

                    // Go back to check other possible better solutions
                    back = true;
                } else {
                    // We have to go forward coloring pending vertices
                    back = false;
                }

            // Color assignment is not possible, search another group of solutions
            } else {
                back = true;
            }

            // We are going backwards (either recently found solution
            // or no possible following coloring solution)
            if (back) {
                // Call the label procedure on actual vertex
                label(k);

                // Go to highest of labeled vertices
                // TODO PENDING

                // If it is part of the clique, there is no point in still searching solutions
                if (k <= w) break;
            }
        }
    }
    
    private void solveInitialClique() {
        // TODO Get a better clique using Dsatur algorithm or equivalent
        w = 1;      // Clique size is 1
        x[1] = 1;   // Color of 1st element is 1
        l = 1;      // Actual colors is 1
        u[1] = 1;   // Colors of partial solution up to 1 is 1
    }
    
    private void label(int k) {
	
	List <Integer> adjacents = this.getNeighbors(k);
	int[] colores = new int[l];
	
	for (Integer i : adjacents) {
	    // Checks for nodes with smaller rank
	    if (i < k) {		
		int color = this.x[i];
		// Adds a color if it hasn't been added OR its new node has a lower rank
		if (colores[color] == 0 || colores[color] > i) {
		    colores[color] = i;
		}
		
	    } 
	}
	// Adds the final list to the label tree
	for (int i = 0; i <= l; i++) {
	    if (colores[i] != 0) {
		labels.add(i);
	    }
	}	
    }
    
    public TreeSet<Integer> determineU(int k) {
	TreeSet<Integer> U = new TreeSet<Integer>();
	int lastColor = Math.min(u[k]+1, q);
	// System.out.println("k: " + k);
	// System.out.println("u[k]: " + (u[k] +1));
	// System.out.println("Q: " + (q));
	// System.out.println("LASTCOLOR: " + lastColor);
	for (int i = 1; i <= lastColor; i++) {
	    U.add(i);
	}
	// System.out.println("PRE: " + U);
	List<Integer> adjacent = this.getNeighbors(k);
	// System.out.println("LISTA: " + adjacent);
	for (int i : adjacent) {
	    if (i < k) U.remove(x[i]);
	}
	return U;
    }

    public static void main(String[] args) {
	System.out.println("Ola bale");
	System.out.println("Probando min: " + Math.min(4, 3));
    }
}

