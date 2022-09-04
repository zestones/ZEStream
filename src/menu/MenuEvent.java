package menu;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import body.bibliotheque.Bibliotheque;
import body.main.Body;
import home.Home;
import home.IGlobal;
import settings.Setting;

public class MenuEvent implements IGlobal, IMenu {
	
	
	public MenuEvent() {
		handleButtonEvent();
		handleMouseMotion();
	}
	
	private void handleButtonEvent() {
		anime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(anime.isButtonActive() && Body.depth == 0) return;
				
				container.removeAll();
				container.revalidate();
				sp.remove(container);
				
				anime.setActiveButton();
				unsetActiveButton(anime.getText());
				
				new Body(Home.coverPathArray, "Anime");
				
				frame.repaint();
			}
		});
		
		biblio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				container.removeAll();
				container.revalidate();
				sp.remove(container);
				
				biblio.setActiveButton();
				unsetActiveButton(biblio.getText());
				
				Bibliotheque.getBiblioCoverFolders();
				new Body(Bibliotheque.coverPathArray, "Biblio");
				
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
				
				new Setting();
				
				frame.repaint();
			}
		});
	}
	
	private void handleMouseMotion() {
		anime.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			anime.setHoverButton();
    			unsetHoverAllButton(anime.getText());
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
		if (anime.isButtonActive() && txt != anime.getText()) anime.unsetHoverButton();
		if (biblio.isButtonActive() && txt != biblio.getText()) biblio.unsetHoverButton();
		if (param.isButtonActive() && txt != param.getText()) param.unsetHoverButton();
	}
	
	private void unsetHoverAllButton(String txt) {
		if (!anime.isButtonActive() && txt != anime.getText()) anime.unsetHoverButton();
		if (!biblio.isButtonActive() && txt != biblio.getText()) biblio.unsetHoverButton();
		if (!param.isButtonActive() && txt != param.getText()) param.unsetHoverButton();
	}

}
