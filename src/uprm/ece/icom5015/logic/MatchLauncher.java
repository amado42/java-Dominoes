package uprm.ece.icom5015.logic;

import java.util.Scanner;

public class MatchLauncher {
	
	public static void controlledMatch(){
		/* Start a new Domino match. */
		Logic match = new Logic();
		/* Print the initial conditions */
		match.printGameStatus();
		/* Used to prompt player for decisions. */
		Scanner scanner = new Scanner(System.in);
		/* Holds the index value of the tile chosen. */
		int chosenIndex;
		/* Holds the domino of the selected index. */
		Domino chosenTile;
		
		
		while (!match.isGameEnded()){
			
			/* Player does not pass */
			if(match.hasMove(match.getCurrentPlayer())){
				match.printAllTiles();
				System.out.println("Valid Tiles: ");
				match.printTiles(match.validPlays(match.getCurrentPlayer()));
				System.out.println("Enter your play Player "+ match.getCurrentPlayer()+": ");
				chosenIndex = scanner.nextInt();
				/* Initializes the chosen domino. */
				chosenTile = match.validPlays(match.getCurrentPlayer())[chosenIndex];
				/* Choose where to throw the tile */
				if (match.hasThrowingOption(chosenTile)){
					System.out.print("Enter which extremity to throw tile: 0-left 1-right");
					chosenIndex = scanner.nextInt();
				}
				/* If no option, location is set to the matching extremity. */
				else if(match.canThrowLeft(chosenTile))
					/* Will throw to the left. */
					chosenIndex = 0;
				else{
					/* Will throw to the right. */
					chosenIndex = 1;
				}
				match.play(chosenTile, chosenIndex);
				
			}
			/* Player passes. */
			else{
				System.out.println("Player "+ match.getCurrentPlayer()+ " passes!");
				match.pass();
			}
			match.printExtremities();
		}
		match.doFinishGame();
		match.printWinningMessage();	
	}
	
	public static void main(String[] args){
		controlledMatch();
	}
}
	
	