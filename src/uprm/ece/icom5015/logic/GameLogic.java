package uprm.ece.icom5015.logic;

import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {
	String currentPlayer;
	Domino[] bot, top, left, right;
    ArrayList<Domino> played;
	ArrayList<Domino> unplayed;
	int playerScore, computerScore, head, tail;
	
	@SuppressWarnings("static-access")
	public GameLogic()
	{
		Tiles t = new Tiles();
		bot = t.player1;
		top = t.player2;
		left = t.player3;
		right = t.player4;
		played = t.played;
		unplayed = t.unplayed;
		playerScore = 0;
		computerScore = 0;
		currentPlayer = "bot";
	}
	
	public Domino[] ValidPlays(Domino[] player){
		Domino[] temp = new Domino[7];
		int counter =0;
		if(played.size()==0){
			return player;
		}
		else { for(int i=0;i<player.length; i++){
			if(player[i].getHeadValue()== head || player[i].getHeadValue()== tail || player[i].getTailValue() == head || player[i].getTailValue() == tail ){
				temp[counter++]=player[i];
			}
		}
		Domino[] temp2 = new Domino[counter];
		for(int j=0; j<counter; j++){
			temp2[j]=temp[j];
		}
		return temp2;
		}
	}
	
	public void FirstPlay(Domino p){
		head=p.getHeadValue();
		tail=p.getTailValue();
		played.add(p);
		Remove(p);
		RemoveFromHand(currentPlayer,p);
		currentPlayer = NextPlayer();
	}
	
	public void RemoveFromHand(String player, Domino p){
		int index=0;
		int counter=0;
		if(player=="bot"){
			for(int i=0;i<bot.length;i++){
				if(bot[i].getHeadValue()==p.getHeadValue() && bot[i].getTailValue()==p.getTailValue())
					index=i;
			}
			Domino[] temp = null;
			int size = bot.length-1;
			temp = new Domino[size];
			for(int j=0;j<bot.length;j++){
				if(j != index){
					temp[counter]=bot[j];
					counter++;
				}
			}
			
			bot = temp;
		}
		
		if(player=="right"){
			for(int i=0;i<right.length;i++){
				if(right[i].getHeadValue()==p.getHeadValue() && right[i].getTailValue()==p.getTailValue())
					index=i;
			}
			Domino[] temp = null;
			int size = right.length-1;
			temp = new Domino[size];
			for(int j=0;j<right.length;j++){
				if(j != index){
					temp[counter]=right[j];
					counter++;
				}
			}
			
			right = temp;
		}
		
		if(player=="top"){
			for(int i=0;i<top.length;i++){
				if(top[i].getHeadValue()==p.getHeadValue() && top[i].getTailValue()==p.getTailValue())
					index=i;
			}
			Domino[] temp = null;
			int size = top.length-1;
			temp = new Domino[size];
			for(int j=0;j<top.length;j++){
				if(j != index){
					temp[counter]=top[j];
					counter++;
				}
			}
			
			top = temp;
		}
		
		if(player=="left"){
			for(int i=0;i<left.length;i++){
				if(left[i].getHeadValue()==p.getHeadValue() && left[i].getTailValue()==p.getTailValue())
					index=i;
			}
			Domino[] temp = null;
			int size = left.length-1;
			temp = new Domino[size];
			for(int j=0;j<left.length;j++){
				if(j != index){
					temp[counter]=left[j];
					counter++;
				}
			}
			
			left = temp;
		}
	}
	
	public String NextPlayer(){
		if(currentPlayer=="bot")
			return "right";
		else if(currentPlayer=="right")
			return "top";
		else if(currentPlayer=="top")
			return "left";
		else return "bot";
	}
	
	public void Play(Domino p){
		if(p.isDouble()){
			played.add(p);
			Remove(p);
			RemoveFromHand(currentPlayer,p);
			currentPlayer = NextPlayer();
			
		}
		else if(p.getHeadValue()==head){
				this.head=p.getTailValue();
				played.add(p);
				Remove(p);
				RemoveFromHand(currentPlayer,p);
				currentPlayer = NextPlayer();
				}
			else if(p.getTailValue()==head){
				this.head=p.getHeadValue();
				played.add(p);
				Remove(p);
				RemoveFromHand(currentPlayer,p);
				currentPlayer = NextPlayer();
				}
								
			
			else if(p.getHeadValue()==tail){
					this.tail=p.getTailValue();
					played.add(p);
					Remove(p);
					RemoveFromHand(currentPlayer,p);
					currentPlayer = NextPlayer();
					}
				else{
					this.tail=p.getHeadValue();
					played.add(p);
					Remove(p);
					RemoveFromHand(currentPlayer,p);
					currentPlayer = NextPlayer();
					}
									
			
		
	}
	
	
	private void Remove(Domino p) {
		int index =unplayed.lastIndexOf(p);
		unplayed.remove(index);
		
	}

	/*public boolean checkForTie(){

	}*/
	public boolean Win(Domino[] a, Domino[] b, Domino[] c, String team){
		int score = 0;
		for(int i=0;i<a.length;i++)
			score += a[i].getWeight();
		for(int j=0;j<b.length;j++)
			score += b[j].getWeight();
		for(int x=0;x<c.length;x++)
			score += c[x].getWeight();
		if(team=="human"){
			playerScore += score;
		}
		
		if(team=="computer"){
			computerScore += score;
		}
		return GameEnd();
		
	}

	public boolean GameEnd() {
		if(playerScore>=200 || computerScore>=200)
			return true;
		return false;
		
	}
	
	public void printTiles(Domino[] tiles){
		for(int i=0;i<tiles.length;i++){
			System.out.print("index: "+i+" ");
			tiles[i].print();
		}
	}

	public void printGameStatus() {
		System.out.println("Player Team Score: "+playerScore);
		System.out.println("Computer Team Score: "+computerScore);
		System.out.println("head value: "+head);
		System.out.println("tail value: "+tail);
		System.out.println("Bottom Player Tiles: ");
		printTiles(bot);
		System.out.println("Top Player Tiles: ");
		printTiles(top);
		System.out.println("Left Player Tiles: ");
		printTiles(left);
		System.out.println("Right Player Tiles: ");
		printTiles(right);
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameLogic testing =new GameLogic();
		testing.printGameStatus();
		System.out.println("Valid Plays for Bottom Player: ");
		testing.printTiles(testing.ValidPlays(testing.bot));
		System.out.println("Enter your play Bottom Player: ");
	    Scanner scanner = new Scanner(System.in);
	    int chosenIndex = scanner.nextInt();
	    Domino chosen = testing.bot[chosenIndex];
	    testing.FirstPlay(chosen);
	    testing.printGameStatus();
	    System.out.println("current player: "+testing.currentPlayer);
	    System.out.println("Valid Plays for right Player: ");
		testing.printTiles(testing.ValidPlays(testing.right));
	    System.out.println("Enter your play right Player: ");
	    chosenIndex = scanner.nextInt();
	    //System.out.println("head or tail? ");
	   // String position = scanner.next();
	    chosen = testing.ValidPlays(testing.right)[chosenIndex];
	    testing.Play(chosen);
	    testing.printGameStatus();

	}


}
