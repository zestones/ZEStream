package home;

import java.util.ArrayList;

import javax.swing.JFrame;

import body.bibliotheque.Bibliotheque;
import body.main.Body;
import menu.Menu;

public class Home implements IGlobal {
	
	public static ArrayList<String> foldersPath = new ArrayList<String>();
	public static ArrayList<String> coverPathArray = new ArrayList<String>();
	
	public Home() {
				
		frame.setPreferredSize(DIM_FRAME);
		frame.setResizable(false);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setLocation(100, 100);
		
		frame.setUndecorated(false);
		
		PathManager.getPathFolders();
		
		PathManager.initPathToCovers();
		PathManager.sortPathArray();
		
		Bibliotheque.getBiblioCoverFolders();
	
		new Menu();
		new Body(coverPathArray, "Anime");
		
		frame.pack();
		frame.setVisible(true);  
	}
		
	public static void main(String[] args) {		
		new Home();
	}
}
