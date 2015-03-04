import java.util.*;

//Main
public class P4 {
    public static void main(String[] args) {	
	Case[] c = Case.lectura();
	for (int i = 0; i < c.length; i++) {
	    System.out.println(c[i] + "\nOptimo: " + c[i].maxWoods(0) + "\n");
	}
    }
}


class Case {
    public int nRows, nCols;
    public String[] matrix;
    public int[] opts;
    
    //Constructor
    public Case(int nRows, int nCols, String[] matrix, int[] opts) {
	this.nRows = nRows;
	this.nCols = nCols;
	this.matrix = matrix;
	this.opts = opts;
    }
    
    //Reads the file and saves the values
    public static Case[] lectura() {
	Scanner scanner = new Scanner(System.in);
	int nCases = scanner.nextInt();
	int nRows, nCols;
	ArrayList<String> matrix = new ArrayList<String>();
	int[] opts;
	Case[] c = new Case[nCases];
	
	//iterate over each test case
	for (int i = 0; i < nCases; i++) {
	    matrix = new ArrayList<String>();
	    nRows = scanner.nextInt();
	    nCols = scanner.nextInt();
	    opts = new int[nRows*nCols];
	    Arrays.fill(opts, -1);
	    scanner.nextLine();
	    
	    //iterates over each matrix column
	    for (int j = 0; j < nRows; j++) {
	    	scanner.nextLine();		
		matrix = Case.readLine(scanner.nextLine().split(""), matrix, j);
	    }
	    
	    
	    c[i] = new Case(nRows, nCols, matrix.toArray(new String[matrix.size()]), opts);
	}
	scanner.close();
	return c;
    }
    
    
    //Solves the MAXWOODS problem
    public int maxWoods(int start) {
	
	//If the algorithm doesn't have the Optimum for that position
	if (this.opts[start] == -1) {
	    
	    //Hash case (dude can't move)
	    if (this.matrix[start].equals("#")) {
		this.opts[start] = 0;
		return 0;
	    }
	    
	    
	    //If it's not on the last column
	    if (start < this.matrix.length - this.nCols) {
		int place = start % this.nCols;
		this.opts[start] = Math.max(maxWoods(start+1), maxWoods(start+this.nCols*2 - place*2 -1));
	    }
	    
	    //If it's not on the last position
	    else if(start < this.matrix.length - 1) {
		this.opts[start] = maxWoods(start+1);
	    }
	    //If it's the last one
	    else {
		this.opts[start] = 0;	
	    }
	    
	    //Tree case
	    if (this.matrix[start].equals("T")) {
		this.opts[start]++;
	    }
	}
	//Blank case, tree return and already calculated case
	return this.opts[start];
	
    }
    
    //Converts each matrix line into a usable string
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