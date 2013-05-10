package uprm.ece.icom5015.ai;

import java.util.ArrayList;
import java.util.HashMap;

import uprm.ece.icom5015.logic.Domino;
import uprm.ece.icom5015.logic.Logic;


/**
 * Artificial Inteligence serves as a super class for any algorithm
 * that will be implemented for the dominoes game. Therefore, this class
 * holds all methods that will be necessary for each algorithm instance.
 * 
 * For predicting algorithms:
 * There are a total of 28 dominoes, meaning that with no information the 
 * probability a player has any X tile is 1/4. The following considerations 
 * are made:
 * 1   = sum(Pnd) from n=1 to N; For any given domino the probability it
 * 								 belongs to player is 1.
 * D^n = sum(Pnd) from d=1 to D; The sum of the probabilities that they own
 * 								 dominoes is equal to the dominoes they own.
 * Where D is the total dominoes left.
 * D^n is the total number of dominoes owned by player n.
 * d represents a Domino.
 * n represents a player.
 * Pnd represents a probability betwen 0 to 1 inclusive that player n owns
 * domino d.
 * N will always be 4 - as there are 4 players.
 *
 */
public abstract class ArtificialIntelligence {


	/*
	 * Logic for this Domino Match
	 */
	protected Logic logic;

	/*
	 * The dominoes owned by the computer player.
	 */
	protected ArrayList<Domino> dominos;


	/*
	 * HashMaps contain probability players owns a domino.
	 * The probabilities can
	 * be calculated in different ways.
	 */
	protected HashMap<Domino, Double>[] dominoProbabilityMap;

	/*
	 * The dominos definitely owned by a player.
	 */
	protected ArrayList<Domino>[] dominoOwn;

	/*
	 * The dominos definitely not owned by a player.
	 */
	protected ArrayList<Domino>[] dominoNotOwned;

	/*
	 * The number of dominos remaining for each opponent.
	 */
	protected int[] numberDominosLeft;

	/*
	 * True if the probabilities are not being updated, false otherwise.
	 */
	protected boolean probabilityFlag;

	/*
	 * The number of the player.
	 */
	private int playerNo;

	/*
	 * The number of moves that have been made.
	 */
	private int numberTurns;

	/*
	 * Determines if a four person cycle has been completed.
	 */
	protected boolean roundOver;


	/**
	 * Basic Constructor -
	 * 
	 * @param logic
	 * @param distribution
	 * @param playerNo
	 */
	public ArtificialIntelligence(final Logic logic, final Domino[] distribution, 
			final int playerNo){

		this.logic = logic;
		this.playerNo = playerNo;
		for (Domino tile : distribution){
			dominos.add(tile);
		}
		dominoProbabilityMap = new HashMap[4];
		dominoNotOwned = new ArrayList[4];
		dominoOwn = new ArrayList[4];
		numberDominosLeft = new int[] {7,7,7,7};
		roundOver = false;
		numberTurns=0;
	}


	/**
	 * Weight how much importance to give into making an opponent
	 * miss. More than 1 gives priority to left player, less than 
	 * 1 to the right
	 */

	public void setLeftOpponentWeight(final double weight){}



	/**
	 * Deduct a Domino from the players rack whenever he makes a 
	 * play.
	 * @param playerNo
	 */
	protected void updateDistribution(final int playerNo){
		numberDominosLeft[playerNo -1]--;
	}

	/**
	 * Increase number of turns whenever a player lays down a tile.
	 */
	protected void updateNumberTurns() {
		numberTurns++;
	}

	/**
	 * Get the amount of total cycles (round rotation of players) that 
	 * have occured in current game.
	 * 
	 */
	protected int getRotationNumber() {
		return (numberTurns/4)+1;

	}

	/**
	 *Updates the ArrayList of definitely owned and not 
	 *owned dominoes. There are two ways that a domino is 
	 *added to the definitely owned list: the rest of the players
	 *do not own the domino or if it is a domino owned by the player
	 *in perspective. That is, player 1 can safely assume that all
	 *tiles in his hand are not owned by players 2-4.
	 *
	 */

	protected void updateDefiniteLists(){
		//If 3 players do not own the domino, then the other
		//player must own it
		for(int i=0; i< dominoNotOwned.length; i++){
			for (Domino unownedDomino : dominoNotOwned[i]){
				int notOwnCounter = 0;
				int playerNotToOwn = 0;
				for (int j =0; j<dominoNotOwned.length;j++){
					//Already on a players own list, so do not add.
					if(dominoOwn[j].contains(unownedDomino)){
						notOwnCounter =0;
						break;
					}
					if(dominoNotOwned[j].contains(unownedDomino)){
						notOwnCounter++;
					}
					else{
						playerNotToOwn = j;
					}
				}
				if (notOwnCounter == 3){
					dominoOwn[playerNotToOwn].add(unownedDomino);
					dominoProbabilityMap[playerNotToOwn].put(unownedDomino, 1.0);
				}
			}
		}
		//A player owns all of their dominos.
		for(int i=0;i<dominoNotOwned.length;i++){
			if(dominoProbabilityMap[i].size()-dominoNotOwned[i].size() ==
					numberDominosLeft[i]){
				for(Domino eachDomino : dominoProbabilityMap[i].keySet()){
					if (!dominoNotOwned[i].contains(eachDomino) && 
							!dominoOwn[i].contains(eachDomino)){
						for(int j=0; j<dominoOwn.length;j++){
							if(i==j){
								dominoOwn[j].add(eachDomino);
								dominoProbabilityMap[j].put(eachDomino, 1.0);
							}
							else if(!dominoNotOwned[j].contains(eachDomino)){
								dominoNotOwned[j].add(eachDomino);
								dominoProbabilityMap[j].put(eachDomino, 0.0);
							}
						}
					}
				}		
			}
		}
	}


	/**
	 * Initializes the probability HashMap so that all owned dominos have
	 * a probability of 0.0. The owned and not owned list must be updated
	 * as each move progresses.
	 */

	protected void initiateProbabilityMap(final Domino[] ownedDominos){
		dominoNotOwned[playerNo-1] = new ArrayList<Domino>();
		dominoOwn[playerNo-1] = new ArrayList<Domino>();
		dominoProbabilityMap[playerNo-1] = new HashMap<Domino, Double>(28);
		Domino domino;
		for(int i=0; i<=6; i++){
			for (int j=i ; j<=6; j++){
				domino = new Domino(i,j);
				dominoProbabilityMap[playerNo-1].put(domino,0.0);
				dominoNotOwned[playerNo-1].add(domino);
			}
		}
		for(Domino ownedDomino : ownedDominos){
			dominoOwn[playerNo-1].add(ownedDomino);
			dominoNotOwned[playerNo-1].remove((Domino) ownedDomino);
			dominoProbabilityMap[playerNo-1].put(ownedDomino, 1.0);
		}

		for( int i=0; i<dominoNotOwned.length;i++){
			if(i!=playerNo-1){
				dominoNotOwned[i]=new ArrayList<Domino>();
				dominoOwn[i] = new ArrayList<Domino>();
				dominoProbabilityMap[i]=new HashMap<Domino, Double>(28);
				for(Domino ownedDomino : ownedDominos){
					dominoNotOwned[i].add(ownedDomino);
				}

				for(int j=0; j<=6; j++){
					for (int k=i ; k<=6; j++){
						dominoProbabilityMap[i].put(new Domino(j,k), 0.0);
					}
				}
			}
		}
	}

	/**
	 * Returns the total weight of the player's hand.
	 */



	/**
	 * Returns the distribution flag, this says
	 * whether the artificial intelligence has finished
	 * thinking.
	 */
	protected boolean isDistributionFlag() {
		return probabilityFlag;
	}
	/**
	 * Returns the probabilities that a player
	 * owns a certain domino. 
	 */
	protected
	HashMap<Domino, Double>[] getDominoDistribution()
	{
		return dominoProbabilityMap;
	}
	/**
	 * Returns the player number.
	 */
	protected int getPlayerNo(){
		return playerNo;
	}

	/**
	 * Returns an array of dominos left for each
	 * player.
	 * @return
	 */
	protected int[] getNumberDominosLeft(){
		return numberDominosLeft;
	}


	//	public static final ArtificialIntelligence getAI(final int aiID, final int playerNo){
	//	switch(aiID){
	//case VERY_EASY : return new NotSmartAI(dominoes, playerNo);
	//	case EASY      : return new StandardAI(dominoes, playerNo);
	//	case NORMAL    : return new ConservativeAI(dominoes, playerNo);
	//	case HARD      : return new PartnerConsciousAI(dominoes, playerNo);
	//	case VERY_HARD : return new AggressiveAI(dominoes, playerNo);
	//		}
	//	return new ConservativeAI(dominoes, playerNo);
	//	}


}
