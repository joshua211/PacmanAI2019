package de.fh.stud.p1;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p3.Suche;
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

		//North
		if (view[x][y - 1] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = copyView(view);
			newView[x][y -1] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(newView, new Vector2(x, y - 1), this, PacmanAction.GO_NORTH));
		}
			
		//East
		if (view[x + 1][y] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = copyView(view);
			newView[x + 1][y] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(view, new Vector2(x + 1, y), this, PacmanAction.GO_EAST));
		}

		//South
		if (view[x][y + 1] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = copyView(view);
			newView[x][y + 1] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(view, new Vector2(x, y + 1), this, PacmanAction.GO_SOUTH));
		}

		//West
		if (view[x - 1][y] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = copyView(view);
			newView[x - 1][y] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(view, new Vector2(x - 1, y), this, PacmanAction.GO_WEST));
		}
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
	
	public PacmanTileType[][] getView() {
		return view;
	}

	@Override
	public String toString() {
		return pos + " " + current;
	}

	@Override
	public boolean equals(Object object) {
		Knoten knoten = (Knoten) object;
		return this.getPos().equals(knoten.getPos()) && Arrays.equals(this.view, knoten.getView());
	}
	
	public int getHeuristik() {
	
		if(this.current == PacmanTileType.DOT)
			return 0;
		Knoten ziel = new Suche().Breitensuche(this);
		int x = ziel.getPos().getX()-this.getPos().getX();
		int y = ziel.getPos().getY()-this.getPos().getY();
		int dist = x*x + y*y;
		dist = (int) Math.sqrt(dist);
		return dist;
	}
	
	public PacmanTileType[][] copyView(PacmanTileType[][] view) {
		PacmanTileType[][] newView = new PacmanTileType[view.length][view[0].length];
		for (int y = 0; y < view.length; y++) 
			for (int x = 0; x < view[0].length; x++) 
				newView[y][x] = view[y][x];	
		return newView;
	}
}
