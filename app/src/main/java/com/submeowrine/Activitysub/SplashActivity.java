package com.submeowrine.Activitysub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.submeowrine.R;

public class SplashActivity extends Activity {

    private ImageView iv;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        iv = new ImageView(this);
        iv.setImageResource(R.drawable.splash);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(iv);
    }

    @Override
    public boolean onTouchEvent(MotionEvent m) {
        float w = iv.getWidth();
        float h = iv.getHeight();
        RectF prefsButton = new RectF(0, 0, 0.8f*w, 0.2f*h);
        RectF aboutButton = new RectF(0, 0.85f*h, 0.15f*w, h);
        RectF playButton = new RectF(300,300,w,h);
        if (m.getAction() == MotionEvent.ACTION_UP) {
            float x = m.getX();
            float y = m.getY();
            Intent marco = new Intent(this, MainActivity.class);
            //startActivity(marco);
            //finish();
            if (prefsButton.contains(x,y)) {
                Intent pref = new Intent(this, Prefs.class);
                startActivity(pref);
            }
            if (aboutButton.contains(x,y)) {
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setMessage("When you shot the items you have to say 'Meaw!'");
                ad.setTitle("General rule").show();
            }
            if (playButton.contains(x,y)) {
                marco.putExtra("test", "right");
                startActivity(marco);
            }
        }
        return true;
    }
}
