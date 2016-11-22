package com.muxistudio.chaser.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.bean.WordDetail;
import com.muxistudio.chaser.db.Word;
import com.muxistudio.chaser.utils.DimenUtil;

/**
 * Created by ybao on 16/11/16.
 */

public class FloatWordView extends LinearLayout {

  public static int x = DimenUtil.getScreenWidth() / 2;
  public static int y = 0;

  public static int width = DimenUtil.dp2px(24);
  public static int height = DimenUtil.dp2px(12);

  private static WindowManager.LayoutParams mLayoutParams;

  //@BindView(R.id.tv_word) TextView mTvWord;
  //@BindView(R.id.tv_explain) TextView mTvExplain;
  private TextView mTvWord;
  private TextView mTvExplain;

  //手指在屏幕的坐标
  private float xInScreen;
  private float yInScreen;

  //手指在 view 所处的坐标
  private float xInView;
  private float yInView;

  private WindowManager mWindowManager;

  private Context mContext;

  public FloatWordView(Context context) {
    super(context);
    mContext = context;
    mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    initWordView();
  }

  private void initWordView() {
    LayoutInflater.from(mContext).inflate(R.layout.view_float_word, this);
    Log.d("tag", "tag");
    //ButterKnife.bind(this,view);
    mTvWord = (TextView) findViewById(R.id.tv_word);
    mTvExplain = (TextView) findViewById(R.id.tv_explain);
    mTvWord.setText("hello");
    mTvExplain.setText("hi");
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    int action = event.getAction();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        xInView = event.getX();
        yInView = event.getY();
        xInScreen = event.getRawX();
        yInScreen = event.getRawY();
        break;
      case MotionEvent.ACTION_MOVE:
        xInView = event.getX();
        yInView = event.getY();
        xInScreen = event.getRawX();
        yInScreen = event.getRawY();
        updateFloatView();
        break;
      default:
        break;
    }
    return true;
  }

  private void updateFloatView() {
    x = (int)(xInScreen - xInView);
    x = x <= 0 ? 0 : x;
    y = (int)(yInScreen - yInView);
    y = y <= 0 ? 0 : y;
    mLayoutParams.x = x;
    mLayoutParams.y = y;
    mWindowManager.updateViewLayout(this,mLayoutParams);
  }

  public void setWord(Word word){
    mTvWord.setText(word.getWord());
    mTvExplain.setText(word.getExplain());
  }

  public void setParams(WindowManager.LayoutParams layoutParams){
    mLayoutParams = layoutParams;
  }

}