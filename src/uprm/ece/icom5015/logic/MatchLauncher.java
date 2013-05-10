package uprm.ece.icom5015.logic;

import java.util.Scanner;

import uprm.ece.icom5015.ai.ConservativeAI;
import uprm.ece.icom5015.ai.RandomAI;
import uprm.ece.icom5015.ai.DiversityAI;
import uprm.ece.icom5015.util.DominoPlayTuple;

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


	public static void uncontrolledMatch(){
		/* Count how many times team US and THEM win respectively */
		int countUs = 0; int countThem=0;

		for (int i = 0; i<2000; i++){
			/* Start a new Domino match. */
			Logic match = new Logic();
			/* Holds the domino and location of the AI's decision. */
			DominoPlayTuple decision = null;
			/* Player 1 */
			DiversityAI player1 = new DiversityAI(match.getTiles().getTiles(1));
			/* Player 2 */
			ConservativeAI player2 = new ConservativeAI();
			/* Player 3 */
			DiversityAI player3 = new DiversityAI(match.getTiles().getTiles(3));
			/* Player 4 */
			ConservativeAI player4 = new ConservativeAI();



			while(!match.isMatchEnd()){

				while(!match.isGameEnded()){

					/* Current Player has a move. */
					if(match.hasMove(match.getCurrentPlayer())){
						/* Determine the tile and location where the player wants to it in. */

						switch(match.getCurrentPlayer()){
						case 1 : decision = player1.choosePlay(match);
						break;
						case 2 : decision = player2.choosePlay(match);
						break;
						case 3 : decision = player3.choosePlay(match);
						break;
						case 4 : decision = player4.choosePlay(match);
						break;
						}
						match.play(decision.getDomino(), decision.getLocation());
					}
					/* Player passes. */
					else{
						match.pass();
					}
				}
				match.doFinishGame();
			}
			/* Determine who won and attribute the match to that team. */
			if (match.getCurrentPlayer()==1||match.getCurrentPlayer()==3)
				countUs++;
			else
				countThem++;
		}
		System.out.println("Team Us won "+countUs+" games.\n" +
						   "Team Them won "+countThem+" games.");
	}

	public static void main(String[] args){
		//controlledMatch();
		uncontrolledMatch();
	}
}

