package uprm.ece.icom5015.logic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class tests {
	
	public static void main(String[] args) throws IOException{
		File path = new File("./data/images/");

				// load source images
				BufferedImage image = ImageIO.read(new File(path, "1.png"));
				BufferedImage overlay = ImageIO.read(new File(path, "2.png"));

				// create the new image, canvas size is the max. of both image sizes
				int w = Math.max(image.getWidth(), overlay.getWidth());
				int h = Math.max(image.getHeight(), overlay.getHeight())*2;
				BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

				// paint both images, preserving the alpha channels
				Graphics g = combined.getGraphics();
				g.drawImage(image, 0, 0, null);
				g.drawImage(overlay, 0, image.getHeight()+1, null);

				// Save as new image
				ImageIO.write(combined, "PNG", new File(path, "combined.png"));
				
				

				
	}

}
