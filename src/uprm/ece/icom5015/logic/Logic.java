package uprm.ece.icom5015.logic;

import java.util.ArrayList;

/**
 * Contains all game and match data for a game of Domino.
 * This class will utilize this data in order to determine
 * game and match ending conditions. It will also constrain the 
 * game to function according to the classic game's rules.
 *
 */
public class Logic {

	/* Holds the number representing the active player. */
	private int currentPlayer;
	/* Holds the number representing the player that starts
	 * the next game.
	 */
	private int startingPlayer;
	/* Holds the 28 tiles and distributions. */
	private Tiles dominoes;
	
	/* Holds the two tiles at each end of the Domino chain.
	 * Also records each of the individual values. */
	private Domino[] extremes;
	private int leftExtreme, rightExtreme;
	/* Holds the teams scores. Teams are distributed as follows:
	 * Team Us = player1 + player3. Team Them = player2 + player4.
	 */
	private int teamUsScore, teamThemScore;
	/* Boolean to determine if its the first game of the match. */
	private boolean isFirstGame;
	
	/**
	 * Basic Constructor. Initializes all values, representing that 
	 * a new Domino match has begun. All team scores are set to 0 and 
	 * the tiles are distributed to represent the initial hand. The extremes 
	 * have not yet been determined. The starting player will be the one to 
	 * whom the double six tile was assigned.
	 * @throws Exception 
	 */
	public Logic(){
		teamUsScore = 0;
		teamThemScore = 0;
		extremes = new Domino[2];
		dominoes = new Tiles();
		isFirstGame = true;
		try {
			startingPlayer = determineMatchStarter(dominoes);
			currentPlayer = startingPlayer;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Iterates through the hands of each player in order 
	 * to determine who yields the double six tile.
	 * @param dominoes
	 * @return
	 */
	private int determineMatchStarter(Tiles dominoes) throws Exception{
		/* Here i represents the players. */
		for (int i=1; i<5; i++){
			for(Domino tile : dominoes.getTiles(i)){
				if(tile.isDouble() && tile.getWeight()==12){
					return (i);
				}
			}
		}	
		throw new Exception("No valid player was found.");
	}
	
	/**
	 * Does a new game. This redistributes all tiles to 
	 * the four players.
	 */
	public void newGame(){
		//The current player is the startingPlayer.
		currentPlayer = startingPlayer;
		dominoes.newHand();
	}
	
	/**
	 * Returns a list of the valid moves a player has according
	 * to the board and status. Note that if it is the first game, the
	 * only valid move is the double six tile. 
	 */
	public Domino[] validPlays(int playerNo){
		Domino[] player = dominoes.getTiles(playerNo);
		ArrayList<Domino> valid = new ArrayList<Domino>();
		/* If it is the first game, force double six. */
		if (isFirstGame && dominoes.played.isEmpty()){
			for ( Domino tile : player){
				if (tile.getHeadValue()==6 && tile.getTailValue()==6)
					valid.add(tile);
			}
		}
		/* If it is start of any game other than the first,
		 * any play is valid.
		 */
		else if (dominoes.played.isEmpty()){
			return player;
		}
		
		/* Verify which tiles are valid */
		else{
			for(Domino tile : player){
				if (tile.getHeadValue() == this.leftExtreme  || tile.getTailValue() == this.leftExtreme ||
					tile.getHeadValue() == this.rightExtreme || tile.getTailValue() == this.rightExtreme){
					valid.add(tile);
				}
			}
		}
		
		return (Domino[]) valid.toArray(new Domino[valid.size()]);
	}
	
	/**
	 * Puts a Domino into the board. It removes it from the player's
	 * hand. Also, it updates the global certainty lists: unplayed and
	 * played. 
	 * @param domino
	 * @param location accepts 0 - leftExtreme or 1 - rightExtreme. Unused
	 * for the event that it is the FirstMove.
	 */
	public void play(Domino domino, int location){
		
		/* If First Move set both extremes to the selected tile. */
		if(dominoes.getPlayedTiles().size()==0){
			extremes[0]=domino;
			extremes[1]=domino;
			leftExtreme =domino.getHeadValue();
			rightExtreme=domino.getTailValue();
			
		}
		else{
			//Plays tile at the left extreme.
			if (location == 0){
				extremes[0]=domino;
				//Left Extreme matches the domino's head.
				if(leftExtreme == domino.getHeadValue()){
					//Simulates connecting the head with the extreme.
					//new extreme will be the domino's tail.
					leftExtreme = domino.getTailValue();
				}
				//Right Extreme matches the domino's tail.
				else{
					//Simulates connecting the tail with the extreme.
					//New extreme will be the domino's head.
					leftExtreme = domino.getHeadValue();
				}
			}
			//Plays tile at the right extreme.
			else{
				extremes[1]=domino;
				//Right Extreme matches the domino's head.
				if(rightExtreme == domino.getHeadValue()){
					//Simulates connecting the head with the extreme.
					//New extreme will be the domino's tail.
					rightExtreme = domino.getTailValue();
				}
				//Right Extreme matches the domino's tail.
				else{
					//Simulates connecting the head with the extreme.
					//New extreme will be the domino's tail.
					rightExtreme = domino.getHeadValue();
				}
			}
		}
		/* Remove domino from player's hand. */
		dominoes.remove(currentPlayer, domino);
		/* Remove domino from unplayed tiles. */
		dominoes.getUnplayedTiles().remove(domino);
		/* Add domino to the boad. */
		dominoes.getPlayedTiles().add(domino);
		
		/* 
		 * If this play does not cause the game to be over,
		 * then set current player to be the one to the right.
		 */
		if(!isGameEnded())
			currentPlayer = nextPlayer(currentPlayer);
		
			
	}
	
	/**
	 * Returns true if the tile can be thrown in either extremity.
	 * @param tile
	 * @return
	 */
	public boolean hasThrowingOption(Domino tile){
		
		return (canThrowLeft(tile) && canThrowRight(tile));
	}
	
	/**
	 * Returns true if the tile can be thrown to the left.
	 * @param tile
	 * @return
	 */
	public boolean canThrowLeft(Domino tile){
		return (tile.getHeadValue() == leftExtreme  || tile.getTailValue() == leftExtreme);
	}
	
	
	public boolean canThrowRight(Domino tile){
		return (tile.getHeadValue() == rightExtreme || tile.getTailValue() == rightExtreme);
	}
	
	
	/**
	 * Updates the scores according to the games result. Furthermore
	 * sets the next game's starting player and starts a new game.
	 */
	public void doFinishGame(){
		isFirstGame = false;
		if (isLocked()){
			int us   = dominoes.teamUsWeight();
			int them = dominoes.teamThemWeight();
			//Verify if hand both teams have the same amount of tiles.
			if(us == them){
			   
				//Do not change previous game's starting player and
				//do not add anything to the scores. Simply restart 
				//game conditions.
			}
			//A lock with different scores on each team.
			else{
				//Team Us executed the lock and won.
				if (us > them && (currentPlayer == 1 || currentPlayer ==3)){
					
					startingPlayer = currentPlayer;
					teamUsScore+= dominoes.getUnplayedCount();
				}
				
				//Team Us executed the lock and lost.
				else if(us < them &&  (currentPlayer == 1 || currentPlayer ==3)){
					startingPlayer = nextPlayer(currentPlayer);
					teamThemScore += dominoes.getUnplayedCount();
				}
				
				//Team Them executed the lock and won.
				else if (us < them && (currentPlayer == 2 || currentPlayer ==4)){
					startingPlayer = currentPlayer;
					teamThemScore += dominoes.getUnplayedCount();
				}
				
				//Team Them executed the lock and lost.
				else{
					startingPlayer = nextPlayer(currentPlayer);
					teamUsScore += dominoes.getUnplayedCount();
				}	
			}	
		}
		//Game ended because a player has 0 tiles left.
		else{
			startingPlayer = currentPlayer;
			//Update us.
			if(currentPlayer==1 || currentPlayer==3){
				teamUsScore+=dominoes.getUnplayedCount();
			}
			//Update them.
			else{
				teamThemScore+=dominoes.getUnplayedCount();
			}
		}
		newGame();
	}
	
	/**
	 * Returns the player's number to the right of the current player.
	 * If a number is passed in the parameter which doesn't represent
	 * a player.
	 * @param playerNo
	 * @return
	 */
	public int nextPlayer(int playerNo){
		return (playerNo%4+1);
	}
	
	/**
	 * Returns the player's number to the left of the current player.
	 * If a number is passed in the parameter which doesn't represent a
	 * player, return -1.
	 * @param playerNo
	 * @return
	 */
	public int previousPlayer(int playerNo){
		return ((playerNo+2)%4+1);
	}
	
	/**
	 * Verifies if the player has a valid move.
	 * @param player
	 * @return
	 */
	public boolean hasMove(int player){
		return (validPlays(player).length >0);
	}
	
	/**
	 * Gives playing priority to the player to the right.
	 * Simulates that the current player has valid moves,
	 * therefore passes.
	 */
	public void pass(){
		currentPlayer = nextPlayer(currentPlayer);
	}
	
	
	/**
	 * A lock in the game of Domino occurs when no player has any valid 
	 * moves while still having tiles at hand.
	 * @return
	 */
	public boolean isLocked(){
		return (validPlays(1).length ==0 && 
				validPlays(2).length ==0 &&
				validPlays(3).length ==0 && 
				validPlays(4).length ==0);
	}
	
	/**
	 * Match ends whenever a team reaches a total amount of 200 points.
	 * @return
	 */
	public boolean isMatchEnd(){
		return (teamUsScore>=200 || teamThemScore>=200);
	}
	
	/**
	 * If a player has no tiles at hand, the game is over. Otherwise,
	 * verify if a lock has occurred and end the game if it has.
	 * @return
	 */
	public boolean isGameEnded(){
		if (dominoes.getTiles(1).length==0 ||
			dominoes.getTiles(2).length==0 ||
			dominoes.getTiles(3).length==0 ||
			dominoes.getTiles(4).length==0){
			return true;
		}
		else {
			return (isLocked());
		}
	}
	
	/**
	 * Set the current player.
	 */
	public void setCurrentPlayer(int playerNo){
		this.currentPlayer = playerNo;
	}
	
	/** 
	 * Returns the current player.
	 */
	public int getCurrentPlayer(){
		return this.currentPlayer;
	}
	
	/**
	 * Returns the distribution of tiles.
	 */
	public Tiles getTiles(){
		return this.dominoes;
	}
	
	
	
	/**
	 * Utility method - prints the tiles specified.
	 * @param tiles
	 */
	public void printTiles(Domino[] tiles){
		StringBuilder hand = new StringBuilder();
		for(int i=0;i<tiles.length;i++){
			hand.append("index: "+ i + " ("+tiles[i].getHeadValue()+
					    "-"+tiles[i].getTailValue()+")  ");
		}
		System.out.println(hand.toString());
	}
	
	/**
	 * Utility method - print extremities.
	 */
	public void printExtremities(){
		System.out.println("Left Extremity: "+leftExtreme + " | Right Extremity " +
						    rightExtreme);
	}
	
	public void printAllTiles(){
		System.out.println("Player 1:" );
		printTiles(dominoes.getTiles(1));
		System.out.println("Player 2:" );
		printTiles(dominoes.getTiles(2));
		System.out.println("Player 3:" );
		printTiles(dominoes.getTiles(3));
		System.out.println("Player 4:" );
		printTiles(dominoes.getTiles(4));
	}
	
	/**
	 * Utility method - prints the status at each start of Game.
	 */
	public void printGameStatus() {	
		System.out.println("Player Team Score: "+ teamUsScore+"\n"+
		           		   "Computer Team Score: "+teamThemScore+"\n"+
		           		   "Current Player: Player "+startingPlayer);
		
	}
	
	public void printWinningMessage(){
		System.out.println("Player "+ currentPlayer + " won the game!\n" +
						   "Player Team Score: "+ teamUsScore+"\n"+
				           "Computer Team Score: "+teamThemScore+"\n");
	}
	
	public void printMatchWonMessage(){
		if (currentPlayer == 1 || currentPlayer == 3){
			System.out.println("Team Us won "+teamUsScore+ " to "+ teamThemScore);
		}
		else
			System.out.println("Team Them won "+ teamThemScore + " to "+teamUsScore);
	}

	public void printUnplayed() {
		// TODO Auto-generated method stub
		System.out.println(dominoes.getUnplayedCount());
	}
	
}
