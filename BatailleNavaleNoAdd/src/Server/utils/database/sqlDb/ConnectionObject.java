package Server.utils.database.sqlDb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionObject {
	private Connection connect;
	private String tableName;
	
	public ConnectionObject(Connection co, String table) {
		this.connect = co;
		this.tableName = table;
	}
	
	public int getIdByUsername(int id) {
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT username AS name FROM "+this.tableName+" WHERE id="+id);
			resultSet.next();
			return resultSet.getInt("name");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return -1; //Erreur -1
	}
}
