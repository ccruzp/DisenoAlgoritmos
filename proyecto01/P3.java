/*
 * Problem 03 Solution of http://ldc.usb.ve/~gpalma/ci5651em15/Proyecto1ci5651em15.pdf
 * 
 * Authors:
 * 		Manuel Pacheco - 10-10524
 * 		Carlos Cruz -  10-10168
 */

import java.util.*;

public class P3 {
    public static void main (String[] args) {
        Case[] cases = Reader01.processInput();

        // For each case
        for (Case ca : cases) {

            int decided = Integer.MIN_VALUE;    // The time with no possible changes to its past
            int tasks = 0;

            // Sort intervals by ending time
            Arrays.sort(ca.intervals);

            // Test the acceptable intervals
            for (Interval i : ca.intervals) {

                // Greedy choice: accept interval if it does not collide with the last
                if (i.start >= decided) {
                    decided = i.end;
                    tasks++;
                }
            }

            // Generate output
            System.out.println(tasks);
        }
    }
}

class Reader01 {
    public static Case[] processInput() {

        // Read nCases
        Scanner scan = new Scanner(System.in);
        int nCases = scan.nextInt();

        Case cases[] = new Case[nCases];

        // For each case, get data
        for (int i=0; i<nCases; i++) {

            int nTasks = scan.nextInt();
            Interval intervals[] = new Interval[nTasks];

            // Get intervals
            for (int j=0; j<nTasks; j++) {
                intervals[j] = new Interval(scan.nextInt(),scan.nextInt());
            }

            // Save case
            cases[i] =  new Case(nTasks,intervals);
        }

        scan.close();
        return cases;
    }
}

class Case {
    public int nTasks;
    public Interval[] intervals;

    public Case(int nTasks, Interval[] intervals) {
        this.nTasks = nTasks;
        this.intervals = intervals;
    }
}

// Contains the given data for each case
class Interval implements Comparable<Interval> {
    public int start;
    public int end;

    public Interval(int start, int end) {
        this.start =  start;
        this.end = end;
    }


    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(Interval i) {
        return this.end - i.end;
    }
}