package de.fh.stud.p1;

import java.util.ArrayList;
import java.util.List;

import de.fh.pacman.enums.PacmanTileType;

public class Knoten {
	
	PacmanTileType[][] view;
	Knoten parent;
	int x, y;
	PacmanTileType northTile, eastTile, southTile, westTile, current;

	public Knoten(PacmanTileType[][] view, int x, int y, Knoten parent ) {
		this.view = view;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.northTile = view[x][y-1];
		this.eastTile = view[x+1][y];
		this.southTile = view[x][y+1];
		this.westTile = view[x-1][y];
		this.current = view[x][y];
	}

	public List<Knoten> expand() {
		List<Knoten> children = new ArrayList<Knoten>();

		if(northTile != PacmanTileType.WALL) 
			children.add(new Knoten(view, x, y-1, this));
			
		if(eastTile != PacmanTileType.WALL) 
			children.add(new Knoten(view, x+1, y, this));

		if(southTile != PacmanTileType.WALL) 
			children.add(new Knoten(view, x, y+1, this));

		if(westTile != PacmanTileType.WALL) 
			children.add(new Knoten(view, x-1, y, this));

		return children;
	}
	
}
