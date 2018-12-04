package com.example.tam.cnpm.ui.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.User;
import com.example.tam.cnpm.ui.login.LoginActivity_;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import static com.example.tam.cnpm.Constant.REQUEST_CODE_LIBRARY;

@EActivity(R.layout.activity_profile)
public class ProfileActivity extends BaseActivity<ProfilePresenterImpl> implements ProfileContract.ProfileView {

    @ViewById(R.id.edit_fname_profile)
    EditText mEditFirstName;

    @ViewById(R.id.edit_lname_profile)
    EditText mEditLastName;

    @ViewById(R.id.text_email_profile)
    TextView mTextEmail;

    @ViewById(R.id.image_profile)
    CircleImageView image;

    Uri mUri;
    boolean isChangeAvatar;
    @Override
    protected void initPresenter() {
        mPresenter = new ProfilePresenterImpl(this);
    }

    @Override
    protected void afterView() {
        setTitle("Profile");
        mPresenter.setLogIn();
    }

    public void editProfileOnClick(View view) {
        mPresenter.editProfile(mEditFirstName.getText().toString(), mEditLastName.getText().toString(), isChangeAvatar?mUri:null);
    }

    public void logOutProfileOnClick(View view) {
        mPresenter.logOut();
    }

    @Override
    public void setProfile(User user) {
        mEditFirstName.setText(user.getFirstName());
        mEditLastName.setText(user.getLastName());
        mTextEmail.setText(user.getEmail());
        Picasso.get().load("http://52.14.71.211" + user.getAvatar())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.ic_avatar_default)
                .into(image);
    }

    @Override
    public void changeActivity() {
        LoginActivity_.intent(this)
                .start();
        finish();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    public void imageOnclick(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_LIBRARY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LIBRARY && resultCode == RESULT_OK && data != null) {
            isChangeAvatar = true;
            mUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(mUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
