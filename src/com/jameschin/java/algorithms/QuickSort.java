package com.jameschin.java.algorithms;

/**
 * QuickSort
 * Type: Comparative, Divide And Conquer, Array Based, In-Place, Recursive, Not Stable
 * Space Best: O(log n)
 * Space Worst: O(n)
 * Time Best: O(n log n)
 * Time Worst: O(n^2) where n is the number of elements.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class QuickSort {
	
	/**
	 * QuickSort on Comparable array.
	 * @param array Comparable array.
	 */
	public static <E extends Comparable<E>> void sort(E[] array) {
		if (array == null)
			return;
		
		sort(array, 0, array.length - 1);
	}
	
	/**
	 * Recursive QuickSort on Comparable array over the specified range.
	 * @param array Comparable array.
	 * @param start starting index of the block to be sorted.
	 * @param end ending index of the block to be sorted.
	 */
	private static <E extends Comparable<E>> void sort(E[] array, int start, int end) {
		if (end - start < 1)
			return;
		
		int pivot = partition(array, start, end);
		sort(array, start, pivot - 1);
		sort(array, pivot + 1, end);
	}
	
	/**
	 * Partition elements to left side or right side based on if less-than/equal-to or greater-than the pivot, respectively.
	 * @param array Comparable array.
	 * @param start starting index of the block to be partitioned.
	 * @param end ending index of the block to be partitioned.
	 * @return final index of the pivot.
	 */
	private static <E extends Comparable<E>> int partition(E[] array, int start, int end) {
		E pivotValue = array[end];
		int pivotPosition = end--;
		
		while (start <= end) {
			if (array[start].compareTo(pivotValue) <= 0)
				start++;
			else {
				E temp = array[start];
				array[start] = array[end];
				array[end--] = temp;
			}
		}
		
		// swap pivot into final position
		array[pivotPosition] = array[start];
		array[start] = pivotValue;
		return start;
	}
}