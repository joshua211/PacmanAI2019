package de.fh.stud.p2;

import de.fh.agent.PacmanAgent;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;

import de.fh.agent.Agent;
import de.fh.pacman.enums.PacmanTileType;

public class MyAgent_P2 extends PacmanAgent {

	/*
	 * TODO Praktikum 2 [1]: Fügt gemäß der Aufgabenstellung neue Attribute hinzu, falls notwendig.
	 */
	
	/**
	 * Die letzte Wahrnehmung der Spielwelt
	 */
	private PacmanPercept percept;

	/**
	 * Das aktuell wahrgenommene 2D Array der Spielwelt
	 */
	private PacmanTileType[][] view;
	
	/**
	 * Die zuletzt empfangene Rückmeldung des Servers auf die ausgeführte Aktion
	 */
	private PacmanActionEffect actionEffect;
	
	public MyAgent_P2(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		MyAgent_P2 agent = new MyAgent_P2("");
		Agent.start(agent, "127.0.0.1", 5000);
	}
	
	/**
	 * In dieser Methode kann das Wissen über die Welt (der Weltzustand)
	 * entsprechend der aktuellen Wahrnehmungen anpasst und ausgebaut werden.
	 *
	 * Wichtig: Diese Methode wird aufgerufen, bevor der Agent handelt, d.h.
	 * bevor die action()-Methode aufgerufen wird...
	 *
	 * @param percept Aktuelle Wahrnehmung
	 * @param actionEffect Rückmeldung des Servers auf vorhergewählte Aktion
	 */
	@Override
	public void updateState(PacmanPercept percept, PacmanActionEffect actionEffect) {
		/*
		 * Aktuelle Wahrnehmung des Agenten, bspw. Position der Geister und Zustand aller Felder der Welt.
		 */
		this.percept = percept;
		
		/*
         * Aktuelle Rückmeldung des Server auf die letzte übermittelte Aktion.
         * 
         * Alle möglichen Serverrückmeldungen:
         * PacmanActionEffect.GAME_INITIALIZED
         * PacmanActionEffect.GAME_OVER
         * PacmanActionEffect.BUMPED_INTO_WALL
         * PacmanActionEffect.MOVEMENT_SUCCESSFUL
         * PacmanActionEffect.DOT_EATEN
		 */
		this.actionEffect = actionEffect;
		
		/*
         * percept.getView() enthält die aktuelle Felderbelegung in einem 2D Array
		 * 
         * Folgende Felderbelegungen sind möglich:
		 * PacmanTileType.WALL;
         * PacmanTileType.DOT
         * PacmanTileType.EMPTY
         * PacmanTileType.PACMAN
         * PacmanTileType.GHOST
         * PacmanTileType.GHOST_AND_DOT
		 */
		this.view = percept.getView();
		
		//Beispiel:
		//Gebe den aktuellen Zustand der Welt aus
		String view_row = "";
		System.out.println("Weltgröße: " + view.length + "*" + view[0].length);
		for (int x = 0; x < view[0].length; x++) {
			for (int y = 0; y < view.length; y++) {
				view_row += " " + view[y][x];
			}
			System.out.println(view_row);
			view_row = "";
		}
		System.out.println("-------------------------------");
		
		
		/*
		 * TODO Praktikum 2 [1]: Erweitern Sie diese updateState-Methode gemäß der Aufgabenstellung.
		 */
		
	}
	
	/**
	 * In dieser Methode wählt der Agent seine nächste Aktion aus, basierend
	 * auf dem Weltzustand des Agenten.
	 * @return Die nächste PacmanAction die vom Server ausgeführt werden soll
	 */
	@Override
	public PacmanAction action() {
		
		/*
		 * Die möglichen zurückzugebenden PacmanActions sind:
		 * PacmanAction.GO_EAST
		 * PacmanAction.GO_NORTH
		 * PacmanAction.GO_SOUTH
		 * PacmanAction.GO_WEST
		 * PacmanAction.QUIT_GAME
		 * PacmanAction.WAIT
		 */
		
		//Nachdem das Spiel gestartet wurde, geht der Agent nach Osten
		if(actionEffect == PacmanActionEffect.GAME_INITIALIZED) {
			nextAction = PacmanAction.GO_EAST;
		}
		
		/*
		 * TODO Praktikum 2 [1]: Erweitern Sie diese action-Methode gemäß der Aufgabenstellung.
		 */
		
		return nextAction;
	}
}
