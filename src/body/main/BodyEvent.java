package body.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import body.IBody;
import body.bibliotheque.Bibliotheque;
import body.episode.Episode;
import filter.FileSearch;
import home.Home;
import home.IGlobal;
import utils.UI.Button;

public class BodyEvent implements IBody {
	private static boolean OK = false;
	
	public BodyEvent(ArrayList<Button> cardButtonArray, ArrayList<Button> txtButtonArray, ArrayList<Button> delButtonArray) {
		cardEvent(cardButtonArray);
		txtEvent(txtButtonArray);
		
		if (Body.currentOnglet.equals("Biblio") && Body.depth < 1) delEvent(delButtonArray);
		if (!OK) backButton();
	}
	
	private void backButton() {
		OK = true;
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearBody();
				
				String separator = IGlobal.SEPARATOR;
				
				int sepPos = Body.currentPath.lastIndexOf(separator);
				String folderPath = Body.currentPath.substring(0, sepPos);
				
				if (Body.depth == 1) 
					if (Body.currentOnglet.equals("Anime")) 
						new Body(Home.coverPathArray, Body.currentOnglet);
					else 
						new Body(Bibliotheque.coverPathArray, Body.currentOnglet);
				else {	
					new Body(folderPath);
					Body.depth -= 2;
				}
				
				frame.repaint();					
			}
		});
		
		sortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> tmp = new ArrayList<String>();
				if (Body.currentOnglet.equals("Anime")) {
					for (int i = Home.coverPathArray.size(); i != 0; i--)
						tmp.add(Home.coverPathArray.get(i - 1));					
				}
				else {
					for (int i = Bibliotheque.coverPathArray.size(); i != 0; i--)
						tmp.add(Bibliotheque.coverPathArray.get(i - 1));		
				}
			
				if (sortAscend == sortButton.getText())			
					sortButton.setText(sortDescend);
				else sortButton.setText(sortAscend);
				
				container.removeAll();
				container.revalidate();
				
				if (Body.currentOnglet.equals("Anime")) {
					Home.coverPathArray = tmp;
					new Body(Home.coverPathArray, "Anime");
				}
				else {
					Bibliotheque.coverPathArray = tmp;
					new Body(Bibliotheque.coverPathArray, "Biblio");
				}
				
				frame.repaint();					
			}
		});
	}
	
	private void delEvent(ArrayList<Button> delButtonArray) {
		for(Button btn : delButtonArray) {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int j = 0; j < delButtonArray.size(); j++) {
											
						if(e.getSource() == delButtonArray.get(j)) {								
							clearBody();							

							Bibliotheque.removeElementBibliotheque(delButtonArray.get(j).getName());
							new Body(Bibliotheque.coverPathArray, Body.currentOnglet);
					
							frame.repaint();

							break;
						}
					}
			}});
		}
	}
	
	private void txtEvent(ArrayList<Button> txtButtonArray) {
		for(Button btn : txtButtonArray) {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int j = 0; j < txtButtonArray.size(); j++) {
											
						if(e.getSource() == txtButtonArray.get(j)) {
							
							String folderPath;
							
							if (Body.depth == 0) {
								initBodyFirstDepth(txtButtonArray, j);
								break;
							}
							else folderPath = Body.currentPath + IGlobal.SEPARATOR + txtButtonArray.get(j).getName();
														
							clearBody();
							
							if (Body.depth >= 1) initBodyDepth(txtButtonArray, j, folderPath);			
							
							frame.repaint();
							break;
						}
					}
			}});
		}
	}
	
	private void cardEvent(ArrayList<Button> cardButtonArray) {
		for(Button btn : cardButtonArray) {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
								
					for(int j = 0; j < cardButtonArray.size(); j++) {
						if(e.getSource() == cardButtonArray.get(j)) {
							
							String folderPath;
							
							if (Body.depth == 0) {
								initBodyFirstDepth(cardButtonArray, j);
								break;
							}
							else folderPath = Body.currentPath + IGlobal.SEPARATOR + cardButtonArray.get(j).getName();
														
							clearBody();
							
							if (Body.depth >= 1) initBodyDepth(cardButtonArray, j, folderPath);
							
							frame.repaint();
							break;
						}
					}
			}});
		}
	}
	
	private void initBodyDepth(ArrayList<Button> buttonArray, int index, String folderPath) {
		FileSearch fs = new FileSearch(folderPath, 1);
		
		if (fs.getFileInDepth().size() > 0) {
			
			File f = new File(folderPath + IGlobal.SEPARATOR + fs.getFileInDepth().get(0));									
			
			if (f.isDirectory()) new Body(folderPath);
			else new Episode(buttonArray.get(index).getImagePath(), Body.previousPage, buttonArray.get(index).getName());
		}
		else new Episode(buttonArray.get(index).getImagePath(), Body.previousPage, buttonArray.get(index).getName());
	}
	
	private void initBodyFirstDepth(ArrayList<Button> buttonArray, int index) {
		String separator = "/";
		
		int sepPos = buttonArray.get(index).getImagePath().lastIndexOf(separator);
		String p = buttonArray.get(index).getImagePath().substring(0, sepPos);
		
		sepPos = p.lastIndexOf(separator);
		String folderPath = p.substring(0, sepPos);
		
		clearBody();
		
		FileSearch fs = new FileSearch(folderPath, 1);
		if (fs.getFileInDepth().size() > 0) {
			String firstFile = fs.getFileInDepth().get(0);
			
			if (firstFile.equalsIgnoreCase("Cover")) firstFile = fs.getFileInDepth().get(1);
			File f = new File(folderPath + IGlobal.SEPARATOR + firstFile);
			
			if (f.isDirectory()) new Body(folderPath);
			else {
				Body.currentPath = folderPath;
				new Episode(buttonArray.get(index).getImagePath(), new File(folderPath).getName(), "");
			}
		}
		else new Episode(buttonArray.get(index).getImagePath(), Body.previousPage, buttonArray.get(index).getName());
		frame.repaint();
	}
	
	private void clearBody() {
		container.removeAll();
		container.revalidate();
		sp.remove(container);
	}
}