package home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;

public class Title extends JLabel {
	private static final long serialVersionUID = 1L;
	
	private Dimension dim;
	private Position pos;
	
	private Rectangle rect;
	
	private static final int TEXT_WIDTH_PADDING = 50;
	private static final int DEFAULT_UNDERLINE_SIZE = 10;
	
	public Title(String txt, Position p, int size, Color foreground, int TYPE) {
		super(txt);
		
		setForeground(foreground);
		
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		
		Font font = new Font("Inter", TYPE, size);
		setFont(font);
		
		int textwidth = (int) (font.getStringBounds(txt, frc).getWidth());
		int textheight = (int) (font.getStringBounds(txt, frc).getHeight() + DEFAULT_UNDERLINE_SIZE + 6);
		
		setBounds(p.getX(), p.getY(), textwidth + TEXT_WIDTH_PADDING, textheight);
		
		dim = new Dimension(textwidth + TEXT_WIDTH_PADDING, textheight);
		pos = p;
	}
	
	public Title(String txt, Position p, int size, Dimension d, Color foreground, int TYPE) {
		super(txt);
		
		setForeground(foreground);
		
		Font font = new Font("Inter", TYPE, size);
		setFont(font);
		
		setBounds(p.getX(), p.getY(), (int) d.getWidth(), (int) d.getHeight());
		
		dim = d;
		pos = p;
	}
	
	public Title(String file, Position p, int i) {
		this(file, p, i, Color.white, Font.PLAIN);
	}
	
	 public void paint(Graphics g) {
		 super.paint(g);
		 
		 if (this.rect != null) this.rect.draw(g);
	 }

	 public void setUnderline(int RECTANGLE_HEIGHT) {
		 this.rect = new Rectangle(new Position(0, this.getHeight() - RECTANGLE_HEIGHT), new Dimension(this.getWidth() - TEXT_WIDTH_PADDING, this.getHeight() - RECTANGLE_HEIGHT));
	 }
	
	 public Position getPosition() { return this.pos; }
	 public Dimension getDimension() { return this.dim; }
	 public int getWidth() { return (int) this.dim.getWidth(); }
	 public int getHeight() { return (int) this.dim.getHeight(); }
}
