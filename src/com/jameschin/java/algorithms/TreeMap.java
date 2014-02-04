package com.jameschin.java.algorithms;

import java.lang.reflect.Array;
import java.util.Stack;

/**
 * TreeMap
 * Null values allowed.
 * Type: Sorted, Comparative, Binary Search Tree
 * Space: O(n)
 * Search: O(log n)
 * Insert: O(log n)
 * Delete: O(log n) where n is the number of elements.
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class TreeMap<K extends Comparable<K>, V> {
	private Node root = null;
	private int size = 0;
	
	private class Node {
		K mKey;
		V mValue;
		
		Node left = null;
		Node right = null;
		
		Node (K newKey, V newValue) {
			mKey = newKey;
			mValue = newValue;
		}
	}
	
	/**
	 * Associates the specified value with the specified key in this map.
	 * If the map previously contained a mapping for the key, the old value is replaced.
	 * @param newKey key of new item to be added.
	 * @param newValue new item to be added.
	 * @return the previous value associated with the key, or null if there was no mapping for the key.
	 */
	public V put (K newKey, V newValue) {
		if (root == null) {
			root = new Node(newKey, newValue);
			size++;
			return null;
		}
		
		Node node = root;
		
		while (true) {
			if (newKey.compareTo(node.mKey) < 0) {
				if (node.left == null) {
					node.left = new Node(newKey, newValue);
					size++;
					return null;
				} else
					node = node.left;
			} else if (newKey.compareTo(node.mKey) > 0) {
				if (node.right == null) {
					node.right = new Node(newKey, newValue);
					size++;
					return null;
				} else
					node = node.right;
			} else { // (newKey.compareTo(node.mKey) == 0)
				V oldValue = node.mValue;
				node.mValue = newValue;
				return oldValue;
			}
		}
	}
	
	/**
	 * Returns an array of all values ordered by key.
	 * @return an array of all values ordered by key.
	 */
	@SuppressWarnings("unchecked")
	public V[] toArray() {
		if (root == null) 
			return null;

		V[] array = (V[]) Array.newInstance(root.mValue.getClass(), size);
		Stack<Node> stack = new Stack<Node>();
		Node node = root;
		int index = 0;
		
		while (true) {
			if (node != null) {
				stack.push(node);
				node = node.left;
			} else {
				node = stack.pop();
				array[index++] = node.mValue;
				if (index == size)
					break;
				node = node.right;
			}
		}
		
		return array;
	}
	
	/**
	 * Returns true if there is a mapping for the specified key.
	 * @param key search key.
	 * @return true if there is a mapping for the specified key, false if not.
	 */
	public boolean containsKey(K key) {
		if (root == null)
			return false;
		
		Node node = root;
		
		while (true) {
			if (key.compareTo(node.mKey) < 0) {
				if (node.left == null)
					return false;
				else
					node = node.left;
			} else if (key.compareTo(node.mKey) > 0) {
				if (node.right == null)
					return false;
				else
					node = node.right;
			} else
				return true; // (key.compareTo(node.mKey) == 0)
		}
	}
	
	/**
	 * Returns the value to which the specified key is mapped.
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or null if there exists no mapping for the key.
	 */
	public V get(K key) {
		if (root == null)
			return null;
		
		Node node = root;
		
		while (true) {
			if (key.compareTo(node.mKey) < 0) {
				if (node.left == null)
					return null;
				else
					node = node.left;
			} else if (key.compareTo(node.mKey) > 0) {
				if (node.right == null)
					return null;
				else
					node = node.right;
			} else
				return node.mValue; // (key.compareTo(node.mKey) == 0)
		}
	}
	
	/**
	 * Clear the map.
	 */
	public void clear() {
		root = null;
		size = 0;
	}
	
	/**
	 * Returns the number of key-value mappings in this map.
	 * @return the number of key-value mappings in this map.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns true if the map is empty.
	 * @return true if the map is empty, false if not.
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * Returns the first key in the map.
	 * @return the first key in the map, or null if the map is empty.
	 */
	public K firstKey() {
		if (root == null)
			return null;
		
		Node node = root;
		
		while (node.left != null)
			node = node.left;
		
		return node.mKey;
	}
	
	/**
	 * Returns the last key in the map.
	 * @return the last key in the map, or null if the map is empty.
	 */
	public K lastKey() {
		if (root == null)
			return null;
		
		Node node = root;
		
		while (node.right != null)
			node = node.right;
		
		return node.mKey;
	}
	
	/**
	 * Removes the mapping for the min key and returns the value which was mapped to it.
	 * @return the value which was mapped to the min key in the map, or null if the map is empty.
	 */
	public V removeMin() {
		if (root == null)
			return null;
		
		Node prev = null;
		Node curr = root;
		size--;
		
		while (curr.left != null) { // shuttle curr to the leftmost node
			prev = curr;
			curr = curr.left;
		}
		
		if (prev == null)
			root = root.right; // if root was the leftmost node
		else
			prev.left = curr.right;
		
		return curr.mValue;
	}
	
	/**
	 * Removes the mapping for the max key and returns the value which was mapped to it.
	 * @return the value which was mapped to the max key in the map, or null if the map is empty.
	 */
	public V removeMax() {
		if (root == null)
			return null;
		
		Node prev = null;
		Node curr = root;
		size--;
		
		while (curr.right != null) { // shuttle curr to the rightmost node
			prev = curr;
			curr = curr.right;
		}
		
		if (prev == null)
			root = root.left; // if root was the rightmost node
		else
			prev.right = curr.left;
		
		return curr.mValue;
	}
	
	/**
	 * Remove the mapping associated with the key, if it exists.
	 * @param key key for which the mapping should be removed.
	 * @return true if the mapping was found in the map and removed, false if not.
	 */
	public boolean remove(K key) {
		if (root == null)
			return false;
		
		Node prevNode = null;
		Node node = root;
		
		while (true) {
			if (key.compareTo(node.mKey) < 0) {
				if (node.left == null)
					return false;
				else {
					prevNode = node;
					node = node.left;
				}
			} else if (key.compareTo(node.mKey) > 0) {
				if (node.right == null)
					return false;
				else {
					prevNode = node;
					node = node.right;
				}
			} else { // node.data matches item, now remove node
				if (node.left != null && node.right != null) { // TWO CHILDREN
					Node prev = null;
					Node curr = node.left;
					
					while (curr.right != null) { // shuttle curr to the rightmost node in the left subtree
						prev = curr;
						curr = curr.right;
					}
					
					node.mKey = curr.mKey; // substitute data from next largest
					node.mValue = curr.mValue;
					
					// remove rightmost node in the left subtree
					if (prev == null)
						node.left = node.left.left; // original left node was max of subtree
					else
						prev.right = curr.left;
					
				} else {
					if (node.left != null) { // ONLY LEFT CHILD
						if (node == root)
							root = root.left;
						else {
							if (prevNode.left != null && prevNode.left.mKey.compareTo(key) == 0)
								prevNode.left = node.left;
							else
								prevNode.right = node.left;
						}
					} else { // ONLY RIGHT OR NO CHILD
						if (node == root)
							root = root.right;
						else {
							if (prevNode.left != null && prevNode.left.mKey.compareTo(key) == 0)
								prevNode.left = node.right;
							else
								prevNode.right = node.right;
						}
					}
				}
				
				size--;
				return true;
			}
		}
	}
}