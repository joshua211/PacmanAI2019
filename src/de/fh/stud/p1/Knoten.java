package de.fh.stud.p1;

import java.util.ArrayList;
import java.util.List;

import de.fh.pacman.Pacman;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;

public class Knoten {

	PacmanTileType[][] view;
	Knoten parent;
	int x, y;
	PacmanTileType northTile, eastTile, southTile, westTile, current;
	PacmanAction parentDirection;

	public Knoten(PacmanTileType[][] view, int x, int y, Knoten parent, PacmanAction parentDirection ) {
		this.view = view;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.northTile = view[x][y-1];
		this.eastTile = view[x+1][y];
		this.southTile = view[x][y+1];
		this.westTile = view[x-1][y];
		this.current = view[x][y];
		this.parentDirection = parentDirection;
	}

	public List<Knoten> expand() {
		List<Knoten> children = new ArrayList<Knoten>();

		if(northTile != PacmanTileType.WALL) 
			children.add(new Knoten(view, x, y-1, this, PacmanAction.GO_NORTH));
			
		if(eastTile != PacmanTileType.WALL) 
			children.add(new Knoten(view, x+1, y, this, PacmanAction.GO_EAST));

		if(southTile != PacmanTileType.WALL) 
			children.add(new Knoten(view, x, y+1, this, PacmanAction.GO_SOUTH));

		if(westTile != PacmanTileType.WALL) 
			children.add(new Knoten(view, x-1, y, this, PacmanAction.GO_WEST));

		return children;
	}

	@Override
	public String toString() {
		return String.format("X: %d, Y:%d Current: %s",x, y, current.name());
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
	
	@Override
	public boolean equals(Object object) {
		Knoten knoten = (Knoten) object;
		return (this.x == knoten.x && this.y == knoten.y);
	}
}
