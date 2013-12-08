package com.jameschin.java.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * StringOps
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class StringOps {
	
	/**
	 * Returns an array of strings containing all unique permutations of the input string.
	 * Type: Recursive
	 * Space: O(n!)
	 * Time: O(n!) where n is the length of the string.
	 * @param string specified string to permute.
	 * @return an array of strings containing all unique permutations of the input string.
	 */
	public static String[] permutations(String string) {
		if (string == null)
			return null;
		
		char[] array = string.toCharArray();
		HashSet<String> hashset = new HashSet<String>();
		
		permutations(array, 0, hashset);
		
		String[] result = new String[hashset.size()];
		
		int index = 0;
		for (String s : hashset)
			result[index++] = s;
		
		return result;
	}
	
	/**
	 * Recursive helper function for permutations.
	 * @param array character array of the original input string.
	 * @param index current index.
	 * @param hashset HashSet to collect unique permutations of the original input string.
	 */
	private static void permutations(char[] array, int index, HashSet<String> hashset) {
		if (index == array.length) {
			hashset.add(new String(array));
			return;
		}
		
		for (int j = index; j < array.length; j++) {
			
				char temp = array[index];
				array[index] = array[j];
				array[j] = temp;
				
				permutations(array, index + 1, hashset);
				
				array[j] = array[index];
				array[index] = temp;
			
		}
	}
	
	/**
	 * Returns the original string with the characters of the specified index range reversed.
	 * Space: O(n)
	 * Time: O(n) where n is the length of the string.
	 * @param string original string whose substring is to be reversed.
	 * @param startIndex index of the first character of the specified string to be reversed.
	 * @param endIndex index position immediately after the last character to be reversed.
	 * @return the original string with the characters of the specified index range reversed.
	 */
	public static String reverseSubstring(String string, int startIndex, int endIndex) {
		if (string == null || startIndex < 0 || startIndex > endIndex || endIndex > string.length())
			return null;
		
		// build result
		String result = string.substring(0, startIndex); // prefix
		
		int currIndex = endIndex;
		while (currIndex-- > startIndex)
			result += string.charAt(currIndex);
		
		result += string.substring(endIndex, string.length()); // suffix
		
		return result;
	}
	
	/**
	 * Returns a string array containing all mutations of the input string using the map of character substitutes.
	 * Type: Stable
	 * Space: O(n^m)
	 * Time: O(n^m) where n is the size of the input string, m is the maximum number of possible replacements for each character.
	 * @param string input string to generate mutations for.
	 * @param map map containing character substitutes.
	 * @return a string array containing all mutations of the input string using the map of character substitutes.
	 */
	public static String[] generateMutations(String string, Map<Character, Character[]> map) {
		if (string == null || string.length() == 0 || map == null)
			return null;
		
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i = 0, length = string.length(); i < length; i++) {
			Character curr = string.charAt(i);
			Character[] array = map.get(curr);
			if (i == 0) { // first character of input string
				list.add(Character.toString(curr));
				if (array != null) {
					for (Character c : array)
						list.add(Character.toString(c));
				}
			} else { // remainder of input string
				int listLength = list.size();
				if (array != null) {
					for (int j = 0, arrayLength = array.length; j < arrayLength; j++) {
						Character c = array[j];
						for (int k = 0; k < listLength; k++) {
							String s = list.get(k);
							s += c;
							list.add(s); // make one extra copy of the current list for each char in the array
						}
					}
				}
				
				for (int j = 0; j < listLength; j++) {
					String s = list.get(j);
					s += curr;
					list.set(j, s); // append original character
				}
			}
		}
		
		// copy list into array and return
		int listLength = list.size();
		String[] result = new String[listLength];
		for (int i = 0; i < listLength; i++)
			result[i] = list.get(i);
		
		return result;
	}
	
	/**
	 * Find all occurrences of a word inside a character array and replace with a new word.
	 * Modified Knuth-Morris-Pratt 1977 algorithm.
	 * Space: O(k)
	 * Time: O(n + k) where k is the length of the search word, n is the length of the string to be searched.
	 * @param array character array of the original string to be searched and modified.
	 * @param oldWord string to be replaced with newWord.
	 * @param newWord string to be substituted wherever oldWord is found.
	 * @return the number of times oldWord was found and replaced with newWord.
	 */
	public static int replace(List<Character> array, String oldWord, String newWord) {
		if (array == null || oldWord == null || newWord == null || oldWord.length() == 0)
			return -1;
		
		int count = 0;
		int oldWordLength = oldWord.length();
		int newWordLength = newWord.length();
		
		// build partial match table
		int[] table = new int[oldWordLength];
		
		if (oldWordLength > 1)
			table[1] = 0;
		
		for (int i = 2, curr = 0; i < oldWordLength; i++) {
			if (oldWord.charAt(i - 1) == oldWord.charAt(curr))
				curr++;
			else
				curr = 0;
			table[i] = curr;
		}
		
		// search
		int strIndex = 0;
		int substrIndex = 0;
		
		while (strIndex < array.size()) {
			if (array.get(strIndex) == oldWord.charAt(substrIndex)) {
				strIndex++;
				if (++substrIndex == oldWordLength) { // match found, replace
					int matchIndex = strIndex - oldWordLength;
					
					for (int i = 0; i < oldWordLength; i++)
						array.remove(matchIndex);
					
					for (int i = 0; i < newWordLength; i++)
						array.add(matchIndex + i, newWord.charAt(i));
					
					substrIndex = 0;
					strIndex = strIndex - oldWordLength + newWordLength;
					count++;
				}
			} else {
				if (substrIndex == 0)
					strIndex++;
				else
					substrIndex = table[substrIndex];
			}
		}
		
		return count;
	}
	
	/**
	 * Returns the index position of the first occurrence of the substring in the string, if it exists.
	 * Modified Knuth-Morris-Pratt 1977 algorithm.
	 * Space: O(k)
	 * Time: O(n + k) where k is the length of the search word, n is the length of the string to be searched.
	 * @param string original string to be searched for the specified substring.
	 * @param substr search substring.
	 * @return the index position of the first occurrence of the substring in the string, if it exists.
	 */
	public static int substring(String string, String substr) {
		if (string == null || substr == null || substr.length() == 0)
			return -1;
		
		int strLength = string.length();
		int substrLength = substr.length();
		
		// build partial match table
		int[] table = new int[substrLength];
		
		if (substrLength > 1)
			table[1] = 0;
		
		for (int i = 2, curr = 0; i < substrLength; i++) {
			if (substr.charAt(i - 1) == substr.charAt(curr))
				curr++;
			else
				curr = 0;
			table[i] = curr;
		}
		
		// search
		int strIndex = 0;
		int substrIndex = 0;
		
		while (strIndex < strLength) {
			if (string.charAt(strIndex) == substr.charAt(substrIndex)) {
				strIndex++;
				if (++substrIndex == substrLength)
					return strIndex - substrLength;
			} else {
				if (substrIndex == 0)
					strIndex++;
				else
					substrIndex = table[substrIndex];
			}
		}
		
		return -1;
	}
	
	/**
	 * Returns true if the input string is a palindrome.
	 * Case insensitive and ignores any non-letter characters.
	 * Space: O(1)
	 * Time: O(n) where n is the length of the string.
	 * @param string input string.
	 * @return true if the input string is a palindrome, false otherwise.
	 */
	public static boolean isPalindrome(String string) {
		if (string == null || string.length() == 0)
			return false;
		
		String s = string.toLowerCase();
		String regex = "[a-z]";
		Pattern pattern = Pattern.compile(regex);
		
		int leftIndex = 0;
		int rightIndex = string.length() - 1;
		
		while (leftIndex < rightIndex) {
			// while leftIndex points to non-letter character, increment
			while (leftIndex < rightIndex && !pattern.matcher(s.subSequence(leftIndex, leftIndex + 1)).matches())
				leftIndex++;
			
			// while rightIndex points to non-letter character, decrement
			while (leftIndex < rightIndex && !pattern.matcher(s.subSequence(rightIndex, rightIndex + 1)).matches())
				rightIndex--;
			
			if (leftIndex < rightIndex && s.charAt(leftIndex++) != s.charAt(rightIndex--))
				return false;
		}
		return true;
	}
}