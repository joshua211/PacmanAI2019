package de.fh.stud.p3;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import de.fh.stud.p1.Knoten;

public class Suche {
	private Queue<Knoten> openList;
	public List<Knoten> closedList;

	public Knoten start(Knoten startKnoten) {
		Knoten lk =  greedySearch(startKnoten);
		System.out.println("ClosedList: " + closedList.size());
		if(openList != null)
			System.out.println("OpenList: " + openList.size());
		return lk;
	}

	public Knoten breitensuche(Knoten node) {
		openList = new LinkedList<Knoten>();
		closedList = new LinkedList<Knoten>();
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
		Stack<Knoten> openStack = new Stack<Knoten>();
		closedList = new LinkedList<Knoten>();
		openStack.add(node);
		while(!openStack.isEmpty()) {
			Knoten temp = openStack.pop();
			closedList.add(temp);
			for(Knoten c: temp.expand()) {
				if(!openStack.contains(c) && !closedList.contains(c)) {
					openStack.push(c);
				}
				if(c.isFinished()) {
					System.out.println(openStack.size());
					return c;
				}
			}
		}
		return null;
	}

	public Knoten greedySearch(Knoten node) {
		openList = new LinkedList<Knoten>();
		closedList = new LinkedList<Knoten>();
		openList.add(node);
		while(!openList.isEmpty()) {
			Knoten besth = openList.stream().min((n,k)->n.compareTo(k)).get();
			if(besth.isFinished()){
				return besth;
			}
			for(Knoten c: besth.expand()) {
				if(!openList.contains(c) && !closedList.contains(c)) {
					openList.add(c);
				}
			}
			openList.remove(besth);
			closedList.add(besth);
		}
		return null;
	}
	
	public Knoten ucs(Knoten node) {
		openList = new PriorityQueue<Knoten>((n, k) -> n.getCost() - k.getCost());
		closedList = new LinkedList<Knoten>();
		openList.add(node);
		while(!openList.isEmpty()) {
			Knoten parent = openList.poll();
			if(parent.isFinished()) {
				return parent;
			}
			for(Knoten c: parent.expand()) {
				if(!closedList.contains(c)) {
					closedList.add(c);
					openList.add(c);
					
				}
			}
		}
		return null;
	}

	public Knoten aStar(Knoten node) {
		openList = new LinkedList<Knoten>();
		closedList = new LinkedList<Knoten>();
		openList.add(node);
		while(!openList.isEmpty()) {
			Knoten bestf = openList.stream().min((n,k) -> Integer.compare(n.getTotalCost(), k.getTotalCost())).get();
			openList.remove(bestf);
			closedList.add(bestf);
			
			if(bestf.isFinished())
				return bestf;
			
			for(Knoten c: bestf.expand()) {
				if(!openList.contains(c) && !closedList.contains(c)) {
					openList.add(c);
				}
			}
			openList.remove(bestf);
			closedList.add(bestf);
		}
		return null;
	}
	
}
