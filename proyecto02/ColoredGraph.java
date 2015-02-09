/**
 * Implements a subclass of SimpleGraph capable
 * of solving the chromatic number problem.
 */

import java.util.*;

public class ColoredGraph extends SimpleGraph {

    // Graph solution variables
    int x[];                        // Colors chosen for each vertex
    int q;                          // Best solution of the problem

    // Solution tool variables
    int k;                          // Actual coloring vertex
    boolean back;                   // Flag to indicate backtracking
    int l;                          // Numbers of colors used on the actual partial solution
    int u[];                        // Numbers of colors used on the partial solutions (history)

    SortedSet<Integer> labels;           // Brelaz labels
    Map<Integer,SortedSet<Integer>> U;   // Sets containing the colors available for each vertex

    // Clique variables
    int w;                          // Dimension of initial clique

    public ColoredGraph(int nVertex, OrderedEdge edges[]) {
	super(nVertex, edges);
        x = new int[nVertex+1];
        q = getNVertex();

        u = new int[nVertex+1];
        // TreeSet is chosen for problem efficiency (ordered set with O(log n) time operations)
        labels = new TreeSet<Integer>();
        // HashMap is chosen for problem efficiency (upper-bound entry and fast random access)
        U = new HashMap<Integer, SortedSet<Integer>>(nVertex+1);
    }

    public void solveColoring() {

        // Initial clique solving (and labeling)
        solveInitialClique();

        // Initialization
        k = w;
        back = false;
        l = w - 1;
        for (int i=1; i<k; i++) u[i]=i;

        // Main Cycle
        while (true) {

            // Expanding the actual partial solution
            if (!back) {
                // Determine posible colors for x[k]
                U.put(k,determineU(k));

            // We are returning so we must eliminate this group of partial solutions
            } else {
                // Remove actual color from posible colors
                U.get(k).remove(x[k]);
                // Remove label on actual element if it exists
                labels.remove(k);
            }

            // If color assignment is possible
            if (!U.get(k).isEmpty()) {
                // Color actual vertex with minimal color possible
                x[k] = U.get(k).first();

                // Increment actual colors used if that is the case TODO Correctness
                if (x[k] > l) ++l;

                // Save colors used in history
                u[k] = l;   // TODO Correctness

                // Go to next vertex
                k++;

                // All vertices have been colored, this is a new BETTER solution
                if (k > getNVertex()) {

                    // This new BETTER solution is the actual solution counter
                    q = l;

                    // If the best solution is the same as the
                    // initial clique size, there is no better solution to search
                    if (q == w) break;

                    // #! EXPERIMENTAL
                    // Delete the color option q from all the nodes
                    for (Integer i: U.keySet()) {
                        U.get(i).remove(q);
                    }

                    // Set k to the minimal rank among the vertices with the last color
                    for (int i=1; i<=getNVertex(); i++) {
                        if (x[i] == q) {
                            k = i;
                            break;
                        }
                    }

                    // Remove all labels from vertices higher or equal than k
                    labels = new TreeSet<Integer>(labels.headSet(k));

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
                k = labels.last();

                // Get colors used with history
                l = u[k];   // TODO Correctness

                // If it is part of the clique, there is no point in still searching solutions
                if (k <= w) {
                    break;
                }
            }
        }
    }

    private void solveInitialClique() {
        // TODO Get a better clique using Dsatur algorithm or equivalent
        w = 1;      // Clique size is 1
        // x[1] = 1;   // Color of 1st element is 1
        // labels.add(1);
	int color = 1;
	while (w == color) {
	    x[w] = color;
	    u[w] = 
	    w++;
	    U.put(w,determineU(w));
	    color = U.get(w).first();
	}
    }

    private void label(int k) {
        List<Integer> adjacent = this.getNeighbors(k);
        int[] nodesByColor = new int[l+1];

        // For each neighbor (rule ii)
        for (Integer i: adjacent) {
            // Filter by smaller rank (rule i)
            if (i < k) {
                // Add only minimal rank per color (rule iii)
                int color = this.x[i];
                if (nodesByColor[color] == 0 || nodesByColor[color] > i) {
                    nodesByColor[color] = i;
                }
            }
        }

        // Adds the final list to the label tree
        for (int i=1; i<=l; i++) {
            if (nodesByColor[i] != 0) {
                labels.add(nodesByColor[i]);
            }
        }
    }

    private SortedSet<Integer> determineU(int k) {

        // Reasons for TreeSet usage explained on constructor
        TreeSet<Integer> U = new TreeSet<Integer>();
        List<Integer> adjacent = this.getNeighbors(k);

        // Upper bound is the min between
        // numbers of colors used in the last partial solution plus a new one
        // and the best solution so far
        int lastColor = Math.min(u[k - 1] + 1, q-1); // TODO PENDING FOR CORRECTNESS

        // Get the set of forbidden colors
        Set<Integer> forbidden = new HashSet<Integer>(q);
        for (Integer i: adjacent) {
            if (i<k) {
                forbidden.add(x[i]);
            }
        }

        // Add all not the forbidden colors inside bounds
        for (int i=1; i<=lastColor; i++) {
            if (!forbidden.contains(i)) {
                U.add(i);
            }
        }
        return U;
    }

    public int getChromaticNumber() {
        return q;
    }

}
