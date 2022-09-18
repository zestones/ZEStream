package utils.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import menu.IMenu;
import utils.shape.Position;

public class SearchBar extends JTextField implements KeyListener, IMenu {
	private static final long serialVersionUID = 1L;

	private String defaultTxt;
	private boolean isFocused;
	
	private Button search;

	public SearchBar(String defaultTxt, int x, int y, int width, int height) {
		super(defaultTxt);

		this.defaultTxt = defaultTxt;
		
		Font font = new Font("Inter", Font.BOLD, 12);
		setFont(font);

		setBounds(x, y, width, height);
		
		setBorder(new LineBorder(DARK_THEME, 1));
		setBorder(BorderFactory.createCompoundBorder(
		        getBorder(), 
		        BorderFactory.createEmptyBorder(0, 15, 0, 0))
				);
		
		setFocusable(false);
		
		search = new Button(new Position(x + width + 10, y), SEARCH_ICON, "search icon", new Dimension(26, 30), false);
		header.add(search);
				
		setDarkTheme();
		addEventFocusedSearchBar();

		addEventUnFocusedSearchBar(container);
		addEventUnFocusedSearchBar(header);
		
		addEventValidateSearch();
		
		setHoverSearchBarEvent();
//		unsetHoverSearchBar(container);
		unsetHoverSearchBarEvent(header);
	
		trackFocus();
	}
	
	private void addEventValidateSearch() {
		addEventValidateKeyBoard();
		addEventValidateButton();
	}
	
	private void addEventValidateKeyBoard() {
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFiles();
				setDarkTheme();
			}
		});
	}
	
	private void addEventValidateButton() {
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFiles();
			}
		});
	}
	
	private void searchFiles() {
		System.out.println("Text = " + getText());
		setFocusable(false);
	}

	private void setHoverSearchBarEvent() {
		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				setLightTheme();
			}
		});
	}
	
	private void unsetHoverSearchBarEvent(java.awt.Component component) {
		component.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (!isFocused) setDarkTheme();
			}
		});
	}

	private void addEventFocusedSearchBar() {
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setText("");
				setFocusable(true);
				requestFocus();
			}
		});
	}

	private void addEventUnFocusedSearchBar(java.awt.Component component) {

		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
						
				if (getText().isEmpty()) setText(defaultTxt);

				setFocusable(false);
				setDarkTheme();
			}
		});
	}
		
	private void setDarkTheme() {
		setBackground(new Color(80, 80, 80));
		setForeground(Color.white);
	}
	
	private void setLightTheme() {
		setBackground(Color.white);
		setForeground(new Color(105, 105, 105));	
	}

	private void trackFocus() {
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) { isFocused = true; }

			@Override
			public void focusLost(FocusEvent e) { isFocused = false; }
		});
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
