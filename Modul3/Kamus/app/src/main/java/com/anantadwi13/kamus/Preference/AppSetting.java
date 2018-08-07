package com.anantadwi13.kamus.Preference;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSetting {
    private static String PREFS_NAME = "appsetting";
    private SharedPreferences sharedPreferences;
    private static String KEY_FIRST_LAUNCH = "firstlaunch";

    public AppSetting(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    }

    public void setFirstLaunch(boolean x){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_FIRST_LAUNCH,x);
        editor.apply();
    }
    public boolean getFirstLaunch(){
        return sharedPreferences.getBoolean(KEY_FIRST_LAUNCH,true);
    }
}
