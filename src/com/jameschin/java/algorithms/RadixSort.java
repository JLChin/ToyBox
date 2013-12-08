package com.jameschin.java.algorithms;

/**
 * RadixSort
 * Positive/zero integers only.
 * Type: MSD, Non-Comparative, In-Place, Not Stable, Recursive
 * Space: O(1)
 * Time: O(n) where n is the number of elements.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class RadixSort {
	private static final byte BASE = 10;
	
	/**
	 * RadixSort on array of positive/zero integers.
	 * @param array positive/zero integer array.
	 */
	public static void sort(int[] array) {
		if (array == null)
			return;
		
		sort(array, 0, array.length - 1, findMaxDivisor(array));
	}
	
	/**
	 * Recursive RadixSort on array of positive/zero integers.
	 * @param array positive/zero integer array.
	 * @param startIndex current starting index to be sorted.
	 * @param endIndex current ending index to be sorted.
	 * @param divisor highest divisor of current integer suffix.
	 */
	private static void sort(int[] array, int startIndex, int endIndex, int divisor) {
		int currentIndex = startIndex;
		int leftIndex = startIndex;
		int rightIndex = endIndex;
		byte leftNumber = 0;
		byte rightNumber = BASE - 1;
		
		// sort 0 and 9 to opposite ends on first pass, 1 and 8 to inner opposite ends on second pass, and so on
		// for Base 10, at most five full passes: 0 9, 1 8, 2 7, 3 6, 4 5
		while (leftIndex < rightIndex) {
			while (currentIndex <= rightIndex) {
				int rightIndexNum = (array[rightIndex] / divisor) % BASE;
				if (rightIndexNum == rightNumber) // special case if rightIndex is already a rightNumber
					rightIndex--;
				else if (rightIndexNum == leftNumber) { // special case if rightIndex is a leftNumber
					if (currentIndex == leftIndex)
						currentIndex++;
					int temp = array[leftIndex];
					array[leftIndex++] = array[rightIndex];
					array[rightIndex] = temp;
				} else { // normal case
					int currentIndexNum = (array[currentIndex] / divisor) % BASE;
					if (currentIndexNum == leftNumber) {
						int temp = array[leftIndex];
						array[leftIndex++] = array[currentIndex];
						array[currentIndex] = temp;
					} else if (currentIndexNum == rightNumber) {
						int temp = array[rightIndex];
						array[rightIndex--] = array[currentIndex];
						array[currentIndex] = temp;
					}
					currentIndex++;
				}
			}
			
			// recursion on the outer ends
			if (divisor / BASE != 0) {
				sort(array, startIndex, leftIndex - 1, divisor / BASE);
				sort(array, rightIndex + 1, endIndex, divisor / BASE);
			}
			
			leftNumber++;
			rightNumber--;
			
			currentIndex = leftIndex;
			
			startIndex = leftIndex;
			endIndex = rightIndex;
		}
	}

	/**
	 * Returns the base power equal in digit length to the highest number in the array.
	 * @param array positive/zero integer array.
	 * @return base power equal in digit length to the highest number.
	 */
	private static int findMaxDivisor(int[] array) {
		int maxDivisor = 1;
		for (int num : array) {
			while (num >= maxDivisor) {
				if (maxDivisor == 1000000000) return maxDivisor;
				else maxDivisor *= BASE;
			}
		}
		return maxDivisor / BASE; // maxOrder is equal size to the highest number
	}
}