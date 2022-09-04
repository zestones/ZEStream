package home;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public abstract class Bibliotheque {
	
	public static ArrayList<String> coverPathArray = new ArrayList<String>();
	public static ArrayList<String> foldersPath = new ArrayList<String>();
	
	public static ArrayList<String> seriesPath = new ArrayList<String>();
	public static ArrayList<String> seriesEpisode = new ArrayList<String>();
	public static ArrayList<String> seriesParent = new ArrayList<String>();
	public static ArrayList<String> seriesTitle = new ArrayList<String>();

	
	private static final String FILE = "./.res/bibliotheque.txt";
	
	public static void updateBiblioPathFolders(String parent, String ep, String path) {
		getBiblioCoverFolders();

		if(pathAlreadySaved(new File(parent).getName())) {
			PopUp confirm = new PopUp("Etes-vous sûres de vouloir mettre à jour cette série ?", "Selectioner une option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
			if(confirm.getAnswer() == JOptionPane.YES_OPTION) {
				updateBibliotheque(parent);
				saveFolderPath(parent, ep, path);
				getBiblioCoverFolders();
			}
		}
		else {
			saveFolderPath(parent, ep, path);
			new PopUp("Serie ajouté à la bibliotheque", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private static void updateBibliotheque(String parent) {
		String title = new File(parent).getName();
		int index = seriesTitle.indexOf(title);
		

		File inputFile = new File(FILE);
		File tempFile = new File("./.res/tmp.txt");

		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			writer = new BufferedWriter(new FileWriter(tempFile));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String lineToRemove = parent + "|" + title + "|" + seriesPath.get(index) + "|" + seriesEpisode.get(index);
		String currentLine;

		try {
			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
			    String trimmedLine = currentLine.trim();
			    if(trimmedLine.equals(lineToRemove)) continue;
			    writer.write(currentLine + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer.close();
			reader.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		tempFile.renameTo(inputFile);
	}
	
	public static void removeElementBibliotheque(String title) {
		int index = seriesTitle.indexOf(title);
		
		String parent = seriesParent.get(index);
		String path = seriesPath.get(index);
		String episode = seriesEpisode.get(index);
		
		File inputFile = new File(FILE);
		File tempFile = new File("./.res/tmp.txt");

		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			writer = new BufferedWriter(new FileWriter(tempFile));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String lineToRemove = parent + "|" + title + "|" + path + "|" + episode;
		String currentLine;

		try {
			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
			    String trimmedLine = currentLine.trim();
			    if(trimmedLine.equals(lineToRemove)) continue;
			    writer.write(currentLine + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer.close();
			reader.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		tempFile.renameTo(inputFile);
		
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
	
	private static void saveFolderPath(String parent, String ep, String path) {
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
	
	static void getBiblioCoverFolders() {
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
				String path = folderPath + "/Cover/";
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
