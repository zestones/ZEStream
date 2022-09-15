package utils.UI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import home.IGlobal;
import utils.shape.Position;  


public class Button extends JButton implements MouseMotionListener, IGlobal {
	private static final long serialVersionUID = 1L;
	
	private static final Color ICON_HOVER_COLOR = new Color(0, 0, 0, 0);
	private static final Color ICON_UNSET_HOVER_COLOR = new Color(100, 100, 100, 50);
	
	private Dimension dim;
	private Position pos;
	private Font font;
	private String imageName;
	private String imgPath;
	private Color background;
	private Color foreground;
	private boolean hoverActive;
	private boolean blur;
		
	private Color iconColor;
	public boolean txtHoverActive = false;
	
	private static final int HOVER_DIFF_BACKGROUND = 60;
	private static final int HOVER_DIFF_FOREGROUND = 125;
		
	public Button(Position p, String txt, int size, Color foreground, Color background, int TYPE) {
		super(txt);
		this.imageName = txt;
		
		this.background = background;
		this.foreground = foreground;
		
		this.hoverActive = true;
		
		Font font = new Font("Inter", TYPE, size);
		setFont(font);
		this.font = font;
		
		setBackground(background);
		
		setVerticalTextPosition(SwingConstants.BOTTOM);
	    setHorizontalTextPosition(SwingConstants.LEFT);
	    
	    setForeground(foreground);
		
		Dimension d = getTextDimension();
		
		setBounds(p.getX(), p.getY(), (int) d.getWidth() + 50, (int) d.getHeight());
		
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusable(false); 	
		
		dim = d;
		pos = p;
		
		addMouseMotionListener(this);
	}
	
	public Button(Position p, String txt, int size, Dimension d, Color foreground, Color background, int TYPE) {
		super(txt);
		this.imageName = txt;
		
		this.background = background;
		this.foreground = foreground;
		
		this.hoverActive = true;
		
		Font font = new Font("Inter", TYPE, size);
		setFont(font);
		this.font = font;
		
		setBackground(background);

		setBorderPainted(false);
		setFocusable(false);
		
		setVerticalTextPosition(SwingConstants.BOTTOM);
	    setHorizontalTextPosition(SwingConstants.LEFT);
	    
	    setForeground(foreground);
			
		setBounds(p.getX(), p.getY(), (int) d.getWidth(), (int) d.getHeight());
   		
		dim = d;
		pos = p;
		
		addMouseMotionListener(this);
	}
		
	public Button(Position p, String path, String name, Dimension d, boolean blur) {		
		imgPath = path;
		this.blur = blur;
		
		File f = new File(path);
		if(!f.exists() || f.isDirectory()) path = IMG_NOT_FOUND;
		
		Icon icon = new ImageIcon(path);
	    
	    Image image = ((ImageIcon) icon).getImage();
	    Image newimg = image.getScaledInstance((int) d.getWidth(), (int) d.getHeight(), java.awt.Image.SCALE_SMOOTH);
	    
	    icon = new ImageIcon(newimg); 
	    setIcon(icon);
	    
	    setBackground(DARK_THEME);
	    
	    setVerticalTextPosition(SwingConstants.BOTTOM);
	    setHorizontalTextPosition(SwingConstants.CENTER);
	    
	    setBounds(p.getX(), p.getY(), (int) d.getWidth(), (int) (d.getHeight())); 
	    
	    dim = d;
		pos = p;
		imageName = name;
		iconColor = ICON_UNSET_HOVER_COLOR;
		
		setRolloverEnabled(false);
		setBorderPainted(false);
		
		addMouseMotionListener(this);
		
		container.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
            	iconColor = ICON_UNSET_HOVER_COLOR;
            	repaint();
    		}	
		});
	}
		
	public void setIcon(String path, Dimension d) {
		Icon icon = new ImageIcon(path);
	    
	    Image image = ((ImageIcon) icon).getImage();
	    Image newimg = image.getScaledInstance((int) d.getWidth(), (int) d.getHeight(), java.awt.Image.SCALE_SMOOTH);
	    
	    icon = new ImageIcon(newimg); 
	    
	    this.setIcon(icon);
	}
	
	public Dimension getTextDimension() {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
			
		int textwidth = (int) (this.font.getStringBounds(this.getText(), frc).getWidth());
		int textheight = (int) (this.font.getStringBounds(this.getText(), frc).getHeight());
		
		return new Dimension(textwidth, textheight);
	}
		 
	 public Position getPosition() { return this.pos; }
	 public void setPosition(Position p) { 
		 this.pos.setX(p.getX());
		 this.pos.setY(p.getY());
	 } 
	 public Dimension getDimension() { return this.dim; }
	 
	 public String getImagePath() { return this.imgPath; }
	 public void setImagePath(String p) { this.imgPath = p; }
	 public String getName() { return this.imageName; }
	 public void setName(String txt) { this.imageName = txt; }

	 public Color getForegroundColor() { return this.foreground; }
	 public Color getBackgroundColor() { return this.background; }

	 public void setForegroundColor(Color foreground) { this.foreground = foreground; }
	 public void setBackgroundColor(Color background) { this.background = background; }
	 
	 public boolean isHoverActive() { return this.hoverActive; }
	 
	 public void setHoverButton() {
		 		 
		 Color f = this.getForegroundColor();
		 Color b = this.getBackgroundColor();
		 
		 Color foregroundColor = upadteHoverColor(f, HOVER_DIFF_FOREGROUND);
		 this.setForeground(foregroundColor);

		 Color backgroundColor = upadteHoverColor(b, -HOVER_DIFF_BACKGROUND);
		 this.setBackground(backgroundColor);
		 
		 this.setBorderPainted(true);
		 this.setBorder(new MatteBorder(2, 0, 2, 0, Color.white));

		 this.hoverActive = true;
	 }
	 
	 public void unsetHoverButton() {
		 		 
		 Color f = this.getForegroundColor();
		 Color b = this.getBackgroundColor();
		 
		 Color foregroundColor = upadteHoverColor(f, -HOVER_DIFF_FOREGROUND);
		 this.setForeground(foregroundColor);
		 
		 Color backgroundColor =  upadteHoverColor(b, HOVER_DIFF_BACKGROUND);
		 this.setBackground(backgroundColor);
		 
		 this.setBorderPainted(false);

		 this.hoverActive = false;
	 }
	 
	 public Color upadteHoverColor(Color c, int HOVER_DIFF) {
		 
		 return new Color(
				 Math.abs(c.getRed() + HOVER_DIFF) < 255 ? 
				 Math.abs(c.getRed() + HOVER_DIFF) :
				 c.getRed(), 
				 Math.abs(c.getGreen() + HOVER_DIFF) < 255 ?
				 Math.abs(c.getGreen() + HOVER_DIFF) :
				 c.getGreen(), 
				 Math.abs(c.getBlue() + HOVER_DIFF) < 255 ?
				 Math.abs(c.getBlue() + HOVER_DIFF) : 
				 c.getBlue()
			);
	 }
	 
	    @Override
		 public void paintComponent(Graphics g) {
			 super.paintComponent(g);
			 if (this.getIcon() != null && this.blur) {
				 g.setColor(iconColor);
				 g.fillRect(0, 0, (int) dim.getWidth(), (int) dim.getHeight());				 
			 }
	    }

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (this.getIcon() != null && this.blur) {
        	iconColor = ICON_HOVER_COLOR;
        	repaint();
        }
        else if(this.getIcon() == null) {
        	Color c = getForegroundColor();
        	Color update = upadteHoverColor(c, 20);
        	this.setForeground(update);
        	txtHoverActive = true;
        }
	}
	 
}
