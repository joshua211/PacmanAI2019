package de.fh.stud.p3;

import java.util.LinkedList;
import java.util.List;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p1.Knoten;

public class Suche {
	
	private LinkedList<Knoten> besucht = new LinkedList<Knoten>();
	/*
	 * TODO Praktikum 3 [1]: Erweitern Sie diese Klasse um die notwendigen
	 * Attribute und Methoden um eine Tiefensuche durchführen zu können.
	 * Die Erweiterung um weitere Suchstrategien folgt in Praktikum 4.
	 */
	
	/*
	 * TODO Praktikum 4 [1]: Erweitern Sie diese Klasse um weitere Suchstrategien (siehe Aufgabenblatt)
	 * zu unterstützen.
	 */
	
	public Knoten start(Knoten startKnoten) {
		
		return Tiefensuche(startKnoten);
	}
	
	public Knoten Tiefensuche(Knoten node) {
		
		if(besucht.contains(node)) 
			return null;
		besucht.add(node);
		
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
	
}
