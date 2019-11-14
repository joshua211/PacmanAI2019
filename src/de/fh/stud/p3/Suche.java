package de.fh.stud.p3;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.fh.stud.p1.Knoten;

public class Suche {

	private Queue<Knoten> openList = new LinkedList<Knoten>();
	private List<Knoten> closedList = new LinkedList<Knoten>();
	/*
	 * TODO Praktikum 4 [1]: Erweitern Sie diese Klasse um weitere Suchstrategien
	 * (siehe Aufgabenblatt) zu unterstützen.
	 */

	public Knoten start(Knoten startKnoten) {
		return tiefensuche(startKnoten);
		// return breitensuche(startKnoten);
		// return greedySearch(startKnoten);
	}

//	public Knoten breitensuche(Knoten node) {
//
//		openList.add(node);
//		Knoten n;
//		while (!openList.isEmpty()) {
//			n = openList.poll();
//			if (!closedList.contains(n)) {
//				closedList.add(n);
//				if (n.isFinished())
//					return n;
//				else
//					openList.addAll(n.expand());
//			}
//		}
//		return null;
//	}

//	public Knoten breitensuche(Knoten node) {
//		openList.add(node);
//		Knoten n;
//
//		while (!openList.isEmpty()) {
//			n = openList.poll();
//			if (n.isFinished()) {
//				System.out.println("Found node: " + n);
//				return n;
//			}
//
//			openList.addAll(n.expand());
//		}
//		return null;
//	}

	public Knoten tiefensuche(Knoten node) {
		System.out.println(node);
		if (closedList.contains(node))
			return null;
		closedList.add(node);
		if (!node.isFinished()) {
			openList.addAll(node.expand());
			for (Knoten n : openList) {
				Knoten erg = tiefensuche(n);
				if (erg != null)
					return erg;
			}
		}
		return node;
	}

//	public Knoten greedySearch(Knoten node) {
//		openList.add(node);
//		while (!openList.isEmpty()) {
//			Knoten n = openList.poll();
//
//			if (!closedList.contains(n)) {
//				closedList.add(n);
//				if (n.getCurrent() == PacmanTileType.DOT)
//					return n;
//				else
//					openList.addAll(n.expand());
//			}
//			System.out.println("open : " + openList);
//			System.out.println("closed: " + closedList);
//		}
//		return null;
//	 }
}
