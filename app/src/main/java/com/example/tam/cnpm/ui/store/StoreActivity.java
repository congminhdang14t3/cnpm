package com.example.tam.cnpm.ui.store;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.base.BaseAdapter;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.service.response.Store;
import com.example.tam.cnpm.ui.detail_product.DetailProductActivity_;
import com.example.tam.cnpm.ui.list_product.ListProductAdapter;
import com.example.tam.cnpm.ui.list_product.ProductActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.tam.cnpm.Constant.STORE_EXTRAS;

@EActivity(R.layout.activity_store)
public class StoreActivity extends BaseActivity<StorePresenterImpl> implements StoreContract.StoreView, BaseAdapter.OnItemClickListener {
    @Extra(STORE_EXTRAS)
    int mStoreId;
    GoogleMap map;
    final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 2805;
    @ViewById(R.id.image_store)
    ImageView mImageStore;
    Store mStore;
    MapFragment mapFragment;

    @ViewById(R.id.recycle_view_store)
    RecyclerView mRecyclerView;

    ListProductAdapter mAdapter;
    ArrayList<Product> mList;

    @Override
    protected void initPresenter() {
        mPresenter = new StorePresenterImpl(this);
    }

    @Override
    protected void afterView() {
        setTitle("Store");
        mList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new ListProductAdapter(this, mList, this);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getStoreById(mStoreId);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
    }

    @Override
    public void init(Store store) {
        mStore = store;
        showLoading();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

                    @Override
                    public void onMapLoaded() {
                        dismissLoading();
                        pemisstion();
                    }
                });
            }
        });
    }

    @Override
    public void getListProduct(ArrayList<Product> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    void pemisstion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {

                // Các quyền cần người dùng cho phép.
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};

                // Hiển thị một Dialog hỏi người dùng cho phép các quyền trên.
                ActivityCompat.requestPermissions(this, permissions,
                        REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

                return;
            }
        }
        show();
    }

    void show() {
        try {
            String[] location = mStore.getLocation().split(", ");
            LatLng store = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(store, 17));
            map.addMarker(new MarkerOptions()
                    .title(mStore.getName())
                    .snippet(mStore.getAddress())
                    .position(store));
        } catch (Exception e) {
        }
        mPresenter.getListProduct(mStoreId);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION: {
                // Chú ý: Nếu yêu cầu bị bỏ qua, mảng kết quả là rỗng.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();

                    // Hiển thị vị trí hiện thời trên bản đồ.
                    show();
                }
                // Hủy bỏ hoặc từ chối.
                else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    public void showProfileOnclick(View view) {
        String information = "Name: " + mStore.getName() +
                ".\nAddress: " + mStore.getAddress() +
                ".\nContact: " + mStore.getPhone()+".";
        showAlertDialog(information);
    }

    @Override
    public void onItemClick(int position) {
        DetailProductActivity_.intent(this)
                .mProduct(mList.get(position))
                .start();
    }
}
