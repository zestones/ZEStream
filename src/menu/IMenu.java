package menu;

import java.awt.Dimension;

import javax.swing.JPanel;

import home.IGlobal;

public interface IMenu extends IGlobal {
	
	// Properties of the menu
	int MENU_WIDTH = FRAME_WIDTH;
	int MENU_HEIGHT = 80;
	Dimension DIM_MENU = new Dimension(MENU_WIDTH, MENU_HEIGHT);

	// panel that contain the menu
	JPanel header = new JPanel();
	
	// the button of the menu
	MenuButton anime = new MenuButton("Séries", 20, 20);
	MenuButton biblio = new MenuButton("Bibliothèque", anime.getWidth() + 30, 20);
	MenuButton param = new MenuButton("Paramètres", (int) (anime.getWidth() + biblio.getWidth()) + 30, 20);
	
	String SEARCH_ICON = "./res/search.png";
}
