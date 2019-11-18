package de.fh.stud.p1;

import java.util.LinkedList;
import java.util.List;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p3.Util;
import de.fh.util.Vector2;

/**
 * Knoten ist die Welt zu einem bestimmten Zeitpunkt
 */
public class Knoten implements Comparable<Knoten> {
	/**
	 * aktuelle Welt
	 */
	private PacmanTileType[][] view;
	/**
	 * Parent des Knotens
	 */
	private Knoten parent;
	/**
	 * <p>
	 * Richtung des Parents
	 * </p>
	 * MÃ¶gliche Werte
	 * <ul>
	 * <li>null
	 * <li>0 GO_NORTH
	 * <li>1 GO_WEST
	 * <li>2 GO_EAST
	 * <li>3 GO_SOUTH
	 * </ul>
	 * <br>
	 */

	private int heuristik;

	public static int expansions = 0;
	public int id;

	private PacmanAction lastAction;
	/**
	 * Position von Pacman
	 */
	private Vector2 pacPos;

	/**
	 * Echter Constructor
	 * 
	 * @param view       Aktuelle Welt
	 * @param pos        Position von Pacman
	 * @param parent     Parent des Knotens
	 * @param lastAction Richtung des Parents
	 */
	private Knoten(PacmanTileType[][] view, Vector2 pacPos, Knoten parent, PacmanAction lastAction) {
		this.view = view;
		this.parent = parent;
		this.pacPos = pacPos;
		this.lastAction = lastAction;
		expansions++;
		this.id = expansions;
	}

	/**
	 * Wrapper Constructor
	 * 
	 * @param view Aktuelle Welt
	 * @param pos  Position von Pacman
	 */
	public Knoten(PacmanTileType[][] view, Vector2 pacPos) {
		this(view, pacPos, null, null);
	}

	/**
	 * @return Liste mit Nachbarknoten
	 */
	public List<Knoten> expand() {
		List<Knoten> children = new LinkedList<Knoten>();
		int x = pacPos.getX();
		int y = pacPos.getY();

		// North
		if (view[x][y - 1] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = Util.copyView(view);
			newView[x][y - 1] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(newView, new Vector2(x, y - 1), this, PacmanAction.GO_NORTH));
		}

		// East
		if (view[x + 1][y] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = Util.copyView(view);
			newView[x + 1][y] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(newView, new Vector2(x + 1, y), this, PacmanAction.GO_EAST));
		}

		// South
		if (view[x][y + 1] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = Util.copyView(view);
			newView[x][y + 1] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(newView, new Vector2(x, y + 1), this, PacmanAction.GO_SOUTH));
		}

		// West
		if (view[x - 1][y] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = Util.copyView(view);
			newView[x - 1][y] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(newView, new Vector2(x - 1, y), this, PacmanAction.GO_WEST));
		}
		return children;
	}

	/**
	 * @return Parent des Knotens
	 */
	public Knoten getParent() {
		return parent;
	}

	/**
	 * @see Knoten#lastAction
	 * @return Richtung des Parents
	 */
	public PacmanAction getLastAction() {
		return lastAction;
	}

	/**
	 * @return Positon von Pacman
	 */
	public Vector2 getPacPos() {
		return pacPos;
	}

	/**
	 * @return die Welt <b>dieses</b> Knotens
	 */
	public PacmanTileType[][] getView() {
		return view;
	}

	@Override
	public String toString() {
		String str = "";
		str += "ID: " + id;
		str += "viewsize: " + view.length + "*" + view[0].length + "\n";
		for (int x = 0; x < view[0].length; x++) {
			String view_row = "";
			for (int y = 0; y < view.length; y++)
				view_row += view[y][x] + " ";
			str += view_row + "\n";
		}
		return str;
	}

	/**
	 * @return Heurstik des aktuellen Knotens in abh�ngigkeit der Anzahl von dots in
	 *         seiner Umgebung
	 */
	public int getHeuristik() {
		return Util.countDots(this);
	}

	public boolean isFinished() {
		for (int x = 0; x < view.length; x++) {
			for (int y = 0; y < view[0].length; y++) {
				if (view[x][y] == PacmanTileType.DOT) {
					return false;
				}
			}
		}
		System.out.println(this);
		return true;
	}

	@Override
	public int compareTo(Knoten n) {
		int h = getHeuristik();
		int nh = n.getHeuristik();
		return h == nh ? 0 : (h < nh ? -1 : 1);
	}

	@Override
	public boolean equals(Object object) {
		Knoten knoten = (Knoten) object;
		for (int i = 0; i < view.length; i++) {
			for (int j = 0; j < view[0].length; j++) {
				if (!view[i][j].equals((knoten.getView()[i][j])))
					return false;
			}
		}
		return true;
	}

}