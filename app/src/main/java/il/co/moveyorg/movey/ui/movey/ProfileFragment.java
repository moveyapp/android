package il.co.moveyorg.movey.ui.movey;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.ui.auth.AuthActivity;
import il.co.moveyorg.movey.ui.auth.EditUserDetailsActivity;
import il.co.moveyorg.movey.ui.base.BaseFragment;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    @BindView(R.id.fragment_profile_image)
    ImageView userImage;

    @BindView(R.id.fragment_profile_email)
    TextView userEmail;

    @BindView(R.id.fragment_profile_name)
    TextView userFirstName;

    @BindView(R.id.fragment_profile_logout_button)
    Button logoutBtn;

    @BindView(R.id.fragment_profile_reset_password_button)
    Button resetPassBtn;

    @BindView(R.id.fragment_profile_edit_button)
    Button editBtn;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Timber.i("Profile Fragment -> User:" + firebaseAuth.getCurrentUser().getUid());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this,view);


        userEmail.setText(user.getEmail());
        userFirstName.setText(user.getDisplayName());
        logoutBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);
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
                startActivity(new Intent(getActivity(),EditUserDetailsActivity.class));
                break;
            }

        }
    }
}
