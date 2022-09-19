package menu;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import body.IBody;
import body.bibliotheque.Bibliotheque;
import body.main.Body;
import home.Home;
import settings.Setting;

public class MenuEvent implements IMenu {
	
	
	public MenuEvent() {
		handleButtonEvent();
		handleMouseMotion();
	}
	
	private void handleButtonEvent() {
		series.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(series.isButtonActive() && Body.depth == 0) return;
				
				container.removeAll();
				container.revalidate();
				sp.remove(container);
				
				series.setActiveButton();
				unsetActiveButton(series.getText());
				
				new Body(Home.coverPathArray, SERIES_TAB);
				
				frame.repaint();
			}
		});
		
		biblio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(biblio.isButtonActive() && Body.depth == 0) return;
				
				container.removeAll();
				container.revalidate();
				sp.remove(container);
				
				biblio.setActiveButton();
				unsetActiveButton(biblio.getText());
				
				Bibliotheque.getBiblioInfosFolder();
				
				if (IBody.sortButton.getText() == IBody.sortDescend)
					IBody.sortButton.setText(IBody.sortAscend);
					
				new Body(Bibliotheque.coverPathArray, LIBRARY_TAB);
				
				frame.repaint();
			}
		});
		
		param.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				if(param.isButtonActive()) return;

				container.removeAll();
				container.revalidate();
				sp.remove(container);
								
				param.setActiveButton();
				unsetActiveButton(param.getText());
				Body.currentTab = "Param";
				
				new Setting();
				
				frame.repaint();
			}
		});
	}
	
	private void handleMouseMotion() {
		series.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			series.setHoverButton();
    			unsetHoverAllButton(series.getText());
    		}	
		});
		
		biblio.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			biblio.setHoverButton();
    			unsetHoverAllButton(biblio.getText());
    		}	
		});

		param.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			param.setHoverButton();
    			unsetHoverAllButton(param.getText());
    		}	
		});
		
		header.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			unsetHoverAllButton("");
    			frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    		}	
		});
		
		frame.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    		}	
		});
		
		container.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    		}	
		});
	}
	
	private void unsetActiveButton(String txt) {
		if (series.isButtonActive() && txt != series.getText()) series.unsetHoverButton();
		if (biblio.isButtonActive() && txt != biblio.getText()) biblio.unsetHoverButton();
		if (param.isButtonActive() && txt != param.getText()) param.unsetHoverButton();
	}
	
	private void unsetHoverAllButton(String txt) {
		if (!series.isButtonActive() && txt != series.getText()) series.unsetHoverButton();
		if (!biblio.isButtonActive() && txt != biblio.getText()) biblio.unsetHoverButton();
		if (!param.isButtonActive() && txt != param.getText()) param.unsetHoverButton();
	}

}
