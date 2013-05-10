package uprm.ece.icom5015.util;

import uprm.ece.icom5015.logic.Domino;

public class DominoPlayTuple {
	
	private Domino domino;
	private int location;
	
	public DominoPlayTuple(Domino domino, int location){
		this.domino = domino;
		this.location = location;
	}
	
	public Domino getDomino(){
		return domino;
	}
	
	public int getLocation(){
		return location;
	}

}
