package com.example.mymagicapp;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

public class Session {
    private static SharedPreferences Preferences(Context context) {
        return context.getSharedPreferences("preferences-key-name", MODE_PRIVATE);
    }

    public static void addKey(Context context, String key, String value){
        SharedPreferences.Editor editor = Preferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getKey(Context context, String key){
        return Preferences(context).getString(key, "default-value");
    }

    public static void removeAllKey(Context context){
        SharedPreferences.Editor editor = Preferences(context).edit();

        editor.clear();
        editor.apply();
    }
}
