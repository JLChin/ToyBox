package com.jameschin.java.algorithms;

/**
 * ReverseSolve
 * Recursively builds all expressions using the given numbers and operators and then evaluates.
 * Returns the first expression found to match the target number.
 * All combinations of operators with all permutations of the input list are tried.
 * build() only returns expressions which completely consume the input list.
 * Space: O(n * n! m^(n-1))
 * Time: O(n! m^(n-1)) where n is the number of input numbers, m is the number of operators.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class ReverseSolve {
	private static final String[] DEFAULT_OPERATORS = {"+", "-", "*", "/"};
	
	public static String build(double[] list, double target) {
		if (list == null || list.length == 0)
			return null;
		
		return buildExpression(list, null, target, "");
	}
	
	public static String build(double[] list, String[] operators, double target) {
		if (list == null || list.length == 0)
			return null;
		
		return buildExpression(list, operators, target, "");
	}
	
	private static String buildExpression(double[] list, String[] operators, double target, String expression) {
		// terminal case
		if (list.length == 1) {
			Calculator calculator = new Calculator();
			expression += list[0];
			double result = calculator.evaluate(expression);
			
			if (result == target)
				return expression;
			else
				return null;
		}

		// list contains two or more numbers
		int listLength = list.length;
		double[] newList;

		for (int i = 0; i < listLength; i++) {
			String exp = expression + list[i];

			// create new list with all numbers except the current
			newList = new double[listLength - 1];
			for (int j = 0, index = 0; j < newList.length; j++, index++) {
				if (index != i)
					newList[j] = list[index];
				else
					j--;
			}

			// build expression with each operator and list subset
			if (operators != null) {
				for (int j = 0; j < operators.length; j++) {
					String newExp = exp + operators[j];
					newExp = buildExpression(newList, operators, target, newExp);
					if (newExp != null)
						return newExp;
				}
			} else {
				for (int j = 0; j < DEFAULT_OPERATORS.length; j++) {
					String newExp = exp + DEFAULT_OPERATORS[j];
					newExp = buildExpression(newList, null, target, newExp);
					if (newExp != null)
						return newExp;
				}
			}
		}
		
		return null;
	}
}