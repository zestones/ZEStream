package home;

import java.util.ArrayList;

import javax.swing.JFrame;

import body.bibliotheque.Bibliotheque;
import body.main.Body;
import menu.Menu;
import utils.OsUtils;

public class Home implements IGlobal {
	
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
		
		PathManager.getPathFolders();
		
		PathManager.initPathToCovers();
		PathManager.sortPathArray();
		
		Bibliotheque.getBiblioCoverFolders();
		
//		printLink();
		
		new Menu();
		new Body(coverPathArray, "Anime");
		
		frame.pack();
		frame.setVisible(true);  
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
