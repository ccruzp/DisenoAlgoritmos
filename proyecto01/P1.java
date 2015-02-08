/*
 * Problem 01 Solution of http://ldc.usb.ve/~gpalma/ci5651em15/Proyecto1ci5651em15.pdf
 * 
 * Authors:
 * 		Manuel Pacheco - 10-10524
 * 		Carlos Cruz -  10-10168
 */

import java.util.*;

public class P1 {
	public static void main (String[] args) {
		Case[] cases = Reader01.processInput();
		
		// For each case
		for (int c=0; c<cases.length; c++) {
			
			Case ca =  cases[c];
			
			// Transform the array of offices into a forest
			// using a DisjointSet
			DisjointSet<Office> forest = new DisjointSet<Office>(ca.offices);
			
			int nOffices = ca.offices.length;
			int nEdges = nOffices * (nOffices - 1) / 2;

			// Build edges of the implicit graph
			Edge edges[] = new Edge[nEdges];
			for (int i=0, k=0; i<nOffices; i++) {
				for (int j=i+1; j<nOffices; j++) {
					edges[k] = new Edge(ca.offices[i],ca.offices[j]);
					k++;
				}
			}
			
			// Sort edges by weight
			Arrays.sort(edges);
			
			// Through all edges, unite the forest sets with the correct edges
			List<Edge> solutionEdges = new ArrayList<Edge>();
			for (int i=0; i<nEdges; i++) {
				if (forest.partitions == ca.modems) {
					break;	// Each modem takes care of a partition
				}
				
				// If edge elements belong to different trees
				if (forest.union(edges[i].src, edges[i].dst)) {
					solutionEdges.add(edges[i]);
				}
			}
			
			// Get the costs of the edges of the solution
			double lowTotal=0, highTotal=0;
			for (Edge e : solutionEdges) {
				if (e.distance <= ca.distance) {
					lowTotal += e.distance * ca.lowCost;
				} else {
					highTotal += e.distance * ca.highCost;
				}
			}
			
			// Output
			System.out.printf("Caso #%d: %.3f %.3f\n",c,lowTotal,highTotal);
			
		}
	}
}

class Reader01 {	
	public static Case[] processInput() {
		int nCases, nOffices, distance, modems, lowCost, highCost;
		Office offices[];
		
		// Read nCases
		Scanner scan = new Scanner(System.in);
		nCases = scan.nextInt();
		
		Case cases[] = new Case[nCases];
		
		// For each case, get data
		for (int i=0; i<nCases; i++) {
			nOffices = scan.nextInt();
			distance = scan.nextInt();
			modems = scan.nextInt();
			lowCost = scan.nextInt();
			highCost = scan.nextInt();
			
			// For each office, get office coords
			offices = new Office[nOffices];
			for (int j=0; j<nOffices; j++) {
				offices[j] = new Office(j,scan.nextInt(),scan.nextInt());
			}
			
			// Save case
			cases[i] =  new Case(offices, modems, lowCost, highCost, distance);
		}
		
		scan.close();
		return cases;
	}
}

// Contains the given data for each case
class Case {
	public int modems, lowCost, highCost, distance;
	public Office offices[];
	
	public Case(Office offices[], int modems, int lowCost, int highCost, int distance) {
		this.offices = offices;
		this.modems = modems;
		this.lowCost = lowCost;
		this.highCost = highCost;
		this.distance = distance;
	}
}

// Contains the data related of a singular office
class Office {
	public int i, x, y;
	
	public Office(int i, int x, int y) {
		this.i = i;
		this.x = x;
		this.y = y;
	}
}

// Contains the data related of a non-directed edge of the office graph
class Edge implements Comparable<Edge>{
	public Office src, dst;
	public double distance;
	public boolean used;
	
	public Edge(Office src, Office dst) {
		this.src = src;
		this.dst = dst;
		this.used = false;
		
		int x1 = (dst.x - src.x);
		int x2 = (dst.y - src.y);
		this.distance = Math.sqrt(x1*x1 + x2*x2);
	}

	@Override
	public int compareTo(Edge e) {
		return (int) (this.distance - e.distance);
	}
}

class DisjointSet<E> {

	private HashMap<E,E> map;			// Maps the representative of each element
	private HashMap<E,Integer> rank;	// Maps each element with a rank
	public int partitions;				// Number of partitions in the set
	
	public DisjointSet(E elems[]) {	
		int nElems = elems.length;
		
		this.map = new HashMap<E,E>(nElems);
		this.rank = new HashMap<E,Integer>(nElems);
		this.partitions = nElems;
		
		// Create new elems
		for (int i=0; i<nElems; i++) {
			map.put(elems[i],elems[i]);
			rank.put(elems[i], 0);
		}
	}
	
	public E find(E x) {
		E value = map.get(x);
		if (value == x) {
			return x;
		} else {
			value = find(value);
			map.put(x, value); // Optimization: Path Compression
		}
		return value;
	}
	
	// Return true if sets where different, false otherwise
	public boolean union(E a, E b) {
		E aRoot = find(a);
		E bRoot = find(b);
		
		if (aRoot == bRoot) { 							// Same set already
			return false;
		}
		
		// Optimization: Union by rank
		if (rank.get(aRoot) > rank.get(bRoot)) {		// A in-tree may be deeper
			map.put(bRoot, aRoot);
		} else if (rank.get(aRoot) < rank.get(bRoot)) {	// B in-tree may be deeper
			map.put(aRoot, bRoot);
		} else {										// Same size
			map.put(bRoot, aRoot);
			rank.put(aRoot, rank.get(aRoot)+1);
		}
		--partitions;
		return true;
	}
}