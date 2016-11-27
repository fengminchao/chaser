package com.muxistudio.chaser.ui;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.speech.RecognizerIntent;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.db.Word;
import com.muxistudio.chaser.service.FloatWindowService;
import com.muxistudio.chaser.ui.about.AboutActivity;
import com.muxistudio.chaser.ui.search.SearchActivity;
import com.muxistudio.chaser.ui.setting.SettingActivity;
import com.muxistudio.chaser.ui.wordbank.WordbankActivity;
import com.muxistudio.chaser.utils.ChaserDaoHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends ToolbarActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.btn) Button mBtn;
  //@BindView(R.id.btn_rm) Button mBtnRm;
  @BindView(R.id.content) FrameLayout mContent;
  @BindView(R.id.nav_view) NavigationView mNavView;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.btn_stop) Button mBtnStop;
  @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
  @BindView(R.id.et_word_num) EditText mEtWordNum;
  @BindView(R.id.et_circle_num) EditText mEtCircleNum;
  @BindView(R.id.et_time) EditText mEtTime;
  @BindView(R.id.btn_enter) Button mBtnEnter;
  @BindView(R.id.btn_listen) Button mBtnListen;
  @BindView(R.id.et_listen) EditText mEtListen;

  private ShareActionProvider mShareActionProvider;

  //private RecognizerListener mRecognizerListener;

  private ServiceConnection mConnection = new ServiceConnection() {
    @Override public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      Log.d("main connect", componentName.getClassName());
      //iBinder.
    }

    @Override public void onServiceDisconnected(ComponentName componentName) {
      Log.d("main disconnect", componentName.getClassName());
    }
  };

  private Intent serviceIntent;
  private List<Word> wordList;

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
            //bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
            serviceIntent = intent;
          }
        } else {
          Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
          startService(intent);
          //bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
        }
      }
    });

    mBtnStop.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        stopService(serviceIntent);
      }
    });

    mBtnEnter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //initVoiceUI();
        showGoogleInputDialog();
      }
    });

    mBtnListen.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        wordList = ChaserDaoHelper.getWordDao().loadAll();
        Toast.makeText(MainActivity.this, wordList.get(2000).getWord(), Toast.LENGTH_SHORT).show();
      }
    });

    initVoice();
  }

  private RecognizerListener mRecognizerListener;

  public void initVoice() {
    //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
    SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this, null);
    //2.设置听写参数，详见《科大讯飞MSC API手册
    // (Android)》SpeechConstant类
    mIat.setParameter(SpeechConstant.DOMAIN, "iat");
    mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
    mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
    //3.开始听写   mIat.startListening(mRecoListener);
    //听写监听器
    mRecognizerListener = new RecognizerListener() {
      //听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
      //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
      //关于解析Json的代码可参见MscDemo中JsonParser类；
      //isLast等于true时会话结束。

      @Override public void onVolumeChanged(int i, byte[] bytes) {

      }

      @Override public void onBeginOfSpeech() {

      }

      @Override public void onEndOfSpeech() {

      }

      @Override public void onResult(RecognizerResult recognizerResult, boolean b) {
        Toast.makeText(MainActivity.this, recognizerResult.getResultString(), Toast.LENGTH_SHORT)
            .show();
      }

      @Override public void onError(SpeechError speechError) {
        speechError.printStackTrace();
      }

      @Override public void onEvent(int i, int i1, int i2, Bundle bundle) {

      }
    };
  }

  public void initVoiceUI() {
    //1.创建RecognizerDialog对象
    InitListener initListener = new InitListener() {
      @Override public void onInit(int i) {

      }
    };
    RecognizerDialog mDialog = new RecognizerDialog(this, null); //2.设置accent、language等参数
    mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
    mDialog.setParameter(SpeechConstant.ACCENT,
        "mandarin");
    //若要将UI控件用于语义理解,必须添加以下参数设置,设置之后onResult回调返回将是语义理解 //结果
    // mDialog.setParameter("asr_sch", "1");
    // mDialog.setParameter("nlp_version", "2.0");
    //3.设置回调接口 mDialog.setListener(mRecognizerDialogListener); //4.显示dialog,接收语音输入
    mDialog.show();
  }

  private void initView() {
    mNavView.setNavigationItemSelectedListener(this);
    View headerLayout = mNavView.getHeaderView(0);
    Log.d("header", "header");
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
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
      case R.id.action_search:
        SearchActivity.start(MainActivity.this);
    }
    return true;
  }

  private final int SPEECH_REQUEST_CODE = 123;

  public void showGoogleInputDialog() {
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    try {
      startActivityForResult(intent, SPEECH_REQUEST_CODE);
    } catch (ActivityNotFoundException a) {
      Toast.makeText(getApplicationContext(), "Your device is not supported!",
          Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case SPEECH_REQUEST_CODE: {
        if (resultCode == RESULT_OK && null != data) {

          ArrayList<String> result = data
              .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
          mEtListen.setText(result.get(0));
        }
        break;
      }

    }
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
