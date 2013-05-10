package uprm.ece.icom5015.ai;

import uprm.ece.icom5015.logic.Domino;
import uprm.ece.icom5015.logic.Logic;
import uprm.ece.icom5015.util.DominoPlayTuple;

public class RandomAI {
	
	private static final int LOCATION = 0;
	
	public RandomAI(){}
	
	public DominoPlayTuple choosePlay(Logic match){
		Domino bestOption = match.validPlays(match.getCurrentPlayer())[0];
		int location;
		
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
