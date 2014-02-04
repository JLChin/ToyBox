package com.jameschin.java.algorithms;

/**
 * Boggle
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class Boggle {
	private char[][] board;
	
	Boggle(char[][] array) {board = array;}
	
	public boolean exists(String w) {
		String word = w.toUpperCase();
		
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (word.charAt(0) == board[row][col])
					if (check(word.substring(1), row, col))
						return true;
			}
		}
		
		return false;
	}
	
	private boolean check(String word, int row, int col) {
		// successfully consumed all characters
		if (word.length() == 0)
			return true;
		
		char currentChar = word.charAt(0);
		
		// check upper left
		if (row > 0 && col > 0 && board[row - 1][col - 1] == currentChar) {
			if (check(word.substring(1), row - 1, col - 1))
				return true;
		}
		
		// check up
		if (row > 0 && board[row - 1][col] == currentChar) {
			if (check(word.substring(1), row - 1, col))
				return true;
		}
		
		// check upper right
		if (row > 0 && col < board[0].length - 1 && board[row - 1][col + 1] == currentChar) {
			if (check(word.substring(1), row - 1, col + 1))
				return true;
		}
		
		// check right
		if (col < board[0].length - 1 && board[row][col + 1] == currentChar) {
			if (check(word.substring(1), row, col + 1))
				return true;
		}
		
		// check lower right
		if (row < board.length - 1 && col < board[0].length - 1 && board[row + 1][col + 1] == currentChar) {
			if (check(word.substring(1), row + 1, col + 1))
				return true;
		}
		
		// check down
		if (row < board.length - 1 && board[row + 1][col] == currentChar) {
			if (check(word.substring(1), row + 1, col))
				return true;
		}
		
		// check lower left
		if (row < board.length - 1 && col > 0 && board[row + 1][col - 1] == currentChar) {
			if (check(word.substring(1), row + 1, col - 1))
				return true;
		}
		
		// check left
		if (col > 0 && board[row][col - 1] == currentChar) {
			if (check(word.substring(1), row, col - 1))
				return true;
		}
		
		return false;
	}
}
