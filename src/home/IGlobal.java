package home;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import utils.OsUtils;
import utils.UI.ScrollPanel;

public interface IGlobal {
	String SEPARATOR = (OsUtils.isWindows()) ? "\\" : "/";

	// Properties of the frame
	JFrame frame = new JFrame("ZEStream");

	int FRAME_WIDTH = 1078;
	int FRAME_HEIGHT = 706;
	Dimension DIM_FRAME = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);

	// Color of the main Theme
	Color DARK_THEME = new Color(36, 36, 36);
	
	// Panel of the main body of the frame
	JPanel container = new JPanel();
	// The scrollable panel
	ScrollPanel sp =  new ScrollPanel(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	// Properties of a card object
	int PADDING_CARDS_SIDE = 65;
	int PADDING_CARDS_TOP = 35;
	int CARD_WIDTH = 175;
	int CARD_HEIGHT = 225;
	Dimension DIM_CARD = new Dimension(CARD_WIDTH, CARD_HEIGHT);
	
	String IMG_NOT_FOUND = "./res/img-not-found.jpg";
	String DELETE_ICON = "./res/delete.png";

	String SERIES_TAB = "Séries";
	String LIBRARY_TAB = "Biblio";
}