package Server.game.bateaux.types;

import Server.game.bateaux.Bateau;

public class ContreTorpilleurs extends Bateau{
	String name;
		
	public ContreTorpilleurs() {
		super();
		this.name = "Contre-torpilleurs";
		this.setLenght(3);
	}
	
	@Override
	public String getName() {
		return name;
	}
}
