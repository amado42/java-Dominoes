package uprm.ece.icom5015.logic;

import java.util.ArrayList;

public class AI {
	ArrayList<Domino> played, unplayed;
	Domino[] AI, valid;
	String player;
	int[] botPassed, topPassed, leftPassed, rightPassed;
	
	

	
	
	
	public AI(ArrayList<Domino> played, ArrayList<Domino> unplayed,
			Domino[] aI, String player, int[] botPassed,
			int[] topPassed, int[] leftPassed, int[] rightPassed, Domino[] valid) {
			
			this.played = played;
			this.unplayed = unplayed;
			AI = aI; this.player=player; this.valid = valid;
			this.botPassed = botPassed;
			this.topPassed = topPassed;
			this.leftPassed = leftPassed;
			this.rightPassed = rightPassed;
	}
	
	public int[] partnersPassed(){
		if(player.equals("bot"))
			return topPassed;
		if(player.equals("top"))
			return botPassed;
		if(player.equals("left"))
			return rightPassed;
		return leftPassed;
	}
	
	public int[] opponentsPassed(){
		
		if(player.equals("bot"))
			return mergePassed(leftPassed, rightPassed);
		if(player.equals("top"))
			return mergePassed(leftPassed, rightPassed);
		return mergePassed(botPassed, topPassed);
	}
	
	public int[] mergePassed(int[] a, int[] b){
		int counter=0;
		int[] temp = new int[a.length+b.length];
	
	
		for(int i=0;i<a.length;i++){
			
			temp[counter++]=a[i];
				
		}
		
		for(int j=0;j<b.length;j++){
			
			temp[counter++]=b[j];
				
		}
return temp;

	}

    public int doub(Domino d){
    	if(d.isDouble())
    		return 1;
    	return 0;
    }
    
    public int HighWeight(Domino d){
    	if(d.getWeight()>=8)
    		return 2;
    	if(d.getWeight()>=4)
    		return 1;
    	return 0;
    }
	
	public Domino bestMove(){
		int tempIndex=0;
		int points=0;
		for(int i=0;i<valid.length;i++){
			if(points(valid[i])>points){
				tempIndex=i;
				points = points(valid[i]);
			}
				
		}
		return valid[tempIndex];
	}

	private int points(Domino d) {
		int temp = 0;
		temp+=doub(d);
		temp+=HighWeight(d);
		temp+=considerLock(d);
		temp+=Quantity(d);
		temp+=partnerAvailability(d);
		temp+=opponentsAvailability(d);
		return temp;
	}

	public int handWeight(Domino[] d){
		int temp=0;
		for(int i=0;i<d.length;i++){
			temp += d[i].getWeight();
		}
		return temp;
	}
	
	public int handWeight(ArrayList<Domino> d){
		int temp=0;
		for(int i=0;i<d.size();i++){
			temp += d.get(i).getWeight();
		}
		return temp;
	}
	
	public int considerLock(Domino d){
		int myTotal = handWeight(AI)-d.getWeight();
		int boardTotal = handWeight(unplayed);
		int opponentsTotal = boardTotal-myTotal;
		double fraction = myTotal/opponentsTotal;
		if(fraction <=0.02){
			return 5;
		}
		if(fraction <=0.05){
			return 4;}
		if(fraction <=0.10){
			return 3;
		}
		if(fraction <=0.15){
			return 2;
		}
		if(fraction <=0.2){
			return 1;
		}
		if(fraction <=0.25){
			return 0;
		}
		if(fraction <=0.30){
			return -1;
		}
		if(fraction <=0.35){
			return -2;
		}
		if(fraction <=0.40){
			return -3;
		}
		if(fraction <=0.45){
			return -4;
		}
		return -5;
		
	}

    public int Quantity(Domino d){
    	int temp=0;
    	for(int i=0;i<AI.length;i++){
    		if(AI[i] != d){
    			if(AI[i].getHeadValue() == d.getHeadValue() || AI[i].getHeadValue() == d.getTailValue()
    					|| AI[i].getTailValue() == d.getHeadValue()|| AI[i].getTailValue() == d.getTailValue())
    				temp +=1;
    		}
    	}
    	return temp;
    }
    
    public int partnerAvailability(Domino d){
    	int temp=1;
    	int[] partner = partnersPassed();
    	for(int i=0;i<partner.length;i++){
    		if(d.getHeadValue() == partner[i] || d.getTailValue() == partner[i])
    			temp-=1;
    		
    	}return temp;
    }
    
    public int opponentsAvailability(Domino d){
    	int temp=0;
    	int[] opponents = opponentsPassed();
    	
    	for(int i=0;i<opponents.length;i++){
    		if(d.getHeadValue() == opponents[i] || d.getTailValue() == opponents[i])
    			temp+=1;
    		
    	}return temp/2;
    }

	public Domino Start(Domino[] startingHand){
		Domino temp = startingHand[0];
		for(int i=0;i<startingHand.length;i++){
			
				if(startingHand[i].isDouble() && startingHand[i].getWeight()>temp.getWeight())
					temp = startingHand[i];
					
			}
		if(!temp.isDouble()){
		for(int j=0;j<startingHand.length;j++){	
			if(startingHand[j].getWeight()>temp.getWeight())
				temp = startingHand[j];
	
	}
	
		}
		return temp;
	}






	public ArrayList<Domino> getPlayed() {
		return played;
	}






	public void setPlayed(ArrayList<Domino> played) {
		this.played = played;
	}






	public ArrayList<Domino> getUnplayed() {
		return unplayed;
	}






	public void setUnplayed(ArrayList<Domino> unplayed) {
		this.unplayed = unplayed;
	}






	public Domino[] getAI() {
		return AI;
	}






	public void setAI(Domino[] aI) {
		AI = aI;
	}



	public int[] getBotPassed() {
		return botPassed;
	}






	public void setBotPassed(int[] botPassed) {
		this.botPassed = botPassed;
	}






	public int[] getTopPassed() {
		return topPassed;
	}






	public void setTopPassed(int[] topPassed) {
		this.topPassed = topPassed;
	}






	public int[] getLeftPassed() {
		return leftPassed;
	}






	public void setLeftPassed(int[] leftPassed) {
		this.leftPassed = leftPassed;
	}






	public int[] getRightPassed() {
		return rightPassed;
	}






	public void setRightPassed(int[] rightPassed) {
		this.rightPassed = rightPassed;
	}


	public String getPlayer() {
		return player;
	}


	public void setPlayer(String player) {
		this.player = player;
	}
	
	
	public Domino[] getValid() {
		return valid;
	}


	public void setValid(Domino[] valid) {
		this.valid = valid;
	}

	
	
	
	
	
	
	
}
