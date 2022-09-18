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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import body.bibliotheque.Bibliotheque;
import body.main.Body;
import filter.StringSimilarity;
import home.Home;
import menu.IMenu;
import utils.shape.Position;

public class SearchBar extends JTextField implements KeyListener, IMenu {
	private static final long serialVersionUID = 1L;

	public static ArrayList<String> sortedCoverPathArray = new ArrayList<String>();
	public static boolean isSearching = false;
	
	
	public String defaultTxt;
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
		search.setContentAreaFilled(false);

		header.add(search);
				
		setDarkTheme();
		addEventFocusedSearchBar();

		addEventUnFocusedSearchBar(container);
		addEventUnFocusedSearchBar(header);
		
		addEventValidateSearch();
		
		setHoverSearchBarEvent();
		unsetHoverSearchBarEvent(container);
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
	
	private ArrayList<String> sortByScore(ArrayList<String> coverPathArray, ArrayList<Double> scoreArray) {
		
        int n = scoreArray.size();
        
        for (int i = 0; i < n - 1; i++) {

        	int min_idx = i;
            for (int j = i + 1; j < n; j++)
                if (scoreArray.get(j) > scoreArray.get(min_idx))
                    min_idx = j;
 
            String temp = coverPathArray.get(min_idx);
            coverPathArray.set(min_idx, coverPathArray.get(i));
            coverPathArray.set(i, temp);
        }
                
        return coverPathArray;
	}
	
	private void searchFiles(String tab, ArrayList<String> searchArray, ArrayList<String> cmp) {
		ArrayList<String> coverPathArray = new ArrayList<String>();
		ArrayList<Double> scoreArray = new ArrayList<Double>();
		
		int index = 0;
		for (String t : searchArray) {
			
			double score = StringSimilarity.similarity(getText(), t);
			if (score > 0.35) {
				coverPathArray.add(cmp.get(index));
				scoreArray.add(score);
			}
			
			index++;
		}
		
		sortedCoverPathArray = sortByScore(coverPathArray, scoreArray);
		isSearching = true;
		
		container.removeAll();
		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				isSearching = false;
				setText(searchBar.defaultTxt);

				setFocusable(false);
				setDarkTheme();
				
				container.removeAll();
				container.revalidate();
								
				if (Body.currentTab.equals(SERIES_TAB)) new Body(Home.coverPathArray, SERIES_TAB);
				else new Body(Bibliotheque.coverPathArray, LIBRARY_TAB);	
				
				container.repaint();
			}
		});
		
		container.add(reset);
		
		new Body(sortedCoverPathArray, tab);
	}
	
	private void searchFiles() {
		if (getText().equals(defaultTxt)) return;
		
		if (Body.currentTab.equals(SERIES_TAB))
			searchFiles(SERIES_TAB, Home.seriesTitle, Home.coverPathArray);
		else if (Body.currentTab.equals(LIBRARY_TAB))
			searchFiles(LIBRARY_TAB, Bibliotheque.seriesTitle, Bibliotheque.coverPathArray);
//		else searchFiles(SERIES_TAB, Home.seriesTitle, Home.coverPathArray);

		setFocusable(false);
		setDarkTheme();
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
				if (getText().equals(defaultTxt)) setText("");
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
		
	public void setDarkTheme() {
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
