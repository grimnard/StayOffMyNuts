package grimgame.somn;

/**
 * Created by grimnard on 1/19/2015.
 */

import android.graphics.Rect;

import grimgame.framework.Image;

public class Tile {

    final char TILE_DIRT = '5';
    final char TILE_GRASS_TOP = '8';
    final char TILE_GRASS_LEFT = '4';
    final char TILE_GRASS_RIGHT = '6';
    final char TILE_GRASS_BOT = '2';
    final char TILE_AIR = '0';
    final char ENEMY_HELIBOY='h';

    final int IMAGE_OFFSET_X = 40, IMAGE_OFFSET_Y = 40;
    public char type;
    public Image tileImage;
    private Robot robot = GameScreen.getRobot();
    private Background bg = GameScreen.getBg1();
    private int tileX, tileY,originalTileX;
    int speedX;
    private Rect tileBorder = new Rect();

    public Tile(int x, int y, char typeChar) {
        tileX = x * IMAGE_OFFSET_X;
        tileY = y * IMAGE_OFFSET_Y;
        originalTileX = x;

        type = typeChar;

        switch (type) {
            case ENEMY_HELIBOY:
                type = TILE_AIR;
                GameScreen.addEnemy(tileX,tileY,GameScreen.ENEMY_TYPE_HELIBOY);
                break;
            case TILE_DIRT:
                tileImage = Assets.tiledirt;
                break;
            case TILE_GRASS_TOP:
                tileImage = Assets.tilegrassTop;
                break;
            case TILE_GRASS_LEFT:
                tileImage = Assets.tilegrassLeft;
                break;
            case TILE_GRASS_RIGHT:
                tileImage = Assets.tilegrassRight;
                break;
            case TILE_GRASS_BOT:
                tileImage = Assets.tilegrassBot;
                break;
            default:
                type = TILE_AIR;
                break;
        }

    }
    public void update(){
        speedX = (bg.getSpeedX()-robot.BACKGROUND_REST_SPEED) * robot.MOVE_SPEED;
        this.update(speedX);

    }
    public void update(int speedX){
        this.update(speedX,false);
    }
    public void update(int speedX,boolean resetImage) {
        if(resetImage){
            robot =  GameScreen.getRobot();
            tileX = originalTileX * IMAGE_OFFSET_X;
            speedX=0;
        }
        tileX += speedX;
        tileBorder.set(tileX, tileY, tileX + IMAGE_OFFSET_X, tileY + IMAGE_OFFSET_Y);


        if (Rect.intersects(tileBorder, robot.rectCollisionAura) && type != TILE_AIR) {
            checkVerticalCollision(robot.rectBodyTop, robot.rectBodyBottom);
            checkSideCollision(robot.rectArmLeft, robot.rectArmRight, robot.rectFootLeft, robot.rectFootRight);
        }

    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public void setTileImage(Image tileImage) {
        this.tileImage = tileImage;
    }

    public void checkVerticalCollision(Rect rtop, Rect rbot) {
        if (Rect.intersects(rtop, tileBorder)) {

        }

        if (Rect.intersects(rbot, tileBorder) && type == TILE_GRASS_TOP) {
            robot.setJumped(false);
            robot.setSpeedY(0);
            robot.setCenterY(tileY - robot.IMAGE_OFFSET_BODY_Y);
        }
    }

    public void checkSideCollision(Rect rectArmLeft, Rect rectArmRight, Rect rectLeftFoot, Rect rectRightFoot) {
        if (type != TILE_DIRT && type != TILE_GRASS_BOT && type != TILE_AIR) {
            if (Rect.intersects(rectArmLeft, tileBorder)) {
                robot.setCenterX(tileX + robot.IMAGE_OFFSET_ARM_X + robot.IMAGE_OFFSET_BODY_X);
                robot.setSpeedX(0);
            } else if (Rect.intersects(rectLeftFoot, tileBorder)) {
                robot.setCenterX(tileX + robot.IMAGE_OFFSET_FOOT_X);
                robot.setSpeedX(0);
            }
            if (Rect.intersects(rectArmRight, tileBorder)) {
                robot.setCenterX(tileX - (robot.IMAGE_OFFSET_ARM_X + robot.IMAGE_OFFSET_BODY_X));
                robot.setSpeedX(0);
            } else if (Rect.intersects(rectRightFoot, tileBorder)) {
                robot.setCenterX(tileX - robot.IMAGE_OFFSET_FOOT_X);
                robot.setSpeedX(0);
            }
        }
    }

}
