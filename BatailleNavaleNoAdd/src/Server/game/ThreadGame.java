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
	private User u1;
	private User u2;
	
	public ThreadGame(User user1, User user2) {
		this.u1 = user1;
		this.u2 = user2;
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		new Game(new Player(new Grid(), new VisualGrid(), u1, new SetBateau()), new Player(new Grid(), new VisualGrid(), u2, new SetBateau()));
		this.u1.setState(ClientState.AFK);
		this.u2.setState(ClientState.AFK);
		new ThreadLobby(u1).start();
		new ThreadLobby(u2).start();
		this.stop();
	}
}
