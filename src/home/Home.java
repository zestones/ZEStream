package home;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import body.bibliotheque.Bibliotheque;
import body.main.Body;
import filter.FileSearch;
import menu.Menu;
import utils.OsUtils;
import utils.UI.PopUp;

public class Home implements IFrame {
	
	public static String SEPARATOR;
	public static ArrayList<String> foldersPath = new ArrayList<String>();
	public static ArrayList<String> coverPathArray = new ArrayList<String>();
	
	public Home() {
		
		if(OsUtils.isWindows()) SEPARATOR = "\\";
		else SEPARATOR = "/";
		
		frame.setPreferredSize(DIM_FRAME);
		frame.setResizable(false);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setLocation(100, 100);
		
		frame.setUndecorated(false);
		
		getPathFolders();
		
		initPathToCovers();
		sortPathArray();
		
		Bibliotheque.getBiblioCoverFolders();
		
//		printLink();
		
		new Menu();
		new Body(coverPathArray, "Anime");
		
		frame.pack();
		frame.setVisible(true);  
	}

	public static void updatePathFolders() {

		foldersPath.removeAll(foldersPath);
		coverPathArray.removeAll(coverPathArray);
		
		getPathFolders();		
		initPathToCovers();
		sortPathArray();
	}
		
	public static void updatePathFolders(File path) {
		if(pathAlreadySaved(path.toString())) {
			new PopUp("Ce dossier est déja enregistré !", "Attention !", JOptionPane.WARNING_MESSAGE);
			return;
		}
		else saveFolderPath(path);
		
		initPathToCovers(path.toString());
		sortPathArray();
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
	
	private static void getPathFolders() {
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
		File f = new File(path + SEPARATOR + folder + SEPARATOR + "Cover");
		if(!f.isDirectory() && !f.isFile()) return "";
		
		FileSearch fsImage = new FileSearch(path + SEPARATOR + folder + SEPARATOR + "Cover", 1);
		
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
			
			coverPathArray.add(path + SEPARATOR + file + SEPARATOR + "Cover" + SEPARATOR + file + "." + extension);		
		}
	}
	
	private static void initPathToCovers() {		
		
		for (String path : foldersPath) {
			FileSearch fs = new FileSearch(path, 1);		
			
			String extension = "";
			for (String file : fs.getFileInDepth()) {
				
				File f = new File(path + SEPARATOR + file);
				if (f.isFile()) continue;
					
				extension = getImageExtension(file, path);
				coverPathArray.add(path + SEPARATOR + file + SEPARATOR + "Cover" + SEPARATOR + file + "." + extension);					
			}				
		}
	}
	
	private static void sortPathArray() {
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
	
//	private void printLink() {
//		for (String path : coverPathArray) {
//			System.out.println(path);
//		}
//	}
	
	public static void main(String[] args) {		
		new Home();
	}
}
