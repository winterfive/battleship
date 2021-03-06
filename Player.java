package com.LeeGainer.Battleship2;

import java.awt.Point;
import java.util.ArrayList;

public class Player {
	
	private ArrayList <Ship> myShips;
	private ArrayList <Point> myShots;
	private String name;
	
	public Player(String name) {
		this.name = name;
		this.myShips = new ArrayList <Ship>();
		this.myShots = new ArrayList <Point>();
	}

	/*
	 * Returns name of Player
	 * void -> String
	 */	
	public String getName(Player p) {
		return name;
	}
	
	/*
	 * Returns ArrayList myShips
	 * Player -> myShips
	 */
	public ArrayList <Ship> getShips(Player p) {
		return p.myShips;
	}
	
	/**
	 * Return ArrayList myShots
	 * void -> ArrayList
	 */
	public ArrayList <Point> getMyShots() {
		return myShots;
	}
		
	/*
	 * Checks if Player name is "user"
	 * Object -> boolean
	 */
	public boolean checkForUser(Player p) {
		if(p.getName(p).equals("user")) {
			return true;			
		} else {
			return false;
		}
	}

	/*
	 * Creates Player "fleet" (ArrayList myShips)
	 * Player -> void
	 */	
	public void createFleet(Player p) {
		
		Counter shipCount = new Counter();
		shipCount.addToCounter();
		
		while(shipCount.getCounter() < 6) {
			
			Point xy = new Point();			
			
			while(true) {
				
				if(p.checkForUser(p)) {
					Console.displayMessage("Enter X coordinate for ship #" + (shipCount.getCounter()) + ": ");
				}
				
				// Get row coordinate
				xy.x = Console.getCoordinate(p);
				
				if(p.checkForUser(p)) {
					Console.displayMessage("Enter Y coordinate for ship #" + (shipCount.getCounter()) + ": ");
				}
				
				// Get column coordinate
				xy.y = Console.getCoordinate(p);
				
				// Check for repeated ship location
				// If this isn't the first ship
				if(shipCount.getCounter() > 0) {
					if(isLocationUsed(p, xy)) {
						// If Player is user
						if(p.checkForUser(p)) {
							Console.displayMessage("That location is already being used.");
						}						
						continue;							
					} else {
						break;
					}
				}
			}
			
			Ship myShip = new Ship(xy, true);
			
			myShips.add(myShip);
			
			// If player is user			
			if(p.checkForUser(p)) {
				Gameboard.updateGameboard(xy, "S");
				
				// Display updated game board w/ each each ship			
				Console.displayGame();
			// If player is computer
			} else {
				// Add point to myShots array
				// Computer cannot repeat shots or hit it's own ships
				myShots.add(xy);						
				
				pause(1000);
				
				Console.displayMessage("Computer ship #" + shipCount.getCounter() + " is ready for battle.");
			}
			
			shipCount.addToCounter();
		}
	}
	
	/*
	 * Check if Point already exists in ArrayList myShips
	 * Point -> boolean
	 */
	public boolean isLocationUsed(Player p, Point point) {
			
		for(Ship s : p.myShips) {
	        if(s.getLocation().equals(point)) {
	        		return true;
	        }
		}
		return false;
	}
	
	/*
	 * Umbrella method for game play methods
	 * void -> void
	 */
	public static void playGame(Player u, Player c) {
		
		Counter shotCount = new Counter();
		shotCount.addToCounter();
		
		while(true) {
			Console.displayMessage("PLAYER'S TURN:");
			Point xy = u.takeShot(u, shotCount);
			u.evaluateShot(u, c, xy);
			Console.displayGame();
			if(u.hasLost(u, c)) {
				break;
			}
			Console.displayMessage("COMPUTER'S TURN:");
			Point xy2 = c.takeShot(c, shotCount);
			c.evaluateShot(c, u, xy2);
			Console.displayGame();
			if(c.hasLost(c, u)) {				
				break;				
			}
			
			shotCount.addToCounter();
		}			
	}
	
	/*
	 * Evaluates "shot" (point) fired by players
	 * Player, Player, Point -> void
	 */
	private void evaluateShot(Player shooter, Player other, Point point) {
		
		// Check point against player array
		
		// shooter is user
		if(shooter.checkForUser(shooter)) {
			// Check user array for hit
			if(isLocationUsed(shooter, point)) {
				
				pause(1000);
				
				// If shot a repeated "sunk your own ship" shot				
				if(shooter.isShotRepeat(shooter, point)) {
					Console.displayMessage("You made that shot before.");
				} else {
					Console.displayMessage("You sank your own ship!");
					
					// Update board
					Gameboard.updateGameboard(point, "x");
					pause(1000);	
					
					// Change user ship.active to false
					shooter.setToFalse(shooter, point);
					
					// Add point to myShots
					myShots.add(point);
				}
			} else {
				// Check user shot against computer array
				if(isLocationUsed(other, point)) {
					
					pause(1000);
					
					// If shot a repeated shot that sunk enemy ship
					if(shooter.isShotRepeat(shooter, point)) {
						Console.displayMessage("You made that shot before.");
					} else {
						Console.displayMessage("You sank an enemy ship!");
						
						// Update board
						Gameboard.updateGameboard(point, "X");
						pause(1000);	
						
						// Change computer ship.active to false
						shooter.setToFalse(other, point);
						
						// Add point to myShots
						myShots.add(point);
					}										
				} else {
					
					pause(1000);
					
					// If shot a repeated missed host
					if(shooter.isShotRepeat(shooter, point)) {
						Console.displayMessage("You made that shot before.");
					} else {
						Console.displayMessage("You missed.");
						
						// Update board
						Gameboard.updateGameboard(point, "-");
						pause(1000);		
						
						// Add point to myShots
						myShots.add(point);										
					}					
				}				
			}
		} else {
			// shooter is computer
			if(isLocationUsed(other, point)) {
				
				pause(1250);
				Console.displayMessage("The computer sank your ship!");
				
				// Update board
				Gameboard.updateGameboard(point, "x");
				pause(1000);	
				
				// record computer shot
				getMyShots().add(point);	
				
				// Change user ship.active to false
				other.setToFalse(other, point);				
			} else {
				
				pause(1250);
				Console.displayMessage("Computer missed.");
				
				// record missed computer shot
				getMyShots().add(point);				
			}
		}
	}
	
	/*
	 * Pauses game for better readability
	 * int -> void
	 */	
	private void pause(int i) { 
		try {
			Thread.sleep(i);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}		
	}

	/*
	 * Checks active state of ships in myArray for victory
	 * Player, Player -> boolean
	 */
	private boolean hasLost(Player u, Player c) {
		
		Counter uSunk = new Counter();
		Counter cSunk = new Counter();
		
		for(Ship s : u.myShips) {
			if(s.getActive() == false) {
				uSunk.addToCounter();
			}			
		}
		
		for(Ship s : c.myShips) {
			if(s.getActive() == false) {
				cSunk.addToCounter();
			}			
		}
		
		if(uSunk.getCounter() == 5) {
			Console.displayMessage("Your fleet is at the bottom of the ocean.\nYou have lost the battle.");
			return true;
		}
		
		if(cSunk.getCounter() == 5) {
			Console.displayMessage("The computer's fleet has been sunk.\nYou have won the battle!");
			return true;
		}		
		return false;
	}
	
	/*
	 * Gets Point for player shots
	 * void -> Point
	 */
	private Point takeShot(Player p, Counter shotCount) {
		
		Point xy = new Point();
		
		// If player is user
		if(p.checkForUser(p)) {
			Console.displayMessage("Enter X coordinate for shot #" + shotCount.getCounter() + ": ");
		}
		
		xy.x = Console.getCoordinate(p);
		
		// If player is user
		if(p.checkForUser(p)) {
			Console.displayMessage("Enter Y coordinate for shot #" + shotCount.getCounter() + ": ");
		}
		
		xy.y = Console.getCoordinate(p);
		
		// Check myShots for repeated shot from computer
		if(!checkForUser(p)) {
			// If this coordinate is a repeat
			while(isShotRepeat(p, xy)) {
				// Get another pair of coordinates
				xy.x = Console.getCoordinate(p);
				xy.y = Console.getCoordinate(p);
			}
		}		
		
		return xy;
	}
	
	/*
	 * Change sunken Ship.active to false
	 * Player, Point -> void
	 */
	public void setToFalse(Player p, Point point) {
		// Change user ship.active to false
		for(Ship s : p.myShips) {
			if(s.getLocation().equals(point)) {
                s.setActive(false);
			}
		}
	}
	
	/*
	 * Checks computer myShots for repeated shots
	 * Player, Point -> boolean
	 */	
	public boolean isShotRepeat(Player p, Point xy) {
		for(Point point : p.myShots) {
	        if(point.equals(xy)) {
	        		return true;
	        }
		}
		return false;
	}
}
