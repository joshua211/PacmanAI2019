package de.fh.stud.p3;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p1.Knoten;

public class Suche {
	
	public Queue<Knoten> openList = new LinkedList<Knoten>();
	public List<Knoten> closedList = new LinkedList<Knoten>();
	/*
	 * TODO Praktikum 4 [1]: Erweitern Sie diese Klasse um weitere Suchstrategien (siehe Aufgabenblatt)
	 * zu unterstützen.
	 */
	
	public Knoten start(Knoten startKnoten) {
		
		//return Tiefensuche(startKnoten);
		//return Breitensuche(startKnoten);
		return GreedySearch(startKnoten);
	}
	
	
	public Knoten Breitensuche(Knoten node) {
		openList.add(node);
		while(!openList.isEmpty()) {
			Knoten n = openList.poll();
			
			if(!closedList.contains(n)) {
				closedList.add(n);
				if(n.getCurrent() == PacmanTileType.DOT)
					return n;
				else
					openList.addAll(n.expand());
			}	
			System.out.println("open  : " + openList);
			System.out.println("closed: " + closedList);
		}
		return null;
		
	}
	
	public Knoten Tiefensuche(Knoten node) {
		
		if(closedList.contains(node)) 
			return null;
		closedList.add(node);
		
		Knoten nextDotNode = null;
		if(node.getCurrent() != PacmanTileType.DOT) {
			List<Knoten> nodes = node.expand();
			for(Knoten n: nodes) {
				Knoten erg = Tiefensuche(n);
				if(erg != null)
					return erg;
			}
		}
		else
			nextDotNode = node;
		return nextDotNode;
	} 
	
	public Knoten GreedySearch(Knoten node) {
		openList.addAll(node.expand());
		Knoten current = node;
		while(current.getHeuristik() > 0) {
			if(openList.isEmpty())
				return null;
			current = openList.stream().min((e,n) -> e.getHeuristik() - n.getHeuristik()).get();
			openList.remove(current);
			if(!closedList.contains(current)) {
				openList.addAll(current.expand());
				closedList.add(current);
			}
		}
		return current;
	}
	
}
