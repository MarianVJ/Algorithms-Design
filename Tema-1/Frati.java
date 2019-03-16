
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

//Comparatorul pentru primul frate 
//( astfel incat valoarile sa fie in ordine descrescatoarea)
class Comparator1 implements Comparator<Concurs> {
	public int compare(Concurs c1, Concurs c2) {
		int d1 = c2.jocuri - c1.benzi;
		int d2 = c1.jocuri - c2.benzi;

		if (d1 < d2) {
			return -1;
		} else {
			if (d2 < d1) {
				return 1;
			} else {
				int aux = -1;
				if (c2.jocuri > c1.jocuri) {
					return -aux;
				} else {
					if (c2.jocuri < c1.jocuri) {
						return aux;
					} else {
						return 0;
					}
				}
			}
		}
	}
}

// Comparatorul pentru al doilea frate
// ( astfel incat valoarile sa fie in ordine descrescatoarea)
class Comparator2 implements Comparator<Concurs> {
	public int compare(Concurs c1, Concurs c2) {
		int d1 = c2.benzi - c1.jocuri;
		int d2 = c1.benzi - c2.jocuri;

		if (d1 < d2) {
			return -1;
		} else {
			if (d2 < d1) {
				return 1;
			} else {
				int aux = -1;

				if (c2.benzi > c1.benzi) {
					return -aux;
				} else {
					if (c2.benzi < c1.benzi) {
						return aux;
					} else {
						return 0;
					}
				}
			}
		}
	}
}

// O clasa care retine numarul de jocuri pt un concur , de benzi si flag este un
// camp care poate indica daca un concurs este liber(0) sau a fost deja luat de
// unul dintre frati
class Concurs {
	int jocuri;
	int benzi;
	int flag = 0;

	Concurs(int jocuri, int benzi) {
		this.jocuri = jocuri;
		this.benzi = benzi;
	}
}

public class Frati {

	public static void main(String[] args) throws IOException {
		String inputFile = "frati.in";
		MyScanner sc = new MyScanner(inputFile);
		int n = sc.nextInt();

		Concurs[] con1 = new Concurs[n];
		Concurs[] con2 = new Concurs[n];

		// fiecare din cei doi vectori va avea acces la aceleasi referinte ale
		// obiectelor , deci vor putea sti daca flagul a fost activat sau nu
		for (int i = 0; i < n; i++) {
			Concurs temp = new Concurs(sc.nextInt(), sc.nextInt());
			con1[i] = temp;
			con2[i] = temp;
		}

		// Sortam probele in functie de interesele fiecarui frate, in fiecare dintre cei
		// 2 vectori ( Fiecare va putea sti daca un obiect a fost luat sau nu deoarece
		// referintele din vectori pointeaza catre aceleasi obiecte de tip Concurs)
		Arrays.sort(con1, new Comparator1());
		Arrays.sort(con2, new Comparator2());

		int i;
		long sum1 = 0;
		long sum2 = 0;
		int i1 = 0;
		int i2 = 0;
		int cnt = 0;
		
		
		while (cnt < n) {
			// In functie de cine trebuie sa aleaga concursul , se face alegerea cea mai
			// optima dintre cele ramase
			if (cnt % 2 == 0) {
				// caut cel mai convenabil element (care sa nu fie luat)
				while (i1 < n && con1[i1].flag != 0) {
					i1++;
				}
				//Semnalam "fratelui" ca am ales deja acest concurs
				con1[i1].flag = 1;
				sum1 += con1[i1].jocuri;
				i1++;
			} else {
				while (i2 < n && con2[i2].flag != 0) {
					i2++;
				}
				con2[i2].flag = 1;
				sum2 += con2[i2].benzi;
				i2++;
			}
			cnt++;
		}

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("frati.out"));
		bufferedWriter.write(sum1 + " " + sum2);
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