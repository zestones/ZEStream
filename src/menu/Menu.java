package menu;

import java.awt.BorderLayout;

public class Menu implements IMenu {

	public Menu() {

		header.setOpaque(true);
		header.setPreferredSize(DIM_MENU);
		header.setBackground(DARK_THEME);

		header.add(series);
		series.setActiveButton();

		header.add(biblio);
		header.add(param);

		header.add(searchBar);

		header.setLayout(null);
		frame.add(header, BorderLayout.NORTH);

		new MenuEvent();
	}
}
