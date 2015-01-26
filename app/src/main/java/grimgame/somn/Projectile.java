package grimgame.somn;

/**
 * Created by grimnard on 1/19/2015.
 */

import android.graphics.Rect;

import grimgame.framework.implementation.AndroidGame;


public class Projectile {

    final int BULLET_SPEED = 7;
    final int BULLET_SIZE_X = 10;
    final int BULLET_SIZE_Y = 5;
    public int damage = 1;
    private int x, y, speedX;
    private boolean visible;
    private Rect rectProjectile;

    public Projectile(int startX, int startY, int startSpeedX) {
        this(startX, startY);
        speedX = startSpeedX;
    }

    public Projectile(int startX, int startY) {
        x = startX;
        y = startY;
        speedX = BULLET_SPEED;
        visible = true;

        rectProjectile = new Rect(0, 0, 0, 0);
    }

    public void update() {
        x += speedX;
        rectProjectile.set(x, y, x + BULLET_SIZE_X, y + BULLET_SIZE_Y);
        if (x > AndroidGame.GAME_DIMENSION_X+20) {
            visible = false;
            rectProjectile = null;
        } else {
            checkCollision();
        }
    }

    private void checkCollision() {
        for (int i = 0; i < GameScreen.enemyArrayList.size(); i++) {
            Enemy enemy = GameScreen.enemyArrayList.get(i);
            if (!enemy.dead) {
                if (Rect.intersects(rectProjectile, enemy.rect)) {
                    visible = false;
                    enemy.takeDamage(damage);
                }
            }
        }


    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


}
