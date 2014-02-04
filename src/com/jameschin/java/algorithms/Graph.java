package com.jameschin.java.algorithms;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Graph
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class Graph {
	private HashSet<Node> nodes = new HashSet<Node>();
	
	public class Node {
		String name;
		HashSet<Node> connectedTo = new HashSet<Node>();
		
		Node(String n) {name = n;}
		
		public String getName() {return name;}
		
		public Node addEdge(Node n) {
			connectedTo.add(n);
			n.connectedTo.add(this);
			return this;
		}
	}
	
	public Graph addNode(Node n) {
		nodes.add(n);
		return this;
	}
	
	public void printGraph() {
		for (Node n : nodes) {
			System.out.print("Node " + n.getName() + ", connected to:");
			for (Node m: n.connectedTo)
				System.out.print(" " + m.getName());
			System.out.println();
		}
	}
	
	/**
	 * Returns a deep copy clone of the graph.
	 * Optimized for sparsely connected graphs where the queue is expected to be very short.
	 * Type: Breadth-first search, Non-recursive
	 * Space: O(v + e)
	 * Time: O(v + e) where v is number of nodes, e is the number of edges.
	 * @return a deep copy clone of the graph.
	 */
	public Graph cloneGraph() {
		Graph graphCopy = new Graph();
		
		HashSet<Node> processed = new HashSet<Node>();
		LinkedList<Node> queue = new LinkedList<Node>();
		LinkedList<Node> queueCopy = new LinkedList<Node>();
		Node currNode, currNodeCopy;
		int index;
		
		for (Node n : nodes) {
			if (!processed.contains(n)) {
				queue.add(n);
				queueCopy.add(new Node(n.getName()));
				
				// breadth first search
				while (!queue.isEmpty()) {
					currNode = queue.remove();
					currNodeCopy = queueCopy.remove();
					
					processed.add(currNode);
					graphCopy.addNode(currNodeCopy);
					
					// build new node
					for (Node m : currNode.connectedTo) {
						if (!processed.contains(m)) {
							index = queue.indexOf(m);
							if (index == -1) { // if neighbor is not already in the queue (no cycle)
								queue.add(m);
								Node node = new Node(m.getName());
								queueCopy.add(node);
								currNodeCopy.addEdge(node);
							} else
								currNodeCopy.addEdge(queueCopy.get(index)); // node already created but not finished
						}
					}
				}
			}
		}
		
		return graphCopy;
	}
}