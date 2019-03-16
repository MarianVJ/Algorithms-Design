import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Numaratoare {

	static int numar;
	// Daca nu se ajunnge la indicele care este cerut in fisierul de input ,
	// finalSol va fi "-"
	static String finalSol = "-";
	static int dimensiune;
	static int aux;
	static int ii;
	// Indicele in care se retine a cata partitie trebuie calculata
	static int indice;

	public static void solve(int sumCrt, int nrTerm, String solCrt, int lastElem) {

		// Daca a fost gasita o suma valida atunci verificam daca am gasit solutia daca
		// nu actualizam indicele si oprim executia firului curent
		if (sumCrt == numar && nrTerm == dimensiune) {
			indice--;
			if (indice == -1) {
				finalSol = Integer.toString(numar) + "=" + solCrt.substring(0, solCrt.length() - 1);
			}
			return;
		}
		// Aceasta conditie este pentru sumele care titusi au suma corespunzatoare dar
		// nu si numarul de termeni ( sau invers)
		if (sumCrt >= numar || nrTerm >= dimensiune) {
			return;
		}
		// Cautam pana la minimul dintre ultimul element ( deoarece numerele trebuie sa
		// fie in ordine descrescatoare) si cel mai mare element ce poate fi adaugat
		aux = Math.min(lastElem, (numar - sumCrt));
		for (int ii = aux; ii >= 1; ii--) {
			// Initiem un nou apel doar daca nu depasim numarul maxim de termeni sau suma
			// maxima
			// sau daca mai exista "destul spatiu" in suma pentru a pune doar cifre de unu
			if (nrTerm + 1 <= dimensiune && indice > -1 && sumCrt + ii <= numar
					&& numar - sumCrt - ii >= dimensiune - nrTerm - 1) {
				solve(sumCrt + ii, nrTerm + 1, solCrt + Integer.toString(ii) + "+", ii);
			}

		}
	}

	public static void main(String[] args) throws IOException {
		String inpFile = "numaratoare.in";

		MyScanner sc = new MyScanner(inpFile);
		// Numarul ce trebuie partitionat
		numar = sc.nextInt();

		// Dimensiune este in cate numere trebuie partitionat intregul nostru
		dimensiune = sc.nextInt();

		// Indicele , este indicele sumei ce trebuie aflata
		indice = sc.nextInt();

		// Apelul recursiv
		solve(0, 0, "", numar - dimensiune + 1);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("numaratoare.out"));
		bufferedWriter.write(finalSol);
		bufferedWriter.close();

	}

}
