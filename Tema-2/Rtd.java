import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;

//Aceasta clasa retine pozitia unei celule , modul in care zarul este pozitionat :  
//fata pe care este asezat(C),costul total care a determinat
//in ajungerea in acea pozitia (cost) , fata de sus (up) si fata din stanga ( left)

class Pereche implements Comparable<Pereche> {

	int x;
	int y;
	int c;
	int left;
	int up;
	int cost;

	Pereche(int x, int y, int c, int left, int up, int cost) {
		this.x = x;
		this.y = y;
		this.c = c;
		this.left = left;
		this.up = up;
		this.cost = cost;
	}

	public int compareTo(Pereche ed) {
		if (this.cost > ed.cost)
			return 1;
		else if (this.cost < ed.cost)
			return -1;
		else
			return 0;
	}
}

public class Rtd {

	public static void main(String[] args) throws IOException {

		String inputFile = "rtd.in";
		MyScanner sc = new MyScanner(inputFile);
		// Dimensiunile matricei
		int n = sc.nextInt();
		int m = sc.nextInt();
		// Coordonatele punctului de start
		int sx = sc.nextInt();
		int sy = sc.nextInt();
		// Coordonatele punctului de final
		int fx = sc.nextInt();
		int fy = sc.nextInt();
		int k = sc.nextInt();

		// Vectorul care retine costul fiecarei fete (cost[1] - costul fetei 1 etc)
		int[] cost = new int[7];
		for (int i = 1; i <= 6; i++) {
			cost[i] = sc.nextInt();
		}

		// Matricea tridimensionala selectat retine pozitiile fiecarui punct din "harta"
		// mea , iar ce-a de a treia dimensiune reprezinta codificare zarului daca
		// ajunge in acel punct.Aceasta codificare este folosita si in cazul matricei de
		// distante si are urmatoarea insemnatate . Un zar are 6 fete , Daca este asezat
		// pe fata X pe o celula acesta se poate afla in patru moduri diferite ( chiar
		// daca este asezat pe fata X) .Astfel numarul starilor este 6 fete * 4
		// stari/fata = 24 de stari posibile in care un zarul se poate afla intr-o
		// celula.
		boolean[][][] selectat = new boolean[n + 1][m + 1][101];
		int INF = Integer.MAX_VALUE / 3;
		int i, j;

		// distanta de la punctul de start la celalalte (de asemenea a treia componenta
		// a matricei tridimensionala reprezinta codificarea modului in care se afla
		// zarul ( una din cele 24 de posibilitati))
		int[][][] d = new int[n + 1][m + 1][51];
		int z;

		// Initial distantele de la pozitia curenta din starea curenta catre toate
		// celelalte celule este 0
		for (i = 1; i <= n; i++) {
			for (j = 1; j <= m; j++) {
				for (z = 1; z <= 50; z++) {
					d[i][j][z] = INF;
				}
			}
		}

		int a, b;
		// Citesc si pozitiile celulelor pe care nu se poate ajunge ( acestea vor avea
		// valoarea -1 pentru a nu putea fi "relaxate" )
		for (i = 1; i <= k; i++) {
			a = sc.nextInt();
			b = sc.nextInt();
			for (z = 1; z <= 50; z++) {
				d[a][b][z] = -1;
			}
		}

		// Distanta de la celula de plecare este chiar costul fetei 1 ( Incepem cautarea
		// cu codificarea zarului conform cu imaginea din enuntul temei)
		d[sx][sy][2 * 7 + 4] = cost[1];

		// Ed contine aceleasi valori ca cele din matricea de distante , cu exceptia
		// faptului Pereche retine si pe ce pozitie este asezat zarul in modul curent ,
		// (coordonatele sale x si y care prin care este accesat fiecare elemente), fata
		// din stanga
		// (cu ajutorul careia putem afla si fata din dreata) , si fata de sus ( cu
		// ajutorul careia putem
		// afla si fata celei de jos, dar si costul pentru a ajunge la fata al carei
		// codificari este data de valorile lui up si left ( 7 * up + left)
		Pereche[][][] ed = new Pereche[n + 1][m + 1][51];

		// Coada de prioritati in care voi adauga pe rand elemente de tipul Pereche (
		// care inglobeaza toate acele finromatii enumerate mai sus), dar sunt scoase in
		// ordinea descrisatoare costului
		PriorityQueue<Pereche> q = new PriorityQueue<Pereche>();
		// Pornim din punctele citite din fisier , cu zarul poztiionat ca in imaginea
		// din enuntul problemei
		Pereche aux = new Pereche(sx, sy, 1, 4, 2, cost[1]);
		ed[sx][sy][18] = aux;

		// Adaugam in coada
		q.add(aux);

		int xn, yn, sum;
		int x, y;
		int codif;
		int crt_cod;

		// Cat timp coada nu este vida
		while (!q.isEmpty()) {

			// Extrag nodul ( din cele m * n ) cu costul cel mai mic ( raportat la nodul
			// initial ( de coordonate sx si sy))
			aux = q.poll();

			// Salvez coordonatele pt a le putea accesa mai usor
			x = aux.x;
			y = aux.y;

			// Aceasta este codificarea starii curente
			crt_cod = 7 * aux.up + aux.left;

			// Marcam nodul curent cu codificarea curenta ca fiind vizitat
			selectat[aux.x][aux.y][crt_cod] = true;

			xn = aux.x - 1;
			// Verificam daca putem reduce dimensiunea drumului de la coordonatele initiale
			// fata de nodul dde deasupra (nodului curent)
			sum = d[aux.x][aux.y][crt_cod] + cost[aux.up];
			// codificarea zarului daca acesta ar ajunge cu o pozitie mai sus pe harta
			codif = aux.left + (7 - aux.c) * 7;

			// Daca nu sunt pe prima linie, celula si codificarea viitoare a zarului nu a
			// fost vizitte , si distanta poate fi micsorata
			if (aux.x != 1 && selectat[xn][y][codif] == false && d[xn][y][codif] > sum) {

				d[xn][y][codif] = sum;
				// Eliminm din coada nodul respectiva daca exista
				q.remove(ed[xn][y][codif]);
				// Actualizez cu noua suma
				ed[xn][y][codif] = new Pereche(xn, y, aux.up, aux.left, (7 - aux.c), sum);
				// Il adaug din nou in coada
				q.add(ed[xn][y][codif]);
			}

			// Aceleasi operatii de mai sus se repeta atunci cand vreau sa vizitez oricare
			// din cei 3 vecini ramasi ai nodului curent ( jos, stanga, dreapta)
			xn = aux.x + 1;
			sum = d[aux.x][aux.y][crt_cod] + cost[7 - aux.up];
			codif = aux.left + aux.c * 7;
			if (aux.x != n && selectat[xn][aux.y][codif] == false && d[xn][y][codif] > sum) {

				d[xn][y][codif] = sum;
				q.remove(ed[xn][y][codif]);
				ed[xn][y][codif] = new Pereche(xn, y, 7 - aux.up, aux.left, aux.c, sum);
				q.add(ed[xn][y][codif]);
			}

			yn = aux.y - 1;
			sum = d[aux.x][aux.y][crt_cod] + cost[aux.left];
			codif = (7 - aux.c) + aux.up * 7;
			if (y != 1 && selectat[x][yn][codif] == false && d[x][yn][codif] > sum) {

				d[x][yn][codif] = sum;
				q.remove(ed[x][yn][codif]);
				ed[x][yn][codif] = new Pereche(x, yn, aux.left, 7 - aux.c, aux.up, sum);
				q.add(ed[x][yn][codif]);
			}

			yn = aux.y + 1;
			sum = d[aux.x][aux.y][crt_cod] + cost[7 - aux.left];
			codif = aux.c + aux.up * 7;
			if (y != m && selectat[x][yn][codif] == false && d[x][yn][codif] > sum) {

				d[x][yn][codif] = sum;
				q.remove(ed[x][yn][codif]);
				ed[x][yn][codif] = new Pereche(x, yn, 7 - aux.left, aux.c, aux.up, sum);
				q.add(ed[x][yn][codif]);
			}
		}

		// Caut distanta minima de la nodul initial catre nodul final
		int min = Integer.MAX_VALUE;
		for (z = 1; z <= 50; z++) {
			if (min > d[fx][fy][z]) {
				min = d[fx][fy][z];
			}
		}

		// Scriu in fisier rezultatul
		FileWriter fileWriter = new FileWriter("rtd.out");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.printf("%d", min);
		printWriter.close();

	}
}
