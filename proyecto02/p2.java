/**
 * Implements a tester for the colored graph solution class
 */
public class p2 {

    public static void main(String[] args) {

        // Verify arguments
        if (args.length != 1) {
            System.out.println("Falta el argumento archivo.");
            System.exit(1);
        }

        // Read graph
        ColoredGraph graph = DimacsReader.processInput(args[0]);

        // Solve Graph
        graph.solveColoring();

        // Return Chromatic Number
        System.out.println(graph.getNVertex() + " " + graph.getChromaticNumber());
    }
}
