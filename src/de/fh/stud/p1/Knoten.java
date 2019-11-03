package de.fh.stud.p1;

import java.util.ArrayList;
import java.util.List;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.util.Vector2;

public class Knoten {

	private PacmanTileType[][] view;
	private Knoten parent;
	private PacmanAction parentDirection;
	private PacmanTileType current;
	private Vector2 pos;

	// Full Constructor mit allen Werten
	private Knoten(PacmanTileType[][] view, Vector2 pos, Knoten parent, PacmanAction parentDirection) {
		this.view = view;
		this.parent = parent;
		this.pos = pos;
		this.current = view[pos.getX()][pos.getY()];
		this.parentDirection = parentDirection;
	}

	// Wrapper Constructor mit pos als Vector2
	public Knoten(PacmanTileType[][] view, Vector2 pos) {
		this(view, pos, null, null);
	}

	// Wrapper Constructor mit pos als int x, int y
	public Knoten(PacmanTileType[][] view, int x, int y) {
		this(view, new Vector2(x, y), null, null);
	}

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

	public PacmanTileType getCurrent() {
		return current;
	}

	public Knoten getParent() {
		return parent;
	}

	public PacmanAction getParentDirection() {
		return parentDirection;
	}

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
