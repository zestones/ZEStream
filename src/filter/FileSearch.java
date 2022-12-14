package filter;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import utils.UI.PopUp;

public class FileSearch {
	private ArrayList<String> filesInDepth = new ArrayList<String>();

	public FileSearch(String path, int depth) {
		File[] file = new File(path).listFiles();
		
		if (!new File(path).isDirectory()) {
			new PopUp(
					"<html> "
						+ "Impossible de charger le contenu du dossier ! "
						+ "<br/>"
						+ "Le dossier est vide !"
					+ "</html>",
					"Erreur !",
					JOptionPane.ERROR_MESSAGE);
		}
		else if (file == null)
			new PopUp(
					"<html> Une erreur inatendue est survenue ! Impossible de charger les fichiers... <br/><br/>Vérifier les chemins dans les paramètres !</html>",
					"Erreur !",
					JOptionPane.ERROR_MESSAGE);
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

	public static ArrayList<String> inverseArrayOrder(ArrayList<String> list) {
		ArrayList<String> tmp = new ArrayList<String>();

		for (int i = list.size(); i != 0; i--)
			tmp.add(list.get(i - 1));

		return tmp;
	}

	public ArrayList<String> getFileInDepth() {
		return this.filesInDepth;
	}

	public void printFilesInDepth() {
		for (String files : this.filesInDepth)
			System.out.println(files);
	}
}
