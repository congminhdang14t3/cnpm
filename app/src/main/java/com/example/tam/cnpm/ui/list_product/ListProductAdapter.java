package com.example.tam.cnpm.ui.list_product;

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
import com.example.tam.cnpm.service.response.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListProductAdapter extends BaseAdapter{

    Context mContext;
    private OnItemClickListener listener;
    private ArrayList<Product> mList;
    protected ListProductAdapter(Context mContext, ArrayList<Product> list, OnItemClickListener listener) {
        super(mContext);
        this.mContext = mContext;
        this.listener = listener;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_product,parent,false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindItemViewHolder((ContentViewHolder) holder, position);
    }

    @SuppressLint("ResourceType")
    private void onBindItemViewHolder(ContentViewHolder holder, int position) {
        Product response = mList.get(position);
        holder.mTextName.setText("Name Product: "+response.getName());
        holder.mTextPrice.setText("Price: "+response.getPrice()+"");
        holder.mTextStatus.setText("Status: "+response.getStatus());
        Picasso.get()
                .load(response.getPicture().isEmpty() ? "null"
                        : response.getPicture().get(0).getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextName,
        mTextPrice,
        mTextStatus;

        public ContentViewHolder(View v) {
            super(v);
            mImageView =  v.findViewById(R.id.image_product);
            mTextName = v.findViewById(R.id.text_product_name);
            mTextPrice = v.findViewById(R.id.text_product_price);
            mTextStatus = v.findViewById(R.id.text_product_status);
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
