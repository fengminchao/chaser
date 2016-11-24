package com.muxistudio.chaser.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.ui.ToolbarActivity;

/**
 * Created by ybao on 16/11/24.
 */

public class SearchActivity extends ToolbarActivity {

  @BindView(R.id.toolbar) Toolbar mToolbar;
  private MenuItem searchItem;
  private SearchView mSearchView;

  public static void start(Context context) {
    Intent starter = new Intent(context, SearchActivity.class);
    context.startActivity(starter);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    initToolbar(mToolbar);
    initView();
  }

  private void initView() {
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    mSearchView = new SearchView(getSupportActionBar().getThemedContext());
    mSearchView.setSubmitButtonEnabled(true);
    mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    mSearchView.setIconifiedByDefault(true);
    mSearchView.setMaxWidth(1000);
    mSearchView.setOnSearchClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        showSearch(true);
      }
    });
    SearchView.SearchAutoComplete autoComplete =
        (SearchView.SearchAutoComplete) mSearchView.findViewById(
            android.support.v7.appcompat.R.id.search_src_text);
    autoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override public void onFocusChange(View view, boolean b) {
        if (!b) {
          showSearch(false);
        }
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    searchItem = menu.add(android.R.string.search_go);
    searchItem.setIcon(R.drawable.ic_search_white_24dp);
    MenuItemCompat.setActionView(searchItem, mSearchView);
    MenuItemCompat.setShowAsAction(searchItem,
        MenuItemCompat.SHOW_AS_ACTION_ALWAYS | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

    return true;
  }

  @Override public boolean onSearchRequested(SearchEvent searchEvent) {
    showSearch(true);
    return false;
  }

  private void showSearch(boolean b) {
    if (b) {
      MenuItemCompat.expandActionView(searchItem);
    } else {
      MenuItemCompat.collapseActionView(searchItem);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}
