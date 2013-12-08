package com.jameschin.java.algorithms;

/**
 * RadixSortLengthLexico
 * RadixSort adapted to word characters such as letters, numbers and symbols.
 * Accepts lower and upper case alphabet letters, numbers, spaces and hyphens.
 * Sorts elements by string length and then by lexicographical order.
 * Type: MSD, Non-Comparative, In-Place, Not Stable, Recursive
 * Space: O(k)
 * Time: O(kn) where k is the length of the longest string, n is the number of strings.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class RadixSortLengthLexico {
	
	/**
	 * RadixSort by length of string outer order, lexicographic inner order.
	 * Type: MSD, Non-Comparative, In-Place, Not Stable, Recursive
	 * Space: O(1)
	 * Time: O(n) where n is the number of elements.
	 * @param array array of Strings to be sorted.
	 */
	public static void sort(String[] array) {
		if (array == null)
			return;
		
		int startIndex = 0;
		int endIndex = array.length - 1;
		
		int leftLength = 1;
		int leftIndex = 0;
		
		int rightLength = findMaxLength(array);
		int rightIndex = endIndex;
		
		int currentIndex = 0;
		String temp;
		
		// sort length 1 and maxLength strings to opposite ends on first pass, 2 and maxLength - 1 to inner opposite ends on second pass, and so on
		// maxLength / 2 passes at most
		while (leftIndex < rightIndex && leftLength < rightLength) {
			while (currentIndex <= rightIndex) {
				if (array[rightIndex].length() == rightLength) { // special case if rightIndex is already a rightLength
					rightIndex--;
				} else if (array[rightIndex].length() == leftLength) { // special case if rightIndex is a leftLength
					if (currentIndex == leftIndex)
						currentIndex++;
					temp = array[leftIndex];
					array[leftIndex++] = array[rightIndex];
					array[rightIndex] = temp;
				} else { // normal case
					if (array[currentIndex].length() == leftLength) {
						temp = array[leftIndex];
						array[leftIndex++] = array[currentIndex];
						array[currentIndex] = temp;
					} else if (array[currentIndex].length() == rightLength) {
						temp = array[rightIndex];
						array[rightIndex--] = array[currentIndex];
						array[currentIndex] = temp;
					}
					currentIndex++;
				}
			}
			
			sortLexico(array, startIndex, leftIndex - 1, 0, leftLength);
			sortLexico(array, rightIndex + 1, endIndex, 0, rightLength);
			
			currentIndex = leftIndex;
			leftLength++;
			rightLength--;
			
			startIndex = leftIndex;
			endIndex = rightIndex;
		}	
	}
	
	/**
	 * RadixSort adapted to word characters such as letters, numbers and symbols.
	 * Accepts lower and upper case alphabet letters, numbers, spaces and hyphens.
	 * Type: MSD, Non-Comparative, In-Place, Not Stable, Recursive
	 * Space: O(1)
	 * Time: O(n) where n is the number of elements.
	 * @param array array of word character strings.
	 * @param startIndex current starting index to be sorted.
	 * @param endIndex current ending index to be sorted.
	 * @param charPos current character position to be sorted.
	 * @param maxPos maximum character position to be sorted.
	 */
	private static void sortLexico(String[] array, int startIndex, int endIndex, int charPos, int maxPos) {
		char leftLetter = ' ';
		int leftIndex = startIndex;
		
		char rightLetter = 'z';
		int rightIndex = endIndex;
		
		int currentIndex = startIndex;
		String temp;
		
		// move all finished words to left first, in stable order - these words are completely done and need no further recursion
		while (currentIndex <= endIndex) {
			if (array[currentIndex].length() <= charPos) {
				temp = array[leftIndex];
				array[leftIndex++] = array[currentIndex];
				array[currentIndex] = temp;
			}
			currentIndex++;
		}
		
		// update left boundary enclosing letters for current charPos, reset cursor
		startIndex = leftIndex;
		currentIndex = leftIndex;
		
		// sort a and z to opposite ends on first pass, b and y to inner opposite ends on second pass, and so on
		// 13 passes at most
		while (leftIndex < rightIndex) {
			while (currentIndex <= rightIndex) {
				if (array[rightIndex].toLowerCase().charAt(charPos) == rightLetter) { // special case if rightIndex is already a rightLetter
					rightIndex--;
				} else if (array[rightIndex].toLowerCase().charAt(charPos) == leftLetter) { // special case if rightIndex is a leftLetter
					if (currentIndex == leftIndex)
						currentIndex++;
					temp = array[leftIndex];
					array[leftIndex++] = array[rightIndex];
					array[rightIndex] = temp;
				} else { // normal case
					if (array[currentIndex].toLowerCase().charAt(charPos) == leftLetter) {
						temp = array[leftIndex];
						array[leftIndex++] = array[currentIndex];
						array[currentIndex] = temp;
					} else if (array[currentIndex].toLowerCase().charAt(charPos) == rightLetter) {
						temp = array[rightIndex];
						array[rightIndex--] = array[currentIndex];
						array[currentIndex] = temp;
					}
					currentIndex++;
				}
			}
			
			// recursion on the outer ends
			if (charPos + 1 < maxPos) {
				sortLexico(array, startIndex, leftIndex - 1, charPos + 1, maxPos);
				sortLexico(array, rightIndex + 1, endIndex, charPos + 1, maxPos);
			}
			
			// define special orderings
			if (leftLetter == ' ')
				leftLetter = '-';
			else if (leftLetter == '-')
				leftLetter = '0';
			else if (leftLetter == '9')
				leftLetter = 'a';
			else
				leftLetter = (char) (leftLetter + 1);
			rightLetter = (char) (rightLetter - 1);
			
			currentIndex = leftIndex;
			
			startIndex = leftIndex;
			endIndex = rightIndex;
		}
	}
	
	/**
	 * Returns the length of the longest word in the array.
	 * @param array array of word character strings.
	 * @return the length of the longest word in the array.
	 */
	private static int findMaxLength(String[] array) {
		int maxLength = 0;
		for (String s : array) {
			if (s.length() > maxLength)
				maxLength = s.length();
		}
		return maxLength;
	}
}