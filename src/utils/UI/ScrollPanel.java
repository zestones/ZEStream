package utils.UI;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;

import home.IGlobal;

public class ScrollPanel extends JScrollPane implements IGlobal {
	private static final long serialVersionUID = 1L;

	public ScrollPanel(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
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
}
