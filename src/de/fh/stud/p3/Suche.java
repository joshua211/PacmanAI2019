package de.fh.stud.p3;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.fh.pacman.PacmanTile;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p1.Knoten;

public class Suche {
	
	private Queue<Knoten> openList = new LinkedList<Knoten>();
	private List<Knoten> closedList = new LinkedList<Knoten>();
	/*
	 * TODO Praktikum 4 [1]: Erweitern Sie diese Klasse um weitere Suchstrategien (siehe Aufgabenblatt)
	 * zu unterstützen.
	 */

	public Knoten start(Knoten startKnoten) {
		
		//return Tiefensuche(startKnoten);
		return Breitensuche(startKnoten);
		//return GreedySearch(startKnoten);
	}
	
	
	public Knoten Breitensuche(Knoten node) {
		
		openList.add(node);
		Knoten n;
		while(!openList.isEmpty()) {
			n = openList.poll();
			if(!closedList.contains(n)) {
				closedList.add(n);
				if(isFinished(n))
					return n;
				else
					openList.addAll(n.expand());
			}
		}
		return null;	
	}
	
//	public Knoten Breitensuche(Knoten node) {
//		openList.add(node);
//		Knoten n;
//		
//		while(!openList.isEmpty()) {
//			n = openList.poll();
//			if(isFinished(n)) {
//				System.out.println("Found node: " + n);
//				return n;
//			}
//			
//			openList.addAll(n.expand());
//		}
//		return null;
//	}
	
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
	
	public boolean isFinished(Knoten n) {
		PacmanTileType[][] view = n.getView();
		 String view_row = "";
		 System.out.println("viewsize: " + view.length + "*" + view[0].length);
		 for (int x = 0; x < view[0].length; x++) {
		 for (int y = 0; y < view.length; y++) {
		 view_row += " " + view[y][x];
		 }
		 System.out.println(view_row);
		 view_row = "";
		 }
//		 System.out.println("-------------------------------");
		System.out.println("Betrachte knoten: " + n);
		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[0].length; x++) {
				if(view[y][x]==PacmanTileType.DOT) {	
					System.out.println("###############false#############");
					return false;
				}
			}
		}
		System.out.println("####################true###################");
		return true;
		
	}
}
