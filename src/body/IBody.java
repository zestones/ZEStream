package body;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import menu.IMenu;
import utils.UI.Button;
import utils.shape.Position;

public interface IBody extends IMenu {
	
	// Properties of the body
	int BODY_WIDTH = MENU_WIDTH - 40;
	int BODY_HEIGHT = FRAME_HEIGHT - MENU_HEIGHT;
	Dimension DIM_BODY = new Dimension(BODY_WIDTH, BODY_HEIGHT);
	
	String sortAscend = "De A à Z";
	String sortDescend = "De Z à A";
	
	// sort Button
	Button sortButton = new Button(new Position(105, 133), sortAscend, 14, new Color(12, 124, 255), DARK_THEME, Font.PLAIN);
	// return button
	Button backButton = new Button(new Position(0, 25), "Retour", 22, new Dimension(180, 35), Color.white, new Color(189, 1, 1), Font.BOLD);

	Color SAVED_CARD_COLOR = new Color(85, 100, 185);
	Color DEFAULT_CARD_COLOR = Color.white;
}