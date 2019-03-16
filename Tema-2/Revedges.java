import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Revedges {

	static int m;
	static int n;
	static int k;
	static int NMAX = 506;

	static int d[][];
	static int a[][];

	public static void main(String[] args) throws IOException {
		String inputFile = "revedges.in";

		MyScanner sc = new MyScanner(inputFile);
		n = sc.nextInt();
		m = sc.nextInt();
		int q = sc.nextInt();

		// Semnificatie matrice
		// a - este matricea de adiacenta a grafului citit (daca exista muchie de la
		// nodul u la nodul v ) a[i][v] este 1 altfel 0
		a = new int[n + 1][n + 1];

		// d - este o matrice pe care "setez"anumit cosutir pentru a face diferentierea
		// dintre o muchie care deja exista ( si este valid) si o potentiala muchie pe
		// care eu as putea sa o intorc pentru a afla drumul minim de la un nod la altul
		// ( cu cat mai putine muchii al caror sens ar terbuie sa il inversez)
		// Astfel : daca exista muchie de la u la v si de la v la u d[u][v] si d[v][u]
		// este 0(nu neceisita inversarea niciune muchii pt a ajunge dintr-un nodul in
		// altul) , daca exista doar muchie de la u la v (sau invers) d[u][v] este 0 ,
		// iar de d[v][u] este 1 (muchie poate fi inversata daca este necesar) , iar
		// daca un exista niciun fel de muchie este INF
		d = new int[n + 1][n + 1];

		int u, v, i;
		for (i = 1; i <= m; i++) {
			u = sc.nextInt();
			v = sc.nextInt();
			a[u][v] = 1;
			d[u][v] = 0;
		}

		int INF = Integer.MAX_VALUE / 3;

		// Realizez umplerea matricei d dupa cum am precizat mai sus
		for (i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (a[i][j] == 1 && a[j][i] != 1) {
					d[j][i] = 1;
				} else {
					if (a[i][j] == 0 && d[i][j] == 0) {
						d[i][j] = INF;
					}
				}
			}
		}
		// Pentru a afla drumul de la un nod la altul ( cu inversarea orientarii unui nr
		// cat mai mic de muchii) voi folosi algoritmul FloydWarshall pe matricea d
		int k, j;
		for (k = 1; k <= n; k++) {
			for (i = 1; i <= n; i++) {
				for (j = 1; j <= n; j++) {
					if (d[i][j] > d[i][k] + d[k][j]) {
						d[i][j] = d[i][k] + d[k][j];
					}
				}
			}
		}

		// Diagonala este 0
		for (i = 1; i <= n; i++) {
			d[i][i] = 0;
		}
		// Citesc perechile de noduri pt care trebuie sa aflu distanta dintre ele
		int[] x = new int[q + 1];
		int[] y = new int[q + 1];
		for (i = 1; i <= q; i++) {
			x[i] = sc.nextInt();
			y[i] = sc.nextInt();
		}

		String rez = "";
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("revedges.out"));
		// Pt a afla drumul minim trebuie sa fac interogarea in matricea drumurilor
		// minime determinata mai sus
		for (i = 1; i <= q; i++) {
			rez = rez + d[x[i]][y[i]] + "\n";
			if (rez.length() > 1000) {
				bufferedWriter.write(rez);
				rez = "";
			}
		}
		bufferedWriter.write(rez);
		bufferedWriter.close();
	}
}
