package uprm.ece.icom5015.logic;

import java.util.ArrayList;
import java.util.Random;

public class Tiles {
	
	static ArrayList<Domino> played = null;
	static ArrayList<Domino> unplayed = null;
	static ArrayList<Domino> undrawn  = null;
	static Domino[] player1, player2, 
				    player3, player4 = null;
	
		
	public Tiles(){
		played   = new ArrayList<Domino>();
		unplayed = new ArrayList<Domino>();
		undrawn  = new ArrayList<Domino>();
		
		for(int i=0; i<=6; i++){
			for (int j=i ; j<=6; j++){
				unplayed.add(new Domino(i,j));
				//System.out.println("Added Domino to collection: "+i+"-"+j);
			}
		}
		
		player1  = new Domino[7];
		player2  = new Domino[7];
		player3  = new Domino[7];
		player4  = new Domino[7];
		newHand();
	}
	
	/**
	 * Reset the game
	 */
	@SuppressWarnings("unchecked")
	static private void newHand(){
		while (!played.isEmpty()){
			unplayed.add(played.remove(0));
		}
		undrawn = (ArrayList<Domino>) unplayed.clone();
		
		int iteration = 0;
		Random r = new Random();
		
		while(!undrawn.isEmpty()){
			player1[iteration] = undrawn.remove(r.nextInt(undrawn.size()));
			player2[iteration] = undrawn.remove(r.nextInt(undrawn.size()));
			player3[iteration] = undrawn.remove(r.nextInt(undrawn.size()));
			player4[iteration] = undrawn.remove(r.nextInt(undrawn.size()));
			
			iteration++;
			
		}	
	}
	
	public Domino[] getPlayerTiles(){
		return player1;
	}
	
	static private void print(){
		System.out.println("Player 1: " +
				player1[0].getHeadValue()+"-"+player1[0].getTailValue()+", "+
				player1[1].getHeadValue()+"-"+player1[1].getTailValue()+", "+
				player1[2].getHeadValue()+"-"+player1[2].getTailValue()+", "+
				player1[3].getHeadValue()+"-"+player1[3].getTailValue()+", "+
				player1[4].getHeadValue()+"-"+player1[4].getTailValue()+", "+
				player1[5].getHeadValue()+"-"+player1[5].getTailValue()+", "+
				player1[6].getHeadValue()+"-"+player1[6].getTailValue());
		System.out.println("Player 2: " +
				player2[0].getHeadValue()+"-"+player2[0].getTailValue()+", "+
				player2[1].getHeadValue()+"-"+player2[1].getTailValue()+", "+
				player2[2].getHeadValue()+"-"+player2[2].getTailValue()+", "+
				player2[3].getHeadValue()+"-"+player2[3].getTailValue()+", "+
				player2[4].getHeadValue()+"-"+player2[4].getTailValue()+", "+
				player2[5].getHeadValue()+"-"+player2[5].getTailValue()+", "+
				player2[6].getHeadValue()+"-"+player2[6].getTailValue());
		System.out.println("Player 3: " +
				player3[0].getHeadValue()+"-"+player3[0].getTailValue()+", "+
				player3[1].getHeadValue()+"-"+player3[1].getTailValue()+", "+
				player3[2].getHeadValue()+"-"+player3[2].getTailValue()+", "+
				player3[3].getHeadValue()+"-"+player3[3].getTailValue()+", "+
				player3[4].getHeadValue()+"-"+player3[4].getTailValue()+", "+
				player3[5].getHeadValue()+"-"+player3[5].getTailValue()+", "+
				player3[6].getHeadValue()+"-"+player3[6].getTailValue());
		System.out.println("Player 4: " +
				player4[0].getHeadValue()+"-"+player4[0].getTailValue()+", "+
				player4[1].getHeadValue()+"-"+player4[1].getTailValue()+", "+
				player4[2].getHeadValue()+"-"+player4[2].getTailValue()+", "+
				player4[3].getHeadValue()+"-"+player4[3].getTailValue()+", "+
				player4[4].getHeadValue()+"-"+player4[4].getTailValue()+", "+
				player4[5].getHeadValue()+"-"+player4[5].getTailValue()+", "+
				player4[6].getHeadValue()+"-"+player4[6].getTailValue());
	}
	
	
	
	
	
//	public static void main(String[] args){
//		new Tiles();
//		print();
//	}
}

