package body.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import body.IBody;
import body.bibliotheque.Bibliotheque;
import body.episode.Episode;
import filter.FileSearch;
import home.Home;
import home.IGlobal;
import utils.UI.Button;
import utils.UI.PopUp;
import utils.UI.SearchBar;

public class BodyEvent implements IBody {
	private static boolean OK = false;

	public BodyEvent(ArrayList<Button> cardButtonArray, ArrayList<Button> txtButtonArray,
			ArrayList<Button> delButtonArray) {
		cardEvent(cardButtonArray);
		txtEvent(txtButtonArray);

		if (Body.currentTab.equals(LIBRARY_TAB) && Body.depth < 1)
			delEvent(delButtonArray);
		if (!OK)
			backButton();
	}

	private void backButton() {
		OK = true;
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearBody();
				
				if (Body.depth == 1)
					if (Body.currentTab.equals(SERIES_TAB))
						new Body(Home.coverPathArray, Body.currentTab);
					else
						new Body(Bibliotheque.coverPathArray, Body.currentTab);
				else {
					int sepPos = Body.currentPath.lastIndexOf(IGlobal.SEPARATOR);
					String folderPath = Body.currentPath.substring(0, sepPos);

					new Body(folderPath);
					Body.depth -= 2;
				}

				frame.repaint();
			}
		});

		sortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> tmp = new ArrayList<String>();

				if (!SearchBar.isSearching) {
					if (Body.currentTab.equals(SERIES_TAB))
						tmp = FileSearch.inverseArrayOrder(Home.coverPathArray);
					else
						tmp = FileSearch.inverseArrayOrder(Bibliotheque.coverPathArray);
				} else {
					tmp = FileSearch.inverseArrayOrder(SearchBar.sortedCoverPathArray);
					SearchBar.sortedCoverPathArray = tmp;
				}

				if (sortAscend == sortButton.getText())
					sortButton.setText(sortDescend);
				else
					sortButton.setText(sortAscend);

				container.removeAll();
				container.revalidate();

				if (SearchBar.isSearching) {
					container.add(reset);
					String tab = (Body.currentTab.equals(SERIES_TAB)) ? SERIES_TAB : LIBRARY_TAB;
					new Body(SearchBar.sortedCoverPathArray, tab);
				} else {
					if (Body.currentTab.equals(SERIES_TAB)) {
						Home.coverPathArray = tmp;
						new Body(Home.coverPathArray, SERIES_TAB);
					} else {
						Bibliotheque.coverPathArray = tmp;
						new Body(Bibliotheque.coverPathArray, LIBRARY_TAB);
					}
				}

				frame.repaint();
			}
		});
	}

	private void delEvent(ArrayList<Button> delButtonArray) {
		for (Button btn : delButtonArray) {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int j = 0; j < delButtonArray.size(); j++) {

						if (e.getSource() == delButtonArray.get(j)) {

							PopUp confirm = new PopUp(
									"Etes-vous sûres de vouloir supprimer cette série ?",
									"Selectioner une option", 
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.WARNING_MESSAGE
									);
							
							if (confirm.getAnswer() == JOptionPane.YES_OPTION) {
								clearBody();

								Bibliotheque.removeElementBibliotheque(delButtonArray.get(j).getName());
								sortButton.setText(sortAscend);

								new Body(Bibliotheque.coverPathArray, Body.currentTab);

								frame.repaint();
								break;
							}
						}
					}
				}
			});
		}
	}

	private void txtEvent(ArrayList<Button> txtButtonArray) {
		for (Button btn : txtButtonArray) {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int j = 0; j < txtButtonArray.size(); j++) {

						if (e.getSource() == txtButtonArray.get(j)) {

							String folderPath;

							if (Body.depth == 0) {
								initBodyFirstDepth(txtButtonArray, j);
								break;
							} else
								folderPath = Body.currentPath + IGlobal.SEPARATOR + txtButtonArray.get(j).getName();

							clearBody();

							if (Body.depth >= 1)
								initBodyDepth(txtButtonArray, j, folderPath);

							frame.repaint();
							break;
						}
					}
				}
			});
		}

		container.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				for (int j = 0; j < txtButtonArray.size(); j++) {
					if (txtButtonArray.get(j).txtHoverActive) {
						Color c = txtButtonArray.get(j).getForegroundColor();
						Color update = txtButtonArray.get(j).upadteHoverColor(c, -20);
						txtButtonArray.get(j).setForeground(update);
					}
				}
			}
		});
	}

	private void cardEvent(ArrayList<Button> cardButtonArray) {
		for (Button btn : cardButtonArray) {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					for (int j = 0; j < cardButtonArray.size(); j++) {
						if (e.getSource() == cardButtonArray.get(j)) {

							String folderPath;

							if (Body.depth == 0) {
								initBodyFirstDepth(cardButtonArray, j);
								break;
							} 
							else folderPath = Body.currentPath + IGlobal.SEPARATOR + cardButtonArray.get(j).getName();

							clearBody();
							if (Body.depth >= 1) initBodyDepth(cardButtonArray, j, folderPath);

							frame.repaint();
							break;
						}
					}
				}
			});
		}
	}

	private void initBodyDepth(ArrayList<Button> buttonArray, int index, String folderPath) {
		FileSearch fs = new FileSearch(folderPath, 1);

		if (fs.getFileInDepth().size() > 0) {

			File f = new File(folderPath + IGlobal.SEPARATOR + fs.getFileInDepth().get(0));

			if (f.isDirectory()) new Body(folderPath);
			else new Episode(buttonArray.get(index).getImagePath(), Body.previousPage, buttonArray.get(index).getName());
		} 
		else new Episode(buttonArray.get(index).getImagePath(), Body.previousPage, buttonArray.get(index).getName());
	}

	private void initBodyFirstDepth(ArrayList<Button> buttonArray, int index) {

		int sepPos = buttonArray.get(index).getImagePath().lastIndexOf(IGlobal.SEPARATOR);
		String p = buttonArray.get(index).getImagePath().substring(0, sepPos);

		sepPos = p.lastIndexOf(IGlobal.SEPARATOR);
		String folderPath = p.substring(0, sepPos);

		clearBody();

		FileSearch fs = new FileSearch(folderPath, 1);
		if (fs.getFileInDepth().size() > 0) {
			String firstFile = fs.getFileInDepth().get(0);

			if (firstFile.equalsIgnoreCase(Body.COVER_FOLDER_NAME))
				firstFile = fs.getFileInDepth().get(1);
			File f = new File(folderPath + IGlobal.SEPARATOR + firstFile);

			if (f.isDirectory())
				new Body(folderPath);
			else {
				Body.currentPath = folderPath;
				new Episode(buttonArray.get(index).getImagePath(), new File(folderPath).getName(), "");
			}
		} else
			new Episode(buttonArray.get(index).getImagePath(), Body.previousPage, buttonArray.get(index).getName());
		frame.repaint();
	}

	private void clearBody() {
		container.removeAll();
		container.revalidate();
		sp.remove(container);
	}
}