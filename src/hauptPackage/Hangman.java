package hauptPackage;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Thorleif Harder
 * Spielname: Hangman 
 * Datum: 19.11.2021
 */

public class Hangman {

	public static boolean nichtGeloest = true;

	@SuppressWarnings({ "resource" })

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int fehlerZaehler = 1;
		final String zufallsWort = suchwort();
		String loesung = loesung(zufallsWort);
		String loesungLeerzeichen = "";
		String eingaben = "";
		String getätigteEingaben = "";

		System.out.println("Spielen wir Hangman!\n");
		System.out.println(zeigeHangman(1));
		System.out.println(leerzeichenLoesung(loesung));
		System.out.println(loesungLeerzeichen);

		while (nichtGeloest) {
			System.out.print("\nRaten sie einen Buchstaben: ");
			String eingabeNutzende = scanner.nextLine().toUpperCase();
			if (eingabeNutzende.length() > 0)
				getätigteEingaben += eingabeNutzende + ", ";

			while (eingabeNutzende.length() < 1 | eingabeNutzende.length() > 1) {
				System.out.println("Deine Eingabe ist falsch!" + "\n Mach eine neue Eingabe: ");
				eingabeNutzende = scanner.nextLine().toUpperCase();
				if (eingabeNutzende.length() > 0)
					getätigteEingaben += eingabeNutzende + ", ";
			}
			loesung = hngmn(zufallsWort, eingabeNutzende, loesung);

			if (zufallsWort.contains(eingabeNutzende) && !eingaben.contains(eingabeNutzende)) {
				loesungLeerzeichen += "";
				System.out.println(
						"\n" + leerzeichenLoesung(loesung) + "\nBereits gemachte Eingaben: " + getätigteEingaben);
			} else {
				loesungLeerzeichen += "";
				fehlerZaehler++;
				System.out.println("\n" + zeigeHangman(fehlerZaehler) + "\nDer Buchstabe ist falsch!\n");
				System.out.println(
						"\n" + leerzeichenLoesung(loesung) + "\nBereits gemachte Eingaben: " + getätigteEingaben);
			}
			eingaben += eingabeNutzende;

			if (fehlerZaehler >= 7) {
				nichtGeloest = false;
			}
			if (loesung.equals(zufallsWort)) {
				nichtGeloest = false;
			}

		}

		if (loesung.equals(zufallsWort)) {
			System.out.println("\nDu hast das Wort mit nur " + (fehlerZaehler - 1) + " Fehlern richtig erraten!");
		}
		if (fehlerZaehler >= 7) {
			System.out.println("\nHangman haengt!\nMein Wort lautete: " + zufallsWort);
		}

	}// main

	/**
	 * Erzeugt einen String aus einem reingegebenen Wortes, welcher alle Zeichen
	 * durch ein Leerzeichen trennt.
	 *
	 * @param loesung: Ein String, dessen Zeichen durch Leerzeichen getrennt werden
	 *                 sollen.
	 * @return Gibt einen String zurueck, der die Zeichen eines in die Methode
	 *         gegebenen Wortes durch Leerzeichen trennt.
	 */
	public static String leerzeichenLoesung(String loesung) {
		String loesungLeerzeichen = "";
		for (char c : loesung.toCharArray()) {
			loesungLeerzeichen += c + " ";
		}
		return loesungLeerzeichen;
	}

	/**
	 * Erzeugt einen String aus Unterstrichen, welcher die Laenge des Zufallswortes
	 * hat.
	 * 
	 * @param zufallsWortLaenge: Ein String, der die Laenge des auszugebenden
	 *                           Strings bestimmt.
	 * @return Gibt einen String aus Unterstrichen mit der Laenge des in die Methode
	 *         gegebenen Wortes zurueck.
	 */
	public static final String loesung(String zufallsWortLaenge) {
		String loesung = "";
		for (int i = 0; i < zufallsWortLaenge.length(); i++) {
			loesung += "_";
		}
		return loesung;
	}

	/**
	 * Erzeugt ein Wort, welches aus einer Liste zufaellig ausgesucht wird.
	 * 
	 * @return Gibt einen String mit einem Zufallswort zurueck.
	 */
	public static String suchwort() {
		String ausgabe = "";
		final List<String> woerter = Arrays.asList("Adler");
		ausgabe = woerter.get((int) (Math.random() * woerter.size())).toUpperCase();
		return ausgabe;
	}

	/**
	 * Erstellt einen String unter Beruecksichtigung des Zufallswortes. Falls der
	 * Buchstabe der Stelle null des eingegebene Strings im Zufallswort enthalten
	 * ist, wird er an den String an der geprueften Stelle eingesetzt.
	 * 
	 * @param zufallsWort: Aus vorgegebener Liste generiertes Zufallswort als
	 * @param eingabe:     Nicht leerer, vom Nutzer eingegebener String.
	 * @param loesung:     String mit der Laenge des Zufallswortes, welcher zum
	 *                     Start des Programms aus Unterstrichen besteht. Die
	 *                     Unterstriche werden nach und nach durch richtige
	 *                     Nutzereingaben an den richtigen index-Stellen ersetzt.
	 * 
	 * @return String, der sich aus den richtigen Nutzereingaben zusammensetzt, bzw
	 *         an den uebrigen oder bei falscher Eingabe aus Unterstrichen.
	 */
	public static final String hngmn(String zufallsWort, String eingabe, String loesung) {

		String ausgabe = "";
		final char ebuch = eingabe.charAt(0);
		int index = -1;
		for (char c : zufallsWort.toCharArray()) {
			index++;
			if (ebuch == c) {
				ausgabe += c;
			} else {
				ausgabe += loesung.charAt(index);
			}
		}
		return ausgabe;
	}

	/**
	 * Erzeugt einen String, der je nach Eingabe die einzelnen Stadien des
	 * Hangman-Galgen beinhaltet.
	 * 
	 * @param zaehler: Eine ganze Zahl zwischen 1 und 7 die bestimmt, welches
	 *                 Stadium des Hangman-Galgen in dem Ausgabe-String eingefuegt
	 *                 werden soll.
	 * @return Gibt einen String zurueck, welcher den Hangman-Galgen des
	 *         entsprechend festgelegten Stadiums enthaelt.
	 */
	public static StringBuffer zeigeHangman(final int zaehler) {
		final StringBuffer ausgabe = new StringBuffer();
		if (zaehler == 1) {
			ausgabe.append(" +-----+" + "\n")
				.append(" |/" + "\n")
				.append(" |" + "\n")
				.append(" |" + "\n")
				.append(" |" + "\n")
				.append(" ***" + "\n")
				.append("************" + "\n");
		}
		return ausgabe;
	}
}