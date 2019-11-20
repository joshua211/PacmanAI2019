package de.fh.stud.p3;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import de.fh.stud.p1.Knoten;
import sun.util.resources.OpenListResourceBundle;

public class Suche {

	private Queue<Knoten> openList;
	private List<Knoten> closedList;

	public Knoten start(Knoten startKnoten) {
		Knoten erg;
		closedList = new LinkedList<Knoten>();

		//erg = tiefensuche(startKnoten);
		//erg = breitensuche(startKnoten);
		//erg = greedySearch(startKnoten);
		//erg= ucs(startKnoten);
		erg= aStar(startKnoten);
		return erg;
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
	
	public Knoten ucs(Knoten node) {
		openList = new PriorityQueue<Knoten>();
		openList.add(node);
		Knoten erg = node;
		while(!openList.isEmpty() && openList.peek().getHeuristik() <= erg.getHeuristik()) {
			System.out.println("while");
			Knoten parent = openList.poll();
			for(Knoten c: parent.expand()) {
				if(!closedList.contains(c) || c.getHeuristik() < erg.getHeuristik()) {
					closedList.add(c);
					openList.add(c);
					if(c.isFinished() && c.getHeuristik()<erg.getHeuristik()) {
						erg = c;
					}
				}
			}
		}
		return erg;
	}
	/*
	 * 
	 * function UNIFORM-COST-SEARCH(problem) returns a solution, or failure
 * if problem's initial state is a goal then return empty path to initial state
 * frontier ← a priority queue ordered by pathCost, with a node for the initial state
* reached ← a table of {state: the best path that reached state}; initially empty
 * solution ← failure
 while frontier is not empty and top(frontier) is cheaper than solution do
   parent ← pop(frontier)
   for child in successors(parent) do
     s ← child.state
     if s is not in reached or child is a cheaper path than reached[s] then
       reached[s] ← child
       add child to the frontier
       if child is a goal and is cheaper than solution then
         solution = child
 return solution
	 * 
	 * */
	public Knoten aStar(Knoten node) {
		openList = new LinkedList<Knoten>();
		openList.add(node);
		while(!openList.isEmpty()) {
			Knoten current = openList.peek();
			for(Knoten l: openList) {
				if(l.f<current.f)
					current=l;
			}
			openList.remove(current);
			closedList.add(current);
			if(current.isFinished()) {
				return current;
			}
			for(Knoten c: current.expand()) {
				if(!closedList.contains(c)) {
					c.g = current.g + 1;
					c.h = c.getHeuristik();
					c.f = c.g + c.h;
					if(openList.contains(c)) {
						if(c.g < openList.peek().g) {
							openList.add(c);
						}
					}
				}
			}
		}
		return null;
	}
	/*
	 * // A* (star) Pathfinding
// Initialize both open and closed list
let the openList equal empty list of nodes
let the closedList equal empty list of nodes
// Add the start node
put the startNode on the openList (leave it's f at zero)
// Loop until you find the end
while the openList is not empty
    // Get the current node
    let the currentNode equal the node with the least f value
    remove the currentNode from the openList
    add the currentNode to the closedList
    // Found the goal
    if currentNode is the goal
        Congratz! You've found the end! Backtrack to get path
    // Generate children
    let the children of the currentNode equal the adjacent nodes
    
    for each child in the children
        // Child is on the closedList
        if child is in the closedList
            continue to beginning of for loop
        // Create the f, g, and h values
        child.g = currentNode.g + distance between child and current
        child.h = distance from child to end
        child.f = child.g + child.h
        // Child is already in openList
        if child.position is in the openList's nodes positions
            if the child.g is higher than the openList node's g
                continue to beginning of for loop
        // Add the child to the openList
        add the child to the openList
	 * */
	
}
