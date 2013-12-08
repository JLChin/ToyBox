package com.jameschin.java.algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Calculator
 * Negative doubles are accepted.
 * Space: O(n)
 * Time: O(n) where n is the length of the mathematical expression.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class Calculator {
	private final ArrayList<Character> numbers = new ArrayList<Character>();
	private final ArrayList<Character> operators = new ArrayList<Character>();
	private Stack<Object> stack = new Stack<Object>();
	private Stack<Character> opStack = new Stack<Character>();
	private ArrayDeque<Object> deque = new ArrayDeque<Object>();
	
	Calculator () {
		numbers.add('0'); numbers.add('1'); numbers.add('2'); numbers.add('3');
		numbers.add('4'); numbers.add('5'); numbers.add('6'); numbers.add('7');
		numbers.add('8'); numbers.add('9'); numbers.add('.');
		
		operators.add('+'); operators.add('-'); operators.add('*'); operators.add('/');
		operators.add('('); operators.add(')');
		operators.add('['); operators.add(']');
		operators.add('{'); operators.add('}');
	}
	
	/**
	 * Evaluate the mathematical expression supplied in the string and return the result.
	 * @param exp string mathematical expression.
	 * @return result.
	 */
	public double evaluate(String exp) {
		int i = exp.length() - 1; // left cursor
		int j = exp.length(); // right cursor
		
		// parse numbers and operators onto stack, from right to left
		while (i > -1) {
			if (numbers.contains(exp.charAt(i))) {
				while (numbers.contains(exp.charAt(i))) {
					i--; // advance to the end of number
					if (i == -1)
						break;
				}
				stack.push(Double.parseDouble(exp.substring(i + 1, j)));
			} else if (operators.contains(exp.charAt(i))) {
				stack.push(exp.charAt(i));
				i--;
			} else if (exp.charAt(i) == ' ') {
				i--;
			}
			
			j = i + 1; // sync start and end cursors for next entry
		}
		
		// stack now contains parsed expression, opStack and deque empty
		// process unary negation
		if (stack.peek().toString().equals("-")) { // if '-' is at beginning of string
			deque.push((double) 0.0); // insert zero
			deque.push(stack.pop()); // transfer '-'
		}
		String peek;
		while (!stack.isEmpty()) {
			if (stack.peek().toString().equals("-")) {
				peek = deque.peek().toString();
				if (peek.equals("(") || peek.equals("[") || peek.equals("{") || peek.equals("*") || peek.equals("/") || peek.equals("+") || peek.equals("-")) {
					// if '-' follows left parentheses or another operator
					stack.pop(); // discard '-'
					deque.push( - (double) stack.pop()); // negate next number
				} else
					deque.push(stack.pop()); // transfer regular subtraction '-'
			} else
				deque.push(stack.pop());
		}
		
		// return elements from deque to stack
		while (!deque.isEmpty()) {
			stack.push(deque.pop());
		}
		
		//change from infix to postfix
		while (!stack.isEmpty()) {
			switch (stack.peek().toString()) {
			case ("*"):
			case ("/"):
				if (!opStack.isEmpty()) {
					if (opStack.peek() == '*' || opStack.peek() == '/')
						deque.push(opStack.pop()); // pop higher or equal priority operation onto postfix deque
				}
				opStack.push((char) stack.pop());
				break;
			case ("+"):
			case ("-"):
				if (!opStack.isEmpty()) {
					if (opStack.peek() == '*' || opStack.peek() == '/' || opStack.peek() == '+' || opStack.peek() == '-')
						deque.push(opStack.pop()); // pop higher or equal priority operation onto postfix deque
				}
				opStack.push((char) stack.pop());
				break;
			case ("("):
			case ("["):
			case ("{"):
				opStack.push((char) stack.pop());
				break;
			case (")"):
				while (opStack.peek() != '(') {
					deque.push(opStack.pop());
				}
				opStack.pop(); // discard '('
				stack.pop(); // discard ')'
				break;
			case ("]"):
				while (opStack.peek() != '[') {
					deque.push(opStack.pop());
				}
				opStack.pop(); // discard '('
				stack.pop(); // discard ')'
				break;
			case ("}"):
				while (opStack.peek() != '{') {
					deque.push(opStack.pop());
				}
				opStack.pop(); // discard '('
				stack.pop(); // discard ')'
				break;
			default:
				deque.push(stack.pop()); // push number onto postfix deque
			}
		}
		
		// entire stack is processed, pop remaining operator stack to complete postfix string
		while (!opStack.isEmpty()) {
			deque.push(opStack.pop());
		}
		
		// evaluate deque postfix string; stack and opStack empty
		double opA, opB;
		char operation;
		while (!deque.isEmpty()) {
			switch (deque.peekLast().toString()) {
			case ("*"):
			case ("/"):
			case ("+"):
			case ("-"):
				opB = (double) stack.pop();
				opA = (double) stack.pop();
				operation = (char) deque.removeLast();
				stack.push(evaluate(opA, opB, operation));
				break;
			default:
				stack.push(deque.removeLast());
			}
		}
		
		return (double) stack.pop();
	}
	
	/**
	 * Evaluate on operands based on operator and return the result.
	 * @param a operand.
	 * @param b operand.
	 * @param op operator.
	 * @return result.
	 */
	private double evaluate(double a, double b, char op) {
		if (op == '+') return a + b;
		if (op == '-') return a - b;
		if (op == '*') return a * b;
		if (op == '/') return a / b;
		return -1;
	}
}