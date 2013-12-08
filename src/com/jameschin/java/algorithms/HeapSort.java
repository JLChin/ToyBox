package com.jameschin.java.algorithms;

/**
 * HeapSort
 * Type: Comparative, Array Based, In-Place, Not Stable
 * Space: O(1)
 * Time: O(n log n) where n is the number of elements.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class HeapSort {
	
	/**
	 * HeapSort on Comparable array over the block starting at startIndex and ending at endIndex.
	 * @param array Comparable array.
	 * @param startIndex starting index of the block to be sorted.
	 * @param endIndex ending index of the block to be sorted.
	 */
	public static <E extends Comparable<E>> void sort(E[] array, int startIndex, int endIndex) {
		if (array == null || startIndex < 0 || endIndex >= array.length || endIndex <= startIndex)
			return;
		
		E temp;
		buildHeap(array, startIndex, endIndex); // convert a block of the array into a MaxHeap
		
		while (endIndex > startIndex) {
			// swap top of MaxHeap with endIndex
			temp = array[startIndex];
			array[startIndex] = array[endIndex];
			array[endIndex--] = temp; // shrink sort space by 1
			
			heapifyDown(array, startIndex, startIndex, endIndex); // re-heapify
		}
	}
	
	/**
	 * Convert a block of the array into a MaxHeap.
	 * @param array original array of elements to be sorted.
	 * @param startIndex starting index to convert the original array to a MaxHeap.
	 * @param endIndex ending index to convert the original array to a MaxHeap.
	 */
	private static <E extends Comparable<E>> void buildHeap(E[] array, int startIndex, int endIndex) {
		int parent = (endIndex + startIndex - 1) / 2; // start with last parent node
		
		// heapify down each parent node
		while (parent >= startIndex) {
			heapifyDown(array, parent, startIndex, endIndex);
			parent--;
		}
	}

	/**
	 * Recursively heapify downwards, starting at index node.
	 * @param heap heap array representing the heap tree.
	 * @param index index of current node to start heapifying.
	 * @param startIndex index of the first node considered as part of the heap tree.
	 * @param endIndex index of the last node considered as part of the heap tree.
	 */
	private static <E extends Comparable<E>> void heapifyDown(E[] heap, int index, int startIndex, int endIndex) {
		int leftChildIndex = index * 2 + 1 - startIndex;
		int rightChildIndex = index * 2 + 2 - startIndex;
		int swapIndex = index;
		
		// if left child exists and greater than current node
		if (leftChildIndex <= endIndex) {
			if (heap[leftChildIndex].compareTo(heap[swapIndex]) > 0)
				swapIndex = leftChildIndex;

			// if right child exists and greater than current node and left child
			if (rightChildIndex <= endIndex) {
				if (heap[rightChildIndex].compareTo(heap[swapIndex]) > 0)
					swapIndex = rightChildIndex;
			}
		}
		
		if (swapIndex != index) { // swap if necessary
			E temp = heap[index];
			heap[index] = heap[swapIndex];
			heap[swapIndex] = temp;
			
			heapifyDown(heap, swapIndex, startIndex, endIndex); // check next level down
		}
	}

	/**
	 * HeapSort on Comparable array.
	 * @param array Comparable array.
	 */
	public static <E extends Comparable<E>> void sort(E[] array) {
		if (array == null)
			return;
		
		sort(array, 0, array.length - 1);
	}
	
	/**
	 * HeapSort on Comparable array over the block starting at startIndex and ending at the end of the array.
	 * @param array Comparable array.
	 * @param startIndex starting index of the block to be sorted.
	 */
	public static <E extends Comparable<E>> void sort(E[] array, int startIndex) {
		if (array == null || startIndex < 0 || startIndex >= array.length)
			return;
		
		sort(array, startIndex, array.length - 1);
	}
}