/**
 * Implements a subclass of SimpleGraph capable
 * of solving the chromatic number problem.
 */
public class ColoredGraph extends SimpleGraph {

    int x[];        // Colors chosen for each vertex
    int labels[];   // Brelaz labels

    int k;          // Actual coloring vertex
    int w;          // Dimension of initial clique
    int q;          // Best solution of the problem
    boolean back;   // Control variable (to detect backtracking? #!)

    int l;          // Numbers of colors used on the actual partial solution
    int u[];        // Numbers of colors used on the partial solutions

    public ColoredGraph(int nVertex, OrderedEdge edges[]) {
        super(nVertex, edges);
        x = new int[nVertex+1];
        labels = new int[nVertex+1];
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

                // TODO Determine posible colors for x[k]

            // We are returning so we must eliminate this group of partial solutions
            } else {
                // TODO Remove actual color from posible colors
                // TODO Remove label if it exists
            }

            // If color assignment is possible
            if (true) { // TODO change for actual condition
                // Color actual vertex with minimal color possible
                x[k] = 100; // TODO change for actual color

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
        // TODO Implementation pending
    }
}
