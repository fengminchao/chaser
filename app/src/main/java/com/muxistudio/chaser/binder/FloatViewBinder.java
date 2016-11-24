package com.muxistudio.chaser.binder;

import android.os.Binder;
import com.muxistudio.chaser.db.Word;
import com.muxistudio.chaser.service.FloatWindowService;

/**
 * Created by ybao on 16/11/23.
 */

public class FloatViewBinder extends Binder {

  private FloatWindowService mFloatWindowService;

  public FloatViewBinder(FloatWindowService service) {
    mFloatWindowService = service;
  }

  public void changeWord(Word word) {

  }

  public void changeCircleNum(int num) {

  }

  public void changeWordSize(int size) {

  }

  public void changeTime(long millisecond) {

  }

  public void removeFloatView() {

  }

}
