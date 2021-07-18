package Solver;

import java.util.ArrayList;

public class Effecteur {
	Etat Belief;
	public ArrayList<Action> Actions = new ArrayList<>();

	public Effecteur(Etat Belief, ArrayList<Action> Actions) {
		this.Belief = (Etat) Copy.DeepCopy.copy(Belief);
		this.Actions = (ArrayList<Action>) Copy.DeepCopy.copy(Actions);
	}

	public void JustDoIt() {

		Etat e = this.Belief;
		for (Action a : Actions) {
			this.Belief = e.transition2(e, a);
			e = this.Belief;
		}

		System.out.println("JustDoIt done!");

		System.out.println(
				"***************************** Etat Manoir après nettoyage, selon les croyances de l'Agent ***************************");
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
		this.Belief.show_Etat();
	}

	public void JustDoIt_Apprentissage(int MA) {

		Etat e = this.Belief;
		for (int i = 0; i <= MA; i++) {
			this.Belief = e.transition2(e, Actions.get(i));
			e = this.Belief;
		}
		System.out.println("JustDoIt_Apprentissage done!");
		System.out.println(
				"***************************** Etat Manoir après nettoyage, selon les croyances de l'Agent ***************************");
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
		this.Belief.show_Etat();
	}

}
