package Server.gestion;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TimerTask;

import Server.MainServer;
import Server.game.ThreadGame;
import Server.utils.ClientState;
import Server.utils.User;

public class GameStartTimer extends TimerTask{
	/*
	 * Le GameStartTimer est une Task qui va etre démarré toutes les dix secondes par le Thread Gestion
	 * 
	 * Cette task va juste recupérer la liste des joueurs connecté et mettre dans des parties chaque binome de joueur en recherche.
	 * 
	 * Les binome de joueur qui leur ThreadLobby qui est detruit et rejoigne un ThreadGame (A la difference des ThreadLobby, un ThreadGame = 2 joueurs)
	 */
	ArrayList<User> indexJoueur = new ArrayList<User>();
	User temp;
	@SuppressWarnings("deprecation")
	public void run() {
		System.out.println("Lancement des parties !");
		indexJoueur = MainServer.getIndexJoueur();
		indexJoueur.forEach((user) ->{
			if(user.getState().equals(Server.utils.ClientState.ATTENTE)) {
				if(temp==null) {
					temp = user;
					try {
						new PrintWriter(user.getSocket().getOutputStream(), true).println("Recherche d'un opposant...");
						System.out.println("Message au joueur 1");
					} catch (IOException e) {
						e.printStackTrace(System.out);
					}
				}
				else {
					System.out.println("Joueur 2 trouvé !");
					temp.setState(ClientState.PARTIE);
					user.setState(ClientState.PARTIE);
					System.out.println("Passage des joueurs en partie !");
					try {
						System.out.println("Tentative de messaging au joueur 1");
						new PrintWriter(temp.getSocket().getOutputStream(), true).println("Une partie a été trouvé !");
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						System.out.println("Tentative de messaging au joueur 1");
						new PrintWriter(user.getSocket().getOutputStream(), true).println("Une partie a été trouvé !");
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Démarrage du ThreadGame pour les deux joueurs");
					temp.getLobby().stop();
					user.getLobby().stop();
					temp.removeLobby();
					user.removeLobby();
					new ThreadGame(temp, user).start();
					temp = null;
				}
			}
		});
		temp=null;
	}
}
