package uprm.ece.icom5015.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import uprm.ece.icom5015.logic.Domino;
import uprm.ece.icom5015.logic.Logic;
import uprm.ece.icom5015.util.DominoPlayTuple;
import uprm.ece.icom5015.util.DominoValueTuple;

public class DiversityAI {
	
	/* Chosen value to weight the importance of a double. */
	private static final int DOUBLE_WEIGHT = 8;
	/* Chosen location to set a tile. */
	private static final int LOCATION = 0;
	/* List representing number values 0-6 */
	private HashMap<Integer, Integer> occurrence;
	/* List representing values determined for each tile. */
	private ArrayList<DominoValueTuple> diversityValue;
	

	public DiversityAI(Domino[] distribution) {

		occurrence = new HashMap<Integer, Integer>();
		diversityValue = new ArrayList<DominoValueTuple>();
		resetOccurrences();
		diversity(distribution);
		
	}
	
	/**
	 * Resets the occurrence list each time a new hand is made.
	 */
	public void resetOccurrences(){
		for (int i = 0; i<7; i++){
			occurrence.put(i,0);
		}
	}

	/**
	 * This algorithm is based on rational ordering and playing upon diversity.
	 * First, it counts the number of each type of domino that the player has. 
	 * Each double is counted only once in this process, resulting in a list that 
	 * guides how important a tile is with respect to others.
	 *  
	 * Next, it evaluates the values assigned to each domino based on occurrence;
	 * creating a tuple that contains the Domino object and a String representing
	 * the tile's head value occurrence + "-" + the tile's tail value. In the case 
	 * of a double, the tail is attributed a value of 8 (making it appear with
	 *  more priority than a regular piece). 
	 *  
	 *  Finally, this list is sorted using each domino's tail value (where the
	 *  double factor is calculated). In the case that there is a tie, the list is
	 *  sorted regarding the head's occurence. These comparisons work because the 
	 *  algorithm is concerned with avoiding laying a domino if its the last of its
	 *  type in his hand.
	 */
	
	public void diversity(Domino[] hand){
		resetOccurrences();
		diversityValue.clear();
		/* For each domino in the hand, count how many tiles of each number there are. */
		for (Domino tile : hand){
			occurrence.put(tile.getHeadValue(), occurrence.get(tile.getHeadValue())+1);
			if(tile.getHeadValue()!=tile.getTailValue())
				occurrence.put(tile.getHeadValue(), occurrence.get(tile.getHeadValue())+1);
		}
		
		/* 
		 * For each domino in the hand use the occurence list to attribute a weight
		 * based on the amount of tiles that share a number. If the tile is a double,
		 * then attribute the tail value a value of (1+dominoNumbers) =8. 
		 */
		for (Domino tile : hand){
			if (tile.getHeadValue() == tile.getTailValue()){
				diversityValue.add(new DominoValueTuple(tile, 
						occurrence.get(tile.getHeadValue())+"-"+DOUBLE_WEIGHT));
						
			}
			else{
				diversityValue.add(new DominoValueTuple(tile,
						occurrence.get(tile.getHeadValue())+"-"+
						occurrence.get(tile.getTailValue())));
			}
		}
		/* Sort the diversity list and return the top result. */
		Collections.sort(diversityValue);
	}
	
	public DominoPlayTuple choosePlay(Logic match){
		Domino bestOption = match.validPlays(match.getCurrentPlayer())[0];
		diversity(match.getTiles().getTiles(match.getCurrentPlayer()));
		int location;
		for (Domino tile : match.validPlays(match.getCurrentPlayer())){
			if (diversityValue.indexOf(tile)<diversityValue.indexOf(bestOption))
				bestOption = tile;
		}
		if(match.hasThrowingOption(bestOption)){
			location = LOCATION;
		}
		else{
			if (match.canThrowLeft(bestOption))
				location = 0;
			else
				location = 1;
		}
		return new DominoPlayTuple(bestOption, location);
	}
}
