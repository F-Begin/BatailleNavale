package Server.game.core;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

import Server.game.bateaux.*;
import Server.game.player.Player;
import Server.utils.FacingDirection;
import Server.utils.WrongPlacementException;

public class Game {
	private Player j1;
	private Player j2;
	private BufferedReader j1in;
	private BufferedReader j2in;
	private PrintWriter j1out;
	private PrintWriter j2out;
	private Scanner scan;
	
	public Game(Player j1, Player j2) {
		
		this.j1 = j1;
		this.j2 = j2;
		this.j1in = j1.getJIn();
		this.j2in = j2.getJIn();
		this.j1out = j1.getJOut();
		this.j2out = j2.getJOut();
		this.initialize();
	}

	public void initialize() {
		this.j1out.println("Vous devez placer vos bateaux...");
		this.j2out.println("Le joueur 1 place ses bateaux...");
		this.playerInitialize(j1);
		this.j1out.println("Vous avez placez vos bateaux ! Le joueur 2 va placer les siens...");
		this.j2out.println("A vous de placer vos bateaux !");
		this.playerInitialize(j2);
		this.sayToBoth("Début de la partie !");
		this.gamePlaying(this.j1, this.j2);
	}
	
	public void gamePlaying(Player j1, Player j2) {
		int turn = 1;
		boolean finish = false;
		boolean legitPos = false;
		String firePos = "";
		Player winner = null;
		while(!finish) {
			if(!(turn%2==0)) {
				scan = new Scanner(j1.getJIn());
				this.sayToBoth("Tour "+turn);
				this.j1out.println(this.j1.getVgrid().toString());
				this.j1out.println("Où voulez vous attaquer ?\n"
						+ "Répondez par \"xy\"\n"
						+ "x : Coordonnée de l'attaque en largeur.\n"
						+ "y : Coordonnée de l'attaque en hauteur.");
				this.j2out.println("Joueur 1 prépare une attaque...");
				while(!legitPos) {
					try {
						firePos = scan.nextLine();
						legitPos = true;
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				if(this.fire(Integer.parseInt(firePos.split("")[0]), Integer.parseInt(firePos.split("")[1]), j1, j2)) {
					finish=true;
					winner = j1;
				}
				legitPos = false;
				turn++;
			}
			else {
				scan = new Scanner(j2.getJIn());
				this.sayToBoth("Tour : "+turn);
				this.j2out.println(this.j2.getVgrid().toString());
				this.j2out.println("Où voulez vous attaquer ?\n"
						+ "Répondez par \"xy\"\n"
						+ "x : Coordonnée de l'attaque en hauteur.\n"
						+ "y : Coordonnée de l'attaque en largeur.");
				this.j1out.println("Joueur 2 prépare une attaque...");
				while(!legitPos) {
					try {
						firePos = scan.nextLine();
						legitPos = true;
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				if(this.fire(Integer.parseInt(firePos.split("")[0]), Integer.parseInt(firePos.split("")[1]), j2, j1)) {
					finish=true;
					winner = j2;
				}
				legitPos = false;
				turn++;
			}
		}
		if(winner.equals(j1))
			this.sayToBoth("Le grand gagnant est le joueur "+j1.getUser().getId()+"!");
		if(winner.equals(j2))
			this.sayToBoth("Le grand gagnant est le joueur "+j2.getUser().getId()+"!");
		this.sayToBoth("Merci d'avoir joué !");
	}
	
	public boolean fire(int x, int y, Player attaque, Player p) {
		boolean win = false;
		if(p.getGrid().isSomethingThere(x, y)) {
			attaque.getJOut().println("Touché !");
			attaque.getVgrid().changeCharOnEnnemyVisualGrid(x, y, 'X');
			
			p.getJOut().println("Un de vos navires est touché !");
			p.getGrid().changeValueThere(x, y);
			p.getVgrid().changeCharOnOwnVisualGrid(x, y, 'X');
			
			if(this.check(p)==0)
				win = true;
		}
		else{
			attaque.getJOut().println("Vous n'avez pas touché !");
			attaque.getVgrid().changeCharOnEnnemyVisualGrid(x, y, 'O');
			
			p.getJOut().println("Vous n'avez pas été touché !");
			p.getVgrid().changeCharOnOwnVisualGrid(x, y, 'O');
		}
		return win;
	}
	
	public int check(Player p) {
		int count = 0;
		for(int x = 0; x<10; x++) {
			for(int y = 0; y<10; y++) {
				if(p.getGrid().isSomethingThere(x, y))
					count++;
			}
		}
		return count;
	}
	
	public void sayToBoth(String message) {
		this.j1out.println(message);
		this.j2out.println(message);
	}
	
	public void playerInitialize(Player j) {
		this.scan = new Scanner(j.getJIn());
		for (Bateau bateau : j.getSbateau().getListBateau()) {
			boolean legitPlacment = false;
			FacingDirection direction = null;
			while(!legitPlacment) {
				j.getJOut().println(j.getVgrid().OnlyOwnGrid());
				j.getJOut().println("Veuillez placer votre : "+bateau.getName()+"\n"
						+ "x : Coordonnée en hauteur\n"
						+ "y : Coordonnée en largeur\n"
						+ "n-s-o-e : Point cardinal que le bateau pointera.");
				String answer = this.scan.nextLine();
				String[] command = answer.split("");
				switch(command[2]) {
				case "n":
					direction = Server.utils.FacingDirection.NORTH;
					break;
				case "s":
					direction = Server.utils.FacingDirection.SOUTH;
					break;
				case "e":
					direction = Server.utils.FacingDirection.EAST;
					break;
				case "o":
					direction = Server.utils.FacingDirection.WEST;
					break;
				}
				try {
					j.getGrid().placeBoatThere(Integer.parseInt(command[0]), Integer.parseInt(command[1]), bateau, direction);
					j.getVgrid().placeVisualBoatOnGrid(Integer.parseInt(command[0]), Integer.parseInt(command[1]), bateau, direction);
					legitPlacment = true;
				} catch (NumberFormatException | WrongPlacementException e) {
					j.getJOut().println("Placement incorrecte !");
				}
			}
		}
	}
	
	public Player getJ1() {
		return j1;
	}
	
	public Player getJ2() {
		return j2;
	}
	
	public BufferedReader getJ1in() {
		return j1in;
	}
	
	public BufferedReader getJ2in() {
		return j2in;
	}
	
	public PrintWriter getJ1out() {
		return j1out;
	}
	
	public PrintWriter getJ2our() {
		return j2out;
	}
}
