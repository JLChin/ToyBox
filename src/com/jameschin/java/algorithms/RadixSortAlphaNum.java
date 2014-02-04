package com.jameschin.java.algorithms;

/**
 * RadixSortAlphaNum
 * RadixSort adapted to word characters such as letters, numbers and symbols.
 * Accepts lower and upper case alphabet letters, numbers, spaces and hyphens.
 * Type: MSD, Non-Comparative, In-Place, Not Stable, Recursive
 * Space: O(k)
 * Time: O(kn) where k is the length of the longest string, n is the number of strings.
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class RadixSortAlphaNum {

	/**
	 * RadixSort on array of word character strings.
	 * @param array array of word character strings.
	 */
	public static void sort(String[] array) {
		if (array == null)
			return;
		
		sort(array, 0, array.length - 1, 0, findMaxLength(array));
	}
	
	/**
	 * Recursive RadixSort on array of word character strings.
	 * @param array array of word character strings.
	 * @param startIndex current starting index to be sorted.
	 * @param endIndex current ending index to be sorted.
	 * @param charPos current character position to be sorted.
	 * @param maxPos maximum character position to be sorted.
	 */
	private static void sort(String[] array, int startIndex, int endIndex, int charPos, int maxPos) {
		int leftIndex = startIndex;
		int rightIndex = endIndex;
		int currentIndex = startIndex;
		char leftLetter = ' ';
		char rightLetter = 'z';
		
		// move all finished words to left first, in stable order - these words are completely done and need no further recursion
		while (currentIndex <= endIndex) {
			if (array[currentIndex].length() <= charPos) {
				String temp = array[leftIndex];
				array[leftIndex++] = array[currentIndex];
				array[currentIndex] = temp;
			}
			currentIndex++;
		}
		
		// update left boundary enclosing letters for current charPos, reset cursor
		startIndex = leftIndex;
		currentIndex = leftIndex;
		
		// sort first and last characters to opposite ends on first pass, next two chars to inner opposite ends on second pass, and so on
		// (numOfChars / 2) passes at most
		while (leftIndex < rightIndex) {
			while (currentIndex <= rightIndex) {
				if (array[rightIndex].toLowerCase().charAt(charPos) == rightLetter) { // special case if rightIndex is already a rightLetter
					rightIndex--;
				} else if (array[rightIndex].toLowerCase().charAt(charPos) == leftLetter) { // special case if rightIndex is a leftLetter
					if (currentIndex == leftIndex)
						currentIndex++;
					String temp = array[leftIndex];
					array[leftIndex++] = array[rightIndex];
					array[rightIndex] = temp;
				} else { // normal case
					if (array[currentIndex].toLowerCase().charAt(charPos) == leftLetter) {
						String temp = array[leftIndex];
						array[leftIndex++] = array[currentIndex];
						array[currentIndex] = temp;
					} else if (array[currentIndex].toLowerCase().charAt(charPos) == rightLetter) {
						String temp = array[rightIndex];
						array[rightIndex--] = array[currentIndex];
						array[currentIndex] = temp;
					}
					currentIndex++;
				}
			}
			
			// recursion on the outer ends
			if (charPos + 1 < maxPos) {
				sort(array, startIndex, leftIndex - 1, charPos + 1, maxPos);
				sort(array, rightIndex + 1, endIndex, charPos + 1, maxPos);
			}
			
			// define special orderings
			if (leftLetter == ' ')
				leftLetter = '-';
			else if (leftLetter == '-')
				leftLetter = '0';
			else if (leftLetter == '9')
				leftLetter = 'a';
			else
				leftLetter++;
			rightLetter--;
			
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