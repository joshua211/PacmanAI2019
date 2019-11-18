package de.fh.stud.p3;

import java.util.Collection;
import java.util.LinkedList;

import de.fh.stud.p1.Knoten;

public class NodeQueue {
	private LinkedList<Knoten> queue;

	public NodeQueue() {
		queue = new LinkedList<>();
	}

	public void add(Knoten node) {
		if (!isEmpty()) {
			if (node.getHeuristik() <= queue.peek().getHeuristik())
				queue.addFirst(node);
			else {
				queue.add(node);
			}
		} else
			queue.add(node);

	}

	public void addAll(Collection<Knoten> nodes) {
		nodes.forEach(n -> add(n));
	}

	public Knoten poll() {
		return queue.pollFirst();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
