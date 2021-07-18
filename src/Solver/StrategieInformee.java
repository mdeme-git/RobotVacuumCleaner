package Solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StrategieInformee {
	ArrayList<String> sommetsVisites = new ArrayList<>();

	public int heuristique(Etat e) {
		Piece pieceAgent = e.getEtat_carte().get(e.getPosition_Actuelle_Agent());
		int i = 0;
		for (HashMap.Entry<Integer, Piece> p : e.getEtat_carte().entrySet()) {
			if (p.getValue().bijou && p.getValue().ordure)
				i = i + 10;
			if ((p.getValue().bijou && !(p.getValue().ordure)) || (!(p.getValue().bijou) && (p.getValue().ordure)))
				i = i + 5;
		}

		if (i != 0) {

			if (!(pieceAgent.bijou) && !(pieceAgent.ordure))
				i = i + 4;
			if ((!(pieceAgent.bijou) && (pieceAgent.ordure)) || ((pieceAgent.bijou) && !(pieceAgent.ordure)))
				i = i + 3;
			if (e.getAction() != null) {
				if (e.getAction().getName().equals("Ne rien faire")) {
					i = i + 30;
				}
			}
		}
		return i;
	}

	public ArrayList<Etat> Expand(Etat e_initial) {

		ArrayList<Etat> res = new ArrayList<Etat>();
		// Toutes les actions possibles d'une piece
		for (Action a : e_initial.actionsPossible()) {
			Etat e1 = new Etat();
			final Etat e = e1.transition2(e_initial, a);
			if (e != null) {
				res.add(e);
			}
		}

		return res;

	}

	public boolean goal_test(Etat e, Etat Desire) {
		for (int i = 0; i < e.getEtat_carte().keySet().size(); i++) {
			if (!(e.getEtat_carte().values().toString().equals(Desire.getEtat_carte().values().toString()))) {
				return false;
			}
		}
		return true;
	}

	public HashMap<Integer, Integer> triMap(HashMap<Integer, Integer> map) {
		LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();

		map.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

		return sortedMap;

	}

	public ArrayList<Etat> GreedySearch(Etat e_initial, Etat e_desire) {

		ArrayList<Etat> solution = new ArrayList<Etat>();

		while (!(goal_test(e_initial, e_desire))) {

			ArrayList<Etat> test = new ArrayList<Etat>();

			test = this.Expand(e_initial);

			HashMap<Integer, Integer> listeNbrPieceSales = new HashMap<>();

			for (int i = 0; i < test.size(); i++) {
				listeNbrPieceSales.put(i, heuristique(test.get(i)));
			}

			HashMap<Integer, Integer> listeNbrPieceSalesTrie = new HashMap<Integer, Integer>();

			listeNbrPieceSalesTrie = triMap(listeNbrPieceSales);

			Etat e;

			ArrayList<Integer> indexs = new ArrayList<Integer>();

			for (Integer i : listeNbrPieceSalesTrie.keySet())
				indexs.add(i);

			int pas = 0;

			while (pas < indexs.size()) {
				e = test.get(indexs.get(pas));
				// si l'etat a deja etait visite
				if (sommetsVisites.contains(e.getAction() + " " + e.getPosition_Actuelle_Agent())) {
					pas++;
				} else {
					sommetsVisites.add(e.getAction() + " " + e.getPosition_Actuelle_Agent());
					solution.add(e);
					e_initial = e;
					pas = indexs.size();
				}

			}
		}
		return solution;
	}

}
