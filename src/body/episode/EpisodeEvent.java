package body.episode;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import body.bibliotheque.Bibliotheque;
import body.main.Body;
import menu.IMenu;
import utils.UI.Button;

public class EpisodeEvent implements IMenu {
	private ArrayList<Button> episodeButtonArray;
	
	public EpisodeEvent(ArrayList<Button> episodeButtonArray) {
		this.episodeButtonArray = episodeButtonArray;
		handleButtonEvent();
		handleMouseMotion();
	}
	
	private void handleButtonEvent() {
		for(Button btn : episodeButtonArray) {
			
			btn.addMouseListener(new MouseAdapter() { 
				public void mousePressed(MouseEvent mouseEvent) {
					for(int i = 0; i < episodeButtonArray.size(); i++) {
						if(mouseEvent.getSource() == episodeButtonArray.get(i))
							if (SwingUtilities.isRightMouseButton(mouseEvent)) {
								Bibliotheque.updateBiblioPathFolders(Body.parentPathName, episodeButtonArray.get(i).getText(), Episode.currentFolderPath);
								///////// REFRESH BUTTON COLOR
								break;
						}
					}					
				}
			});
			
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int j = 0; j < episodeButtonArray.size(); j++) {
						if(e.getSource() == episodeButtonArray.get(j)) {
							try {
								Desktop.getDesktop().open(new File(Episode.currentFolderPath + "/" + episodeButtonArray.get(j).getText()));
							} catch (IOException e1) {
								e1.printStackTrace();
							}	
							break;
						}
					}
				}});
		}
	}
		
	private void handleMouseMotion() {
		for(Button btn : episodeButtonArray) {
			btn.addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					for(int j = 0; j < episodeButtonArray.size(); j++) {
						if (btn.isHoverActive()) episodeButtonArray.get(j).unsetHoverButton();
						if(e.getSource() == episodeButtonArray.get(j)) {
							if (!btn.isHoverActive()) episodeButtonArray.get(j).setHoverButton();
							unsetHoverAllButton(episodeButtonArray.get(j).getText());
							break;
						}	
					}
				}	
			});
		}
		
		container.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			unsetHoverAllButton("");
    		}	
		});
		
		header.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			unsetHoverAllButton("");
    		}	
		});
	}
		
	public void unsetHoverAllButton(String txt) {
		if (txt == "")
			for (Button btn : episodeButtonArray) {
				if (btn.isHoverActive()) btn.unsetHoverButton();
			}
		else 
			for (Button btn : episodeButtonArray) {
				if (!btn.getText().equals(txt)) {
					if (btn.isHoverActive()) btn.unsetHoverButton();
				}
			}
	}
}


