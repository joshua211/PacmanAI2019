package de.fh.stud.p3;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p1.Knoten;

public class Suche {
	
	public Queue<Knoten> openList = new LinkedList<Knoten>();
	public List<Knoten> closedList = new LinkedList<Knoten>();
	/*
	 * TODO Praktikum 3 [1]: Erweitern Sie diese Klasse um die notwendigen
	 * Attribute und Methoden um eine Tiefensuche durchfÃ¼hren zu kÃ¶nnen.
	 * Die Erweiterung um weitere Suchstrategien folgt in Praktikum 4.
	 */
	
	/*
	 * TODO Praktikum 4 [1]: Erweitern Sie diese Klasse um weitere Suchstrategien (siehe Aufgabenblatt)
	 * zu unterstÃ¼tzen.
	 */
	
	public Knoten start(Knoten startKnoten) {
		
		//return Tiefensuche(startKnoten);
		return Breitensuche(startKnoten);
	}
	
	
	public Knoten Breitensuche(Knoten node) {
		openList.add(node);
		System.out.println(openList);
		System.out.println(closedList);
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
	
	/*
	 * function greedy search (…)
Initialisierung: Queue EXP_KAND = [(Startknoten, Bewertung)]
loop do
if EXP_KAND == leer then return Fehler
Entnehme das führende Tupel (k,b) aus EXP_KAND
// das Wegstück k mit der niedrigsten Bewertung b
if “k führt zum Ziel” then return k
else expandiere k (Kind-Knoten: Elements)
KnotenEinfuegen(EXP_KAND, Elements) =
Füge Elements der Bewertung nach aufsteigend sortiert den Wegstücken
in der Queue EXP_KAND hinzu.
	 * */

	
}
