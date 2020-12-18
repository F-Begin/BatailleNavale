package Server.game.bateaux;

public class BateauCoordinate {
	int x;
	int y;
	/*
	 * Cette objet n'est pas encore utilisé 
	 * Elle le sera a l'avenir pour de l'optimisation
	 */
	public BateauCoordinate() {}
	
	public BateauCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public BateauCoordinate getBateauCoordinate() {
		return this;
	}
}
