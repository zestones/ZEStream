package menu;

import java.awt.BorderLayout;

import filter.SearchBar;
import home.IGlobal;

public class Menu implements IGlobal, IMenu {

	public Menu() {
				
		header.setOpaque(true);
		header.setPreferredSize(DIM_MENU);
		header.setBackground(DARK_THEME);
		
		header.add(anime);
		anime.setActiveButton();
		
		header.add(biblio);		
		header.add(param);		
		
		SearchBar searchBar = new SearchBar("  Rechercher", FRAME_WIDTH - 280, 25, 200, 25);
		header.add(searchBar);
		
		header.setLayout(null);		
		frame.add(header, BorderLayout.NORTH);
		
		new MenuEvent();
	}
}
