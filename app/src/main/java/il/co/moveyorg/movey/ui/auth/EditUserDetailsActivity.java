package il.co.moveyorg.movey.ui.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.firebase.FirebaseDbHelper;
import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.base.BaseActivity;
import timber.log.Timber;

public class EditUserDetailsActivity extends BaseActivity implements View.OnClickListener, ValueEventListener {

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

    private User currentUserModel;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);
        ButterKnife.bind(this);
        doneBtn.setOnClickListener(this);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null) {
            FirebaseDbHelper.Users.getUser(currentUser.getUid(),this);
        }

        // TODO: listen (probably in base activity) to auth events and finish activity if logged out
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_edit_user_details_btn_done: {
                saveDetails();
                break;
            }
        }
    }

    private void saveDetails() {

        if(currentUserModel != null) {
            currentUserModel.setUserName(userNameEditText.getText().toString());
            currentUserModel.setFirstName(firstNameEditText.getText().toString());
            currentUserModel.setLastName(lastNameEditText.getText().toString());
            currentUserModel.setCountry(countryEditText.getText().toString());
        }

        FirebaseDbHelper.Users.saveUser(currentUserModel);

        finish();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        currentUserModel = dataSnapshot.getValue(User.class);

        if(currentUserModel != null) {
            userNameEditText.setText(currentUserModel.getUserName());
            firstNameEditText.setText(currentUserModel.getFirstName());
            lastNameEditText.setText(currentUserModel.getLastName());
            countryEditText.setText(currentUserModel.getCountry());
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
