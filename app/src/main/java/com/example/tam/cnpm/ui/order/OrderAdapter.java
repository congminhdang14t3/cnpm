package com.example.tam.cnpm.ui.order;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseAdapter;
import com.example.tam.cnpm.service.response.Order;
import com.example.tam.cnpm.service.response.MessageResponse;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.service.retrofit2.APIUtils;
import com.example.tam.cnpm.ui.store.StoreActivity_;
import com.example.tam.cnpm.ulti.SharedPrefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tam.cnpm.Constant.STORE_EXTRAS;
import static com.example.tam.cnpm.Constant.TOKEN;
import static com.example.tam.cnpm.ulti.Ulti.changeMoneyIntToString;

public class OrderAdapter extends BaseAdapter {

    Context mContext;
    private ArrayList<Order> mList;

    protected OrderAdapter(Context mContext, ArrayList<Order> list) {
        super(mContext);
        this.mContext = mContext;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.order_item, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindItemViewHolder((ContentViewHolder) holder, position);
    }

    private void onBindItemViewHolder(final ContentViewHolder holder, final int position) {
        final Order order = mList.get(position);

        holder.textCreate.setText(order.getCreated());
        holder.textStore.setText(">>> " + order.getStore().getName());
        holder.textStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreActivity_.intent(mContext)
                        .extra(STORE_EXTRAS, order.getStore().getId())
                        .start();
            }
        });
        holder.textCode.setText(order.getOrderCode());
        holder.textMoney.setText(changeMoneyIntToString(order.getMoney()) + " đ");
        holder.textStatusPayment.setText(order.getStatusPayment());
        holder.textStatusOrder.setText(order.getStatusOrder());

        List<Product> listProduct = order.getProducts();
        String[] name = new String[listProduct.size() + 1];
        name[0] = "List Product";
        for (int i = 0; i < listProduct.size(); i++) {
            Product product = listProduct.get(i);
            name[i + 1] = product.getName() + "\n"
                    + changeMoneyIntToString(product.getPrice()) + " đ";
        }
        holder.spinner.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, name));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private Spinner spinner;
        private TextView textCreate,
                textStore,
                textCode,
                textMoney,
                textStatusPayment,
                textStatusOrder;

        public ContentViewHolder(View v) {
            super(v);
            spinner = v.findViewById(R.id.spinner_order);
            textCreate = v.findViewById(R.id.text_create_order);
            textStore = v.findViewById(R.id.text_store_order);
            textCode = v.findViewById(R.id.text_code_order);
            textMoney = v.findViewById(R.id.text_money_order);
            textStatusPayment = v.findViewById(R.id.text_status_payment);
            textStatusOrder = v.findViewById(R.id.text_status_order);
            v.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale_list));
        }
    }
}
