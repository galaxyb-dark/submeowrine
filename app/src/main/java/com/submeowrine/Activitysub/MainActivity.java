package com.submeowrine.Activitysub;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;


public class MainActivity extends Activity {

    private GameView rv;

    /**
     * instanciate View class here
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent test = getIntent();
        //String sc = test.getStringExtra("test");
        rv = new GameView(this);
        setContentView(rv);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rv.stopSong();
        rv.saveHighScore();
    }

    @Override
    public void onPause() {
        super.onPause();
        rv.pauseSong();
    }

    @Override
    public void onResume() {
        super.onResume();
        rv.resumeSong();
    }


}
