package com.LeeGainer.Battleship2;

import java.util.Random;
import java.util.Scanner;

public class Console {
	
	// I/O
	
	/*
	 * Outputs message
	 * String -> void
	 */
	public static void displayMessage(String string) {
		System.out.println(string);		
	}
	
	/*
	 * Displays Gameboard Array[][], gameboard
	 * Gameboard object -> void
	 */
	public static void displayGame() {
		
		int mr = Gameboard.getMaxRow();
		int mc = Gameboard.getMaxCol();
		
		for(int i = 0; i < mr; i++) {
			System.out.println("\r");
			for(int j = 0; j < mc; j++) {
				System.out.print(Gameboard.getBoard()[i][j]);
			}
		}
		
		Console.displayMessage("\n");
	}

	/*
	 * Gets coordinate from user or computer
	 * Player -> int
	 */
	public static int getCoordinate(Player p) {
		
		int maxR = Gameboard.getMaxRow() - 3;
			
		// if getting coordinate from user
		if(p.checkForUser(p)) {
			Scanner getCo = new Scanner(System.in);
			String c = getCo.next();
			
			// Validate coordinate
			while(!c.matches("[0-9]")) {		
				
				Console.displayMessage("That input is invalid.\n"
						+ "Please enter a number between 0 and " + Integer.toString(maxR) + ". ");
				
				Scanner getCoAgain = new Scanner(System.in);
				c = getCoAgain.next();
			}
			
			return Integer.parseInt(c);
		
		} else {
			// Generate random coordinate for computer ship
			Random a = new Random();		
				
			int x = a.nextInt(9);			
			return x;
		}		
	}		
}
