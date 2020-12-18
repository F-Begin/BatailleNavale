package Client;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class MainClient {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		/*
		 * Il s'agit d'un code de base pour se connecter a un serveur via la technologie Socket
		 * 
		 * Ce programme va démarré un ListeningThread qui sera chargé d'afficher les messages recu du serveur.
		 * 
		 * Si l'utilisateur tape "quit" le programme s'arrêtera automatiquement.
		 */
		try {
			Socket s = new Socket("127.0.0.1", 1500);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			ListeningThread t = new ListeningThread(s);
			t.start();
			System.out.println("Connexion rï¿½ussie!");
			Scanner sc=new Scanner(System.in);
			String message="";
			while (!message.equals("quit")) {
			message=sc.nextLine();
			out.println(message);
			}
			t.scan.close();
			t.stop();
			sc.close();
			s.close();
			} catch(Exception e) {
			// Traitement d'erreur
			}
	}
}