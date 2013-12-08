package com.jameschin.java.algorithms;

/**
 * Chess
 * //TODO blocking, castling, en passant, promotion, check.
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class Chess {
	private ChessPiece[][] board = {{new Rook(false), new Knight(false), new Bishop(false), new Queen(false), new King(false), new Bishop(false), new Knight(false), new Rook(false)},
			{new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false)},
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true)},
			{new Rook(true), new Knight(true), new Bishop(true), new Queen(true), new King(true), new Bishop(true), new Knight(true), new Rook(true)}
	};
	private boolean whiteTurn = true;
	
	private abstract class ChessPiece {
		boolean isWhite;
		String symbol;
		ChessPiece(boolean isWhite) {this.isWhite = isWhite;}
		abstract boolean isLegalMove(int sRow, int sCol, int dRow, int dCol);
	}
	
	private class King extends ChessPiece {
		King(boolean isWhite) {super(isWhite); symbol = "Ki";}
		
		boolean isLegalMove(int sRow, int sCol, int dRow, int dCol) {
			if (dCol == sCol + 1 && dRow <= sRow + 1 && dRow >= sRow - 1) return true; // right or diagonal right one
			if (dCol == sCol - 1 && dRow <= sRow + 1 && dRow >= sRow - 1) return true; // left or diagonal left one
			if (dCol == sCol && dRow == sRow + 1) return true; // up one
			if (dCol == sCol && dRow == sRow - 1) return true; // down one
			return false;
		}
	}
	
	private class Queen extends ChessPiece {
		Queen(boolean isWhite) {super(isWhite); symbol = "Qn";}

		boolean isLegalMove(int sRow, int sCol, int dRow, int dCol) {
			if (dRow - sRow == dCol - sCol && dRow != sRow) return true; // backward diagonal
			if (sRow - dRow == dCol - sCol && dRow != sRow) return true; // forward diagonal
			if (dCol == sCol && dRow != sRow) return true; // straight up or down
			if (dRow == sRow && dCol != sCol) return true; // straight right or left
			return false;
		}
	}
	
	private class Bishop extends ChessPiece {
		Bishop(boolean isWhite) {super(isWhite); symbol = "Bi";}

		boolean isLegalMove(int sRow, int sCol, int dRow, int dCol) {
			if (dRow - sRow == dCol - sCol && dRow != sRow) return true; // backward diagonal
			if (sRow - dRow == dCol - sCol && dRow != sRow) return true; // forward diagonal
			
			System.out.println(sRow + " " + sCol + " " + dRow + " " + dCol);
			return false;
		}
	}
	
	private class Rook extends ChessPiece {
		Rook(boolean isWhite) {super(isWhite); symbol = "Rk";}
		
		boolean isLegalMove(int sRow, int sCol, int dRow, int dCol) {
			if (dCol == sCol && dRow != sRow) return true; // straight up or down
			if (dRow == sRow && dCol != sCol) return true; // straight right or left
			return false;
		}
	}
	
	private class Knight extends ChessPiece {
		Knight(boolean isWhite) {super(isWhite); symbol = "Kn";}
		
		boolean isLegalMove(int sRow, int sCol, int dRow, int dCol) {
			if ((dRow == sRow + 2 || dRow == sRow - 2) && (dCol == sCol + 1 || dCol == sCol - 1)) return true; // up or down
			if ((dCol == sCol + 2 || dCol == sCol - 2) && (dRow == sRow + 1 || dRow == sRow - 1)) return true; // left or right
			return false;
		}
	}
	
	private class Pawn extends ChessPiece {
		Pawn(boolean isWhite) {super(isWhite); symbol = "Pn";}
		
		boolean isLegalMove(int sRow, int sCol, int dRow, int dCol) {
			if (isWhite && dCol == sCol && dRow == sRow - 1) return true;
			if (isWhite && dCol == sCol && dRow == 4 && sRow == 6) return true; // white two step opening
			if (!isWhite && dCol == sCol && dRow == sRow + 1) return true;
			if (!isWhite && dCol == sCol && dRow == 3 && sRow == 1) return true; // black two step opening
			return false;
		}
	}
	
	public void drawBoard() {
		String piece = "";
		
		System.out.println("\n   -----------------------------------------------");
		
		for (int row = 0; row < 8; row++) {
			System.out.print(8 - row + " | ");
			
			for (int col = 0; col < 8; col++) {
				if (board[row][col] != null) {
					if (board[row][col].isWhite)
						piece += "W";
					else
						piece += "B";
					piece += board[row][col].symbol;
					System.out.print(piece);
					piece = "";
				} else
					System.out.print("   ");
				
				System.out.print(" | ");
			}
			
			System.out.println("\n   -----------------------------------------------");
		}
		System.out.println("     A     B     C     D     E     F     G     H");
	}
	
	/**
	 * Translates move from A-H 1-8 form to Row Column form. 
	 * Returns an array in the form of {sRow, sCol, dRow, dCol}.
	 * @param sc source column character.
	 * @param sr source row integer.
	 * @param dc destination column character.
	 * @param dr destination row integer.
	 * @return an array in the form of {sRow, sCol, dRow, dCol}.
	 */
	private int[] translateMove(char sc, int sr, char dc, int dr) {
		int[] array = new int[4];
		
		array[0] = 8 - sr;
		array[1] = (int) sc - 65;
		array[2] = 8 - dr;
		array[3] = (int) dc - 65;
		
		return array;
	}
	
	public boolean move(char sc, int sr, char dc, int dr) {
		int[] array = translateMove(sc, sr, dc, dr);
		int sRow = array[0];
		int sCol = array[1];
		int dRow = array[2];
		int dCol = array[3];
		
		if (outOfBounds(sRow, sCol) || outOfBounds(dRow, dCol)) {
			System.out.println("Out of bounds!");
			return false;
		}
		
		ChessPiece piece = board[sRow][sCol];
		if (piece == null) {
			System.out.println("No chess piece at source location!");
			return false;
		}
		
		if (piece.isWhite != whiteTurn) {
			System.out.println("Cannot move a piece belonging to your opponent!");
			return false;
		}
		
		if (!piece.isLegalMove(sRow, sCol, dRow, dCol)) {
			System.out.println("Illegal move!");
			return false;
		}
		
		// move is legal
		board[dRow][dCol] = piece;
		board[sRow][sCol] = null;
		
		// increment turn
		whiteTurn ^= true;
		
		drawBoard();
		return true;
	}
	
	private boolean outOfBounds(int row, int col) {
		if (row > 7 || row < 0 || col > 7 || col < 0) return true;
		return false;
	}
}