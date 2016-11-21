package com.muxistudio.chaser.ui.about;

import android.content.Context;
import android.content.Intent;
import android.widget.Toolbar;
import com.muxistudio.chaser.ui.ToolbarActivity;

/**
 * Created by ybao on 16/11/21.
 */

public class AboutActivity extends ToolbarActivity{

  public static void start(Context context) {
      Intent starter = new Intent(context, AboutActivity.class);
      context.startActivity(starter);
  }
}
