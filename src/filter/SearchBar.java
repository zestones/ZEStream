package filter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class SearchBar extends JTextField implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	private String defaultTxt;
	
	public SearchBar(String defaultTxt, int x, int y, int width, int height) {
		super(defaultTxt);
		setBounds(x, y, width, height);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("---->"  + e);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("---->"  + e);
		
		if (this.getText() + "d" == defaultTxt+ "d") 
			this.setText("");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("---->"  + e);
		
	}
}
