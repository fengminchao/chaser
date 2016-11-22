package com.muxistudio.chaser.net;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ybao on 16/11/17.
 */

public class ChaserApi {

  private static ChaserService sChaserService;

  public ChaserApi() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build();
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://182.254.247.206:8900/")
        .client(client)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    sChaserService = retrofit.create(ChaserService.class);
  }

  public static ChaserService getService(){
    if (sChaserService == null){
      sChaserService = new ChaserApi().getService();
      return sChaserService;
    }
    return sChaserService;
  }
}
