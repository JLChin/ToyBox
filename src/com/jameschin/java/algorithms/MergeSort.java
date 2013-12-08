package com.jameschin.java.algorithms;

import java.lang.reflect.Array;

/**
 * MergeSort
 * Type: Comparative, Divide And Conquer, Array Based, Not In-Place, Recursive, Stable
 * Space: O(n)
 * Time: O(n log n) where n is the number of elements.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class MergeSort {
	
	/**
	 * MergeSort on Comparable array.
	 * @param array Comparable array.
	 */
	public static <E extends Comparable<E>> void sort(E[] array) {
		if (array == null || array.length <= 1)
			return;
		
		sort(array, 0, array.length - 1);
	}
	
	/**
	 * Recursive MergeSort on Comparable array over the specified range.
	 * @param array Comparable array.
	 * @param startIndex starting index of the block to be sorted.
	 * @param endIndex ending index of the block to be sorted.
	 */
	private static <E extends Comparable<E>> void sort(E[] array, int startIndex, int endIndex) {
		if (endIndex == startIndex)
			return;
		
		int rightStart = (endIndex - startIndex + 1) / 2 + startIndex;
		int leftEnd = rightStart - 1;
		
		sort(array, startIndex, leftEnd);
		sort(array, rightStart, endIndex);
		
		merge(array, startIndex, leftEnd, rightStart, endIndex);
	}
	
	/**
	 * Merges two sorted halves of a block of the array.
	 * @param array Comparable array.
	 * @param leftStart starting index of the left half to be merged.
	 * @param leftEnd ending index of the left half to be merged.
	 * @param rightStart starting index of the right half to be merged.
	 * @param rightEnd ending index of the right half to be merged.
	 */
	@SuppressWarnings("unchecked")
	private static <E extends Comparable<E>> void merge(E[] array, int leftStart, int leftEnd, int rightStart, int rightEnd) {
		int length = rightEnd - leftStart + 1;
		E[] mergedArray = (E[]) Array.newInstance(array[0].getClass(), length);
		int index = leftStart;
		
		for (int i = 0; i < length; i++) {
			if (leftStart <= leftEnd && (rightStart > rightEnd || array[leftStart].compareTo(array[rightStart]) <= 0)) // <= for stable sort
				mergedArray[i] = array[leftStart++];
			else
				mergedArray[i] = array[rightStart++];
		}
		
		// copy mergedArray back into original array
		for (int i = 0; i < length; i++)
			array[index++] = mergedArray[i];
	}
}