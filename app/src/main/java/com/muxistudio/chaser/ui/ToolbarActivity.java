package com.muxistudio.chaser.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.muxistudio.chaser.R;

/**
 * Created by ybao on 16/11/21.
 */

public class ToolbarActivity extends BaseActivity{

  private Toolbar mToolbar;

  public void initToolbar(Toolbar toolbar){
    if (toolbar != null){
      mToolbar = toolbar;
      setSupportActionBar(mToolbar);
      if (canBack()){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      }
    }
  }
  //@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
  //  super.onCreate(savedInstanceState);
  //  mToolbar = (Toolbar) findViewById(R.id.toolbar);
  //  setSupportActionBar(mToolbar);
  //  if (canBack()){
  //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //  }
  //}

  protected boolean canBack(){
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home){
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
