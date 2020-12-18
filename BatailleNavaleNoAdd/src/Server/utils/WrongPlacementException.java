package Server.utils;

@SuppressWarnings("serial")
public class WrongPlacementException extends Exception{
	
	//Création d'une exception custom pour dire qu'un bateau est a un placement incorrecte 
	
	public WrongPlacementException() {
		super("Mauvais Placement !");
	}
	
	public WrongPlacementException(String message) {
		super(message);
	}
}
