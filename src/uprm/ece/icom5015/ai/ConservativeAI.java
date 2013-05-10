package uprm.ece.icom5015.ai;

import uprm.ece.icom5015.logic.Domino;
import uprm.ece.icom5015.logic.Logic;
import uprm.ece.icom5015.util.DominoPlayTuple;

/**
 * This Artificial Intelligence is concerned with getting the tiles
 * that have the greatest weight in the hand. Therefore it will chose
 * the highest valued tile in the hand's valid moves.
 *
 */
public class ConservativeAI {
	
	public static final int LOCATION = 0;
	
	public ConservativeAI(){};
	
	/**
	 * This algorithm attempts to minimize the effects of losing by 
	 * getting rid of high valued tiles as soon as possible. This will
	 * lower the opponent's victory points in case of a loss.
	 * @param match
	 * @return
	 */
	public DominoPlayTuple choosePlay(Logic match){
		Domino bestOption = match.validPlays(match.getCurrentPlayer())[0];
		int location;
		
		for (Domino tile : match.validPlays(match.getCurrentPlayer())){
			if (tile.getWeight()>bestOption.getWeight())
				bestOption=tile;
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
