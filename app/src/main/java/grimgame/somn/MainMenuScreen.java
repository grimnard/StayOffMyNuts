package grimgame.somn;

/**
 * Created by grimnard on 1/19/2015.
 */
import java.util.List;

import grimgame.framework.Graphics;
import grimgame.framework.Game;
import grimgame.framework.Screen;
import grimgame.framework.Input.TouchEvent;

public class MainMenuScreen extends Screen {
    final int PLAY_BUTTON_X=50;
    final int PLAY_BUTTON_Y=350;
    final int PLAY_BUTTON_WIDTH=250;
    final int PLAY_BUTTON_HEIGHT=450;

    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                //Play Button
                if (TouchEvent.inBounds(event, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT)) {
                    game.setScreen(new GameScreen(game));
                }

            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.menu, 0, 0);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
