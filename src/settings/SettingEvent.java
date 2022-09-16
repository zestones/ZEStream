package settings;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import body.bibliotheque.Bibliotheque;
import body.main.Body;
import home.Home;
import home.IGlobal;
import home.PathManager;
import utils.UI.Button;
import utils.UI.PopUp;
import utils.shape.Position;

public class SettingEvent implements IGlobal, ISetting {
	private static boolean OK = false;
	
	ArrayList<JPanel> cardButtonArray;
	private JTextField jt;
	private Button submitCoverFolder;
	
	public SettingEvent(ArrayList<Button> deleteButtonArray, ArrayList<JPanel> cardButtonArray) {
		if (!OK) handleButtonEvent();
		this.cardButtonArray = cardButtonArray;
		handleButtonDelete(deleteButtonArray);
		
		handleCoverFolderEvent(Setting.modifyFolderCoverName);
		handleCoverFolderEvent(Setting.folderCoverBtn);
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
				    	PathManager.updatePathFolders(choose.getSelectedFile());

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

							PopUp confirm = new PopUp("Etes-vous sûres de vouloir supprimer le lien ?", "Selectioner une option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
							
							if(confirm.getAnswer() == JOptionPane.YES_OPTION) {
								frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								PathManager.removePathFromFile(deleteButtonArray.get(j).getName());
								
								// update the current session														
								PathManager.updatePathFolders();
								
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
	
	private void handleCoverFolderEvent(Button btn) {
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = Setting.folderCoverBtn.getName();
				Dimension dim = Setting.folderCoverBtn.getDimension();
				Position pos = new Position(Setting.folderCoverBtn.getPosition().getX(), Setting.folderCoverBtn.getPosition().getY());
				
						
				jt = new JTextField(name.length());
				jt.setText(name);
			    jt.setLayout(null);
				jt.setBounds(pos.getX(), pos.getY(), (int) dim.getWidth(), (int) dim.getHeight());
				
				submitCoverFolder = new Button(
						new Position(
								Setting.modifyFolderCoverName.getPosition().getX(),
								Setting.modifyFolderCoverName.getPosition().getY() + 2
								),
						"./res/submit.png", 
						"path", 
						new Dimension(100, (int) dim.getHeight()), 
						false
						);
				
				submitCoverFolder.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String txt = jt.getText();
						
						Setting.folderCoverBtn.setText(txt);
						Setting.folderCoverBtn.setName(txt);
						
						Setting.setFolderCoverName(txt);
						Body.COVER_FOLDER_NAME = txt;
						
						Home.clearArrays();
						PathManager.getPathFolders();
						
						PathManager.initPathToCovers();
						PathManager.sortPathArray(Home.coverPathArray);
						
						Bibliotheque.getBiblioInfosFolder();
						
						
						container.remove(jt);
						container.add(Setting.folderCoverBtn);
						
						container.remove(submitCoverFolder);
						container.add(Setting.modifyFolderCoverName);
						
						container.repaint();	
					}
				});
								
				container.remove(Setting.folderCoverBtn);
				container.add(jt);
				
				container.remove(Setting.modifyFolderCoverName);
				container.add(submitCoverFolder);
				
				container.repaint();
			}
		});
		
	}
}
