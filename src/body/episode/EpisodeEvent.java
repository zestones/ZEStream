package body.episode;

import java.awt.Desktop;
import java.awt.Dimension;
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
import utils.shape.Position;

public class EpisodeEvent implements IMenu, IEpisode {
	private ArrayList<Button> episodeButtonArray;
	
	private static Button delBookmarksEpBtn;
	private static Button bookmarkEpbtn;
		
	public EpisodeEvent(ArrayList<Button> episodeButtonArray) {
		this.episodeButtonArray = episodeButtonArray;
		
		handleButtonEvent();
		handleMouseMotion();
	}
	
	private void updateBookmarkSeries(int index) {
		boolean success = Bibliotheque.updateBiblioPathFolders(Body.parentPathName, episodeButtonArray.get(index).getText(), Episode.currentFolderPath);
		
		if (success) {

			if (episodeButtonArray.size() > Episode.bookMarksBtn) {
				Button lastBookmarkedEp = episodeButtonArray.get(Episode.bookMarksBtn);
				
				lastBookmarkedEp.setBackgroundColor(EPISODE_BACKGROUND);
				lastBookmarkedEp.setForegroundColor(EPISODE_FOREGROUND);
				
				lastBookmarkedEp.unsetHoverButton();
				lastBookmarkedEp.repaint();
				
				removeBookmarkButton();
			}
			
			Button currentBookmarkedEp = episodeButtonArray.get(index);
			
			currentBookmarkedEp.setBackgroundColor(EPISODE_MARKED_BACKGROUND);
			currentBookmarkedEp.setForegroundColor(EPISODE_MARKED_FOREGROUND);
			
			currentBookmarkedEp.unsetHoverButton();					
			currentBookmarkedEp.repaint();
									
			Episode.bookMarksBtn = index;
		}
	}
	
	private void handleButtonEvent() {
		for(Button btn : episodeButtonArray) {
			
			btn.addMouseListener(new MouseAdapter() { 
				public void mousePressed(MouseEvent mouseEvent) {
					for(int i = 0; i < episodeButtonArray.size(); i++) {
						if(mouseEvent.getSource() == episodeButtonArray.get(i))
							if (SwingUtilities.isRightMouseButton(mouseEvent)) {
								updateBookmarkSeries(i);
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
								Desktop.getDesktop().open(new File(Episode.currentFolderPath + SEPARATOR + episodeButtonArray.get(j).getText()));
							} catch (IOException e1) {
								e1.printStackTrace();
							}	
							break;
						}
					}
				}});
		}
	}
		
	
	private void displayRemoveButton(int index) {
		Button delEp = new Button(
				new Position(
						episodeButtonArray.get(index).getPosition().getX() + episodeButtonArray.get(index).getWidth(),
						episodeButtonArray.get(index).getY()) ,
				REMOVE_ICON,
				"delete-episode", 
				new Dimension(40, 50),
				false
				);
										
		if(!delEp.equals(delBookmarksEpBtn)) {
			removeBookmarkButton();
			delBookmarksEpBtn = delEp;
			container.add(delBookmarksEpBtn);
			
			delBookmarksEpBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Bibliotheque.removeElementBibliotheque(new File(Body.parentPathName).getName());
					
					if (episodeButtonArray.size() > Episode.bookMarksBtn) {
						Button lastBookmarkedEp = episodeButtonArray.get(Episode.bookMarksBtn);
						
						lastBookmarkedEp.setBackgroundColor(EPISODE_BACKGROUND);
						lastBookmarkedEp.setForegroundColor(EPISODE_FOREGROUND);
						
						lastBookmarkedEp.unsetHoverButton();
						lastBookmarkedEp.repaint();
						
						removeBookmarkButton();
					}
				}});
		}
	}
	
	private void displayBookmarkButton(int index) {
		Button bookmarkedEp = new Button(
				new Position(
						episodeButtonArray.get(index).getPosition().getX() + episodeButtonArray.get(index).getWidth(),
						episodeButtonArray.get(index).getY()) ,
				BOOKMARK_ICON,
				"bookmark-episode", 
				new Dimension(40, 50),
				false
				);
		if(!bookmarkedEp.equals(bookmarkEpbtn)) {
			removeBookmarkButton();
			bookmarkEpbtn = bookmarkedEp;
			container.add(bookmarkEpbtn);
						
			bookmarkedEp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateBookmarkSeries(index);
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
							if (!btn.isHoverActive()) {
								episodeButtonArray.get(j).setHoverButton();
										
								if (Bibliotheque.seriesEpisode.contains(btn.getName()))
									displayRemoveButton(j);
								else displayBookmarkButton(j);
							}
																								
							unsetHoverAllButton(episodeButtonArray.get(j).getText());
							frame.repaint();
							break;
						}	
					}
				}	
			});
		}
		
		container.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			unsetHoverAllButton("");
    			removeBookmarkButton();
    		}	
		});
		
		header.addMouseMotionListener(new MouseAdapter() {
    		public void mouseMoved(MouseEvent e) {
    			unsetHoverAllButton("");
    			removeBookmarkButton();
    		}	
		});
	}
	
		
	public static void removeBookmarkButton() {
		if(bookmarkEpbtn != null) container.remove(bookmarkEpbtn);
		if(delBookmarksEpBtn != null) container.remove(delBookmarksEpBtn);
		
		frame.repaint();
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


