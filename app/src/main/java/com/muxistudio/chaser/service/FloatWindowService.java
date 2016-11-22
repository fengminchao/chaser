package com.muxistudio.chaser.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.muxistudio.chaser.FloatWindowManager;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ybao on 16/11/16.
 */

public class FloatWindowService extends Service {

  private Handler mHandler = new Handler();
  private Timer mTimer;

  private MyBinder mMyBinder = new MyBinder();

  public class MyBinder extends Binder{

  }

  @Override public void onCreate() {
    super.onCreate();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    if (mTimer == null) {
      mTimer = new Timer();
      mTimer.scheduleAtFixedRate(new RefreshTask(), 0, 1000);
    }
    Log.d("service", "service start");
    return super.onStartCommand(intent, flags, startId);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mTimer.cancel();
    mTimer = null;
    mHandler = null;
  }

  @Override public boolean onUnbind(Intent intent) {
    return super.onUnbind(intent);
  }

  @Override public IBinder onBind(Intent intent) {
    //return null;
    return mMyBinder;
  }

  class RefreshTask extends TimerTask {

    @Override public void run() {
      Log.d("service ", "service task");
      if (!FloatWindowManager.isWindowShowing()) {
        mHandler.post(new Runnable() {
          @Override public void run() {
            FloatWindowManager.createFloatView(FloatWindowService.this);
          }
        });
      } else if (FloatWindowManager.isWindowShowing()) {
        mHandler.post(new Runnable() {
          @Override public void run() {
            FloatWindowManager.updateFloatView(FloatWindowService.this);
          }
        });
      }
    }
  }
}
