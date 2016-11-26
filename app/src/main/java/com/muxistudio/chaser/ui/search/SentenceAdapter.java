package com.muxistudio.chaser.ui.search;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 */

public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.ViewHolder> {

  public List<WordExplainResult.Sentence> mSentences;

  public SentenceAdapter(List<WordExplainResult.Sentence> sentences) {
    mSentences = sentences;
    //Log.d("search",mSentences.get(0).networdEn);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sentence, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.mTvSentence.setText(mSentences.get(position).networdEn);
    holder.mTvExplain.setText(mSentences.get(position).networdCn);
  }

  @Override public int getItemCount() {
    return mSentences.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_sentence) TextView mTvSentence;
    @BindView(R.id.tv_explain) TextView mTvExplain;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);
    }
  }
}
