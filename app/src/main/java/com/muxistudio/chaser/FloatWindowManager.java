package com.muxistudio.chaser;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.muxistudio.chaser.ui.FloatWordView.FloatWordView;
import com.muxistudio.chaser.utils.DimenUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ybao on 16/11/16.
 */
public class FloatWindowManager {

  /**
   * 小悬浮窗View的实例
   */
  private static FloatWordView sFloatWordView;

  private static LayoutParams sFloatParams;


  /**
   * 用于控制在屏幕上添加或移除悬浮窗
   */
  private static WindowManager mWindowManager;

  /**
   * 用于获取手机可用内存
   */
  private static ActivityManager mActivityManager;

  /**
   * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
   *
   * @param context 必须为应用程序的Context.
   */
  public static void createFloatView(Context context){
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    if (sFloatWordView == null){
      sFloatWordView = new FloatWordView(context);
      if (sFloatParams == null){
        sFloatParams = new LayoutParams();
        sFloatParams.type = LayoutParams.TYPE_PHONE;
        sFloatParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
        sFloatParams.format = PixelFormat.RGBA_8888;
        sFloatParams.gravity = Gravity.LEFT | Gravity.TOP;
        sFloatParams.width = FloatWordView.width;
        sFloatParams.height = FloatWordView.height;
        sFloatParams.x = FloatWordView.x;
        sFloatParams.y = FloatWordView.y - DimenUtil.getStatusBarHeight();
      }
      Log.d("tag",sFloatParams.type + "");
      sFloatWordView.setLayoutParams(sFloatParams);
      windowManager.addView(sFloatWordView,sFloatParams);
    }
  }

  /**
   * 将小悬浮窗从屏幕上移除。
   *
   * @param context 必须为应用程序的Context.
   */
  public static void removeFloatView(Context context) {
    if (sFloatWordView != null) {
      WindowManager windowManager = getWindowManager(context);
      windowManager.removeView(sFloatWordView);
      sFloatWordView = null;
    }
  }

  /**
   * 更新小悬浮窗的TextView上的数据，显示内存使用的百分比。
   *
   * @param context 可传入应用程序上下文。
   */
  public static void updateUsedPercent(Context context) {
    if (sFloatWordView != null) {
      TextView percentView = (TextView) sFloatWordView.findViewById(R.id.tv_word);
      percentView.setText(getUsedPercentValue(context));
    }
  }

  public static void updateFloatView(Context context){
    if (sFloatWordView != null){
      TextView mTvWord = (TextView) sFloatWordView.findViewById(R.id.tv_word);
      mTvWord.setText(getUsedPercentValue(context));
    }
  }

  /**
   * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
   *
   * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
   */
  public static boolean isWindowShowing() {
    return sFloatWordView != null;
  }

  /**
   * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
   *
   * @param context 必须为应用程序的Context.
   * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
   */
  private static WindowManager getWindowManager(Context context) {
    if (mWindowManager == null) {
      mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }
    return mWindowManager;
  }

  /**
   * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
   *
   * @param context 可传入应用程序上下文。
   * @return ActivityManager的实例，用于获取手机可用内存。
   */
  private static ActivityManager getActivityManager(Context context) {
    if (mActivityManager == null) {
      mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }
    return mActivityManager;
  }

  /**
   * 计算已使用内存的百分比，并返回。
   *
   * @param context 可传入应用程序上下文。
   * @return 已使用内存的百分比，以字符串形式返回。
   */
  public static String getUsedPercentValue(Context context) {
    String dir = "/proc/meminfo";
    try {
      FileReader fr = new FileReader(dir);
      BufferedReader br;
      br = new BufferedReader(fr, 2048);
      String memoryLine = br.readLine();
      String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
      br.close();
      long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
      long availableSize = getAvailableMemory(context) / 1024;
      int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
      return percent + "%";
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "悬浮窗";
  }

  /**
   * 获取当前可用内存，返回数据以字节为单位。
   *
   * @param context 可传入应用程序上下文。
   * @return 当前可用内存。
   */
  private static long getAvailableMemory(Context context) {
    ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
    getActivityManager(context).getMemoryInfo(mi);
    return mi.availMem;
  }
}