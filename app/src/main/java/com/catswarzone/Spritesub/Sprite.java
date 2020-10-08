package com.catswarzone.Spritesub;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public abstract class Sprite implements TickListener{

    protected Bitmap image;
    protected RectF bounds;
    protected PointF velocity;
    //protected float screenWidth, screenHeight;

    /**
     * default constructor.
     */
    public Sprite() {
        bounds = new RectF();
        velocity = new PointF();
    }

    /**
     * set the sprite's top-left corner to (x,y)
     * @param x the new X coordinate
     * @param y the new Y coordinate
     */
    public void setLocation(float x, float y) {
        bounds.offsetTo(x, y);
    }

    public void setCentroid(float x, float y) {
        bounds.offsetTo(x-bounds.width()/2, y-bounds.height()/2);
    }

    /**
     * set the sprite's bottom-left corner to (x,y)
     * @param p the new point
     */
    public void setBottomLeft(PointF p) {
        bounds.offsetTo(p.x, p.y-bounds.height());
    }

    /**
     * set the sprite's bottom-right corner to (x,y)
     * @param p the new point
     */
    public void setBottomRight(PointF p) {
        bounds.offsetTo(p.x-bounds.width(), p.y-bounds.height());
    }

    /**
     * Render the sprite at its pre-set location
     * @param c the Android Canvas object
     */
    public void draw(Canvas c) {
        c.drawBitmap(image, bounds.left,  bounds.top, null);
    }

    public void move() {
        bounds.offset(velocity.x, velocity.y);
    }

//    /**
//     * Scales the sprite to its correct size, given the width of the screen
//     * @param w the width (in pixels) of the screen
//     */
//    public void scale(float w, float h) {
//        screenWidth = w;
//        screenHeight = h;
//        float aspectRatio = (float)image.getHeight()/(float)image.getWidth();
//        float newWidth = w * relativeWidth();
//        float newHeight = newWidth * aspectRatio;
//        image = Bitmap.createScaledBitmap(image,
//                (int)newWidth, (int)newHeight, true);
//        bounds.set(0,0,newWidth,newHeight);
//    }

//    protected abstract float relativeWidth();

    /**
     * helper method to find out how tall the sprite is
     * @return the height of the sprite, in pixels
     */
    public float getHeight() {
        return bounds.height();
    }

    /**
     * helper method to return the top Y coordinate of the sprite
     * @return the top Y coordinate of the sprite
     */
    public float getTop() {
        return bounds.top;
    }

    /**
     * helper method to return the lower Y coordinate of the sprite
     * @return the lower Y coordinate of the sprite
     */
    public float getBottom() {
        return bounds.bottom;
    }

    /**
     * helper method to find out how wide the sprite is
     * @return the width of the sprite, in pixels
     */
    public float getWidth() {
        return bounds.width();
    }

    public void tick(){
        move();
    }

    public boolean overlaps(Sprite other) {
        return RectF.intersects(this.bounds, other.bounds);
    }
}
