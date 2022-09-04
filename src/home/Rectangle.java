package home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Rectangle {
	private Position pos;
	private Dimension dim;
	
	public Rectangle(Position p, Dimension d) {
		this.pos = p;
		this.dim = d;
	}
	
	public Position getPosition() { return this.pos; }
	public Dimension getDimension() { return this.dim; }
	
	public void draw(Graphics g) {
		 g.setColor(new Color(57, 73, 223));
		 g.fillRect(this.getPosition().getX(), this.getPosition().getY(),
				 (int) this.getDimension().getWidth(), (int) this.getDimension().getHeight()
				 );
	}
}
