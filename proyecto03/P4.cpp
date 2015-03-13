#include <iostream>
#include <cstring>
#include <vector>
using namespace std;

int init() {
}

int MAXWOODS(vector<int>& V1, vector<int>& V2, int pair, int rows, string line, int maximum) {
	
	//Moving left case
	if (pair == 0) {
		for (int i = 0; i < rows; i++) {
			//If it's a #, -1
			if (line[i] == '#') {
				V2[i] = -1;
			}
			//If it's a 0, return the max
			else if (line[i] == '0') {
				if (i != 0) V2[i] = max(V2[i-1], V1[i]);
				else V2[i] = V1[i];
			}
			//If it's a T, return the max + 1
			else if (line[i] == 'T') {
				if (i != 0)  V2[i] = max(V2[i-1], V1[i]);
				else V2[i] = V1[i];
				if (V2[i] != -1) V2[i]++;
			}
			
			if (maximum < V2[i]) maximum = V2[i];
			
			
		}
	} 
	//Moving right case
	else {
		for (int i = rows-1; i >= 0; i--) {
			//If it's a #, -1
			if (line[i] == '#') {
				V2[i] = -1;
			}
			//If it's a 0, return the max
			else if (line[i] == '0') {  
				if (i != rows-1) V2[i] = max(V2[i+1], V1[i]);
				else V2[i] = V1[i];
			}
			//If it's a T, return the max + 1
			else if (line[i] == 'T') {
				//cout << "V1: " << V1[i] << " after was" << V2[i+1] << endl;
				if (i != rows-1) V2[i] = max(V2[i+1], V1[i]);
				else V2[i] = V1[i];
				if (V2[i] != -1) V2[i]++;
			}
			
			if (maximum < V2[i]) maximum = V2[i];
		}
	}
	
	return maximum;
}

int readStuff(){
	
	int N, M; 				//Rows and Columns
	vector<int> V1;			//optimization vector #1
	vector<int> V2;			//optimization vector #1
	string line;			//Actual line
	int maximum = 0;

	cin >> N >> M;
	V1 = vector<int>(M, -1);
	V1[0] = 0;
	for (int i = 0; i < N; i++) {
		V2 = vector<int>(M, 0);
		cin >> line;
		maximum = MAXWOODS(V1, V2, i%2, M, line, maximum);
		V1 = V2;
		
	}
	cout << maximum << endl;
	return 0;	
}



int main() {
	int cases;
	cin >> cases;
	while(cases--){
		readStuff();
	}
	return 0;	
}
