package com.example.tam.cnpm.ui.list_product;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.service.response.Picture;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    Context mContext;
    List<Picture> mList;

    public ImagePagerAdapter(Context context, List<Picture> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.image_item,
                container, false);

        ImageView imageView = view.findViewById(R.id.image_detail_product);
        Picasso.get().load(mList.get(position).getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position+1)+"";
    }
}
