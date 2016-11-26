package com.muxistudio.chaser.bean;

import java.util.List;

/**
 * Created by ybao on 16/11/26.
 */

public class WordSuggestion {

  public int status;
  public List<Message> message;

  public class Message{

    //单词
    public String key;
    public List<Mean> means;

    public class Mean{
      //词性
      public String part;
      public List<String> means;
    }
  }
}
