package utils.UI;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

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
	private static final int REFRESH_HEIGHT = 32;

	public Position posView = new Position(0, 0);
	public static int lastPosY = 0;

	public ScrollPanel(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		scrollBarEvent();
	}

	public void updateScrollPanelStyle() {
		getVerticalScrollBar().setValue(0);

		getViewport().add(container);
		setBorder(BorderFactory.createEmptyBorder());
		getVerticalScrollBar().setUnitIncrement(7);

		getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(200, 185, 200);
			}
		});

		UIManager.put("ScrollBar.width", 17);
	}

	private void scrollBarEvent() {
		getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {

				if (e.getAdjustmentType() == AdjustmentEvent.TRACK) {
					final JViewport viewport = sp.getViewport();

					Point p = viewport.getViewPosition();

					if (posView.getY() < p.getY()) {
						posView = new Position((int) p.getX(), (int) p.getY());

						if (posView.getY() - lastPosY >= REFRESH_HEIGHT) {
							lastPosY = posView.getY();

							if (!SearchBar.isSearching) {
								if (Body.currentTab.equals(LIBRARY_TAB) && Body.depth == 0)
									Body.updateBodyContent(Bibliotheque.coverPathArray);
								else if (Body.currentTab.equals(SERIES_TAB) && Body.depth == 0)
									Body.updateBodyContent(Home.coverPathArray);
							} else {
								Body.updateBodyContent(SearchBar.sortedCoverPathArray);
							}
						}
					}
				}
			}
		});
	}
}
