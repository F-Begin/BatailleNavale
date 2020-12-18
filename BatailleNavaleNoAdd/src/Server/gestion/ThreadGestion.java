package Server.gestion;

import java.util.Timer;

public class ThreadGestion extends Thread{
	/*
	 * Ce thread est demarré au démarrage du serveur, il démarre un "GameStartTimer()" toutes les 10 secondes
	 */
	public void run() {
		Timer timer = new Timer();
		timer.schedule(new GameStartTimer(), 0, 10000);
	}
}
