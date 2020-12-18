package Server.utils.database.json;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Server.utils.User;

public class ConnectionObjectJSON {
	private File file;
	private ObjectMapper mapper;
	
	public ConnectionObjectJSON(File jfile) {
		this.mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		if(!jfile.isFile())
			try {
				jfile.createNewFile();
				ArrayList<User> us = new ArrayList<User>();
				mapper.writeValue(jfile, us);
			} catch (IOException e) {
				e.printStackTrace();
			}
		this.file = jfile;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<User> getJsonListe() {
		ArrayList<User> liste = new ArrayList<>();
		try {
			liste = mapper.readValue(this.file, ArrayList.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return liste;
	}
	
	public int getIdByUsername(String username) {
		for (User user : this.getJsonListe()) {
			if(user.getUsername().equals(username)) {
				return user.getId();
			}
		}
		return -1;
	}
	
	public boolean checkUser(String username, String pass) {
		for(User user : this.getJsonListe()) {
			if(user.getUsername().equals(username)) {
				if(user.getPassword().equals(pass))
					return true;
			}
		}
		return false;
	}
	
	public int getCount() {
		return this.getJsonListe().size();
	}
	
	public boolean addUser(Socket sock, String userN, String psw) {
		String hashPsw = "";
		if(this.getIdByUsername(userN)==-1) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(psw.getBytes());
				byte[] bytes = md.digest();
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<bytes.length ;i++) {
					sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				}
				hashPsw = sb.toString();
			}catch(NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			addToList(new User(this.getCount(), userN, hashPsw));
			return true;
		}
		return false;
	}
	
	public User getUser(Socket sock, int id) {
		for (User user : this.getJsonListe()) {
			if(user.getId() == id)
				return new User(sock, id, user.getUsername(), user.getPassword());
		}
		return null;
	}
	
	public void addToList(User user) {
		ArrayList<User> liste = this.getJsonListe();
		this.file.delete();
		try {
			this.file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		liste.add(user);
		try {
			mapper.writeValue(this.file, liste);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
