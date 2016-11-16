package com.muxistudio.chaser.ui.FloatWordView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.utils.DimenUtil;
import java.util.logging.Logger;
import org.w3c.dom.Text;

/**
 * Created by ybao on 16/11/16.
 */

public class FloatWordView extends LinearLayout {

  public static int x = DimenUtil.getScreenWidth() / 2;
  public static int y = 0;

  public static int width = DimenUtil.dp2px(24);
  public static int height = DimenUtil.dp2px(12);

  //@BindView(R.id.tv_word) TextView mTvWord;
  //@BindView(R.id.tv_explain) TextView mTvExplain;
  private TextView mTvWord;
  private TextView mTvExplain;

  private Context mContext;

  public FloatWordView(Context context) {
    super(context);
    mContext = context;
    initWordView();
  }

  private void initWordView() {
    LayoutParams params =
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    LayoutInflater.from(mContext).inflate(R.layout.view_float_word, this);
    Log.d("tag","tag");
    //ButterKnife.bind(this,view);
    mTvWord = (TextView) findViewById(R.id.tv_word);
    mTvExplain = (TextView) findViewById(R.id.tv_explain);
    mTvWord.setText("hello");
    mTvExplain.setText("hi");
  }

  public void moveToPosition(int x, int y) {

  }
}
