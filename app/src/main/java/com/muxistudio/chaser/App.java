package com.muxistudio.chaser;

import android.app.Application;
import android.content.Context;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by ybao on 16/11/16.
 */

public class App extends Application {

  public static Context sContext;

  @Override public void onCreate() {
    super.onCreate();
    sContext = this;
    SpeechUtility.createUtility(this, SpeechConstant.APPID +"=58317730");
  }
}
