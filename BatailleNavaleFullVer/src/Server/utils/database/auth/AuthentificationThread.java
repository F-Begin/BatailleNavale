package Server.utils.database.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import Server.MainServer;
import Server.lobby.ThreadLobby;
import Server.utils.database.DbMode;
import Server.utils.database.json.ConnectionObjectJSON;
import Server.utils.database.sqlDb.ConnectionObjectSQL;

public class AuthentificationThread extends Thread{
	private String username;
	private String pass;
	private Object connectObj;
	private PrintWriter jout;
	private BufferedReader jin;
	private Scanner sc;
	private boolean success = false;
	private int id;
	private Socket sock;
	private DbMode dbmode;
	
	public AuthentificationThread(Socket sock) {
		this.defineMode();
		this.sock = sock;
		connectObj = MainServer.getConnectionObject();
		try {
			if(this.dbmode==DbMode.SQL) {
				this.connectObj = (ConnectionObjectSQL) this.connectObj;
			}
			if(this.dbmode==DbMode.JSON) {
				this.connectObj = (ConnectionObjectJSON) this.connectObj;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			this.jin = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			this.jout = new PrintWriter(sock.getOutputStream(), true);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void defineMode() {
		if(MainServer.getMode() == DbMode.SQL) {
			this.dbmode = DbMode.SQL;
		}
		if(MainServer.getMode() == DbMode.JSON) {
			this.dbmode = DbMode.JSON;
		}
	}
	
	private void connexion() {
		switch (this.dbmode) {
		case SQL:
			this.sqlConnexion();
			break;
		case JSON:
			this.jsonConnexion();
			break;
		default:
			break;
		}
	}
	
	private void sqlConnexion() {
		this.jout.println("Username ?");
		this.username = this.sc.nextLine();
		this.jout.println("Password ?");
		this.pass = this.sc.nextLine();
		String hashPass = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(this.pass.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<bytes.length ;i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			hashPass = sb.toString();
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(((ConnectionObjectSQL) connectObj).checkUser(this.username, hashPass)) {
			this.jout.println("Connexion réussis.");
			this.success = true;
			this.id = ((ConnectionObjectSQL) connectObj).getIdByUsername(this.username);
		}
	}
	
	private void jsonConnexion() {
		this.jout.println("Username ?");
		this.username = this.sc.nextLine();
		this.jout.println("Password ?");
		this.pass = this.sc.nextLine();
		String hashPass = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(this.pass.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<bytes.length ;i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			hashPass = sb.toString();
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(((ConnectionObjectJSON) connectObj).checkUser(this.username, hashPass)) {
			this.jout.println("Connexion réussis.");
			this.success = true;
			this.id = ((ConnectionObjectJSON)connectObj).getIdByUsername(this.username);
		}
		else {
			this.jout.println("Erreur de connexion, vérifiez les identifiants.");
		}
	}
	
	private void createAccount() {
		switch(this.dbmode) {
		case SQL:
			this.createAccountSQL();
			break;
		case JSON:
			this.createAccountJson();
			break;
		default:
			break;
		}
	}
	
	private void createAccountSQL() {
		boolean ok;
		this.jout.println("Username du nouveau compte ?");
		this.username = this.sc.nextLine();
		this.jout.println("Password du nouveau compte ?");
		this.pass = this.sc.nextLine();
		ok = ((ConnectionObjectSQL) connectObj).addUser(this.username, this.pass);
		if(ok) {
			try {
				this.jout.println("Création du compte réussit.");
				this.success = true;
				this.id = ((ConnectionObjectSQL) connectObj).getIdByUsername(this.username);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			this.jout.println("Impossible de créer le compte.");
			this.createAccountSQL();
		}
	}
	
	private void createAccountJson() {
		boolean ok;
		this.jout.println("Username du nouveau compte ?");
		this.username = this.sc.nextLine();
		this.jout.println("Password du nouveau compte ?");
		this.pass = this.sc.nextLine();
		ok = ((ConnectionObjectJSON) connectObj).addUser(this.sock, this.username, this.pass);
		if(ok) {
			try {
				this.jout.println("Création du compte réussit.");
				this.success = true;
				this.id = ((ConnectionObjectJSON) connectObj).getIdByUsername(this.username);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			this.jout.println("Impossible de créer le compte");
			this.createAccountJson();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		System.out.println("Authentification thread started for "+this.sock.getInetAddress());
		sc = new Scanner(this.jin);
		int tried = 0;
		do {
			jout.println("Bonjour !\n1.Se connecter à un compte existant.\n2.Créer un nouveau compte.\n>>>");
			String ans = sc.nextLine();
			switch(ans) {
			case "1":
				if(success)
					break;
				tried++;
				this.connexion();
				break;
			case "2":
				if(success)
					break;
				tried++;
				this.createAccount();
				break;
			default:
				if(success)
					break;
				tried++;
				jout.println("Réponse non comprise.");
			}
		}while(tried<4 && (!success));
		if(success) {
			if(this.dbmode==DbMode.SQL) {
				MainServer.getIndexJoueur().add(((ConnectionObjectSQL) connectObj).getUser(sock, id));
				new ThreadLobby(((ConnectionObjectSQL) connectObj).getUser(this.sock, id)).start();
			}
			if(this.dbmode==DbMode.JSON) {
				MainServer.getIndexJoueur().add(((ConnectionObjectJSON) connectObj).getUser(sock, id));
				new ThreadLobby(((ConnectionObjectJSON) connectObj).getUser(this.sock, id)).start();
			}
		}
		else {
			jout.println("Vous avez trop essayé, déconnexion...");
		}
		this.stop();
	}
}