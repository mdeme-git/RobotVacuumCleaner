package Solver;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class StrategieExplorationIDS {

	ArrayList<Etat> cutoff = new ArrayList<>();
	ArrayList<String> sommetsVisites = new ArrayList<>();
	int stop;
	int limit = 5;

	/**
	 * 
	 * @param e : un certain état de l'environnement
	 * @return : retourne true si toutes les pièces sont propres et peu importe la
	 *         position de l'agent. C'est pourquoi on teste uniquement les états des
	 *         pièces avec la carte.
	 */

	public boolean goal_test(Etat e, Etat Desire) {
		// ** Passer plutot le desire en paramètre
		for (int i = 0; i < e.getEtat_carte().keySet().size(); i++) {
			if (!(e.getEtat_carte().values().toString().equals(Desire.getEtat_carte().values().toString()))) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<Etat> Expand(Etat e_initial) {

		final Etat einitial = e_initial;
		final HashMap<Integer, Piece> etat_initial = e_initial.getEtat_carte();
		Piece p = einitial.etat_carte.get(einitial.position_Actuelle_Agent);
		final boolean ordure = p.isOrdure();
		final boolean bijou = p.isBijou();
		ArrayList<Etat> res = new ArrayList<Etat>();
		// Toutes les actions possibles d'une piece
		for (Action a : einitial.actionsPossible()) {
			Etat e1 = new Etat();
			final Etat e = e1.transition2(einitial, a);
			if (e != null) {
				res.add(e);
			}
		}
		return res;

	}

	public ArrayList<Etat> Depth_Limited_Search(Etat e_initial, Etat e_desire, int limit) {
		// Créer un nouveau Etat pour l'Etat initial utilité??
		return Recursive_DLS(e_initial, e_desire, this.limit);
	}

	public ArrayList<Etat> Recursive_DLS(Etat e_initial, Etat e_desire, int limit) {

		ArrayList<Etat> solution = new ArrayList<>();

		boolean cutoff_occured = false;
		if (this.goal_test(e_initial, e_desire)) {
			solution.add(e_initial);
			stop = 1;
			return solution;

		} else if (e_initial.getProfondeur() == this.limit) {
			cutoff_occured = true;
			stop = 1;
			cutoff.add(e_initial);
			return cutoff;

		} else {
			ArrayList<Etat> test = new ArrayList<>();
			test = this.Expand(e_initial);

			// Initialise un etat
			Etat e = new Etat();
			int index = 0;
			// On parcourt tous les etats de test
			while (index < test.size() && stop != 1) {
				e = test.get(index);
				// si l'etat a deja etait visité
				if (sommetsVisites.contains(e.getAction() + " " + e.getPosition_Actuelle_Agent())) {
					index++;
				} else {
					if (index + 1 == test.size()) {
						index = 0;
					}
					sommetsVisites.add(e.getAction() + " " + e.getPosition_Actuelle_Agent());
					solution = Recursive_DLS(e, e_desire, this.limit);
				}
			}
			if (cutoff.equals(solution)) {
				cutoff_occured = true;
			} else if (!(solution.equals(null)) && solution.size() != 0) {
				return solution;
			}

		}

		if (cutoff_occured) {
			return cutoff;

		} else {
			// Echec
			ArrayList<Etat> failure = new ArrayList<>();
			failure.add(e_initial);
			return failure;
		}
	}

	public ArrayList<Etat> IterativeDeepeningSearch(Etat e_initial, Etat e_desire) throws EchecException {
		ArrayList<Etat> res = new ArrayList<>();

		while (e_initial.getProfondeur() >= 0) {
			stop = 0;
			res = Depth_Limited_Search(e_initial, e_desire, this.limit++);

			if (res.equals(null) || res.size() == 0) {

				throw new EchecException();
			} else {
				e_initial = res.get(res.size() - 1);
			}
			if (!res.equals(cutoff)) {
				Etat solution = res.get(0);
				Etat solution_parent = new Etat();
				while (solution.getProfondeur() > 1) {
					solution_parent = solution.getParent();
					solution = solution_parent;
					res.add(solution);

				}
				ArrayList<Etat> resu = new ArrayList<>();
				for (int i = res.size() - 1; i >= 0; i--) {
					resu.add(res.get(i));
				}
				return resu;
			}
		}

		System.out.println("Exploration done!");
		return null;

	}

}
