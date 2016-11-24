package com.muxistudio.chaser.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ybao on 16/11/17.
 */

public class WordExplainResult {

  public int errno;
  public String errmsg;
  @SerializedName("baesInfo")
  public BaseInfo baseInfo;

  public class BaseInfo {
    @SerializedName("word_name")
    public String wordName;

    public Exchange exchange;

    public class Exchange{
      @SerializedName("word_pl")
      public String wordPl;

      @SerializedName("word_past")
      public String wordPast;

      @SerializedName("word_done")
      public String wordDone;

      @SerializedName("word_ing")
      public String wordIng;

      @SerializedName("word_third")
      public String wordThird;

    }

    public Symbols symbols;

    public class Symbols{


    }
  }
}
