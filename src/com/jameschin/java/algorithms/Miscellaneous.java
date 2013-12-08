package com.jameschin.java.algorithms;

import java.util.ArrayList;

/**
 * Miscellaneous Algorithms
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class Miscellaneous {
	
	/**
	 * Returns the binary sum of two binary numbers represented as strings.
	 * Space: O(n)
	 * Time: O(n) where n is the length of the longer string.
	 * @param num1 binary number represented as a string.
	 * @param num2 binary number represented as a string.
	 * @return the binary sum of two binary numbers represented as strings.
	 */
	public static String addBinaryNumbers(String num1, String num2) {
		if (num1 == null || num2 == null)
			return null;
		
		int index1 = num1.length() - 1;
		int index2 = num2.length() - 1;
		boolean carry = false;
		String result = "";
		
		while(index1 >= 0 && index2 >= 0) {
			int sum = Integer.parseInt(num1.substring(index1, index1 + 1)) + Integer.parseInt(num2.substring(index2, index2 + 1));
			if (sum == 0) {
				if (carry) {
					result += '1';
					carry = false;
				} else
					result += '0';
			} else if (sum == 1) {
				if (carry)
					result += '0';
				else
					result += '1';
			} else { // sum == 2
				if (carry)
					result += '1';
				else {
					result += '0';
					carry = true;
				}
			}
			
			index1--;
			index2--;
		}
		
		while(index1 >= 0) {
			if (num1.charAt(index1) == '0') {
				if (carry) {
					result += '1';
					carry = false;
				} else
					result += '0';
			} else { // num1.charAt(index1) == '1'
				if (carry)
					result += '0';
				else
					result += '1';
			}
			
			index1--;
		}
		
		while(index2 >= 0) {
			if (num2.charAt(index2) == '0') {
				if (carry) {
					result += '1';
					carry = false;
				} else
					result += '0';
			} else { // num2.charAt(index2) == '1'
				if (carry)
					result += '0';
				else
					result += '1';
			}
			
			index2--;
		}
		
		if (carry)
			result += '1';
		
		// reverse result string
		String reverseResult = "";
		for (int i = result.length() - 1; i >= 0; i--)
			reverseResult += result.charAt(i);
		
		return reverseResult;
	}
	
	/**
	 * Shift right faster, using a little more memory.
	 * Space: O(k) where k is the number of positions shifted.
	 * Time: O(n) where n is the number of elements.
	 * @param array element E array.
	 * @param shift number of positions to shift.
	 */
	public static <E> void rightShift (E[] array, int shift) {
		if (shift > 0) {
			// save items displaced off of right end
			ArrayList<E> temp = new ArrayList<E>();
			int length = array.length;
			for (int i = length - shift ; i < length; i++) {
				temp.add(array[i]);
			}
			
			// copy in place
			for (int i = length - 1; i > shift - 1; i--) {
				array[i] = array[i - shift];
			}
			
			// copy displaced items to left end
			for (int i = 0; i < shift; i++) {
				array[i] = temp.get(i);
			}
		} else return;
	}

	/**
	 * Shift left using the least memory.
	 * Space: O(1)
	 * Time: O(2n) where n is the number of elements.
	 * @param array element E array.
	 * @param shift number of positions to shift.
	 */
	public static <E> void leftShift (E[] array, int shift) {
		if (shift > 0) {
			int length = array.length;
			reverse(array, 0, length - 1);
			reverse(array, 0, length - shift - 1);
			reverse(array, length - shift, length - 1);
		} else return;
	}
	
	/**
	 * Reverse items in place.
	 * Space: O(1)
	 * Time: O(n) where n is the number of elements.
	 * @param array element E array.
	 * @param startIndex starting index of the block to be reversed.
	 * @param endIndex ending index of the block to be reversed.
	 */
	public static <E> void reverse (E[] array, int startIndex, int endIndex) {
		E temp;
		
		// swap items at both ends
		while (startIndex < endIndex) {
			temp = array[startIndex];
			array[startIndex] = array[endIndex];
			array[endIndex] = temp;
			
			startIndex++;
			endIndex--;
		}
	}
}