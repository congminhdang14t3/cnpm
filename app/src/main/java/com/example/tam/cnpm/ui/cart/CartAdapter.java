package com.example.tam.cnpm.ui.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseAdapter;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter{

    Context mContext;
    private ArrayList<Cart> mList;
    protected CartAdapter(Context mContext, ArrayList<Cart> list) {
        super(mContext);
        this.mContext = mContext;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_cart,parent,false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindItemViewHolder((ContentViewHolder) holder, position);
    }

    private void onBindItemViewHolder(ContentViewHolder holder, final int position) {
        Cart response = mList.get(position);
        Product product = response.getProduct();
        holder.mTextName.setText("Name: "+product.getName());
        holder.mTextPrice.setText("Price: "+product.getPrice()+"");
        holder.mEditCountCart.setText(response.getQuantity()+"");
        holder.mLinearDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setMessage("delete this cart?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mList.remove(position);
                                notifyItemChanged(position);
                            }
                        })
                        .setNegativeButton("No",null)
                        .create().show();
            }
        });
        Picasso.get()
                .load(R.drawable.noimage)
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
        mTextPrice;
        LinearLayout mLinearDeleteCart;
        EditText mEditCountCart;
        public ContentViewHolder(View v) {
            super(v);
            mImageView =  v.findViewById(R.id.image_cart);
            mTextName = v.findViewById(R.id.text_cart_name);
            mTextPrice = v.findViewById(R.id.text_cart_price);
            mLinearDeleteCart = v.findViewById(R.id.linear_delete_cart);
            mEditCountCart = v.findViewById(R.id.edit_count_cart);
            v.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.scale_list));
        }
    }
}
