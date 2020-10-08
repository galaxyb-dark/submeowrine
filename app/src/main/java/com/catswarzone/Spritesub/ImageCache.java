package com.catswarzone.Spritesub;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.catswarzone.R;

public class ImageCache {

    /**
     * field
     */
    private static float screenWidth, screenHeight;
    private static Bitmap battleship;
    private static Bitmap water;
    private static Bitmap airplane_small_left2right;
    private static Bitmap airplane_med_left2right;
    private static Bitmap airplane_big_left2right;
    private static Bitmap airplane_small_right2left;
    private static Bitmap airplane_med_right2left;
    private static Bitmap airplane_big_right2left;
    private static Bitmap submarine_small_left2right;
    private static Bitmap submarine_med_left2right;
    private static Bitmap submarine_big_left2right;
    private static Bitmap submarine_small_right2left;
    private static Bitmap submarine_med_right2left;
    private static Bitmap submarine_big_right2left;
    private static Bitmap depth_charge;
    private static Bitmap explosion_submarine;
    private static Bitmap explosion_airplane;
    private static Bitmap pop;

    /**
     * scaring the objects
     * @param res
     * @param w
     * @param h
     */
    public static void init(Resources res, float w, float h) {
        screenWidth = w;
        screenHeight = h;
        battleship = loadAndScale(res, R.drawable.battleship, w*0.2f);
        water = loadAndScale(res, R.drawable.water, w/45);
        airplane_small_left2right = loadAndScale(res, R.drawable.little_airplane_flip, w*0.08f);
        airplane_med_left2right = loadAndScale(res, R.drawable.medium_airplane_flip, w*0.083f);
        airplane_big_left2right = loadAndScale(res, R.drawable.big_airplane_flip, w*0.15f);
        airplane_small_right2left = loadAndScale(res, R.drawable.little_airplane, w*0.05f);
        airplane_med_right2left = loadAndScale(res, R.drawable.medium_airplane, w*0.083f);
        airplane_big_right2left = loadAndScale(res, R.drawable.big_airplane, w*0.12f);
        //submarine_small_left2right = loadAndScale(res, R.drawable.little_submarine, w*0.05f);
        submarine_small_left2right = loadAndScale(res, R.drawable.little_submarine, w*0.25f);
        submarine_med_left2right = loadAndScale(res, R.drawable.medium_submarine, w*0.083f);
        submarine_big_left2right = loadAndScale(res, R.drawable.big_submarine, w*0.15f);
        //submarine_small_right2left = loadAndScale(res, R.drawable.little_submarine_flip, w*0.05f);
        submarine_small_right2left = loadAndScale(res, R.drawable.little_submarine_flip, w*0.25f);
        submarine_med_right2left = loadAndScale(res, R.drawable.medium_submarine_flip, w*0.083f);
        submarine_big_right2left = loadAndScale(res, R.drawable.big_submarine_flip, w*0.15f);
        depth_charge = loadAndScale(res, R.drawable.depth_charge, w*0.025f);
        pop = loadAndScale(res, R.drawable.star, w*0.05f);
        explosion_airplane = loadAndScale(res, R.drawable.airplane_explosion, w*0.12f);
        explosion_submarine = loadAndScale(res, R.drawable.submarine_explosion, w*0.12f);
    }

    /**
     * load and scale the objects
     * @param res
     * @param id
     * @param newWidth
     * @return
     */
    private static Bitmap loadAndScale(Resources res, int id, float newWidth) {
        Bitmap original = BitmapFactory.decodeResource(res, id);
        float aspectRatio = (float)original.getHeight()/(float)original.getWidth();
        float newHeight = newWidth * aspectRatio;
        return Bitmap.createScaledBitmap(original, (int)newWidth, (int)newHeight, true);
    }

    /**
     * separate each objects as their size follow (airplane)
     * @param s
     * @param d
     * @return
     */
    public static Bitmap getAirplaneImage(Size s, Direction d) {
        if (d==Direction.LEFT_TO_RIGHT) {
            if (s==Size.BIG) {
                return airplane_big_left2right;
            } else if (s==Size.MEDIUM) {
                return airplane_med_left2right;
            } else {
                return airplane_small_left2right;
            }
        } else {
            if (s==Size.BIG) {
                return airplane_big_right2left;
            } else if (s==Size.MEDIUM) {
                return airplane_med_right2left;
            } else {
                return airplane_small_right2left;
            }
        }
    }

    /**
     * separate each objects as their size follow (submarine)
     * @param s
     * @param d
     * @return
     */
    public static Bitmap getSubmarineImage(Size s, Direction d) {
        if (d==Direction.LEFT_TO_RIGHT) {
            if (s==Size.BIG) {
                return submarine_big_left2right;
            } else if (s==Size.MEDIUM) {
                return submarine_med_left2right;
            } else {
                return submarine_small_left2right;
            }
        } else {
            if (s==Size.BIG) {
                return submarine_big_right2left;
            } else if (s==Size.MEDIUM) {
                return submarine_med_right2left;
            } else {
                return submarine_small_right2left;
            }
        }
    }

    /**
     * get each of the image objects
     * @return
     */
    public static Bitmap getWaterImage() {
        return water;
    }

    public static Bitmap getBattleshipImage() {
        return battleship;
    }

    public static Bitmap getDepthChargeImage() {
        return depth_charge;
    }

    public static Bitmap getSubmarineExplosion() {
        return explosion_submarine;
    }

    public static Bitmap getAirplaneExposion() {
        return explosion_airplane;
    }

    public static Bitmap getCannonFire() {
        return pop;
    }

    /**
     * get width and height of the screen
     * @return
     */

    public static float screenWidth() {
        return screenWidth;
    }

    public static float screenHeight() {
        return screenHeight;
    }

}
