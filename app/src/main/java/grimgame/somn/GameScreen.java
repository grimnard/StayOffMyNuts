package grimgame.somn;

/**
 * Created by grimnard on 1/19/2015.
 */

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import grimgame.framework.Graphics;
import grimgame.framework.Game;
import grimgame.framework.Image;
import grimgame.framework.Input.TouchEvent;
import grimgame.framework.Screen;
import grimgame.framework.implementation.AndroidGame;

public class GameScreen extends Screen {

    final static int ENEMY_TYPE_HELIBOY = 1;
    public static ArrayList<Enemy> enemyArrayList = new ArrayList<>();
    public static ArrayList<Tile> tileArray = new ArrayList<>();
    private static Background bg1, bg2;
    private static int backgGroundHeight, backGroundWidth;
    // Variable Setup
    private static Robot robot;
    final int CONTROL_BUTTON_UPARROW_X = 0;
    final int CONTROL_BUTTON_UPARROW_Y = 285;
    final int CONTROL_BUTTON_UPARROW_WIDTH = 65;
    final int CONTROL_BUTTON_UPARROW_HEIGHT = 65;
    final int CONTROL_BUTTON_DOWNARROW_X = 0;
    final int CONTROL_BUTTON_DOWNARROW_Y = 415;
    final int CONTROL_BUTTON_DOWNARROW_WIDTH = 65;
    final int CONTROL_BUTTON_DOWNARROW_HEIGHT = 65;
    final int CONTROL_BUTTON_FIRE_X = 0;
    final int CONTROL_BUTTON_FIRE_Y = 350;
    final int CONTROL_BUTTON_FIRE_WIDTH = 65;
    final int CONTROL_BUTTON_FIRE_HEIGHT = 65;
    final int CONTROL_BUTTON_PAUSE_X = 0;
    final int CONTROL_BUTTON_PAUSE_Y = 0;
    final int CONTROL_BUTTON_PAUSE_WIDTH = 35;
    final int CONTROL_BUTTON_PAUSE_HEIGHT = 35;
    final int CONTROL_MOVE_LEFT_X = 300;
    final int CONTROL_MOVE_RIGHT_X = 500;
    GameState state = GameState.Ready;
    int livesLeft = 2;
    Paint paint, paint2;
    private Image currentSprite, character, character2, character3;
    private Animation animCharacter;

    public GameScreen(Game game) {
        super(game);

        // Initialize game objects here

        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 0);
        robot = new Robot();

        character = Assets.character;
        character2 = Assets.character2;
        character3 = Assets.character3;


        animCharacter = new Animation();
        animCharacter.addFrame(character, 1250);
        animCharacter.addFrame(character2, 50);
        animCharacter.addFrame(character3, 50);
        animCharacter.addFrame(character2, 50);


        currentSprite = animCharacter.getImage();

        loadMap();

        // Defining a paint object
        paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        paint2 = new Paint();
        paint2.setTextSize(100);
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.WHITE);

    }

    public static void addEnemy(int x, int y, int enemyType) {
        Enemy enemy;
        switch (enemyType) {
            case ENEMY_TYPE_HELIBOY:
                enemy = new HeliBoy(x, y);
                enemyArrayList.add(enemy);
                break;
        }
    }

    public static Background getBg1() {
        // TODO Auto-generated method stub
        return bg1;
    }

    public static Background getBg2() {
        // TODO Auto-generated method stub
        return bg2;
    }

    public static Robot getRobot() {
        // TODO Auto-generated method stub
        return robot;
    }

    private void loadMap() {
        ArrayList<String> lines = new ArrayList<>();
        int width = 0;
        backGroundWidth = 0;
        backgGroundHeight = 0;
        Scanner scanner = new Scanner(SampleGame.map);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // no more lines to read
            if (line == null) {
                break;
            }

            if (!line.startsWith("!")) {
                lines.add(line);
                backgGroundHeight++;
                width = Math.max(width, line.length());

            }
        }
        backGroundWidth = width;


        for (int j = 0; j < backgGroundHeight; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {

                if (i < line.length()) {
                    char ch = line.charAt(i);
                    Tile t = new Tile(i, j, ch);
                    tileArray.add(t);
                }

            }
        }

    }

    @Override
    public void update(float deltaTime) {
        List touchEvents = game.getInput().getTouchEvents();

        // We have four separate update methods in this example.
        // Depending on the state of the game, we call different update methods.
        // Refer to Unit 3's code. We did a similar thing without separating the
        // update methods.

        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List touchEvents) {

        // This example starts with a "Ready" screen.
        // When the user touches the screen, the game begins.
        // state now becomes GameState.Running.
        // Now the updateRunning() method will be called!

        if (touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List touchEvents, float deltaTime) {

        // This is identical to the update() method from our Unit 2/3 game.

        // 1. All touch input is handled here:
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = (TouchEvent) touchEvents.get(i);
            switch (event.type) {
                case TouchEvent.TOUCH_DOWN:

                    if (TouchEvent.inBounds(event, CONTROL_BUTTON_UPARROW_X, CONTROL_BUTTON_UPARROW_Y, CONTROL_BUTTON_UPARROW_WIDTH, CONTROL_BUTTON_UPARROW_HEIGHT)) {
                        //UpArrow
                        if (!robot.isJumped()) {
                            robot.jump();
                            currentSprite = animCharacter.getImage();
                            robot.setDucked(false);
                        }

                    } else if (TouchEvent.inBounds(event, CONTROL_BUTTON_FIRE_X, CONTROL_BUTTON_FIRE_Y, CONTROL_BUTTON_FIRE_WIDTH, CONTROL_BUTTON_FIRE_HEIGHT) ||
                            TouchEvent.inBounds(event,robot.getCenterX()-robot.IMAGE_OFFSET_BODY_X,robot.getCenterY()-robot.IMAGE_OFFSET_BODY_Y,robot.IMAGE_OFFSET_BODY_X*2,robot.IMAGE_OFFSET_BODY_Y*2)) {
                        //Fire
                        if (!robot.isDucked() && !robot.isJumped()
                                && robot.isReadyToFire()) {
                            robot.shoot();

                        }
                        break;
                    } else if (TouchEvent.inBounds(event, CONTROL_BUTTON_DOWNARROW_X, CONTROL_BUTTON_DOWNARROW_Y, CONTROL_BUTTON_DOWNARROW_WIDTH, CONTROL_BUTTON_DOWNARROW_HEIGHT)
                            ) {
                        //DownArrow
                        if (!robot.isJumped()) {
                            currentSprite = Assets.characterDown;
                            robot.setDucked(true);
                            robot.setSpeedX(0);

                        }
                        break;
                    } else {
                        if (event.x > CONTROL_MOVE_RIGHT_X) {
                            // Move right.
                            robot.moveRight();
                            robot.setMovingRight(true);
                        }
                        if (event.x < CONTROL_MOVE_LEFT_X && !(TouchEvent.inBounds(event, CONTROL_BUTTON_UPARROW_X, CONTROL_BUTTON_UPARROW_Y, CONTROL_BUTTON_UPARROW_WIDTH, CONTROL_BUTTON_UPARROW_HEIGHT))) {
                            //Move Left
                            robot.moveLeft();
                            robot.setMovingLeft(true);
                        }
                    }


                    break;

                case TouchEvent.TOUCH_UP:

                    if (TouchEvent.inBounds(event, CONTROL_BUTTON_UPARROW_X, CONTROL_BUTTON_UPARROW_Y, CONTROL_BUTTON_UPARROW_WIDTH, CONTROL_BUTTON_UPARROW_HEIGHT)) {
                        //UpArrow
                        break;
                    } else if (TouchEvent.inBounds(event, CONTROL_BUTTON_FIRE_X, CONTROL_BUTTON_FIRE_Y, CONTROL_BUTTON_FIRE_WIDTH, CONTROL_BUTTON_FIRE_HEIGHT)) {
                        //Fire
                        break;
                    } else if (TouchEvent.inBounds(event, CONTROL_BUTTON_DOWNARROW_X, CONTROL_BUTTON_DOWNARROW_Y, CONTROL_BUTTON_DOWNARROW_WIDTH, CONTROL_BUTTON_DOWNARROW_HEIGHT)
                            ) {
                        //DownArrow
                        currentSprite = animCharacter.getImage();
                        robot.setDucked(false);
                        break;
                    } else {
                        robot.stopLeft();
                        robot.stopRight();
                    }


                    break;
            }
        }

        // 2. Check miscellaneous events like death:


        // 3. Call individual update() methods here.
        // This is where all the game updates happen.
        // For example, robot.update();
        robot.update();
        if (robot.isJumped()) {
            currentSprite = Assets.characterJump;
        } else if (!robot.isJumped() && !robot.isDucked()) {
            currentSprite = animCharacter.getImage();
        }

        ArrayList projectiles = robot.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            if (p.isVisible()) {
                p.update();
            } else {
                projectiles.remove(i);
            }
        }

        updateTiles();
        for (int j = 0; j < enemyArrayList.size(); j++) {
            Enemy enemy = (Enemy) enemyArrayList.get(j);
            if (enemy.dead) {
                GameScreen.enemyArrayList.remove(j);
            } else {
                enemy.update();
            }
        }
        bg1.update();
        bg2.update();
        animate();

        if (robot.getCenterY() > AndroidGame.GAME_DIMENSION_Y + (robot.IMAGE_OFFSET_BODY_Y * 2)) {
            this.setLivesLeft(livesLeft--);
        }
    }


    private void updatePaused(List touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = (TouchEvent) touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (TouchEvent.inBounds(event, 0, 0, 800, 240)) {

                    if (!TouchEvent.inBounds(event, 0, 0, 35, 35)) {
                        resume();
                    }
                }

                if (TouchEvent.inBounds(event, 0, 240, 800, 240)) {
                    nullify();
                    goToMenu();
                }
            }
        }
    }

    public void setLivesLeft(int livesLeft) {
        this.livesLeft = livesLeft;
        if (this.livesLeft <= 0) {
            state = GameState.GameOver;
        } else {
            robot = null;
            robot = new Robot();
            resetTiles();
            state = GameState.Ready;
            System.gc();
        }
    }

    private void updateGameOver(List touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = (TouchEvent) touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (TouchEvent.inBounds(event, 0, 0, 800, 480)) {
                    nullify();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }

    }

    private void resetTiles() {
        for (int i = 0; i < tileArray.size(); i++) {
            Tile t = (Tile) tileArray.get(i);
            t.update(i, true);
        }
    }

    private void updateTiles() {

        for (int i = 0; i < tileArray.size(); i++) {
            Tile t = (Tile) tileArray.get(i);
            t.update();
        }

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
        g.drawImage(Assets.background, bg2.getBgX(), bg2.getBgY());
        paintTiles(g);

        ArrayList projectiles = robot.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            g.drawRect(p.getX(), p.getY(), 10, 5, Color.YELLOW);
        }
        // First draw the game elements.

        g.drawImage(currentSprite, robot.getCenterX() - 61,
                robot.getCenterY() - 63);
        for (int i = 0; i < enemyArrayList.size(); i++) {
            Enemy enemy = enemyArrayList.get(i);
            g.drawImage(enemy.getImage(), enemy.getCenterX() - enemy.getImageOffsetX(), enemy.getCenterY() - enemy.getImageOffsetY());

        }
        // Example:
        // g.drawImage(Assets.background, 0, 0);
        // g.drawImage(Assets.character, characterX, characterY);

        // Secondly, draw the UI above the game elements.
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

    }

    private void paintTiles(Graphics g) {
        for (int i = 0; i < tileArray.size(); i++) {
            Tile t = tileArray.get(i);
            if (t.type != '0') {
                g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY());
            }
        }
    }

    public void animate() {
        animCharacter.update(10);
        for (int i = 0; i < enemyArrayList.size(); i++) {
            Enemy enemy = enemyArrayList.get(i);
            enemy.updateAnimation();

        }
    }

    private void nullify() {

        // Set all variables to null. You will be recreating them in the
        // constructor.
        paint = null;
        bg1 = null;
        bg2 = null;
        robot = null;
        for (int i = 0; i < enemyArrayList.size(); i++) {
            Enemy enemy = enemyArrayList.get(i);
            enemy.nullify();
            enemyArrayList.remove(i);

        }
        currentSprite = null;
        character = null;
        character2 = null;
        character3 = null;
        animCharacter = null;

        // Call garbage collector to clean up memory.
        System.gc();

    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap to Start.", 400, 240, paint);

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.button, 0, 285, 0, 0, 65, 65);
        g.drawImage(Assets.button, 0, 350, 0, 65, 65, 65);
        g.drawImage(Assets.button, 0, 415, 0, 130, 65, 65);
        g.drawImage(Assets.button, 0, 0, 0, 195, 35, 35);

    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Resume", 400, 165, paint2);
        g.drawString("Menu", 400, 360, paint2);

    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER.", 400, 240, paint2);
        g.drawString("Tap to return.", 400, 290, paint);

    }

    @Override
    public void pause() {
        if (state == GameState.Running)
            state = GameState.Paused;

    }

    @Override
    public void resume() {
        if (state == GameState.Paused)
            state = GameState.Running;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    }

    private void goToMenu() {
        // TODO Auto-generated method stub
        game.setScreen(new MainMenuScreen(game));

    }

    enum GameState {
        Ready, Running, Paused, GameOver
    }

}