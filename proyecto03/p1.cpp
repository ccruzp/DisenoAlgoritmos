#include <iostream>
#include <vector>
#include <algorithm>
#include <sstream>

using namespace std;

struct {
    string acronym;
    vector<string> words;
} typedef Case;

Case tokenize(const string &phrase, vector<string> &bannedWords) {
    stringstream ss(phrase);
    string word;

    Case out;

    // First word is acronym
    getline(ss,out.acronym,' ');

    while (getline(ss,word,' ')) {
        // If the word is not banned
        if (!(find(bannedWords.begin(), bannedWords.end(), word) != bannedWords.end())) {
            // Add it to the output
            out.words.push_back(word);
        }
    }
    return out;
}

vector<Case> processInput() {
    int nBans;
    vector<Case> out;

    // Read first number of bans
    cin >> nBans;
    // Discard rest of line
    cin.ignore(numeric_limits<streamsize>::max(), '\n');

    // While it is not zero
    while (nBans > 0) {

        // Read the banned words
        vector<string> bannedWords;

        for (int i=0; i<nBans; i++) {
            string word;
            getline(cin,word);
            bannedWords.push_back(word);
        }

        // Read all the phrases
        while (true) {
            string phrase;

            // Read phrase
            getline(cin,phrase);

            // Exit is it is 'LAST PHRASE'
            if (phrase.compare("LAST CASE") == 0) {
                break;
            }

            // Tokenize and sanitize
            Case c = tokenize(phrase,bannedWords);

            // Add case
            out.push_back(c);
        }

        // Read next number of bans
        cin >> nBans;
        // Discard rest of line
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
    }
    return out;

}

/* Returns a vector with the number of letters used by valid possibilities */
vector<unsigned long> sublistSizes(unsigned long letter, unsigned long word, const Case &aCase) {

    unsigned long index = 0;
    unsigned long top = aCase.words[word].size();
    unsigned long topLetter = aCase.acronym.size();
    vector<unsigned long> out;
    vector<unsigned long> backtrack;

    // Going over the whole word until backtrack is done
    while (index != top || !(backtrack.empty())) {
        // We need to backtrack if we reach end of word (or end of acronym)
        if (index == top || letter == topLetter) {
            // Go back to the last element trying next
            index = backtrack.back() + 1;
            backtrack.pop_back();

            // Searching the previous last letter
            --letter;
            continue;
        }

        // If actual index is the actual letter
        if ((aCase.words[word][index]) == tolower(aCase.acronym[letter])) {
            // Add index to backtrack
            backtrack.push_back(index);

            // Add as a solution
            out.push_back(backtrack.size());

            // Increase letter search
            ++letter;
        }

        // Next index
        ++index;
    }
    return out;
}

/**
* Returns the recursive number of solutions
* from letter of Acronym and word of phrase
*/
int nSolutions(unsigned long letter, unsigned long word, const Case &aCase, int **solutions) {

    unsigned long topLetter = aCase.acronym.size();
    unsigned long top = aCase.words.size();

    // Base case:
    // All letters used with all words
    if (letter >= topLetter && word >= top) {
        return 1;
    }

    // Off cases:
    // Letters pending with all words or
    // Not all words used
    if (letter >= topLetter || word >= top) {
        return 0;
    }


    // Check if value is calculated
    if (solutions[letter][word] >= 0) {
        return solutions[letter][word];
    }

    vector<unsigned long> sublists = sublistSizes(letter, word, aCase);
    int total = 0;

    for (auto it = sublists.begin(); it != sublists.end(); it++) {
        total += nSolutions(letter+(*it),word+1,aCase,solutions);
    }

    // Save result
    solutions[letter][word] = total;
    // Return it
    return total;
}

int main() {

    // Reading
    vector<Case> cases = processInput();

    // Processing
    for (auto it = cases.begin(); it != cases.end(); it++) {
        unsigned long letters = (*it).acronym.size();
        unsigned long words = (*it).words.size();

        // Allocate memory
        int **solutions = new int*[letters];
        for (unsigned long i=0; i<letters; i++) {
            solutions[i] = new int[words];
        }

        // Initialize values
        for (unsigned long i=0; i<letters; i++) {
            for (unsigned long j=0; j<words; j++) {
                solutions[i][j] = -1;
            }
        }

        // Get answer
        int answer = nSolutions(0,0, (*it), solutions);

        // Print answer
        if (answer) {
            cout << (*it).acronym << " can be formed in " << answer << " ways" << endl;
        } else {
            cout << (*it).acronym << " is not a valid abbreviation" << endl;
        }

        // Deallocate memory
        for (unsigned long i=0; i<letters; i++) {
            delete solutions[i];
        }
        delete solutions;
    }
    return 0;
}