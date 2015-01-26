package grimgame.framework;

/**
 * Created by grimnard on 1/18/2015.
 */
import java.util.List;

public interface Input {

    public static class TouchEvent {
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;
        public static final int TOUCH_HOLD = 3;

        public int type;
        public int x, y;
        public int pointer;


        public static boolean inBounds(TouchEvent event, int x, int y, int width,
                                       int height) {
            return event.x > x && event.x < x + width - 1 && event.y > y
                    && event.y < y + height - 1;
        }
    }
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
