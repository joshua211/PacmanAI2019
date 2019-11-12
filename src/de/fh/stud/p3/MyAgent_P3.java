package de.fh.stud.p3;

import java.util.Stack;

import de.fh.agent.Agent;
import de.fh.agent.PacmanAgent;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p1.Knoten;

public class MyAgent_P3 extends PacmanAgent {

	/**
	 * Aktuelle Wahrnehmung des Agenten, bspw. Position der Geister und Zustand
	 * aller Felder der Welt.
	 */
	private PacmanPercept percept;

	/**
	 * Aktuelle Rückmeldung des Server auf die letzte übermittelte Aktion. Alle
	 * möglichen Serverrückmeldungen:
	 * <ul>
	 * <li>0 GAME_INITIALIZED
	 * <li>1 GAME_OVER
	 * <li>2 BUMPED_INTO_WALL
	 * <li>3 DOT_EATEN
	 * <li>4 MOVEMENT_SUCCESSFUL
	 * <li>5 BUMPED_INTO_PACMAN
	 * </ul>
	 */
	private PacmanActionEffect actionEffect;
	/**
	 * <p>
	 * Das aktuell wahrgenommene 2D Array der Spielwelt
	 * </p>
	 * Mögliche Werte pro Tile:
	 * <ul>
	 * <li>0 EMPTY
	 * <li>1 WALL
	 * <li>2 DOT
	 * <li>3 PACMAN
	 * <li>4 GHOST_AND_DOT
	 * <li>5 GHOST
	 * </ul>
	 */
	private PacmanTileType[][] view;
	/**
	 * Der gefundene Lösungsknoten der Suche
	 */
	private Knoten loesungsKnoten;
	/**
	 * Der gefunde Weg zum Lösungsknoten
	 */
	private Stack<PacmanAction> path;

	/**
	 * Agent Constructor
	 * 
	 * @param name Name des Agents auf dem Server
	 */
	public MyAgent_P3(String name) {
		super(name);
		path = new Stack<PacmanAction>();
	}

	public static void main(String[] args) {
		MyAgent_P3 agent = new MyAgent_P3("");
		Agent.start(agent, "127.0.0.1", 5000);
	}

	/**
	 * In dieser Methode kann das Wissen über die Welt (der Weltzustand)
	 * entsprechend der aktuellen Wahrnehmungen anpasst und ausgebaut werden.
	 *
	 * Wichtig: Diese Methode wird aufgerufen, bevor der Agent handelt, d.h. bevor
	 * die action()-Methode aufgerufen wird...
	 *
	 * @param percept      Aktuelle Wahrnehmung
	 * @param actionEffect Rückmeldung des Servers auf vorhergewählte Aktion
	 */
	@Override
	public void updateState(PacmanPercept percept, PacmanActionEffect actionEffect) {
		this.percept = percept;
		this.actionEffect = actionEffect;
		this.view = percept.getView();

		// Gebe den aktuellen Zustand der Welt aus
		// String view_row = "";
		// System.out.println("viewsize: " + view.length + "*" + view[0].length);
		// for (int x = 0; x < view[0].length; x++) {
		// for (int y = 0; y < view.length; y++) {
		// view_row += " " + view[y][x];
		// }
		// System.out.println(view_row);
		// view_row = "";
		// }
		// System.out.println("-------------------------------");

	}

	/**
	 * In dieser Methode wählt der Agent seine nächste Aktion aus, basierend auf dem
	 * Weltzustand des Agenten.
	 * <ul>
	 * <li>0 GO_NORTH
	 * <li>1 GO_WEST
	 * <li>2 GO_EAST
	 * <li>3 GO_SOUTH
	 * <li>4 QUIT_GAME
	 * <li>5 WAIT
	 * </ul>
	 * 
	 * @return Die nächste PacmanAction die vom Server ausgeführt werden soll
	 */
	@Override
	public PacmanAction action() {
		// Wenn noch keine Lösung gefunden wurde, dann starte die Suche
		if (path.isEmpty()) {
			Knoten current = new Knoten(view, percept.getPosition());

			/*
			 * TODO Praktikum 4 [2]: Entscheiden Sie hier welches Suchverfahren ausgeführt
			 * werden soll.
			 */
			Suche suche = new Suche();

			loesungsKnoten = suche.start(current);

			Knoten n = loesungsKnoten;

			while (n != current) {
				path.push(n.getParentDirection());
				n = n.getParent();
			}

			System.out.println(loesungsKnoten);
		}

		// Wenn die Suche eine Lösung gefunden hat, dann ermittle die als nächstes
		// auszuführende Aktion
		if (!path.isEmpty()) {
			PacmanAction a = path.pop();
			nextAction = a;
			System.out.println(a);

		} else {
			// Ansonsten wurde keine Lösung gefunden und der Pacman kann das Spiel aufgeben
			nextAction = PacmanAction.QUIT_GAME;
		}

		return nextAction;
	}

}
