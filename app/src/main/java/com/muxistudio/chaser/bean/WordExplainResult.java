package com.muxistudio.chaser.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by ybao on 16/11/17.
 */

public class WordExplainResult {

  public int errno;
  public String errmsg;
  @SerializedName("baesInfo") public BaseInfo baseInfo;

  public class BaseInfo {
    @SerializedName("word_name") public String wordName;

    //public Exchange exchange;
    //
    //public class Exchange{
    //  @SerializedName("word_pl")
    //  public String wordPl;
    //
    //  @SerializedName("word_past")
    //  public String wordPast;
    //
    //  @SerializedName("word_done")
    //  public String wordDone;
    //
    //  @SerializedName("word_ing")
    //  public String wordIng;
    //
    //  @SerializedName("word_third")
    //  public String wordThird;
    //
    //}

    public List<Symbol> symbols;

    public class Symbol {

      @SerializedName("ph_en") public String phEn;
      @SerializedName("ph_am") public String phAm;
      @SerializedName("ph_en_mp3") public String phEnMp3;
      @SerializedName("ph_am_mp3") public String phAmMp3;

      public List<Part> parts;

      public class Part {

        public String part;

        public List<String> means;
      }
    }
  }

  public List<Sentence> sentence;

  public class Sentence {

    @SerializedName("Network_en") public String networdEn;
    @SerializedName("Network_cn") public String networdCn;
    @SerializedName("tts_mp3") public String ttsMp3;
  }

}
