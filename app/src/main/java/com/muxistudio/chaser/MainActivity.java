package com.muxistudio.chaser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.muxistudio.chaser.service.FloatWindowService;
import com.muxistudio.chaser.ui.FloatWordView.FloatWordView;
import com.muxistudio.chaser.utils.DimenUtil;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.btn) Button mBtn;
  @BindView(R.id.btn_rm) Button mBtnRm;

  private LinearLayout mLinearLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    mLinearLayout = (LinearLayout) findViewById(R.id.content);
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

        //Log.d("main","addview");
        //FloatWordView wordView = new FloatWordView(MainActivity.this);
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DimenUtil.dp2px(20),
        //    DimenUtil.dp2px(40)
        //);
        //mLinearLayout.addView(wordView,params);
      }
    });

    mBtnRm.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        FloatWindowManager.removeFloatView(MainActivity.this);
      }
    });
  }
}
