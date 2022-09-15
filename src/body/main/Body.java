package body.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.border.MatteBorder;

import body.IBody;
import body.bibliotheque.Bibliotheque;
import filter.FileSearch;
import home.IGlobal;
import settings.Setting;
import utils.OsUtils;
import utils.UI.Button;
import utils.UI.Image;
import utils.UI.ScrollPanel;
import utils.UI.Title;
import utils.shape.Position;

public class Body implements IBody {

	private final static int CONTAINER_WIDTH = FRAME_WIDTH;
	
    private static ArrayList<Button> cardButtonArray;
    private static ArrayList<Button> txtButtonArray;
    private static ArrayList<Button> delButtonArray;

    private static Position lastDisplayedPos;
    
    public static String parentPathName;
    public static String previousPage;
    public static String currentPath;
    public static String subFolders; 
	public static int depth;
	
	private static int indexDisplayView = 0;
	public static String currentOnglet; 
	
	public static String COVER_FOLDER_NAME = Setting.getFolderCoverName();
	
	public Body(ArrayList<String> coverPathArray, String onglet) {
		currentOnglet = onglet;
		previousPage = "";
		currentPath = "";
		depth = 0;
		
		sp.posView = new Position(0, 0);
		ScrollPanel.lastPosY = 0;
		indexDisplayView = 0;

		container.setBackground(DARK_THEME);
		container.setBorder(new MatteBorder(2, 0, 0, 0, new Color(84, 84, 84)));
		
	    container.setLayout(null);
	    	    
		container.add(new Title("Parcourir les Séries", new Position(50, 60), 32, Color.white, Font.PLAIN));
		container.add(new Title("Filtrer : ", new Position(50, 125), 14, Color.white, Font.PLAIN));
		
		sortButton.setContentAreaFilled(false);
		container.add(sortButton);
		
		cardButtonArray = new ArrayList<Button>();
		txtButtonArray = new ArrayList<Button>();
		delButtonArray = new ArrayList<Button>();

		this.fillBody(coverPathArray);		
		new BodyEvent(cardButtonArray, txtButtonArray, delButtonArray);

		sp.updateScrollPanelStyle();

		frame.getContentPane().add(sp);
	}
	
	public Body(String folderPath) {
		depth++;
		if (depth == 1) parentPathName = folderPath;
				
		sp.posView = new Position(0, 0);
		ScrollPanel.lastPosY = 0;
		indexDisplayView = 0;
		
		container.setBackground(DARK_THEME);
		container.setBorder(new MatteBorder(2, 0, 0, 0, new Color(84, 84, 84)));

	    container.setLayout(null);
	    container.add(backButton);
		
		String title = new File(folderPath).getName();
		
		Title pageTitle = new Title(title, new Position(50, 90), 32, Color.white, Font.BOLD);
		pageTitle.setUnderline(6);
		
		container.add(pageTitle);

		cardButtonArray = new ArrayList<Button>();
		txtButtonArray = new ArrayList<Button>();

		currentPath = folderPath;
		previousPage = title;
				
		this.fillBody(folderPath);
		new BodyEvent(cardButtonArray, txtButtonArray, delButtonArray);

		sp.updateScrollPanelStyle();

		frame.getContentPane().add(sp);
	}
	
	private String getImageExtension(String path, String file) {
		File f = new File(path + IGlobal.SEPARATOR + COVER_FOLDER_NAME);
		if (!f.isDirectory() || f.isFile()) return "";
		
		FileSearch fsImage = new FileSearch(path + IGlobal.SEPARATOR + COVER_FOLDER_NAME, 1);
				
		for (String img : fsImage.getFileInDepth()) {
			if (file.equalsIgnoreCase(FileSearch.getFileName(img)))
				return FileSearch.getFileExtension(img);
		}
		
		return "";
	}
	
	// fill the body main page
	private void fillBody(ArrayList<String> coverPathArray) {
		
		Position cardPos = new Position(70, 190);		
		Button txt = new Button(new Position(50,50), "", 18, DEFAULT_CARD_COLOR, Color.red, Font.PLAIN);
		
		int index = 0;
		for (String path : coverPathArray) {
						
			File file = new File(path);
			String fileName = FileSearch.getFileName(file.getName());
								
			if (cardPos.getY() >= sp.posView.getY() && cardPos.getY() <= sp.posView.getY() + FRAME_HEIGHT - MENU_HEIGHT ) {
				Button anime = new Button(cardPos, path, fileName, DIM_CARD, true);		
				
				Color c = DEFAULT_CARD_COLOR;
				for (String t : Bibliotheque.seriesTitle) {
					if (t.equals(fileName)) {
						c = SAVED_CARD_COLOR;
						if (currentOnglet.equals("Anime")) {
											
							Image bookmarks = new Image(new Position(cardPos.getX() + CARD_WIDTH, cardPos.getY()), "./.res/bookmark.png", new Dimension(20, 78));
							container.add(bookmarks);
						}
					}
				}

				txt = new Button(new Position(cardPos.getX() - 20, cardPos.getY() + (int) anime.getDimension().getHeight() + 10), fileName, 18, c, DARK_THEME, Font.PLAIN);
				
				if(txt.getTextDimension().getWidth() > DIM_CARD.getWidth())
					txt = adjustTextButton(fileName, anime, cardPos);
				
				txt.setImagePath(path);
				
				cardButtonArray.add(anime);
				txtButtonArray.add(txt);
				
				if (currentOnglet.equals("Biblio")) {
					Button delete = new Button(new Position(cardPos.getX() + CARD_WIDTH, cardPos.getY()), DELETE_ICON, fileName, new Dimension(25, 25), false);
					delButtonArray.add(delete);
					container.add(delete);				
				}
				
				lastDisplayedPos = new Position(cardPos.getX(), cardPos.getY());
				
				lastDisplayedPos.setX(lastDisplayedPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE);				
				
				if (lastDisplayedPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE >= FRAME_WIDTH && index < coverPathArray.size() - 1) {
					lastDisplayedPos.setX(70);
					lastDisplayedPos.setY(lastDisplayedPos.getY() + CARD_HEIGHT + txt.getHeight() + PADDING_CARDS_TOP);
				}
				
				container.add(anime);
				container.add(txt);
				
				indexDisplayView++;
			}
						
			cardPos.setX(cardPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE);				
			
			if (cardPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE >= FRAME_WIDTH && index < coverPathArray.size() - 1) {
				cardPos.setX(70);
				cardPos.setY(cardPos.getY() + CARD_HEIGHT + txt.getHeight() + PADDING_CARDS_TOP);
			}
									
			index++;
		}
	
	    container.setPreferredSize(new Dimension(CONTAINER_WIDTH, cardPos.getY() + CARD_HEIGHT + txt.getHeight() + PADDING_CARDS_TOP));
	}
	
	public static void updateBodyContent(ArrayList<String> coverPathArray) {
				
		ArrayList<Button> cardButtonArray = new ArrayList<Button>();
	    ArrayList<Button> txtButtonArray = new ArrayList<Button>();
	    ArrayList<Button> delButtonArray = new ArrayList<Button>();
		
		Position cardPos = new Position(lastDisplayedPos.getX(), lastDisplayedPos.getY());
		Button txt = new Button(new Position(50,50), "", 18, DEFAULT_CARD_COLOR, Color.red, Font.PLAIN);
			
		int index = 0;
		for (int i = indexDisplayView; i < coverPathArray.size(); i++) {
						
			String path = coverPathArray.get(i);
			
			File file = new File(path);
			String fileName = FileSearch.getFileName(file.getName());
								
			if (cardPos.getY() >= sp.posView.getY() && cardPos.getY() <= sp.posView.getY() + FRAME_HEIGHT - MENU_HEIGHT ) {
				Button anime = new Button(cardPos, path, fileName, DIM_CARD, true);		

				Color c = DEFAULT_CARD_COLOR;
				for (String t : Bibliotheque.seriesTitle) {
					if (t.equals(fileName)) {
						c = SAVED_CARD_COLOR;
						if (currentOnglet.equals("Anime")) {
											
							Image bookmarks = new Image(new Position(cardPos.getX() + CARD_WIDTH, cardPos.getY()), "./.res/bookmark.png", new Dimension(20, 78));
							container.add(bookmarks);
						}
					}
				}
				
				txt = new Button(new Position(cardPos.getX() - 20, cardPos.getY() + (int) anime.getDimension().getHeight() + 10), fileName, 18, c, DARK_THEME, Font.PLAIN);
				
				if(txt.getTextDimension().getWidth() > DIM_CARD.getWidth())
					txt = adjustTextButton(fileName, anime, cardPos);
				
				txt.setImagePath(path);
				
				cardButtonArray.add(anime);
				txtButtonArray.add(txt);
				
				if (currentOnglet.equals("Biblio")) {
					Button delete = new Button(new Position(cardPos.getX() + CARD_WIDTH, cardPos.getY()), DELETE_ICON, fileName, new Dimension(25, 25), false);
					delButtonArray.add(delete);
					container.add(delete);				
				}
						
				lastDisplayedPos = new Position(cardPos.getX(), cardPos.getY());
				
				lastDisplayedPos.setX(lastDisplayedPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE);				
				
				if (lastDisplayedPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE >= FRAME_WIDTH && index < coverPathArray.size() - 1) {
					lastDisplayedPos.setX(70);
					lastDisplayedPos.setY(lastDisplayedPos.getY() + CARD_HEIGHT + txt.getHeight() + PADDING_CARDS_TOP);
				}
				
				container.add(anime);
				container.add(txt);
								
				indexDisplayView++;
			}
	
			cardPos.setX(cardPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE);				
			
			if (cardPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE >= FRAME_WIDTH && index < coverPathArray.size() - 1) {
				cardPos.setX(70);
				cardPos.setY(cardPos.getY() + CARD_HEIGHT + txt.getHeight() + PADDING_CARDS_TOP);
			}
									
			index++;
		}
				
	    container.repaint();
	    new BodyEvent(cardButtonArray, txtButtonArray, delButtonArray);
	}
	
	
	private void fillBody(String path) {
		
		String parent = new File(parentPathName).getName();
		String[] coloredPath = null;
		
		for (String t : Bibliotheque.seriesTitle) {
			if(t.equals(parent)) {
				String titlePath = Bibliotheque.seriesPath.get(Bibliotheque.seriesTitle.indexOf(parent));
				
				if(OsUtils.isWindows()) {
					titlePath = titlePath.replace(IGlobal.SEPARATOR, IGlobal.SEPARATOR + IGlobal.SEPARATOR);
					parentPathName = parentPathName.replace(IGlobal.SEPARATOR, IGlobal.SEPARATOR + IGlobal.SEPARATOR);

					String[] separated = titlePath.split(parentPathName);
					coloredPath = separated[0].split(IGlobal.SEPARATOR + IGlobal.SEPARATOR);
				}
				else {
					String[] separated = titlePath.split(parentPathName);
					coloredPath = separated[1].split(IGlobal.SEPARATOR);
				}
			}
		}
		
		String folderPath = path ;
			
		FileSearch fs = new FileSearch(folderPath, 1);
		Position cardPos = new Position(70, 190);

		Button txt = new Button(new Position(50,50), "", 18, DEFAULT_CARD_COLOR, Color.red, Font.PLAIN);
						
		ArrayList<String> filesArray = new ArrayList<>();
		filesArray = fs.getFileInDepth();
		Collections.sort(filesArray);
		
		int limitDisplay = (filesArray.contains(COVER_FOLDER_NAME)) ? filesArray.size() - 2 : filesArray.size() - 1;
		
		int index = 0;
		for (String file : filesArray) {
			
			File f = new File(path + IGlobal.SEPARATOR + file);
			if (f.isFile()) continue;
			
			if (!FileSearch.getFileName(file).equalsIgnoreCase(COVER_FOLDER_NAME)) {
				
				String extension = getImageExtension(parentPathName, file);
				Button anime = new Button(cardPos, parentPathName + IGlobal.SEPARATOR + COVER_FOLDER_NAME + IGlobal.SEPARATOR + file + "." + extension, FileSearch.getFileName(file), DIM_CARD, true);
										
				String title = FileSearch.getFileName(file);
				Color c = DEFAULT_CARD_COLOR;
				if (coloredPath != null) {
					for(int i = 0; i < coloredPath.length; i++) {
						if (coloredPath[i].equals(title)) c = SAVED_CARD_COLOR;
					}					
				}
					
				txt = new Button(new Position(cardPos.getX() - 20, cardPos.getY() + (int) anime.getDimension().getHeight() + 10), file, 18, c, DARK_THEME, Font.PLAIN);
				
				if(txt.getTextDimension().getWidth() > DIM_CARD.getWidth())
					txt = adjustTextButton(file, anime, cardPos);
				
				txt.setImagePath(folderPath + IGlobal.SEPARATOR + COVER_FOLDER_NAME + IGlobal.SEPARATOR + file + "." + extension);
				
				cardButtonArray.add(anime);
				txtButtonArray.add(txt);
				
				cardPos.setX(cardPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE);				
				
				// -2 pour le dossier Cover
				if (cardPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE >= FRAME_WIDTH && index < limitDisplay) {
					cardPos.setX(70);
					cardPos.setY(cardPos.getY() + CARD_HEIGHT + txt.getHeight() + PADDING_CARDS_TOP);
				}
				
				container.add(anime);
				container.add(txt);	
				
				index++;
			}	
		}
		
		container.setPreferredSize(new Dimension(CONTAINER_WIDTH, cardPos.getY() + CARD_HEIGHT + txt.getHeight() + PADDING_CARDS_TOP));	
	}
	
	private static Button adjustTextButton(String fileName, Button anime, Position cardPos) {
		
		String[] separated = fileName.split(" ");
		Button txt = new Button(new Position(cardPos.getX() - 20, cardPos.getY() + (int) anime.getDimension().getHeight() + 10), separated[0], 18, Color.white, DARK_THEME, Font.PLAIN);
		Dimension d = txt.getTextDimension();
		String splittedName = "";
		
		int j = 0;
		while (d.getWidth() < DIM_CARD.getWidth()) {
			Color c = DEFAULT_CARD_COLOR;
			for (String t : Bibliotheque.seriesTitle) {
				if (t.equals(fileName)) c = SAVED_CARD_COLOR;
			}
				
			splittedName += separated[j] + " ";

			txt = new Button(
					new Position(
							cardPos.getX() - 20, 
							cardPos.getY() + (int) anime.getDimension().getHeight() + 10),
					splittedName, 
					18,
					c, 
					DARK_THEME, 
					Font.PLAIN
					);
					
			if (new Button(
					new Position(
							cardPos.getX() - 20, 
							cardPos.getY() + (int) anime.getDimension().getHeight() + 10),
					splittedName + separated[j + 1] + "...", 
					18,
					c, 
					DARK_THEME, 
					Font.PLAIN
					).getTextDimension().getWidth() >=  DIM_CARD.getWidth()) break;
			
			d = txt.getTextDimension();
			j++;
		}	
		
		txt.setText(splittedName + "...");
		
		return txt;
	}
	
}
