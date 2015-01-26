package grimgame.framework;

/**
 * Created by grimnard on 1/18/2015.
 */

public interface Image {
    public int getWidth();
    public int getHeight();
    public Graphics.ImageFormat getFormat();
    public void dispose();
}