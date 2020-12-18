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
	static ArrayList<User> indexJoueur = new ArrayList<User>(); //Liste des joueurs connectés
	static User newUser; //Variable temporaire pour supporter une nouvelle connexion
	
	/*
	 * Le main ici va juste accepter les connexions et les envoyers dans des Thread Lobby
	 * D'abord ont configure le port sur lequel on va ecouter
	 * Ensuite la variable id permet de compter les utilisateurs ne jamais avoir deux utilisateurs avec le meme ID
	 * Ensuite on démarre le ThreadGestion
	 * Ensuite on demarre la boucle qui va s'occuper d'accepter les joueurs et les envoyers dans les Thread Lobby en les ajoutant dans la liste de joueur définit plus haut
	 */
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ServerSocket ecoute = new ServerSocket(1500);
			System.out.println("Serveur lancÃ©!");
			int id=0;	
			new ThreadGestion().start();;
			System.out.println("Thread de gestion démaré !");
			while(true) {
				Socket client = ecoute.accept();
				System.out.println("Nouveau joueur connecté : "+client.getInetAddress());
				newUser = new User(client, id);
				new ThreadLobby(newUser).start();
				indexJoueur.add(newUser);
				id++;
				newUser = null;
			}
		} catch(Exception e) {
		// Traitement dï¿½erreur
		}
}
 
	/*
	 * Methode qui permet de recuperer la liste des joueurs connecté depuis les autres Thread nottament
	 */
public static ArrayList<User> getIndexJoueur() {
	return indexJoueur;
}
}