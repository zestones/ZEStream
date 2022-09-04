package settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import home.Home;
import home.IFrame;
import utils.UI.Button;
import utils.UI.PopUp;

public class SettingEvent implements IFrame {
	private static boolean OK = false;
	ArrayList<JPanel> cardButtonArray;
	public SettingEvent(ArrayList<Button> deleteButtonArray, ArrayList<JPanel> cardButtonArray) {
		if (!OK) handleButtonEvent();
		this.cardButtonArray = cardButtonArray;
		handleButtonDelete(deleteButtonArray);
	}
	
	private void handleButtonEvent() {
		OK = true;
		addLocationPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser choose = new JFileChooser(
				        FileSystemView
				        .getFileSystemView()
				        .getHomeDirectory()
				    );
				    
				    choose.setDialogTitle("Choisissez un repertoire: ");
				    choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				    int res = choose.showSaveDialog(null);
				    
				    if(res == JFileChooser.APPROVE_OPTION) {
				    	Home.updatePathFolders(choose.getSelectedFile());

				    	container.removeAll();
						container.revalidate();
						sp.remove(container);
												
						new Setting();
						
						frame.repaint();
				    }
			}
		});
	}
	
	private void handleButtonDelete(ArrayList<Button> deleteButtonArray) {
		for(Button btn : deleteButtonArray) {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
								
					for(int j = 0; j < deleteButtonArray.size(); j++) {
						if(e.getSource() == deleteButtonArray.get(j)) {

							PopUp confirm = new PopUp("Etes-vous sûres de vouloir supprimer le lien ?", "Selectioner une option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
							
							if(confirm.getAnswer() == JOptionPane.YES_OPTION) {
								frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								removePathFromFile(deleteButtonArray.get(j).getName());
								
								// update the current session														
								Home.updatePathFolders();
								
								container.removeAll();
								container.revalidate();
								sp.remove(container);
								
								Setting.subContainer.removeAll();
								Setting.subContainer.revalidate();
								
								new Setting();
							      
								frame.repaint();
							      
								break;
							}
						}
					}
			}});
		}
	}
	
	private void removePathFromFile(String path) {
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
