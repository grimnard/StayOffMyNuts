package grimgame.somn;

/**
 * Created by grimnard on 1/19/2015.
 */

import android.graphics.Rect;

import java.util.ArrayList;

import grimgame.framework.implementation.AndroidGame;

public class Robot {

    // Constants are Here
    final int MOVE_BORDER_LEFT = 325;
    final int MOVE_BORDER_RIGHT = AndroidGame.GAME_DIMENSION_X - MOVE_BORDER_LEFT;
    final int JUMP_SPEED = -20;
    final int MOVE_SPEED = 5;
    final int BACKGROUND_SCROLL_RIGHT = -1;
    final int BACKGROUND_SCROLL_LEFT = 1;
    final int BACKGROUND_REST_SPEED = 0;
    final int STARTING_CENTER_X = 100;
    private int centerX = STARTING_CENTER_X;
    final int STARTING_CENTER_Y = 377;
    private int centerY = STARTING_CENTER_Y;
    final int IMAGE_OFFSET_BODY_X = 34;
    final int IMAGE_OFFSET_BODY_Y = 63;
    final int IMAGE_OFFSET_ARM_X = 26;
    final int IMAGE_OFFSET_ARM_Y = 32;
    final int IMAGE_OFFSET_ARM_SIZE = 20;
    final int IMAGE_OFFSET_FOOT_X = 50;
    final int IMAGE_OFFSET_FOOT_Y = 20;
    final int IMAGE_OFFSET_FOOT_SIZE = 15;
    final int COLLISION_AURA_Y_UP = -110;
    final int COLLISION_AURA_Y_DOWN = 110;
    final int COLLISION_AURA_X_LEFT = -110;
    final int COLLISION_AURA_X_RIGHT = 110;
    final int PROJECTILE_START_X = 50;
    final int PROJECTILE_START_Y = -25;
    public Rect rectBodyTop = new Rect(0, 0, 0, 0);
    public Rect rectBodyBottom = new Rect(0, 0, 0, 0);
    public Rect rectArmLeft = new Rect(0, 0, 0, 0);
    public Rect rectArmRight = new Rect(0, 0, 0, 0);
    public Rect rectFootLeft = new Rect(0, 0, 0, 0);
    public Rect rectFootRight = new Rect(0, 0, 0, 0);
    public Rect rectCollisionAura = new Rect(0, 0, 0, 0);
    private boolean jumped = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean ducked = false;
    private boolean readyToFire = true;
    private int speedX = 0;
    private int speedY = 0;
    private Background bg1 = GameScreen.getBg1();
    private Background bg2 = GameScreen.getBg2();
    private ArrayList<Projectile> projectiles = new ArrayList<>();


    // Prevents going beyond X coordinate of 0
    public void update() {
        // Moves Character or Scrolls Background accordingly.

        bg1.setSpeedX(BACKGROUND_REST_SPEED);
        bg2.setSpeedX(BACKGROUND_REST_SPEED);
        int lnMax = GameScreen.tileArray.size() - 1;

        Tile tileLeftMapEdge = GameScreen.tileArray.get(0);
        Tile tileRightMapEdge = GameScreen.tileArray.get(lnMax);

        boolean lMapEdgeRight = (tileRightMapEdge.getTileX() <= AndroidGame.GAME_DIMENSION_X - 1);
        boolean lMapEdgeLeft = (tileLeftMapEdge.getTileX() >= -1);

        if (speedX > 0) {
            //Moving Right
            if (centerX <= MOVE_BORDER_RIGHT) {
                centerX += speedX;
            } else {
                //Are we Showing the edge? if not move the clouds, not us
                if (!lMapEdgeRight) {
                    bg1.setSpeedX(BACKGROUND_SCROLL_RIGHT);
                    bg2.setSpeedX(BACKGROUND_SCROLL_RIGHT);
                } else {
                    centerX += speedX;
                }
            }
        } else if (speedX < 0) {
            //Moving Left
            if (centerX >= MOVE_BORDER_LEFT) {
                centerX += speedX;
            } else {
                //Are we Showing the edge? if not move the clouds, not us
                if (!lMapEdgeLeft) {
                    bg1.setSpeedX(BACKGROUND_SCROLL_LEFT);
                    bg2.setSpeedX(BACKGROUND_SCROLL_LEFT);
                } else {
                    centerX += speedX;
                }
            }
        }
        //Are we at the full right side? dont go off screen
        if (centerX + speedX >= AndroidGame.GAME_DIMENSION_X - (IMAGE_OFFSET_FOOT_X + IMAGE_OFFSET_ARM_X)) {
            centerX = AndroidGame.GAME_DIMENSION_X - (IMAGE_OFFSET_FOOT_X + IMAGE_OFFSET_ARM_X) - 1;
        }
        //Are we at the full left side? dont go off screen
        if (centerX + speedX <= IMAGE_OFFSET_FOOT_X + IMAGE_OFFSET_ARM_X) {
            centerX = IMAGE_OFFSET_FOOT_X + IMAGE_OFFSET_ARM_X + 1;
        }
        // Updates Y Position
        centerY += speedY;

        // Handles Jumping

        speedY += 1;

        if (speedY > 3) {
            jumped = true;
        }

        rectBodyTop.set(centerX - IMAGE_OFFSET_BODY_X, centerY - IMAGE_OFFSET_BODY_Y, centerX + IMAGE_OFFSET_BODY_X, centerY);
        rectBodyBottom.set(rectBodyTop.left, centerY, rectBodyTop.right, rectBodyTop.bottom + IMAGE_OFFSET_BODY_Y);
        rectArmLeft.set(rectBodyTop.left - IMAGE_OFFSET_ARM_X, rectBodyTop.top + IMAGE_OFFSET_ARM_Y, rectBodyTop.left, rectBodyTop.top + IMAGE_OFFSET_ARM_Y + IMAGE_OFFSET_ARM_SIZE);
        rectArmRight.set(rectBodyTop.right, rectBodyTop.top + IMAGE_OFFSET_ARM_Y, rectBodyTop.right + IMAGE_OFFSET_ARM_X, rectBodyTop.top + IMAGE_OFFSET_ARM_Y + IMAGE_OFFSET_ARM_SIZE);

        rectFootLeft.set(centerX - IMAGE_OFFSET_FOOT_X, centerY + IMAGE_OFFSET_FOOT_Y, centerX, centerY + IMAGE_OFFSET_FOOT_Y + IMAGE_OFFSET_FOOT_SIZE);
        rectFootRight.set(centerX, centerY + IMAGE_OFFSET_FOOT_Y, centerX + IMAGE_OFFSET_FOOT_X, centerY + IMAGE_OFFSET_FOOT_Y + IMAGE_OFFSET_FOOT_SIZE);

        rectCollisionAura.set(centerX + COLLISION_AURA_X_LEFT, centerY + COLLISION_AURA_Y_UP, centerX + COLLISION_AURA_X_RIGHT, centerY + COLLISION_AURA_Y_DOWN);


    }

    public void moveRight() {
        if (!ducked) {
            speedX = MOVE_SPEED;
        }
    }

    public void moveLeft() {
        if (!ducked) {
            speedX = -MOVE_SPEED;
        }
    }

    public void stopRight() {
        setMovingRight(false);
        stop();
    }

    public void stopLeft() {
        setMovingLeft(false);
        stop();
    }

    private void stop() {
        if (!isMovingRight() && !isMovingLeft()) {
            speedX = 0;
        }

        if (!isMovingRight() && isMovingLeft()) {
            moveLeft();
        }

        if (isMovingRight() && !isMovingLeft()) {
            moveRight();
        }

    }

    public void jump() {
        if (!jumped) {
            speedY = JUMP_SPEED;
            jumped = true;
        }

    }

    public void shoot() {
        if (readyToFire) {
            Projectile p = new Projectile(centerX + PROJECTILE_START_X, centerY + PROJECTILE_START_Y);
            projectiles.add(p);
        }
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public boolean isJumped() {
        return jumped;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public boolean isDucked() {
        return ducked;
    }

    public void setDucked(boolean ducked) {
        this.ducked = ducked;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public ArrayList getProjectiles() {
        return projectiles;
    }

    public boolean isReadyToFire() {
        return readyToFire;
    }

    public void setReadyToFire(boolean readyToFire) {
        this.readyToFire = readyToFire;
    }

}