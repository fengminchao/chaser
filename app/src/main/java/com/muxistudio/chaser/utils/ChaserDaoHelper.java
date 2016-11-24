package com.muxistudio.chaser.utils;

import com.muxistudio.chaser.App;
import com.muxistudio.chaser.db.DaoMaster;
import com.muxistudio.chaser.db.DaoSession;
import com.muxistudio.chaser.db.Word;
import com.muxistudio.chaser.db.WordDao;
import com.muxistudio.chaser.db.WordbankDao;
import java.util.List;

/**
 * Created by ybao on 16/11/21.
 */

public class ChaserDaoHelper {

  private static DaoSession sDaoSession;

  public static DaoSession getInstance() {
    if (sDaoSession == null) {
      DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(App.sContext, "chaser.db");
      sDaoSession = new DaoMaster(helper.getReadableDatabase()).newSession();
    }
    return sDaoSession;
  }

  public static WordbankDao getWordbankDao() {
    return getInstance().getWordbankDao();
  }

  public static WordDao getWordDao() {
    return getInstance().getWordDao();
  }

  public static List<Word> loadWord(int size,int classId){
    return getWordDao().queryBuilder()
        .orderAsc(WordDao.Properties.Id)
        .limit(size)
        .where(WordDao.Properties.ClassId.eq(classId))
        .list();
  }

  public static List<Word> loadWord(long id, int size, int classId) {
    return getWordDao().queryBuilder()
        .orderAsc(WordDao.Properties.Id)
        .limit(size)
        .where(WordDao.Properties.ClassId.eq(classId), WordDao.Properties.Id.ge(id))
        .list();
  }
}
