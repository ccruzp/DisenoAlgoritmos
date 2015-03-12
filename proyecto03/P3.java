import java.util.*;

public class P3 {
    public static void main(String[] args) {
	Case [] c = Reader.processInput();
	for (int i = 0; i < c.length; i++) {
	    System.out.println("Sin colorear: " + c[i].blackOrWhite(1, 0, 0));
	}
    }
}

class Reader {

    public Reader () {

    }

    public static Case[] processInput() {
	int[] seq;
	Scanner scanner = new Scanner(System.in);
	int nElem = scanner.nextInt();
	ArrayList<Case> c = new ArrayList<Case>();
	while(nElem != -1) {
	    seq = new int[nElem+1];
	    for (int i = 1; i <= nElem; i++) {
		seq[i] = scanner.nextInt();
	    }
	    c.add(new Case(seq));
	    nElem = scanner.nextInt();
	}
	return c.toArray(new Case[c.size()]);
    }
}

class Case {
    public int[] seq;
    public int[][][] mem;

    public Case(int[] seq) {
	this.seq = seq;
	int n = seq.length;
	this.mem = new int[n+1][n+1][n+1];
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < n; j++) {
		for (int k = 0; k < n; k++) {
		    mem[i][j][k] = -1;
		}
	    }
	}
    }

    public String toString() {
	String res = "Sequence: ";
	for (int i = 0; i < seq.length; i++) {
	    res += seq[i] + " ";
	}
	return res;
    }

    public int blackOrWhite(int pos, int cre, int dec) {
	int n = seq.length;
	if (pos >= n) {
	    return 0;
	}
	if (mem[pos][cre][dec] == -1) {
	    mem[pos][cre][dec] = Integer.MAX_VALUE;
	    if ((dec == 0 || seq[pos] < seq[dec]) && (cre == 0 || seq[pos] > seq[cre])) {
		mem[pos][cre][dec] = Math.min(1 + this.blackOrWhite(pos+1, cre, dec), this.blackOrWhite(pos+1, cre, pos));
		mem[pos][cre][dec] = Math.min(mem[pos][cre][dec], this.blackOrWhite(pos+1, pos, dec));

	    } else if (dec == 0 || seq[pos] < seq[dec]) {
		mem[pos][cre][dec] = Math.min(1 + this.blackOrWhite(pos+1, cre, dec), this.blackOrWhite(pos+1, cre, pos));

	    } else if (cre == 0 || seq[pos] > seq[cre]) {
		mem[pos][cre][dec] = Math.min(1 + this.blackOrWhite(pos+1, cre, dec), this.blackOrWhite(pos+1, pos, dec));

	    } else {
		mem[pos][cre][dec] = 1 + this.blackOrWhite(pos+1, cre, dec);
	    }
	}
	return mem[pos][cre][dec];

    }
}
	    