package uprm.ece.icom5015.util;


import uprm.ece.icom5015.logic.Domino;

/**
 * Utility method that holds a Domino and a Value. This
 * is utilized to facilitate the diversity algorithm sorting.
 *
 */
public class DominoValueTuple implements Comparable<DominoValueTuple>{

	private Domino domino;
	private int leftValue;
	private int rightValue;
	
	public DominoValueTuple(Domino domino, String value){
		this.domino = domino;
		leftValue  = Integer.parseInt(value.split("-")[0]);
		rightValue = Integer.parseInt(value.split("-")[1]);
	}
	
	public int getLeftValue(){
		return leftValue;
	}
	
	public int getRightValue(){
		return rightValue;
	}
	
	public Domino getDomino(){
		return domino;
	}

	@Override
	public int compareTo(DominoValueTuple duple) {
		if (rightValue == duple.getRightValue()){
			return duple.getLeftValue()-leftValue;
		}
		return duple.getRightValue()-rightValue ;
	}

}

