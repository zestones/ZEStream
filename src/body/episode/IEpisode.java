package body.episode;

import java.awt.Color;

public interface IEpisode {
	
	Color EPISODE_FOREGROUND = new Color(50, 50, 50);
    Color EPISODE_BACKGROUND = new Color(97, 97, 97);
 
    Color EPISODE_MARKED_FOREGROUND = new Color(80, 80, 85);
    Color EPISODE_MARKED_BACKGROUND = Color.red;
    
    String BOOKMARK_ICON = "./res/bookmark-episode.png";
    String REMOVE_ICON = "./res/remove.png";
}
