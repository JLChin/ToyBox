package com.jameschin.java.algorithms;

import java.util.ArrayList;

/**
 * RadixSort
 * Positive/zero integers only.
 * Type: LSD, Non-Comparative, Not In-Place, Stable
 * Space: O(n)
 * Time: O(n) where n is the number of elements.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class RadixSortLSD {
	private static final byte BASE = 10;

	/**
	 * RadixSort on array of positive/zero integers.
	 * @param array positive/zero integer array.
	 */
	public static void sort(int[] array) {
		if (array == null)
			return;
		
		int maxDivisor = findMaxDivisor(array);
		
		// iterate through one digit per loop, starting from LSD
		for (int divisor = 1; divisor <= maxDivisor; divisor *= BASE) {
			// create list of buckets
			ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>(BASE);
			for (int i = 0; i < BASE; i++)
				list.add(new ArrayList<Integer>());
			
			// sort numbers into radix buckets
			for (int num : array)
				list.get((num / divisor) % BASE).add(num);
			
			// empty buckets back into original array
			for (int j = 0, k = 0; j < BASE; j++) {
				for (int i : list.get(j))
					array[k++] = i;
			}
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