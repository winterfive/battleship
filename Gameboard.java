package com.LeeGainer.Battleship2;

import java.awt.Point;

public class Gameboard {
	
	// The gameboard 2D array is size [maxRow][maxCol].
	// The area in the array that is used for play is [maxRow - 2][maxCol - 6].
	// The play area is square, [m][m].
	
	private static int maxRow = 12;
	private static int maxCol = 16;
	private static String[][] gameboard;
	
	public Gameboard() {
		Gameboard.gameboard = new String [maxRow][maxCol];
	}

	/*
	 * Create initial gameboard
	 * void -> void
	 */
	public void setUpGameboard() {
		
		for(int i = 0; i < maxRow; i++) {
			for(int j = 0; j < maxCol; j++) {
				
				// Place numbers in first row
				if (i == 0 && j != 0 && j != 1 && j != 2 && j != maxCol - 3 && j != maxCol - 2 && j != maxCol - 1) {
					gameboard[i][j] = String.valueOf(j - 3) + " ";
				}
				
				// Draw spaces in all columns but last number column
				// The last column that holds numbers (ships) needs 2 spaces
				else if(i != 0 && i != 11 && j != maxCol - 4) {
					gameboard[i][j] = "  ";
				}
				
				// draw spaces
				else {
					gameboard[i][j] = " ";
				}
				
				// place numbers in bottom row
				if (i == maxRow - 1 && j != 0 && j != 1 && j != 2 && j != maxCol - 3 && j != maxCol - 2 && j != maxCol - 1) {
					gameboard[i][j] = String.valueOf(j - 3) + " ";
				}
				
				// place numbers in columns on Lside of board
				if (j == 0 && i != 0 && i != maxRow - 1) {
					gameboard[i][j] = String.valueOf(i - 1);
				}
				
				// place numbers in columns on Rside of board
				if (j == maxCol - 1 && i != 0 && i != maxRow - 1) {
					gameboard[i][j] = String.valueOf(i - 1) + " ";
				}
				
				//place spaces along side columns
				if ((j == 1 || j == maxCol - 2) && i != 0 && i != maxRow - 1) {
					gameboard[i][j] = " ";
				}
				
				// place pipes along sides
				if ((j == 2 || j == maxCol - 3) && i != 0 && i != maxRow - 1) {
					gameboard[i][j] = "|";
				}
			}			
		}
	}

	/*
	 * returns maxRow
	 * void -> int
	 */	
	public static int getMaxRow() {
		return maxRow;
	} 
	
	/*
	 * returns maxCol
	 * void -> int
	 */	
	public static int getMaxCol() {
		return maxCol;
	}
	
	/*
	 * Returns field gameboard
	 * void -> Array[][]
	 */
	public static String[][] getBoard() {
		return gameboard;
	}

	/*
	 * Updates Gameboard String[][] gameboard
	 * Point -> void
	 */
	public static void updateGameboard(Point p, String s) {
		int x = p.x;
		int y = p.y;
				
		if(y == 9) {
			Gameboard.getBoard()[x + 1][y + 3] = s;	
		} else {
			Gameboard.getBoard()[x + 1][y + 3] = s + " ";
		}
	}
	
}
