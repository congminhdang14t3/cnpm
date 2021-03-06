package com.example.tam.cnpm.ui.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseAdapter;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.MessageResponse;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.service.retrofit2.APIUtils;
import com.example.tam.cnpm.ui.detail_product.DetailProductActivity_;
import com.example.tam.cnpm.ui.list_product.ProductActivity;
import com.example.tam.cnpm.ulti.SharedPrefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.tam.cnpm.Constant.TOKEN;
import static com.example.tam.cnpm.ulti.Ulti.changeMoneyIntToString;

public class CartAdapter extends BaseAdapter {

    Context mContext;
    private ArrayList<Cart> mList;

    protected CartAdapter(Context mContext, ArrayList<Cart> list) {
        super(mContext);
        this.mContext = mContext;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_cart, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindItemViewHolder((ContentViewHolder) holder, position);
    }

    private void onBindItemViewHolder(final ContentViewHolder holder, final int position) {
        final Cart response = mList.get(position);
        final Product product = response.getProduct();
        holder.mTextName.setText(product.getName());
        holder.mTextCountCart.setText(response.getQuantity()+"");
        holder.mTextPrice.setText(changeMoneyIntToString(product.getPrice())+" đ");
        holder.mLinearDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setMessage(getContext().getString(R.string.delete_cart))
                        .setPositiveButton(mContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String token = SharedPrefs.getInstance().get(TOKEN, String.class);
                                if (!token.equals("")) {
                                    Call<MessageResponse> call = APIUtils.getData().modifyCart(token,
                                            response.getProduct().getId(), 0);
                                    call.enqueue(new Callback<MessageResponse>() {
                                        @Override
                                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response1) {
                                            if (response1.isSuccessful()) {
                                                mList.remove(response);
                                                notifyDataSetChanged();
                                                CartActivity.changeTotal();
                                                Toast.makeText(mContext, mContext.getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MessageResponse> call, Throwable t) {

                                        }
                                    });
                                } else {
                                    mList.remove(response);
                                    notifyDataSetChanged();
                                    CartActivity.changeTotal();
                                    Toast.makeText(mContext, mContext.getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(getContext().getString(R.string.no), null)
                        .create().show();
            }
        });
        holder.mTextDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailProductActivity_.intent(mContext)
                        .mProduct(product)
                        .start();
            }
        });
        if(product.getPicture().isEmpty()){
            Picasso.get()
                    .load(R.drawable.errorimage)
                    .into(holder.mImageView);
            return;
        }
        Picasso.get()
                .load(product.getPicture().get(0).getImage())
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
                mTextDetail,
                mTextCountCart;
        LinearLayout mLinearDeleteCart;

        public ContentViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.image_cart);
            mTextName = v.findViewById(R.id.text_cart_name);
            mTextPrice = v.findViewById(R.id.text_cart_price);
            mTextDetail = v.findViewById(R.id.text_detail_product);
            mLinearDeleteCart = v.findViewById(R.id.linear_delete_cart);
            mTextCountCart = v.findViewById(R.id.text_count_cart);
            v.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale_list));
        }
    }
}
