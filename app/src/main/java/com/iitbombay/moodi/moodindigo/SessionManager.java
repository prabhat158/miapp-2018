package com.iitbombay.moodi.moodindigo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mrunz on 12/12/17.
 */

public class SessionManager {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String name = "user details";
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void enterString(String key, String string) {
        editor.putString(key, string);
        editor.apply();
        editor.commit();
    }

    public void enterInt(String key, int integer) {
        editor.putInt(key, integer);
        editor.apply();
        editor.commit();
    }

    public void enterBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
        editor.commit();
    }

    public String getString(String key) {
        return prefs.getString(key, "empty");
    }

    public int getInt(String key) {
        return prefs.getInt(key, -1);
    }

    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    public void clear() {
        editor.clear().apply();
        editor.commit();
    }

    public void remove(String string) {
        editor.remove(string);
        editor.apply();
        editor.commit();
    }
}
