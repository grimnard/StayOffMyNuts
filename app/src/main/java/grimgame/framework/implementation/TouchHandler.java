package grimgame.framework.implementation;

/**
 * Created by grimnard on 1/18/2015.
 */

import android.view.View.OnTouchListener;

import java.util.List;

import grimgame.framework.Input.TouchEvent;

public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
