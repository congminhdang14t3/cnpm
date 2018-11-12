package com.example.tam.cnpm.ui.detail_product;

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
import com.example.tam.cnpm.service.response.FeedBack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedBackAdapter extends BaseAdapter {

    Context mContext;
    private ArrayList<FeedBack> mList;

    protected FeedBackAdapter(Context mContext, ArrayList<FeedBack> list) {
        super(mContext);
        this.mContext = mContext;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_feedback, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindItemViewHolder((ContentViewHolder) holder, position);
    }

    private void onBindItemViewHolder(final ContentViewHolder holder, final int position) {
        final FeedBack feedBack = mList.get(position);
        Picasso.get()
                .load(R.drawable.ic_avatar_default)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(holder.mImageView);
        holder.mTextCreated.setText(feedBack.getCreated());
        holder.mTextName.setText(feedBack.getCustomer());
        holder.mTextDetail.setText(feedBack.getDetail());
        holder.mTextStar.setText("Star: " + feedBack.getStar());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImageView;
        private TextView mTextName,
                mTextDetail,
                mTextCreated,
                mTextStar;

        public ContentViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.image_feedback);
            mTextName = v.findViewById(R.id.text_feedback_email);
            mTextDetail = v.findViewById(R.id.text_feedback_detail);
            mTextStar = v.findViewById(R.id.text_feedback_star);
            mTextCreated = v.findViewById(R.id.text_created_feedback);
            v.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale_list));
        }
    }
}
