package settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import home.IGlobal;
import home.PathManager;
import utils.UI.Button;
import utils.UI.PopUp;

public class SettingEvent implements IGlobal, ISetting {
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
}
