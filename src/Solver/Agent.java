package Solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import Application.Main;

public class Agent {

	public int MA = 10;

	public Capteur capteur;

	public Etat Desire = new Etat(); // Toutes les pieces propres

	public ArrayList<Action> Intentions = new ArrayList<>();

	public Etat Belief;

	public Effecteur effecteur;

	public ArrayList<Action> getIntentions() {
		return Intentions;
	}

	public void setIntentions(ArrayList<Action> intentions) {
		Intentions = intentions;
	}

	public Etat getBelief() {
		return Belief;
	}

	public void setBelief(Etat belief) {
		Belief = belief;
	}

	public Agent(Capteur c) {

		this.capteur = c;

	}

	public void ObserveEnvironment(Environnement env) {
		this.capteur.observer(env);
		System.out.println("ObserveEnvironment done!");

	}

	public void UpdateMyState() {
		this.Belief = (Etat) Copy.DeepCopy.copy(this.capteur.getObservation());
		System.out.println("UpdateMyState done!");
		System.out.println("");

		System.out.println("***************************** Etat du Manoir apr�s observation ***************************");
		try {
			Thread.sleep(900);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.Belief.show_Etat();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void chooseActions() {

		if (Main.CHOIX_STRATEGIE.equals("2")) {

			StrategieInformee strategy = new StrategieInformee();
			Collection<Etat> resultat = strategy.GreedySearch(Belief, Desire);
			for (Etat e : resultat)
				Intentions.add(e.getAction());
			System.out.println("ChooseActions done!");
		} else {
			StrategieExplorationIDS strategy = new StrategieExplorationIDS();
			ArrayList<Etat> resultat;
			try {
				resultat = (ArrayList<Etat>) strategy.IterativeDeepeningSearch(Belief, Desire);

				for (Etat e : resultat)
					Intentions.add(e.getAction());
				System.out.println("ChooseActions done!");
				System.out.println(" ");
			} catch (EchecException e1) { //
				e1.printStackTrace();
			}
		}
	}

	public int Apprentissage(Environnement env) {
		HashMap<Integer, ArrayList<Integer>> MA_Performance = new HashMap<>();

		for (int i = 1; i < this.Intentions.size(); i += 10) {
			MA_Performance.put(i, new ArrayList<Integer>());
		}

		Double moyenne = 0.0;
		Double somme = 0.0;
		HashMap<Integer, Double> listeMoyenne = new HashMap<Integer, Double>();

		Double moyenne_optim = 0.0;

		int MA_optim = (int) MA_Performance.keySet().toArray()[0];

		for (int i = 0; i < 5; i++) {

			this.ObserveEnvironment(env);
			this.UpdateMyState();
			this.chooseActions();
			ArrayList<Integer> Performance;
			for (Integer MA : MA_Performance.keySet()) {
				Performance = MA_Performance.get(MA);
				if (MA < this.Intentions.size()) {
					Performance.add(env.mesurePerformance(this, MA));
				}
				MA_Performance.put(MA, Performance);
			}

		}

		for (HashMap.Entry<Integer, ArrayList<Integer>> i : MA_Performance.entrySet()) {
			somme = 0.0;
			for (int j : i.getValue()) {
				somme += j;
			}

			moyenne = (somme) / (i.getValue().size());

			listeMoyenne.put(i.getKey(), moyenne);
		}

		moyenne_optim = listeMoyenne.get(MA_optim);

		for (HashMap.Entry<Integer, Double> i : listeMoyenne.entrySet()) {
			if (i.getValue() > moyenne_optim) {
				moyenne_optim = i.getValue();
				MA_optim = i.getKey();
			}
		}

		return MA_optim;
	}

	public void RunAgent(Environnement env) {

		// Avant l'apprentissage
		this.ObserveEnvironment(env);
		this.UpdateMyState();
		this.chooseActions();
		effecteur = new Effecteur(this.Belief, Intentions);
		effecteur.JustDoIt();
		this.Belief = effecteur.Belief;
		System.out.println("Avant l'apprentissage l'agent à une mesure de performance égal à "
				+ env.mesurePerformance(this, Intentions.size()));

		HashMap<Integer, Piece> carte1 = new HashMap<>();
		carte1 = (HashMap<Integer, Piece>) Copy.DeepCopy.copy(this.Belief.getEtat_carte());
		env.setCarte(carte1);
		env.setPositionAgent(this.Belief.getPosition_Actuelle_Agent());

		// Apprentissage
		int MA_optimal = Apprentissage(env);
		MA = MA_optimal;
		while (!(Belief.equals(Desire))) {
			this.ObserveEnvironment(env);
			this.UpdateMyState();
			this.chooseActions();
			effecteur = new Effecteur(Belief, Intentions);
			effecteur.JustDoIt_Apprentissage(MA);
			this.Belief = effecteur.Belief;
			System.out.println("Après l'apprentissage l'agent à une mesure de performance égal à "
					+ env.mesurePerformance(this, MA_optimal));

			HashMap<Integer, Piece> carte2 = new HashMap<>();
			carte2 = (HashMap<Integer, Piece>) Copy.DeepCopy.copy(this.Belief.getEtat_carte());
			env.setCarte(carte2);
			env.setPositionAgent(this.Belief.getPosition_Actuelle_Agent());
		}

	}

}
