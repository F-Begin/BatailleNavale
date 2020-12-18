package Server.game.bateaux;

import java.util.ArrayList;

import Server.game.bateaux.types.*;

public class SetBateau {
	/*
	 * Un SetBatteau represente simplement une ArrayList contenant tout les bateaux d'un joueur
	 */
	ArrayList<Bateau> listeBateau = new ArrayList<Bateau>();
	
	public SetBateau() {
		this.listeBateau.add(new PorteAvions());
		this.listeBateau.add(new Croiseur());
		this.listeBateau.add(new ContreTorpilleurs());
		this.listeBateau.add(new ContreTorpilleurs());
		this.listeBateau.add(new Torpilleur());
	}
	/*
	 * Renvoie la liste de bateau d'un joueur
	 */
	public ArrayList<Bateau> getListBateau() {
		return this.listeBateau;
	}
}
