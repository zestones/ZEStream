package body.bibliotheque;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import body.main.Body;
import filter.FileSearch;
import home.IGlobal;
import utils.UI.PopUp;

public abstract class Bibliotheque {
	
	public static ArrayList<String> coverPathArray = new ArrayList<String>();
	public static ArrayList<String> foldersPath = new ArrayList<String>();
	
	public static ArrayList<String> seriesPath = new ArrayList<String>();
	public static ArrayList<String> seriesEpisode = new ArrayList<String>();
	public static ArrayList<String> seriesParent = new ArrayList<String>();
	public static ArrayList<String> seriesTitle = new ArrayList<String>();

	private static final String FILE = "./.res/bibliotheque.txt";
	
	public static boolean updateBiblioPathFolders(String parent, String ep, String path) {
		boolean success = false;
		if(pathAlreadySaved(new File(parent).getName())) {
			PopUp confirm = new PopUp("Etes-vous sûres de vouloir mettre à jour cette série ?", "Selectioner une option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			if(confirm.getAnswer() == JOptionPane.YES_OPTION) {
				
				updateBibliotheque(parent);
				saveNewEntry(parent, ep, path);
				getBiblioCoverFolders();

				success = true;
			}
		}
		else {
			saveNewEntry(parent, ep, path);
			new PopUp("Serie ajouté à la bibliotheque", "Information", JOptionPane.INFORMATION_MESSAGE);
			success = true;
		}
		
		getBiblioCoverFolders();
		
		return success;
	}
	
	private static void updateBibliotheque(String parent) {
		String title = new File(parent).getName();
		removeElementBibliotheque(title);
	}
	
	private static void clearFile() {
		try {
			FileWriter myWriter = new FileWriter(FILE, false);
			myWriter.write("");
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removeElementBibliotheque(String title) {
		int index = seriesTitle.indexOf(title);
		
		/**
		 * Parent path | title | path to ep | episode
		 */
		
		clearFile();
		for (int i = 0; i < seriesTitle.size(); i++) {
			
			String parent = seriesParent.get(i);
			String path = seriesPath.get(i);
			String episode = seriesEpisode.get(i);
			String t = new File(parent).getName();

			String save = parent + "|" + t + "|" + path + "|" + episode;
			File file = new File(save);
			
			if(!file.isDirectory()) {
				if(i == index) continue;
				try {
					FileWriter myWriter = new FileWriter(FILE, true);
					myWriter.write(file + "\n");
					myWriter.close();
				} catch (IOException ex) {
					System.out.println("An error occurred.");
					ex.printStackTrace();
				}
			}
		}
		
		getBiblioCoverFolders();
	}
	
	private static String getImageExtension(String path, String title) {
		File f = new File(path);
		if(!f.isDirectory() && !f.isFile()) return "";
		
		FileSearch fsImage = new FileSearch(path, 1);
		
		for (String img : fsImage.getFileInDepth()) {
			if (title.equalsIgnoreCase(FileSearch.getFileName(img)))
				return FileSearch.getFileExtension(img);
		}
		
		return "";
	}
	
	private static void saveNewEntry(String parent, String ep, String path) {
		String title = new File(parent).getName();
		
		/**
		 * Parent path | title | path to ep | episode
		 */
		String save = parent + "|" + title + "|" + path + "|" + ep;
		
		File file = new File(save);
		
		if(!file.isDirectory()) {
			foldersPath.add(file.toString());
			try {
				FileWriter myWriter = new FileWriter(FILE, true);
				myWriter.write(file + "\n");
				myWriter.close();
			} catch (IOException ex) {
				System.out.println("An error occurred.");
				ex.printStackTrace();
			}
		}
		else new PopUp("Choisir un dossier !", "Erreur !", JOptionPane.ERROR_MESSAGE);

	}
	
	
	private static boolean pathAlreadySaved(String title) {
		
		for(String t : seriesTitle)
			if (t.equals(title)) return true;
		 
		return false;
	}
	
	public static void getBiblioCoverFolders() {
		coverPathArray.removeAll(coverPathArray);
		seriesEpisode.removeAll(seriesEpisode);
		seriesPath.removeAll(seriesPath);
		seriesTitle.removeAll(seriesTitle);
		seriesParent.removeAll(seriesParent);

		try {
			File myObj = new File(FILE);
			Scanner myReader = new Scanner(myObj);
			
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				
				String[] separated = data.split("\\|");
				String folderPath = separated[0];
				String title = separated[1];
				String path = folderPath + IGlobal.SEPARATOR + Body.COVER_FOLDER_NAME + IGlobal.SEPARATOR;
				String ext = getImageExtension(path, title);

				seriesParent.add(separated[0]);
				seriesTitle.add(separated[1]);
				seriesPath.add(separated[2]);
				seriesEpisode.add(separated[3]);
				coverPathArray.add(path + title + "." + ext);
			}
			
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
