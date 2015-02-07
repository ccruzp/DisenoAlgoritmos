/**
 * Implements a DIMACS format coloring graph
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DimacsReader {
    public static SimpleGraph processInput(String inputFile) {

        // Scanner initialization
        Scanner scanner = null;
        if (inputFile != null) {
            try {
                scanner = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("No se encontró el archivo " + inputFile);
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            scanner = new Scanner(System.in);
        }

        OrderedEdge edges[] = new OrderedEdge[0];
        int nVertex = 0;
        int edgeIter = 0;

        // Main reading cycle
        while (scanner.hasNextLine()) {
            String line;            // Line of the input
            String tokens[];        // Tokenized line
            OrderedEdge edge;

            // Read a line
            line = scanner.nextLine();

            // Discard required information
            if (!(line.startsWith("p ")  || line.startsWith("e "))) {
                continue;
            }

            // Tokenize line
            tokens = line.split(" ");

            // Problem description
            if (tokens[0].equals("p")) {
                nVertex = Integer.parseInt(tokens[2]);
                edges = new OrderedEdge[Integer.parseInt(tokens[3])];
            }

            // Edge description
            if (tokens[0].equals("e")) {
                int src = Integer.parseInt(tokens[1]);
                int dst = Integer.parseInt(tokens[2]);

                edges[edgeIter] = new OrderedEdge(src,dst);
                edgeIter++;
            }
        }
        return new SimpleGraph(nVertex, edges);
    }
}
