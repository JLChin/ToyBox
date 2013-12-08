package com.jameschin.java.algorithms;

/**
 * SelectionSort
 * Type: Comparative, In-Place, Not Stable
 * Space: O(1)
 * Time: O(n * n) where n is the number of elements.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class SelectionSort {
	
	/**
	 * SelectionSort on Comparable array over the block starting at startIndex and ending at endIndex.
	 * @param array Comparable array.
	 * @param startIndex starting index of the block to be sorted.
	 * @param endIndex ending index of the block to be sorted.
	 */
	public static <E extends Comparable<E>> void sort(E[] array, int startIndex, int endIndex) {
		if (array == null || startIndex < 0 || endIndex >= array.length || startIndex >= endIndex)
			return;
		
		E temp;
		int tempIndex;
		int innerEnd = endIndex;
		int i, j;
		
		i = startIndex;
		while (i <= endIndex) {
			temp = array[startIndex];
			tempIndex = startIndex;
			j = startIndex;
			
			while (j <= innerEnd) {
				if (array[j].compareTo(temp) > 0) {
					temp = array[j];
					tempIndex = j;
				}
				j++;
			} // found largest, now swap
			array[tempIndex] = array[endIndex - (i - startIndex)];
			array[endIndex - (i - startIndex)] = temp;
			
			innerEnd--;
			i++;
		}
	}

	/**
	 * SelectionSort on Comparable array.
	 * @param array Comparable array.
	 */
	public static <E extends Comparable<E>> void sort(E[] array) {
		if (array == null)
			return;
		
		sort(array, 0, array.length - 1);
	}
	
	/**
	 * SelectionSort on Comparable array over the block starting at startIndex and ending at the end of the array.
	 * @param array Comparable array.
	 * @param startIndex starting index of the block to be sorted.
	 */
	public static <E extends Comparable<E>> void sort(E[] array, int startIndex) {
		if (array == null || startIndex < 0 || startIndex >= array.length)
			return;
		
		sort(array, startIndex, array.length - 1);
	}
}