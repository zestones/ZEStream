package body.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import body.bibliotheque.Bibliotheque;
import filter.FileSearch;
import home.IFrame;
import utils.UI.Button;
import utils.UI.Title;
import utils.shape.Position;

public class Body implements IFrame {
	
    private ArrayList<Button> cardButtonArray;
    private ArrayList<Button> txtButtonArray;
    private ArrayList<Button> delButtonArray;

    public static String parentPathName;
    static String subFolders; 
    public static int depth;
	public static String previousPage;
	public static String currentPath;
	
	static String currentOnglet; 
	
	final static int CONTAINER_WIDTH = FRAME_WIDTH;
	
	public Body(ArrayList<String> coverPathArray, String onglet) {
		currentOnglet = onglet;
		depth = 0;
		previousPage = "";
		currentPath = "";
		
		container.setBackground(DARK_THEME);
		container.setBorder(new MatteBorder(2, 0, 0, 0, new Color(84, 84, 84)));
		sp.getVerticalScrollBar().setValue(0);
		
	    container.setLayout(null);
	    	    
		container.add(new Title("Parcourir les Animes", new Position(50, 60), 32, Color.white, Font.PLAIN));
		container.add(new Title("Filtrer : ", new Position(50, 125), 14, Color.white, Font.PLAIN));
		// TODO : Filter !!
		container.add(sortButton);
		
		this.cardButtonArray = new ArrayList<Button>();
		this.txtButtonArray = new ArrayList<Button>();
		this.delButtonArray = new ArrayList<Button>();

		this.fillBody(coverPathArray);		
		new BodyEvent(cardButtonArray, txtButtonArray, delButtonArray);

		sp.getViewport().add(container);
		sp.setBorder(BorderFactory.createEmptyBorder());	
		sp.getVerticalScrollBar().setUnitIncrement(20);
		
		UIManager.put("ScrollBar.width", 22);

		sp.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
		    @Override
		    protected void configureScrollBarColors() {
		        this.thumbColor = new Color(200, 185, 200);
		    }
		});
		
		UIManager.put("ScrollBar.width", 17);

		frame.getContentPane().add(sp);
	}
	
	public Body(String folderPath) {
		depth++;
				
		if (depth == 1) parentPathName = folderPath;
				
		container.setBackground(DARK_THEME);
		container.setBorder(new MatteBorder(2, 0, 0, 0, new Color(84, 84, 84)));
		sp.getVerticalScrollBar().setValue(0);
		sp.getVerticalScrollBar().setUnitIncrement(20);

	    container.setLayout(null);

		container.add(backButton);
		
		String title = new File(folderPath).getName();
		
		Title pageTitle = new Title(title, new Position(50, 90), 32, Color.white, Font.BOLD);
		pageTitle.setUnderline(6);
		
		container.add(pageTitle);
	    // TODO : add cards events ! 
		this.cardButtonArray = new ArrayList<Button>();
		this.txtButtonArray = new ArrayList<Button>();

		currentPath = folderPath;
		previousPage = title;
				
		this.fillBody(folderPath);
		new BodyEvent(cardButtonArray, txtButtonArray, delButtonArray);

		sp.getViewport().add(container);
		sp.setBorder(BorderFactory.createEmptyBorder());	
		
		frame.getContentPane().add(sp);
	}
	
	private String getImageExtension(String path, String file) {
		File f = new File(path + "/" + "Cover");
		if (!f.isDirectory() || f.isFile()) return "";
		
		FileSearch fsImage = new FileSearch(path + "/Cover", 1);
				
		for (String img : fsImage.getFileInDepth()) {
			if (file.equalsIgnoreCase(FileSearch.getFileName(img)))
				return FileSearch.getFileExtension(img);
		}
		
		return "";
	}
	
	private void fillBody(String path) {
		
		String parent = new File(parentPathName).getName();
		String[] coloredPath = null;
		for (String t : Bibliotheque.seriesTitle) {
			if(t.equals(parent)) {

				String titlePath = Bibliotheque.seriesPath.get(Bibliotheque.seriesTitle.indexOf(parent));
				String[] separated = titlePath.split(parentPathName);
				coloredPath = separated[1].split("/");
			}
		}
		
		String folderPath = path ;
			
		FileSearch fs = new FileSearch(folderPath, 1);
		Position cardPos = new Position(70, 190);

		Button txt = new Button(new Position(50,50), "test", 18, Color.white, Color.red, Font.PLAIN);
						
		ArrayList<String> filesArray = new ArrayList<>();
		filesArray = fs.getFileInDepth();
		Collections.sort(filesArray);
		
		int limitDisplay = (filesArray.contains("Cover")) ? filesArray.size() - 2 : filesArray.size() - 1;
		
		int index = 0;
		for (String file : filesArray) {
			
			File f = new File(path + "/" + file);
			if (f.isFile()) continue;
			
			if (!FileSearch.getFileName(file).equalsIgnoreCase("Cover")) {
				
				String extension = getImageExtension(parentPathName, file);
				Button anime = new Button(cardPos, parentPathName + "/Cover/" + file + "." + extension, FileSearch.getFileName(file), DIM_CARD);
										
				String title = FileSearch.getFileName(file);
				Color c = Color.white;
				if (coloredPath != null) {
					for(int i = 0; i < coloredPath.length; i++) {
						if (coloredPath[i].equals(title)) c = new Color(85, 100, 185);
					}					
				}
					
				txt = new Button(new Position(cardPos.getX() - 20, cardPos.getY() + (int) anime.getDimension().getHeight() + 10), title, 18, c, DARK_THEME, Font.PLAIN);
				txt.setImagePath(folderPath + "/Cover/" + file + "." + extension);
				
				this.cardButtonArray.add(anime);
				this.txtButtonArray.add(txt);
				
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
	
	private void fillBody(ArrayList<String> coverPathArray) {

		Position cardPos = new Position(70, 190);
		Button txt = new Button(new Position(50,50), "", 18, Color.white, Color.red, Font.PLAIN);
				
		int index = 0;
		for (String path : coverPathArray) {
						
			File file = new File(path);
			String fileName = FileSearch.getFileName(file.getName());
			
			Button anime = new Button(cardPos, path, fileName, DIM_CARD);		
			
			Color c = Color.white;
			for (String t : Bibliotheque.seriesTitle) {
				if (t.equals(fileName)) c = new Color(85, 100, 185);
			}
			
			txt = new Button(new Position(cardPos.getX() - 20, cardPos.getY() + (int) anime.getDimension().getHeight() + 10), fileName, 18, c, DARK_THEME, Font.PLAIN);
					
			if(txt.getTextDimension().getWidth() > DIM_CARD.getWidth())
				txt = adjustTextButton(fileName, anime, cardPos);
			
			txt.setImagePath(path);
			
			this.cardButtonArray.add(anime);
			this.txtButtonArray.add(txt);

			if (currentOnglet.equals("Biblio")) {
				Button delete = new Button(new Position(cardPos.getX() + CARD_WIDTH, cardPos.getY()), "./.res/delete.png", fileName, new Dimension(25, 25));
				delButtonArray.add(delete);
				container.add(delete);				
			}
			
			cardPos.setX(cardPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE);				
			
			if (cardPos.getX() + CARD_WIDTH + PADDING_CARDS_SIDE >= FRAME_WIDTH && index < coverPathArray.size() - 1) {
				cardPos.setX(70);
				cardPos.setY(cardPos.getY() + CARD_HEIGHT + txt.getHeight() + PADDING_CARDS_TOP);
			}
						
			container.add(anime);
			container.add(txt);
			
			index++;
		}	
	    container.setPreferredSize(new Dimension(CONTAINER_WIDTH, cardPos.getY() + CARD_HEIGHT + txt.getHeight() + PADDING_CARDS_TOP));
	}
	
	private Button adjustTextButton(String fileName, Button anime, Position cardPos) {
		
		String[] separated = fileName.split(" ");
		Button txt = new Button(new Position(cardPos.getX() - 20, cardPos.getY() + (int) anime.getDimension().getHeight() + 10), separated[0], 18, Color.white, DARK_THEME, Font.PLAIN);
		Dimension d = txt.getTextDimension();
		String splittedName = "";
		
		int j = 0;
		while (d.getWidth() < DIM_CARD.getWidth()) {
			Color c = Color.white;
			for (String t : Bibliotheque.seriesTitle) {
				if (t.equals(fileName)) c = Color.blue;
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
