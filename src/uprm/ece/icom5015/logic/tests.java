package uprm.ece.icom5015.logic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class tests {

	static int startTopY= 43;
	static int startBotY=755;
	static int startLeftX = 38;
	static int startRightX = 758;

	static int startOffset = 144;
	static int bracketLength = 575;
	static int tileWidth = 51;

	static public File path = new File("./data/images/");

	
	public static void setPlayer(Domino[] tiles) throws IOException{
		BufferedImage game = ImageIO.read(new File(path, "GameBoard.png"));
		BufferedImage tile = null;
		
		int w = game.getWidth();
		int h = game.getHeight();
		int startingPosition = startOffset+(bracketLength-(tileWidth*tiles.length)
								+(tiles.length-1))/2;
		
		BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = combined.getGraphics();
		g.drawImage(game, 0, 0, null);
		for (int i=0; i<tiles.length; i++){
			tile = ImageIO.read(new File(path, "tiles/"+tiles[i].getHeadValue()
								+"-"+tiles[i].getTailValue()+".png"));
			g.drawImage(tile, startingPosition+52*i, startBotY, null);
		}
		ImageIO.write(combined, "PNG", new File(path, "test.png"));
	}
	
	public static void setComputer(String player, int tiles) throws IOException{
		BufferedImage game = ImageIO.read(new File(path, "test.png"));
		BufferedImage tile = null;

		int w = game.getWidth();
		int h = game.getHeight();
		int startingPosition = startOffset+(bracketLength-(tileWidth*tiles)+(tiles-1))/2;

		BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics g = combined.getGraphics();
		g.drawImage(game,  0,  0,  null);
		if(player.equals("top")){
			tile = ImageIO.read(new File(path, "topPlayer.png"));
			
		}
		if(player.equals("left")){
			tile = ImageIO.read(new File(path, "leftPlayer.png"));
			
		}
			
		if (player.equals("right")){
			tile = ImageIO.read(new File(path, "rightPlayer.png"));
		}
		
		for(int i=0; i<tiles; i++){
			if (player.equals("top")){
				g.drawImage(tile, startingPosition+52*i, startTopY, null);
			}
			if (player.equals("right")){
				g.drawImage(tile,  startRightX, startingPosition+52*i, null);
			}
			if (player.equals("left")){
				g.drawImage(tile, startLeftX, startingPosition+52*i, null );
			}
		}
		ImageIO.write(combined, "PNG", new File(path, "test.png"));
	}

	public static void drawTiles() throws IOException{
		for(int i=0; i<=6; i++){
			for (int j=i ; j<=6; j++){

				BufferedImage tile = ImageIO.read(new File(path, "domino.png"));
				int w = tile.getWidth();
				int h = tile.getHeight();
				BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

				BufferedImage overlayTop = null;
				BufferedImage overlayBot = null;
				// load source images
				if(i!=0)
					overlayTop = ImageIO.read(new File(path, i+".png"));
				if(j!=0)
					overlayBot = ImageIO.read(new File(path, j+".png"));



				// paint both images, preserving the alpha channels
				Graphics g = combined.getGraphics();
				g.drawImage(tile, 0, 0, null);
				if (i!=0)
					g.drawImage(overlayTop, 1, 2, null);
				if(j!=0)
					g.drawImage(overlayBot, 1, 34, null);

				// Save as new image
				ImageIO.write(combined, "PNG", new File(path, "tiles/"+i+"-"+j+".png"));

			}
		}
	}

	public static void main(String[] args) throws IOException{
		Tiles hand = new Tiles();
		
		setPlayer(hand.getPlayerTiles());
		
		setComputer("top",5);
		setComputer("left", 4);
		setComputer("right", 6);


	}

}
