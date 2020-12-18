package Server.lobby;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Server.MainServer;
import Server.utils.ClientState;
import Server.utils.User;

import java.util.Scanner;

public class ThreadLobby extends Thread{
	User user;
	BufferedReader in;
	PrintWriter out;
	
	/*
	 * Ceci est le Thread dans lequel l'utilisateur est envoyé après s'être connectée
	 */
	
	public ThreadLobby(User user) {
		try {
			/*
			 * Définition des attributs du ThreadLobby
			 * user = L'utilisateur dans le Lobby
			 * in = Son stream d'entrée
			 * out = Son stream de sortie
			 * 
			 * Ensuit on affiches les messages de bienvenue
			 */
			this.user = user;
			in = new BufferedReader(new InputStreamReader(user.getSocket().getInputStream()));
			out = new PrintWriter(user.getSocket().getOutputStream(), true);
			out.println("Bienvenue dans le lobby joueur "+user.getId()+"!\n");
			out.println("Vous êtes actuellement en "+user.getState());
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.user.setLobby(this);
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		//Scanner de l'utilisateur
		Scanner scanner = new Scanner(this.in);
		try {
			while(true) {
				/*
				 * Boucle dans laquelle l'utilisateur peut lancer et annuler une recherche de partie
				 * 
				 * Le switch permet de recup la reponse de l'utilisateur
				 * 
				 * De plus, les exceptions sont gérées si l'utilisateur écrit n'importe quoi
				 */
				out.println("1 : Chercher une partie / Cesser de chercher.");
				out.println("2 : Quitter.");
				if(!this.user.getState().equals(Server.utils.ClientState.PARTIE)) {
					String answer = scanner.nextLine();
					switch(answer) {
					case "1":
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
					case "2":
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
