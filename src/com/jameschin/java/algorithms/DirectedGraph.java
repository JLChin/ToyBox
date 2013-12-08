package com.jameschin.java.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * DirectedGraph
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class DirectedGraph {
	private HashSet<Node> nodes = new HashSet<Node>();
	
	public class Node {
		String name;
		HashSet<Edge> incoming = new HashSet<Edge>();
		HashSet<Edge> outgoing = new HashSet<Edge>();
		
		Node(String n) {name = n;}
		
		public String getName() {return name;}
		
		public Node addEdge(Node node) {
			Edge e = new Edge(this, node);
			this.outgoing.add(e);
			node.incoming.add(e);
			return this;
		}
	}

	public class Edge {
		Node from;
		Node to;
		
		Edge(Node f, Node t) {
			from = f;
			to = t;
		}
	}
	
	public DirectedGraph addNode(Node node) {
		nodes.add(node);
		return this;
	}
		
	/**
	 * Topological Sort
	 * Kahn 1962 algorithm: choose vertices in the same order as the eventual topological sort.
	 * Space: O(v)
	 * Time: O(v + e) where v is number of nodes, e is the number of edges.
	 * @return topological ordered list containing the nodes in the graph.
	 */
	public ArrayList<String> topSort() {
		// Empty list that will contain the sorted elements
		LinkedList<Node> sortedList = new LinkedList<Node>();
		
		// Set s of all nodes n with no incoming edges
		LinkedList<Node> s = new LinkedList<Node>();
		for (Node node : nodes) {
			if (node.incoming.isEmpty()) {
				s.add(node);
			}
		}
		
		Node n, m;
		Edge e;
		while (! s.isEmpty()) {
			n = s.remove();
			sortedList.add(n);
			
			// for each node m with an edge e from n to m do
			for (Iterator<Edge> i = n.outgoing.iterator(); i.hasNext(); ){
				e = i.next();
				m = e.to;
				
				// remove edge e from graph
				i.remove();
				m.incoming.remove(e);
				
				// if m has no other incoming edges then insert m into s
				if (m.incoming.isEmpty())
					s.add(m);
			}
		}
		
		ArrayList<String> list = new ArrayList<String>();
		for (Node node : nodes) {
			if (! node.outgoing.isEmpty()) {
				System.out.println("Error: DirectedGraph has at least one cycle");
				return list;
			}
		}
		
		for (Node node : sortedList) {
			list.add(node.getName());
		}
		return list;
	}
}