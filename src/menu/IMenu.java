package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import home.IGlobal;
import utils.UI.Button;
import utils.UI.SearchBar;
import utils.shape.Position;

public interface IMenu extends IGlobal {
	
	// Properties of the menu
	int MENU_WIDTH = FRAME_WIDTH;
	int MENU_HEIGHT = 80;
	Dimension DIM_MENU = new Dimension(MENU_WIDTH, MENU_HEIGHT);

	// panel that contain the menu
	JPanel header = new JPanel();
	
	// the button of the menu
	MenuButton series = new MenuButton("Séries", 20, 20);
	MenuButton biblio = new MenuButton("Bibliothèque", series.getWidth() + 30, 20);
	MenuButton param = new MenuButton("Paramètres", (int) (series.getWidth() + biblio.getWidth()) + 30, 20);
	
	// the Search bar
	SearchBar searchBar = new SearchBar("Rechercher", FRAME_WIDTH - 280, 25, 200, 30);
	Button reset = new Button(new Position(375, 150), "Cliquer pour annuler la recherche", 14, Color.white, DARK_THEME, Font.BOLD);

	String SEARCH_ICON = "./res/search.png";
}
