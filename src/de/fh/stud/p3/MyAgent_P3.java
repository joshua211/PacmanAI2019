package de.fh.stud.p3;

import de.fh.agent.Agent;
import de.fh.agent.PacmanAgent;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p1.Knoten;

public class MyAgent_P3 extends PacmanAgent {

	/**
	 * Die aktuelle Wahrnehmung der Spielwelt
	 */
	private PacmanPercept percept;
	/**
	 * Die empfangene Rückmeldung des Servers auf die zuletzt ausgeführte Aktion
	 */
	private PacmanActionEffect actionEffect;
	/**
	 * Das aktuell wahrgenommene 2D Array der Spielwelt
	 */
	private PacmanTileType[][] view;
	
	/**
	 * Der gefundene Lösungknoten der Suche
	 */
	private Knoten loesungsKnoten;
	
	public MyAgent_P3(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		MyAgent_P3 agent = new MyAgent_P3("");
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
		
		
		//Gebe den aktuellen Zustand der Welt aus
		String view_row = "";
		System.out.println("viewsize: " + view.length + "*" + view[0].length);
		for (int x = 0; x < view[0].length; x++) {
			for (int y = 0; y < view.length; y++) {
				view_row += " " + view[y][x];
			}
			System.out.println(view_row);
			view_row = "";
		}
		System.out.println("-------------------------------");
		
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
		
		//Wenn noch keine Lösung gefunden wurde, dann starte die Suche
		if (loesungsKnoten == null) {
			/*
			 * TODO Praktikum 3 [3]: Übergeben Sie hier der Suche die notwendigen Informationen, um
			 * von einem Startzustand zu einem Zielzustand zu gelangen.
			 */
			/*
			 * TODO Praktikum 4 [2]: Entscheiden Sie hier welches Suchverfahren ausgeführt werden soll.
			 */
			Suche suche = new Suche();
			loesungsKnoten = suche.start();
		}
		
		//Wenn die Suche eine Lösung gefunden hat, dann ermittle die als nächstes auszuführende Aktion
		if (loesungsKnoten != null) {
			/*
			 * TODO Praktikum 3 [4]: Ermitteln Sie hier die als naechstes auszufuehrende Aktion
			 * basierend auf dem loesungsknoten und weisen Sie diese nextAction zu.
			 */
			
		} else {
			//Ansonsten wurde keine Lösung gefunden und der Pacman kann das Spiel aufgeben
			nextAction = PacmanAction.QUIT_GAME;
		}
		
		return nextAction;
	}
	
}
