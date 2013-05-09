package uprm.ece.icom5015.logic;

/**
 * Represents a single Domino tile. Holds methods to 
 * determine the point value (weight) of the tile, to determine
 * if it is a double, and a fashion to access either the head or tail.
 *
 */
public class Domino {
	
	/* Holds the head value of the tile. */
	private int head;
	/* Holds the tail value of the tile. */
	private int tail;
	/* Holds the boolean value that determines if it is double. */
	private boolean isDouble;
	
	/**
	 * Initializes the Domino. Determines if it is
	 * a double.
	 * @param headValue
	 * @param tailValue
	 */
	public Domino(int headValue, int tailValue){
		this.head = headValue;
		this.tail = tailValue;
		isDouble = (headValue == tailValue);
	}
	
	/**
	 * Returns the value in the upper bound of the tile.
	 * @return
	 */
	public int getHeadValue(){
		return head;
	}
	
	/**
	 * Returns the value in the lower bound of the 
	 * tile.
	 * @return
	 */
	public int getTailValue(){
		return tail;
	}
	
	/**
	 * Returns the total point value assigned to this
	 * tile. Represents the sum of each dot in the tile.
	 * @return
	 */
	public int getWeight(){
		return tail+head;
	}
	
	/**
	 * Returns a boolean representing whether this
	 * tile is a double or not.
	 * @return
	 */
	public boolean isDouble(){
		return isDouble;
	}
	
	/**
	 * Utility method to print Domino into console.
	 */
	public void print(){
		System.out.println("("+head+"|"+tail+")");
	}
	

}
