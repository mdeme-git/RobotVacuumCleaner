package Solver;

public class Capteur {
	
	public Etat observation;
	
	public void observer(Environnement env) {
        observation = (Etat) Copy.DeepCopy.copy(new Etat(env));
    }

	public Etat getObservation() {
		return observation;
	}

}
