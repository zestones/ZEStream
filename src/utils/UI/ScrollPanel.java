package utils.UI;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;

import body.bibliotheque.Bibliotheque;
import body.main.Body;
import home.Home;
import home.IGlobal;
import utils.shape.Position;

public class ScrollPanel extends JScrollPane implements IGlobal {
	private static final long serialVersionUID = 1L;
	
	private static final int REFRESH_HEIGHT = 60;
	
	public Position posView = new Position(0, 0);	
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
								
				if (e.getWheelRotation() > 0) {
//					System.out.println((posView.getY() - lastPosY));
					
					if (posView.getY() - lastPosY == REFRESH_HEIGHT) {
						lastPosY = posView.getY();
						
						if (Body.currentOnglet.equals("Biblio"))
							Body.updateBodyContent(Bibliotheque.coverPathArray);
						else if (Body.currentOnglet.equals("Anime") && Body.depth == 0) Body.updateBodyContent(Home.coverPathArray);				
					}
				} 	
		    }
		});
	}
}
