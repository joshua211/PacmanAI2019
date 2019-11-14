package de.fh.stud.p1;

import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.util.Vector2;

/**
 * Knoten ist die Welt zu einem bestimmten Zeitpunkt
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
	private PacmanAction parentDirection;
	/**
	 * Position von Pacman
	 */
	private Vector2 pacPos;

	/**
	 * Echter Constructor
	 * 
	 * @param view            Aktuelle Welt
	 * @param pos             Position von Pacman
	 * @param parent          Parent des Knotens
	 * @param parentDirection Richtung des Parents
	 */
	private Knoten(PacmanTileType[][] view, Vector2 pacPos, Knoten parent, PacmanAction parentDirection) {
		this.view = view;
		this.parent = parent;
		this.pacPos = pacPos;
		this.parentDirection = parentDirection;
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
			PacmanTileType[][] newView = copyView(view);
			newView[x][y - 1] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(newView, new Vector2(x, y - 1), this, PacmanAction.GO_NORTH));
		}

		// East
		if (view[x + 1][y] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = copyView(view);
			newView[x + 1][y] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(newView, new Vector2(x + 1, y), this, PacmanAction.GO_EAST));
		}

		// South
		if (view[x][y + 1] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = copyView(view);
			newView[x][y + 1] = PacmanTileType.PACMAN;
			newView[x][y] = PacmanTileType.EMPTY;
			children.add(new Knoten(newView, new Vector2(x, y + 1), this, PacmanAction.GO_SOUTH));
		}

		// West
		if (view[x - 1][y] != PacmanTileType.WALL) {
			PacmanTileType[][] newView = copyView(view);
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
	 * @see Knoten#parentDirection
	 * @return Richtung des Parents
	 */
	public PacmanAction getParentDirection() {
		return parentDirection;
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
		str += "viewsize: " + view.length + "*" + view[0].length + "\n";
		for (int x = 0; x < view[0].length; x++) {
			String view_row = "";
			for (int y = 0; y < view.length; y++)
				view_row += view[y][x] + " ";
			str += view_row + "\n";
		}
		return str;
	}

	@Override
	public boolean equals(Object object) {
		Knoten knoten = (Knoten) object;
		return Arrays.equals(this.view, knoten.getView());
	}

	// public int getHeuristik() {
	// if(this.current == PacmanTileType.DOT)
	// return 0;
	// Knoten ziel = new Suche().Breitensuche(this);
	// int x = ziel.getPos().getX()-this.getPos().getX();
	// int y = ziel.getPos().getY()-this.getPos().getY();
	// int dist = x*x + y*y;
	// dist = (int) Math.sqrt(dist);
	// return dist;
	// }

	public PacmanTileType[][] copyView(PacmanTileType[][] view) {
		PacmanTileType[][] newView = new PacmanTileType[view.length][view[0].length];
		for (int y = 0; y < view.length; y++)
			for (int x = 0; x < view[0].length; x++)
				newView[y][x] = view[y][x];
		return newView;
	}

	public boolean isFinished() {
		// System.out.println("Betrachte knoten: " + this.getPacPos());
		for (int x = 0; x < view.length; x++) {
			for (int y = 0; y < view[0].length; y++) {
				if (view[x][y] == PacmanTileType.DOT) {
					// System.out.println("####################false##################");
					return false;
				}
			}
		}
		// System.out.println("####################true###################");
		return true;

	}
}
