package com.metclue.app.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AppManager extends Application {

    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;
    public static final String SHARED = "Preferences";

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPref = getSharedPreferences(AppManager.SHARED, Context.MODE_PRIVATE);
    }


    public static void putPrefeb(String key, String val) {
        if (editor == null) {
            editor = sharedPref.edit();
        }
        editor.putString("" + key, "" + val);
        editor.commit();
    }

    public static String getPrefeb(String key) {
        return sharedPref.getString("" + key, "null");
    }

    public static void clearPreferences(){
        if (editor == null) {
            editor = sharedPref.edit();
        }
        editor.clear();
        editor.commit();
    }



}
