package com.muxistudio.chaser.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.muxistudio.chaser.bean.WordDetail;
import com.muxistudio.chaser.db.Word;
import com.muxistudio.chaser.widget.FloatWordView;
import com.muxistudio.chaser.utils.DimenUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by ybao on 16/11/16.
 */
public class FloatWindowManager {

  private static FloatWordView sFloatWordView;

  private static LayoutParams sFloatParams;

  private static WindowManager mWindowManager;

  private static ActivityManager mActivityManager;



  private static List<WordDetail> sWordDetails;

  public static void createFloatView(Context context) {
    WindowManager windowManager = getWindowManager(context);
    if (sFloatWordView == null) {
      sFloatWordView = new FloatWordView(context);
      if (sFloatParams == null) {
        sFloatParams = new LayoutParams();
        sFloatParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
        //sFloatParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
        sFloatParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE
            | LayoutParams.FLAG_NOT_TOUCH_MODAL
            | LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        sFloatParams.format = PixelFormat.TRANSLUCENT ;
        sFloatParams.gravity = Gravity.LEFT | Gravity.TOP;
        sFloatParams.width = FloatWordView.width;
        sFloatParams.height = FloatWordView.height;
        sFloatParams.x = FloatWordView.x;
        sFloatParams.y = FloatWordView.y;
      }
      Log.d("tag", sFloatParams.type + "");
      sFloatWordView.setLayoutParams(sFloatParams);
      sFloatWordView.setParams(sFloatParams);
      windowManager.addView(sFloatWordView, sFloatParams);
    }
  }

  public static void removeFloatView(Context context) {
    if (sFloatWordView != null) {
      WindowManager windowManager = getWindowManager(context);
      windowManager.removeView(sFloatWordView);
      sFloatWordView = null;
    }
  }

  public static void updateFloatView(Word word) {
    if (sFloatWordView != null) {
      sFloatWordView.setWord(word);
    }else {
      sFloatWordView.showEmpty();
    }
  }

  public static boolean isWindowShowing() {
    return sFloatWordView != null;
  }

  private static WindowManager getWindowManager(Context context) {
    if (mWindowManager == null) {
      mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }
    return mWindowManager;
  }

  private static ActivityManager getActivityManager(Context context) {
    if (mActivityManager == null) {
      mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }
    return mActivityManager;
  }

}