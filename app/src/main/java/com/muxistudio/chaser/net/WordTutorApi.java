package com.muxistudio.chaser.net;

import com.muxistudio.chaser.bean.Wordbank;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ybao on 16/11/17.
 */

public class WordTutorApi {

  private static WordTutorService mWordTutorService;

  public WordTutorApi() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build();
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("182.254.247.206:7000/")
        .client(client)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    mWordTutorService = retrofit.create(WordTutorService.class);
  }

  public static WordTutorService getService(){
    if (mWordTutorService == null){
      mWordTutorService = new WordTutorApi().getService();
      return mWordTutorService;
    }
    return mWordTutorService;
  }
}
