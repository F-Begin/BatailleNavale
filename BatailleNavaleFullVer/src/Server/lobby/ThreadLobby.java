package Server.lobby;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Server.MainServer;
import Server.utils.ClientState;
import Server.utils.User;

import java.util.Scanner;

public class ThreadLobby extends Thread{
	private User user;
	private BufferedReader in;
	private PrintWriter out;
	
	public ThreadLobby(User user) {
		try {
			this.user = user;
			this.in = new BufferedReader(new InputStreamReader(user.getSocket().getInputStream()));
			this.out = new PrintWriter(user.getSocket().getOutputStream(), true);
			out.println("Bienvenue dans le lobby joueur "+user.getUsername()+"!\n");
			out.println("Vous êtes actuellement en "+user.getState());
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.user.setLobby(this);
	}
	
	@SuppressWarnings("deprecation")
	public synchronized void run() {
		System.out.println("go");
		Scanner scanner = new Scanner(in);
		try {
			while(true) {
				out.println("1 : Chercher une partie / Cesser de chercher.");
				out.println("2 : Quitter.");
				if(!this.user.getState().equals(Server.utils.ClientState.PARTIE)) {
					int answer = scanner.nextInt();
					switch(answer) {
					case 1:
						if(this.user.getState() == Server.utils.ClientState.AFK) {
							this.user.setState(Server.utils.ClientState.ATTENTE);
							out.println("Nouveau status définis sur : "+user.getState());
						}
						else if(this.user.getState() == Server.utils.ClientState.ATTENTE) {
							this.user.setState(ClientState.AFK);
							out.println("Nouveau status définis sur : "+user.getState());
						}
						else {
							out.println("Pas de changement pour une raison obscure ...?");
						}
						break;
					case 2:
						scanner.close();
						MainServer.getIndexJoueur().remove(MainServer.getIndexJoueur().indexOf(this.user));
						this.stop();
						break;
					default:
						out.println("Réponse non comprise...");
					}
				}
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
