/**
 * Implements a tester for the colored graph solution class
 */
public class ColoredGraphTest {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

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
        System.out.println("The chromatic number is: " + graph.getChromaticNumber());

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("TIME: " + ((double) totalTime)/1000 + " secs.");
    }
}
