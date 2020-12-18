package Server.utils.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import Server.utils.User;
import Server.utils.database.json.ConnectionObjectJSON;
import Server.utils.database.sqlDb.ConnectionObjectSQL;

public class Database {
	private String nomBase = "batailleNavale";
	private String cmpl = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String nomTable = "batailleNavale";
	private String url = "jdbc:mysql://localhost/";
	private String user;
	private String password;
	private Scanner sc;
	private boolean initialize = false;
	private Connection connection;
	private DbMode mode;
	private File file;
	
	public Database() {
		sc = new Scanner(System.in);
		System.out.println("Voules vous utiliser une base de données ?");
		System.err.println("Si vous refusez les données des joueurs seront stockés dans un fichier.");
		System.out.println("[y/n]>>> ");
		boolean answerLegit = false;
		do {
			String ans = sc.nextLine();
			switch(ans.toLowerCase()) {
			case "y":
				answerLegit = true;
				this.initDb();
				break;
			case "n":
				answerLegit = true;
				this.initJson();
				break;
			default:
				System.err.println("Réponse non comprise.");
			}
		}while(!answerLegit);
		this.sc.close();
	}
	
	public void resetDefault() {
		this.nomBase = "batailleNavale";
		this.cmpl = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		this.nomTable = "batailleNavale";
		this.url = "jdbc:mysql://localhost/";
		this.user = null;
		this.password = null;
		this.initialize = false;
		this.connection = null;
	}
	/*
	 * GESTION SQL
	 */
	public void initDb() {
		this.resetDefault();
		System.out.println("Voulez vous utiliser une URL spécifique ou rester en local ?");
		System.err.println("(Laissez vide pour utilisation en local)");
		String ans = sc.nextLine();
		if(!ans.equals(""))
			url = ans;
		System.out.print("Quel nom voulez vous utiliser pour la base de donnée SQL ?");
		System.err.println("(batailleNavale)");
		ans = sc.nextLine();
		if(!ans.equals(""))
			this.nomBase = ans;
		url+=nomBase;
		url+=this.cmpl;
		System.out.print("Quel nom de table voulez vous utiliser ?");
		System.err.println("(batailleNavale)");
		ans = sc.nextLine();
		if(!ans.equals(""))
			this.nomTable = ans;
		System.out.println("Identifiant ?");
		this.user = sc.nextLine();
		System.out.println("Mot de passe ?");
		this.password = sc.nextLine();
		
		System.err.println("Test de connectivité...");
		try {
			this.connection = DriverManager.getConnection(this.url, this.user, this.password);
			this.dbTable();
		} catch (SQLException e) {
			System.err.println("Erreur de connection à la base de donnée !");
			e.printStackTrace();
			System.out.println("Voulez vous réessayer ?[y/n]");
			boolean legitAnswer = false;
			do {
				ans = sc.nextLine();
				switch (ans.toLowerCase()) {
				case "y":
					legitAnswer = true;
					this.initDb();
					break;
				case "n":
					legitAnswer = true;
					this.initJson();
					break;
				default:
					System.err.println("Réponse non comprise.");
					break;
				}
			}while(!legitAnswer);
		}
	}
	
	public void dbTable() {
		System.out.println("Création de la table si elle n'existe pas...");
		String sqlCreate = "CREATE TABLE IF NOT EXISTS " + this.nomTable
        + " (id INT,"
        + "username TEXT,"
        + "password TEXT)";
		try {
			Statement statement = this.connection.createStatement();
			statement.execute(sqlCreate);
			this.initialize = true;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la requête !");
			e.printStackTrace();
		}
		if(this.initialize) {
			this.mode = DbMode.SQL;
			System.out.println("OK !");
		}
		else {
			System.out.println("Voulez vous essayez de reconfigurer la connection ?[y/n]");
			String ans = sc.nextLine();
			boolean legitAns = false;
			do {
				switch (ans.toLowerCase()) {
				case "y":
					legitAns = true;
					this.initDb();
					break;
				case "n":
					legitAns = true;
					this.initJson();
					break;
				default:
					System.err.println("Réponse non comprise.");
					break;
				}
			}while(!legitAns);
		}
	}
	/*
	 * FIN GESTION SQL
	 */
	
	/*
	 * GESTION JSON
	 */
	
	public void initJson() {
		System.out.println("Nom du fichier base de donnée ?\n>>>");
		String ans = sc.nextLine();
		this.file = new File(ans+".json");
		this.mode = DbMode.JSON;
	}
	/*
	 * FIN GESTION JSON
	 */
	
	/*
	 * METHODE GENERAL
	 */
	
	public DbMode getMode() {
		return this.mode;
	}
	
	public Object getConnectionObj() {
		if(this.getMode().equals(DbMode.SQL)) {
			return new ConnectionObjectSQL(this.connection, this.nomTable);
		}
		if(this.getMode().equals(DbMode.JSON)) {
			return new ConnectionObjectJSON(this.file);
		}
		return null;
	}
}
