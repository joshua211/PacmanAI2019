package de.fh.stud.p3;

import java.util.LinkedList;
import java.util.List;
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

	public static int countDots(Knoten node) {
		int count = 0;
		PacmanTileType[][] view = node.getView();
		for (int i = 0; i < view.length; i++) {
			for (int j = 0; j < view[1].length; j++) {
				if (view[i][j] == PacmanTileType.DOT)
					count++;
			}
		}

		return count;
	}

	public static int countNodesFromStart(Knoten node) {
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

	/**
	 * heuristik zu dem n�chsten dot;
	 * 
	 * @param view
	 *            view von dem knoten f�r den die heuristik berechnet wird
	 * @param node
	 *            knoten f�r den die heuristik berechnet wird
	 * @return anzahl der schritte die pacman gehen m�sste<br>
	 *         um den n�chsten dot zu erreichen von <b>node</b> ausgehend
	 */
	public static int tempHeuristik(PacmanTileType[][] view, Knoten node) {
		// counter sagt die distanz von dem Knoten der die funktion aufruft bis zum
		// n�chsten knoten der einen Dot besitzt
		int count = 0;
		// tempor�re liste die alle knoten enth�llt
		List<Knoten> open = new LinkedList<Knoten>();
		List<Knoten> closed = new LinkedList<Knoten>();
		// alle knoten um den jetzigen herum hinzuf�gen
		closed.addAll(node.expand());
		// debug nachricht
		System.out.println(closed.stream().anyMatch(k -> isDotAtPosition(view, k)));
		// solange in den neuen knoten kein dot gegessen wird
		while (!closed.stream().anyMatch(k -> isDotAtPosition(view, k))) {
			// n�chste ebene hinzuf�gen
			closed.forEach(k -> open.addAll(k.expand()));
			closed.addAll(open);
			open.clear();
			// die distanz erh�hen weil pacman einen schritt mehr gehen muss bis er einen m�glichen dot erreicht
			count++;
			// debug nachricht
			if (closed.stream().anyMatch(k -> isDotAtPosition(view, k)))
				System.out.println(count);
		}
		return count;
	}
	
	/**
	 * heuristik bis zum zielknoten
	 * 
	 * @param view
	 *            view von dem knoten f�r den die heuristik berechnet wird
	 * @param node
	 *            knoten f�r den die heuristik berechnet wird
	 * @return anzhal an schritten die pacman gehen m�sste um einen zielknoten zu erreichen
	 */
	public static int tempZielHeuristik(PacmanTileType[][] view, Knoten node) {
		// counter sagt die distanz von dem Knoten der die funktion aufruft bis zum
		// n�chsten zielKnoten
		int count = 1;
		// tempor�re liste die alle knoten enth�llt
		List<Knoten> liste = new LinkedList<Knoten>();
		// alle knoten um den jetzigen herum hinzuf�gen
		liste.addAll(node.expand());
		// debug nachricht
		System.out.println(liste.stream().anyMatch(k -> isDotAtPosition(view, k)));
		// solange kein zielknoten existiert
		while (!liste.stream().anyMatch(k -> k.isFinished())) {
			// n�chste ebene hinzuf�gen
			liste.forEach(k -> k.expand());
			// die distanz erh�hen weil pacman einen schritt mehr gehen muss bis er einen m�glichen dot erreicht
			count++;
			// debug nachricht
			if (liste.stream().anyMatch(k -> k.isFinished()))
				System.out.println(count);
		}
		return count;
	}

}
