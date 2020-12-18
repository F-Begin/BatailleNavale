package Server.game.grid;

import java.util.Arrays;
import Server.game.bateaux.*;
import Server.utils.FacingDirection;
import Server.utils.WrongPlacementException;

/*
 * L'objet Grid est la grille que le serveur utilisera pour savoir si où sont les bateaux d'un joueur et si ces derniers sont touchés
 * Il s'agit d'un grille de boolean de 10x10
 * 
 * Il y a des methodes customs et les Getters et Setters habituels.
 */

public class Grid {
	private boolean[][] grid = new boolean[10][10];
	
	public Grid() {
		for(boolean[] g : grid) {
			Arrays.fill(g, false);
		}
	}
	/*
	 * Cette methode permet de retourner True si il y a un morceau de bateau aux coordonnées et false dans le cas contraire
	 */
	public boolean isSomethingThere(int x, int y) {
		return this.grid[x][y];
	}
	
	/*
	 * Cette methode permet d'inserver la valeur d'une case de grille
	 * True -> False
	 * False -> True
	 */
	public void changeValueThere(int x, int y) {
		if(this.grid[x][y] == true)
			this.grid[x][y] = false;
		else
			this.grid[x][y] = true;
	}
	/*
	 * Cette methode permet de verifier et, si la position est valide, de placer un bateau au coordonnées données.
	 * Cette methode utilise l'exception Custom, WrondPlacementException (cf Server.utils.WrongPlacementException) qui est envoyé en cas de placement a des coordonnées incorrectes.
	 * 
	 * On verifie si les coordonnées et la direction du bateau ne vont pas provoquer un bateau qui sortira de la grille ou qui croisera un autre bateau
	 */
	public void placeBoatThere(int ligne, int colonne, Bateau bateau, FacingDirection direction) throws WrongPlacementException {
		boolean legitPos = true;
		switch(direction) {
		case NORTH:
			if(ligne-(bateau.getLenght()-1)<0) {
				legitPos = false;
			}
			if(legitPos) {
				for(int i = 0; i<bateau.getLenght(); i++) {
					if(isSomethingThere(ligne-i, colonne))
						legitPos = false;
				}
			}
			break;
		case SOUTH:
			if(ligne+(bateau.getLenght()-1)>9) {
				legitPos = false;
			}
			if(legitPos) {
				for(int i = 0; i<bateau.getLenght(); i++) {
					if(isSomethingThere(ligne+i, colonne))
						legitPos = false;
				}
			}
			break;
		case EAST:
			if(colonne+(bateau.getLenght()-1)>9) {
				legitPos = false;
			}
			if(legitPos) {
				for(int i = 0; i<bateau.getLenght(); i++) {
					if(isSomethingThere(ligne, colonne))
						legitPos = false;
				}
			}
			break;
		case WEST:
			if(colonne-(bateau.getLenght()-1)<0) {
				legitPos = false;
			}
			if(legitPos) {
				for(int i = 0; i<bateau.getLenght(); i++) {
					if(isSomethingThere(ligne, colonne))
						legitPos = false;
				}
			}
			break;
		}
		if(legitPos) {
			switch(direction) {
			case NORTH:
				for(int i = 0; i<bateau.getLenght(); i++)
					changeValueThere(ligne-i, colonne);
				break;
			case SOUTH:
				for(int i = 0; i<bateau.getLenght(); i++)
					changeValueThere(ligne+i, colonne);
				break;
			case EAST:
				for(int i = 0; i<bateau.getLenght(); i++)
					changeValueThere(ligne, colonne+i);
				break;
			case WEST:
				for(int i = 0; i<bateau.getLenght(); i++)
					changeValueThere(ligne, colonne-i);
				break;
			}
		}
		else{
			throw new WrongPlacementException();
		}
	}
	/*
	 * La methode toString ici est presente uniquement pour le debug des Grid, le serveur n'affiche en l'etat actuel jamais l'objet Grid à l'utilisateur.
	 */
	@Override
	public String toString() {
		String message = "";
		for(int x = 0; x<this.grid.length; x++) {
			for(int y = 0; y<this.grid.length; y++) {
				if(this.grid[x][y])
					message += "True ";
				else
					message +="False ";
			}
			message += "\n";
		}
		return message;
	}
}
