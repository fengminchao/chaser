package com.muxistudio.chaser.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.muxistudio.chaser.utils.FloatWindowManager;
import com.muxistudio.chaser.db.Word;
import com.muxistudio.chaser.utils.ChaserDaoHelper;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ybao on 16/11/16.
 */

public class FloatWindowService extends Service {

  private Handler mHandler = new Handler();
  private Timer mTimer;

  //一个单词出现的时间
  private long timeDisplay = 60 * 1000;

  //一组单词的个数
  private int wordNum = 10;

  //一组单词的循环次数
  private int circleNum = 5;

  //private long curWordId = 1;

  private final int wordListSize = 100;

  //当前单词的位置
  private int wordPos = 0;

  //当前循环的次数
  private int curCircleNum = 0;

  //当前要背的单词classid
  private int mClassId;

  //要背单词的 list ,长度为一组单词的个数
  private List<Word> mWordList;

  public class FloatViewBinder extends Binder {

    public void changeWordbank(int classId) {
      FloatWindowService.this.changeWordbank(classId);
    }

    //改变同一个词库的单词
    public void changeWord(int id) {
      FloatWindowService.this.changeWord(id);
    }

    public void changeCircleNum(int num) {
      FloatWindowService.this.changeCircleNum(num);
    }

    public void changeWordSize(int size) {
      FloatWindowService.this.changeWordSize(size);
    }

    public void changeTime(long time) {
      FloatWindowService.this.changeTime(time);
    }

    public void removeFloatView() {
      FloatWindowService.this.removeFloatView();
    }
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
    return new FloatViewBinder();
  }

  public void changeWordbank(int classId) {
    mClassId = classId;
    wordPos = 0;
    mWordList = ChaserDaoHelper.loadWord(mWordList.size(), classId);
    FloatWindowManager.updateFloatView(mWordList.get(wordPos));
  }

  //改变同一个词库的单词
  public void changeWord(int id) {
    if (id > mWordList.get(mWordList.size() - 1).getId() || id < mWordList.get(0).getId()) {
      mWordList = ChaserDaoHelper.loadWord(id, mWordList.size(), mClassId);
      wordPos = 0;
    } else {
      wordPos = id - mWordList.get(0).getId().intValue();
    }
    FloatWindowManager.updateFloatView(mWordList.get(wordPos));
  }

  public void changeCircleNum(int num) {
    if (curCircleNum > num) {
      curCircleNum = 1;
      wordPos = 0;
      mWordList = ChaserDaoHelper.loadWord(mWordList.get(mWordList.size() - 1).getId() + 1,
          mWordList.size(), mClassId);
    }
    circleNum = num;
  }

  public void changeWordSize(int size) {
    wordNum = size;
    mWordList = ChaserDaoHelper.loadWord(mWordList.get(0).getId(), size, mClassId);
    if (wordPos > mWordList.size() - 1) {
      wordPos = 0;
      curCircleNum++;
      if (curCircleNum > circleNum) {
        mWordList =
            ChaserDaoHelper.loadWord(mWordList.get(mWordList.size() - 1).getId(), size, mClassId);
        curCircleNum = 1;
      }
    }
  }

  public void changeTime(long time) {
    timeDisplay = time;
  }

  public void removeFloatView() {
    FloatWindowManager.removeFloatView(FloatWindowService.this);
    stopSelf();
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
      } else {
        mHandler.post(new Runnable() {
          @Override public void run() {
            showEndMessageIfNull();
            FloatWindowManager.updateFloatView(mWordList.get(wordPos));
            if (wordPos == mWordList.size() - 1) {
              if (curCircleNum < circleNum) {
                wordPos = 0;
                curCircleNum++;
              } else {
                wordPos = 0;
                mWordList =
                    ChaserDaoHelper.loadWord(mWordList.get(mWordList.size() - 1).getId() + 1,
                        wordNum, mClassId);
                curCircleNum = 1;
              }
              return;
            }
            wordPos++;
          }
        });
      }
    }
  }

  /**
   * 当前词库背诵完成时调用
   */
  public void showEndMessageIfNull() {
    if (mWordList == null || mWordList.size() == 0) {
      FloatWindowManager.updateFloatView(null);
    }
  }
}
