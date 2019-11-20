package de.fh.stud.p3;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import de.fh.stud.p1.Knoten;
import sun.util.resources.OpenListResourceBundle;

public class Suche {
	private Queue<Knoten> openList;
	public List<Knoten> closedList;

	public Knoten start(Knoten startKnoten) {
		Knoten min, temp;
		int size;
		min = tiefensuche(startKnoten);
		size = closedList.size();
		temp = breitensuche(startKnoten);
		temp = greedySearch(startKnoten);
		if(closedList.size() < size)
			min = temp;
		temp = ucs(startKnoten);
		if(closedList.size() < size)
			min = temp;
		temp = aStar(startKnoten);
		if(closedList.size() < size)
			min = temp;
		return min;
	}

	public Knoten breitensuche(Knoten node) {
		openList = new LinkedList<Knoten>();
		closedList = new LinkedList<Knoten>();
		openList.add(node);
		Knoten n;
		while (!openList.isEmpty()) {
			if(closedList.size() > 3000)
				return null;
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
			if(closedList.size() > 3000)
				return null;
			Knoten temp = openStack.pop();
			closedList.add(temp);
			for(Knoten c: temp.expand()) {
				if(!openStack.contains(c) && !closedList.contains(c)) {
					openStack.push(c);
				}
				if(c.isFinished()) {
					return c;
				}
			}
		}
		return null;
	}

	public Knoten greedySearch(Knoten node) {
//		// openList = new PriorityQueue<Knoten>((n, k) -> k.compareTo(n));
//		// openList.addAll(node.expand());
//		NodeQueue queue = new NodeQueue();
//		queue.addAll(node.expand());
//
//		while (!queue.isEmpty()) {
//			node = queue.poll();
//			if (!closedList.contains(node)) {
//				if (node.isFinished())
//					return node;
//				queue.addAll(node.expand());
//				closedList.add(node);
//			}
//		}
//		System.out.println("null");
//		return null;
		openList = new LinkedList<Knoten>();
		closedList = new LinkedList<Knoten>();
		openList.add(node);
		while(!openList.isEmpty()) {
			if(closedList.size() > 3000)
				return null;
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
		openList = new PriorityQueue<Knoten>();
		closedList = new LinkedList<Knoten>();
		openList.add(node);
		Knoten erg = node;
		while(!openList.isEmpty() && openList.peek().getHeuristik() <= erg.getHeuristik()) {
			if(closedList.size() > 3000)
				return null;
			Knoten parent = openList.poll();
			for(Knoten c: parent.expand()) {
				if(!closedList.contains(c) && c.getCost() < erg.getCost()) {
					closedList.add(c);
					openList.add(c);
					if(c.isFinished() && c.getCost()<erg.getCost()) {
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
		if(closedList.size() > 3000)
			return null;
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
