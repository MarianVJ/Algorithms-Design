// Copyright [year] <Copyright Owner>
#include <fstream>
#include <vector>
#include <algorithm>
#include <queue>
#include <stack>
using namespace std;

const int kNmax = 100030;

class Task {
 public:
	void solve() {
		read_input();
		get_result();
	}

 private:
	int n;
	int m;

	stack<int> st;
	//Listele de adiacenta pentru fiecare nod		
	vector<int> adj[kNmax];
	
	int d[kNmax];
	int low[kNmax] ;
	int p[kNmax];
	int time;
	long long int rez;
	long long int cont;
		
	void read_input() {
		ifstream fin("disjcnt.in");
		fin >> n >> m;
		
		//Initializez la 0 toti vectorii auxiliari de care am nevoie
		for (int i = 1; i <= n+2; i++){
			d[i] = 0;
			low [i] = 0;
			p[i] = 0;
		}
		
		//Graful este neorientat
		//Citesc fiecare muchie , iar daca aceasta este duplicat 
		//Am adaugat un nod in plus (santinela) , si voi adauga 
		//2 muchii ( intre nodul santinela si fiecare nod celelalte 2 noduri)
		for (int i = 1, x, y; i <= m; i++) {
			fin >> x >> y;
			bool check = true;
			for(vector<int>::iterator it=adj[x].begin(); it!=adj[x].end(); ++it){
				int u = *it;
				if(u == y){
					check = false;
					break;
				}
			}
			if(check == true){
				adj[x].push_back(y);
				adj[y].push_back(x);
			}	
			else{
				adj[x].push_back(n+1);
				adj[n+1].push_back(x);
				adj[y].push_back(n+1);
				adj[n+1].push_back(y);
			}	
		}
		fin.close();
	}
	int get_result() {
		//Initializez variabilele globale la 0 
		time = 0;
		rez = 0;
		//Deoarece se precizeaza in enunt faptul ca graful este conex 
		//, este necesar un singur apel al functiei ( pentru determinarea
		//componentelor biconexe)
		dfsCB(1);

		//Rezultatul il scriu in fisier
		ofstream fout("disjcnt.out");			
		fout <<rez;
		fout.close();
	}

	void dfsCB(int u){
		
		time++;
		low[u] = d[u] = time;
		
		for(vector<int>::iterator it=adj[u].begin(); it!=adj[u].end(); ++it){
			int	v = *it;
			if (v == p[u])
				continue;
			//Daca v a fost deja intalnit in parcurgerea dfs (actualizam nivelul minim la care 
			//se poate ajunge parcurgand o muchie de inatoarcere care are radacina in nodul u
			if (d[v] > 0) {
				low[u] = min(low[u], d[v]);
			} else {			
				st.push(u);
				p[v] = u;
				dfsCB(v);
				low[u] =min(low[u], low[v]);
				verificareComponenta(u, v);
			}
		}
	}
	 void verificareComponenta(int u, int v) {
	 	int a;
		if (low[v] >= d[u] && st.size() > 0) {
			//cont este nr de elemente din componenta biconexa curenta
			cont = 1;
			do {
				cont++;
				a = st.top();
				st.pop();
			} while (a != u && !st.empty());

			//Numarul perechilor ce se pot forma cu n numere este (n * (n-1)) / 2
			//(Combinari de n luate cate 2 )
			if (cont > 2)
				rez += ((cont) * (cont - 1) / 2);
		}
	}
};

int main() {
	Task *task = new Task();
	task->solve();
	delete task;
	return 0;
}

