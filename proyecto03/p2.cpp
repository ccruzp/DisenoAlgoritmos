#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

const int MAX_INT = numeric_limits<int>::max();

struct RunCase {
    uint size;
    vector<int> *baby;
    vector<int> *final;
};

vector<RunCase> processInput() {
    uint size = 0;
    vector<RunCase> out;

    while ((cin >> size) && (size > 0)) {
        RunCase rc;
        rc.size = size;
        rc.baby = new vector<int>;
        rc.final = new vector<int>;

        // Read baby
        for (auto i=0; i<size; i++) {
            int elem;
            cin >> elem;
            rc.baby->push_back(elem);
        }

        // Read final
        for (auto i=0; i<size; i++) {
            int elem;
            cin >> elem;
            rc.final->push_back(elem);
        }

        // Add to set of cases
        out.push_back(rc);
    }
    return out;
}

int moveCost(int x1, int y1, int x2, int y2) {
    return abs(x1-x2) + abs(y1-y2);
}

int optimalSteps(uint column, uint mask, const RunCase &rc, int **solutions) {
    int optimal = MAX_INT;
    int option = 0;

    // Base off case
    if (column >= rc.size) {
        return 0;
    }

    // Memoization
    if (solutions[column][mask] >= 0) {
        return solutions[column][mask];
    }

    // For each column
    for (auto i=0; i<rc.size; i++) {
        // Only if still available on the mask
        if (!((1 << i) & mask)) {
            // Get the solution with a recursive subsolution
            option = moveCost(column,(*rc.baby)[column],i,(*rc.final)[i]) +
                    optimalSteps(column+1, (1 << i) | mask, rc, solutions);

            // Update if it is better
            if (optimal > option) {
                optimal = option;

                // Save and return at once if its 0 (cannot be better)
                if (optimal == 0) {
                    solutions[column][mask] = optimal;
                    return optimal;
                }
            }
        }
    }

    // Save and return
    solutions[column][mask] = optimal;
    return optimal;
}

int main() {

    // Reading
    vector<RunCase> vrc = processInput();

    // Calculate answers
    for (auto it = vrc.begin(); it != vrc.end(); ++it) {

        // Define max number of columns
        uint columns = (*it).size;

        // Define max number of masks
        uint masks = 0;
        for (auto i=0; i<columns; i++) {
            masks |= 1 << i;
        }

        // Allocate memory
        int **solutions = new int*[columns];
        for (auto i=0; i<columns; i++) {
            solutions[i] = new int[masks];
            // Initialize values
            fill(solutions[i],solutions[i]+masks,-1);
        }

        // Get optimal answer
        int optimal = optimalSteps(0,0,(*it),solutions);

        // Print answer
        cout << optimal << endl;

        // Deallocate RunCase structures
        delete (*it).baby;
        delete (*it).final;

        // Deallocate memory
        for (auto i=0; i<columns; i++) {
            delete[] solutions[i];
        }
        delete[] solutions;
    }
    return 0;
}