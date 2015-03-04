import java.util.*;

public class P4 {
    public static void main(String[] args) {
	
	Case[] c = Case.lectura();
	for (int i = 0; i < c.length; i++) {
	    System.out.println(c[i]);
	}
    }
}

class Case {
    int nRows, nCols;
    String[] matrix;

    public Case(int nRows, int nCols, String[] matrix) {
	this.nRows = nRows;
	this.nCols = nCols;
	this.matrix = matrix;
    }

    public static Case[] lectura() {
	Scanner scanner = new Scanner(System.in);
	int nCases = scanner.nextInt();
	int nRows, nCols;
	ArrayList<String> matrix = new ArrayList<String>();
	Case[] c = new Case[nCases];

	for (int i = 0; i < nCases; i++) {
	    nRows = scanner.nextInt();
	    nCols = scanner.nextInt();
	    scanner.nextLine();
	    for (int j = 0; j < nRows; j++) {
	    	scanner.nextLine();		
		matrix = Case.readLine(scanner.nextLine().split(""), matrix, j);
	    }
	    c[i] = new Case(nRows, nCols, matrix.toArray(new String[matrix.size()]));
	}
	scanner.close();
	return c;
    }

    public static ArrayList<String> readLine(String[] line, ArrayList<String> matriz, int num) {
	if (num % 2 == 0) {
	    for (int i = 1; i < line.length; i++) {
		matriz.add(line[i]);
	    }
	} else {
	    for (int i = line.length-1; i > 0; i--) {
		matriz.add(line[i]);
	    }
	}
	return matriz;
    }
    
    public String toString() {
	String res = "Rows: " + nRows + "\nCols: " + nCols + "\nMatrix: ";
	for (int i = 0; i < matrix.length; i++) {
	    res += matrix[i] + " ";
	}
	return res;
    }
}