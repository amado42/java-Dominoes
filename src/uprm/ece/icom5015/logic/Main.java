package uprm.ece.icom5015.logic;

import java.util.Scanner;

public class Main {
	
	public static void PromptYoSolo(){
		
		GameLogic testing =new GameLogic();
		testing.printGameStatus();
		String starting;
		Scanner scanner = new Scanner(System.in);
		int chosenIndex;
		Domino chosen;
		
		
		if(testing.currentPlayer=="bot"){
		System.out.println("Valid Plays for Player: "+testing.currentPlayer);
		testing.printTiles(testing.ValidPlays(testing.currentHand()));
		System.out.println("Enter your play Player: "+testing.currentPlayer);
	    chosenIndex = scanner.nextInt();
	    chosen = testing.currentHand()[chosenIndex];
	    testing.FirstPlay(chosen);
	    testing.printGameStatus();
		} 
		if(testing.currentPlayer!="bot"){
			chosen = testing.currentHand()[0];
		    testing.FirstPlay(chosen);
		    testing.printGameStatus();
		}
		while(!testing.GameEnd()){
			
		
		if(testing.ValidPlays(testing.currentHand()).length != 0 && testing.currentPlayer.equals("bot")){
		System.out.println("Valid Plays for Player: "+testing.currentPlayer);
		testing.printTiles(testing.ValidPlays(testing.currentHand()));
		System.out.println("Enter your play Player: "+testing.currentPlayer);
	    chosenIndex = scanner.nextInt();
	    chosen = testing.ValidPlays(testing.currentHand())[chosenIndex];
	    testing.Play(chosen);
	    testing.CheckForLock();
	    testing.printGameStatus();}
		else if(testing.ValidPlays(testing.currentHand()).length != 0 && testing.currentPlayer!="bot"){
			testing.printTiles(testing.ValidPlays(testing.currentHand()));
		    chosen = testing.ValidPlays(testing.currentHand())[0];
		    testing.PlayBasicAI(chosen);
		    testing.CheckForLock();
		    testing.printGameStatus();
		}
		else {
		System.out.println("Player "+testing.currentPlayer+" has no valid plays, turn skipped");
		testing.currentPlayer=testing.NextPlayer();
		}
		}
		
		testing.setCurrentPlayer(testing.LastPlayer());
		starting = testing.StartingPlayer();
		System.out.println("**game is over, next player is: "+starting);
		testing.printGameStatus();
		
		while(!testing.MatchEnd()){
			testing.printPlayerTiles();
			testing.NewGame(starting);
			if(testing.currentPlayer=="bot"){
			System.out.println("Valid Plays for Player: "+testing.currentPlayer);
			testing.printTiles(testing.ValidPlays(testing.currentHand()));
			System.out.println("Enter your play Player: "+testing.currentPlayer);
		    scanner = new Scanner(System.in);
		    chosenIndex = scanner.nextInt();
		    chosen = testing.currentHand()[chosenIndex];
		    testing.FirstPlay(chosen);
		    testing.printGameStatus();
			}
			if(testing.currentPlayer!="bot"){
			    chosen = testing.currentHand()[0];
			    testing.FirstPlay(chosen);
			    testing.printGameStatus();
			}
			while(!testing.GameEnd()){
				
				
				if(testing.ValidPlays(testing.currentHand()).length != 0 && testing.currentPlayer.equals("bot")){
					
				System.out.println("Valid Plays for Player: "+testing.currentPlayer);
				testing.printTiles(testing.ValidPlays(testing.currentHand()));	
				System.out.println("Enter your play Player: "+testing.currentPlayer);
			    chosenIndex = scanner.nextInt();
			    chosen = testing.ValidPlays(testing.currentHand())[chosenIndex];
			    testing.Play(chosen);
			    testing.CheckForLock();
			    testing.printGameStatus();	
				}
				else if(testing.ValidPlays(testing.currentHand()).length != 0 && testing.currentPlayer!="bot"){
					testing.printTiles(testing.ValidPlays(testing.currentHand()));
					chosen = testing.ValidPlays(testing.currentHand())[0];
				    testing.PlayBasicAI(chosen);
				    testing.CheckForLock();
				    testing.printGameStatus();	
				}
				
				else {
				System.out.println("Player "+testing.currentPlayer+" has no valid plays, turn skipped");
				testing.currentPlayer=testing.NextPlayer();
				}
				}
				
				testing.setCurrentPlayer(testing.LastPlayer());
				starting = testing.StartingPlayer();
				System.out.println("**game is over, next player is: "+starting);
				testing.printGameStatus();}
		
	}
	
	public static void PromptTodos(){

		GameLogic testing =new GameLogic();
		testing.printGameStatus();
		String starting;
		
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
	    chosenIndex = scanner.nextInt();
	    chosen = testing.ValidPlays(testing.currentHand())[chosenIndex];
	    testing.Play(chosen);
	    testing.CheckForLock();
	    testing.printGameStatus();}
		else {
		System.out.println("Player "+testing.currentPlayer+" has no valid plays, turn skipped");
		testing.currentPlayer=testing.NextPlayer();
		}
		}
		
		testing.setCurrentPlayer(testing.LastPlayer());
		starting = testing.StartingPlayer();
		System.out.println("**game is over, next player is: "+starting);
		testing.printGameStatus();
		
		while(!testing.MatchEnd()){
			testing.NewGame(starting);
			
			System.out.println("Valid Plays for Player: "+testing.currentPlayer);
			testing.printTiles(testing.ValidPlays(testing.currentHand()));
			System.out.println("Enter your play Player: "+testing.currentPlayer);
		    scanner = new Scanner(System.in);
		    chosenIndex = scanner.nextInt();
		    chosen = testing.currentHand()[chosenIndex];
		    testing.FirstPlay(chosen);
		    testing.printGameStatus();
			
			while(!testing.GameEnd()){
				System.out.println("Valid Plays for Player: "+testing.currentPlayer);
				testing.printTiles(testing.ValidPlays(testing.currentHand()));
				if(testing.ValidPlays(testing.currentHand()).length != 0){
				System.out.println("Enter your play Player: "+testing.currentPlayer);
			    chosenIndex = scanner.nextInt();
			    chosen = testing.ValidPlays(testing.currentHand())[chosenIndex];
			    testing.Play(chosen);
			    testing.CheckForLock();
			    testing.printGameStatus();}
				else {
				System.out.println("Player "+testing.currentPlayer+" has no valid plays, turn skipped");
				testing.currentPlayer=testing.NextPlayer();
				}
				}
				
				testing.setCurrentPlayer(testing.LastPlayer());
				starting = testing.StartingPlayer();
				System.out.println("**game is over, next player is: "+starting);
				testing.printGameStatus();
			
		
		
		
		
		}
		
	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//PromptTodos();
		PromptYoSolo();
		}

	
}
		
