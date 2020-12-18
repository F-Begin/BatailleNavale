package Server.utils.database.sqlDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Server.utils.User;

public class ConnectionObjectSQL {
	private Connection connect;
	private String tableName;
	
	public ConnectionObjectSQL(Connection co, String table) {
		this.connect = co;
		this.tableName = table;
	}
	
	public int getIdByUsername(String usern) {
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT id AS uid FROM "+this.tableName+" WHERE username='"+usern+"'");
			while(resultSet.next()) {
				return resultSet.getInt("uid");
			}
		} catch(SQLException e) {e.printStackTrace();}
		return -1; //Erreur -1
	}
	
	public boolean checkUser(String username, String pass) {
		Statement statement = null;
		ResultSet resultSet = null;
		String hashPass = "";
		
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT password AS psw FROM "+this.tableName+" WHERE username='"+username+"'");
			resultSet.next();
			hashPass = resultSet.getString("psw");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if(pass.equals(hashPass))
			return true;
		return false;
	}
	
	public boolean addUser(String userN, String psw) {
		String hashedPsw = "";
		if(getIdByUsername(userN) == -1) {
			PreparedStatement prepStatement = null;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(psw.getBytes());
				byte[] bytes = md.digest();
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<bytes.length ;i++) {
					sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				}
				hashedPsw = sb.toString();
			}catch(NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			try {
				prepStatement = this.connect.prepareStatement("INSERT INTO "+this.tableName+"(`id`, `username`, `password`) VALUES (?,?,?)");
				prepStatement.setInt(1, this.getCount()+1);
				prepStatement.setString(2, userN);
				prepStatement.setString(3, hashedPsw);
				
				prepStatement.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return true;
		}
		else
			return false;
	}
	
	public int getCount() {
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = this.connect.createStatement();
			resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM "+this.tableName);
			resultSet.next();
			return resultSet.getInt("total");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1; //Erreur
	}
	
	public User getUser(Socket sock, int id) {
		Statement statement = null;
		ResultSet result = null;
		
		try {
			statement = this.connect.createStatement();
			result = statement.executeQuery("SELECT * FROM "+this.tableName+" WHERE id="+id);
			result.next();
			return new User(sock, id, result.getString("username"), result.getString("password"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}