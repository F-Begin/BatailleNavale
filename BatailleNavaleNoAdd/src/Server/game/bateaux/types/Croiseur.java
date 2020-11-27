package Server.game.bateaux.types;

import Server.game.bateaux.Bateau;

public class Croiseur extends Bateau{
	String name;
		
	public Croiseur() {
		super();
		this.name = "Croiseur";
		this.setLenght(4);
	}
	
	@Override
	public String getName() {
		return name;
	}
}
