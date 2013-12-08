package com.jameschin.java.algorithms;

import java.lang.reflect.Array;

/**
 * LinkedList
 * Singly linked list with MergeSort and Reverse.
 * Type: List, Queue, Stack
 * Space: O(n) where n is the number of elements.
 * Search: O(1) from head or tail, O(n) otherwise.
 * Insert: O(1) from head or tail, O(n) otherwise.
 * Delete: O(1) from head or tail, O(n) otherwise.
 * 
 * Sort Type: Comparative, In-Place, Recursive, Stable, Highly Parallelizable
 * Sort Space: O(1)
 * Sort Time: O(n log n)
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class LinkedList<E extends Comparable<E>> {
	private Node head = null;
	private Node end = null;
	private int size = 0;

	private class Node {
		E data;
		Node next = null;
		
		Node(E d) {data = d;}
	}
	
	/**
	 * Appends the specified element to the end of the list.
	 * @param newItem new element to be appended to the list.
	 * @return true.
	 */
	public boolean add(E newItem) {
		Node newNode = new Node(newItem);
		
		if (end == null) // empty list
			head = newNode;
		else
			end.next = newNode;
		end = newNode;
		
		size++;
		return true;
	}
	
	/**
	 * Returns true if the list contains the specified element.
	 * @param item search item.
	 * @return true if the list contains the specified element, false otherwise.
	 */
	public boolean contains(E item) {
		Node cursor = head;
		while (cursor != null) {
			if (cursor.data.compareTo(item) == 0)
				return true;
			cursor = cursor.next;
		}
		return false;
	}
	
	/**
	 * Returns the element at the specified position in the list.
	 * @param index index of the element to return.
	 * @return the element at the specified position in the list.
	 */
	public E get(int index) {
		if (index >= size || index < 0)
			return null;
		
		int count = 0;
		Node cursor = head;
		
		while (count < index) { // shuttle the cursor to the index node
			cursor = cursor.next;
			count++;
		}
		return cursor.data;
	}
	
	/**
	 * Returns the first element in the list.
	 * @return the first element in the list.
	 */
	public E getFirst() {
		return peek();
	}
	
	/**
	 * Returns the last element in the list.
	 * @return the last element in the list.
	 */
	public E getLast() {
		return peekLast();
	}
	
	/**
	 * Returns an array of all items in list order.
	 * @return an array of all items in list order.
	 */
	@SuppressWarnings("unchecked")
	public E[] toArray() {
		if (head == null)
			return null;
		
		E[] array = (E[]) Array.newInstance(head.data.getClass(), size);
		
		Node cursor = head;
		int index = 0;
		while (cursor != null) {
			array[index++] = cursor.data;
			cursor = cursor.next;
		}
		return array;
	}
	
	/**
	 * Returns the first element of the list.
	 * @return the first element of the list.
	 */
	public E peek() {
		if (head == null)
			return null;
		return head.data;
	}
	
	/**
	 * Returns the first element of the list.
	 * @return the first element of the list.
	 */
	public E peekFirst() {
		return peek();
	}
	
	/**
	 * Returns the last element of the list.
	 * @return the last element of the list.
	 */
	public E peekLast() {
		if (end == null)
			return null;
		return end.data;
	}
	
	/**
	 * Returns and removes the first element of the list.
	 * @return the first element of the list.
	 */
	public E poll() {
		if (head == null)
			return null;
		
		E temp = head.data;
		head = head.next;
		
		size--;
		if (size == 0) // removed only node
			end = null;
		
		return temp;
	}
	
	/**
	 * Returns and removes the first element of the list.
	 * @return the first element of the list.
	 */
	public E pollFirst() {
		return poll();
	}
	
	/**
	 * Returns and removes the last element of the list.
	 * @return the last element of the list.
	 */
	public E pollLast() {
		if (end == null)
			return null;
		
		size--;
		E temp = end.data;
		
		if (size == 0) { // removed only node
			head = null;
			end = null;
			return temp;
		}
		
		Node cursor = head;
		while (cursor.next != end){ // shuttle the cursor to the node before the end
			cursor = cursor.next;
		}
		end = cursor;
		end.next = null;
		
		return temp;
	}
	
	/**
	 * Removes the element at the specified position in the list. Shifts any subsequent elements to the left.
	 * @param index index of the element to be removed.
	 * @return the element previously at the specified position.
	 */
	public E remove(int index) {
		if (index >= size || index < 0)
			return null;
		
		size--;
		E temp;
		
		if (index == 0) { // removing head node
			temp = head.data;
			head = head.next;
			if (size == 0) // removed only node
				end = null;
			return temp;
		}
		
		int count = 0;
		Node cursor = head;
		while (count < index - 1) { // shuttle the cursor to the node before the one to be removed
			cursor = cursor.next;
			count++;
		}
		
		temp = cursor.next.data;
		if (cursor.next == end) { // removing end node
			end = cursor;
			end.next = null;
		} else
			cursor.next = cursor.next.next;
		return temp;
	}
	
	/**
	 * Removes the first occurrence of the specified element from the list, if it exists. Shifts any subsequent elements to the left.
	 * @param item element to be removed from the list.
	 * @return true if the element was found and removed, false otherwise.
	 */
	public boolean remove(E item) {
		if (head == null)
			return false;
		
		size--;
		if (head.data.compareTo(item) == 0) { // if head matches item
			head = head.next;
			if (size == 0) { // removed only node
				end = null;
			}
			return true;
		}
		
		Node cursor = head;
		while (cursor.next != null) { // shuttle the cursor to the node before the one to be removed, if it exists
			if (cursor.next.data.compareTo(item) == 0) {
				if (cursor.next == end) { // removing end node
					end = cursor;
					end.next = null;
				} else
					cursor.next = cursor.next.next;
				return true;
			}
			cursor = cursor.next;
		}
		return false;
	}
	
	/**
	 * Returns and removes the first element of the list.
	 * @return the first element of the list.
	 */
	public E remove() {
		return poll();
	}
	
	/**
	 * Returns and removes the first element of the list.
	 * @return the first element of the list.
	 */
	public E removeFirst() {
		return poll();
	}
	
	/**
	 * Returns and removes the last element of the list.
	 * @return the last element of the list.
	 */
	public E removeLast() {
		return pollLast();
	}
	
	/**
	 * Adds the new element to the front of the list.
	 * @param newItem new element to be added.
	 */
	public void push(E newItem) {
		Node newNode = new Node(newItem);
		newNode.next = head;
		head = newNode;
		if (end == null) // if only item
			end = newNode;
		size++;
	}
	
	/**
	 * Returns and removes the first element of the list.
	 * @return the first element of the list.
	 */
	public E pop() {
		return poll();
	}
	
	/**
	 * Returns the number of elements in the list.
	 * @return the number of elements in the list.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns true if the list is empty.
	 * @return true if the list is empty, false if not.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}
	
	/**
	 * Clear the list.
	 */
	public void clear() {
		head = null;
		end = null;
		size = 0;
	}
	
	/**
	 * Replaces the element at the specified position in the list with the specified element.
	 * @param index index of the element to replace.
	 * @param item new element to be stored at the specified position.
	 * @return the element previously at the specified position.
	 */
	public E set(int index, E item) {
		if (index < 0 || index >= size)
			return null;
		
		E temp;
		Node cursor = head;
		int counter = 0;
		while (counter != index) {
			cursor = cursor.next;
			counter++;
		}
		
		temp = cursor.data;
		cursor.data = item;
		return temp;
	}
	
	/**
	 * Inserts the element at the specified position in the list. Shifts any subsequent elements to the right.
	 * @param index index to store the new element.
	 * @param newItem new element to be stored at the specified position.
	 * @return true if the element was successfully inserted, false if not.
	 */
	public boolean insert(int index, E newItem) {
		if (index < 0 || index > size)
			return false;
		
		Node newNode = new Node(newItem);
		size++;
		
		if (index == 0) { // insert node at head
			newNode.next = head;
			head = newNode;
			if (end == null) // if only item
				end = newNode;
			return true;
		}
		
		if (index == size) { // insert node at tail
			end.next = newNode;
			end = newNode;
			return true;
		}
		
		Node cursor = head;
		int counter = 0;
		while (counter < index - 1) { // shuttle the cursor to the node before the index to be inserted
			cursor = cursor.next;
			counter++;
		}
		newNode.next = cursor.next;
		cursor.next = newNode;
		return true;
	}
	
	/**
	 * Reverses the order of the list.
	 */
	public void reverse() {
		head = reverse(head);
	}
	
	/**
	 * Recursive internal function to reverse the order of the list.
	 * @param startNode starting node at which to reverse the remainder of the list.
	 * @return the starting node of the reversed sublist, previously the tail.
	 */
	private Node reverse(Node startNode) {
		if (startNode == null) // empty list
			return null;
		
		if (startNode.next == null) // only list item, becomes head of the new list
			return startNode;
		
		// recursively reverse
		Node nextNode = startNode.next;
		startNode.next = null; // startNode becomes the end node, unlink to avoid cycles
		
		Node newStartNode = reverse(nextNode); // nextNode is now the end node of the sublist, newStartNode points at the head
		nextNode.next = startNode;
		return newStartNode;
	}
	
	/**
	 * MergeSort on the elements in the list.
	 */
	public void sort() {
		head = mergeSort(head, size);
	}
	
	/**
	 * MergeSort
	 * Type: Comparative, In-Place, Recursive, Stable, Highly Parallelizable
	 * Space: O(1)
	 * Time: O(n log n)
	 * @param start starting node of the sublist to sort.
	 * @param length length of the sublist to sort.
	 * @return starting node of the sorted sublist.
	 */
	private Node mergeSort(Node start, int length) {
		if (length <= 1)
			return start;
		
		int leftLength = length / 2;
		int rightLength = length - leftLength;
		int index = 0;
		Node cursor = start;
		
		while (index++ < leftLength)
			cursor = cursor.next;
		
		start = mergeSort(start, leftLength);
		cursor = mergeSort(cursor, rightLength);
		
		return merge(start, leftLength, cursor, rightLength);
	}
	
	/**
	 * MergeSort helper function, merges two sublists in stable order.
	 * @param startLeft starting node of the left sublist to merge.
	 * @param leftLength length of the left sublist to merge.
	 * @param startRight starting node of the right sublist to merge.
	 * @param rightLength length of the right sublist to merge.
	 * @return starting node of the merged lists.
	 */
	private Node merge(Node startLeft, int leftLength, Node startRight, int rightLength) {
		int leftCounter = 0;
		Node leftCursor = startLeft;
		
		int rightCounter = 0;
		Node rightCursor = startRight;
		
		Node startCursor = null;
		Node mergeCursor = null;
		int totalLength = leftLength + rightLength;
		
		for (int i = 0; i < totalLength; i++) {
			if (leftCounter < leftLength && (rightCounter >= rightLength || leftCursor.data.compareTo(rightCursor.data) <= 0)) {
				if (startCursor == null){
					mergeCursor = leftCursor;
					startCursor = mergeCursor;
				}
				else {
					mergeCursor.next = leftCursor;
					mergeCursor = leftCursor;
				}
				leftCursor = leftCursor.next;
				leftCounter++;
			}
			else {
				if (startCursor == null) {
					mergeCursor = rightCursor;
					startCursor = mergeCursor;
				}
				else {
					mergeCursor.next = rightCursor;
					mergeCursor = rightCursor;
				}
				rightCursor = rightCursor.next;
				rightCounter++;
			}
		}
		
		mergeCursor.next = null; // unlink the last node to avoid cycles
		return startCursor;
	}
	
	/**
	 * MergeSort on Comparable array.
	 * Space: O(n)
	 * Time: O(n log n)
	 * @param array Comparable array.
	 * @return Comparable array in sorted order.
	 */
	public static <E extends Comparable<E>> E[] sort(E[] array) {
		if (array == null)
			return null;
		
		LinkedList<E> linkedList = new LinkedList<E>(); 
		for (E e : array) {
			linkedList.add(e);
		}
		linkedList.sort();
		return linkedList.toArray();
	}
}