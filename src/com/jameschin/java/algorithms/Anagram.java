package com.jameschin.java.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Anagram Algorithms
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class Anagram {
	
	private static class Data {
		String word; // original word with characters sorted
		int index; // position in the original list
		Data(String s, int i) {word = s; index = i;}
	}
	
	/**
	 * Returns a list of anagram groupings of the original input array.
	 * Space: O(kn)
	 * Time: O(kn) where k is the length of the longest string, n is the number of strings.
	 * @param array input array of words.
	 * @return a list of anagram groupings of the original input array.
	 */
	public static List<List<String>> collect(String[] array) {
		if (array == null || array.length == 0)
			return null;
		
		int arrayLength = array.length;
		Data[] dataArray = new Data[arrayLength];
		
		// sort characters in each word, O(kn) time
		for (int i = 0; i < arrayLength; i++)
			dataArray[i] = new Data(sortCharacters(array[i]), i);
		
		// sort words, O(kn) time
		sortWords(dataArray, 0, arrayLength - 1, 0, findMaxLength(array));
		
		// collect anagrams in one pass
		List<List<String>> result = new ArrayList<List<String>>();
		String prev = null;
		for (int i = 0; i < arrayLength; i++) {
			if (!dataArray[i].word.equals(prev))
				result.add(new ArrayList<String>());
			result.get(result.size() - 1).add(array[dataArray[i].index]);
			prev = dataArray[i].word;
		}
		
		return result;
	}
	
	/**
	 * Returns the original string toUpperCase with the characters sorted.
	 * Non-comparative in-place radix sort.
	 * Space: O(k)
	 * Time: O(k) where k is the length of the string.
	 * @param string input string.
	 * @return the original string toUpperCase with the characters sorted.
	 */
	private static String sortCharacters(String string) {
		if (string == null)
			return null;
		
		char[] array = string.toUpperCase().toCharArray();
		
		char leftChar = 'A';
		char rightChar = 'Z';
		int index = 0;
		int left = 0;
		int right = array.length - 1;
		
		// 13 passes at most
		while (left < right) {
			while (index < right) {
				if (array[right] == rightChar) // special case if right is already a rightChar
					right--;
				else if (array[right] == leftChar) { // special case if right is a leftChar
					if (index == left)
						index++;
					array[right] = array[left];
					array[left++] = leftChar;
				} else { // normal case
					if (array[index] == leftChar) {
						array[index] = array[left];
						array[left++] = leftChar;
					} else if (array[index] == rightChar) {
						array[index] = array[right];
						array[right--] = rightChar;
					}
					index++;
				}
			}
			
			index = left;
			leftChar++;
			rightChar--;
		}
		
		return new String(array);
	}
	
	/**
	 * Non-comparative in-place radix sort on array of word character strings.
	 * Space: O(k)
	 * Time: O(kn) where k is the length of the longest string, n is the number of strings.
	 * @param array array of strings.
	 * @param start current starting index to be sorted.
	 * @param end current ending index to be sorted.
	 * @param charPos current character position to be sorted.
	 * @param maxPos maximum character position to be sorted.
	 */
	private static void sortWords(Data[] array, int start, int end, int charPos, int maxPos) {
		char leftLetter = 'A';
		char rightLetter = 'Z';
		int left = start;
		int right = end;
		int curr = start;
		Data temp;
		
		// move all finished words to left - these words are completely done and need no further recursion
		while (curr <= end) {
			if (array[curr].word.length() <= charPos) {
				temp = array[left];
				array[left++] = array[curr];
				array[curr] = temp;
			}
			curr++;
		}
		
		// update left boundary enclosing letters for current charPos, reset cursor
		start = left;
		curr = left;
		
		// sort first and last characters to opposite ends on first pass, next two chars to inner opposite ends on second pass, and so on
		// 13 passes at most
		while (left < right) {
			while (curr <= right) {
				if (array[right].word.charAt(charPos) == rightLetter) { // special case if right is already a rightLetter
					right--;
				} else if (array[right].word.charAt(charPos) == leftLetter) { // special case if right is a leftLetter
					if (curr == left)
						curr++;
					temp = array[left];
					array[left++] = array[right];
					array[right] = temp;
				} else { // normal case
					if (array[curr].word.charAt(charPos) == leftLetter) {
						temp = array[left];
						array[left++] = array[curr];
						array[curr] = temp;
					} else if (array[curr].word.charAt(charPos) == rightLetter) {
						temp = array[right];
						array[right--] = array[curr];
						array[curr] = temp;
					}
					curr++;
				}
			}
			
			// recursion on the outer ends
			if (charPos + 1 < maxPos) {
				sortWords(array, start, left - 1, charPos + 1, maxPos);
				sortWords(array, right + 1, end, charPos + 1, maxPos);
			}
			
			leftLetter++;
			rightLetter--;
			
			curr = left;		
			start = left;
			end = right;
		}
	}
	
	/**
	 * Returns the length of the longest word in the array.
	 * @param array array of strings.
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