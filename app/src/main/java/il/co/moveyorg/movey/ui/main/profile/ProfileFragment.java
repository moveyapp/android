package il.co.moveyorg.movey.ui.main.profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.auth.editprofile.EditProfileActivity;
import il.co.moveyorg.movey.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAnalytics mFirebaseAnalytics;

    @BindView(R.id.fragment_profile_image)
    CircleImageView userImage;

    @BindView(R.id.fragment_profile_email_text)
    TextView userEmail;

    @BindView(R.id.fragment_profile_username_text)
    TextView userName;

    @BindView(R.id.fragment_profile_first_name_text)
    TextView userFirstName;

    @BindView(R.id.fragment_profile_last_name_text)
    TextView userLastName;

    @BindView(R.id.fragment_profile_country_text)
    TextView userCountry;

    @BindView(R.id.fragment_profile_logout_button)
    Button logoutBtn;

    @BindView(R.id.fragment_profile_reset_password_button)
    Button resetPassBtn;

    @BindView(R.id.fragment_profile_edit_button)
    Button editBtn;



    private DatabaseReference userDbRef;
    private User currentUser;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);


        userEmail.setText(user.getEmail());
        logoutBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);

        // TODO: move ugly logic to presenter
        userDbRef =
                FirebaseDatabase.getInstance().getReference().child("social").child("users")
                        .child(firebaseAuth.getCurrentUser().getUid());

        userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                currentUser = dataSnapshot.getValue(User.class);

                if(currentUser != null) {
                    userName.setText(currentUser.getUserName());
                    userFirstName.setText(currentUser.getFirstName());
                    userLastName.setText(currentUser.getLastName());
                    userCountry.setText(currentUser.getCountry());

                    if( getActivity() != null && !getActivity().isFinishing()) {
                        //TODO: investigate bug (You cannot start a load for a destroyed activity)
                        Glide.with(getActivity()).load(currentUser.getProfileImageUrl()).into(userImage);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_profile_logout_button: {
                firebaseAuth.signOut();
                break;
            }
            case R.id.fragment_profile_edit_button: {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "button-edit-profile");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "edit profile button");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);




                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                break;
            }

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
