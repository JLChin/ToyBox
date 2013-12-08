package com.jameschin.java.algorithms;

/**
 * Knapsack Algorithms
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class Knapsack {
	
	/**
	 * Returns the maximum value possible using unlimited copies of n items specified by weight and value, under the constraint of the maximum weight allowed.
	 * Positive/zero weights only.
	 * Space: O(W)
	 * Time: O(nW) where n is the number of items, W is the maximum weight allowed.
	 * @param values array of integer values.
	 * @param weights array of integer weights.
	 * @param maxWeight maximum weight allowed.
	 * @return the maximum value possible using unlimited copies of n items specified by weight and value, under the constraint of the maximum weight allowed.
	 */
	public static int maxValueUnbounded(int[] values, int[] weights, int maxWeight) {
		if (values == null || weights == null || values.length != weights.length || maxWeight <= 0)
			return -1;
		
		int n = values.length;
		
		for (int i = 0; i < n; i++) {
			if (weights[i] < 0)
				return -1;
		}
		
		int[] maxValue = new int[maxWeight + 1]; // maximum value, indexed by totalWeight
		
		for (int totalWeight = 1; totalWeight <= maxWeight; totalWeight++) {
			for (int i = 0; i < n; i++) {
				int currItemWeight = weights[i];
				if (currItemWeight <= totalWeight) {
					int value = maxValue[totalWeight - currItemWeight] + values[i];
					if (value > maxValue[totalWeight])
						maxValue[totalWeight] = value;
				}
			}
		}
		
		return maxValue[maxWeight];
	}
	
	/**
	 * Returns the maximum value possible using n items specified by weight and value, under the constraint of the maximum weight allowed.
	 * Positive/zero weights only.
	 * @param values array of integer values.
	 * @param weights array of integer weights.
	 * @param maxWeight maximum weight allowed.
	 * @return the maximum value possible using n items specified by weight and value, under the constraint of the maximum weight allowed.
	 */
	public static int maxValue01(int[] values, int[] weights, int maxWeight) {
		if (values == null || weights == null || values.length != weights.length || maxWeight <= 0)
			return -1;
		
		int n = values.length;
		
		for (int i = 0; i < n; i++) {
			if (weights[i] < 0)
				return -1;
		}
		
		int[] currMaxValue = new int[maxWeight + 1]; // maximum value, indexed by totalWeight
		int[] nextMaxValue = new int[maxWeight + 1];
		
		for (int i = 0; i < n; i++) {
			int currItemWeight = weights[i];
			int currItemValue = values[i];
			
			for (int totalWeight = 1; totalWeight <= maxWeight; totalWeight++) {
				if (currItemWeight <= totalWeight) {
					int value = currMaxValue[totalWeight - currItemWeight] + currItemValue;
					nextMaxValue[totalWeight] = (value > currMaxValue[totalWeight]) ? value : currMaxValue[totalWeight];
				} else
					nextMaxValue[totalWeight] = currMaxValue[totalWeight];
			}
			
			currMaxValue = nextMaxValue;
			nextMaxValue = new int[maxWeight + 1];
		}
		
		return currMaxValue[maxWeight];
	}
}