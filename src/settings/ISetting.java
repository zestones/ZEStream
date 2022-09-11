package settings;

import java.awt.Dimension;

import utils.UI.Button;
import utils.shape.Position;

public interface ISetting {

	Button addLocationPath = new Button(new Position(80, 140), "./.res/add.png", "add-location-path", new Dimension(70, 70), false);
}
