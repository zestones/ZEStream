package home;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import menu.MenuButton;
import utils.UI.Button;
import utils.shape.Position;

public interface IFrame {
	
	String FILE_FOLDER_PATH = "./.res/folders-path.txt";
	
	JFrame frame = new JFrame("ZeroStream");	
	
	int FRAME_WIDTH = 1078;
	int FRAME_HEIGHT = 706;
	Dimension DIM_FRAME = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
	
	
	int MENU_WIDTH = FRAME_WIDTH;
	int MENU_HEIGHT = 80;
	Dimension DIM_MENU = new Dimension(MENU_WIDTH, MENU_HEIGHT);
	
	Color DARK_THEME = new Color(36, 36, 36);
	
	
	JPanel header = new JPanel();
	MenuButton anime = new MenuButton("Anime", 20, 20);
	MenuButton biblio = new MenuButton("Bibliothèque", anime.getWidth() + 30, 20);
	MenuButton param = new MenuButton("Paramètre", (int) (anime.getWidth() + biblio.getWidth()) + 30, 20);
	
	JPanel container = new JPanel();
	JScrollPane sp =  new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	Button backButton = new Button(new Position(0, 25), "Retour", 22, new Dimension(180, 35), Color.white, new Color(189, 1, 1), Font.BOLD);
	
	int BODY_WIDTH = MENU_WIDTH - 40;
	int BODY_HEIGHT = FRAME_HEIGHT - MENU_HEIGHT;
	Dimension DIM_BODY = new Dimension(BODY_WIDTH, BODY_HEIGHT);
	
	String sortAscend = "De A à Z";
	String sortDescend = "De Z à A";
	
	Button sortButton = new Button(new Position(105, 133), sortAscend, 14, new Color(12, 124, 255), DARK_THEME, Font.PLAIN);
	
	int PADDING_CARDS_SIDE = 65;
	int PADDING_CARDS_TOP = 35;
	int CARD_WIDTH = 175;
	int CARD_HEIGHT = 225;
	Dimension DIM_CARD = new Dimension(CARD_WIDTH, CARD_HEIGHT);
	
	Button addLocationPath = new Button(new Position(80, 140), "./.res/add.png", "add-location-path", new Dimension(70, 70));
}
