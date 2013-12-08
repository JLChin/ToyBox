package com.jameschin.java.algorithms;

import java.util.Random;

/**
 * RandomWeighted
 * Fast generation of discrete random variables using Marsaglia 1963 condensed table lookup algorithm.
 * Positive/zero integer weights only.
 * Space: O(k) where k is the number of weights.
 * Time: O(1)
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class RandomWeighted {
	private static final byte BASE = 10;
	private Random random;
	private int[][] table; // row = divisor position (0 MSD), column = distribution for each divisor position, labeled by weight index i.e. {0, 0, 1, 2, 4, 4 ...}
	private int[] radixSum; // sum of all the radix for each divisor position
	private int maxDivisor; // max power of BASE of the weights
	private byte maxDivisorLength; // digit length of maxDivisor
	private int totalWeight;
	private boolean useWeighted; // if no weights provided, default to uniform distribution
	
	RandomWeighted(int[] weights) {
		random = new Random();
		totalWeight = 0;
		
		if (weights.length == 0)
			useWeighted = false;
		else {
			maxDivisor = findMaxDivisor(weights);
			
			String s = String.valueOf(maxDivisor);
			maxDivisorLength = (byte) s.length();
			
			table = new int[maxDivisorLength][];
			radixSum = new int[maxDivisorLength];
			
			int[] radix;
			int divisor = maxDivisor;
			int columnIndex;
			for (byte rowIndex = 0; rowIndex < maxDivisorLength; rowIndex++) {
				radix = new int[weights.length]; // reset the array each loop
				
				for (int i = 0; i < weights.length; i++) {
					radix[i] = (weights[i] / divisor) % BASE;
					radixSum[rowIndex] += radix[i];
				}
				
				table[rowIndex] = new int[radixSum[rowIndex]]; // contains the distribution for this radix position
				
				columnIndex = 0;
				for (int i = 0; i < weights.length; i++){
					for (int j = 0; j < radix[i]; j++) {
						table[rowIndex][columnIndex++] = i; // set distribution, labeled 0 to (weights.length - 1) 
					}
				}
				
				divisor /= BASE;
			}
			
			for (int i : weights) {
				totalWeight += i;
			}
			
			useWeighted = true;
		}
	}
	
	/**
	 * Returns a random index based on the specified weight distribution.
	 * @return random index corresponding to the weights in the order specified, starting from 0 to weights.length - 1.
	 */
	public int nextInt() {
		if (! useWeighted)
			return random.nextInt();
		
		int divisor = maxDivisor;
		int roll = random.nextInt(totalWeight);
		int rollRadix; // this number ranges from 0 to radixSum[rowIndex]
		int currRange;
		int prevRange = 0;
		
		for (byte rowIndex = 0; rowIndex < maxDivisorLength; rowIndex++) {
			currRange = radixSum[rowIndex] * divisor;
			
			if (roll < currRange + prevRange) {
				rollRadix = (roll - prevRange)/ divisor;
				return table[rowIndex][rollRadix];
			}
			
			prevRange += currRange;
			divisor /= BASE;
		}
		
		return -1; // never reaches here
	}
	
	/**
	 * Returns a random index based on the specified weight distribution.
	 * @param weights array of weight integers specifying random distribution weight.
	 * @return random index corresponding to the weights in the order specified, starting from 0 to weights.length - 1.
	 */
	public static int nextInt(int[] weights) {
		RandomWeighted rw = new RandomWeighted(weights);
		return rw.nextInt();
	}
	
	/**
	 * Returns the base power equal in digit length to the highest number in the array.
	 * @param array positive/zero integer array.
	 * @return base power equal in digit length to the highest number.
	 */
	private int findMaxDivisor(int[] array) {
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