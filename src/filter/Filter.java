package filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;

import utils.shape.Position;

public class Filter extends JLabel {
	private static final long serialVersionUID = 1L;

	public Filter(String opt1, String opt2, String opt3, Position p) {
		super(opt1);
		
		setForeground(new Color(12, 124, 255));
		
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		
		Font font = new Font("Inter", Font.PLAIN, 14);
		setFont(font);
		
		int textwidth = (int) (font.getStringBounds(opt1, frc).getWidth());
		int textheight = (int) (font.getStringBounds(opt1, frc).getHeight());
		
		setBounds(p.getX(), p.getY(), textwidth + 50, textheight);
	}
}
