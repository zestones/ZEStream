package home;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class PopUp extends JOptionPane implements IFrame {
	private static final long serialVersionUID = 1L;
	
	private int answer;
	
	public PopUp(String txt, String title, int TYPE_MESSAGE) {

		updateBackgroundColor();		
		 
		JLabel label = updateMessageStyle(txt, 14, Font.PLAIN);
		
		showMessageDialog(frame, label, title, TYPE_MESSAGE);
	}
	
	public PopUp(String txt, String title, int OPTION, int TYPE_MESSAGE) {
		updateBackgroundColor();	
		
		JLabel label = updateMessageStyle(txt, 14, Font.PLAIN);
		
		answer = JOptionPane.showConfirmDialog(frame, label, title, OPTION, TYPE_MESSAGE);							    
	}
	
	public int getAnswer() { return this.answer; }
	
	
	private void updateBackgroundColor() {
		UIManager.put("OptionPane.background", new Color(87, 87, 87));
		UIManager.put("Panel.background", new Color(87, 87, 87));
	}
	
	private JLabel updateMessageStyle(String txt, int size, int FONT_STYLE) {
		JLabel label = new JLabel(txt);
		
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		label.setForeground(Color.white);
		
		return label;
	}
}
