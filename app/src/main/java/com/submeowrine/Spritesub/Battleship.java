package com.submeowrine.Spritesub;

import android.graphics.PointF;

public class Battleship extends Sprite {

    /**
     * default cosntructor
     */
    public Battleship() {
        super();
        image = ImageCache.getBattleshipImage();
        bounds.set(0,0,image.getWidth(), image.getHeight());
    }

//    @Override
//    protected float relativeWidth() {
//        return 0.4f;
//    }

    public PointF getRightCannonPosition() {
        return new PointF(bounds.left + bounds.width()*(167f/183f), bounds.top);
    }

    public PointF getLeftCannonPosition() {
        return new PointF(bounds.left + bounds.width()*(22f/183f), bounds.top);
    }

}