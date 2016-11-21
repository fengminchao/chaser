package com.muxistudio.chaser.utils;

import com.muxistudio.chaser.App;
import com.muxistudio.chaser.db.DaoMaster;
import com.muxistudio.chaser.db.DaoSession;
import com.muxistudio.chaser.db.WordDao;
import com.muxistudio.chaser.db.WordbankDao;

/**
 * Created by ybao on 16/11/21.
 */

public class ChaserDaoHelper {

  private static DaoMaster.DevOpenHelper sDevOpenHelper;
  private static DaoSession sDaoSession;

  public static DaoSession getInstance(){
    if (sDaoSession == null) {
      DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(App.sContext,"chaser.db");
      sDaoSession =  new DaoMaster(helper.getReadableDatabase()).newSession();
    }
    return sDaoSession;
  }

  public static WordbankDao getWordbankDao(){
    return getInstance().getWordbankDao();
  }

  public static WordDao getWordDao(){
    return getInstance().getWordDao();
  }
}
