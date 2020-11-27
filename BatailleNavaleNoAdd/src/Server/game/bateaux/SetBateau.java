package Server.game.bateaux;

import java.util.ArrayList;

import Server.game.bateaux.types.*;

public class SetBateau {
	ArrayList<Bateau> listeBateau = new ArrayList<Bateau>();
	
	public SetBateau() {
		this.listeBateau.add(new PorteAvions());
		this.listeBateau.add(new Croiseur());
		this.listeBateau.add(new ContreTorpilleurs());
		this.listeBateau.add(new ContreTorpilleurs());
		this.listeBateau.add(new Torpilleur());
	}
	
	public ArrayList<Bateau> getListBateau() {
		return this.listeBateau;
	}
}
