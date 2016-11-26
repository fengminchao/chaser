package com.muxistudio.chaser.ui.search;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.muxistudio.chaser.R;
import com.muxistudio.chaser.bean.WordExplainResult;
import java.util.List;

/**
 * Created by ybao on 16/11/26.
 * 单词解释adapter
 */

public class ExplainAdapter extends RecyclerView.Adapter<ExplainAdapter.ViewHolder> {

  private List<WordExplainResult.BaseInfo.Symbol.Part> mPartList;

  public ExplainAdapter(List<WordExplainResult.BaseInfo.Symbol.Part> partList) {
    mPartList = partList;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explain, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.mTvClass.setText(mPartList.get(position).part);
    holder.mTvExplain.setText(TextUtils.join(";",mPartList.get(position).means));
  }

  @Override public int getItemCount() {
    return mPartList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @BindView(R.id.tv_explain) TextView mTvExplain;
    @BindView(R.id.tv_class) TextView mTvClass;
  }
}
