package Server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Server.lobby.ThreadLobby;
import Server.utils.User;
import Server.utils.database.Database;
import Server.utils.database.DbMode;
import Server.utils.database.auth.AuthentificationThread;
import Server.gestion.ThreadGestion;

/*
 * Copyright (c) 2020 F-Begin
 * 
 * MIT License
 * 
 * https://github.com/F-Begin/BatailleNavale/blob/main/LICENSE
 * 
 * CETTE VERSION EST ***EXTREMEMENT*** EXPERIMENTALE
 * SI VOUS TROUVEZ DES BUGS, SOUCIS DE SECURITE, ETC...
 * CONTACTEZ begin19u@etu.univ-lorraine.fr
 */

public class MainServer {
	private static ArrayList<User> connectedUser = new ArrayList<User>();
	private static Database db;
	
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ServerSocket ecoute = new ServerSocket(1500);
			System.out.println("Serveur lancÃ©!");
			db = new Database();
			new ThreadGestion().start();
			System.out.println("Thread de gestion démaré !");
			System.out.println(getMode());
			while(true) {
				Socket client = ecoute.accept();
				System.out.println("Nouveau joueur connecté : "+client.getInetAddress());
				new AuthentificationThread(client).start();
			}
		} catch(Exception e) {
		// Traitement dï¿½erreur
		}
}

public static ArrayList<User> getIndexJoueur() {
	return connectedUser;
}

public static void addIndexJoueur(User user) {
	connectedUser.add(user);
}

public static Object getConnectionObject() {
	return db.getConnectionObj();
}

public static DbMode getMode() {
	return db.getMode();
}
}