package com.muxistudio.chaser.ui.wordbank;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.bean.WordDetail;
import com.muxistudio.chaser.bean.Wordbank;
import com.muxistudio.chaser.db.Word;
import com.muxistudio.chaser.net.ChaserApi;
import com.muxistudio.chaser.ui.ToolbarActivity;
import com.muxistudio.chaser.utils.ChaserDaoHelper;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ybao on 16/11/21.
 */

public class BankMarketActivity extends ToolbarActivity {

  @BindView(R.id.recyclerview) RecyclerView mRecyclerview;

  private List<Wordbank> mWordbankList;
  private BankMarketAdapter mBankMarketAdapter;

  private Subscription mSubscription;

  public static void start(Context context) {
      Intent starter = new Intent(context, BankMarketActivity.class);
      context.startActivity(starter);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bankmarket);
    ButterKnife.bind(this);
    loadBanks();
    initRecyclerView();
  }

  private void initRecyclerView() {
    mRecyclerview.setHasFixedSize(true);
    mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
  }

  private void loadBanks() {
    ChaserApi.getService()
        .getWordbank()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<Wordbank>>() {
          @Override public void call(List<Wordbank> wordbanks) {
            mWordbankList = wordbanks;
            mBankMarketAdapter = new BankMarketAdapter(mWordbankList);
            mBankMarketAdapter.setOnItemClickListener(new BankMarketAdapter.OnItemClickListener() {
              @Override public void onItemClick(Wordbank wordbank) {
                showDialog(wordbank);
              }
            });
            mRecyclerview.setAdapter(mBankMarketAdapter);
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            throwable.printStackTrace();
          }
        });
  }

  //显示alertDialog
  private void showDialog(final Wordbank wordbank) {

    final AlertDialog dialog = new AlertDialog.Builder(this).setTitle(wordbank.name)
        .setMessage(wordbank.category + "\t" + wordbank.download + "次下载")
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
          }
        })
        .setPositiveButton("下载", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            Subscription s = ChaserApi.getService().getWord(wordbank.classId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WordDetail>() {
                  @Override public void call(WordDetail wordDetail) {
                    com.muxistudio.chaser.db.Wordbank wordbankData = new com.muxistudio.chaser.db.Wordbank();
                    wordbankData.setCategory(wordbank.category);
                    wordbankData.setClassId(wordbank.classId);
                    wordbankData.setName(wordbank.name);
                    ChaserDaoHelper.getWordbankDao().insert(wordbankData);
                    for (int i = 0;i < wordDetail.count;i ++){
                      Word word = new Word();
                      word.setClassId(wordbank.classId);
                      word.setExplain((wordDetail.data.get(i)).explain);
                      word.setPhonetic((wordDetail.data.get(i)).phonetic);
                      word.setWord((wordDetail.data.get(i)).word);
                      ChaserDaoHelper.getWordDao().insert(word);
                    }
                    hideProgress();
                  }
                }, new Action1<Throwable>() {
                  @Override public void call(Throwable throwable) {
                    throwable.printStackTrace();
                  }
                });
            addSubscription(s);
            showProgress("正在下载词库，请稍后");
            dialogInterface.dismiss();
          }
        })
        .create();
    dialog.show();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}
