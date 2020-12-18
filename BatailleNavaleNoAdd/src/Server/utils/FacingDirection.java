package Server.utils;

/*
 * Enum simplement pour la direction des bateaux
 */

public enum FacingDirection {
	NORTH() {},
	SOUTH() {},
	EAST() {},
	WEST() {};
	
	public FacingDirection getFacingDirection() {
		return this;
	}
}
