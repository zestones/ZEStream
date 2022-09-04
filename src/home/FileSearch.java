package home;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class FileSearch {
    private ArrayList<String> filesInDepth = new ArrayList<String>();
    
    public FileSearch(String path, int depth) {
    	File[] file = new File(path).listFiles();
    	
    	if (file == null)
    		new PopUp("<html> Une erreur inatendu est survenue ! Impossible de charger les fichier.. <br/><br/>L'erreur provient très probablement de caractère non reconnue dans le nom du dossier.</html>", "Erreur !", JOptionPane.ERROR_MESSAGE);
    	else 
    		this.filesInDepth = fileScanner(file, depth);    		
    }
       
    private ArrayList<String> fileScanner(File files[], int depth) { 
    	if (depth > 0) {
            for (File file : files) {
        		filesInDepth.add(file.getName());
        		
        		if (file.isDirectory())
        			fileScanner(file.listFiles(), depth - 1);		
            }
        }
        return filesInDepth;
    }
    
    public static String getFileExtension(String file) {
    	String[] parts = file.split("[.]", 0);
    	return parts[1];
    }
    
    public static String getFileName(String file) {
    	String[] parts = file.split("[.]", 0);
    	return parts[0];
    }
    
    public ArrayList<String> getFileInDepth() { return this.filesInDepth; }
    
    public void printFilesInDepth() {
    	for (String files : this.filesInDepth)
            System.out.println(files);
    }
}
