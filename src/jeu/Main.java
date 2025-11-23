package jeu;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Merci à w3schools.com, openclassroom.com.

public class Main {
	public static void main(String[] args){
		Mur m = new Mur();
		Paquet jeu = new Paquet();
		while (!jeu.carreauVide())
		{
			System.out.println(m.toString());
			int carteConsigne = jeu.piocher(m);
			jeu.afficherChoix(carteConsigne);
			Scanner sc = new Scanner(System.in);
			String choix = jeu.prendreCarreau(carteConsigne, sc,m);
			int ligne = sc.nextInt();
			int colonne = sc.nextInt();
			while (!m.poserCarreau(choix,jeu.largeurCarreau(choix), jeu.hauteurCarreau(choix), ligne, colonne))
			{
				choix = jeu.prendreCarreau(carteConsigne, sc,m);
				ligne = sc.nextInt();
				colonne = sc.nextInt();
			}
			jeu.retirerCarreau(choix);
		}
	}
}
