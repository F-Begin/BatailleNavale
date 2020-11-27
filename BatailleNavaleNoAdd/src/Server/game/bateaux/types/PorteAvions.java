package Server.game.bateaux.types;

import Server.game.bateaux.Bateau;

public class PorteAvions extends Bateau {
	String name;
		
	public PorteAvions() {
		super();
		this.name = "Porte-Avions";
		this.setLenght(5);
	}
	
	@Override
	public String getName() {
		return name;
	}
}
