package jeu;
import static constantes.Constante.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Paquet {
	private Map<String, Integer> dictioCarreau;
	private ArrayList <ArrayList<String>> carreauDispo;
	private ArrayList <Integer> pioche;
	private int score;
	public Paquet(){
		initialiserDictio();
		initialiserCarrDispo();
		initialiserPioche();
		score = 0;
	}
	public boolean carreauVide(){
		for (int iCouleur = 0; iCouleur < NBCOULEURS; iCouleur += 1)
			if (!carreauDispo.get(iCouleur).isEmpty())
				return false;
		return true;
	}
	public void stop(Mur m){
		int nbCarteEcarte = Math.abs(score);
		int nbCarreauNonPose = 0;
		score += m.score();
		for(int iCouleur = 0;iCouleur < NBCOULEURS; iCouleur += 1)
			for (int iCarreau = 0;iCarreau < carreauDispo.get(iCouleur).size(); iCarreau += 1)
			{
				nbCarreauNonPose += 1;
				score -= 1;
			}
		System.out.println(score+" points ("+m.score()/POINTPARNIVEAU+" niveaux complets, "+nbCarreauNonPose+" carreaux non posés, "+nbCarteEcarte+" cartes écartées)");
		System.exit(1);
	}
	public void majScore(int point){
		score += point;
	}
	public int hauteurCarreau(String carreau){
		return dictioCarreau.get(carreau)%10;
	}
	public int largeurCarreau(String carreau){
		return dictioCarreau.get(carreau)/10;
	}
	public void retirerCarreau(String carreau ){
		for (ArrayList<String> l:carreauDispo)
			for (int i = 0;i < l.size(); i += 1)
				if(l.get(i).equals(carreau))
					l.remove(i);
	}
	public String prendreCarreau(int carte, Scanner sc, Mur m){
		String choix = sc.next();
		if (choix.equals("stop"))
			this.stop(m);
		else if (choix.equals("next"))
		{
			score -= 1; 
			carte = this.piocher(m);
			this.afficherChoix(carte);
			choix = this.prendreCarreau(carte, sc, m);
		}
		else if (!carreauDispo.get(carte).contains(choix))
		{
			System.out.println("Le carreau choisi n'est pas désigné par la consigne. Veuillez réessayer.");
			int ligne = sc.nextInt();
			int colonne = sc.nextInt();
			choix = this.prendreCarreau(carte, sc, m);
		}
		return choix;
	}
	public String afficherChoix(int carte){
		int colonne;
		for (int iHauteur = HAUTEURMAX; iHauteur > 0; iHauteur -= 1)
		{
			for (int iCarreau = 0;iCarreau < carreauDispo.get(carte).size();iCarreau += 1)
			{
				colonne = 0;
				if (dictioCarreau.get(carreauDispo.get(carte).get(iCarreau))%10 >= iHauteur)
				{
					for (int iLettre = 0; iLettre < dictioCarreau.get(carreauDispo.get(carte).get(iCarreau))/10; iLettre += 1)
						System.out.print(carreauDispo.get(carte).get(iCarreau));
					colonne -= dictioCarreau.get(carreauDispo.get(carte).get(iCarreau))/10;
				}
				colonne += 1 + dictioCarreau.get(carreauDispo.get(carte).get(iCarreau))/10 ;
				for (int iEspace = 0; iEspace < colonne; iEspace += 1)
					System.out.print(" ");
			}
			System.out.println();
		}
		
		return "";
	}
	public int piocher(Mur m){
		if (pioche.isEmpty())
			stop(m);
		int tmp = pioche.get(0);
		pioche.remove(0);
		if (carreauDispo.get(tmp).isEmpty())
		{
			System.out.println("Aucun carreau ne satisfait la consigne "+CARTES[tmp]+", vous repiochez et perdez un point");
			score -= 1;
			return piocher(m);
		}
		else
		{
			System.out.println(CARTES[tmp]);
			return tmp;
		}	
	}
	public String toString() {
		String s = new String();
		s+= "Les identifiants des carreaux " + dictioCarreau+"\n";
		s+= "Les carreaux encore disponibles "+carreauDispo+"\n";
		s+= "La pioche "+pioche+"\nLe score "+score;
		return s;
	}
	private void initialiserCarrDispo(){
		carreauDispo = new ArrayList<>(NBCARTESDIFFERENTE);
		for (int tas = 0; tas < NBCARTESDIFFERENTE; tas += 1)
			carreauDispo.add(new ArrayList<>());
		// Initialisation des carreaux de couleurs
		for (int i = 0; i < NBCOULEURS; i+=1)
			for (int j = 0; j < NBCARTESCOULEUR;j += 1)
			{
				if ( i == TASROUGE)
					carreauDispo.get(i).add(IDCARREAUX[j].toUpperCase());
				else
					carreauDispo.get(i).add(IDCARREAUX[j]);
			}
		// Initialisation des carreaux de tailles
		for (int tas = TASCARREAUTAILLE[0]; tas <= TASCARREAUTAILLE[TASCARREAUTAILLE.length-1]; tas += 1)	
			for (int i = 0; i < NBCOULEURS; i += 1)
				for (int j = 0; j < NBCARTESCOULEUR; j += 1)
					carreauDispo.get(tas).add(carreauDispo.get(i).get(j));
		// Tri des carreaux de tailles
		for (int tas = TASCARREAUTAILLE[0], iTaille = 1; tas <= TASCARREAUTAILLE[TASCARREAUTAILLE.length-1]; tas += 1, iTaille += 1)
			for (int iCarreau = 0; iCarreau < carreauDispo.get(tas).size(); iCarreau += 1)
				if (dictioCarreau.get(carreauDispo.get(tas).get(iCarreau))%10 != iTaille && dictioCarreau.get(carreauDispo.get(tas).get(iCarreau))/10 != iTaille)
				{
					carreauDispo.get(tas).remove(iCarreau);
					iCarreau -= 1;
				}
	}
		
	private void initialiserDictio(){		
		dictioCarreau = new HashMap<>();
		for (int iCarreau = 0; iCarreau < IDCARREAUX.length;iCarreau += 1)
			dictioCarreau.put(IDCARREAUX[iCarreau], DIMCARREAUX[iCarreau]);
		for (int iCarreau = 0; iCarreau < IDCARREAUX.length;iCarreau += 1)
			dictioCarreau.put(IDCARREAUX[iCarreau].toUpperCase(), DIMCARREAUX[iCarreau]);
	}
	private void initialiserPioche(){
		pioche = new ArrayList<>();
		for (int tas = 0; tas < NBCARTESDIFFERENTE; tas +=1 ) 
			for (int c = 0; c < NBCONSIGNES[tas]; c += 1 ) 
				pioche.add(IDCARTES[tas]);
		Collections.shuffle(pioche);
	}
}
