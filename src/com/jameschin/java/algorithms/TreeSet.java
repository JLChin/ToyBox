package com.jameschin.java.algorithms;

import java.lang.reflect.Array;
import java.util.Stack;

/**
 * TreeSet
 * Type: Sorted, Comparative, Binary Search Tree
 * Space: O(n)
 * Search: O(log n)
 * Insert: O(log n)
 * Delete: O(log n) where n is the number of elements.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class TreeSet<E extends Comparable<E>> {
	private Node root = null;
	private int size = 0;
	
	private class Node {
		E data;
		
		Node left = null;
		Node right = null;
		
		Node (E newItem) {
			data = newItem;
		}
	}
	
	/**
	 * Add new item.
	 * @param newItem new item to be added.
	 * @return true if successfully added, false if item already exists in set.
	 */
	public boolean add (E newItem) {
		if (root == null) {
			root = new Node(newItem);
			size++;
			return true;
		}
		
		Node node = root;
		
		while (true) {
			if (newItem.compareTo(node.data) < 0) {
				if (node.left == null) {
					node.left = new Node(newItem);
					size++;
					return true;
				} else
					node = node.left;
			} else if (newItem.compareTo(node.data) > 0) {
				if (node.right == null) {
					node.right = new Node(newItem);
					size++;
					return true;
				} else
					node = node.right;
			} else
				return false; // (newKey.compareTo(node.mKey) == 0)
		}
	}
	
	/**
	 * Returns an array of all items in order.
	 * @return an array of all items in order.
	 */
	@SuppressWarnings("unchecked")
	public E[] toArray() {
		if (root == null) 
			return null;
		
		E[] array = (E[]) Array.newInstance(root.data.getClass(), size);
		Stack<Node> stack = new Stack<Node>();
		Node node = root;
		int index = 0;
		
		while (true) {
			if (node != null) {
				stack.push(node);
				node = node.left;
			} else {
				node = stack.pop();
				array[index++] = node.data;
				if (index == size)
					break;
				node = node.right;
			}
		}
		
		return array;
	}
	
	/**
	 * Returns true if item is in the set.
	 * @param item search item.
	 * @return true if item is in the set, false if not.
	 */
	public boolean contains(E item) {
		if (root == null)
			return false;
		
		Node node = root;
		
		while (true) {
			if (item.compareTo(node.data) < 0) {
				if (node.left == null)
					return false;
				else
					node = node.left;
			} else if (item.compareTo(node.data) > 0) {
				if (node.right == null)
					return false;
				else
					node = node.right;
			} else
				return true; // (key.compareTo(node.mKey) == 0)
		}
	}
	
	/**
	 * Clear the set.
	 */
	public void clear() {
		root = null;
		size = 0;
	}
	
	/**
	 * Returns the size of the set.
	 * @return size of the set.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns true if the set is empty.
	 * @return true if the set is empty, false if not.
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * Get the element at index i, starting from zero.
	 * @param i element to retrieve.
	 * @return element E at index i.
	 */
	public E get(int i) {
		if (i >= size || i < 0)
			return null;
		
		Stack<Node> stack = new Stack<Node>();
		int counter = 0;
		Node node = root;
		
		while (true) {
			if (node != null) {
				stack.push(node);
				node = node.left;
			} else {
				node = stack.pop();
				if (counter++ == i)
					return node.data;
				node = node.right;
			}
		}
	}
	
	/**
	 * Returns the first element in the set.
	 * @return the first element in the set.
	 */
	public E first() {
		if (root == null)
			return null;
		
		Node node = root;
		
		while (node.left != null)
			node = node.left;
		
		return node.data;
	}
	
	/**
	 * Returns the last element in the set.
	 * @return the last element in the set.
	 */
	public E last() {
		if (root == null)
			return null;
		
		Node node = root;
		
		while (node.right != null)
			node = node.right;
		
		return node.data;
	}
	
	/**
	 * Removes and returns the min data of the set.
	 * @return the min data which was in the set, or null if the set is empty.
	 */
	public E removeMin() {
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
		
		return curr.data;
	}
	
	/**
	 * Removes and returns the max data of the set.
	 * @return the max data which was in the set, or null if the set is empty.
	 */
	public E removeMax() {
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
		
		return curr.data;
	}
	
	/**
	 * Remove item from set, if it exists.
	 * @param item element to remove from set.
	 * @return true if the item was found in the set and removed, false if not.
	 */
	public boolean remove(E item) {
		if (root == null)
			return false;
		
		Node prevNode = null;
		Node node = root;
		
		while (true) {
			if (item.compareTo(node.data) < 0) {
				if (node.left == null)
					return false;
				else {
					prevNode = node;
					node = node.left;
				}
			} else if (item.compareTo(node.data) > 0) {
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
					
					node.data = curr.data; // substitute data from next largest
					
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
							if (prevNode.left != null && prevNode.left.data.compareTo(item) == 0)
								prevNode.left = node.left;
							else
								prevNode.right = node.left;
						}
					} else { // ONLY RIGHT OR NO CHILD
						if (node == root)
							root = root.right;
						else {
							if (prevNode.left != null && prevNode.left.data.compareTo(item) == 0)
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