package uprm.ece.icom5015.logic;

import java.util.Random;

public class Dice {
    
	private int sides;

    /**
     * Default Constructor
     * @param s number of sides the die will have
     */
    public Dice(int s){
       sides = s;
    }
    
	/**
	 * roll method depending on sides up there
	 * 
	 * @return the side that landed up
	 */
    public int roll(){
    	Random r =new Random();
    	return r.nextInt(sides);
    }
}

