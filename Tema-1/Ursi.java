import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ursi {
	static int MOD = 1000000007;

	public static void main(String[] args) throws IOException {

		String inpFile = "ursi.in";
		MyScanner sc = new MyScanner(inpFile);

		String st = sc.nextLine();
		char[] v = st.toCharArray();

		int i, j;
		int n = v.length;
		long[][] dp = new long[n + 2][n + 2];

		// Cazul de baza (folosit in cazul in care am primul caracter ^)
		dp[0][1] = 1;
		for (i = 1; i <= n; i++) {
			for (j = 1; j <= n; j++) {
				// Calculez in cate moduri pot forma fete cu primele i caractere avand (j-1 fete
				// deschise) ( j-1 datorita faptului ca am pus o linie si o
				// coloana in plus de zerouri in matricea dp) in functie de ultimul caracter din
				// cele i
				if (v[i - 1] == '^') {
					dp[i][j] = (((dp[i - 1][j - 1] % MOD * ((j - 1) % MOD)) % MOD) 
							+ (dp[i - 1][j + 1] % MOD)) % MOD;
				} else {
					dp[i][j] = ((dp[i - 1][j] % MOD) * ((j - 1) % MOD)) % MOD;
				}
			}
		}

		// Rezultatul este reprezentat de numarul de moduri in care putem construi fete
		// avand n caractere(toate) si 1-1 = 0 fete deschise
		int rez = (int) dp[n][1];

		FileWriter fileWriter = new FileWriter("ursi.out");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.printf("%d", rez);
		printWriter.close();

	}
}
