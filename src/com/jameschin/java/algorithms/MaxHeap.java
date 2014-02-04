package com.jameschin.java.algorithms;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Binary MaxHeap
 * Type: Comparative, Array Based, In-Place
 * Space: O(n)
 * Find Max: O(1)
 * Insert: O(log n)
 * Delete: O(log n) where n is the number of elements.
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class MaxHeap <E extends Comparable<E>>{
	private int currentIndex = 0; // array index of next empty slot
	private ArrayList<E> heap = new ArrayList<E>();
	
	/**
	 * Add new element to heap.
	 * @param newItem new element to be added to the heap.
	 * @return this heap object.
	 */
	public MaxHeap<E> add(E newItem) {
		heap.add(newItem); // add as last element on the last level
		heapifyUp(currentIndex);
		currentIndex++;
		return this;
	}
	
	/**
	 * Recursively heapify upwards, starting at index node.
	 * @param index index of current node to start heapifying.
	 */
	private void heapifyUp(int index) {
		int parentIndex = (index - 1) / 2;
		
		if (index == 0)
			return;
		else if (heap.get(index).compareTo(heap.get(parentIndex)) > 0) { // if parent is smaller, swap with parent
			E temp = heap.get(parentIndex);
			heap.set(parentIndex, heap.get(index));
			heap.set(index, temp);
			
			heapifyUp(parentIndex); // check next level up
		}
	}
	
	/**
	 * Returns the object at the top of the MaxHeap.
	 * @return the object at the top of the MaxHeap.
	 */
	public E findMax() {
		return heap.get(0);
	}
	
	/**
	 * Clear the heap.
	 */
	public void clear() {
		heap.clear();
		currentIndex = 0;
	}
	
	/**
	 * Returns the size of the heap.
	 * @return the size of the heap.
	 */
	public int size() {
		return currentIndex;
	}
	
	/**
	 * Returns true if the heap is empty.
	 * @return true if the heap is empty, false if not.
	 */
	public boolean isEmpty() {
		if (currentIndex == 0)
			return true;
		return false;
	}
	
	/**
	 * Returns an array representing the heap.
	 * @param e the array into which the elements of the heap are to be stored, if it is big enough, otherwise a new array will be created.
	 * @return an array representing the heap, index 0 contains the max item, index 1-2 contain the next level of the tree, etc.
	 */
	@SuppressWarnings("unchecked")
	public E[] toArray(E[] e) {
		if (e.length < heap.size()) {
			e = (E[]) Array.newInstance(e.getClass().getComponentType(), heap.size());
		}
		return heap.toArray(e);
	}
	
	/**
	 * Returns an array representing the heap, or null array if the heap is empty.
	 * @return an array representing the heap, index 0 contains the max item, index 1-2 contain the next level of the tree, etc.
	 */
	@SuppressWarnings("unchecked")
	public E[] toArray() {
		E[] array = null;
		if (!heap.isEmpty()) {
			array = (E[]) Array.newInstance(heap.get(0).getClass(), heap.size());
			int i = 0;
			for (E e : heap) {
				array[i++] = e;
			}
		}
		return array;
	}
	
	/**
	 * Removes the max item from the heap and re-heapifies.
	 * @return the max item that was removed, if the tree was non-empty, null otherwise.
	 */
	public E deleteMax() {
		E max = null;
		if (currentIndex > 0) { // replace the root of heap with the last element on the last level
			max = heap.get(0); // save current max
			
			heap.set(0, heap.get(currentIndex - 1));
			heap.remove(currentIndex - 1);
			currentIndex--;
			
			heapifyDown(0); // re-heapify
		}
		return max; // if tree is empty, returns null
	}
	
	/**
	 * Recursively heapify downwards, starting at index node.
	 * @param index index of current node to start heapifying.
	 */
	private void heapifyDown(int index) {
		int leftChildIndex = index * 2 + 1;
		int rightChildIndex = index * 2 + 2;
		int swapIndex = index;
		
		// if left child exists and greater than current node
		if (leftChildIndex < currentIndex) {
			if (heap.get(leftChildIndex).compareTo(heap.get(swapIndex)) > 0)
				swapIndex = leftChildIndex;

			// if right child exists and greater than current node and left child
			if (rightChildIndex < currentIndex) {
				if (heap.get(rightChildIndex).compareTo(heap.get(swapIndex)) > 0)
					swapIndex = rightChildIndex;
			}
		}
		
		if (swapIndex != index) { // swap if necessary
			E temp = heap.get(index);
			heap.set(index, heap.get(swapIndex));
			heap.set(swapIndex, temp);

			heapifyDown(swapIndex); // check next level down
		}
	}
	
	/**
	 * Removes a single instance of the specified element from the heap, if it is present.
	 * @param item element to be removed from this heap, if present.
	 * @return true if element was found and removed, false if not.
	 */
	public boolean remove(E item) {
		for (int i = 0; i < heap.size(); i++) {
			if (item.compareTo(heap.get(i)) == 0) { // if element found, replace with last element on last level
				heap.set(i, heap.get(currentIndex - 1));
				heap.remove(currentIndex - 1);
				currentIndex--;
				
				heapifyDown(i); // re-heapify
				return true;
			}
		}
		return false;
	}

}