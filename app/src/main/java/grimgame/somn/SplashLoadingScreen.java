package grimgame.somn;

/**
 * Created by grimnard on 1/18/2015.
 */
import grimgame.framework.Graphics;
import grimgame.framework.Game;
import grimgame.framework.Screen;

public class SplashLoadingScreen extends Screen {
    public SplashLoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.splash= g.newImage( "splash.jpg", Graphics.ImageFormat.RGB565);

        game.setScreen(new LoadingScreen(game));

    }

    @Override
    public void paint(float deltaTime) {

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

    }
}