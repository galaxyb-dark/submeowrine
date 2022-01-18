package com.submeowrine.Spritesub;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import static com.submeowrine.Spritesub.Direction.LEFT_TO_RIGHT;
import static com.submeowrine.Spritesub.Direction.RIGHT_TO_LEFT;

public abstract class Enemy extends Sprite {
    protected Size size;
    public Direction direction;
    public boolean exploding;
    public double r;


    /**
     * default constructor. Randomly selects a size (small, medium, large) for the sprite
     */
    public Enemy() {
        super();
        r = Math.random();
        if (r < 0.333) {
            size = Size.SMALL;
        } else if (r < 0.6667) {
            size = Size.MEDIUM;
        } else {
            size = Size.BIG;
        }

        if (r < 0.5) {
            direction = RIGHT_TO_LEFT;
        } else {
            direction = LEFT_TO_RIGHT;
        }

        reset();
    }

    /**
     * repeat and reset after the explosion
     */
    public void reset() {
        if (r < 0.333) {
            size = Size.SMALL;
        } else if (r < 0.6667) {
            size = Size.MEDIUM;
        } else {
            size = Size.BIG;
        }

        if (r < 0.5) {
            direction = RIGHT_TO_LEFT;
        } else {
            direction = LEFT_TO_RIGHT;
        }

    }

    /**
     * randomize velocity
     */
    @Override
    public void move() {
        super.move();

        //change the velocity 10% of the time
        if (Math.random() < 0.1) {
            velocity.x = getRandomVelocity();
        }
    }

//    @Override
//    public void scale(float w, float h) {
//        super.scale(w, h);
//        velocity.x = getRandomVelocity();
//    }

    /**
     * sets a new X velocity for the sprite.
     * I multiply it by signum to ensure that we preserve the direction
     * (left or right) of the velocity. Positive stays positive, negative
     * stays negative
     * @return a new random X velocity
     */
    protected float getRandomVelocity() {
        return (float)(Math.random() * 0.00625 * ImageCache.screenWidth() * Math.signum(velocity.x));
    }

    /**
     * original explode method
     */
    public void explode() {
        this.image = getExplodingImage();
        velocity.set(0, 0);
        exploding = true;
    }

    /**
     * to implement getExplodingImage method
     * @return
     */
    protected abstract Bitmap getExplodingImage();


    /**
     * to draw explosion
     * @param c the Android Canvas object
     */
    @Override
    public void draw(Canvas c) {
        super.draw(c);
        if (exploding == true) {
            exploding = false;
            reset();
        }
    }

    /**
     * to get a point value
     * @return
     */
    protected abstract int getPointValue();

}