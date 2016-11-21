package com.muxistudio.chaser.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.muxistudio.chaser.App;

/**
 * Created by ybao on 16/11/21.
 */

public class PreferenceUtil {

  public static final String KEY_CUR_REMBER_BANK = "key_curbank";

  public static void putInt(String key, int value) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.sContext);
    sp.edit().putInt(key, value).apply();
  }

  public static int getInt(String key) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.sContext);
    return sp.getInt(key, 0);
  }

  public static void putString(String key, String value) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.sContext);
    sp.edit().putString(key, value).apply();
  }

  public static String getString(String key) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.sContext);
    return sp.getString(key, "");
  }

  public static void putBoolean(String key, boolean b) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.sContext);
    sp.edit().putBoolean(key, b).apply();
  }

  public static boolean getBoolean(String key) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.sContext);
    return sp.getBoolean(key, false);
  }
}
