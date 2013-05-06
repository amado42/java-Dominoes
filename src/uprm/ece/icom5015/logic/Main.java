package uprm.ece.icom5015.logic;

import java.util.Scanner;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameLogic testing =new GameLogic();
		testing.printGameStatus();
		
		System.out.println("Valid Plays for Player: "+testing.currentPlayer);
		testing.printTiles(testing.ValidPlays(testing.currentHand()));
		System.out.println("Enter your play Player: "+testing.currentPlayer);
	    Scanner scanner = new Scanner(System.in);
	    int chosenIndex = scanner.nextInt();
	    Domino chosen = testing.currentHand()[chosenIndex];
	    testing.FirstPlay(chosen);
	    testing.printGameStatus();
	    
		while(!testing.GameEnd()){
		System.out.println("Valid Plays for Player: "+testing.currentPlayer);
		testing.printTiles(testing.ValidPlays(testing.currentHand()));
		if(testing.ValidPlays(testing.currentHand()).length != 0){
		System.out.println("Enter your play Player: "+testing.currentPlayer);
	    scanner = new Scanner(System.in);
	    chosenIndex = scanner.nextInt();
	    chosen = testing.currentHand()[chosenIndex];
	    testing.Play(chosen);
	    testing.printGameStatus();}
		else {
		System.out.println("Player "+testing.currentPlayer+" has no valid plays, turn skipped");
		testing.currentPlayer=testing.NextPlayer();
		}
		}
		
		System.out.println("game is over, player "+testing.currentPlayer+" has won");

	}

}
