package Solver;

import java.util.ArrayList;
import java.util.HashMap;

public class Environnement {

	protected HashMap<Integer, Piece> carte = new HashMap<Integer, Piece>(); // la cl� est le numero de la piece sous
																				// forme de Integer( "1")

	protected int positionAgent = 2;

	// Environnement mesure la performance de l'Agent
	public int mesurePerformance(Agent a, Integer MA) {

		int res = 0;

		ArrayList<Action> intentions = new ArrayList<>();
		for (int i = 0; i < MA; i++) {
			intentions.add(a.getIntentions().get(i));
		}
		HashMap<Integer, ArrayList<String>> l = new HashMap<Integer, ArrayList<String>>();

		for (int i : carte.keySet()) {
			ArrayList<String> la = new ArrayList<String>();

			for (Action act : intentions) {
				int p = act.getPiece().getNumero();
				if (i == p) {

					la.add(act.getName());
				}
			}
			l.put(i, la);

		}

		for (HashMap.Entry<Integer, ArrayList<String>> i : l.entrySet()) {

			int nPiece = i.getKey();

			if (this.carte.get(nPiece).isBijou() && !(this.carte.get(nPiece).isOrdure())) {
				if (!(i.getValue().contains("Ramasser"))) {
					// Bijou dans la piece il ne l'a pas ramass�e
					res--;
				} else if ((i.getValue().contains("Aspirer"))) {
					// Bijou dans la piece il l'a aspir�e
					res -= 2;
				}

			} else if (this.carte.get(nPiece).isOrdure() && !(this.carte.get(nPiece).isBijou())) {
				if (!(i.getValue().contains("Aspirer"))) {
					// Ordure dans la piece il ne l'a pas aspir�e
					res--;
				} else if ((i.getValue().contains("Ramasser"))) {
					// Ordure dans la piece il l'a ramass�e
					res -= 2;
				}
			} else if (this.carte.get(nPiece).isBijou() && this.carte.get(nPiece).isOrdure()) {

				if (!(i.getValue().contains("Aspirer")) && !(i.getValue().contains("Ramasser"))) {
					// Il n'a ni ramass�e ni aspir�e
					res -= 1;
				} else if ((i.getValue().contains("Aspirer") && !(i.getValue().contains("Ramasser")))) {
					// Il a juste aspir�e
					res -= 2;
				} else if (!((i.getValue().contains("Aspirer")) && (i.getValue().contains("Ramasser")))) {
					// Il a juste ramass�e
					res -= 2;
				}
			}
		}
		return res;
	}

	// Cr�aation des 25 piecees de l'environnement
	public Environnement() {
		for (int i = 1; i <= 25; i++) {
			Piece piece = new Piece(i);
			this.add_Piece(piece);
		}
	}

	public int getPositionAgent() {
		return positionAgent;
	}

	public void setPositionAgent(int positionAgent) {
		this.positionAgent = positionAgent;
	}

	public HashMap<Integer, Piece> getCarte() {
		return carte;
	}

	public void setCarte(HashMap<Integer, Piece> carte) {
		this.carte = carte;
	}

	public void add_Piece(Piece p) {
		carte.put(p.numero, p);

	}

	/* AFFICHAGE */

	public void show_environnement() {
		for (HashMap.Entry<Integer, Piece> l : carte.entrySet()) {
			System.out.println("Key: " + l.getKey() + "\n" + " - Value : " + l.getValue().toString());
		}
	}

	/* Running environment */

	public void runEnv() {
		for (HashMap.Entry<Integer, Piece> l : carte.entrySet()) {
			if (l.getValue().shouldBethereNewDirty())
				l.getValue().generateDirty();
			if (l.getValue().shouldBethereNewJewel())
				l.getValue().generateJewel();
		}
	}

}
