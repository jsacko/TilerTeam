package jeu;
import static constantes.Constante.*;

import java.util.ArrayList;
import java.util.Random;

public class Mur {
	private ArrayList<ArrayList<String>> mur ;
	private int nbLignes = 0;
	private static final int pasExtension = HAUTEURMAX;
	
	public Mur() {
		initialiserMur();
		placerPieceNeutre();
	}
	public String toString(){
		String s = new String();
		boolean ligneVide = true;
		int limite = 1;
		for (int iLigne = 1; iLigne < mur.size(); iLigne += 1,limite += 1)
		{
			ligneVide = true;
			for (int iColonne = 1; iColonne < NB_COLONNES;iColonne += 1)
				if (!mur.get(iLigne).get(iColonne).equals(" "))
				{
					ligneVide = false;
					break;
				}
			if (ligneVide)
				break;
		}
		for (int iLigne = limite; iLigne >=0; iLigne -= 1)
		{
			for (int iColonne = 0; iColonne < NB_COLONNES;iColonne += 1)
				s+= mur.get(iLigne).get(iColonne);
			s+="\n";
		}
		return s;
	}			
	public int score(){
		int nbNiveau= 0;
		boolean lignePleine = true;
		for (int iLigne = 1; iLigne < mur.size();iLigne += 1,nbNiveau += 1)
		{
			for (int iColonne = 1; iColonne < NB_COLONNES; iColonne +=1 )
			{
				if (mur.get(iLigne).get(iColonne).equals(" "))
				{
					lignePleine = false;
					break;
				}
			}
			if (!lignePleine)
				break;
		}
		return nbNiveau * POINTPARNIVEAU;
		
	}
	public boolean poserCarreau(String carreau, int largeur, int hauteur, int ligne, int colonne){
		//System.out.println("Vous essayez de poser le carreau "+carreau+", de largeur "+largeur+" et de hauteur "+hauteur+"\nSur la ligne "+ligne+", colonne "+colonne);
		if (!(colonne >= 0 && colonne + largeur<=NB_COLONNES && ligne > 0 && ligne <= nbLignes))
		{
			System.out.println("Vous dépassez la zone à carreler");
			return false;
		}
		for (int i = 0; i < largeur; i+= 1)
			if (mur.get(ligne-1).get(colonne+i) == " " || mur.get(ligne).get(colonne+i) != " ")
			{
				System.out.println("Le carreau n'est pas stable ou touche au moins un carreau déjà posé");
				return false;
			}
		if (ligne + hauteur > nbLignes)
			this.augmenterMur();
		boolean clonageGauche = colonne > 1?clonageCote(largeur, hauteur, ligne, colonne):false;
		boolean clonageDroite = colonne+largeur < NB_COLONNES?clonageCote(largeur, hauteur, ligne,colonne +largeur+1):false;
		if (clonageDessous(largeur, hauteur, ligne, colonne)|| clonageGauche || clonageDroite)
		{
			System.out.println("Votre carreau clone un autre carreau");
			return false;
		}
		for (int iLigne = ligne+hauteur-1; iLigne >= ligne; iLigne-=1)
			for (int iColonne = 0; iColonne < largeur; iColonne +=1)
				mur.get(iLigne).set(colonne+iColonne, carreau);
		return true;
	}
	private boolean clonageCote(int largeur, int hauteur, int ligne, int colonne){
		boolean cotePareil = true;
		String aCote = mur.get(ligne).get(colonne-1);
		for (int j = 1; j < hauteur; j += 1)
			if (mur.get(ligne + j).get(colonne - 1) != aCote)
			{
				cotePareil = false;
				break;
			}
		boolean differentEnDessous = ligne==1?true:mur.get(ligne-1).get(colonne-1) != aCote;
		boolean differentAuDessus = mur.get(ligne+hauteur).get(colonne-1) != aCote;
		if (cotePareil && differentEnDessous && differentAuDessus)
			return true;
		return false;
	}
	private boolean clonageDessous(int largeur, int hauteur, int ligne, int colonne){
		boolean cotePareil = true;
		String enDessous = mur.get(ligne-1).get(colonne);
		for (int j = 1; j < largeur; j += 1)
			if (mur.get(ligne-1).get(colonne+j) != enDessous)
			{
				cotePareil = false;
				break;
			}
		boolean differentAvant = colonne==1?true:mur.get(ligne-1).get(colonne-1) != enDessous;
		boolean differentApres = colonne+largeur == NB_COLONNES?true:mur.get(ligne-1).get(colonne+largeur) != enDessous;
		if (cotePareil && differentAvant && differentApres && ligne >1)
			return true;
		return false;
	}
	public void augmenterMur () {
		for (int i = 0; i < pasExtension; i += 1)
		{
				mur.add(new ArrayList<>());
				nbLignes += 1;
				mur.get(nbLignes).add(nbLignes+"");
				for (int iColonne = 1; iColonne < NB_COLONNES; iColonne += 1)
					mur.get(nbLignes).add(" ");
		}
	}
	
	private void placerPieceNeutre(){
		int[] posPossible =  {1,NB_COLONNES-1};
		int[] taillePossible = {1,HAUTEURMAX};
		Random rd = new Random();
		int x = posPossible[rd.nextInt(posPossible.length)];
		int hauteur = taillePossible[rd.nextInt(taillePossible.length)];
		int largeur = hauteur==HAUTEURMAX?1:LARGEURMAX;
		if (x == 1)
		{
			for (int iLigne = hauteur; iLigne > 0;iLigne -= 1)
				for(int iColonne = x; iColonne <= largeur; iColonne += 1)
					if (iColonne == x || iLigne == 1)
						mur.get(iLigne).set(iColonne, IDPIECENEUTRE);
		}
		else
			for (int iLigne = hauteur; iLigne > 0;iLigne -= 1)
				for(int iColonne = x; iColonne >= NB_COLONNES-largeur; iColonne -= 1)
					if (iColonne == x || iLigne == 1)
						mur.get(iLigne).set(iColonne, IDPIECENEUTRE);
	}
	
	private void initialiserMur() {
		mur = new ArrayList<>(NB_COLONNES);
		mur.add(new ArrayList<>());
		mur.get(0).add(" ");
		for (int iColonne = 1; iColonne < NB_COLONNES;iColonne += 1)
			mur.get(0).add(iColonne+"");
		for (int iLigne = 1; iLigne < NB_LIGNES; iLigne += 1)
		{
			mur.add(new ArrayList<>());
			nbLignes += 1;
			mur.get(iLigne).add(nbLignes+"");
			for (int iColonne = 1; iColonne < NB_COLONNES; iColonne += 1)
				mur.get(iLigne).add(" ");
		}
	}
}
