package grimgame.somn;

import grimgame.framework.Image;

/**
 * Created by grimnard on 1/19/2015.
 */
public class HeliBoy extends Enemy {
    Image heliboy, heliboy2, heliboy3, heliboy4, heliboy5;
    private Animation animHeliBoy;

    public HeliBoy(int centerX, int centerY) {
        setHealth(5);
        setCenterX(centerX);
        setCenterY(centerY);
        setImage();
        setImageOffsetX(48);
        setImageOffsetY(48);

    }
    @Override
    public void nullify(){
        super.nullify();
        heliboy=null;
        heliboy2 =null;
        heliboy3 = null;
        heliboy4 = null;
        heliboy5 = null;
        animHeliBoy = null;
    }

    @Override
    public void setImage() {
        heliboy = Assets.heliboy;
        heliboy2 = Assets.heliboy2;
        heliboy3 = Assets.heliboy3;
        heliboy4 = Assets.heliboy4;
        heliboy5 = Assets.heliboy5;

        animHeliBoy = new Animation();
        animHeliBoy.addFrame(heliboy, 100);
        animHeliBoy.addFrame(heliboy2, 100);
        animHeliBoy.addFrame(heliboy3, 100);
        animHeliBoy.addFrame(heliboy4, 100);
        animHeliBoy.addFrame(heliboy5, 100);
        animHeliBoy.addFrame(heliboy4, 100);
        animHeliBoy.addFrame(heliboy3, 100);
        animHeliBoy.addFrame(heliboy2, 100);
        setAnimation(animHeliBoy);
        setAnimationSpeed((50));
    }

}