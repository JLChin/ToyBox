package com.jameschin.java.algorithms;

import java.lang.reflect.Array;

/**
 * HashMap
 * Type: Unsorted Linked List Bucket, Automatic Geometric Resizing
 * Space: O(n)
 * Best: O(1)
 * Worst: O(n) where n is the number of elements.
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class HashMap<K, V> {
	private Node[] mTable;
	private Node[] resizedTable; // temp area to build resized table 
	private int tableSize = 16; // number of table buckets
	private int size = 0; // number of mappings contained
	private final double loadFactor = 0.75; // size/tableSize ratio threshold before resizing
	private final int resizeFactor = 2; // table resize multiplier
	
	@SuppressWarnings("unchecked")
	HashMap() {
		mTable = (Node[]) Array.newInstance(Node.class, tableSize);
	}
	
	private class Node {
		K mKey;
		V mValue;
		Node next = null;
		
		Node(K key, V value) {
			mKey = key;
			mValue = value;
		}
	}
	
	/**
	 * Internal function to hash the key value into a table index value.
	 * @param key key of new item to be added.
	 * @return hashed value to be used as the table index.
	 */
	private int hashFunction(K key) {
		int sum = 0;
		String keyString = key.toString();
		for (int i = 0; i < keyString.length(); i++) {
			sum += (int) keyString.charAt(i);
		}
		return sum % tableSize;
	}
	
	/**
	 * Associates the specified value with the specified key in this map.
	 * If the map previously contained a mapping for the key, the old value is replaced.
	 * @param newKey key of new item to be added.
	 * @param newValue new item to be added.
	 * @return the previous value associated with key, or null if there was no mapping for the key.
	 */
	public V put(K newKey, V newValue) {
		if (newKey == null)
			return null;
		
		int index = hashFunction(newKey);
		
		if (mTable[index] != null) { // table bucket contains at least one entry already
			Node currentNode = mTable[index];
			
			// check if key is already mapped
			while (true) {
				if (currentNode.mKey == newKey) {
					V temp = currentNode.mValue;
					currentNode.mValue = newValue;
					return temp;
				} else if (currentNode.next == null)
					break;
				else
					currentNode = currentNode.next;
			}
			
			// key is not already mapped, currentNode.next == null
			currentNode.next = new Node(newKey, newValue);
			size++;
			if ((double) size/tableSize > loadFactor)
				resize();
			return null;
			
		} else { // table bucket is empty
			mTable[index] = new Node(newKey, newValue);
			size++;
			if ((double) size/tableSize > loadFactor)
				resize();
			return null;
		}
	}
	
	/**
	 * Returns the value to which the specified key is mapped.
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or null if there exists no mapping for the key.
	 */
	public V get(K key) {
		int index = hashFunction(key);
		
		if (mTable[index] != null) { // table bucket contains at least one entry
			Node currentNode = mTable[index];
			
			while (currentNode != null) {
				if (currentNode.mKey == key) 
					return currentNode.mValue;
				else
					currentNode = currentNode.next;
			}
		}
		return null; // key not found
	}
	
	/**
	 * Clear the map.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		tableSize = 16;
		size = 0;
		mTable = (Node[]) Array.newInstance(Node.class, tableSize);
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
		return (size == 0);
	}
	
	/**
	 * Internal function to resize the table when the load factor is exceeded.
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		tableSize = tableSize * resizeFactor;
		resizedTable = (Node[]) Array.newInstance(Node.class, tableSize);
		Node currentNode;
		
		for (int i = 0; i < tableSize / resizeFactor; i++) { // old tableSize
			currentNode = mTable[i];
			
			// transfer all mappings into new resized table
			while (currentNode != null) { 
				resizePut(currentNode.mKey, currentNode.mValue); // note hashing with new tableSize value
				currentNode = currentNode.next;
			}
		}
		
		// new resized table complete
		mTable = resizedTable;
	}
	
	/**
	 * Internal put function to transfer mappings to resizedTable, assumes there will be no pre-existing mapping for the new key.
	 * @param newKey key of new item to be added.
	 * @param newValue new item to be added.
	 */
	private void resizePut(K newKey, V newValue) {		
		int index = hashFunction(newKey);
		
		if (resizedTable[index] != null) { // table bucket contains at least one entry already
			Node currentNode = resizedTable[index];
			
			// advance to end of linked list
			while (currentNode.next != null) {
				currentNode = currentNode.next;
			}
			
			currentNode.next = new Node(newKey, newValue);
			return;
		} else { // table bucket is empty
			resizedTable[index] = new Node(newKey, newValue);
			return;
		}
	}
	
	/**
	 * Remove the mapping associated with the key, if it exists.
	 * @param key key for which the mapping should be removed.
	 * @return the previous value associated with key, or null if there was no mapping for the key.
	 */
	public V remove(K key) {
		if (key == null)
			return null;
		
		int index = hashFunction(key);
		
		if (mTable[index] != null) { // table bucket contains at least one entry
			Node currentNode = mTable[index];
			V temp;
			
			if (currentNode.mKey == key) { // if first node matches key
				temp = currentNode.mValue;
				
				if (currentNode.next != null) { // if there is another node following the one to be removed
					mTable[index] = currentNode.next;
				} else
					mTable[index] = null;
				
				return temp;
			}
			
			while (true) { // first node does not match, check rest of list
				if (currentNode.next == null) {
					return null;
				} else if (currentNode.next.mKey == key) {
					temp = currentNode.next.mValue;
					
					if (currentNode.next.next != null) { // if there is another node following the one to be removed
						currentNode.next = currentNode.next.next;
					} else
						currentNode.next = null;
					return temp;
				} else
					currentNode = currentNode.next;
			}
		} else // table bucket is empty
			return null;
	}
}