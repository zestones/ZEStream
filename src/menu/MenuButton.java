package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.JButton;

import home.IGlobal;
import utils.shape.Position;
import utils.shape.Rectangle;

public class MenuButton extends JButton implements IGlobal, IMenu {
		
	private static final long serialVersionUID = 1L;
	
	private Color ForegroundActive = new Color(255, 255, 255);
	private Color ForegroundInactive = new Color(186, 186, 186);
	
	private Dimension dim;
	private Position pos;
	
	private boolean isActive;
	public Rectangle rect;
	private String txt;
	
	private int textwidth, textheight;
		
	private static final int TEXT_WIDTH_PADDING = 50;
	private static final int DEFAULT_UNDERLINE_SIZE = 10;
	
	public MenuButton(String text, int x, int y) {
		super(text);
		
		isActive = false;
		txt = text;
		
		rect = null;
		
		setBackground(DARK_THEME);

		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusable(false);
				
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		
		Font font = new Font("Inter", Font.PLAIN, 24);
		setFont(font);

		setForeground(ForegroundInactive);
		setText(text);
		
		textwidth = (int) (font.getStringBounds(text, frc).getWidth());
		textheight = (int) (font.getStringBounds(text, frc).getHeight()) + DEFAULT_UNDERLINE_SIZE + 6;
		
		setBounds(x, y, textwidth + TEXT_WIDTH_PADDING, textheight);
		
		dim = new Dimension(textwidth + TEXT_WIDTH_PADDING, textheight);
		pos = new Position(x, y);
		
	}
	
	 public void paint(Graphics g) {
		 super.paint(g);
		 
		 if (this.isActive) this.rect.draw(g);
	 }
	
	public Position getPosition() { return this.pos; }
	public Dimension getDimension() { return this.dim; }
	public int getWidth() { return (int) this.dim.getWidth(); }
	public int getHeight() { return (int) this.dim.getHeight(); }
	public boolean isButtonActive() { return this.isActive; }
	public String getText() { return this.txt; }
	
	private void setUnderline(int RECTANGLE_HEIGHT) {
		this.rect = new Rectangle(new Position(25, this.getHeight() - RECTANGLE_HEIGHT), new Dimension(this.getWidth() - TEXT_WIDTH_PADDING, this.getHeight() - RECTANGLE_HEIGHT));
	}
	
	public void setActiveButton() {
		this.isActive = true;
		this.setForeground(ForegroundActive);
		
		this.setUnderline(DEFAULT_UNDERLINE_SIZE);		
	}
	
	public void setHoverButton() {
		this.setForeground(ForegroundActive);
	}
	
	public void unsetHoverButton() {
		this.setForeground(ForegroundInactive);
		this.isActive = false;
	}
	
}

