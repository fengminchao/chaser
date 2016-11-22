package com.example;

import java.io.IOException;
import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreendaoGenerator {

  public static void main(String[] args) throws Exception{
    Schema schema = new Schema(1,"com.muxistudio.chaser.db");

    Entity wordbank = schema.addEntity("Wordbank");

    wordbank.addIdProperty();
    wordbank.addStringProperty("category");
    wordbank.addStringProperty("name");
    wordbank.addIntProperty("classId");

    Entity word = schema.addEntity("Word");
    word.addIdProperty();
    word.addStringProperty("word");
    word.addStringProperty("phonetic");
    word.addStringProperty("explain");
    word.addIntProperty("classId");

    new DaoGenerator().generateAll(schema,"../Chaser/app/src/main/java");
  }
}
