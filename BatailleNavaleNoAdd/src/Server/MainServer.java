package Server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Server.lobby.ThreadLobby;
import Server.utils.User;
import Server.gestion.ThreadGestion;

/*
 * CETTE VERSION NE PRENDS PAS ENCORE EN CHARGE LES BASES DE DONNEES !
 * CETTE VERSION NE CONTIENT PAS NON PLUS LES OPTIONS SUPPLEMENTAIRES !
 * IL NE S'AGIT QUE DU TP DE BASE SANS AJOUTS SUPPLEMENTAIRE
 * POUR OBTENIR LA VERSION AVEC OPTION -> Branche "Version Full" sur le repo GitHub
 * https://github.com/F-Begin/BatailleNavale/tree/Version-Full
 */

public class MainServer {
	static ArrayList<User> indexJoueur = new ArrayList<User>();
	static User newUser;
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ServerSocket ecoute = new ServerSocket(1500);
			System.out.println("Serveur lancé!");
			int id=0;	
			new ThreadGestion().run();;
			System.out.println("Thread de gestion d�mar� !");
			while(true) {
				Socket client = ecoute.accept();
				System.out.println("Nouveau joueur connect� : "+client.getInetAddress());
				newUser = new User(client, id);
				new ThreadLobby(newUser).start();
				indexJoueur.add(newUser);
				id++;
				newUser = null;
			}
		} catch(Exception e) {
		// Traitement d�erreur
		}
}

public static ArrayList<User> getIndexJoueur() {
	return indexJoueur;
}
}