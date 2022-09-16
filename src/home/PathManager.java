package home;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import body.main.Body;
import filter.FileSearch;
import utils.UI.PopUp;

public abstract class PathManager extends Home {
	private static final String FILE_FOLDER_PATH = "./.res/folders-path.txt";

	public static void updatePathFolders() {

		foldersPath.removeAll(foldersPath);
		coverPathArray.removeAll(coverPathArray);
		
		getPathFolders();		
		initPathToCovers();
		sortPathArray(coverPathArray);
	}
		
	public static void updatePathFolders(File path) {
		if(pathAlreadySaved(path.toString())) {
			new PopUp("Ce dossier est déja enregistré !", "Attention !", JOptionPane.WARNING_MESSAGE);
			return;
		}
		else saveFolderPath(path);
		
		initPathToCovers(path.toString());
		sortPathArray(coverPathArray);
	}
	
	private static void saveFolderPath(File path) {
		if(path.isDirectory()) {
			foldersPath.add(path.toString());
			try {
				FileWriter myWriter = new FileWriter(FILE_FOLDER_PATH, true);
				myWriter.write(path + "\n");
				myWriter.close();
			} catch (IOException ex) {
				System.out.println("An error occurred.");
				ex.printStackTrace();
			}
		}
		else new PopUp("Choisir un dossier !", "Erreur !", JOptionPane.ERROR_MESSAGE);
	}
	
	private static boolean pathAlreadySaved(String path) {
		try {
			File myObj = new File(FILE_FOLDER_PATH);
			try (Scanner myReader = new Scanner(myObj)) {
				
				while (myReader.hasNextLine()) {
					
					String data = myReader.nextLine();
					if(data.equalsIgnoreCase(path)) return true;
					
				}  
				myReader.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		 
		return false;
	}
	
	public static void getPathFolders() {
		try {
			File myObj = new File(FILE_FOLDER_PATH);
			Scanner myReader = new Scanner(myObj);
			
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				foldersPath.add(data);
			}
			
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	private static String getImageExtension(String folder, String path) {
		File f = new File(path + SEPARATOR + folder + SEPARATOR + Body.COVER_FOLDER_NAME);
		if(!f.isDirectory() && !f.isFile()) return "";
		
		FileSearch fsImage = new FileSearch(path + SEPARATOR + folder + SEPARATOR + Body.COVER_FOLDER_NAME, 1);
		
		for (String img : fsImage.getFileInDepth()) {
			if (folder.equalsIgnoreCase(FileSearch.getFileName(img)))
				return FileSearch.getFileExtension(img);
		}
		
		return "";
	}
	
	private static void initPathToCovers(String path) {		
		
		FileSearch fs = new FileSearch(path, 1);		
		
		String extension = "";
		for (String file : fs.getFileInDepth()) {
			
			File f = new File(path + SEPARATOR + file);
			if (f.isFile()) continue;
			
			extension = getImageExtension(file, path);
			
			coverPathArray.add(path + SEPARATOR + file + SEPARATOR + Body.COVER_FOLDER_NAME + SEPARATOR + file + "." + extension);		
		}
	}
	
	public static void initPathToCovers() {		
		
		for (String path : foldersPath) {
			FileSearch fs = new FileSearch(path, 1);		
			
			String extension = "";
			for (String file : fs.getFileInDepth()) {
				
				File f = new File(path + SEPARATOR + file);
				if (f.isFile()) continue;
					
				extension = getImageExtension(file, path);
				coverPathArray.add(path + SEPARATOR + file + SEPARATOR + Body.COVER_FOLDER_NAME + SEPARATOR + file + "." + extension);					
			}				
		}
	}
	
	public static void sortPathArray(ArrayList<String> coverPathArray) {
		String separator = SEPARATOR;
		
		int pos;
        String temp;
        for (int i = 0; i < coverPathArray.size(); i++) { 
            pos = i; 
            
            int sepPos1, sepPos2;
			String p1, p2;
			
			for (int j = i + 1; j < coverPathArray.size(); j++) {
				sepPos1 = coverPathArray.get(j).lastIndexOf(separator) + 1;
				p1 = coverPathArray.get(j).substring(sepPos1).trim();
				
				sepPos2 = coverPathArray.get(pos).lastIndexOf(separator) + 1;
				p2 = coverPathArray.get(pos).substring(sepPos2).trim();

				if (p1.compareTo(p2) < 0) pos = j;
            }

            temp = coverPathArray.get(pos);
            coverPathArray.set(pos, coverPathArray.get(i)); 
            coverPathArray.set(i, temp); 
        } 	
	}
	
	public static void removePathFromFile(String path) {
		ArrayList<String> lines = new ArrayList<String>();
			
		File myObj = new File(FILE_FOLDER_PATH);
		
		try (Scanner myReader = new Scanner(myObj)) {
			while (myReader.hasNextLine()) {
				
				String data = myReader.nextLine();
				
				if(!data.equalsIgnoreCase(path))
					lines.add(data);
			}
			  
			myReader.close();
			
			FileWriter myWriter = new FileWriter(FILE_FOLDER_PATH);

			for (String line : lines) myWriter.write(line + "\n");
			
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	 
	}
}
