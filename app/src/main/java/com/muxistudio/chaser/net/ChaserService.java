package com.muxistudio.chaser.net;

import com.muxistudio.chaser.bean.WordDetail;
import com.muxistudio.chaser.bean.WordExplainResult;
import com.muxistudio.chaser.bean.WordSuggestion;
import com.muxistudio.chaser.bean.Wordbank;
import java.util.List;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ybao on 16/11/17.
 */

public interface ChaserService {

  //@GET("http://www.iciba.com/index.php?a=getWordMean&c=search&list=1%2C3%2C4%2C8%2C9%2C12%2C13%2C15&_=1479276597365&callback=jsonp7")
  //Callback<Response<>>

  @GET("api/wordbank") Observable<List<Wordbank>> getWordbank();

  @GET("api/wordbank/{id}") Observable<WordDetail> getWord(@Path("id") int id);

  //查询单词和句子,句子用+代表空格
  @GET("http://www.iciba.com/index.php?a=getWordMean&c=search&list=1%2C3%2C4%2C8%2C9%2C12%2C13%2C15&_=1479276597365")
  Observable<WordExplainResult> searchWord(@Query("word") String word);

  @GET("http://dict-mobile.iciba.com/interface/index.php?c=word&m=getsuggest&nums=5&client=6&uid=0&is_need_mean=1&_=1480128476806")
  Observable<WordSuggestion> getWordSuggestion(@Query("word") String word);
}
