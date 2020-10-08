package com.catswarzone.Activitysub;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class Prefs extends PreferenceActivity {

    public static final String MUSIC_PREF = "MUSIC_PREF";
    public static final String SUB_SPEED_PREF = "SUB_SPEED_PREF";
    public static final String SOUND_PREF = "SOUND_PREF";
    public static final String MISSILE_PREF = "MISSILE_PREF";
    public static final String DC_PREF = "DC_PREF";

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(this);

        CheckBoxPreference music = new CheckBoxPreference(this);
        music.setTitle("Background Music");
        music.setSummaryOn("Music will play");
        music.setSummaryOff("Music will not play");
        music.setChecked(true);
        music.setKey(MUSIC_PREF);
        screen.addPreference(music);

        CheckBoxPreference sound = new CheckBoxPreference(this);
        sound.setTitle("Sound Effects");
        sound.setSummaryOn("Sounds will play");
        sound.setSummaryOff("Sounds will not play");
        sound.setChecked(true);
        sound.setKey(SOUND_PREF);
        screen.addPreference(sound);

        CheckBoxPreference missile = new CheckBoxPreference(this);
        missile.setTitle("Missile");
        missile.setSummaryOn("The player can shoot another missile while there's already one missile on-screen");
        missile.setSummaryOff("The player cannot shoot another missile while there's already one missile on-screen");
        missile.setChecked(true);
        missile.setKey(MISSILE_PREF);
        screen.addPreference(missile);

        CheckBoxPreference dc = new CheckBoxPreference(this);
        dc.setTitle("Depth Charge");
        dc.setSummaryOn("The player can launch another depth charge while there's already one depth charge on-screen");
        dc.setSummaryOff("The player cannot launch another depth charge while there's already one depth charge on-screen");
        dc.setChecked(true);
        dc.setKey(DC_PREF);
        screen.addPreference(dc);

        setPreferenceScreen(screen);

        ListPreference speed = new ListPreference(this);
        speed.setTitle("Submarine Speed");
        speed.setSummary("How fast should the submarines move?");
        speed.setKey(SUB_SPEED_PREF);
        String[] labels = {"Fast", "Medium", "Slow"};
        String[] values = {"50", "10", "5"};
        speed.setEntries(labels);
        speed.setEntryValues(values);
        screen.addPreference(speed);

    }

    //this is a "facade" method.
    public static boolean getMusicPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(MUSIC_PREF, true);
    }

    public static boolean getSoundPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(SOUND_PREF, true);
    }

    public static boolean getMissilePref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(MISSILE_PREF, true);
    }

    public static boolean getDCPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(DC_PREF, true);
    }

    public static int getSpeedPref(Context c) {
        String seth = PreferenceManager.
                getDefaultSharedPreferences(c).getString(SUB_SPEED_PREF, "10");
        return Integer.parseInt(seth);
    }
}
