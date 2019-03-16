import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Minlexbfs {
	static int n;
	static int m;
	static int NMAX = 100005;

	// Listele de adiacenta pentru fiecare nod
	@SuppressWarnings("unchecked")
	static ArrayList<Integer> adj[] = new ArrayList[NMAX];

	public static void main(String[] args) throws IOException {

		String inputFile = "minlexbfs.in";

		MyScanner sc = new MyScanner(inputFile);
		n = sc.nextInt();
		m = sc.nextInt();

		for (int i = 1; i <= n; i++) {
			adj[i] = new ArrayList<Integer>();
		}
		int u, v;
		// Citirea muchiilor
		for (int i = 1; i <= m; i++) {
			u = sc.nextInt();
			v = sc.nextInt();
			adj[u].add(v);
			adj[v].add(u);
		}

		// Sortam Fiecare lista de adiacenta in parte , astfel incat nodurile vecine sa
		// fie in ordine crescatoare
		for (int i = 1; i <= n; i++) {
			Collections.sort(adj[i]);
		}

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("minlexbfs.out"));
		// Vectorul de color (Semnificatie false == alb , true = negru)
		boolean[] c = new boolean[n + 1];
		// Aceasta lista simuleaza o coada (pe care o voi folosi in parcurgerea BFS)
		LinkedList<Integer> qu = new LinkedList<Integer>();
		// Nodul de start din care pornesc este 1
		qu.add(1);
		// Il marchez ca fiind vizitat
		c[1] = true;

		// Deoarece scrierea in Java necesita o durata mai mare de timp , voi stoca
		// mereu rezultatul in rez si atunci cand va avea o anumita lungime voi scrie o
		// parte in rezultat in fisier
		String rez = "";

		// ALGORITMUL BFS ( De parcurgere in latime a unui graf)
		while (!qu.isEmpty()) {
			u = qu.removeFirst();
			rez = rez + u + " ";
			if (rez.length() > 1000) {
				bufferedWriter.write(rez);
				rez = "";
			}
			// Parcurg pe rand fiecare nivel al nodului curent ( care este inca nemarcat)
			for (int vv : adj[u]) {
				if (c[vv] == false) {
					qu.add(vv);
					c[vv] = true;
				}
			}
		}
		bufferedWriter.write(rez);
		bufferedWriter.close();

	}

}

// Aceasta clasa este luata din scheletul de cod oferit la testul practic de
// anul trecut pentru cei care rezolva in Java
class MyScanner {
	BufferedReader br = null;
	StringTokenizer st;

	public MyScanner(String file) {
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(fr);
	}

	String next() {
		while (st == null || !st.hasMoreElements()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}

	int nextInt() {
		return Integer.parseInt(next());
	}

	long nextLong() {
		return Long.parseLong(next());
	}

	double nextDouble() {
		return Double.parseDouble(next());
	}

	String nextLine() {
		String str = "";
		try {
			str = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}