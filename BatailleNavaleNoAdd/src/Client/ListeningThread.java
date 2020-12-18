package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ListeningThread extends Thread{
	BufferedReader in;
	Scanner scan;
	
	public ListeningThread(Socket s) throws IOException {
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		scan = new Scanner(in);
	}
	/*
	 * Thread de base pour l'affichage des messages recu du serveur
	 * 
	 * On utilise un Scanner pour attendre les nouveaux messages plutot qu'envoyer "null" en boucle lorsque le serveur ne communique pas.
	 */
	public void run(){
		while (true) {
			try {
			System.out.println(scan.nextLine());
			}catch(NoSuchElementException e) {}
		}
	}
}