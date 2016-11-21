package com.muxistudio.chaser.ui.wordbank;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.db.Wordbank;
import com.muxistudio.chaser.utils.PreferenceUtil;
import java.util.List;

/**
 * Created by ybao on 16/11/21.
 */

public class WordbankAdapter extends RecyclerView.Adapter<WordbankAdapter.ViewHolder> {

  private List<Wordbank> mWordbankList;

  public WordbankAdapter(List<Wordbank> wordbanks) {
    super();
    mWordbankList = wordbanks;
  }

  @Override public long getItemId(int position) {
    return super.getItemId(position);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wordbank, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.mTvTitle.setText(mWordbankList.get(position).getName());
    holder.mTvCategory.setText(mWordbankList.get(position).getCategory());
    if (position == PreferenceUtil.getInt(PreferenceUtil.KEY_CUR_REMBER_BANK)){
      holder.mTvUse.setText("当前背诵词库");
    }
  }

  @Override public int getItemCount() {
    return mWordbankList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);
    }

    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.tv_category) TextView mTvCategory;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.tv_use) TextView mTvUse;
  }
}
