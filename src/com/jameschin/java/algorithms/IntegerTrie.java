package com.jameschin.java.algorithms;

/**
 * IntegerTrie
 * Finds in constant time the first zero or positive integer that does not appear in the collection.
 * Works well for both sparse and dense collections. Self-trimming.
 * Positive/zero integers only.
 * Type: Trie, Sorted, Recursive
 * Space: O(n log n), where n is the number of integers stored.
 * Search: O(1)
 * Insert: O(1)
 * Delete: O(1)
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class IntegerTrie {
	private int maxDivisor; // equal in length to the highest number in the trie
	private final int DEFAULT_DIVISOR = 1000000000;
	private final int BASE = 10;
	private int size = 0;
	private Node root = new Node(null);
	
	IntegerTrie(int[] array) {
		maxDivisor = DEFAULT_DIVISOR;
		
		for (int i : array)
			add(i);
	}
	
	IntegerTrie() {
		maxDivisor = DEFAULT_DIVISOR; 
	}
	
	private class Node {
		boolean full = false;
		Node parent = null;
		Node[] children = new Node[BASE];
		
		Node(Node p) {parent = p;}
	}
	
	/**
	 * Add integer to the trie.
	 * @param newNum integer to be added.
	 * @return true if the integer has not previously been added to the trie, false if it has.
	 */
	public boolean add(int newNum) {
		return add(newNum, root, maxDivisor);
	}
	
	/**
	 * Add array of integers to the trie.
	 * @param array array of integers to be added.
	 */
	public void add(int[] array) {
		for (int i : array)
			add(i);
	}
	
	/**
	 * Add integer to the trie, starting search from node.
	 * @param newNum current suffix of the integer to be added.
	 * @param node root of the tree/sub-tree to search.
	 * @param divisor highest divisor of current integer suffix.
	 * @return true if the integer has not previously been added to the trie, false if it has.
	 */
	private boolean add(int newNum, Node node, int divisor) {
		int currentNum = (newNum / divisor) % BASE; // 0-9
		
		if (node.children[currentNum] == null) // add if doesn't already exist
			node.children[currentNum] = new Node(node);
		
		if (divisor != 1) // recurse next level down
			return add(newNum % divisor, node.children[currentNum], divisor / BASE);
		else { // divisor == 1, at leaf node, check if full and propagate up if necessary
			if (node.children[currentNum].full == false) {
				node.children[currentNum].full = true;
				size++;
				checkFull(node);
				return true;
			}
			return false; // number already exists in Trie
		}
	}
	
	/**
	 * Checks if all suffixes indexed by the current node are full, updates full flag and recurses upwards if so.
	 * @param node root of the tree/sub-tree to check.
	 */
	private void checkFull(Node node) {
		for (Node n : node.children) {
			if (n == null)
				return; // not full, nothing to do
			else if (n.full == false)
				return; // not full, nothing to do
		}
		
		// all children are full, flag and check recursively upwards
		node.full = true;
		if (node != root)
			checkFull(node.parent);
	}
	
	/**
	 * Returns sorted array of all integers added to the trie.
	 * @return sorted array of all integers added to the trie.
	 */
	public int[] toArray() {
		int[] array = new int[size];
		toArray(array, 0, root, maxDivisor, 0);
		return array;
	}
	
	/**
	 * Copy integers to array in order, starting search from node.
	 * @param array array to copy entries into while traversing.
	 * @param index current array index position.
	 * @param node root of the tree/sub-tree to search.
	 * @param divisor highest divisor of current integer suffix.
	 * @param currentNum current number prefix.
	 * @return next array index position.
	 */
	private int toArray(int[] array, int index, Node node, int divisor, int currentNum) {
		if (divisor != 1) {
			for (int i = 0; i < BASE; i++) {
				if (node.children[i] != null)
					index = toArray(array, index, node.children[i], divisor / BASE, currentNum + i * divisor);
			}
		} else { // divisor == 1, at leaf node
			for (int i = 0; i < BASE; i++) {
				if (node.children[i] != null)
					array[index++] = currentNum + i;
			}
		}
		return index;
	}
	
	/**
	 * Returns the first zero or positive integer that does not appear in the trie.
	 * @return the first zero or positive integer that does not appear in the trie.
	 */
	public int nextInt() {
		return nextInt(root, maxDivisor, 0);
	}
	
	/**
	 * Returns the first zero or positive integer that does not appear in the trie.
	 * @param node root of the tree/sub-tree to search.
	 * @param divisor highest divisor of current integer suffix.
	 * @param currentNum current number prefix.
	 * @return the first zero or positive integer that does not appear in the trie.
	 */
	private int nextInt(Node node, int divisor, int currentNum) {
		if (divisor != 1) {
			for (int i = 0; i < BASE; i++) {
				if (node.children[i] == null) { // null means entire suffix is unused
					return currentNum + i * divisor;
				} else if (!node.children[i].full) // full means entire suffix is used
					return nextInt(node.children[i], divisor / BASE, currentNum + i * divisor);
			}
		} else { // divisor == 1, at leaf node with at least one unused slot
			for (int i = 0; i < BASE; i++) {
				if (node.children[i] == null)
					return currentNum + i;
			}
		}
		return 0;
	}
	
	/**
	 * Clear the trie.
	 */
	public void clear() {
		root = new Node(null);
		size = 0;
	}
	
	/**
	 * Returns the number of integers stored in the trie.
	 * @return the number of integers stored in the trie.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns true if the trie is empty.
	 * @return true if the trie is empty, false if not.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}
	
	/**
	 * Returns true if the integer is in the trie.
	 * @param num search integer.
	 * @return true if the integer is in the trie, false if not.
	 */
	public boolean contains(int num) {
		if (contains(num, root, maxDivisor) == null)
			return false;
		return true;
	}
	
	/**
	 * Returns the leaf node of the represented integer in the tree, if it exists, null if not.
	 * @param num current suffix of the search integer.
	 * @param node root of the tree/sub-tree to search.
	 * @param divisor highest divisor of current integer suffix.
	 * @return the leaf node of the represented integer in the tree, if it exists, null if not.
	 */
	private Node contains(int num, Node node, int divisor) {
		int currentNum = (num / divisor) % BASE; // 0-9
		
		if (node.children[currentNum] == null) // suffix empty, number not mapped
			return null;
		
		if (divisor != 1) // recurse next level down
			return contains(num % divisor, node.children[currentNum], divisor / BASE);
		else { // divisor == 1, at leaf node
			if (node.children[currentNum] != null)
				return node.children[currentNum];
			return null;
		}
	}
	
	/**
	 * Remove integer from the trie, if it exists.
	 * @param num integer to be removed.
	 * @return true if the integer was found in the trie and removed, false if not.
	 */
	public boolean remove(int num) {
		Node target = contains(num, root, maxDivisor);
		if (target != null) {
			target.parent.children[num % BASE] = null; // target will be unreachable after this function finishes, GC
			size--;
			
			// revise full status of ancestors
			Node node = target.parent;
			while (node.full == true && node != root) {
				node.full = false;
				node = node.parent;
			}
			
			trimUp(target.parent);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the current node if it contains no children, and checks upwards recursively.
	 * This is only called on non-leaf parents after one of their leaf nodes has been removed.
	 * @param node current node.
	 */
	private void trimUp(Node node) {	
		for (Node n : node.children) {
			if (n != null)
				return; // not empty, do nothing
		}
		
		// all children empty, remove node
		for (int i = 0; i < BASE; i++) {
			if (node.parent.children[i] == node) {
				node.parent.children[i] = null;
				break;
			}
		}
		
		// recursively check upwards
		if (node.parent != root)
			trimUp(node.parent);
	}
}