package grimgame.somn;

/**
 * Created by grimnard on 1/19/2015.
 */

import android.graphics.Rect;

import grimgame.framework.Image;
import grimgame.framework.implementation.AndroidGame;


public class Enemy {
    public int imageOffsetX,imageOffsetY;
    public boolean dead;
    public Rect rect = new Rect(0, 0, 0, 0);
    public boolean doesFollow = true;
    private Animation animation;
    private int power, centerX, speedX, centerY;
    private Background bg = GameScreen.getBg1();
    private Robot robot;
    private int health;
    private int movementSpeed,animationSpeed;
    public void setAnimationSpeed(int animationSpeed){
        this.animationSpeed = animationSpeed;
    }
    public int getAnimationSpeed(){return animationSpeed;}

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;
    }
    public void nullify(){
        animation = null;
        robot = null;
        bg = null;
        rect = null;

    }
    // Behavioral Methods
    public void update() {

        if (!this.dead) {
            robot = GameScreen.getRobot();
            if (doesFollow) follow();
            centerX += speedX;
            speedX = bg.getSpeedX() * 5 + movementSpeed;
            rect.set(centerX - 25, centerY - 25, centerX + 25, centerY + 35);

            if (Rect.intersects(rect, robot.rectCollisionAura)) {
                checkCollision();
            }

        }
    }

    private void checkCollision() {
        robot = GameScreen.getRobot();
        if (Rect.intersects(rect, robot.rectBodyTop) || Rect.intersects(rect, robot.rectBodyBottom)
                || Rect.intersects(rect, robot.rectArmLeft) || Rect.intersects(rect, robot.rectArmRight)) {

        }
    }

    public void follow() {

        if (centerX < -95 || centerX > 810) {
            movementSpeed = 0;
        } else if (Math.abs(robot.getCenterX() - centerX) < 5) {
            movementSpeed = 0;
        } else {

            if (robot.getCenterX() >= centerX) {
                movementSpeed = 1;
            } else {
                movementSpeed = -1;
            }
        }

    }

    public void die() {
        this.setCenterX(-AndroidGame.GAME_DIMENSION_X);
        this.health = 0;
        this.speedX = 0;
        this.dead = true;
        rect = null;

    }

    public void attack() {

    }

    public void takeDamage(int damage) {

        this.health -= damage;
        if (this.health <= 0) {
            this.die();
        }
    }

    public void setImage() {
    }

    public Image getImage() {
        return this.animation.getImage();
    }
    public void updateAnimation(){
        this.animation.update(this.animationSpeed);
    }
    public void setImageOffsetX(int imageOffsetX){
        this.imageOffsetX=imageOffsetX;
    }
    public void setImageOffsetY(int imageOffsetY){
        this.imageOffsetY=imageOffsetY;
    }
    public int getImageOffsetX(){return imageOffsetX;}
    public int getImageOffsetY(){return imageOffsetY;}
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
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

    public Background getBg() {
        return bg;
    }

    public void setBg(Background bg) {
        this.bg = bg;
    }

}
