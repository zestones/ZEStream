package settings;

import java.awt.Dimension;

import home.IGlobal;
import utils.UI.Button;
import utils.shape.Position;

public interface ISetting {

	String ADD_ICON = "./res/add.png";
	String SUBMIT_ICON = "./res/submit.png";
	String MODIFY_ICON = "./res/modify.png";

	String FILE_COVER_NAME = "./res/cover_name.txt";

	 Dimension PATH_CARD_DIM = new Dimension(IGlobal.FRAME_WIDTH - 222, 110);

	Button addLocationPath = new Button(new Position(80, 140), ADD_ICON, "add-location-path", new Dimension(70, 70), false);
}
