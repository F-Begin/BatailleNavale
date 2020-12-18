package Server.game.bateaux.types;

import Server.game.bateaux.Bateau;

public class Torpilleur extends Bateau{
	String name;
		
	public Torpilleur() {
		super();
		this.name = "Torpilleur";
		this.setLenght(2);
	}
	
	@Override
	public String getName() {
		return name;
	}
}
