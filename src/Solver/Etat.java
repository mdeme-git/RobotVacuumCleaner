package Solver;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Etat correspond a une capture de l'environnement Ã un instant t
 */

public class Etat implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int nb_piece = 25;

	protected HashMap<Integer, Piece> etat_carte = new HashMap<Integer, Piece>();
	protected int position_Actuelle_Agent;

	public Etat parent = null; // l'etat initial n'a pas de parent

	public Action action = null; // pour l'etat initial

	public int profondeur = 0; // Pour l'etat initial

	public int cout = 0; // Pour l'etat initial

	/**
	 * 
	 * @param env : l'environnement dynamique
	 */

	public Etat(Environnement env) {
		this.etat_carte = env.getCarte();
		this.position_Actuelle_Agent = env.getPositionAgent();
	}

	/**
	 * Un autre constructeur permettant d'obtenir un Ã©tat propre de l'environnement
	 * : l'etat desire !
	 */

	public Etat() {

		for (int i = 1; i < nb_piece + 1; i++) {
			this.etat_carte.put(i, new Piece(i));
		}

	}

	/**
	 * 
	 * @param e : un certain Ã©tat de l'environnement
	 * @param a : une action
	 * @return l'etat resultant de l'action a sur l'environnement Ã l'etat e. Si
	 *         l'action a n'est pas possible, alors l'etat reste inchangÃ©.
	 */

	public Etat transition2(Etat e, Action a) {

		Etat res = null;

		Piece p = null;

		if (!(a.equals(null) && !a.isNoOp())) {

			res = (Etat) Copy.DeepCopy.copy(e);

			p = (Piece) Copy.DeepCopy.copy(e.etat_carte.get(e.position_Actuelle_Agent));

			if (a.getName().equals("Ramasser")) {
				p.setBijou(false);
				res.etat_carte.put(p.getNumero(), p);
				res.setAction(p.RAMASSER);
				res.setParent(e);
				res.setProfondeur(e.profondeur + 1);
				res.setCout(e.cout + 1);

			} else if (a.getName().equals("Aspirer")) {
				p.setOrdure(false);
				if (p.isBijou())
					p.setBijou(false); // Aspirer = aspirer + ramasser

				res.etat_carte.put(p.getNumero(), p);
				res.setAction(p.ASPIRER);
				res.setParent(e); // e est le parent et non this
				res.setProfondeur(e.profondeur + 1);
				res.setCout(e.cout + 1);

			}

			else if (a.getName().equals("Haut") && (p.isHaut())) {
				res.setPosition_Actuelle_Agent(e.getPosition_Actuelle_Agent() - 5);
				res.setAction(p.HAUT);
				res.setParent(e);
				res.setProfondeur(e.profondeur + 1);
				res.setCout(e.cout + 1);

			}

			else if (a.getName().equals("Bas") && (p.isBas())) {
				res.setPosition_Actuelle_Agent(e.getPosition_Actuelle_Agent() + 5);
				res.setAction(p.BAS);
				res.setParent(e);
				res.setProfondeur(e.profondeur + 1);
				res.setCout(e.cout + 1);

			}

			else if (a.getName().equals("Droite") && (p.isDroite())) {
				res.setPosition_Actuelle_Agent(e.getPosition_Actuelle_Agent() + 1);
				res.setAction(p.DROITE);
				res.setParent(e);
				res.setProfondeur(e.profondeur + 1);
				res.setCout(e.cout + 1);

			} else if (a.getName().equals("Gauche") && (p.isGauche())) {
				res.setPosition_Actuelle_Agent(e.getPosition_Actuelle_Agent() - 1);
				res.setAction(p.GAUCHE);
				res.setParent(e);
				res.setProfondeur(e.profondeur + 1);
				res.setCout(e.cout + 1);
			}

			else if (a.getName().equals("Ne rien faire")) {
				res.setAction(p.NE_RIEN_FAIRE);
				res.setParent(e);
				res.setProfondeur(e.profondeur + 1);

			}

			return res;

		} else
			return null;

	}

	public ArrayList<Action> actionsPossible() {

		ArrayList<Action> res = new ArrayList<Action>();

		Piece p = this.etat_carte.get(this.position_Actuelle_Agent);

		if (p.isRamasser()) {
			p.RAMASSER.setPiece(p);
			res.add(p.RAMASSER);
		}
		if (p.isAspirer()) {
			p.ASPIRER.setPiece(p);
			res.add(p.ASPIRER);
		}

		if (p.isBas()) {
			p.BAS.setPiece(p);
			res.add(p.BAS);

		}
		if (p.isHaut()) {
			p.HAUT.setPiece(p);
			res.add(p.HAUT);
		}

		if (p.isDroite()) {
			p.DROITE.setPiece(p);
			res.add(p.DROITE);
		}

		if (p.isGauche()) {
			p.GAUCHE.setPiece(p);
			res.add(p.GAUCHE);
		}

		p.NE_RIEN_FAIRE.setPiece(p);
		res.add(p.NE_RIEN_FAIRE);

		return res;

	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Etat getParent() {
		return parent;
	}

	public void setParent(Etat parent) {
		this.parent = parent;
	}

	public int getProfondeur() {
		return profondeur;
	}

	public void setProfondeur(int profondeur) {
		this.profondeur = profondeur;
	}

	public HashMap<Integer, Piece> getEtat_carte() {
		return etat_carte;
	}

	public void setEtat_carte(HashMap<Integer, Piece> etat_carte) {
		this.etat_carte = etat_carte;
	}

	public int getPosition_Actuelle_Agent() {
		return position_Actuelle_Agent;
	}

	public void setPosition_Actuelle_Agent(int position_Actuelle_Agent) {
		this.position_Actuelle_Agent = position_Actuelle_Agent;
	}

	/* AFFICHAGE */

	public void show_Etat() {
		System.out.println("Position de l'agent : " + this.position_Actuelle_Agent + "\n\n Environnement :");
		for (Map.Entry<Integer, Piece> l : etat_carte.entrySet()) {
			System.out.println("Key: " + l.getKey() + "\n" + " - Value : " + l.getValue().toString());
		}
	}

}
