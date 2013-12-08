package com.jameschin.java.algorithms;

import java.util.Map;
import java.util.TreeMap;

/**
 * Trie
 * Type: String Trie, Sorted
 * Space: Less than O(n), where n is the number of strings stored.
 * Search: O(m)
 * Insert: O(m) where m is the length of the string.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class Trie {
	private Node root = new Node();
	private int size = 0; // number of strings stored in the trie
	
	private class Node {
		int count = 0;
		TreeMap<Character, Node> children = new TreeMap<Character, Node>();
	}
	
	/**
	 * Add the specified string to the trie.
	 * @param word string to be added to the trie.
	 * @return true if the string has not previously been added to the trie, false if it has.
	 */
	public boolean add(String word) {
		return add(word, root);
	}
	
	/**
	 * Add the specified string to the trie, starting search from node.
	 * @param word current suffix of the word to be added.
	 * @param node root of the tree/sub-tree to search.
	 * @return true if the string has not previously been added to the trie, false if it has.
	 */
	private boolean add(String word, Node node) {
		if (word.length() > 0) { // still more to traverse
			char nextChar = word.charAt(0);
			if (! node.children.containsKey(nextChar))
				node.children.put(nextChar, new Node());
			return add(word.substring(1), node.children.get(nextChar));
		} else { // arrived at correct node
			node.count++;
			if (node.count == 1) {
				size++;
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Remove the specified string from the trie, if it exists.
	 * @param word string to be removed from the trie.
	 * @return true if the string was found in the trie and removed, false if not.
	 */
	public boolean remove(String word) {
		return remove(word, root);
	}
	
	/**
	 * Remove the specified string from the trie, if it exists, starting search from node.
	 * @param word current suffix of the word to be removed.
	 * @param node root of the tree/sub-tree to search.
	 * @return true if the string was found in the trie and removed, false if not.
	 */
	private boolean remove(String word, Node node) {
		boolean flag;
		char nextChar;
		Node child;
		
		if (word.length() > 0) { // still more to traverse
			nextChar = word.charAt(0);
			if (! node.children.containsKey(nextChar))
				return false; // word does not exist in the trie
			child = node.children.get(nextChar);
			flag = remove(word.substring(1), child);
		} else { // arrived at correct node
			if (node.count > 0) {
				node.count = 0;
				size--;
				return true;
			}
			return false;
		}
		
		// remove empty branches from the trie
		if (flag == true) {
			if (child.children.isEmpty() && child.count == 0) // if child does not store a word and has no grandchildren then remove it
				node.children.remove(nextChar);
		}
		return flag;
	}

	/**
	 * Returns number of times word has been added.
	 * @param word search string.
	 * @return number of times word has been added, 0 if not found.
	 */
	public int contains(String word) {
		return contains(word, root);
	}
	
	/**
	 * Returns number of times word has been added, starting search from node.
	 * @param word current suffix of the search string.
	 * @param node root of the tree/sub-tree to search.
	 * @return number of times word has been added, 0 if not found.
	 */
	private int contains(String word, Node node) {
		if (word.length() > 0) {
			char nextChar = word.charAt(0);
			if (! node.children.containsKey(nextChar))
				return 0;
			else
				return contains(word.substring(1), node.children.get(nextChar));
		} else // arrived at correct node
			return node.count;
	}
	
	/**
	 * Returns array of all terms in lexicographic order.
	 * @return array of all terms in lexicographic order.
	 */
	public String[] toArray() {
		String[] array = new String[size];
		int index = 0;
		String currentWord = "";
		if (root.children.isEmpty())
			return array;
		else
			toArrayPreOrder(array, index, currentWord, root);
		return array;
	}
	
	/**
	 * Copy strings to array in order, starting search from node.
	 * @param array array to copy entries into while traversing.
	 * @param index current array index position.
	 * @param currentWord current word prefix.
	 * @param node root of the tree/sub-tree to search.
	 * @return next array index position.
	 */
	private int toArrayPreOrder(String[] array, int index, String currentWord, Node node) {
		if (node.count > 0) {
			array[index++] = currentWord;
		}
		for (Map.Entry<Character, Node> entry : node.children.entrySet()) { // iterate through children
			String newWord = currentWord; // copy
			newWord += entry.getKey(); // add next letter
			index = toArrayPreOrder(array, index, newWord, entry.getValue()); // recursion
		}
		return index;
	}
	
	/**
	 * Clear the trie;
	 */
	public void clear() {
		root = new Node();
		size = 0;
	}
	
	/**
	 * Returns the number of words stored in the trie.
	 * @return the number of words stored in the trie.
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
}