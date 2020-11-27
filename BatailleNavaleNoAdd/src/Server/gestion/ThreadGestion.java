package Server.gestion;

import java.util.Timer;

public class ThreadGestion {
	public void run() {
		Timer timer = new Timer();
		timer.schedule(new GameStartTimer(), 0, 10000);
	}
}
