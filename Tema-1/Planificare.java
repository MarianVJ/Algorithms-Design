import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Planificare {

	public static void main(String[] args) {

		String inpFile = "planificare.in";
		MyScanner sc = new MyScanner(inpFile);

		// Nr total de probe
		int nrProbe = sc.nextInt();
		// Durata maxima a unei probe
		long durata = sc.nextInt();
		// Pauza dintre doua probe care apartin aceluias concurs
		int pauza = sc.nextInt();
		int i, j;
		long[] vec = new long[nrProbe + 1];
		long variab;
		for (i = 1; i <= nrProbe; i++) {
			vec[i] = sc.nextInt();
		}

		long min;
		long bufferAux = 0;

		// in dp retine suma pierderilor minima dintre concursurile de la 1 la i , iar
		// in contor[i] sunt stocate numarul de de concursuri care contribuie la
		// realizarea acelor pierderi minime
		long[] dp = new long[nrProbe + 1];
		long[] contor = new long[nrProbe + 1];

		// Indexul maxim pana la care voi incerca sa unesc toate probleme intr-un
		// concurs , daca pentru concursul curent nu voi reusi sa fac aceast lucru ,
		// atunci nu mai are rost sa merg pana la acel indice nici la pasul urmator asa
		// ca ii voi modifica valoarea cu 1 in functie de ultima proba ce nu a putut fi
		// inglobata la pasul curent
		int indexMaxim;
		indexMaxim = 0;
		long min_nr = 0;
		int poz = 0;
		// Primul concurs nu poate fi cuplat cu nimeni (initial)
		dp[1] = (durata - vec[1]) * (durata - vec[1]) * (durata - vec[1]);

		for (i = 1; i < nrProbe; i++) {
			// Pentru fiecare concurs calculez folodind celelalte rezultate calculae
			// anterior , pierderea minima pentru concursurile 1...i si modul de cuplare a
			// concursurilor dintre un concurs[j] si cel curent
			j = i - 1;
			min = Long.MAX_VALUE;

			while (j >= indexMaxim) {
				poz = i;
				bufferAux = vec[poz];
				// Concatenez cate probe pot intr-un singur concurs
				while (poz > j + 1 && bufferAux + vec[poz - 1] + pauza <= durata) {
					bufferAux = bufferAux + vec[poz - 1] + pauza;
					poz--;
				}
				// Daca nu am ajuns la j+1 inseamna ca nu s-a putut realiza cumularea tututorr
				// probelor intr-un singur concurs
				if (poz != j + 1) {
					break;
				} else {
					// Daca da pt a calculat pierderea totala ma folosesc de pierderea minima
					// calculata anterior pentru primele j probe
					variab = (durata - bufferAux);
					variab = variab * variab * variab;
					variab = variab + dp[j];
					// Pastrez de fiecare data pierderea minima , dar si numarul concursurilor
					// necesare asociate pentru acea pierdere minima
					if (variab < min) {
						min = variab;
						min_nr = contor[j];
					}
				}
				j--;
			}
			// La final acutalizez minimul si numarul de concursuri
			dp[i] = min;
			contor[i] = min_nr + 1;
			// De fiecare data nu ma duc decat pana unde pot concatena toate probele intr-un
			// singur concurs
			indexMaxim = j + 1;
		}

		// Calculez pierderile minime pentru probe , dar de data aceasta pentru ultima
		// proba (sunt aceeasi pasi ca cei realizati anterior)
		// Dar voi considera pierderile concatenate la fiecare pas , 0 de
		// aceasta data deoarece nu mai exista niciun concurs dupa
		j = nrProbe - 1;
		min = Long.MAX_VALUE;
		while (j >= indexMaxim) {
			poz = i;
			bufferAux = vec[poz];
			while (poz > j + 1 && bufferAux + vec[poz - 1] + pauza <= durata) {
				bufferAux = bufferAux + vec[poz - 1] + pauza;
				poz--;
			}
			if (poz != j + 1) {
				break;
			} else {
				variab = 0 + dp[j];
				if (variab < min) {
					min = variab;
					min_nr = contor[j];
				}
			}
			j--;
		}
		dp[i] = min;
		contor[i] = min_nr + 1;

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("planificare.out"));
			bufferedWriter.write(dp[nrProbe] + " " + contor[nrProbe]);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
