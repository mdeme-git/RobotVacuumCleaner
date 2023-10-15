package Application;

import java.util.Scanner;

import Solver.Agent;
import Solver.Capteur;
import Solver.Etat;

public class Main {
	public static String CHOIX_STRATEGIE = null;

	public static void main(String args[]) {

		// Choix de la strategie que l'Agent va utiliser

		Scanner myObj = new Scanner(System.in); // Create a Scanner object
		System.out.println("Choix de la stratégie d'exploration que l'Agent utilisera");
		System.out.println("1 pour Stratégie non informée");
		System.out.println("2 pour Stratégie informée");
		System.out.println("3 pour mettre fin à l'execution");

		CHOIX_STRATEGIE = myObj.nextLine(); // Read user input
		while (!(CHOIX_STRATEGIE.equals("3"))) {

			if (CHOIX_STRATEGIE.equals("1") || CHOIX_STRATEGIE.equals("2")) {
				System.out.println("Votre choix est le " + CHOIX_STRATEGIE); // Output user input

				// Initialisation du thread de l'environnement
				EnvThread env = new EnvThread();
				env.start();
				Etat but = new Etat();

				// Initialisation de l'Agent
				Capteur c = new Capteur();
				Agent a = new Agent(c);

				a.RunAgent(env.manoir);
				// Affichage de l'environnement final
				env.manoir.show_environnement();
				break;

			} else if (CHOIX_STRATEGIE.equals("3")) {
				break;
			} else {
				System.out.println("Choississez un numéro entre 1, 2 et 3");
				CHOIX_STRATEGIE = myObj.nextLine(); // Read user input
			}
		}

	}

}
