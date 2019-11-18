package de.fh.stud.p3;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.fh.stud.p1.Knoten;

public class Suche {

	private Queue<Knoten> openList;
	private List<Knoten> closedList;

	public Knoten start(Knoten startKnoten) {
		closedList = new LinkedList<Knoten>();

		// return tiefensuche(startKnoten);
		// return breitensuche(startKnoten);
		return greedySearch(startKnoten);
	}

	public Knoten breitensuche(Knoten node) {
		openList = new LinkedList<Knoten>();
		openList.add(node);
		Knoten n;
		while (!openList.isEmpty()) {
			n = openList.poll();
			if (!closedList.contains(n)) {
				closedList.add(n);
				if (n.isFinished())
					return n;
				else
					openList.addAll(n.expand());
			}
		}
		return null;
	}

	public Knoten tiefensuche(Knoten node) {
		openList = new LinkedList<Knoten>();

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

	public Knoten greedySearch(Knoten node) {
		// openList = new PriorityQueue<Knoten>((n, k) -> k.compareTo(n));
		// openList.addAll(node.expand());
		NodeQueue queue = new NodeQueue();
		queue.addAll(node.expand());

		while (!queue.isEmpty()) {
			node = queue.poll();
			if (!closedList.contains(node)) {
				if (node.isFinished())
					return node;
				queue.addAll(node.expand());
				closedList.add(node);
			}
		}
		System.out.println("null");
		return null;

	}
}
