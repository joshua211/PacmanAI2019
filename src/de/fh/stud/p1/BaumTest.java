package de.fh.stud.p1;

import java.util.ArrayList;
import java.util.List;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.util.Vector2;

public class BaumTest {

	public static void main(String[] args) {
		//Anfangszustand nach Aufgabe
		PacmanTileType[][] view = {
				{PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL},
				{PacmanTileType.WALL,PacmanTileType.EMPTY,PacmanTileType.DOT,PacmanTileType.WALL},
				{PacmanTileType.WALL,PacmanTileType.DOT,PacmanTileType.WALL,PacmanTileType.WALL},
				{PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL}
		};
		//Startposition des Pacman
		Vector2 pos = new Vector2(1, 1);
		List<Knoten> tree = new ArrayList<Knoten>();
		Knoten parent = new Knoten(view, pos);
		tree.add(parent);
		while(tree.size() < 10) {
			tree.addAll(parent.expand());
			parent = tree.get(tree.indexOf(parent) +1);
			tree.forEach(k -> System.out.println(k));
		}
		tree.forEach(k -> System.out.println(k));
		

		/*
		/ Problem: Die Knoten rechts und unter dem Startknoten können als folgeknoten nur den Startknoten expandieren
		/ und wiederholen sich? Keine Ahnung
		*/
	}
}
