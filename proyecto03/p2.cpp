#include <iostream>
#include <vector>
#include <cstdlib>

using namespace std;

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
        for (uint i=0; i<size; i++) {
            int elem;
            cin >> elem;
            rc.baby->push_back(elem);
        }

        // Read final
        for (uint i=0; i<size; i++) {
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

int optimalSteps(int column, int mask, const RunCase &rc, int **solutions) {

    return 0;
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
        for (uint i=0; i<columns; i++) {
            masks |= 1 << i;
        }

        // Allocate memory
        int **solutions = new int*[columns];
        for (uint i=0; i<columns; i++) {
            solutions[i] = new int[masks];
        }

        // Get optimal answer
        int optimal = optimalSteps(0,0,(*it),solutions);

        // Print answer
        cout << optimal << endl;

        // Deallocate RunCase structures
        delete (*it).baby;
        delete (*it).final;

        // Deallocate memory
        for (unsigned long i=0; i<columns; i++) {
            delete[] solutions[i];
        }
        delete[] solutions;

    }

    return 0;
}