package com.muxistudio.chaser.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ybao on 16/11/17.
 */

public class WordDetail {
  /**
   * name : 六级必备词汇
   * count : 2080
   * data : [{"word":"panther","phonetic":"[ˈpænθə]   ","explain":"n. 豹，黑豹；美洲豹"}]
   */

  public String name;
  public int count;
  /**
   * word : panther
   * phonetic : [ˈpænθə]
   * explain : n. 豹，黑豹；美洲豹
   */

  public List<DataBean> data;

  public  class DataBean {
    public String word;
    public String phonetic;
    public String explain;
  }
}
