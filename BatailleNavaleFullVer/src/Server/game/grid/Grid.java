package Server.game.grid;

import java.util.Arrays;
import Server.game.bateaux.*;
import Server.utils.FacingDirection;
import Server.utils.WrongPlacementException;

public class Grid {
	private boolean[][] grid = new boolean[10][10];
	
	public Grid() {
		for(boolean[] g : grid) {
			Arrays.fill(g, false);
		}
	}
	
	public boolean isSomethingThere(int x, int y) {
		return this.grid[x][y];
	}
	
	public void changeValueThere(int x, int y) {
		if(this.grid[x][y] == true)
			this.grid[x][y] = false;
		else
			this.grid[x][y] = true;
	}
	
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
