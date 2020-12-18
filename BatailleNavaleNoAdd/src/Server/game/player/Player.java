package Server.game.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Server.game.bateaux.SetBateau;
import Server.game.grid.Grid;
import Server.game.grid.VisualGrid;
import Server.utils.User;

public class Player {
	
	/*
	 * Les objets players sont les objets qui vont etre utilisé pour représenter un joueur au sein d'une partie
	 * Ils sont crée a partir des Objets Custom User, Grid, VisualGrid, SetBateau...
	 * 
	 * Nous utilisons les Getters et Setters habituels
	 */
	private Grid grid;
	private VisualGrid vgrid;
	
	private User user;
	
	private SetBateau sbateau;
	
	private BufferedReader jin;
	private PrintWriter jout;	
	
	public Player() {}
	
	public Player(Grid g, VisualGrid vg, User u, SetBateau sb) {
		this.grid = g;
		this.vgrid = vg;
		this.user = u;
		this.sbateau = sb;
		try {
			this.jin = new BufferedReader(new InputStreamReader(u.getSocket().getInputStream()));
			this.jout = new PrintWriter(u.getSocket().getOutputStream(), true);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public VisualGrid getVgrid() {
		return vgrid;
	}

	public void setVgrid(VisualGrid vgrid) {
		this.vgrid = vgrid;
	}

	public SetBateau getSbateau() {
		return sbateau;
	}

	public void setSbateau(SetBateau sbateau) {
		this.sbateau = sbateau;
	}
	
	public BufferedReader getJIn() {
		return jin;
	}
	
	public PrintWriter getJOut() {
		return jout;
	}
	
	public User getUser() {
		return user;
	}
}
