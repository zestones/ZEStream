package utils.UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import home.IGlobal;
import utils.shape.Position;

public class Image extends JPanel implements IGlobal {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	private Dimension dim;

    public Image(Position p, String path, Dimension d) {
    	File f = new File(path);
    	if(!f.exists() || f.isDirectory()) path = IMG_NOT_FOUND;
    	
    	try {
    		image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	dim = d;
    	
		setBounds(p.getX() + 1, p.getY() - 2, (int) d.getWidth(), (int) d.getHeight());
		image.getScaledInstance((int) d.getWidth(), (int) d.getHeight(), java.awt.Image.SCALE_SMOOTH);

		setFocusable(false); 	
		setOpaque(false);
    }

    @Override
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
	 
		 g.drawImage(image, 0, 0, (int) dim.getWidth(), (int) dim.getHeight(), this); 
    }
}