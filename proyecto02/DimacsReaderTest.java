/**
 * Tester for the DIMACS format reader and graph saving.
 */
import java.util.List;

class DimacsReaderTest {

    public static void main(String[] args) {

        // Verify arguments
        if (args.length != 1) {
            System.out.println("Falta el argumento archivo.");
            System.exit(1);
        }

        // Read graph
        SimpleGraph graph = DimacsReader.processInput(args[0]);

        for (int i=0; i<graph.getNVertex(); i++) {
            System.out.println("Los vecinos de " + i + " son:");
            List<Integer> neighbors = graph.getNeighbors(i);

            // Print neighbors of i
            for (int n : neighbors) {
                System.out.print("" + n + " ");
            }

            System.out.println();
        }
    }
}
