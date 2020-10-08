package com.catswarzone.Activitysub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.catswarzone.R;
import com.catswarzone.Spritesub.Airplane;
import com.catswarzone.Spritesub.Battleship;
import com.catswarzone.Spritesub.DepthCharge;
import com.catswarzone.Spritesub.Direction;
import com.catswarzone.Spritesub.ImageCache;
import com.catswarzone.Spritesub.Missile;
import com.catswarzone.Spritesub.Timer;
import com.catswarzone.Spritesub.Sprite;
import com.catswarzone.Spritesub.Submarine;
import com.catswarzone.Spritesub.TickListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameView extends View implements TickListener {

    /**
     * fields
     */
    private Bitmap water;
    private Battleship battleship;
    private Airplane plane1, plane2, plane3;
    private Submarine sub1, sub2, sub3;
    private boolean init;
    private ArrayList<DepthCharge> bombs;
    private ArrayList<Missile> missiles;
    private Timer timer;
    public int score;
    public Paint paint;
    int countDown;
    long timeNow, timeBefore;
    int highScore;
    int howManyPoints;
    private MediaPlayer song, rightgun, leftgun, dcsound, kitten_mew, kitten_mew_two;

    public GameView(Context context) {
        super(context);
        loadHighScore();
        bombs = new ArrayList<>();
        missiles = new ArrayList<>();
        init = false;
        //score = 0;

        //At start up
        //countDown = 180;
        countDown = 60;
        timeBefore = System.currentTimeMillis();

        //highScore = 0;
        howManyPoints = 0;

        song = MediaPlayer.create(getContext(), R.raw.ocean_waves);
        song.setLooping(true);

        dcsound = MediaPlayer.create(getContext(), R.raw.depth_charge);
        rightgun = MediaPlayer.create(getContext(), R.raw.right_gun);
        leftgun = MediaPlayer.create(getContext(), R.raw.left_gun);
        kitten_mew = MediaPlayer.create(getContext(), R.raw.kitten_mew);
        kitten_mew_two = MediaPlayer.create(getContext(), R.raw.kitten_mew);
    }

    private void cleanUp() {
        //clean up depth charges that go off-screen
        List<Sprite> doomed = new ArrayList<>();
        for (DepthCharge dc : bombs) {
            if (dc.getTop() > getHeight()) {
                doomed.add(dc);
            }
        }
        for (Sprite d : doomed) {
            bombs.remove(d);
            timer.unregister(d);
        }
        doomed.clear();
        //clean up missiles that go off-screen
        for (Missile dc : missiles) {
            if (dc.getBottom() < 0) {
                doomed.add(dc);
            }
        }
        for (Sprite d : doomed) {
            bombs.remove(d);
            timer.unregister(d);
        }

    }

    public void stopSong() {
        song.release();
    }

    public void pauseSong() {
        if (Prefs.getMusicPref(getContext())) {
            song.pause();
        }
    }

    public void resumeSong() {
        if (Prefs.getMusicPref(getContext())) {
            song.start();
        }
    }

    private boolean dcVisible() {
        if (bombs.size() > 0) {
                return true;
            } return false;
        }

    /**
     * Scales, positions, and renders the scene
     * @param c the Canvas object, provided by system
     */
    @Override
    public void onDraw(Canvas c) {
        int w = getWidth();
        int h = getHeight();
        //countDown = 10;
        //timeBefore = System.currentTimeMillis();
        c.drawColor(Color.parseColor("#96B3E0"));
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setStyle(Paint.Style.FILL);
        String numberWithZero = String.format("%02d",countDown % 60);
        c.drawText("SCORE = " + score, 30, h/2 + 150, paint);
        c.drawText("TIME: " + countDown / 60 + ":" + numberWithZero, w - 250, h/2 + 150, paint);

        if (countDown <= 0) {
            timer.removeCallbacksAndMessages(null);
            loadHighScore();
            AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
            if (score >=  highScore) {
                highScore = score;
                saveHighScore();
                ad.setMessage("Nice, you've got the highest score. The score was " + highScore + ". Play again?");
            }
            else if (score <= highScore){

                ad.setMessage("You did okay. The current high score is " + highScore + ". Play again?");
            }

            ad.setTitle("Meaw...")
                    .setCancelable(false)
                    .setPositiveButton("yup", (d, i) -> {
                            Message mes = new Message();
                            timer.sendMessage(mes);
                            countDown = 60;
                            score = 0;
                    })
                    .setNegativeButton("nope", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int i) {
                            Activity parent = (Activity)getContext();
                            parent.finish();
                        }
                    })

                    .show();
        }

        if (init == false) {
            init = true;
            ImageCache.init(getResources(), w, h);

            water = ImageCache.getWaterImage();
            battleship = new Battleship();
            plane1 = new Airplane();
            plane2 = new Airplane();
            plane3 = new Airplane();
            sub1 = new Submarine();
            sub2 = new Submarine();
            sub3 = new Submarine();

            /**
             * instanciate all the objects from the arraylist
             */

            timer = new Timer();
            timer.register(plane1);
            timer.register(plane2);
            timer.register(plane3);
            timer.register(sub1);
            timer.register(sub2);
            timer.register(sub3);
            timer.register(this);


//
//            //scale the water
//            float aspectRatio = (float)water.getHeight()/(float)water.getWidth();
//            float newWidth = w/45;
//            float waterHeight = newWidth*aspectRatio;
//            water = Bitmap.createScaledBitmap(water, (int)newWidth, (int)waterHeight, true);
//
//            //scale the battleship
//            battleship.scale(w,h);
//
//            //scale enemies
//            plane1.scale(w,h);
//            plane2.scale(w,h);
//            plane3.scale(w,h);
//            sub1.scale(w,h);
//            sub2.scale(w,h);
//            sub3.scale(w,h);

            //position enemies
            float battleshipX = w/2-battleship.getWidth()/2; //center the ship
            float battleshipY = h/2-battleship.getHeight()+water.getHeight(); //put the ship above the water line
            battleship.setLocation(battleshipX, battleshipY);

            //start them all off-screen
            sub1.setLocation(2*w, 0);
            sub2.setLocation(2*w, 0);
            sub3.setLocation(2*w, 0);
            plane1.setLocation(-w, 0);
            plane2.setLocation(-w, 0);
            plane3.setLocation(-w, 0);

            //inform Airplane class of acceptable upper/lower limits of flight
            Airplane.setSkyLimits(0, battleship.getTop()-plane1.getHeight()*2);

            //inform Submarine class of acceptable upper/lower limits of depth
            Submarine.setWaterDepth(h/2 + water.getHeight()*2, h-water.getHeight()*2);

            //Once everything is in place, start the animation loop!
            new Timer();
        }

        //now draw everything
        float waterX = 0;
        while (waterX < w) {
            c.drawBitmap(water, waterX, h/2, null);
            waterX += water.getWidth();
        }
        /**
         * draw objects here
         */
        sub1.draw(c);
        sub2.draw(c);
        sub3.draw(c);
        plane1.draw(c);
        plane2.draw(c);
        plane3.draw(c);

        for (DepthCharge d : bombs) {
            d.draw(c);
        }
        for (Missile d : missiles) {
            d.draw(c);
        }
        battleship.draw(c);

    }

    /**
     * set the touch events
     * @param m
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent m) {
        if (m.getAction() == MotionEvent.ACTION_DOWN) {
            float x = m.getX();
            float y = m.getY();
            //did the user tap the bottom half of the screen? Depth Charge!
            boolean dcCheck = false;
            boolean msCheck = false;

            if (Prefs.getDCPref(getContext())) {
                if (bombs.size() > 0) {
                    dcCheck = true;
                }
            }

            if (Prefs.getMissilePref(getContext())) {
                if (missiles.size() > 0) {
                    msCheck = true;
                }
            }

            /**
             * now instanciate dc and missiles
             */
            if (y > getHeight() / 2) {
                if (dcCheck == false) {
                    launchNewDepthCharge();
                    if (Prefs.getSoundPref(getContext())) {
                        dcsound.start();
                    }
                }
            } else {
                //did the user tap the top half of the screen? missile!
                Missile miss = null;
                if (x < getWidth() / 2) {
                    if (msCheck == false) {
                        launchNewMissile(Direction.RIGHT_TO_LEFT);
                        if (Prefs.getSoundPref(getContext())) {
                            leftgun.start();
                        }
                    }
                } else {
                    if (msCheck == false) {
                        launchNewMissile(Direction.LEFT_TO_RIGHT);
                        if (Prefs.getSoundPref(getContext())) {
                            rightgun.start();
                        }
                    }
                }
                cleanUp();
            }
        }
        return true;
    }

    private void launchNewDepthCharge() {
        DepthCharge dc = new DepthCharge();
        //dc.scale(getWidth(), getHeight());
        dc.setCentroid(getWidth() / 2, getHeight() / 2);
        bombs.add(dc);
        timer.register(dc);
    }

    private void launchNewMissile(Direction d) {
        Missile miss = new Missile(d);
        if (d == Direction.RIGHT_TO_LEFT) {
            miss.setBottomRight(battleship.getLeftCannonPosition());
        } else {
            miss = new Missile(Direction.LEFT_TO_RIGHT);
            //miss.scale(getWidth(), getHeight());
            miss.setBottomLeft(battleship.getRightCannonPosition());
        }
        missiles.add(miss);
        timer.register(miss);
    }

    /**
     * tick method (on each tick, an event happens)
     */
    @Override
    public void tick(){
        detectCollisions();
        invalidate();
        timeNow = System.currentTimeMillis();
        if (timeNow - timeBefore >= 1000 && countDown > 0) {
            countDown--;
            timeBefore = timeNow;
        }
        /*
        if (countDown == 0) {
            AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                    ad.setTitle("Game Over!!")
                    .setMessage("Would you like to play again?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }

         */
    }

    /**
     * see if depthcharge and missiles overlap objects
     */
    public void detectCollisions() {
        for (DepthCharge d : bombs) {
            if (sub1.overlaps(d)) {
                sub1.explode();
                score += sub1.getPointValue();
                bombs.remove(d);
                if (Prefs.getSoundPref(getContext())) {
                    kitten_mew.start();
                }
                break;
            }
            if (sub2.overlaps(d)) {
                sub2.explode();
                score += sub2.getPointValue();
                bombs.remove(d);
                if (Prefs.getSoundPref(getContext())) {
                    kitten_mew.start();
                }
                break;
            }
            if (sub3.overlaps(d)) {
                sub3.explode();
                score += sub3.getPointValue();
                bombs.remove(d);
                if (Prefs.getSoundPref(getContext())) {
                    kitten_mew.start();
                }
                break;
            }
        }
        for (Missile m : missiles) {
            if (plane1.overlaps(m)) {
                plane1.explode();
                score += plane1.getPointValue();
                missiles.remove(m);
                if (Prefs.getSoundPref(getContext())) {
                    kitten_mew_two.start();
                }
                break;
            }
            if (plane2.overlaps(m)) {
                plane2.explode();
                score += plane2.getPointValue();
                missiles.remove(m);
                if (Prefs.getSoundPref(getContext())) {
                    kitten_mew_two.start();
                }
                break;
            }
            if (plane3.overlaps(m)) {
                plane3.explode();
                score += plane3.getPointValue();
                missiles.remove(m);
                if (Prefs.getSoundPref(getContext())) {
                    kitten_mew_two.start();
                }
                break;
            }
        }
    }

    /**
     * save the high score
     */
    public void saveHighScore() {
        try {
            FileOutputStream fo = getContext().openFileOutput("score.txt", Context.MODE_PRIVATE);
            String string = "" + score;
            fo.write(string.getBytes());
            fo.close();
        } catch(IOException e) {
            //blissfully ignore
        }
    }


    /**
     * load the high score
     */
    private void loadHighScore() {
        try {
            FileInputStream fi = getContext().openFileInput("score.txt");
            Scanner s = new Scanner(fi);
            String line = s.nextLine();
            highScore = Integer.parseInt(line);
            s.close();
        } catch (FileNotFoundException e) {
            //blissfully ignore
        }
    }
}
