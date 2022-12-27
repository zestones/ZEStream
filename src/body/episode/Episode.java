package body.episode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import body.IBody;
import body.bibliotheque.Bibliotheque;
import body.main.Body;
import filter.FileSearch;
import home.IGlobal;
import menu.IMenu;
import utils.UI.Button;
import utils.UI.Image;
import utils.UI.SearchBar;
import utils.UI.Title;
import utils.shape.Position;

public class Episode implements IGlobal, IEpisode {

	protected ArrayList<Button> episodeButtonArray;
	public static String currentFolderPath;
	public static int BOOKMARKED_POSITION;

	private int index;
	protected static int bookMarksBtn = 0;

	public Episode(String coverPath, String title, String folderName) {

		Body.depth++;
		this.episodeButtonArray = new ArrayList<Button>();

		SearchBar.isSearching = false;

		IMenu.searchBar.setText(IMenu.searchBar.defaultTxt);

		IMenu.searchBar.setFocusable(false);
		IMenu.searchBar.setDarkTheme();

		if (Body.depth == 1) {
			Body.currentPath += folderName;
			currentFolderPath = Body.currentPath;
			Body.parentPathName = currentFolderPath;
		} else {
			Body.currentPath += IGlobal.SEPARATOR + folderName;
			currentFolderPath = Body.currentPath;
		}

		index = -1;
		for (String path : Bibliotheque.seriesPath) {
			if (currentFolderPath.equals(path)) {
				index = Bibliotheque.seriesPath.indexOf(path);
			}
		}

		FileSearch fs = new FileSearch(currentFolderPath, 1);

		container.setBackground(DARK_THEME);
		container.setBorder(new MatteBorder(2, 0, 0, 0, new Color(84, 84, 84)));

		container.setLayout(null);
		container.add(IBody.backButton);

		JPanel infos = new JPanel();

		infos.setBounds(0, 90, FRAME_WIDTH, 275);
		infos.setLayout(null);

		infos.setBackground(new Color(20, 66, 120));

		infos.add(new Image(new Position(25, 25), coverPath, DIM_CARD));
		Title h1 = new Title(title, new Position(25 + CARD_WIDTH + 30, 25), 32, Color.white, Font.PLAIN);
		infos.add(h1);

		// TYPE
		infos.add(new Title("TYPE", new Position(h1.getX() + 30, h1.getHeight() + h1.getY() + 30), 14, Color.white,
				Font.BOLD));

		String type = getType(folderName, title);
		infos.add(new Title(type, new Position(h1.getX() + 150, h1.getHeight() + h1.getY() + 30), 14, Color.white,
				Font.PLAIN));

		// EPISODE
		infos.add(new Title("EPISODES", new Position(h1.getX() + 30, h1.getHeight() + h1.getY() + 65), 14, Color.white,
				Font.BOLD));

		int numberEp;
		if (Body.depth == 1 && new File(Body.parentPathName + SEPARATOR + folderName + Body.COVER_FOLDER_NAME).exists())
			numberEp = fs.getFileInDepth().size() - 1;
		else
			numberEp = fs.getFileInDepth().size();

		infos.add(new Title(Integer.toString(numberEp), new Position(h1.getX() + 150, h1.getHeight() + h1.getY() + 65),
				14, Color.white, Font.PLAIN));

		// DUREE
		infos.add(new Title("DURÉE", new Position(h1.getX() + 30, h1.getHeight() + h1.getY() + 100), 14, Color.white,
				Font.BOLD));
		infos.add(new Title("Inconnu", new Position(h1.getX() + 150, h1.getHeight() + h1.getY() + 100), 14,
				Color.white, Font.PLAIN));

		// SAISON
		infos.add(new Title("SAISON", new Position(h1.getX() + 30, h1.getHeight() + h1.getY() + 135), 14, Color.white,
				Font.BOLD));
		String season = (type.equalsIgnoreCase("Séries") && folderName.contains("Saison"))
				? (folderName.split("[ ]", 0))[1]
				: "";
		infos.add(new Title(season, new Position(h1.getX() + 150, h1.getHeight() + h1.getY() + 135), 14, Color.white,
				Font.PLAIN));

		Boolean canGoto = Bibliotheque.seriesPath.contains(currentFolderPath);
		Button gotoBookmarked = null;
		if (canGoto) {
			gotoBookmarked = new Button(
					new Position(
							h1.getX() + 150 + 17 + (FRAME_WIDTH - (h1.getX() + 150) - 300) / 2,
							(275 - 50) / 2),
					"BOOKMARK", 16,
					new Dimension(300, 50),
					Color.white,
					new Color(20, 66, 120),
					Font.BOLD);

			gotoBookmarked.setBorderPainted(true);
			gotoBookmarked.setBorder(new MatteBorder(1, 0, 1, 0, Color.white));
			infos.add(gotoBookmarked);
		}

		container.add(infos);

		fillBody(fs);

		sp.updateScrollPanelStyle();

		frame.getContentPane().add(sp);
		EpisodeEvent ee = new EpisodeEvent(episodeButtonArray);
		ee.unsetHoverAllButton("");

		if (canGoto)
			ee.gotoBtnEvent(gotoBookmarked);
	}

	private void fillBody(FileSearch fs) {

		ArrayList<String> filesArray = new ArrayList<>();
		filesArray = fs.getFileInDepth();
		Collections.sort(filesArray);

		Position p = new Position(90, 385);
		int i = 0;
		for (String file : filesArray) {
			File f = new File(Body.currentPath + IGlobal.SEPARATOR + file);
			if (f.isDirectory())
				continue;

			Color fore = EPISODE_FOREGROUND;
			Color back = EPISODE_BACKGROUND;

			if (index != -1 && file.toString().equals(Bibliotheque.seriesEpisode.get(index))) {
				fore = EPISODE_MARKED_FOREGROUND;
				back = EPISODE_MARKED_BACKGROUND;
				bookMarksBtn = i;
				BOOKMARKED_POSITION = p.getY();
			}

			Button btn = new Button(p, file, 16, new Dimension(FRAME_WIDTH - 225, 50), fore, back, Font.BOLD);
			episodeButtonArray.add(btn);

			p = new Position(p.getX(), p.getY() + 55);
			container.add(btn);

			i++;
		}

		container.setPreferredSize(new Dimension(FRAME_WIDTH, p.getY()));
	}

	private String getType(String folderName, String title) {
		if (title.equalsIgnoreCase("Movie") || title.equalsIgnoreCase("FILM"))
			return "Film";
		else if (folderName.equalsIgnoreCase("Movie") || folderName.equalsIgnoreCase("FILM"))
			return "Film";
		else if (folderName.equalsIgnoreCase("OAV"))
			return "OAV";
		else if (folderName.equalsIgnoreCase("Spin-Off"))
			return "Spin-Off";
		return "Séries";
	}
}
