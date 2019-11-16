package de.fh.stud.p3;

import java.util.Stack;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p1.Knoten;

public class Util {

	public static PacmanTileType[][] copyView(PacmanTileType[][] view) {
		PacmanTileType[][] newView = new PacmanTileType[view.length][view[0].length];
		for (int y = 0; y < view.length; y++)
			for (int x = 0; x < view[0].length; x++)
				newView[y][x] = view[y][x];
		return newView;
	}

	public static int countNodes(Knoten node) {
		int count = 0;
		while (node.getParent() != null) {
			node = node.getParent();
			count++;
		}

		return count;
	}

	public static boolean isDotAtPosition(PacmanTileType[][] view, Knoten node) {
		if (view[node.getPacPos().getX()][node.getPacPos().getY()] == PacmanTileType.DOT)
			return true;
		return false;
	}

	public static Stack<PacmanAction> getPathToNode(Knoten node) {
		Stack<PacmanAction> path = new Stack<>();

		while (node.getParent() != null) {
			path.push(node.getLastAction());
			node = node.getParent();
		}

		return path;
	}

}
