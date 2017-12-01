package il.co.moveyorg.movey.ui.auth.editprofile;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.firebase.FirebaseDbHelper;
import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.base.BaseActivity;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener, EditProfileMvpView {

    @BindView(R.id.activity_edit_user_details_image)
    CircleImageView profileImageView;

    @BindView(R.id.activity_edit_user_details_edittext_username)
    EditText userNameEditText;

    @BindView(R.id.activity_edit_user_details_edittext_first_name)
    EditText firstNameEditText;

    @BindView(R.id.activity_edit_user_details_edittext_last_name)
    EditText lastNameEditText;

    @BindView(R.id.activity_edit_user_details_edittext_country)
    EditText countryEditText;

    @BindView(R.id.activity_edit_user_details_btn_done)
    Button doneBtn;

    private FirebaseUser currentUser;

    @Inject
    EditProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);
        activityComponent().inject(this);

        ButterKnife.bind(this);
        profileImageView.setOnClickListener(this);
        doneBtn.setOnClickListener(this);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            finish();
            return;
        }

        presenter.attachView(this);
        presenter.init();

        // TODO: listen (probably in base activity) to auth events and finish activity if logged out
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_edit_user_details_btn_done: {
                syncUserModel();
                presenter.saveProfile();
                break;
            }
            case R.id.activity_edit_user_details_image: {
                pickProfileImage();
                break;
            }
        }
    }

    private void pickProfileImage() {
        FishBun.with(EditProfileActivity.this)
                .MultiPageMode()
                .setPickerSpanCount(4)
                .setMaxCount(1)
                .setActionBarColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"), true)
                .setActionBarTitleColor(Color.parseColor("#000000"))
                .setButtonInAlbumActivity(true)
                .setCamera(true).setReachLimitAutomaticClose(false)
                .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp))
                .setOkButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_check_black_24dp))
                .setAllViewTitle("Gallery")
                .setActionBarTitle("Movey")
                .textOnImagesSelectionLimitReached("You can't select any more.")
                .textOnNothingSelected("Please select a photo!")
                .startAlbum();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        switch (requestCode) {
            case Define.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    ArrayList<Uri> path = imageData.getParcelableArrayListExtra(Define.INTENT_PATH);
                    if (path != null) {
                        presenter.setImageUriToUpload(path.get(0));
                        profileImageView.setImageURI(path.get(0));
                    }
                    break;
                }
        }
    }


    void syncUserModel() {

        presenter.syncUserModel(
                userNameEditText.getText().toString(),
                firstNameEditText.getText().toString(),
                lastNameEditText.getText().toString(),
                countryEditText.getText().toString()
                );
    }


    @Override
    public void showProfile(User user) {
        if (user != null) {

            userNameEditText.setText(user.getUserName());
            firstNameEditText.setText(user.getFirstName());
            lastNameEditText.setText(user.getLastName());
            countryEditText.setText(user.getCountry());

            //TODO: investigate bug (You cannot start a load for a destroyed activity)
            Glide.with(this).load(user.getProfileImageUrl()).into(profileImageView);
        }
    }

    @Override
    public void onSaveProfile() {
        Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onError() {

    }

    @Override
    public void showToast(String msg) {

    }
}
