package Server.utils;

import java.net.Socket;

import Server.game.ThreadGame;
import Server.lobby.ThreadLobby;

public class User {
	private Socket socket;
	private ClientState state;
	private int id;
	private ThreadLobby lobby;
	private String username;
	private String password;
	
	//Pas encore utilisé
	@SuppressWarnings("unused")
	private ThreadGame game;
	
	public User() {}
	
	public User(Socket sock, int id, String username, String password) {
		this.socket = sock;
		this.id = id;
		this.state = ClientState.AFK;
		this.lobby = null;
		this.username = username;
		this.password = password;
	}
	
	public User(int id, String username, String password) {
		this.id = id;
		this.state = null;
		this.lobby = null;
		this.username = username;
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getUsername() {
		return this.username;
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
