package Application;

import Solution.Environnement;

public class EnvThread extends Thread {

	Environnement manoir = new Environnement();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (true) {
			// Environnement en marche

			manoir.runEnv();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
