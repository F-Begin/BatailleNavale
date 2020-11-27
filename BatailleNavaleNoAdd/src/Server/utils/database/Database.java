package Server.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

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
	
	public Database() {
		sc = new Scanner(System.in);
		System.out.println("Voules vous utiliser une base de donn�es ?");
		System.err.println("Si vous refusez les donn�es des joueurs seront stock�s dans un fichier.");
		System.out.println("[y/n]>>> ");
		boolean answerLegit = false;
		do {
			String ans = sc.nextLine();
			switch(ans.toLowerCase()) {
			case "y":
				this.initDb();
				answerLegit = true;
				break;
			case "n":
				answerLegit = true;
				//TODO
				break;
			default:
				System.err.println("R�ponse non comprise.");
			}
		}while(!answerLegit);
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
		try {
			Class.forName("com.mysql.cj.jdcb.Driver");
		} catch(ClassNotFoundException e) {
			System.err.println("Erreur avec le driver.");
			e.printStackTrace();
		}
		System.out.println("Voulez vous utiliser une URL sp�cifique ou rester en local ?");
		System.err.println("(Laissez vide pour utilisation en local)");
		String ans = sc.nextLine();
		if(!ans.equals(""))
			url = ans;
		System.out.print("Quel nom voulez vous utiliser pour la base de donn�e SQL ?");
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
		
		System.err.println("Test de connectivit�...");
		try {
			this.connection = DriverManager.getConnection(this.url, this.user, this.password);
		} catch (SQLException e) {
			System.err.println("Erreur de connection � la base de donn�e !");
			e.printStackTrace();
			System.out.println("Voulez vous r�essayer ?[y/n]");
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
					//TODO
					break;
				default:
					System.err.println("R�ponse non comprise.");
					break;
				}
			}while(!legitAnswer);
		}
		System.out.println("Connexion r�ussis, v�rification de la table...");
	}
	
	public void dbTable() {
		System.out.println("Cr�ation de la table si elle n'existe pas...");
		String sqlCreate = "CREATE TABLE IF NOT EXISTS " + this.nomTable
        + "  (id           INTEGER,"
        + "   username            TEXT,"
        + "   password          TEXT,"
        + "   state           TEXT,";
		try {
			Statement statement = this.connection.createStatement();
			statement.execute(sqlCreate);
			this.initialize = true;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la requ�te !");
			e.printStackTrace();
		}
		if(this.initialize == true) {
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
					//TODO
					break;
				default:
					System.err.println("R�ponse non comprise.");
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
	
	/*
	 * FIN GESTION JSON
	 */
	
	/*
	 * METHODE GENERAL
	 */
	
	public DbMode getMode() {
		return this.mode;
	}
}
