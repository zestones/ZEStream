package settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import home.Home;
import home.IGlobal;
import utils.UI.Button;
import utils.UI.Title;
import utils.shape.Position;

public class Setting implements IGlobal, ISetting {
	
    private ArrayList<Button> deleteButtonArray;
    private ArrayList<JPanel> cardButtonArray;
    
    protected static JPanel subContainer = new JPanel();
    
    private static final Dimension PATH_CARD_DIM = new Dimension(FRAME_WIDTH - 222, 110);
    
	public Setting() {
		container.setBackground(DARK_THEME);
		container.setBorder(new MatteBorder(2, 0, 0, 0, new Color(84, 84, 84)));	
	    container.setLayout(null);
	    	    
		container.add(new Title("Emplacement de recherche", new Position(50, 60), 32, Color.white, Font.PLAIN));
		container.add(addLocationPath);
		container.add(new Title("Ajouter un chemin", new Position(addLocationPath.getX() + addLocationPath.getWidth() + 20, addLocationPath.getY() + 10), 28, Color.white, Font.PLAIN));
		
		subContainer.setBounds(100, 180 + addLocationPath.getHeight(), FRAME_WIDTH - 222, (int) (PATH_CARD_DIM.getHeight() + PADDING_CARDS_TOP) * Home.foldersPath.size());
		subContainer.setLayout(null);
		subContainer.setBackground(DARK_THEME);
		
		container.add(subContainer);
		
		this.cardButtonArray = new ArrayList<JPanel>();
		this.deleteButtonArray = new ArrayList<Button>();

		this.fillBody();
		
		new SettingEvent(deleteButtonArray, cardButtonArray);

		sp.updateScrollPanelStyle();

		frame.getContentPane().add(sp);
	}
	
	private void fillBody() {
		Position p = new Position(0, 0);

		for (String folder : Home.foldersPath) {
			JPanel card = new JPanel();
			
			card.setBackground(new Color(55, 55, 55));
			card.setBounds(p.getX(), p.getY(), subContainer.getWidth(), (int) PATH_CARD_DIM.getHeight());
			
			card.setBorder(BorderFactory.createLineBorder(Color.white));
			
			File f = new File(folder);
			Title t = new Title(f.getName(), new Position(20, 20), 22, Color.white, Font.BOLD);
			
			Title path = new Title(folder, new Position(55, 65), 18, Color.white, Font.PLAIN);
			Button delete = new Button(new Position(card.getWidth() - 45, 15), "./.res/delete.png", folder, new Dimension(25, 25));
			
			card.add(t);
			card.add(path);
			card.add(delete);			

			this.deleteButtonArray.add(delete);
			this.cardButtonArray.add(card);
			
			p.setY(p.getY() + PADDING_CARDS_TOP + card.getHeight());
			
			card.setLayout(null);
			subContainer.add(card);
		}
		
	    container.setPreferredSize(new Dimension(FRAME_WIDTH, subContainer.getHeight() + subContainer.getY()));
	}

}
