package com.muxistudio.chaser.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ybao on 16/11/16.
 */

public class BaseActivity extends AppCompatActivity{

  private ProgressDialog mProgressDialog;

  private CompositeSubscription composSubscription;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public void showProgress (String msg){
    if (mProgressDialog == null){
      initProgressDialog();
    }
    mProgressDialog.setMessage(msg);
    if (!mProgressDialog.isShowing()){
      mProgressDialog.show();
    }
  }

  public void hideProgress(){
    if (mProgressDialog.isShowing()){
      mProgressDialog.hide();
    }
  }

  public void initProgressDialog(){
    mProgressDialog = new ProgressDialog(this);
  }

  public void addSubscription(Subscription s){
    if (composSubscription == null){
      composSubscription = new CompositeSubscription();
    }
    composSubscription.add(s);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (composSubscription != null && composSubscription.isUnsubscribed()){
      composSubscription.unsubscribe();
      composSubscription = null;
    }
  }

}
