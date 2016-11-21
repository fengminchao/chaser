package com.muxistudio.chaser.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.bean.Wordbank;
import com.muxistudio.chaser.service.FloatWindowService;
import com.muxistudio.chaser.ui.about.AboutActivity;
import com.muxistudio.chaser.ui.setting.SettingActivity;
import com.muxistudio.chaser.ui.wordbank.WordbankActivity;

public class MainActivity extends ToolbarActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.btn) Button mBtn;
  //@BindView(R.id.btn_rm) Button mBtnRm;
  @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
  @BindView(R.id.content) FrameLayout mContent;
  @BindView(R.id.nav_view) NavigationView mNavView;
  @BindView(R.id.toolbar) Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initToolbar(mToolbar);
    mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
    //mToolbar.setTitle(getString(R.string.app_name));
    initView();
    mBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

        if (Build.VERSION.SDK_INT >= 23) {
          if (!Settings.canDrawOverlays(MainActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1234);
          } else {
            Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
            startService(intent);
          }
        }
      }
    });
  }

  private void initView() {
    mNavView.setNavigationItemSelectedListener(this);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    switch (itemId){
      case R.id.action_wordbank:
        WordbankActivity.start(MainActivity.this);
        return true;
      case R.id.action_setting:
        SettingActivity.start(MainActivity.this);
        return true;
      case R.id.action_about:
        AboutActivity.start(MainActivity.this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {

    return false;
  }

  @Override protected boolean canBack() {
    return false;
  }
}
