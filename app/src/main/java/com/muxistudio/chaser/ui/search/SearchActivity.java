package com.muxistudio.chaser.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.widget.FrameLayout;
import android.widget.SearchView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.ui.ToolbarActivity;

/**
 * Created by ybao on 16/11/24.
 */

public class SearchActivity extends ToolbarActivity {

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.search_view) MaterialSearchView mSearchView;
  @BindView(R.id.toolbar_container) FrameLayout mToolbarContainer;

  public static void start(Context context) {
    Intent starter = new Intent(context, SearchActivity.class);
    context.startActivity(starter);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    initToolbar(mToolbar);
    //initView();
  }

  private void initView() {
    mSearchView.setAnimationDuration(0);
    mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        //Do some magic
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        //Do some magic
        return false;
      }
    });

    mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
      @Override
      public void onSearchViewShown() {
        //Do some magic
      }

      @Override
      public void onSearchViewClosed() {
        //Do some magic
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {

    getMenuInflater().inflate(R.menu.search,menu);
    MenuItem item = menu.findItem(R.id.action_search);
    mSearchView.setMenuItem(item);
    return true;
    //searchItem = menu.add(android.R.string.search_go);
    //searchItem.setIcon(R.drawable.ic_search_white_24dp);
    //MenuItemCompat.setActionView(searchItem, mSearchView);
    //MenuItemCompat.setShowAsAction(searchItem,
    //    MenuItemCompat.SHOW_AS_ACTION_ALWAYS | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

    //MenuInflater menuInflater = getMenuInflater();
    //menuInflater.inflate(R.menu.search, menu);
    //
    //MenuItem searchItem = menu.findItem(R.id.search);
    //mSearchView = (SearchView) searchItem.getActionView();
    //mSearchView.setQueryHint("fff");
    //
    //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    //mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    //return true;
  }

  @Override public boolean onSearchRequested(SearchEvent searchEvent) {
    //showSearch(true);
    return false;
  }

  //private void showSearch(boolean b) {
  //  if (b) {
  //    MenuItemCompat.expandActionView(searchItem);
  //  } else {
  //    MenuItemCompat.collapseActionView(searchItem);
  //  }
  //}

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}
