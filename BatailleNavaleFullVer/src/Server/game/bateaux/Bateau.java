package Server.game.bateaux;

import Server.utils.FacingDirection;

public class Bateau {
	private String name;
	private int lenght;
	private boolean[] state;
	private BateauCoordinate coor;
	private FacingDirection direction;
	
	public Bateau() {}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public boolean[] getState() {
		return state;
	}

	public void setState(boolean[] state) {
		this.state = state;
	}
	
	public void damage(int index) {
		this.state[index] = false;
	}
	
	public boolean getStateAtPos(int index) {
		return this.state[index];
	}
	
	public Bateau getBateau() {
		return this;
	}
	
	public BateauCoordinate getCoor() {
		return this.coor;
	}
	
	public FacingDirection getBateauDirection() {
		return this.direction.getFacingDirection();
	}
	
	public String getName() {
		return this.name;
	}
}
