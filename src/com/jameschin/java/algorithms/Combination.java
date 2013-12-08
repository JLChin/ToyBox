package com.jameschin.java.algorithms;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Combination Algorithms
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class Combination {
	
	/**
	 * Returns a list containing all combinations of the input elements of selection size k, with or without replacement, with or without permutations.
	 * Note: combinations w/ permutations w/o replacement is undefined for this method. Please use permutations() instead.
	 * Time/Space w/o Replacement w/o Permutations: O(k * (n choose k))
	 * Time/Space w/ Replacement w/o Permutations: O(k * (n multichoose k))
	 * Time/Space w/ Replacement w/ Permutations: O(k * n ^ k) where k is the selection size, n is the number of elements, k <= n.
	 * @param array input array of elements.
	 * @param k selection size.
	 * @param replacementAllowed boolean value specifying whether or not replacement is allowed.
	 * @param permutationsAllowed boolean value specifying whether or not permutations are allowed.
	 * @return a list containing all combinations of the input elements of selection size k, with or without replacement, with or without permutations.
	 */
	public static <E> List<E[]> nCk(E[] array, int k, boolean replacementAllowed, boolean permutationsAllowed) {
		if (array == null || array.length < k || k < 1 || (permutationsAllowed && !replacementAllowed))
			return null;
		
		List<E[]> result = new ArrayList<E[]>();
		Stack<E> stack = new Stack<E>();
		nCk(array, 0, stack, k, result, replacementAllowed, permutationsAllowed);
		
		return result;
	}
	
	/**
	 * Recursive helper function for nCk.
	 * @param array input array of elements.
	 * @param index current index.
	 * @param stack stack of elements representing the current combination being constructed.
	 * @param k selection size.
	 * @param result a list containing all combinations of the input elements of selection size k, with or without replacement, with or without permutations.
	 * @param replacementAllowed boolean value specifying whether or not replacement is allowed.
	 * @param permutationsAllowed boolean value specifying whether or not permutations are allowed.
	 */
	@SuppressWarnings("unchecked")
	private static <E> void nCk(E[] array, int index, Stack<E> stack, int k, List<E[]> result, boolean replacementAllowed, boolean permutationsAllowed) {
		if (stack.size() == k) {
			E[] contents = (E[]) Array.newInstance(array.getClass().getComponentType(), k);
			
			int i = 0;
			for (E e : stack)
				contents[i++] = e;
			
			result.add(contents);
			return;
		}
		
		for (int j = (permutationsAllowed ? 0 : index); j < array.length; j++) {
			stack.push(array[j]);
			nCk(array, j + (replacementAllowed ? 0 : 1), stack, k, result, replacementAllowed, permutationsAllowed);
			stack.pop();
		}
	}
	
	/**
	 * Returns a list containing all permutations of the input elements.
	 * Space: O(n!)
	 * Time: O(n!) where n is the number of elements.
	 * @param array input array of elements.
	 * @return a list containing all permutations of the input elements.
	 */
	public static <E> List<E[]> permutations(E[] array) {
		if (array == null || array.length == 0)
			return null;
		
		List<E[]> result = new ArrayList<E[]>();
		
		permutations(array, 0, result);
		
		return result;
	}
	
	/**
	 * Recursive helper function for permutations.
	 * @param array input array of elements.
	 * @param index current index.
	 * @param result a list containing all permutations of the input elements.
	 */
	private static <E> void permutations(E[] array, int index, List<E[]> result) {
		if (index == array.length) {
			result.add(array.clone());
			return;
		}
		
		for (int j = index; j < array.length; j++) {
			E temp = array[index];
			array[index] = array[j];
			array[j] = temp;
			
			permutations(array, index + 1, result);
			
			array[j] = array[index];
			array[index] = temp;
		}
	}
}