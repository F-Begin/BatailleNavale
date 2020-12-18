package Server.utils;

import java.net.Socket;

import Server.game.ThreadGame;
import Server.lobby.ThreadLobby;

public class User {
	private Socket socket;
	private ClientState state;
	private int id;
	private String usernameMd5;
	private String passwordMd5;
	private ThreadLobby lobby;
	
	/*
	 * Creation ici de l'Objet User, c'est l'objet qui est crée pour chaque joueurs se connectant au serveur
	 * (Le constructeur avec username et password n'est pas utilisé ici, il le sera dans la version avec base de donnée)
	 * 
	 * Ensuite il y a les Setters et Getters normaux.
	 * 
	 * Enfin, les deux methodes @Override hashcode() et equals() permettent d'effectuer des User.equals(AnotherUser)
	 * car equals ne peux pas, de base, verifier l'egalité entre deux objets ".
	 */
	
	//Pas encore utilisé
	@SuppressWarnings("unused")
	private ThreadGame game;
	
	public User() {}
	
	public User(Socket sock, int id) {
		this.socket = sock;
		this.id = id;
		this.state = ClientState.AFK;
		this.lobby = null;
	}
	
	public User(Socket sock, int id, String username, String password) {
		this.socket = sock;
		this.id = id;
		this.usernameMd5 = username;
		this.passwordMd5 = password;
		this.lobby = null;
		this.state = ClientState.AFK;
	}
	
	public String getPassword() {
		return this.passwordMd5;
	}
	
	public String getUsername() {
		return this.usernameMd5;
	}
	
	public int getId() {
		return this.id;
	}
	
	public ClientState getState() {
		return this.state;
	}
	
	public void setLobby(ThreadLobby th) {
		this.lobby = th;
	}
	
	public ThreadLobby getLobby() {
		return this.lobby;
	}
	
	public void removeLobby() {
		this.lobby = null;
	}
	
	public Socket getSocket() {
		return this.socket;
	}
	
	public void setState(ClientState newstate) {
		this.state = newstate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.socket.getPort();
		result = prime * result + this.id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if(this.socket != other.getSocket())
			return false;
		if(this.id != other.getId())
			return false;
		return true;
	}
}
