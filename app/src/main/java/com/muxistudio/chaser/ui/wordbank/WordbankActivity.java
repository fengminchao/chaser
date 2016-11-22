package com.muxistudio.chaser.ui.wordbank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
    initToolbar(mToolbar);
    mToolbar.setTitle("词库");
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
    mAdapter.setOnItemClickListener(new WordbankAdapter.ItemClickListener() {
      @Override public void onItemClick(String name, int classId) {

      }
    });
    mRecyclerview.setAdapter(mAdapter);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_wordbank,menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_bankmarket){
      BankMarketActivity.start(WordbankActivity.this);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
