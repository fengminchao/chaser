package com.muxistudio.chaser.ui.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.bean.WordExplainResult;
import com.muxistudio.chaser.bean.WordSuggestion;
import com.muxistudio.chaser.net.ChaserApi;
import com.muxistudio.chaser.ui.BaseActivity;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import static android.view.View.GONE;

/**
 * Created by ybao on 16/11/24.
 */

public class SearchActivity extends BaseActivity{

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.search_view) MaterialSearchView mSearchView;
  @BindView(R.id.toolbar_container) FrameLayout mToolbarContainer;
  @BindView(R.id.tv_word) TextView mTvWord;
  @BindView(R.id.tv_phonetic_en) Button mTvPhoneticEn;
  @BindView(R.id.tv_phonetic_us) Button mTvPhoneticUs;
  @BindView(R.id.rv_explain) RecyclerView mRvExplain;
  @BindView(R.id.rv_sentences) RecyclerView mRvSentences;
  @BindView(R.id.layout_explain) RelativeLayout mLayoutExplain;

  private Subscription mSubscription;

  private ExplainAdapter mExplainAdapter;
  private SentenceAdapter mSentenceAdapter;
  private List<WordExplainResult.BaseInfo.Symbol.Part> mExplainList;
  private List<WordExplainResult.Sentence> mSentenceList;

  public static void start(Context context) {
    Intent starter = new Intent(context, SearchActivity.class);
    context.startActivity(starter);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    initView();
  }

  private void initView() {
    //mSearchView.setAnimation(null);
    mSearchView.setAnimation(null);
    mSearchView.setVoiceSearch(true);
    mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        Log.d("ww", query);
        loadSearchResult(query);
        mSearchView.closeSearch();
        return true;
      }

      @Override public boolean onQueryTextChange(String newText) {
        loadSearchSuggestion(newText);
        return true;
      }
    });
    //mSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    //  @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    //    mSearchView.setSubmitOnClick(true);
    //    Log.d("search", "search");
    //  }
    //});

    mRvExplain.setLayoutManager(new LinearLayoutManager(this));
    mRvExplain.setHasFixedSize(false);
    mRvSentences.setLayoutManager(new LinearLayoutManager(this));
    mRvSentences.setHasFixedSize(false);
  }

  public void loadSearchResult(String query) {
    if (mSubscription != null && !mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
    int index = query.indexOf(".");
    int lastSpaceIndex = query.substring(0, index).lastIndexOf(" ");
    query = query.substring(0, lastSpaceIndex);
    query = query.replace(" ", "+");
    ChaserApi.getService()
        .searchWord(query)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<WordExplainResult>() {
          @Override public void call(WordExplainResult wordExplainResult) {
            if (wordExplainResult.errno != 0){
              return;
            }
            mLayoutExplain.setVisibility(View.VISIBLE);
            mTvWord.setText(wordExplainResult.baseInfo.wordName);
            Typeface typeface = Typeface.createFromAsset(getAssets(),"font/segoeui.ttf");
            mTvPhoneticEn.setTypeface(typeface);
            mTvPhoneticUs.setTypeface(typeface);
            mTvPhoneticEn.setText(wordExplainResult.baseInfo.symbols.get(0).phEn);
            mTvPhoneticUs.setText(wordExplainResult.baseInfo.symbols.get(0).phAm);
            mExplainList = wordExplainResult.baseInfo.symbols.get(0).parts;
            mSentenceList = wordExplainResult.sentence;
            Log.d("search",mSentenceList.size() + "");
            Log.d("search",mSentenceList.get(0).networdEn);
            mExplainAdapter = new ExplainAdapter(mExplainList);
            mSentenceAdapter = new SentenceAdapter(mSentenceList);
            mRvExplain.setAdapter(mExplainAdapter);
            mRvSentences.setAdapter(mSentenceAdapter);
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            throwable.printStackTrace();
            mLayoutExplain.setVisibility(GONE);
          }
        });
  }

  public void loadSearchSuggestion(String key) {
    if (key.contains(" ")) {
      return;
    }
    if (mSubscription != null && !mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
    mSubscription = ChaserApi.getService()
        .getWordSuggestion(key)
        .subscribeOn(Schedulers.io())
        .map(new Func1<WordSuggestion, Object>() {
          @Override public Object call(WordSuggestion wordSuggestion) {
            return wordSuggestion.message;
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Object>() {
          @Override public void call(Object o) {
            List<WordSuggestion.Message> messages = (List<WordSuggestion.Message>) o;
            String[] suggestions = new String[messages.size()];
            for (int i = 0; i < messages.size(); i++) {
              suggestions[i] = messages.get(i).key + " ";
              for (int j = 0; j < messages.get(i).means.size(); j++) {
                suggestions[i] += messages.get(i).means.get(j).part + TextUtils.join(";",
                    messages.get(i).means.get(j).means);
              }
              if (suggestions[i].length() > 20) {
                suggestions[i] = suggestions[i].substring(0, 20) + "...";
              }
            }
            mSearchView.setSuggestions(suggestions);
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            throwable.printStackTrace();
          }
        });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.search, menu);
    MenuItem item = menu.findItem(R.id.search);
    mSearchView.setMenuItem(item);
    return true;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
      ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      if (matches != null && matches.size() > 0) {
        String searchWrd = matches.get(0);
        if (!TextUtils.isEmpty(searchWrd)) {
          mSearchView.setQuery(searchWrd, false);
        }
      }

      return;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override public void onBackPressed() {
    if (mSearchView.isSearchOpen()) {
      mSearchView.closeSearch();
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    switch (itemId){
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return true;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}
