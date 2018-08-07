package com.anantadwi13.cataloguemovie.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppReminderPref {
    private static String PREF_NAME = "notifReminder";
    private SharedPreferences sharedPref;
    private static String DAILY_REMINDER = "dailyReminder",
            RELEASE_REMINDER = "releaseReminder";

    public AppReminderPref(Context context) {
        sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }

    public void setDailyReminderOn(boolean flag){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(DAILY_REMINDER,flag);
        editor.apply();
    }

    public void setReleaseReminderOn(boolean flag){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(RELEASE_REMINDER,flag);
        editor.apply();
    }

    public boolean getDailyReminderOn(){
        return sharedPref.getBoolean(DAILY_REMINDER,true);
    }

    public boolean getReleaseReminderOn(){
        return sharedPref.getBoolean(RELEASE_REMINDER,true);
    }
}
