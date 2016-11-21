package com.muxistudio.chaser.net;

import com.muxistudio.chaser.bean.Wordbank;
import com.muxistudio.chaser.bean.WordDetail;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ybao on 16/11/17.
 */

public interface WordTutorService {

  @GET("/api/wordbank")
  Observable<List<Wordbank>> getWordbankList();

  @GET("/api/wordbank/{id}")
  Observable<List<WordDetail>> getWordbankInfo(@Path("id") int id);
}
