package grimgame.somn;

/**
 * Created by grimnard on 1/18/2015.
 */
import grimgame.framework.Game;
import grimgame.framework.Graphics;
import grimgame.framework.Screen;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {

        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.menu = g.newImage("menu.png", Graphics.ImageFormat.RGB565);
        Assets.background = g.newImage("background.png", Graphics.ImageFormat.RGB565);
        Assets.character = g.newImage("character.png", Graphics.ImageFormat.ARGB4444);
        Assets.character2 = g.newImage("character2.png", Graphics.ImageFormat.ARGB4444);
        Assets.character3  = g.newImage("character3.png", Graphics.ImageFormat.ARGB4444);
        Assets.characterJump = g.newImage("jumped.png", Graphics.ImageFormat.ARGB4444);
        Assets.characterDown = g.newImage("down.png", Graphics.ImageFormat.ARGB4444);


        Assets.heliboy = g.newImage("heliboy.png", Graphics.ImageFormat.ARGB4444);
        Assets.heliboy2 = g.newImage("heliboy2.png", Graphics.ImageFormat.ARGB4444);
        Assets.heliboy3  = g.newImage("heliboy3.png", Graphics.ImageFormat.ARGB4444);
        Assets.heliboy4  = g.newImage("heliboy4.png", Graphics.ImageFormat.ARGB4444);
        Assets.heliboy5  = g.newImage("heliboy5.png", Graphics.ImageFormat.ARGB4444);



        Assets.tiledirt = g.newImage("tiledirt.png", Graphics.ImageFormat.RGB565);
        Assets.tilegrassTop = g.newImage("tilegrasstop.png", Graphics.ImageFormat.RGB565);
        Assets.tilegrassBot = g.newImage("tilegrassbot.png", Graphics.ImageFormat.RGB565);
        Assets.tilegrassLeft = g.newImage("tilegrassleft.png", Graphics.ImageFormat.RGB565);
        Assets.tilegrassRight = g.newImage("tilegrassright.png", Graphics.ImageFormat.RGB565);

        Assets.button = g.newImage("button.jpg", Graphics.ImageFormat.RGB565);

        //This is how you would load a sound if you had one.
        //Assets.click = game.getAudio().createSound("explode.ogg");


        game.setScreen(new MainMenuScreen(game));

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.splash, 0, 0);
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
