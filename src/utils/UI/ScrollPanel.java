package utils.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;

import home.IGlobal;
import utils.shape.Position;

public class ScrollPanel extends JScrollPane implements IGlobal {
	private static final long serialVersionUID = 1L;
	
	private static final int REFRESH_HEIGHT = 150;
	
	public Position posView = new Position(0, 0);
	public Dimension dimView = new Dimension(0, 0);
	
	public static int lastPosY = 0;
	
	public ScrollPanel(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		mouseWheelEvent();
	}
	
	public void updateScrollPanelStyle() {
		getVerticalScrollBar().setValue(0);
		
		getViewport().add(container);
		setBorder(BorderFactory.createEmptyBorder());	
		getVerticalScrollBar().setUnitIncrement(20);
		
		UIManager.put("ScrollBar.width", 22);

		getVerticalScrollBar().setUI(new BasicScrollBarUI() {
		    @Override
		    protected void configureScrollBarColors() {
		        this.thumbColor = new Color(200, 185, 200);
		    }
		});
		
		UIManager.put("ScrollBar.width", 17);
	}
	
	private void mouseWheelEvent() {
		addMouseWheelListener(new MouseWheelListener() {
		    @Override
		    public void mouseWheelMoved(MouseWheelEvent e) {
		    	final JViewport viewport = sp.getViewport();
				
				Point p = viewport.getViewPosition();
				
				posView = new Position((int) p.getX(), (int) p.getY());
				dimView = new Dimension(viewport.getWidth(), viewport.getHeight());
								
				if (e.getWheelRotation() > 0) {
					if (posView.getY() - lastPosY > REFRESH_HEIGHT) {
						lastPosY = posView.getY();
						System.out.println("\n--------- UPDATE VIEW --------------\n");
//						Body.updateBodyContent(Home.coverPathArray);				
					}
				} 	
		    }
		});
	}
}
