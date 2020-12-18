package Server.game;

import Server.game.bateaux.SetBateau;
import Server.game.core.Game;
import Server.game.grid.Grid;
import Server.game.grid.VisualGrid;
import Server.game.player.Player;
import Server.lobby.ThreadLobby;
import Server.utils.ClientState;
import Server.utils.User;

public class ThreadGame extends Thread {
	/*
	 * Le ThreadGame possede deux joueurs, u1 et u2
	 */
	private User u1;
	private User u2;
	
	public ThreadGame(User user1, User user2) {
		//Initialisation des arguments du Thread
		this.u1 = user1;
		this.u2 = user2;
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		/*
		 * Une fois le Thread démarré on va crée un nouvel objet Game avec pour constructeur deux objects Players qui sont crée a partir de u1 et u2
		 * Une fois la partie terminé les etats des deux joueurs sont remis sur AFK (cf ClientState) et sont renvoyé dans des ThreadLobby individuel avant que le ThreadGame soit finalement détruit
		 */
		new Game(new Player(new Grid(), new VisualGrid(), u1, new SetBateau()), new Player(new Grid(), new VisualGrid(), u2, new SetBateau()));
		this.u1.setState(ClientState.AFK);
		this.u2.setState(ClientState.AFK);
		new ThreadLobby(u1).start();
		new ThreadLobby(u2).start();
		this.stop();
	}
}
