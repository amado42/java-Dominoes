package uprm.ece.icom5015.logic;

import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {
	String currentPlayer,Starts;
	Domino[] bot, top, left, right;
	ArrayList<Domino> played;
	ArrayList<Domino> unplayed;
	int playerScore, computerScore, head, tail;
	boolean lock;
	
	public GameLogic()
	{
		lock = false;
		playerScore = 0;
		computerScore = 0;
		currentPlayer = FirstGame();
		
	}
	
	public Domino[] currentHand(){
		if(currentPlayer.equals("bot"))
			return bot;
		if(currentPlayer.equals("top"))
			return top;
		if(currentPlayer.equals("left"))
			return left;
		else return right;
	}
	@SuppressWarnings("static-access")
	public void NewGame(String winner){
		Tiles t = new Tiles();
		bot = t.player1;
		top = t.player2;
		left = t.player3;
		right = t.player4;
		played.clear(); 
		unplayed.clear();
		played = t.played;
		unplayed = t.unplayed;
		currentPlayer = winner;
		lock=false;
		
	}
	@SuppressWarnings("static-access")
	public String FirstGame(){
		Tiles t = new Tiles();
		bot = t.player1;
		top = t.player2;
		left = t.player3;
		right = t.player4;
		played = t.played;
		unplayed = t.unplayed;
		String doubleSix = null;
		for(int i=0;i<7;i++){
			if(bot[i].getHeadValue() ==6 && bot[i].getTailValue()==6){
				doubleSix = "bot";
				break;
			}
			if(top[i].getHeadValue() ==6 && top[i].getTailValue()==6){
				doubleSix = "top";
				break;
			}
			if(left[i].getHeadValue() ==6 && left[i].getTailValue()==6){
				doubleSix = "left";
				break;
			}
			if(right[i].getHeadValue() ==6 && right[i].getTailValue()==6){
				doubleSix = "right";
				break;
			}
		}
		setStarts(doubleSix);
		return doubleSix;
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
			
			setBot(temp);
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
			
			setRight(temp);
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
			
			setTop(temp);
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
			
			setLeft(temp);
		}
	}
	
	public String LastPlayer(){
		if(currentPlayer.equals("bot"))
			return "left";
		else if(currentPlayer.equals("right"))
			return "bot";
		else if(currentPlayer.equals("top"))
			return "right";
		else return "top";
	}
	
	public String NextPlayer(){
		if(currentPlayer.equals("bot"))
			return "right";
		else if(currentPlayer.equals("right"))
			return "top";
		else if(currentPlayer.equals("top"))
			return "left";
		else return "bot";
	}
	
	public void Play(Domino p){
		int curHead = p.getHeadValue();
		int curTail = p.getTailValue();
		Scanner scanner = new Scanner(System.in);
		int chosenPlay;
		if(p.isDouble()){
			played.add(p);
			Remove(p);
			RemoveFromHand(currentPlayer,p);
			currentPlayer = NextPlayer();
			
		}
		else if((curHead == head && curTail == tail) || (curHead == tail && curTail == head)){
			System.out.println("Where do you wish to play your tile? ");
			System.out.println(" 0. Head ");
			System.out.println(" 1. Tail ");
			chosenPlay = scanner.nextInt();
			if(chosenPlay ==0){
				
				if(curHead==head){
					setHead(curTail);
					played.add(p);
					Remove(p);
					RemoveFromHand(currentPlayer,p);
					currentPlayer = NextPlayer();
					}
				else {
					setHead(curHead);
					played.add(p);
					Remove(p);
					RemoveFromHand(currentPlayer,p);
					currentPlayer = NextPlayer();
					}
			}
			if(chosenPlay ==1){
				
				if(curHead==tail){
					setTail(curTail);
					played.add(p);
					Remove(p);
					RemoveFromHand(currentPlayer,p);
					currentPlayer = NextPlayer();
					}
				else{
					setTail(curHead);
					played.add(p);
					Remove(p);
					RemoveFromHand(currentPlayer,p);
					currentPlayer = NextPlayer();
					}
			}
		}
		
			else if(curHead==head){
			setHead(curTail);
			played.add(p);
			Remove(p);
			RemoveFromHand(currentPlayer,p);
			currentPlayer = NextPlayer();
			}
			else if(curTail==head){
				setHead(curHead);
				played.add(p);
				Remove(p);
				RemoveFromHand(currentPlayer,p);
				currentPlayer = NextPlayer();
				}
								
			
			else if(curHead==tail){
					setTail(curTail);
					played.add(p);
					Remove(p);
					RemoveFromHand(currentPlayer,p);
					currentPlayer = NextPlayer();
					}
				else{
					setTail(curHead);
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
	public void Win(){
		Domino[] a = null;
		Domino[] b = null;
		Domino[] c = null;
		int score = 0;
		if(currentPlayer.equals("bot")){
			 a = top;
			 b = left;
			 c = right;	
		}
		if(currentPlayer.equals("top")){
			 a = bot;
			 b = left;
			 c = right;	
			}
		if(currentPlayer.equals("left")){
			 a = top;
			 b = bot;
			 c = right;	
			}
		if(currentPlayer.equals("right")){
			 a = top;
			 b = left;
			 c = bot;	
			}
			
		for(int i=0;i<a.length;i++)
			score += a[i].getWeight();
		for(int j=0;j<b.length;j++)
			score += b[j].getWeight();
		for(int x=0;x<c.length;x++)
			score += c[x].getWeight();
		if(currentPlayer.equals("top") || currentPlayer.equals("bot") ){
			playerScore += score;
		}
		
		if(currentPlayer.equals("left") || currentPlayer.equals("right") ){
			computerScore += score;
		}
	
		
	}

	public boolean MatchEnd() {
		if(playerScore>=200 || computerScore>=200)
			return true;
		return false;
		
	}
	
	public boolean GameEnd(){
		if(bot.length == 0 || top.length == 0 || left.length == 0 || right.length == 0){
			return true;}
		if(lock)
			return true;
		else return false;
		}
	
	public void CheckForLock(){
		if(ValidPlays(bot).length == 0 && ValidPlays(bot).length == 0 && ValidPlays(bot).length == 0 && ValidPlays(bot).length == 0)
			setLock(true);
	}
	
	public int PlayerWeight(Domino[] d){
		int temp=0;
		for(int i=0;i<d.length;i++){
			temp += d[i].getWeight();
		}
		return temp;
	}
	
	public String StartingPlayer(){
		
		if(lock){
		int we = PlayerWeight(bot)+PlayerWeight(top);
	    int them = PlayerWeight(left)+PlayerWeight(right);
	    if(we<them){
	    	setComputerScore(computerScore+we+them);
	    	if(currentPlayer.equals("left") || currentPlayer.equals("right")){
	    		return currentPlayer;
	    	}
	    	else if(currentPlayer.equals("top")){
	    		return "left";
	    	}
	    	else return "right";
	    }
		if(we>them){
			setPlayerScore(playerScore+we+them);
			if(currentPlayer.equals("top") || currentPlayer.equals("bot")){
	    		return currentPlayer;
	    	}
	    	else if(currentPlayer.equals("left")){
	    		return "bot";
	    	}
	    	else return "top";
		}
		else{
			return Starts;
		}
		}
		Win();
		return currentPlayer;
		
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
//		System.out.println("Bottom Player Tiles: ");
//		printTiles(bot);
//		System.out.println("Top Player Tiles: ");
//		printTiles(top);
//		System.out.println("Left Player Tiles: ");
//		printTiles(left);
//		System.out.println("Right Player Tiles: ");
//		printTiles(right);
		
	}
	/**
	 * @param args
	 */
	
    public String getCurrentPlayer() {
		return currentPlayer;
	}

	public Domino[] getBot() {
		return bot;
	}

	public Domino[] getTop() {
		return top;
	}

	public Domino[] getLeft() {
		return left;
	}

	public Domino[] getRight() {
		return right;
	}

	public ArrayList<Domino> getPlayed() {
		return played;
	}

	public ArrayList<Domino> getUnplayed() {
		return unplayed;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public int getComputerScore() {
		return computerScore;
	}

	public int getHead() {
		return head;
	}

	public int getTail() {
		return tail;
	}

	public boolean isLock() {
		return lock;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setBot(Domino[] bot) {
		this.bot = bot;
	}

	public void setTop(Domino[] top) {
		this.top = top;
	}

	public void setLeft(Domino[] left) {
		this.left = left;
	}

	public void setRight(Domino[] right) {
		this.right = right;
	}

	public void setPlayed(ArrayList<Domino> played) {
		this.played = played;
	}

	public void setUnplayed(ArrayList<Domino> unplayed) {
		this.unplayed = unplayed;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public void setComputerScore(int computerScore) {
		this.computerScore = computerScore;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public void setTail(int tail) {
		this.tail = tail;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public String getStarts() {
		return Starts;
	}

	public void setStarts(String starts) {
		Starts = starts;
	}

}
