package de.fh.stud.p1;

import java.util.ArrayList;
import java.util.List;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p3.MyAgent_P3;
import de.fh.util.Vector2;

/**
 * Knoten klasse
 */
public class Knoten {
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
	 * Mögliche Werte
	 * <ul>
	 * <li>null
	 * <li>0 GO_NORTH
	 * <li>1 GO_WEST
	 * <li>2 GO_EAST
	 * <li>3 GO_SOUTH
	 * </ul>
	 * <br>
	 */
	private PacmanAction parentDirection;
	/**
	 * <p>
	 * TileType des Knotens
	 * </p>
	 * Mögliche Werte
	 * <ul>
	 * <li>0 EMPTY
	 * <li>1 WALL
	 * <li>2 DOT
	 * <li>3 PACMAN
	 * <li>4 GHOST_AND_DOT
	 * <li>5 GHOST
	 * </ul>
	 */
	private PacmanTileType current;
	/**
	 * Position des Knotens
	 */
	private Vector2 pos;

	/**
	 * Echter Constructor
	 * 
	 * @param view            Aktuelle Welt
	 * @param pos             Position des Knotens
	 * @param parent          Parent des Knotens
	 * @param parentDirection Richtung des Parents
	 */
	private Knoten(PacmanTileType[][] view, Vector2 pos, Knoten parent, PacmanAction parentDirection) {
		this.view = view;
		this.parent = parent;
		this.pos = pos;
		this.current = view[pos.getX()][pos.getY()];
		if (parentDirection == null || parentDirection.ordinal() < 4)
			this.parentDirection = parentDirection;
		else
			throw new RuntimeException("ungültiger Wert für parentDirection: " + parentDirection.name());
	}

	/**
	 * Wrapper Constructor mit pos als Vector
	 * 
	 * @param view Aktuelle Welt
	 * @param pos  Position des Knotens
	 */
	public Knoten(PacmanTileType[][] view, Vector2 pos) {
		this(view, pos, null, null);
	}

	/**
	 * @return Liste mit Nachbarknoten
	 */
	public List<Knoten> expand() {
		List<Knoten> children = new ArrayList<Knoten>();
		int x = pos.getX();
		int y = pos.getY();

		if (view[x][y - 1] != PacmanTileType.WALL)// north
			children.add(new Knoten(view, new Vector2(x, y - 1), this, PacmanAction.GO_NORTH));

		if (view[x + 1][y] != PacmanTileType.WALL)// east
			children.add(new Knoten(view, new Vector2(x + 1, y), this, PacmanAction.GO_EAST));

		if (view[x][y + 1] != PacmanTileType.WALL)// south
			children.add(new Knoten(view, new Vector2(x, y + 1), this, PacmanAction.GO_SOUTH));

		if (view[x - 1][y] != PacmanTileType.WALL)// west
			children.add(new Knoten(view, new Vector2(x - 1, y), this, PacmanAction.GO_WEST));

		return children;
	}

	/**
	 * @see Knoten#current
	 * @return TileType des Knotens
	 */
	public PacmanTileType getCurrent() {
		return current;
	}

	/**
	 * @return Parent des Knotens
	 */
	public Knoten getParent() {
		return parent;
	}

	/**
	 * @see Knoten#parentDirection
	 * @return Richtung des Parents
	 */
	public PacmanAction getParentDirection() {
		return parentDirection;
	}

	/**
	 * @return Positon des Knotens
	 */
	public Vector2 getPos() {
		return pos;
	}

	@Override
	public String toString() {
		return pos + " " + current;
	}

	@Override
	public boolean equals(Object object) {
		Knoten knoten = (Knoten) object;
		return this.getPos().equals(knoten.getPos());
	}
}
