package com.catswarzone.Spritesub;

import android.os.Message;

import java.util.ArrayList;

public class Timer extends android.os.Handler {

    /**
     * To instanciate all the objects
     */
        public ArrayList<TickListener> tl;
        private boolean pause;

        public Timer() {
            tl = new ArrayList<TickListener>();
            pause = false;
            sendMessageDelayed(obtainMessage(0), 0);
        }

    /**
     * add objects to the lists
     * @param m
     */
    @Override
        public void handleMessage(Message m) {
            for (TickListener t: tl) {
                t.tick();
            }
            if (pause == false) {
                sendMessageDelayed(obtainMessage(0), 10);
            }
        }



    /**
     * add obejects to ticklister arraylist
     * @param t
     */
    public void register(TickListener t){
            tl.add(t);
        }

        public void unregister(TickListener t){
            tl.remove(t);
    }

}
