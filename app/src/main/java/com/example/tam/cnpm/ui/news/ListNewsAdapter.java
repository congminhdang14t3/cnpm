package com.example.tam.cnpm.ui.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseAdapter;
import com.example.tam.cnpm.service.response.DocBao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListNewsAdapter extends BaseAdapter{

    Context mContext;
    private OnItemClickListener listener;
    private ArrayList<DocBao> mList;
    protected ListNewsAdapter(Context mContext, ArrayList<DocBao> list, OnItemClickListener listener) {
        super(mContext);
        this.mContext = mContext;
        this.listener = listener;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_news,parent,false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindItemViewHolder((ContentViewHolder) holder, position);
    }

    private void onBindItemViewHolder(ContentViewHolder holder, int position) {
        DocBao response = mList.get(position);
        Picasso.get()
                .load(response.getmImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(holder.mImageView);
        holder.mTextTitle.setText(response.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextTitle;

        public ContentViewHolder(View v) {
            super(v);
            mImageView =  v.findViewById(R.id.ivDocbao);
            mTextTitle = v.findViewById(R.id.tvTitleDocbao);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
            v.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.scale_list));
        }
    }
}
