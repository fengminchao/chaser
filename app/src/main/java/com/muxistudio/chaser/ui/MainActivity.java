package com.muxistudio.chaser.ui;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ShareActionProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.service.FloatWindowService;
import com.muxistudio.chaser.ui.about.AboutActivity;
import com.muxistudio.chaser.ui.setting.SettingActivity;
import com.muxistudio.chaser.ui.wordbank.WordbankActivity;

public class MainActivity extends ToolbarActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.btn) Button mBtn;
  //@BindView(R.id.btn_rm) Button mBtnRm;
  @BindView(R.id.content) FrameLayout mContent;
  @BindView(R.id.nav_view) NavigationView mNavView;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.btn_stop) Button mBtnStop;
  @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

  private ShareActionProvider mShareActionProvider;

  private ServiceConnection mConnection = new ServiceConnection() {
    @Override public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      Log.d("main connect", componentName.getClassName());
    }

    @Override public void onServiceDisconnected(ComponentName componentName) {
      Log.d("main disconnect", componentName.getClassName());
    }
  };

  private Intent serviceIntent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mDrawerLayout.openDrawer(Gravity.LEFT);
    //getSupportActionBar().setDisplayShowHomeEnabled(true);
    //initToolbar(mToolbar);

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
            bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
            serviceIntent = intent;
          }
        } else {
          Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
          startService(intent);
          bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
        }
      }
    });

    mBtnStop.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        stopService(serviceIntent);
      }
    });
  }

  private void initView() {
    mNavView.setNavigationItemSelectedListener(this);
    View headerLayout = mNavView.getHeaderView(0);
    Log.d("header", "header");
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    //getMenuInflater().inflate(R.menu.menu_share, menu);
    //MenuItem item = menu.findItem(R.id.menu_item_share);
    //mShareActionProvider = (ShareActionProvider) item.getActionProvider();
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    switch (itemId) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(Gravity.LEFT);
        break;
      case R.id.action_wordbank:
        WordbankActivity.start(MainActivity.this);
        break;
      case R.id.action_setting:
        SettingActivity.start(MainActivity.this);
        break;
      case R.id.action_about:
        AboutActivity.start(MainActivity.this);
        break;
    }
    return true;
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int itemId = item.getItemId();
    switch (itemId) {
      case R.id.action_wordbank:
        WordbankActivity.start(MainActivity.this);
        return true;
      case R.id.action_about:
        AboutActivity.start(MainActivity.this);
        return true;
      case R.id.action_setting:
        SettingActivity.start(MainActivity.this);
        return true;
      case R.id.action_share:
        //mShareActionProvider.setShareIntent();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "share from chaser"));
        return true;
    }
    return false;
  }

  @Override protected boolean canBack() {
    return false;
  }
}
