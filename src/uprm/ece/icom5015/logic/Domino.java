package uprm.ece.icom5015.logic;

public class Domino {
	
	private int head;
	private int tail;
	
	public Domino(int headValue, int tailValue){
		this.head = headValue;
		this.tail = tailValue;
	}
	
	public int getHeadValue(){
		return head;
	}
	
	public int getTailValue(){
		return tail;
	}
	
	public int getWeight(){
		return tail+head;
	}
	
	
	

}
