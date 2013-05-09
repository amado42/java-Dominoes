package uprm.ece.icom5015.logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the 28 tiles in the classic game of Dominos.
 * The constructor initializes all Domino tiles and players.
 * Each hand can be randomly distributed to each player through the
 * newHand method.
 *
 */
public class Tiles {

	/* Represents tiles that have been played.                    */
	ArrayList<Domino> played = null;
	/* Represents tiles that have not been played.                */
	ArrayList<Domino> unplayed = null;
	/* Represents tiles that have not been assigned to
	 * a player. Is reduced to 0 when all 28 tiles are drawn.     */
	ArrayList<Domino> undrawn  = null;
	/* Represents the hand of each player. Initialized at 7 tiles */
	static ArrayList<Domino> player1, player2, 
	player3, player4 = null;

	/**
	 * Basic constructor. Initializes all 28 tiles, the four players,
	 * and the status lists that represent whether tiles are played or
	 * not.	
	 */
	public Tiles(){
		/* Initialize status lists */
		played   = new ArrayList<Domino>();
		unplayed = new ArrayList<Domino>();
		undrawn  = new ArrayList<Domino>();

		/* Initialize all 28 Domino tiles. */
		for(int i=0; i<=6; i++){
			for (int j=i ; j<=6; j++){
				unplayed.add(new Domino(i,j));
			}
		}
		/* Initialize all player hands to hold 7 dominos. */
		player1  = new ArrayList<Domino>();
		player2  = new ArrayList<Domino>();
		player3  = new ArrayList<Domino>();
		player4  = new ArrayList<Domino>();

		/* Distribute all tiles to the four players. */
		newHand();
	}

	/**
	 * Reset the game by redistributing the tiles to each player.
	 * As is in the classic Domino game, each player is assigned a total
	 * of 7 Domino tiles.
	 */
	@SuppressWarnings("unchecked")
	public void newHand(){
		/* Bring all played tiles back to the unplayed list. */
		while (!played.isEmpty()){
			unplayed.add(played.remove(0));
		}
		/* All 28 tiles are unplayed and not distributed. Thus
		 * the undrawn list should also represent the 28 tiles.
		 */
		undrawn = (ArrayList<Domino>) unplayed.clone();

		
		Random r = new Random();

		/* Pseudo-randomly distribute each undrawn tile to a player. Do 
		 * until all tiles are distributed.
		 */
		while(!undrawn.isEmpty()){
			player1.add(undrawn.remove(r.nextInt(undrawn.size())));
			player2.add(undrawn.remove(r.nextInt(undrawn.size())));
			player3.add(undrawn.remove(r.nextInt(undrawn.size())));
			player4.add(undrawn.remove(r.nextInt(undrawn.size())));


		}	
	}


	/**
	 * Gets the hand of the given player. Currently, the 
	 * human is player 1; all other players are computers.
	 * @param playerNo
	 * @return
	 */
	public Domino[] getTiles(final int playerNo){
		switch(playerNo){
		case 1 : return (Domino[])player1.toArray(new Domino[player1.size()]);
		
		case 2 : return (Domino[])player2.toArray(new Domino[player2.size()]);
		
		case 3 : return (Domino[])player3.toArray(new Domino[player3.size()]);
		
		case 4 : return (Domino[])player4.toArray(new Domino[player4.size()]);
		}
		return null;

	}
	
	/**
	 * Returns the list of played domino tiles.
	 * @return
	 */
	public ArrayList<Domino> getPlayedTiles(){
		return this.played;
	}
	
	/**
	 * Returns the list of unplayed domino tiles.
	 * @return
	 */
	public ArrayList<Domino> getUnplayedTiles(){
		return this.unplayed;
	}

	/**
	 * Removes the given tile from the payer's list.
	 * @param player
	 * @param tile
	 */
	public void remove(int player, Domino tile){
		switch(player){
		case 1: player1.remove(tile);
		case 2: player2.remove(tile);
		case 3: player3.remove(tile);
		case 4: player4.remove(tile);
		}
	}
	
	/**
	 * Returns the total weight of the specified collection 
	 * of tiles.
	 * @param tiles
	 * @return
	 */
	public int handWeight(Domino[] tiles){
		int count = 0 ;
		for (Domino tile : tiles){
			count += tile.getWeight();
		}
		return count;
	}
	
	/**
	 * Returns the total weight in the remaining tiles of the 
	 * team denoted as Us : player 1 and 3.
	 * @return
	 */
	public int teamUsWeight(){
		return handWeight(getTiles(1))+handWeight(getTiles(3));
	}
	
	/**
	 * Returns the total weight in the remaining tiles of the 
	 * team denoted as Them : player 2 and 4.
	 * @return
	 */
	public int teamThemWeight(){
		return handWeight(getTiles(2))+handWeight(getTiles(4));
	}
	
	
	/**
	 * Returns the total board count. Used to determine when 
	 * to force a lock.
	 * @return
	 */
	public int getBoardCount(){
		int count = 0;
		for(Domino tile : played){
			count+=tile.getWeight();
		}
		return count;
	}
	
	/**
	 * Returns the total count of tiles in all player hands.
	 */
	public int getUnplayedCount(){
		int count = 0;
		for(Domino tile : unplayed){
			count+=tile.getWeight();
		}
		return count;
	}

	/** 
	 * Deprecated Method - Returns player's hand.
	 * @return
	 */
	@Deprecated
	public Domino[] getPlayerTiles(){
		return (Domino[])player1.toArray(new Domino[player1.size()]);
	}

	


}

