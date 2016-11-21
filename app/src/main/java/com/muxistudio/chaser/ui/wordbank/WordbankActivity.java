package com.muxistudio.chaser.ui.wordbank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.db.Wordbank;
import com.muxistudio.chaser.ui.ToolbarActivity;
import com.muxistudio.chaser.utils.ChaserDaoHelper;
import java.util.List;

/**
 * Created by ybao on 16/11/21.
 */

public class WordbankActivity extends ToolbarActivity {

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.recyclerview) RecyclerView mRecyclerview;

  private WordbankAdapter mAdapter;

  public static void start(Context context) {
    Intent starter = new Intent(context, WordbankActivity.class);
    context.startActivity(starter);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wordbank);
    ButterKnife.bind(this);
    initVariable();
    initView();
  }

  private void initVariable() {
    List<Wordbank> wordbanks = ChaserDaoHelper.getWordbankDao().loadAll();
    mAdapter = new WordbankAdapter(wordbanks);
  }

  private void initView() {
    mRecyclerview.setHasFixedSize(true);
    mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerview.setAdapter(mAdapter);
  }
}
