package com.muxistudio.chaser.ui.wordbank;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.bean.Wordbank;
import java.util.List;

/**
 * Created by ybao on 16/11/22.
 */

public class BankMarketAdapter extends RecyclerView.Adapter<BankMarketAdapter.ViewHolder> {


  private List<Wordbank> mWordbankList;
  private OnItemClickListener mItemClickListener;

  public BankMarketAdapter(List<Wordbank> wordbankList) {
    mWordbankList = wordbankList;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bankmarket, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, final int position) {
    holder.mTvName.setText(mWordbankList.get(position).name);
    holder.mTvCategory.setText(mWordbankList.get(position).category);
    holder.mTvDownload.setText(mWordbankList.get(position).download + "次下载");
    holder.mTvUploader.setText(mWordbankList.get(position).uploader);
    holder.mItemLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mItemClickListener.onItemClick(mWordbankList.get(position));
      }
    });
  }

  @Override public int getItemCount() {
    return mWordbankList.size();
  }

  public void setOnItemClickListener(OnItemClickListener listener){
    mItemClickListener = listener;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_uploader) TextView mTvUploader;
    @BindView(R.id.tv_category) TextView mTvCategory;
    @BindView(R.id.tv_download) TextView mTvDownload;
    @BindView(R.id.item_layout) RelativeLayout mItemLayout;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);
    }
  }

  interface OnItemClickListener{
    void onItemClick(Wordbank wordbank);
  }
}
