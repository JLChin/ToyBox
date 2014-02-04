package com.jameschin.java.algorithms;

/**
 * InsertionSort
 * Type: Comparative, Array Based, In-Place, Stable
 * Space: O(1)
 * Best: O(n)
 * Worst: O(n * n) where n is the number of elements.
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class InsertionSort {
	
	/**
	 * InsertionSort on Comparable array.
	 * @param array Comparable array.
	 */
	public static <E extends Comparable<E>> void sort(E[] array) {
		if (array == null || array.length <= 1)
			return;
		
		E temp;
		int holeIndex;
		int current = 1;
		
		while (current < array.length) {
			if (array[current].compareTo(array[current - 1]) < 0) {
				temp = array[current];
				holeIndex = current;
				
				// move hole left to the proper index
				do {
					array[holeIndex] = array[holeIndex - 1];
					holeIndex--;
					if (holeIndex == 0)
						break;
				} while (temp.compareTo(array[holeIndex - 1]) < 0);
				
				array[holeIndex] = temp;
			}
			current++;
		}
	}
}