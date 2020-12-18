package Server.game.grid;

import java.util.Arrays;

import Server.game.bateaux.Bateau;
import Server.utils.FacingDirection;

public class VisualGrid {
	private char[][] visualOwnGrid= new char[10][10];
	private char[][] visualEnnemyGrid = new char[10][10];
	
	/*
	 * ~ : Case d'eau (Encore inconnue)
	 * 
	 * O : Case d'eau (Déjà tiré ici)
	 * X : Case touché !
	 * 
	 * < : Arrière d'un bateau horizontal.
	 * > : Avant d'un bateau horizontal.
	 * = : Corps d'un bateau simple.
	 * A : Avant d'un bateau vertical.
	 * V : Arrière d'un bateau vertical.
	 * | : Corps d'un bateau vertical.
	 * 
	 */
	
	public VisualGrid() {
		for(char[] g : this.visualOwnGrid) {
			Arrays.fill(g, '~');
		}
		for(char[] g : this.visualEnnemyGrid) {
			Arrays.fill(g, '~');
		}
	}
	
	public void changeCharOnOwnVisualGrid(int x, int y, char c) {
		this.visualOwnGrid[x][y] = c;
	}
	public void changeCharOnEnnemyVisualGrid(int x, int y, char c) {
		this.visualEnnemyGrid[x][y] = c;
	}
	
	public char[][] getVisualOwnGrid() {
		return this.visualOwnGrid;
	}
	
	public char[][] getVisualEnnemyGrid() {
		return this.visualEnnemyGrid;
	}
	
	public void placeVisualBoatOnGrid(int ligne, int colonne, Bateau boat, FacingDirection direction) {
		switch(direction) {
		case NORTH:
			for(int i = 0; i<boat.getLenght(); i++) {
				if(i==0) {
					this.changeCharOnOwnVisualGrid(ligne, colonne, 'V');
				}
				else if(i==boat.getLenght()-1) {
					this.changeCharOnOwnVisualGrid(ligne, colonne, 'A');
				}
				else {
					this.changeCharOnOwnVisualGrid(ligne, colonne, '|');
				}
				ligne--;
			}
			break;
		case SOUTH:
			for(int i = 0; i<boat.getLenght(); i++) {
				if(i==0) {
					this.changeCharOnOwnVisualGrid(ligne, colonne, 'A');
				}
				else if(i==boat.getLenght()-1) {
					this.changeCharOnOwnVisualGrid(ligne, colonne, 'V');
				}
				else {
					this.changeCharOnOwnVisualGrid(ligne, colonne, '|');
				}
				ligne++;
			}
			break;
		case EAST:
			for(int i = 0; i<boat.getLenght(); i++) {
				if(i==0) {
					this.changeCharOnOwnVisualGrid(ligne, colonne, '<');
				}
				else if(i==boat.getLenght()-1) {
					this.changeCharOnOwnVisualGrid(ligne, colonne, '>');
				}
				else {
					this.changeCharOnOwnVisualGrid(ligne, colonne, '=');
				}
				colonne++;
			}
			break;
		case WEST:
			for(int i = 0; i<boat.getLenght(); i++) {
				if(i==0) {
					this.changeCharOnOwnVisualGrid(ligne, colonne, '>');
				}
				else if(i==boat.getLenght()-1) {
					this.changeCharOnOwnVisualGrid(ligne, colonne, '<');
				}
				else {
					this.changeCharOnOwnVisualGrid(ligne, colonne, '=');
				}
				colonne--;
			}
			break;
		}
	}
	
	@Override
	public String toString() {
		String message = "Votre grille : \nX 0 1 2 3 4 5 6 7 8 9\n";
		for(int ligne = 0; ligne<10; ligne++) {
			message += ligne + " ";
			for(int colonne = 0; colonne<10; colonne++) {
				message += this.visualOwnGrid[ligne][colonne] + " ";
			}
			message+="\n";
		}
		message+="\nGrille de votre adversaire : \nX 0 1 2 3 4 5 6 7 8 9\n";
		for(int ligne = 0; ligne<10; ligne++) {
			message += ligne + " ";
			for(int colonne = 0; colonne<10; colonne++) {
				message += this.visualEnnemyGrid[ligne][colonne] + " ";
			}
			message+="\n";
		}
		return message;
	}
	
	public String OnlyOwnGrid() {
		String message = "Votre grille : \nX 0 1 2 3 4 5 6 7 8 9\n";
		for(int ligne = 0; ligne<10; ligne++) {
			message+=ligne + " ";
			for(int colonne = 0; colonne<10; colonne++) {
				message += this.visualOwnGrid[ligne][colonne] + " ";
			}
			message+="\n";
		}
		return message;
	}
}