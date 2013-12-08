package com.jameschin.java.algorithms;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Find Algorithms
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class Find {
	
	/**
	 * Returns index n of the Fibonacci sequence.
	 * Space: O(1)
	 * Time: O(n) where n is the index of the Fibonacci sequence.
	 * @param n the specified index of the Fibonacci sequence to return the number.
	 * @return index n of the Fibonacci sequence.
	 */
	public static int fibonacci(int n) {
		if (n < 2)
			return n;
		
		int minusOne = 1; // store fibonacci(n - 1)
		int minusTwo = 0; // store fibonacci(n - 2)
		
		for (int i = 2; i < n; i++) {
			int temp = minusOne;
			minusOne += minusTwo;
			minusTwo = temp;
		}
		
		return minusOne + minusTwo;
	}
	
	/**
	 * Returns the kth smallest element, starting from zero.
	 * Space: O(k)
	 * Time: O(n log k) where n is the number of elements, k is the order rank of the item to return.
	 * @param array array of Comparable elements.
	 * @param k the order rank of the item to return, starting from zero.
	 * @return the kth smallest element, starting from zero.
	 */
	public static <E extends Comparable<E>> E kthSmallest(E[] array, int k) {
		if (array == null || k >= array.length || k < 0)
			return null;
		
		MaxHeap<E> maxheap = new MaxHeap<E>();
		
		for (E e : array) {
			if (maxheap.size() < k + 1)
				maxheap.add(e);
			else if (e.compareTo(maxheap.findMax()) < 0) {
				maxheap.deleteMax();
				maxheap.add(e);
			}
		}
		
		return maxheap.findMax();
	}
	
	/**
	 * Returns the kth largest element, starting from zero.
	 * Space: O(k)
	 * Time: O(n log k) where n is the number of elements, k is the order rank of the item to return.
	 * @param array array of Comparable elements.
	 * @param k the order rank of the item to return, starting from zero.
	 * @return the kth largest element, starting from zero.
	 */
	public static <E extends Comparable<E>> E kthLargest(E[] array, int k) {
		if (array == null || k >= array.length || k < 0)
			return null;
		
		MinHeap<E> minheap = new MinHeap<E>();
		
		for (E e : array) {
			if (minheap.size() < k + 1)
				minheap.add(e);
			else if (e.compareTo(minheap.findMin()) > 0) {
				minheap.deleteMin();
				minheap.add(e);
			}
		}
		
		return minheap.findMin();
	}
	
	/**
	 * Prints out the Gray code sequence up to 2^n, exclusive.
	 * Space: O(1)
	 * Time: O(2^n)
	 * @param n the power of two up to which to return the Gray code sequence.
	 */
	public static void grayCode(int n) {
		if (n < 0)
			return;
		
		int limit = 1 << n;
		
		for (int i = 0; i < limit; i++)
			System.out.println((i >> 1) ^ i);
	}
	
	/**
	 * Returns the integer value of the number represented by the roman numeral string.
	 * Space: O(1)
	 * Time: O(n) where n is the length of the roman numeral string.
	 * @param roman roman numeral string.
	 * @return the integer value of the number represented by the roman numeral string.
	 */
	public static int romanToInt(String roman) {
		if (roman == null || roman.length() == 0)
			return 0;
		
		String r = roman.toUpperCase();
		int currValue;
		int prevMaxValue = 0;
		int sum = 0;
		
		for (int i = r.length() - 1; i >= 0; i--) {
			switch (r.charAt(i)) {
			case ('I'): currValue = 1; break;
			case ('V'): currValue = 5; break;
			case ('X'): currValue = 10; break;
			case ('L'): currValue = 50; break;
			case ('C'): currValue = 100; break;
			case ('D'): currValue = 500; break;
			case ('M'): currValue = 1000; break;
			default: return -1;
			}
			
			if (currValue > prevMaxValue) {
				sum += currValue;
				prevMaxValue = currValue;
			} else if (currValue == prevMaxValue)
				sum += currValue;
			else
				sum -= currValue;
		}
		
		return sum;
	}
	
	/**
	 * Returns the power set of the specified set of elements.
	 * //TODO does not support deep copy of generic objects (use Integer, Double, Character etc only).
	 * Type: Non-recursive
	 * Space: O(2^n)
	 * Time: O(2^n)
	 * @param set set of elements.
	 * @return the power set of the specified set of elements.
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[][] powerSet(E[] set) {
		if (set == null)
			return null;
		
		int setLength = set.length;
		Class<?> setClass = set.getClass().getComponentType();

		E[][] sets = (E[][]) Array.newInstance(set.getClass(), 1 << setLength);
		sets[0] = (E[]) Array.newInstance(setClass, 0);
		
		// iterate through the set, building the power set with one new element each loop
		for (int i = 0, currentSize = 1; i < setLength; i++, currentSize = currentSize << 1) {
			for (int j = 0; j < currentSize; j++) {
				E[] newSet = (E[]) Array.newInstance(setClass, sets[j].length + 1);
				
				// construct set
				int index = 0;
				for (E e : sets[j])
					newSet[index++] = e; // copy set
				newSet[sets[j].length] = set[i]; // append new element
				sets[currentSize + j] = newSet; // assign newSet to correct slot
			}
		}
		
		return sets;
	}
	
	/**
	 * Returns the power set of the specified set of elements.
	 * Type: Recursive
	 * Space: O(2^n)
	 * Time: O(2^n)
	 * @param set set of elements.
	 * @return the power set of the specified set of elements.
	 */
	public static <E> Set<Set<E>> powerSetRecursive(Set<E> set) {
		if (set == null)
			return null;
		
		Set<Set<E>> sets = new HashSet<Set<E>>();
		
		// base case
		if (set.isEmpty()) {
			sets.add(new HashSet<E>());
			return sets;
		}
		
		// split one element from the set and recurse
		Iterator<E> iterator = set.iterator();
		E element = iterator.next();	
		set.remove(element);
		
		for (Set<E> s : powerSetRecursive(set)) {
			Set<E> newSet = new HashSet<E>(s);
			newSet.add(element);
			sets.add(newSet);
			sets.add(s);
		}
		
		return sets;
	}
	
	/**
	 * Prints all subsets of the array of size three.
	 * Space: O(1)
	 * Time: O(n^3) where n is the number of elements.
	 * @param array array of Comparable elements.
	 */
	public static <E extends Comparable <E>> void subsets(E[] array) {
		if (array == null)
			return;
		
		HeapSort.sort(array);

		for (int i = 0; i < array.length - 2; i++) {
			
			if (array[i] != array[i + 1]) {
				for (int j = i + 1; j < array.length - 1; j++) {
					
					if (array[j] != array[j + 1]) {
						for (int k = j + 1; k < array.length; k++) {
							
							if (k == array.length - 1 || array[k] != array[k + 1])
								System.out.println(array[i] + " " + array[j] + " " + array[k]);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Returns an ordered array containing the prime numbers below the integer specified.
	 * Based on Sieve of Eratosthenes algorithm.
	 * Space: O(n)
	 * Time: O(n^2) where n is the integer limit on the search range.
	 * @param n maximum of the range of positive integers to be considered while searching for prime numbers, non-inclusive.
	 * @return an ordered array containing the prime numbers below the integer specified.
	 */
	public static int[] primes(int n) {
		if (n <= 2) {
			int[] result = {};
			return result;
		}
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		boolean[] array = new boolean[n];

		for (int i = 3; i < n; i += 2) { // consider odds only
			array[i] = true;
		}
		
		int prime = 3;
		int q;
		list.add(2);
		
		while (prime < n) {
			list.add(prime);
			q = prime * prime; // skip to first square, all others below have already been eliminated
			
			while (q > 0 && q < n) { // check overflow
				array[q] = false;
				q += prime;
			}
			
			prime += 2;
			while (prime < n && array[prime] == false) {
				prime += 2; // check odds only
			}
		}
		
		int[] result = new int[list.size()];
		int index = 0;
		for (int i : list) {
			result[index++] = i;
		}
		
		return result;
	}
	
	/**
	 * Returns the number of occurrences of the specified key in the sorted array of Comparable elements.
	 * Space: O(1)
	 * Time: O(log n) where n is the number of elements.
	 * @param array sorted array of Comparable elements to search for the specified key.
	 * @param key search element.
	 * @return the number of occurrences of the specified key in the sorted array of Comparable elements.
	 */
	public static <E extends Comparable<E>> int frequency(E[] array, E key) {
		if (array == null || array.length == 0)
			return 0;
		
		int num = 0;
		int leftIndex = 0;
		int rightIndex = array.length - 1;
		int pivot = 0;
		
		// find start of key run
		while (leftIndex <= rightIndex) {
			pivot = (leftIndex + rightIndex) / 2;
			if (key.compareTo(array[pivot]) < 0)
				rightIndex = pivot - 1;
			else if (key.compareTo(array[pivot]) > 0)
				leftIndex = pivot + 1;
			else { // key.compareTo(array[pivot]) == 0
				if (pivot > 0) {
					if (array[pivot - 1].compareTo(key) < 0)
						break;
					else // pivot - 1 is also key 
						rightIndex = pivot - 1;
				} else if (pivot == 0)
					break;
			}
		}
		
		// if key was found, pivot now points to first index of run
		while (array[pivot].compareTo(key) == 0) {
			num++;
			pivot++;
			if (pivot == array.length)
				break;
		}
		return num;
	}
	
	/**
	 * Returns a sorted integer array containing the first k products of the factors specified.
	 * If factors include 0, returns an array of all 0's.
	 * If factors include only 1's, returns an array of all 1's.
	 * Space: O(k)
	 * Time: O(k) where k is the number of products.
	 * @param factors integer array containing the factors to find the first k products with.
	 * @param k the number of products to return.
	 * @return a sorted integer array containing the first k products of the factors specified.
	 */
	public static int[] products(int[] factors, int k) {
		if (k <= 0 || factors == null || factors.length == 0)
			return null;
		
		// handle 0 factors
		for (int i : factors) {
			if (i == 0)
				return new int[k];
		}
		
		// handle 1 factors
		boolean factorsContainsOne = false;
		LinkedList<Integer> fac = new LinkedList<Integer>();
		for (int i : factors) {
			if (i != 1)
				fac.add(i);
			else
				factorsContainsOne = true;
		}
		
		// factors contained only 1's
		if (fac.isEmpty()) {
			int[] ones = new int[k];
			for (int i = 0; i < k; i++)
				ones[i] = 1;
			return ones;
		}
		
		int[] array = new int[k + 1];
		array[0] = 1;
		
		int[] nextFactorIndex = new int[fac.size()]; // 0's by default
		
		int min; 
		int product;
		int flag = 0;
		for (int i = 1; i <= k; i++) { // array length k + 1
			min = Integer.MAX_VALUE;
			
			for (int j = 0; j < fac.size(); j++) {
				product = fac.get(j) * array[nextFactorIndex[j]];
				if (product < min) {
					min = product;
					flag = j;
				}
			}
			if (min == array[i - 1])
				i--; // redo loop to consume duplicates
			else
				array[i] = min;
			nextFactorIndex[flag]++;
		}
		
		// array now contains the product of powers, starting at powers of zero
		int[] products = new int[k];
		for (int i = 0; i < k; i++) {
			if (! factorsContainsOne)
				products[i] = array[i + 1];
			else
				products[i] = array[i];
		}
		return products;
	}
	
	/**
	 * Returns true if the sorted 2D matrix contains the specified element, false if not.
	 * Space: O(1)
	 * Time: O(n + m) where n is the length of the rows and m is the length of the columns.
	 * O(n) for n x n array.  
	 * @param matrix two dimensional array of Comparable elements. Rows and columns must both be sorted.
	 * @param element specified element to search for in the matrix.
	 * @return true if the sorted 2D matrix contains the specified element, false if not.
	 */
	public static <E extends Comparable<E>> boolean searchSortedMatrix(E[][] matrix, E element) {
		if (matrix == null)
			return false;
		
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i] == null)
				return false;
		}
		
		int rowLength = matrix.length;
		int row = 0;
		int col = matrix[0].length - 1; // column index, initially at last column
		
		// stepwise search from upper right corner going left and down only
		while (true) {
			if (matrix[row][col].compareTo(element) == 0)
				return true;
			if (matrix[row][col].compareTo(element) > 0)
				col--;
			else
				row++;
			if (row == rowLength || col == -1)
				return false;
		}
	}
	
	/**
	 * Returns the increment by which the sorted array was rotated/shifted to the right.
	 * This is also equal to the index of the lowest element in the array.
	 * Space: O(1)
	 * Time: O(log n) where n is the number of elements.
	 * @param array array of rotated, sorted, Comparable items.
	 * @return the increment by which the sorted array was rotated/shifted to the right.
	 */
	public static <E extends Comparable<E>> int pivotOfRotated(E[] array) {
		if (array == null)
			return -1;
		
		int leftIndex = 0;
		int rightIndex = array.length - 1;
		int pivot;
		
		while (leftIndex < rightIndex) {
			pivot = (rightIndex + leftIndex) / 2;
			
			if (array[pivot].compareTo(array[rightIndex]) > 0) // pivot somewhere to the right
				leftIndex = pivot + 1;
			else
				rightIndex = pivot;
		}
		
		return leftIndex;
	}
	
	/**
	 * Returns the position of the element in the sorted array if it exists, otherwise returns -1.
	 * Array can be rotated/shifted. Duplicates not allowed if rotated/shifted.
	 * Space: O(1)
	 * Time: O(log n) where n is the number of elements.
	 * @param array array of rotated, sorted, Comparable items.
	 * @param element Comparable element to search for.
	 * @return the position of the element in the sorted array if it exists, otherwise returns -1.
	 */
	public static <E extends Comparable<E>> int positionOfRotated(E[] array, E element) {
		if (array == null)
			return -1;
		
		int leftIndex = 0;
		int rightIndex = array.length - 1;
		int pivot;
		
		while (leftIndex <= rightIndex) {
			pivot = (rightIndex + leftIndex) / 2;
			
			if (element.compareTo(array[pivot]) == 0)
				return pivot;
			else if (array[pivot].compareTo(array[leftIndex]) > 0) { // left half strictly ascending
				if (element.compareTo(array[leftIndex]) >= 0 && element.compareTo(array[pivot]) < 0)
					rightIndex = pivot - 1;
				else 
					leftIndex = pivot + 1;
			} else { // right half strictly ascending
				if (element.compareTo(array[rightIndex]) <= 0 && element.compareTo(array[pivot]) > 0)
					leftIndex = pivot + 1;
				else
					rightIndex = pivot - 1;
			} 
		}
		
		return -1;
	}
	
	/**
	 * Returns the position of the element in the sorted array if it exists, otherwise returns -1.
	 * Space: O(1)
	 * Time: O(log n) where n is the number of elements.
	 * @param array array of sorted, Comparable items.
	 * @param element Comparable element to search for.
	 * @return the position of the element in the sorted array if it exists, otherwise returns -1.
	 */
	public static <E extends Comparable<E>> int positionOf(E[] array, E element) {
		if (array == null)
			return -1;
		
		int leftIndex = 0;
		int rightIndex = array.length - 1;
		int pivot;
		
		while (leftIndex <= rightIndex) {
			pivot = (rightIndex + leftIndex) / 2;
			
			if (element.compareTo(array[pivot]) > 0)
				leftIndex = pivot + 1;
			else if (element.compareTo(array[pivot]) < 0)
				rightIndex = pivot - 1;
			else // element.compareTo(array[pivot]) == 0
				return pivot;
		}
		
		return -1;
	}
	
	/**
	 * Return the top k elements of array in no particular order, k must be larger than the size of the array.
	 * Space: O(k) where k is the number of top elements to return.
	 * Time: O(n) where n is the number of elements.
	 * @param array array of Comparable elements.
	 * @param k the number of top Comparable elements to return.
	 * @return array containing the top k Comparable elements in no particular order.
	 */
	public static <E extends Comparable<E>> E[] getTop(E[] array, int k) {
		if (array == null || array.length == 0 || k <= 0)
			return null;
		
		MinHeap<E> minHeap = new MinHeap<E>();		
		minHeap.add(array[0]);

		for (int i = 1; i < array.length; i++) {
			if (minHeap.size() < k)
				minHeap.add(array[i]);
			else if (array[i].compareTo(minHeap.findMin()) > 0) {
				minHeap.deleteMin();
				minHeap.add(array[i]);
			}
		}

		return minHeap.toArray();
	}
}