package Server.utils;

@SuppressWarnings("serial")
public class WrongPlacementException extends Exception{
	
	public WrongPlacementException() {
		super("Mauvais Placement !");
	}
	
	public WrongPlacementException(String message) {
		super(message);
	}
}
