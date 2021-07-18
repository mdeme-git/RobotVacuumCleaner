package Solver;

import java.io.Serializable;

public class Piece implements Serializable {
	
	public int numero ; // numero de la pi√®ce
	
	public boolean ordure = false; // True si il y'a des ordures
	
	public boolean bijou = false; // True si bijou

	
	/**
	 * Listes d'actions dans une piece
	 */

	public final Action HAUT = new Action("Haut");

	public final Action BAS = new Action("Bas");

	public final Action DROITE = new Action("Droite");

	public final Action GAUCHE = new Action("Gauche");
	
	public final Action RAMASSER = new Action("Ramasser");
	
	public final Action ASPIRER = new Action("Aspirer");
	
	public final Action NE_RIEN_FAIRE = new Action("Ne rien faire");

	public final Action NO_OP = new Action("NoOp"){
		@Override
		public boolean isNoOp() {
			return true;
		}
	};
	
		
	/**
	 * Constructeur
	 * @param num: numero de la piece √† construire
	 */
	public Piece(int num) {
		
		this.numero = num;
	}
	

	public int getNumero() {
		return numero;
	}
	

	public boolean isOrdure() {
		return ordure;
	}
	

	public void setOrdure(boolean ordure) {
		this.ordure = ordure;
	}
	
	
	// Faut-il generer des ordures? 
	
	protected boolean shouldBethereNewDirty() {
		double proba = Math.random(); 
		if(proba< 0.05)
			return true;
		else
			return false;
	}
	
	
	// generation ordures
	
	public void generateDirty() {
		this.ordure = true;
	}
	
	
	// Faut-il generer un bijou? 
	
		protected boolean shouldBethereNewJewel() {
			double proba = Math.random(); 
			if(proba< 0.001)
				return true;
			else
				return false;
		}
		
		
	// generation de bijou
	public void generateJewel() {
		this.bijou = true;
	}
	

	public boolean isBijou() {
		return bijou;
	}
	

	public void setBijou(boolean bijou) {
		this.bijou = bijou;
	}
	
	
	public boolean isBas() {
		if((this.numero > 0) && (this.numero+5 <= 25)) // 25 correspond au nombre de piece dans notre cas
			return true;
		else
			return false;
	}
	
	
	public boolean isHaut() {
		if((this.numero <= 25) && (this.numero-5>0))
			return true;
		else
			return false;
	}
	
	
	public boolean isDroite() {
		
		if((this.numero <= 25) && (this.numero+1)%5 != 1)
			return true;
		else
			return false;
	}
	
	
	public boolean isGauche() {
		if((this.numero <= 25) && (this.numero-1)%5 != 0)
			return true;
		else
			return false;
	}
	
	
	public boolean isRamasser() {
		return this.bijou;
	}
	
	
	public boolean isAspirer() {
		return this.ordure;
	}
	
	
public String toString() {
		
		String etat_ordure = "Propre";
		String etat_bijou = "Pas de bijou dans la piËce";
		if(this.bijou)
			etat_bijou = "Bijou dans la piËce";
		if(this.ordure)
			etat_ordure ="Sale";
		
		return "Piece " + this.numero + "\n"+ etat_bijou + "\n" + etat_ordure + "\n" ;
		
	}
	
	
	

}
