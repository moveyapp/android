package il.co.moveyorg.movey.ui.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.base.BaseActivity;
import timber.log.Timber;

public class EditUserDetailsActivity extends BaseActivity implements View.OnClickListener {

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

    private DatabaseReference userDbRef;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);
        ButterKnife.bind(this);
        doneBtn.setOnClickListener(this);


        userDbRef =
                FirebaseDatabase.getInstance().getReference().child("social").child("users")
                        .child(firebaseAuth.getCurrentUser().getUid());

        userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                currentUser = dataSnapshot.getValue(User.class);
                userNameEditText.setText(currentUser.getUserName());
                firstNameEditText.setText(currentUser.getFirstName());
                lastNameEditText.setText(currentUser.getLastName());
                countryEditText.setText(currentUser.getCountry());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
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
        currentUser.setUserName(userNameEditText.getText().toString());
        currentUser.setFirstName(firstNameEditText.getText().toString());
        currentUser.setLastName(lastNameEditText.getText().toString());
        currentUser.setCountry(countryEditText.getText().toString());

        userDbRef.setValue(currentUser);
        finish();
    }
}
